package com.cosmetics.lush.ui.products.product_details.productoption

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.interaction.product.cart.AddProductToCartUseCase
import com.cosmetics.domain.interaction.product.cart.AddToCartResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.OptionValue
import com.cosmetics.domain.model.product.Product
import com.cosmetics.domain.model.product.cart.AddProductToCartRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductOptionsViewModel(
    val product: Product,
    val quantity: String,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : BaseViewModel() {
    val response: MutableLiveData<AddToCartResponse> = MutableLiveData()
    private val optionMasterList = hashMapOf<String, Any>()
    private val optionCheckboxList =
        mutableListOf<HashMap<Int, MutableList<String>>>()

    init {
        setDefaultOptionValues()
    }

    override fun getBindingId(): Int = 0

    /**
     * There are default options available and local list is updated with default value.
     */
    private fun setDefaultOptionValues() {
        viewModelScope.launch {
            //It is running in separate thread just to avoid the overhead of for loop which will be
            //running in the page start up
            withContext(Dispatchers.IO) {
                product.options.forEach {
                    if (!it.value.isNullOrEmpty() && it.optionValue.isNullOrEmpty()) {
                        optionMasterList[it.productOptionId.toString()] = it.value
                    }
                }
            }
        }
    }

    fun onCheckedChange(productOptionMainId: Int, option: OptionValue, check: Boolean) {
        Log.d("onCheckedChange", "onCheckedChange: $check")
        if (check) {
            //checkbox is checked
            addToCheckBoxList(productOptionMainId, option)
        } else {
            //checkbox is unchecked
            removeCheckBoxList(productOptionMainId, option)
        }
    }

    /**
     * @optionCheckboxList : Is there becz there is a chance of multiple batch of checkbox option.
     *  @Structure_of_optionCheckboxList
     *  mapOf[
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId]),
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId])
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId]),
     *  ],
     *  mapOf[
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId]),
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId])
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId]),
     *  ]
     * When user clicks on checkbox item :
     *  1) If there no hashmap with key as the batch id then a new hashmap is create with key
     *  as the batch id nd values as the list of option user selected
     *  2) if  there is already a hashmap with key as the batch id then existing value(list)
     *  is update by adding the new user selected option to the list.
     *
     */
    private fun addToCheckBoxList(productOptionMainId: Int, option: OptionValue) {
        if (optionCheckboxList.isNullOrEmpty()) {
            val item = hashMapOf<Int, MutableList<String>>()
            item[productOptionMainId] = mutableListOf(
                option.productOptionValueId
            )
            optionCheckboxList.add(item)
        } else {
            updateExistingCheckBoxList(productOptionMainId, option)
        }
    }

    /**
     * When user clicks on checkbox item :
     *
     * ---> 2) if  there is already a hashmap with key as the batch id then existing value(list)
     *  is update by adding the new user selected option to the list.
     *
     *  @optionCheckboxList
     *  mapOf[
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId]),
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId])
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId]),
     *  ],
     *  mapOf[
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId]),
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId])
     *  (id,[selectedOptionId,selectedOptionId,selectedOptionId]),
     *  ]
     *
     */
    private fun updateExistingCheckBoxList(productOptionMainId: Int, option: OptionValue) {
        var isKeyFound = false
        optionCheckboxList.forEach { mainList ->
            mainList.forEach { checkList ->
                if (checkList.key == productOptionMainId) {
                    //Indicate that there is already an array created and we just need to add the new item
                    checkList.value.add(option.productOptionValueId)
                    isKeyFound = true
                }
            }
        }
        if (!isKeyFound) {
            //Create a new map becz not map is created for this id
            val item = hashMapOf<Int, MutableList<String>>()
            item[productOptionMainId] = mutableListOf(option.productOptionValueId)
            optionCheckboxList.add(item)
        }
    }

    private fun removeCheckBoxList(productOptionMainId: Int, option: OptionValue) {
        var itemPosition = -1
        var needToBeRemoved = false
        optionCheckboxList.forEachIndexed { position, it ->
            it.forEach { mainList ->
                if (mainList.key == productOptionMainId) {
                    removeItem(mainList.value, option.productOptionValueId)
                    itemPosition = position
                    if (mainList.value.isNullOrEmpty()) needToBeRemoved = true
                }
            }
        }
        if (needToBeRemoved)
            optionCheckboxList.remove(optionCheckboxList[itemPosition])
    }

    private fun removeItem(mutableList: MutableList<String>, optionValueId: String)
            : MutableList<String>? {
        val iterator = mutableList.iterator()
        while (iterator.hasNext()) {
            val it = iterator.next()
            if (it == optionValueId) {
                iterator.remove()
            }
        }
        if (mutableList.isNullOrEmpty()) return null
        return mutableList
    }

    /**
     * Method invoked when any option other than checkbox is clicked by the user
     */
    fun setOptionData(optionId: String, it: String) {
        optionMasterList[optionId] = it
    }

    fun addToCart() {
        executeUseCase {
            addProductToCartUseCase(
                AddProductToCartRequest(product.id, quantity, getProductOptions())
            )
                .onSuccess {
                    viewState.value = Completed
                    response.value = Results.Success(it).data
                }
                .onFailure {
                    handleError(it)
                }
        }
    }


    private fun getProductOptions(): HashMap<String, Any> {
        //Add checkbox options to the mainlist
        optionCheckboxList.forEach { optionMainCheckbox ->
            optionMainCheckbox.forEach { item ->
                optionMasterList[item.key.toString()] = item.value
            }
        }
        return optionMasterList
    }

    fun removeFromOptionData(id: String) {
        val iterator = optionMasterList.iterator()
        while (iterator.hasNext()) {
            val it = iterator.next()
            if (it.key == id) {
                iterator.remove()
            }
        }
    }

}

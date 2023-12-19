package com.cosmetics.domain.model.product


const val ORDER_DEFAULT = "p.sort_order"
const val PRICE = "pd.price"
const val NAME = "pd.name"
const val MODEL = "pd.model"
const val RATING = "rating"
const val ORDER_DESC = "DESC"
const val ORDER_ASC = "ASC"

data class ProductListRequest(
    val categoryId: Int,
    var pageNumber: Int = 1,
    var sortType: String = ORDER_DEFAULT,
    var sortOrder: String = ORDER_ASC
)
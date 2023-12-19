package com.cosmetics.domain.interaction.order

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results

interface GetKnetPayDetailsUseCase : BaseUseCase<String, String> {

    override suspend operator fun invoke(emptyString: String)
            : Results<String>

}
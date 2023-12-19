package com.cosmetics.domain.model.order

data class ProductListRequest(
    val categoryId: Int,
    var pageNumber: Int = 1
)
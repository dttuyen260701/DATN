package com.example.realestateapp.data.models

/**
 * Created by tuyen.dang on 5/27/2023.
 */

data class PagingModel(
    var pageIndex: Int = 1,
    var pageSize: Int = 15,
    var totalRecords: Int = 0,
    var totalPage: Int = 1
) {
    internal fun checkAvailableCallApi() = (pageIndex <= totalPage)
}

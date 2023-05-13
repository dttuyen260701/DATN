package com.example.realestateapp.data.models

/**
 * Created by tuyen.dang on 5/14/2023.
 */
 
data class PagingItem<T>(
    val items: MutableList<T>,
    val pageIndex: Int,
    val pageSize: Int,
    val totalRecords: Int,
    val pageCount: Int
)

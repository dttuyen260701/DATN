package com.example.realestateapp.util

import com.example.realestateapp.data.apiresult.ResponseAPI

/**
 * Created by tuyen.dang on 4/30/2023.
 */

class Constants {

    object HeaderRequest {
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_VALUE = "application/json"
        const val AUTHORIZATION = "Authorization"
    }

    object MethodRequest {

    }

    object DefaultValue {
        val DEFAULT_RESULT_API = ResponseAPI(
            false,
            "Lỗi khi thực hiện thao tác !!!",
            null
        )
    }

    object MessageErrorAPI {
        const val INVALID_INPUT_ERROR = "Vui lòng kiểm tra lại các trường đã nhập !"
        const val AUTHENTICATION_ERROR = "Vui lòng đăng nhập để thực hiện chức năng này !"
        const val INTERNAL_SERVER_ERROR = "Server đang bận, vui lòng thử lại sau !"
    }

    object DefaultConfig {
        //retrofit
        const val NETWORK_TIMEOUT = 30L
    }
}
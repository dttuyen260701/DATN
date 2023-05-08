package com.example.realestateapp.util

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
        const val MARGIN_VIEW = 10
        const val MARGIN_TEXT_FIELD = 15
        const val MARGIN_DIFFERENT_VIEW = 24
        const val TOOLBAR_HEIGHT = 56
        const val PADDING_VIEW = 5
        const val PADDING_ICON = 7
        const val PADDING_HORIZONTAL_SCREEN = 20
        const val PADDING_TEXT = 10
        const val ROUND_CIRCLE = 50
        const val ROUND_RECTANGLE = 20
        const val TRAILING_ICON_PADDING = 17
        const val BORDER_WIDTH = 4
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
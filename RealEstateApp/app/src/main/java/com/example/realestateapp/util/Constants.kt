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

    object DataStore {
        const val NAME = "real_estate"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
    }

    object DefaultValue {
        const val MARGIN_VIEW = 10
        const val MARGIN_DIFFERENT_VIEW = 24
        const val TOOLBAR_HEIGHT = 56
        const val PADDING_VIEW = 5
        const val PADDING_ICON = 7
        const val PADDING_HORIZONTAL_SCREEN = 20
        const val PADDING_TEXT = 10
        const val ROUND_CIRCLE = 50
        const val ROUND_RECTANGLE = 20
        const val ROUND_DIALOG = 10
        const val TRAILING_ICON_SIZE = 20
        const val ICON_ITEM_SIZE = 40
        const val TRAILING_ICON_PADDING = 17
        const val BORDER_WIDTH = 4
        const val WARNING_TEXT_SIZE = 10
        const val ALPHA_HINT_COLOR = 0.7f
        const val TWEEN_ANIMATION_TIME = 300
    }

    object PriceUnit {
        const val BILLION = "Tỷ"
        const val MILLION = "Triệu"
        const val THOUSAND_CHAR = "k"
        const val MILLION_CHAR = "m"
        const val BILLION_CHAR = "b"
    }

    object MessageErrorAPI {
        const val INVALID_INPUT_ERROR = "Vui lòng kiểm tra lại các trường đã nhập !"
        const val AUTHENTICATION_ERROR = "Vui lòng đăng nhập để thực hiện chức năng này !"
        const val INTERNAL_SERVER_ERROR = "Server đang bận, vui lòng thử lại sau !"
        const val NOT_FOUND_ERROR = "Không tìm thấy đường dẫn !"
        const val NOT_FOUND_INTERNET = "Vui lòng kiểm tra lại mạng !"
    }

    object DefaultConfig {
        //retrofit
        const val NETWORK_TIMEOUT = 30L
    }
}

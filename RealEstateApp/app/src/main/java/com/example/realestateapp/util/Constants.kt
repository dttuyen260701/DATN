package com.example.realestateapp.util

import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.ItemMessenger
import com.example.realestateapp.data.models.RealEstateDetail

/**
 * Created by tuyen.dang on 4/30/2023.
 */

class Constants {

    companion object {
        internal fun getIdChannel(idUser: Int, idGuest: Int): String =
            if (idUser < idGuest) "$idUser-$idGuest"
            else "$idGuest-$idUser"

        internal fun ItemMessenger.isPhoto() = typeMessage == MessageDefault.TYPE_PHOTO
    }

    object RequestNotification {
        const val DEFAULT_NOTIFICATION = 0
        const val MESSAGE_NOTIFICATION = 1
        const val POST_NOTIFICATION = 2
    }

    object NotificationChannel {
        const val DEFAULT_CHANNEL = 0
        const val MESSAGE_CHANNEL = 1
        const val POST_CHANNEL = 2
    }

    object FireBaseRef {
        const val ROOT_DATA = "data"
        const val CHANNEL_GUEST = "channel_guest"
        const val CHANNEL_CHAT = "channel_chat"
        const val CHANNEL_POST = "channel_post"
        const val PROPERTY_READ = "read"
    }

    object HeaderRequest {
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_VALUE = "application/json"
        const val AUTHORIZATION = "Authorization"
    }

    object MapConfig {
        const val MAX_ZOOM = 20f
        const val MIN_ZOOM = 12f
        const val DEFAULT_ZOOM = 16f
    }

    object DataStore {
        const val NAME = "real_estate"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
    }

    object MessageDefault {
        const val SEND_IMAGE = "Đã gửi 1 ảnh!"
        const val TYPE_MESSAGE = 0
        const val TYPE_PHOTO = 1
    }

    object DefaultField {
        const val FIELD_TYPE = "type"
        const val FIELD_ADDRESS = "address"
        const val FIELD_ADDRESS_MAP = "address_map"
        const val FIELD_DISTRICT = "district"
        const val FIELD_WARD = "ward"
        const val FIELD_STREET = "street"
        const val FIELD_PRICE = "price"
        const val FIELD_SQUARE = "square"
        const val FIELD_BED_ROOM = "bedroom"
        const val FIELD_FLOOR = "floor"
        const val FIELD_JURIDICAL = "juridical"
        const val FIELD_DIRECTION = "direction"
        const val FIELD_STREET_OF_FRONT = "street_of_front"
        const val FIELD_WIDTH = "width"
        const val FIELD_LENGTH = "length"
        const val FIELD_CAR_PARKING = "car_parking"
        const val FIELD_ROOFTOP = "rooftop"
        const val FIELD_DINING_ROOM = "dining_room"
        const val FIELD_KITCHEN_ROOM = "kitchen_room"
        const val FIELD_PREDICT_PRICE = "predict_price"
        const val FIELD_DATE = "date"
        const val FIELD_GENDER = "gender"
    }

    object PermissionTitle {
        const val PHONE = "Gọi điện thoại"
        const val COARSE_LOCATION = "Vị trí chính xác"
        const val FINE_LOCATION = "Vị trí tương đối"
        const val CAMERA = "Sử dụng Camera"
        const val WRITE_EXTERNAL = "Ghi bộ nhớ ngoài"
        const val READ_EXTERNAL = "Đoc bộ nhớ ngoài"
        const val POST_NOTIFICATIONS = "Tạo thông báo"
    }

    object DefaultValue {
        const val CHANNEL_ID = "CHANNEL"
        val DEFAULT_ITEM_CHOSEN = ItemChoose(0, "", 0)
        const val MAX_IMAGE_POST = 10
        const val DEFAULT_ID_POST = -1
        const val ALPHA_TITLE = 0.8f
        const val MARGIN_VIEW = 10
        const val MARGIN_DIFFERENT_VIEW = 24
        const val TOOLBAR_HEIGHT = 56
        const val IMAGE_SIZE = 78
        const val SELECT_BOX_HEIGHT = 42
        const val PADDING_VIEW = 5
        const val PADDING_ICON = 7
        const val PADDING_HORIZONTAL_SCREEN = 20
        const val PADDING_TEXT = 10
        const val ROUND_CIRCLE = 50
        const val BOTTOM_ICON_SIZE = 50
        const val ROUND_RECTANGLE = 20
        const val ROUND_DIALOG = 10
        const val TRAILING_ICON_SIZE = 20
        const val ICON_ITEM_SIZE = 40
        const val TRAILING_ICON_PADDING = 17
        const val BORDER_WIDTH = 1
        const val WARNING_TEXT_SIZE = 10
        const val ALPHA_HINT_COLOR = 0.5f
        const val TWEEN_ANIMATION_TIME = 300
        const val CLICK_BUTTON_TIME = 1000L
        const val SEARCH_TIME = 700L
        const val MAP_INSTALL_REQUEST = "Vui lòng cài đặt Google Map!"
        val REAL_ESTATE_DEFAULT = RealEstateDetail(
            postId = 0,
            description = "",
            title = null,
            createdDate = "",
            ownerName = null,
            ownerPhone = null,
            price = 0.0,
            views = 0,
            isSaved = false,
            legalName = "",
            propertyTypeName = "",
            nameDirection = "",
            width = 0f,
            square = 0f,
            carParking = null,
            streetInFront = null,
            length = 0f,
            address = "",
            latitude = 0.0,
            longitude = 0.0,
            bedrooms = null,
            floors = null,
            kitchen = null,
            rooftop = null,
            diningRoom = null,
            images = mutableListOf(),
            status = 1,
            ownerId = 1,
            dueDate = "",
            comboOptionId = 1,
            comboOptionName = "",
            propertyTypeId = 0,
            legalId = 0,
            districtId = 0,
            districtName = "",
            wardId = 0,
            wardName = "",
            streetId = 0,
            streetName = "",
            directionId = 0,
            suggestedPrice = 0.0,
            imageOwner = ""
        )
    }

    object ComboOptions {
        const val BRONZE = 3
        const val SILVER = 2
        const val GOLD = 1
    }

    object PriceUnit {
        const val THOUSAND_BILLION = "Ngàn Tỷ"
        const val BILLION = "Tỷ"
        const val MILLION = "Triệu"
        const val THOUSAND = "Ngàn"
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

    object ValidData {
        const val INVALID_EMAIL = "Sai định dạng email"
        const val INVALID_PASSWORD = "Sai định dạng mật khẩu"
    }

    object DefaultConfig {
        //retrofit
        const val NETWORK_TIMEOUT = 30L
    }
}

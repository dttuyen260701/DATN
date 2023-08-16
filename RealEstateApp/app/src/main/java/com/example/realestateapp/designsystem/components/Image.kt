package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.util.Constants.DefaultValue.BORDER_WIDTH
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT

/**
 * Created by tuyen.dang on 5/6/2023.
 */

@Composable
fun ImageProfile(
    modifier: Modifier = Modifier,
    size: Int = TOOLBAR_HEIGHT * 2,
    model: String = "",
    contentDescription: String? = null
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(size.dp)
            .border(
                BorderStroke(BORDER_WIDTH.dp, RealEstateAppTheme.colors.primary),
                CircleShape
            )
            .padding(BORDER_WIDTH.dp)
            .clip(CircleShape)
            .then(modifier),
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.sale_real_estate),
        placeholder = painterResource(R.drawable.sale_real_estate)
    )
}

@Composable
@Preview
fun ImagePreview() {
    ImageProfile(
        model = "https://binhminhdigital.com/StoreData/PageData/2372/nhung-loi-co-ban-khi-chup-anh-phong-canh%20(5).jpg"
    )
}

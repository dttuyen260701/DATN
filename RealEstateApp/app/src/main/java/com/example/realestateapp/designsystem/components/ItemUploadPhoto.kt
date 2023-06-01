package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.realestateapp.R
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.TOOLBAR_HEIGHT
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_SIZE

/**
 * Created by tuyen.dang on 6/1/2023.
 */

@Composable
internal fun ItemUploadPhoto(
    modifier: Modifier = Modifier,
    title: String,
    maxSize: Int,
    data: MutableList<Image>,
    onAddImageClick: () -> Unit,
    onItemDelete: (Image) -> Unit
) {
    ConstraintLayout(

    ) {
        val (btnAdd, slideShow) = createRefs()

    }
}

@Composable
internal fun PhotoRadius(
    modifier: Modifier,
    size: Int = TOOLBAR_HEIGHT * 2,
    item: Image,
    onItemClick: () -> Unit,
    onItemDelete: (Image) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .size(size.dp)
            .then(modifier)
    ) {
        val (img, btnDelete) = createRefs()
        AsyncImage(
            model = item.url,
            contentDescription = null,
            modifier = Modifier
                .border(
                    BorderStroke(1.dp, Color.White),
                    RoundedCornerShape(ROUND_DIALOG.dp)
                )
                .padding(1.dp)
                .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                .clickable {
                    onItemClick()
                }
                .constrainAs(img) {
                    linkTo(parent.top, parent.bottom)
                    linkTo(parent.start, parent.end)
                },
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.sale_real_estate)
        )
        ButtonUnRepeating(onClick = { onItemDelete(item) }) {
            BaseIcon(
                icon = AppIcon.DrawableResourceIcon(RealEstateIcon.Clear),
                modifier = Modifier
                    .size(TRAILING_ICON_SIZE.dp)
                    .clickable { it() }
                    .constrainAs(btnDelete) {
                        top.linkTo(img.top, PADDING_VIEW.dp)
                        end.linkTo(img.end, PADDING_VIEW.dp)
                    },
                contentDescription = null,
                tint = RealEstateAppTheme.colors.primary
            )
        }
    }
}

package com.example.realestateapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.realestateapp.R
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealStateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.ICON_ITEM_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_RECTANGLE
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_SIZE

/**
 * Created by tuyen.dang on 5/12/2023.
 */

@Composable
internal fun ItemType(
    modifier: Modifier = Modifier,
    item: ItemChoose,
    borderColor: Color = RealEstateAppTheme.colors.primary,
    borderColorSelected: Color = RealEstateAppTheme.colors.bgTextField,
    textColor: Color = RealEstateAppTheme.colors.primary,
    textColorSelected: Color = Color.White,
    bgColor: Color = RealEstateAppTheme.colors.bgTextField,
    bgColorSelected: Color = RealEstateAppTheme.colors.primary,
    onItemClick: (ItemChoose) -> Unit,
) {
    item.run {
        Text(text = name, style = RealEstateTypography.body1.copy(
            color = if (isSelected) textColorSelected else textColor,
            textAlign = TextAlign.Center
        ), modifier = Modifier
            .wrapContentHeight()
            .border(
                BorderStroke(
                    width = (0.5f).dp,
                    color = if (isSelected) borderColorSelected else borderColor
                ), shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                onItemClick(this@run)
            }
            .background(
                color = if (isSelected) bgColorSelected else bgColor,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .then(modifier))
    }
}

@Composable
internal fun ItemRealEstate(
    modifier: Modifier = Modifier,
    item: RealEstateList
) {
    val roundedCornerShape = RoundedCornerShape(ROUND_RECTANGLE.dp)
    item.run {
        ConstraintLayout(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth(0.85f)
                .clip(roundedCornerShape)
                .background(
                    color = Color.White,
                    shape = roundedCornerShape
                )
                .border(
                    BorderStroke(
                        width = (0.5f).dp,
                        color = Color.Gray.copy(0.3f)
                    ),
                    shape = roundedCornerShape
                )
        ) {
            val (img, btnSave, tvViews, tvName, tvAddress,
                tvSquare, tvFloors, tvBedrooms, tvPrice) = createRefs()

            AsyncImage(
                model = image ?: "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .constrainAs(img) {
                        top.linkTo(parent.top)
                    },
                placeholder = painterResource(id = R.drawable.sale_real_estate)
            )
            IconButton(modifier = Modifier
                .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                .background(RealEstateAppTheme.colors.bgIconsWhite50)
                .constrainAs(btnSave) {
                    top.linkTo(parent.top, MARGIN_VIEW.dp)
                    end.linkTo(parent.end, MARGIN_VIEW.dp)
                }
                .size(ICON_ITEM_SIZE.dp), onClick = {

            }) {
                BaseIcon(
                    icon = AppIcon.DrawableResourceIcon(
                        if (isSaved) RealStateIcon.PostSaved
                        else RealStateIcon.PostSavedOutline
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(TRAILING_ICON_SIZE.dp),
                    tint = Color.White
                )
            }
            TextIcon(
                text = views.toString(),
                icon = AppIcon.DrawableResourceIcon(RealStateIcon.Visibility),
                isIconFirst = false,
                size = 14,
                modifier = Modifier
                    .constrainAs(tvViews) {
                        top.linkTo(img.bottom, MARGIN_VIEW.dp)
                        end.linkTo(parent.end, MARGIN_VIEW.dp)
                    }
            )
            Text(
                text = title,
                style = RealEstateTypography.body1.copy(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .constrainAs(tvName) {
                        top.linkTo(tvViews.top)
                        linkTo(
                            start = parent.start,
                            startMargin = MARGIN_VIEW.dp,
                            end = tvViews.start,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = address,
                style = RealEstateTypography.body1.copy(
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .constrainAs(tvAddress) {
                        top.linkTo(tvName.bottom, MARGIN_VIEW.dp)
                        linkTo(
                            start = parent.start,
                            startMargin = MARGIN_VIEW.dp,
                            end = tvPrice.start,
                            endMargin = MARGIN_VIEW.dp,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    }
            )
            TextIcon(
                text = stringResource(R.string.squareUnit, square.toString()),
                icon = AppIcon.DrawableResourceIcon(RealStateIcon.Square),
                size = 12,
                modifier = Modifier
                    .constrainAs(tvSquare) {
                        linkTo(
                            top = tvAddress.bottom,
                            topMargin = PADDING_VIEW.dp,
                            bottom = parent.bottom,
                            bottomMargin = MARGIN_DIFFERENT_VIEW.dp,
                        )
                        start.linkTo(parent.start, MARGIN_VIEW.dp)
                    }
            )
            floors?.let {
                TextIcon(
                    text = it.toString(),
                    icon = AppIcon.DrawableResourceIcon(RealStateIcon.Floors),
                    size = 12,
                    modifier = Modifier
                        .constrainAs(tvFloors) {
                            linkTo(top = tvSquare.top, bottom = tvSquare.bottom)
                            start.linkTo(tvSquare.end, MARGIN_VIEW.dp)
                        }
                )
            }
            bedRooms?.let {
                TextIcon(
                    text = it.toString(),
                    icon = AppIcon.DrawableResourceIcon(RealStateIcon.Bed),
                    size = 12,
                    modifier = Modifier
                        .constrainAs(tvBedrooms) {
                            linkTo(top = tvSquare.top, bottom = tvSquare.bottom)
                            start.linkTo(
                                (if (floors != null) tvFloors else tvSquare).end,
                                MARGIN_VIEW.dp
                            )
                        }
                )
            }
            TextIcon(
                text = price.toString(),
                icon = AppIcon.DrawableResourceIcon(RealStateIcon.Dollar),
                size = 23,
                modifier = Modifier
                    .constrainAs(tvPrice) {
                        linkTo(top = tvAddress.top, bottom = tvSquare.bottom, bias = 1f)
                        linkTo(
                            start = (if (bedRooms != null) tvBedrooms
                            else if (floors != null) tvFloors
                            else tvSquare).end,
                            startMargin = MARGIN_VIEW.dp,
                            end = parent.end,
                            endMargin = MARGIN_VIEW.dp,
                            bias = 1f
                        )
                    }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewItemRealEstate() {
    ItemRealEstate(
        item = RealEstateList(
            id = "123",
            image = "https://cdn.dribbble.com/userupload/4259155/file/original-9c5357566528015850f4375ca16fee72.jpg?compress=1&resize=1024x768",
            title = "Nhà 3 ",
            square = 100.0f,
            price = 3_000_000f,
            bedRooms = 3,
            floors = 3,
            address = "15 Lỗ giáng 19",
            views = 1234,
            isSaved = true
        )
    )
}

@Preview
@Composable
private fun PreviewItemType() {
    var item = ItemChoose(2, "Test")
    ItemType(item = item, onItemClick = {
        item = it.copy(isSelected = !it.isSelected)
    })
}

package com.example.realestateapp.designsystem.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.realestateapp.R
import com.example.realestateapp.data.models.Image
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon
import com.example.realestateapp.designsystem.theme.RealEstateAppTheme
import com.example.realestateapp.designsystem.theme.RealEstateTypography
import com.example.realestateapp.extension.setVisibility
import com.example.realestateapp.ui.base.BaseIcon
import com.example.realestateapp.util.Constants.DefaultValue.ALPHA_TITLE
import com.example.realestateapp.util.Constants.DefaultValue.IMAGE_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.PADDING_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.ROUND_DIALOG
import com.example.realestateapp.util.Constants.DefaultValue.TRAILING_ICON_SIZE
import com.example.realestateapp.util.Constants.DefaultValue.TWEEN_ANIMATION_TIME

/**
 * Created by tuyen.dang on 6/1/2023.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ItemUploadPhoto(
    modifier: Modifier = Modifier,
    isUpLoading: Boolean,
    title: String,
    maxSize: Int,
    data: MutableList<Image>,
    onAddImageClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onItemDelete: (Image) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = PADDING_VIEW.dp)
            .wrapContentHeight()
            .then(modifier)
    ) {
        val (tvTitle, btnAdd, listItem) = createRefs()
        Text(
            text = title,
            style = RealEstateTypography.body1.copy(
                fontSize = 13.sp,
                color = RealEstateAppTheme.colors.primary.copy(ALPHA_TITLE),
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .constrainAs(tvTitle) {
                    top.linkTo(parent.top)
                    linkTo(parent.start, parent.end, bias = 0f)
                }
        )
        ButtonUnRepeating(onAddImageClick) {
            IconButton(
                onClick = it,
                modifier = Modifier
                    .size(IMAGE_SIZE.dp)
                    .clip(RoundedCornerShape(ROUND_DIALOG.dp))
                    .background(
                        color = RealEstateAppTheme.colors.bgTextField
                    )
                    .border(
                        BorderStroke(
                            width = (0.5f).dp,
                            color = RealEstateAppTheme.colors.primary
                        ),
                        shape = RoundedCornerShape(ROUND_DIALOG.dp)
                    )
                    .constrainAs(btnAdd) {
                        start.linkTo(tvTitle.start)
                        linkTo(
                            top = tvTitle.bottom,
                            topMargin = MARGIN_VIEW.dp,
                            bottom = parent.bottom
                        )
                        visibility = setVisibility(data.size < maxSize)
                    }

            ) {
                if (isUpLoading) {
                    CircularProgressIndicator(
                        color = RealEstateAppTheme.colors.progressBar,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else {
                    BaseIcon(
                        icon = AppIcon.ImageVectorIcon(RealEstateIcon.Add),
                        modifier = Modifier
                            .size(TRAILING_ICON_SIZE.dp),
                        contentDescription = null,
                        tint = RealEstateAppTheme.colors.primary
                    )
                }
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(listItem) {
                    linkTo(btnAdd.top, btnAdd.bottom)
                    linkTo(btnAdd.end, parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(PADDING_VIEW.dp),
            contentPadding = PaddingValues(horizontal = MARGIN_VIEW.dp)
        ) {
            items(
                items = data,
                key = { image ->
                    image.toString()
                },
            ) { image ->
                PhotoRadius(
                    item = image,
                    onItemClick = {
                        onItemClick(data.indexOf(image))
                    },
                    onItemDelete = onItemDelete,
                    modifier = Modifier.animateItemPlacement(
                        tween(durationMillis = TWEEN_ANIMATION_TIME)
                    )
                )
            }
        }
    }
}

@Composable
internal fun PhotoRadius(
    modifier: Modifier = Modifier,
    size: Int = IMAGE_SIZE,
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
                tint = RealEstateAppTheme.colors.primaryVariant
            )
        }
    }
}

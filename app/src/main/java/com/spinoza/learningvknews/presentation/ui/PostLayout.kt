package com.spinoza.learningvknews.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spinoza.learningvknews.R
import com.spinoza.learningvknews.presentation.ui.theme.LearningVkNewsTheme

private const val WEIGHT = 1f

@Composable
fun PostCard() {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(size = 8.dp),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            PostHeader(R.drawable.post_comunity_thumbnail, "уволено", "14:15")
            SpacerHeight8dp()

            PostBody(
                R.drawable.post_content_image,
                stringResource(R.string.template_text)
            )
            SpacerHeight8dp()

            PostFooter(206, 106, 11, 197)
        }
    }
}

@Composable
private fun PostHeader(imageId: Int, text: String, time: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = imageId),
            contentDescription = stringResource(R.string.image_author)
        )
        SpacerWidth8dp()
        Column(
            modifier = Modifier.weight(WEIGHT)
        ) {
            Text(
                text = text,
                color = MaterialTheme.colors.onPrimary
            )
            SpacerWidth4dp()
            Text(
                text = time,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = stringResource(R.string.menu),
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun PostBody(imageId: Int, text: String) {
    Column {
        Text(text = text)
        SpacerHeight8dp()
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = imageId),
            contentDescription = stringResource(R.string.image_content),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
private fun PostFooter(viewsCount: Int, repostsCount: Int, commentsCount: Int, likesCount: Int) {
    Row{
        Row(
            modifier = Modifier.weight(WEIGHT)
        ) {
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                iconDescriptionResId = R.string.views_count,
                text = "$viewsCount"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(WEIGHT),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconWithText(
                iconResId = R.drawable.ic_share,
                iconDescriptionResId = R.string.reposts_count,
                text = "$repostsCount"
            )

            IconWithText(
                iconResId = R.drawable.ic_comment,
                iconDescriptionResId = R.string.comments_count,
                text = "$commentsCount"
            )

            IconWithText(
                iconResId = R.drawable.ic_like,
                iconDescriptionResId = R.string.likes_count,
                text = "$likesCount"
            )
        }
    }
}

@Composable
private fun IconWithText(
    iconResId: Int,
    iconDescriptionResId: Int,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            painter = painterResource(id = iconResId),
            contentDescription = stringResource(iconDescriptionResId),
            tint = MaterialTheme.colors.onSecondary
        )
        SpacerWidth4dp()
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun SpacerWidth8dp() {
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
private fun SpacerWidth4dp() {
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
private fun SpacerHeight8dp() {
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
private fun PreviewLight() {
    LearningVkNewsTheme(darkTheme = false) {
        PostCard()
    }
}

@Preview
@Composable
private fun PreviewDark() {
    LearningVkNewsTheme(darkTheme = true) {
        PostCard()
    }
}
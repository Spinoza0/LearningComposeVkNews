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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spinoza.learningvknews.R
import com.spinoza.learningvknews.presentation.ui.theme.LearningVkNewsTheme

@Composable
fun PostCard() {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(size = 8.dp),
        //backgroundColor = MaterialTheme.colors.background,
        //border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            PostHeader(R.drawable.post_comunity_thumbnail, "уволено", "14:15")
            PostBody(
                R.drawable.post_content_image,
                "кабаныч, когда узнал, что если сотрудникам не платить, они начинают " +
                        "умирать от голода"
            )
            PostFooter(206, 106, 11, 197)
        }
    }
}

@Composable
private fun PostHeader(imageId: Int, text: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = imageId),
            contentDescription = stringResource(R.string.image_author)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.padding(4.dp))
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
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = imageId),
            contentDescription = stringResource(R.string.image_content)
        )
    }
}

@Composable
private fun PostFooter(viewsCount: Int, repostsCount: Int, commentsCount: Int, likesCount: Int) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = "$viewsCount"
        )
        Image(
            modifier = Modifier.size(25.dp),
            painter = painterResource(id = R.drawable.ic_views_count),
            contentDescription = stringResource(R.string.views_count)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = "$repostsCount"
            )
            Image(
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = stringResource(R.string.reposts_count)
            )
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = "$commentsCount"
            )
            Image(
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.ic_comment),
                contentDescription = stringResource(R.string.comments_count)
            )
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = "$likesCount"
            )
            Image(
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = stringResource(R.string.likes_count)
            )
        }
    }
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
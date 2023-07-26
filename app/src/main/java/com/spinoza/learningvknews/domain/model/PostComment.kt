package com.spinoza.learningvknews.domain.model

import com.spinoza.learningvknews.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.comment_author_avatar,
    val commentText: String = "Long comment",
    val publicationDate: String = "15:35",
)
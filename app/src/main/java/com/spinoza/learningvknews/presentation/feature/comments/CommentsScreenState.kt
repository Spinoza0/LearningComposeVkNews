package com.spinoza.learningvknews.presentation.feature.comments

import com.spinoza.learningvknews.domain.model.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    class Comments(val comments: List<PostComment>) : CommentsScreenState()
}
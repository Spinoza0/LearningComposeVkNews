package com.spinoza.learningvknews.data.network

import com.spinoza.learningvknews.domain.TokenStorage

object TokenStorageImpl : TokenStorage {

    private var value = ""

    override fun setToken(token: String) {
        value = token
    }

    override fun getToken() = value
}
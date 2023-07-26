package com.spinoza.learningvknews.data.network

import com.spinoza.learningvknews.domain.TokenStorage

object TokenStorageImpl : TokenStorage {

    private var value: String? = null

    override fun setToken(token: String?) {
        value = token
    }

    override fun getToken() = value ?: throw IllegalStateException("Token is null")
}
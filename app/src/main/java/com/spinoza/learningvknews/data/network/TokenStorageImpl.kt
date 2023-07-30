package com.spinoza.learningvknews.data.network

object TokenStorageImpl : TokenStorage {

    private var value: String? = null

    override fun setToken(token: String?) {
        value = token
    }

    override fun getToken() = value ?: throw IllegalStateException("Token is null")
}
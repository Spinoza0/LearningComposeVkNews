package com.spinoza.learningvknews.data.network

interface TokenStorage {

    fun setToken(token: String?)

    fun getToken(): String
}
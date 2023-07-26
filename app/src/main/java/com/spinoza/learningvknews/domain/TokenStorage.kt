package com.spinoza.learningvknews.domain

interface TokenStorage {

    fun setToken(token: String)

    fun getToken(): String
}
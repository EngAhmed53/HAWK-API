package com.shouman.hawkAPI.model

data class ServerResponse<T>(val status: Boolean, val responseCode: ResponseCode, val body: T?)
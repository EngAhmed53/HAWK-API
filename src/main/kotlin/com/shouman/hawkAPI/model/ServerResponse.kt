package com.shouman.hawkAPI.model

data class ServerResponse<T>(val responseCode: ResponseCode, val body: T?)
package com.jayesh.commons.request.params

interface CommonRequestParams {
    fun getBearerToken(): String
    fun getAcceptType(): String
    fun setBearerToken(token: String)
    fun setAcceptType(type: String)

    fun getHeaderMap(): HashMap<String, Any>

    fun init(token: String = "", acceptType: String = "")
}
package com.jayesh.commons.request.params

import javax.inject.Inject

class CommonRequestParamsImpl @Inject constructor() : CommonRequestParams {
    private var bearerToken: String = ""
    private var acceptType: String = "application/vnd.github+json"

    override fun getBearerToken(): String {
        return bearerToken
    }

    override fun getAcceptType(): String {
        return acceptType
    }

    override fun setBearerToken(token: String) {
        this.bearerToken = token
    }

    override fun setAcceptType(type: String) {
        this.acceptType = type
    }

    override fun getHeaderMap(): HashMap<String, Any> {
        return hashMapOf(
            "Accept" to acceptType,
            "Authorization" to "Bearer $bearerToken"
        )
    }

    override fun init(token: String, acceptType: String) {
        this.bearerToken = token
        this.acceptType = acceptType
    }
}
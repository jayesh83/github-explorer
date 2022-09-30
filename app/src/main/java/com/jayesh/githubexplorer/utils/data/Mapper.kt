package com.jayesh.githubexplorer.utils.data

interface Mapper<From, To> {
    fun mapFrom(from: From): To
}
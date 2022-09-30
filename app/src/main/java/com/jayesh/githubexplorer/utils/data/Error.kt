package com.jayesh.githubexplorer.utils.data

class EmptyResultException(
    override val message: String = "No data found"
) : Exception()

fun returnNoDataException(message: String = "No data found"): Nothing {
    throw EmptyResultException(message = message)
}
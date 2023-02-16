package org.messaging.exception

data class ApiException(val msg: String, val code: Int) : Exception(msg)

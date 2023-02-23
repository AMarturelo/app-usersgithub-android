package com.amarturelo.usersgithub.commons

interface Constants {
    object NetworkStatus {
        const val NETWORK_STATUS_SUCCESS = 1
        const val NETWORK_STATUS_IN_PROCESS = 2
        const val NETWORK_STATUS_ERROR = 3
    }

    object API {
        const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        const val API_CONNECTION_TIMEOUT = 5L
    }
}
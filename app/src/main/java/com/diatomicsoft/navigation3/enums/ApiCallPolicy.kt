package com.diatomicsoft.navigation3.enums

enum class ApiCallPolicy {
    CANCEL_PREVIOUS, // Fresh call
    JOIN_PREVIOUS    // Deduplicated call
}
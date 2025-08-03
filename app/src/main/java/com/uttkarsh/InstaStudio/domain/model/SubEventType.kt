package com.uttkarsh.InstaStudio.domain.model

enum class SubEventType(val displayName: String) {
    PRE_WEDDING("Pre-Wedding"),
    ENGAGEMENT("Engagement"),
    HALDI("Haldi"),
    MEHENDI("Mehendi"),
    SANGEET("Sangeet"),
    RECEPTION("Reception"),
    VIDAI("Vidai");

    override fun toString(): String = displayName

    companion object {
        fun fromDisplayName(name: String): SubEventType {
            return SubEventType.entries.find { it.displayName.equals(name, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown display name: $name")
        }
    }
}
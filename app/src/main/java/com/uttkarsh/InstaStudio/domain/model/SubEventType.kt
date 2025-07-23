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
}
package com.uttkarsh.InstaStudio.domain.model

enum class EventType(val displayName: String) {
    WEDDING("Wedding"),
    HALDI("haldi"),
    BIRTHDAY("Birthday"),
    CORPORATE("Corporate"),
    OTHER("Other");

    override fun toString(): String = displayName
}
package com.uttkarsh.InstaStudio.domain.model

enum class EventType(val displayName: String) {
    WEDDING("Wedding"),
    HALDI("haldi"),
    MAHENDI("mahendi"),
    RING_CEREMONY("ring_ceremony"),
    VIDAI("vidai"),
    BIRTHDAY("Birthday"),
    CORPORATE("Corporate"),
    OTHER("Other");

    override fun toString(): String = displayName
}
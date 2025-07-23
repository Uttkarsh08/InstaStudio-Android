package com.uttkarsh.InstaStudio.domain.model

enum class EventType(val displayName: String) {

    //Wedding & Related
    WEDDING("Wedding"),
    ANNIVERSARY("Anniversary"),

    //Maternity & Baby
    MATERNITY("Maternity"),
    BABY_SHOWER("Baby Shower"),
    BABY_MILESTONE("Baby Milestone"),
    KIDS_BIRTHDAY("Kids Birthday"),

    //Family & Personal
    FAMILY_PORTRAIT("Family Portrait"),
    COUPLE_SHOOT("Couple Shoot"),
    INDIVIDUAL_PORTRAIT("Individual Portrait"),
    BIRTHDAY("Birthday"),

    //Corporate
    CORPORATE_EVENT("Corporate Event"),
    CONFERENCE("Conference"),

    //Cultural & Religious
    FESTIVAL("Festival"),
    RELIGIOUS_CEREMONY("Religious Ceremony"),
    CULTURAL_EVENT("Cultural Event"),

    //School & College
    GRADUATION("Graduation"),
    SCHOOL_FUNCTION("School Function"),
    COLLEGE_EVENT("College Event"),

    //Product & Commercial
    PRODUCT_SHOOT("Product Shoot"),
    FOOD_PHOTOGRAPHY("Food Photography"),
    FASHION_SHOOT("Fashion Shoot"),
    JEWELRY_SHOOT("Jewelry Shoot"),
    ECOMMERCE_SHOOT("E-commerce Shoot"),

    //Modeling & Portfolio
    MODELING_PORTFOLIO("Modeling Portfolio"),
    ACTING_PORTFOLIO("Acting Portfolio"),

    //Miscellaneous
    PET_PHOTOGRAPHY("Pet Photography"),
    REAL_ESTATE("Real Estate"),
    TRAVEL_EVENT("Travel Event"),
    YOUTUBE_CONTENT("YouTube Content"),

    //General fallback
    OTHER("Other");

    override fun toString(): String = displayName
}
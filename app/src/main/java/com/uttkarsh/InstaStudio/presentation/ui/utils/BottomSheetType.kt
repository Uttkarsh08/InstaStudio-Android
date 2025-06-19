package com.uttkarsh.InstaStudio.presentation.ui.utils

sealed class BottomSheetType {
    object None : BottomSheetType()
    object Edit : BottomSheetType()
    object Add : BottomSheetType()
}
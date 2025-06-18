package com.uttkarsh.InstaStudio.presentation.ui.ResourcePages

sealed class BottomSheetType {
    object None : BottomSheetType()
    object Edit : BottomSheetType()
    object Add : BottomSheetType()
}
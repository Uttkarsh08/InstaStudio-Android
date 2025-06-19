package com.uttkarsh.InstaStudio.presentation.ui.utils

import androidx.compose.runtime.saveable.Saver

val BottomSheetTypeSaver = Saver<BottomSheetType, String>(
    save = {
        when (it) {
            is BottomSheetType.Edit -> "Edit"
            is BottomSheetType.Add -> "Add"
            is BottomSheetType.None -> "None"
        }
    },
    restore = {
        when (it) {
            "Edit" -> BottomSheetType.Edit
            "Add" -> BottomSheetType.Add
            else -> BottomSheetType.None
        }
    }
)
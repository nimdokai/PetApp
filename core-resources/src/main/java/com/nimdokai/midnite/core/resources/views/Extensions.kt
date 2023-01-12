package com.nimdokai.midnite.core.resources.views

import android.content.Context
import android.widget.ImageView
import androidx.annotation.StringRes
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun ImageView.loadTeamImage(imageUrl: String) {
    load(imageUrl) {
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}

fun Context.showDefaultErrorDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes buttonText: Int,
    action: () -> Unit
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(buttonText) { _, _ -> action.invoke() }
        .show()
}

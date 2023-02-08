package com.nimdokai.pet.core.resources.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nimdokai.pet.core.resources.databinding.KittyProgressIndicatorBinding

class KittyProgressBar(context: Context, attributeSet: AttributeSet?) : ConstraintLayout(context, attributeSet) {

    init {
        val layoutInflater = LayoutInflater.from(context)
        KittyProgressIndicatorBinding.inflate(layoutInflater, this)
    }
}
package com.ntncode.restaurantclient.util.dialogs

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ntncode.restaurantclient.R

class LoadStyleDialogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val PADDING_DEFAULT = 16
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.popup_load_dialog_custom, this, true)
        setPadding(PADDING_DEFAULT.dp, PADDING_DEFAULT.dp, PADDING_DEFAULT.dp, PADDING_DEFAULT.dp)
    }

    fun set(
        message: String? = null,
    ) {
        message?.let {
            val body_text: TextView? = findViewById(R.id.body_text)
            body_text?.text = it
        }
    }
}

private val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
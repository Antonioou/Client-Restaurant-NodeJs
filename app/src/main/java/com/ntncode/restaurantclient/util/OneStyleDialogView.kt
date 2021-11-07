package com.ntncode.restaurantclient.util

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.ntncode.restaurantclient.R


internal typealias ButtonListener = () -> Unit

class OneStyleDialogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val PADDING_DEFAULT = 16
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.popup_signin, this, true)
        setPadding(PADDING_DEFAULT.dp, PADDING_DEFAULT.dp, PADDING_DEFAULT.dp, PADDING_DEFAULT.dp)
    }

    fun set(
        title: String? = null,
        message: String? = null,
        negativeButtonText: String? = null,
        negativeButtonListener: ButtonListener? = null,
        positiveButtonText: String? = null,
        positiveButtonListener: ButtonListener? = null
    ) {
        title?.let {
            val header_text: TextView? = findViewById(R.id.header_text)
            header_text?.text = it
        }
        message?.let {
            val body_text: TextView? = findViewById(R.id.body_text)
            body_text?.text = it
        }
        negativeButtonText?.let {
            val btn_negative: MaterialButton? = findViewById(R.id.btn_negative)

            btn_negative?.text = it
            btn_negative?.setOnClickListener { negativeButtonListener?.invoke() }
        }
        positiveButtonText?.let {
            val btn_positive: MaterialButton? = findViewById(R.id.btn_positive)

            btn_positive?.text = it
            btn_positive?.setOnClickListener { positiveButtonListener?.invoke() }
        }
    }
}

private val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
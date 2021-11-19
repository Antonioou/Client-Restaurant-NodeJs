package com.ntncode.restaurantclient.util.dialogs

import android.app.AlertDialog
import android.content.Context
import androidx.core.content.ContextCompat
import com.ntncode.restaurantclient.R

class SimpleStyleAlertDialog(context: Context, isCancelable: Boolean) {

    companion object {
        private const val PADDING_DEFAULT = 16
    }

    private val alertDialog: AlertDialog by lazy {
        AlertDialog.Builder(context)
            .setCancelable(isCancelable)
            .create().apply {
                val backgroundDrawable =
                    ContextCompat.getDrawable(context, R.drawable.popup_rounded_corner)
                window?.setBackgroundDrawable(backgroundDrawable)
            }
    }

    private val simpleStyleDialogView: SimpleStyleDialogView by lazy { SimpleStyleDialogView(context) }

    @JvmOverloads
    fun set(
        title: String? = null,
        message: String? = null,
    ): SimpleStyleAlertDialog {
        simpleStyleDialogView.set(
            title,
            message
        )
        alertDialog.setView(simpleStyleDialogView)
        return this
    }

    fun show() {
        alertDialog.show()
    }

    fun dismiss() {
        alertDialog.dismiss()
    }

    // Here will be the custom view as private inner class
}
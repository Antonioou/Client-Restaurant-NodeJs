package com.ntncode.restaurantclient.util.dialogs

import android.app.AlertDialog
import android.content.Context
import androidx.core.content.ContextCompat
import com.ntncode.restaurantclient.R

class LoadStyleAlertDialog(context: Context, isCancelable: Boolean) {

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

    private val loadStyleDialogView: LoadStyleDialogView by lazy { LoadStyleDialogView(context) }

    @JvmOverloads
    fun set(
        message: String? = null,
    ): LoadStyleAlertDialog {
        loadStyleDialogView.set(
            message
        )
        alertDialog.setView(loadStyleDialogView)
        return this
    }

    fun show() {
        alertDialog.show()
    }

    fun dismiss() {
        alertDialog.dismiss()
    }
}
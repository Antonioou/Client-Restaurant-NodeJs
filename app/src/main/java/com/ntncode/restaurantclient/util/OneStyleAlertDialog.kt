package com.ntncode.restaurantclient.util

import android.app.AlertDialog
import android.content.Context
import androidx.core.content.ContextCompat
import com.ntncode.restaurantclient.R

class OneStyleAlertDialog(context: Context) {


    //[SOURCE]
    //https://medium.com/@manuelmato/c%C3%B3mo-crear-un-alertdialog-custom-en-android-con-kotlin-y-el-patr%C3%B3n-delegation-353196eb2f58

    companion object {
        private const val PADDING_DEFAULT = 16
    }

    private val alertDialog: AlertDialog by lazy {
        AlertDialog.Builder(context)
            .setCancelable(true)
            .create().apply {
                val backgroundDrawable =
                    ContextCompat.getDrawable(context, R.drawable.popup_rounded_corner)
                window?.setBackgroundDrawable(backgroundDrawable)
            }
    }

    private val oneStyleDialogView: OneStyleDialogView by lazy { OneStyleDialogView(context) }

    @JvmOverloads
    fun set(
        title: String? = null,
        message: String? = null,
        negativeButtonText: String? = null,
        negativeButtonListener: ButtonListener? = null,
        positiveButtonText: String? = null,
        positiveButtonListener: ButtonListener? = null
    ): OneStyleAlertDialog {
        oneStyleDialogView.set(
            title,
            message,
            negativeButtonText,
            negativeButtonListener,
            positiveButtonText,
            positiveButtonListener
        )

        alertDialog.setView(oneStyleDialogView)

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
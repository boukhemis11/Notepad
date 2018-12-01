package com.example.boukh.notepad

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

class ConfirmeDeleteDialogFrangment : DialogFragment() {

    interface ConfirmDeleteDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    var listener: ConfirmDeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Etes-vous sur de supprimer le note ?")
                .setPositiveButton("Supprimer",
                    DialogInterface.OnClickListener { dialog, id -> listener?.onDialogPositiveClick() })
                .setNegativeButton("Annuler",
                    DialogInterface.OnClickListener { dialog, id -> listener?.onDialogNegativeClick() })

        return builder.create()
    }
}
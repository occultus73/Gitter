package io.github.occultus73.gitter.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import io.github.occultus73.gitter.R

import kotlinx.android.synthetic.main.custom_dialog.*

class CustomAlertDialog(context : Context, private val onOkButtonClick: OnOkButtonClick)
    : Dialog(context), View.OnClickListener{
     private var customDialog : AlertDialog? = null
     private lateinit var resultValue : String


    fun CustomAlertDialog(message: String) {
        resultValue = message
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
         customDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .show()
        val bodyText = dialogView.findViewById<TextView>(R.id.body_text_textview)
        val okButton = dialogView.findViewById<TextView>(R.id.ok_textview)
        bodyText.text = message
        okButton.setOnClickListener(this)
    }



    override fun onClick(v: View?) {
          when(v?.id)  {
              R.id.ok_textview -> {
                  customDialog?.dismiss()
                  if(resultValue.contains("no user record")) {
                      onOkButtonClick.onFailureClick()
                  } else {
                      onOkButtonClick.onSuccessClick()
                  }
              }
          }
    }

    interface OnOkButtonClick {
        fun onSuccessClick()
        fun onFailureClick()
    }


}
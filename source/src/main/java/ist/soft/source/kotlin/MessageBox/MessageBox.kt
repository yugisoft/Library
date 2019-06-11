package ist.soft.source.kotlin.MessageBox

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipDescription
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DialogTitle
import android.view.LayoutInflater
import android.widget.TextView
import ist.soft.source.R

public class MessageBox : IMessage {
    //region Title
    var mTitle: String = ""
    var Title: String
        get() = mTitle
        set(value) {
            mTitle = value
        }
    //endregion
    //region Description
    var mDescription: String = ""
    var Description: String
        get() = mDescription
        set(value) {
            mDescription = value
        }
    //endregion

    constructor(title: String = "", description: String = "") {
        Title = title;
        Description = description;
    }

    private var dialog : AlertDialog? = null
    var DialogScreen : AlertDialog?
      get() = dialog
    set(value)
    {
        dissmiss()
        dialog = value
    }

    fun show(cntx:Context)
    {

        dialog  = AlertDialog.Builder(cntx).create();
        dialog?.setView(LayoutInflater.from(cntx).inflate(R.layout.layout_message_box_loading,null,false));
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show();

}
    fun dissmiss()
    {
        dialog?.dismiss()
    }
}

package library.yugisoft.library;

import android.content.Context;

import library.yugisoft.module.yugi;

public class testDialog2 extends library.yugisoft.module.Base.BaseDialog {


    public testDialog2(int id) {
        super(id);
    }

    public testDialog2(Context context) {
        super(context, R.layout.dialog_result1);
        dialog.setOnDismissListener((p)-> yugi.activity.hideKeyboard());
        dialog.setOnCancelListener((p)-> yugi.activity.hideKeyboard());
    }

    @Override
    public void Initilialize()
    {

    }


    @Override
    public void dismiss() {
        super.dismiss();
        yugi.activity.hideKeyboard();
    }
}
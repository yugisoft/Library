package library.yugisoft.library;

import android.content.Context;

import library.yugisoft.module.yugi;

public class testDialog extends library.yugisoft.module.Base.BaseDialog {


    public testDialog(int id) {
        super(id);
    }

    public testDialog(Context context) {
        super(context, R.layout.detail_test);
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
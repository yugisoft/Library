package library.yugisoft.module.Base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import library.yugisoft.module.yugi;

public abstract class BaseDialog  extends RecyclerView.ViewHolder {
    public Context context;
    public  Dialog dialog;
    public  static Dialog mDialog;

    public BaseDialog(int id) {
        this(yugi.activity, id);
    }

    public BaseDialog(Context context, int id) {
        super(yugi.activity.getLayoutInflater().inflate(id, null));
        this.context = context;
        init();
    }

    void init()
    {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(itemView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Initilialize();
    }

    public abstract void Initilialize();

    public void show()
    {
        try
        {
            mDialog = dialog;
            dialog.show();
        }
        catch (Exception ex)
        {
            init();
            mDialog = dialog;
            dialog.show();
        }
    }
    public void dismiss()
    {
        dialog.dismiss();
        yugi.activity.hideKeyboard();
    }

    public static boolean isShowing()
    {
        return mDialog!=null && mDialog.isShowing();
    }

    public void hideKeyboard() {
        InputMethodManager in = (InputMethodManager) yugi.activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(dialog.getWindow().peekDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

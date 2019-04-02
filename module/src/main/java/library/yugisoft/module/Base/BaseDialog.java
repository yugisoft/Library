package library.yugisoft.module.Base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import library.yugisoft.module.yugi;

public abstract class BaseDialog  extends RecyclerView.ViewHolder {
    public Context context;
    public Dialog dialog;

    public BaseDialog(int id) {
        this(yugi.activity, id);
    }

    public BaseDialog(Context context, int id) {
        super(yugi.activity.getLayoutInflater().inflate(id, null));
        this.context = context;
        Initilialize();
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(itemView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public abstract void Initilialize();

    public void show()
    {
        dialog.show();
    }
    public void dismiss()
    {
        dialog.dismiss();
    }
}
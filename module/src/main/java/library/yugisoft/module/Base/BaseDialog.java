package library.yugisoft.module.Base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.Window;

import library.yugisoft.module.yugi;

public abstract class BaseDialog  extends RecyclerView.ViewHolder {
    public Context context;
    public static Dialog dialog;

    public BaseDialog(int id) {
        this(yugi.activity, id);
    }

    public BaseDialog(Context context, int id) {
        super(yugi.activity.getLayoutInflater().inflate(id, null));
        this.context = context;

        dialog = new Dialog(context)
        {
            @Override
            public boolean onTouchEvent(@NonNull MotionEvent event) {
                if (MotionEvent.ACTION_OUTSIDE == event.getAction())
                {
                    dialog = null;
                }
                return super.onTouchEvent(event);
            }
        };
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(itemView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Initilialize();

        dialog.setOnCancelListener(d-> dialog = null);
        dialog.setOnDismissListener(d-> dialog = null);



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

    public static boolean isShowing()
    {
        return dialog!=null && dialog.isShowing();
    }
}

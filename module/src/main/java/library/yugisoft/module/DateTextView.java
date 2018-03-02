package library.yugisoft.module;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;




/**
 * Created by Yusuf on 26.10.2017.
 */

public class DateTextView extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener
{
    INTERFACES.OnDateSelectedListener onDateSelectedListener;
    public void setOnDateSelectedListener(INTERFACES.OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }
    public DateTextView(Context context) {
        super(context);
        init();
    }

    public DateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);init();
    }

    public DateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);init();
    }

    void init()
    {
        this.setOnClickListener(this);
        if(this.getText().length()==0)
        {
            this.setText(yugi.Tarih.getDate());
            if(onDateSelectedListener!=null)onDateSelectedListener.onDataSelectedListener(yugi.Tarih.getDate());
        }
    }

    @Override
    public void onClick(View v)
    {
        Activity a=null;
        try {
            a = ((Activity)getContext());
        }
        catch (Exception e)
        {
            a= yugi.activity;
        }
        yugi.Tarih.Date(new yugi.OnDateSelectedListener() {
            @Override
            public void onDataSelectedListener(String Date) {
                DateTextView.this.setText(Date);
                if(onDateSelectedListener!=null)onDateSelectedListener.onDataSelectedListener(Date);
            }
        });

    }
}

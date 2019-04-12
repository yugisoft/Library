package library.yugisoft.module;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import library.yugisoft.module.popwindow.DatePickerPopWin;


/**
 * Created by Yusuf on 26.10.2017.
 */

public class DateTextView extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener, DatePickerPopWin.OnDatePickedListener {
    DateTime dateTime = null;
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
        if(this.getText().length()==0 || dateTime == null)
        {
            dateTime = DateTime.Now();
            dateTime.setOnDatePickedListener(this);
            this.setText(dateTime.toShortDateString());
            if(onDateSelectedListener!=null)onDateSelectedListener.onDataSelectedListener(dateTime.toShortDateString());
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
        dateTime.showDatePopup(yugi.activity);
    }

    @Override
    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
        this.setText(dateTime.toShortDateString());
        if(onDateSelectedListener!=null)onDateSelectedListener.onDataSelectedListener(dateTime.toShortDateString());
    }

    public DateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(DateTime dateTime)
    {
        this.dateTime=dateTime;
        this.setText(dateTime.toShortDateString());
        dateTime.setOnDatePickedListener(this);
        if(onDateSelectedListener!=null)onDateSelectedListener.onDataSelectedListener(dateTime.toShortDateString());
    }

}

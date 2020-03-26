package library.yugisoft.module;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;



import library.yugisoft.module.popwindow.DatePickerPopWin;
import library.yugisoft.module.popwindow.TimePickerPopWin;

public class TimeTextView extends AppCompatTextView implements View.OnClickListener, TimePickerPopWin.OnTimePickListener
{
    DateTime dateTime = null;
    private TimePickerPopWin.OnTimePickListener onTimePickListener;
    public TimeTextView(Context context) {
        super(context);
        init();
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);init();
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);init();
    }

    void init()
    {
        this.setOnClickListener(this);
        if(this.getText().length()==0 || dateTime == null)
        {
            dateTime = DateTime.Now();
            this.setText(dateTime.toShortTimeString());
        }
        dateTime.setOnTimePickListener(this::onTimePickCompleted);
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
        this.setText(dateTime.toShortTimeString());
    }



    @Override
    public void onClick(View view) {
        Activity a=null;
        try { a = ((Activity)getContext()); } catch (Exception e) { a= yugi.activity; }
        dateTime.showTimePopup(a);
    }




    @Override
    public void onTimePickCompleted(int hour, int minute, String time) {
        this.setText(dateTime.toShortTimeString());
        if (onTimePickListener!= null)
            onTimePickListener.onTimePickCompleted(hour, minute, time);
    }

    public void setOnTimePickListener(TimePickerPopWin.OnTimePickListener onTimePickListener) {
        this.onTimePickListener = onTimePickListener;
    }

    public TimePickerPopWin.OnTimePickListener getOnTimePickListener() {
        return onTimePickListener;
    }
}

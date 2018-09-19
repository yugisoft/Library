package library.yugisoft.module;

import android.app.Activity;
import android.graphics.Color;

//import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import library.yugisoft.module.popwindow.DatePickerPopWin;
import library.yugisoft.module.popwindow.TimePickerPopWin;

public class DateTime extends Date {

    private static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

    private static String
            _popupFormat="yyyy[]MM[]dd",
            _dateFormat="dd[]MM[]yyyy",
            _shortDateTimeFormat="dd[]MM[]yyyy HH:mm",
            _longDateTimeFormat="dd[]MM[]yyyy HH:mm:ss",
            _timeFormat ="HH:mm",
            _longTimeFormat ="HH:mm:ss";


    public static String getPopupFormat() {
        return _popupFormat.replace("[]",getDateSeparator());
    }

    public static String getDateFormat() {
        return _dateFormat.replace("[]",getDateSeparator());
    }

    public static String getShortDateTimeFormat() {
        return _shortDateTimeFormat.replace("[]",getDateSeparator());
    }

    public static String getLongDateTimeFormat() {
        return _longDateTimeFormat.replace("[]",getDateSeparator());
    }

    TimeZone tz ;
    public static String dateSeparator=".";
    public static String getDateSeparator() {
        return dateSeparator;
    }

    public static void setDateSeparator(String dateSeparator) {
        DateTime.dateSeparator = dateSeparator;
    }
    public DateTime()
    {
        tz = TimeZone.getTimeZone("UTC");
    }

    public static DateTime fromISO8601UTC(String dateStr) {
        DateTime dateTime = new DateTime();
        ParsePosition position = new ParsePosition(0);

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(ISO8601);
        df.setTimeZone(tz);

        try
        {
            dateTime.setDateTime(df.parse(dateStr));
        }
        catch (Exception e) {

        }

        return dateTime;
    }
    public static DateTime fromDateTime(String dateStr) {
        DateTime dateTime = new DateTime();

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(getShortDateTimeFormat());
        df.setTimeZone(tz);
        try
        {
            dateTime.setDateTime(df.parse(dateStr));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    @Override
    public String toString() {
                return getIso8601();
    }
    public String toString(String format) {

        return getFormat(format);
    }
    public String toLongDateTimeString() {

        SimpleDateFormat format=new SimpleDateFormat(getLongDateTimeFormat());
        return format.format(this);
    }
    public String toShortDateTimeString() {

        SimpleDateFormat format=new SimpleDateFormat(getShortDateTimeFormat());
        return format.format(this);
    }
    public String toShortDateString() {

        SimpleDateFormat format=new SimpleDateFormat(getDateFormat());
        return format.format(this);
    }
    public String toShortTimeString() {

        SimpleDateFormat format=new SimpleDateFormat(_timeFormat);
        return format.format(this);
    }



    public static DateTime Now()
    {
        Date date =Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
        DateTime dateTime = new DateTime();
        dateTime.setDateTime(date);
        return  dateTime;
    }


    //region FastFormat

    public String getIso8601()
    {
        return getFormat(ISO8601);
    }

    public String getFormat(String Format) {
        DateFormat dateFormat = new SimpleDateFormat(Format);
        dateFormat.setTimeZone(tz);
        String nowAsISO = dateFormat.format(this);
        return nowAsISO;
    }

    //endregion


    public void setDateFormat(String dateFormat) {
        this._dateFormat = dateFormat;
    }

    public void setShortDateTimeFormat(String shortDateTimeFormat) {
        this._shortDateTimeFormat = shortDateTimeFormat;
    }

    public void setLongDateTimeFormat(String longDateTimeFormat) {
        this._longDateTimeFormat = longDateTimeFormat;
    }

    public void setDateTime(Date date) {
        setTime(date.getDate());
        setTime(date.getTime());
    }

    private DatePickerPopWin.OnDatePickedListener onDatePickedListener;

    public void showDateTimePoup(Activity context)
    {
        showDatePopup(context,true);
    }

    public void showDatePopup(Activity activity)
    {
        showDatePopup(activity,false);
    }

    public void showDatePopup(Activity context,boolean time)
    {
        showDatePopup(context, this.getFormat(getPopupFormat()), new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {

                        int h = DateTime.this.getHours();
                        int m = DateTime.this.getMinutes();

                        long milliseconds = DatePickerPopWin.getLongFromyyyyMMdd(year+DateTime.getDateSeparator()+month+DateTime.getDateSeparator()+day);
                        Calendar calendar = Calendar.getInstance();
                        if (milliseconds != -1) {

                            calendar.setTimeInMillis(milliseconds);
                            DateTime.this.setDateTime(calendar.getTime());
                            DateTime.this.setHours(h);
                            DateTime.this.setMinutes(m);
                        }
                        if (!time) {
                            if (onDatePickedListener != null)
                                onDatePickedListener.onDatePickCompleted(year, month, day, DateTime.this.toShortDateString());
                        }
                        else
                        {
                            showTimePopup(context);
                        }
                    }
                };
            }
        });
    }
    public void showTimePopup(Activity context)
    {
        showTimePopup(context, getTimes(), new TimePickerPopWin.OnTimePickListener() {
            @Override
            public void onTimePickCompleted(int hour, int minute, String time) {
                DateTime.this.setHours(hour);
                DateTime.this.setMinutes(minute);
                if (onDatePickedListener!=null)
                    onDatePickedListener.onDatePickCompleted(DateTime.this.getYear(),DateTime.this.getMonth(),DateTime.this.getDay(),DateTime.this.toShortDateTimeString());
            }
        });
    }

    public DatePickerPopWin.OnDatePickedListener getOnDatePickedListener() {
        return onDatePickedListener;
    }

    public void setOnDatePickedListener(DatePickerPopWin.OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }

    public int[] getTimes()
    {
        String[] sTime = this.toLongDateTimeString().split(":");
        int[] time = { Integer.parseInt(sTime[0]),Integer.parseInt(sTime[1]),Integer.parseInt(sTime[2]) };
        return  time;
    }

    public static void showDatePopup(Activity context, DatePickerPopWin.OnDatePickedListener listener)
    {
        showDatePopup(context,DateTime.Now().getFormat("yyyy"+DateTime.getDateSeparator()+"MM"+DateTime.getDateSeparator()+"dd"),listener);
    }
    public static void showDatePopup(Activity context,String date, DatePickerPopWin.OnDatePickedListener listener)
    {
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(context,listener).textConfirm(context.getResources().getString(R.string.kaydet)) //text of confirm button
                .textCancel(context.getResources().getString(R.string.iptal)) //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .minYear(1990) //min year in loop
                .maxYear(2550) // max year in loop
                .dateChose(date)//"2013-11-11") // date chose when init popwindow
                .build();
        pickerPopWin.showPopWin(context);
    }
    public static void showTimePopup(Activity context, TimePickerPopWin.OnTimePickListener listener)
    {
        showTimePopup(context,DateTime.Now().getTimes(),listener);
    }
    public static void showTimePopup(Activity context,int[] t, TimePickerPopWin.OnTimePickListener listener)
    {
        TimePickerPopWin timePickerPopWin=new TimePickerPopWin.Builder(context, listener).textConfirm(context.getResources().getString(R.string.kaydet)) //text of confirm button
                .textCancel(context.getResources().getString(R.string.iptal)) //text of cancel button
                .btnTextSize(16)
                .viewTextSize(25)
                .setMinute(t[1])
                .setHour(t[0])
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .build();
        timePickerPopWin.showPopWin(context);
    }
}

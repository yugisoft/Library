package library.yugisoft.module;

import android.app.Activity;
import android.graphics.Color;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import library.yugisoft.module.popwindow.DatePickerPopWin;
import library.yugisoft.module.popwindow.TimePickerPopWin;

//import com.google.gson.internal.bind.util.ISO8601Utils;

public class DateTime extends Date {

    private static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

    private static String
            _popupFormat="yyyy[]MM[]dd",
            _dateFormat="dd[]MM[]yyyy",
            _longDateFormat="dd MMMM yyyy",
            _shortDateTimeFormat="dd[]MM[]yyyy HH:mm",
            _shortTimeFormat="HH:mm",
            _longTimeFormat ="HH:mm:ss",
            _longDateTimeFormat="dd MMMM yyyy HH:mm:ss"
    ;


    public static String getPopupFormat() {
        return _popupFormat.replace("[]",getDateSeparator());
    }
    public static String getDateFormat() {
        return _dateFormat.replace("[]",getDateSeparator());
    }
    public static String getLongDateFormat() {
        return _longDateFormat.replace("[]",getDateSeparator());
    }
    public static String getShortTimeFormat() {
        return _shortTimeFormat;
    }

    public static void setShortTimeFormat(String _shortTimeFormat) {
        DateTime._shortTimeFormat = _shortTimeFormat;
    }

    public static String getLongTimeFormat() {
        return _longTimeFormat;
    }

    public static void setSongTimeFormat(String _longTimeFormat) {
        DateTime._longTimeFormat = _longTimeFormat;
    }

    public static void setLongDateFormat(String _longDateFormat) {
        DateTime._longDateFormat = _longDateFormat;
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
        return fromDateTime(dateStr,ISO8601,"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }


    public static DateTime fromDateTime(String dateStr) {
        return fromDateTime(dateStr,getShortDateTimeFormat());
    }
    public static DateTime fromDateTime(String dateStr,String Format) {
        DateTime dateTime = new DateTime();

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(Format);
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
    public static DateTime fromDateTime(String dateStr,String Format,String Format2) {
        DateTime dateTime = new DateTime();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(Format);
        df.setTimeZone(tz);
        try
        {
            dateTime.setDateTime(df.parse(dateStr));
        }
        catch (Exception e) {
            try {
                df = new SimpleDateFormat(Format2);
                dateTime.setDateTime(df.parse(dateStr));
            } catch (ParseException e1) {

            }
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





    public String toShortDateString() {

        SimpleDateFormat format=new SimpleDateFormat(getDateFormat());
        return format.format(this);
    }
    public String toLongDateString() {

        SimpleDateFormat format=new SimpleDateFormat(getLongDateFormat());
        return format.format(this);
    }

    public String toShortDateTimeString() {

        SimpleDateFormat format=new SimpleDateFormat(getShortDateTimeFormat());
        return format.format(this);
    }
    public String toLongDateTimeString() {

        SimpleDateFormat format=new SimpleDateFormat(getLongDateTimeFormat());
        return format.format(this);
    }

    public String toLongTimeString() {

        SimpleDateFormat format=new SimpleDateFormat(getLongTimeFormat());
        return format.format(this);
    }
    public String toShortTimeString() {

        SimpleDateFormat format=new SimpleDateFormat(getShortTimeFormat());
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
        return getFormat(ISO8601).replace("+0000","Z");
    }

    public String getFormat(String Format) {
        tz = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat(Format, Locale.getDefault());
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
    private TimePickerPopWin.OnTimePickListener onTimePickListener;



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
        });
    }
    public void showTimePopup(Activity context)
    {
        showTimePopup(context, getTimes(), new TimePickerPopWin.OnTimePickListener() {
            @Override
            public void onTimePickCompleted(int hour, int minute, String time) {
                DateTime.this.setHours(hour);
                DateTime.this.setMinutes(minute);
                if (getOnTimePickListener()!=null)
                    getOnTimePickListener().onTimePickCompleted(hour, minute, time);
                else if(getOnDatePickedListener()!=null)
                    getOnDatePickedListener().onDatePickCompleted(DateTime.this.getYear(),DateTime.this.getMonth(),DateTime.this.getDay(),DateTime.this.toShortDateTimeString());
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
        String[] sTime = this.toLongTimeString().split(":");
        int[] time = { Integer.parseInt(sTime[0]),Integer.parseInt(sTime[1]),Integer.parseInt(sTime[2]) };
        return  time;
    }

    public static void showDatePopup(Activity context, DatePickerPopWin.OnDatePickedListener listener)
    {
        showDatePopup(context,DateTime.Now().getFormat("yyyy"+DateTime.getDateSeparator()+"MM"+DateTime.getDateSeparator()+"dd"),listener);
    }
    public static void showDatePopup(Activity context,String date, DatePickerPopWin.OnDatePickedListener listener)
    {
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(context, listener).textConfirm(context.getResources().getString(R.string.kaydet)) //text of confirm button
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

    public static DateTime FirstDayOfWeek()
    {
        DateTime time = DateTime.Now();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK,cal.getFirstDayOfWeek());
        time.setDateTime(cal.getTime());
        return time;
    }
    public static DateTime LastDayOfWeek()
    {
        DateTime time = DateTime.Now();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK,cal.getFirstDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        time.setDateTime(cal.getTime());
        return time;
    }
    public static DateTime FirstDayOfMounth()
    {
        DateTime time = DateTime.Now();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH,1);

        time.setDateTime(cal.getTime());
        return time;
    }
    public static DateTime LastDayOfMounth()
    {
        DateTime time = DateTime.Now();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        time.setDateTime(cal.getTime());




        return time;
    }

    public TimePickerPopWin.OnTimePickListener getOnTimePickListener() {
        return onTimePickListener;
    }

    public void setOnTimePickListener(TimePickerPopWin.OnTimePickListener onTimePickListener) {
        this.onTimePickListener = onTimePickListener;
    }
}

package library.yugisoft.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import library.yugisoft.module.DateTime;

public class sample_DateTime extends AppCompatActivity {

    //region DECLARE
    public TextView txt_long_date;
    public TextView txt_short_date;
    public TextView txt_long_time;
    public TextView txt_short_time;
    public TextView txt_long_datetime;
    public TextView txt_short_datetime,txt_iso8061;
    public EditText edit_iso8601;
    Button button;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_date_time);
        setTitle("YugiSoft - DateTime");

        txt_long_date = (TextView)findViewById(R.id.txt_long_date);
        txt_short_date = (TextView)findViewById(R.id.txt_short_date);
        txt_long_time = (TextView)findViewById(R.id.txt_long_time);
        txt_short_time = (TextView)findViewById(R.id.txt_short_time);
        txt_long_datetime = (TextView)findViewById(R.id.txt_long_datetime);
        txt_short_datetime = (TextView)findViewById(R.id.txt_short_datetime);
        txt_iso8061= (TextView)findViewById(R.id.txt_iso8061);
        edit_iso8601 = (EditText)findViewById(R.id.edit_iso8601);

        button = (Button)findViewById(R.id.button);

        displayDateTime( DateTime.Now());
        button.setOnClickListener(p-> displayDateTime(DateTime.fromISO8601UTC(edit_iso8601.getText().toString())));
    }

    private void displayDateTime(DateTime dateTime)
    {
        txt_iso8061.setText(dateTime.getIso8601());
        edit_iso8601.setText(dateTime.getIso8601());
        txt_long_date.setText(dateTime.toLongDateString());
        txt_short_date.setText(dateTime.toShortDateString());
        txt_long_time.setText(dateTime.toLongTimeString());
        txt_short_time.setText(dateTime.toShortTimeString());
        txt_long_datetime.setText(dateTime.toLongDateTimeString());
        txt_short_datetime.setText(dateTime.toShortDateTimeString());
    }
}

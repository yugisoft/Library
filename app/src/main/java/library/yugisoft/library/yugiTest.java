package library.yugisoft.library;

import android.os.Bundle;

import library.yugisoft.module.Generic;
import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test11);



      //  DateTime dateTime = DateTime.fromISO8601UTC("2018-09-19T18:44:12.687+03:00");
       // String str = dateTime.toLongDateTimeString();
    }


    public static class test
    {
public String name;
    }


    public static class test3<T> extends Generic<T>
    {

    }





}
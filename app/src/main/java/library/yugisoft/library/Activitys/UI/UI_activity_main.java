package library.yugisoft.library.Activitys.UI;
import android.content.*;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.util.AttributeSet;
import android.view.*;
import android.widget.*;

import library.yugisoft.library.R;
import library.yugisoft.module.*;

/* Created By Android Resurce Manager Of Yusuf AYDIN 19.10.2019 - 13:23*/

public abstract class  UI_activity_main extends yugi.vActivity
{

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
}

public abstract void Ready();

//region DECLARE

//endregion

public void init()
{

    Ready();
}




 



}
package library.yugisoft.library;
    import android.os.Build;
    import android.os.Bundle;
    import android.support.annotation.RequiresApi;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import java.io.IOException;
    import java.io.InputStream;
    import java.nio.file.Files;
    import java.nio.file.Paths;
    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;
    import library.yugisoft.library.Activitys.UI.UI_activity_main;
    import library.yugisoft.module.Json;
    import library.yugisoft.module.Utils.JsonConverter;
    import library.yugisoft.module.ViewPager;
    import library.yugisoft.module.vList;
    import library.yugisoft.module.yugi;

public class yugiTest extends UI_activity_main implements View.OnClickListener {




    //region jsondata
    String jsonData = "";
    //endregion



    @Override
    public void Ready()

    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try {
                InputStream is = yugi.activity.getAssets().open("JsonData.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonData = new String(buffer);
                if ((int)jsonData.charAt(0) == '\uFEFF')
                    jsonData = jsonData.replace(jsonData.substring(0,1),"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }

    @Override
    public void onClick(View v)
    {


    }







}

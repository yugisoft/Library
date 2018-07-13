package library.yugisoft.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Locale;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.LocaleManager;
import library.yugisoft.module.yugi;

public class yugiTest extends AppCompatActivity implements View.OnClickListener{

    DataTable dataTable = new DataTable("[{\"c1\":\"v1\",\"c2\":\"v1\"},{\"c1\":\"v2\",\"c2\":\"v2\"},{\"c1\":\"v3\",\"c2\":\"v3\"}]");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    @Override
    public void onClick(View view) {
        LocaleManager.setNewLocale(this, "en");
        Intent intent=new Intent();
        intent.setClass(this, this.getClass());
        this.startActivity(intent);
        this.finish();
    }
}

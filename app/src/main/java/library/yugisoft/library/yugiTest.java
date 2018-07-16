package library.yugisoft.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import library.yugisoft.library.Models.CartModel.Cart;
import library.yugisoft.library.Models.ConfigModel;
import library.yugisoft.module.DataTable;
import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.JSON;
import library.yugisoft.module.LocaleManager;
import library.yugisoft.module.http;
import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener{

    DataTable dataTable = new DataTable("[{\"c1\":\"v1\",\"c2\":\"v1\"},{\"c1\":\"v2\",\"c2\":\"v2\"},{\"c1\":\"v3\",\"c2\":\"v3\"}]");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public class  MDL
    {
        public String STR1,STR2;
        public MD md ;
        public List<MD> lmd;
    }

    public class MD
    {

        public String STR1,STR2;
    }

    @Override
    public void onClick(View view) {

    }
}

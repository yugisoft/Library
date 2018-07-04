package library.yugisoft.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.yugi;

public class yugiTest extends AppCompatActivity {

    DataTable dataTable = new DataTable("[{\"c1\":\"v1\",\"c2\":\"v1\"},{\"c1\":\"v2\",\"c2\":\"v2\"},{\"c1\":\"v3\",\"c2\":\"v3\"}]");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        yugi.MessageBox.ShowLoading(this);
    }








}

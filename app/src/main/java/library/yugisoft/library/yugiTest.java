package library.yugisoft.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import library.yugisoft.module.DataTable;

public class yugiTest extends AppCompatActivity {

    DataTable dataTable = new DataTable("[{\"c1\":\"v1\",\"c2\":\"v1\"},{\"c1\":\"v2\",\"c2\":\"v2\"},{\"c1\":\"v3\",\"c2\":\"v3\"}]");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<DataTable.DataRow> list2 = dataTable.Where("c1==v1|c1==v3");
        String s = list2.get(0).get(0);
    }








}

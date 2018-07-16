package library.yugisoft.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


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
       // ConfigModel.DEMO();
       // String json = "{\"Barcode\":\"1\",\"IsIncreasing\":\"true\",\"Quantity\":\"1\"}";
//
       // http.httpPOST post = new http.httpPOST(new http.OnHttpResponse() {
       //     @Override
       //     public void onResponse(http.Response response) {
       //         yugi.MessageBox.Close();
       //         if (response.isException)
       //             yugi.MessageBox.Show(yugi.activity,response.HataAciklama,true);
       //         else
       //         {
//
       //         }
//
       //     }
       // }, json);
       // post.execute("http://10.34.41.58/IstePosAPI/api/CartProduct");
       // yugi.MessageBox.ShowLoading();
    }

    @Override
    public void onClick(View view) {

    }
}

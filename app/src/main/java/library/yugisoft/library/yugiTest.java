package library.yugisoft.library;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import library.yugisoft.module.DataAdapter;
import library.yugisoft.module.DialogBox;
import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mdi);
        DialogBox.showOK("Yeni Tema Deneme!","",null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_jsonparse:
                startActivity(new Intent(this,sample_jsonparse.class));
                break;
            case R.id.btn_datetime:
                startActivity(new Intent(this,sample_DateTime.class));
                break;
        }
    }

}

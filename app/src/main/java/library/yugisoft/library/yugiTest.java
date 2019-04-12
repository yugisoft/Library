package library.yugisoft.library;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import library.yugisoft.module.BaseGridView.BaseDataGridView;
import library.yugisoft.module.BaseGridView.BaseGridAdapter;
import library.yugisoft.module.DataAdapter;
import library.yugisoft.module.DateTime;
import library.yugisoft.module.DialogBox;
import library.yugisoft.module.Interfaces.IFormatter;
import library.yugisoft.module.ItemLooper;
import library.yugisoft.module.ViewPager;
import library.yugisoft.module.parse;
import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {

    BaseGridAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);








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
                default:
                    adapter.notifyDataSetChanged();
                    break;
        }
    }

    public static class Test
    {
        public DateTime Tarih = DateTime.Now();
        public String ID = "₺";
        public Double Tutar = 10.99;

        @Override
        public String toString() {
            return ID;
        }
    }

}

package library.yugisoft.library;

import android.content.Context;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import library.yugisoft.module.ComboBox;
import library.yugisoft.module.Data.DataGridAdapter;
import library.yugisoft.module.Data.DataGridView;
import library.yugisoft.module.DataTable;
import library.yugisoft.module.DialogBox;
import library.yugisoft.module.Generic;
import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.SmartList;
import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test11);






    }


    @Override
    public void onClick(View v) {
        DataTable dataTable = new DataTable("[{\"SkuID\":1,\"ProductID\":1,\"Barcode\":\"1001\",\"ColorName\":\"Açık mavi\",\"SizeNo\":\"XS\"},{\"SkuID\":2,\"ProductID\":1,\"Barcode\":\"1002\",\"ColorName\":\"Açık Mor\",\"SizeNo\":\"S\"},{\"SkuID\":3,\"ProductID\":1,\"Barcode\":\"1003\",\"ColorName\":\"Açık Sarı\",\"SizeNo\":\"M\"},{\"SkuID\":1803,\"ProductID\":1,\"Barcode\":\"2800\",\"ColorName\":\"Gök mavisi\",\"SizeNo\":\"44\"},{\"SkuID\":1813,\"ProductID\":1,\"Barcode\":\"2805\",\"ColorName\":\"Bordo\",\"SizeNo\":\"42\"},{\"SkuID\":1814,\"ProductID\":1,\"Barcode\":\"2806\",\"ColorName\":\"Gök mavisi\",\"SizeNo\":\"45\"},{\"SkuID\":1815,\"ProductID\":1,\"Barcode\":\"2807\",\"ColorName\":\"Fuşya\",\"SizeNo\":\"42\"},{\"SkuID\":1816,\"ProductID\":1,\"Barcode\":\"2808\",\"ColorName\":\"Ekru\",\"SizeNo\":\"3XL\"},{\"SkuID\":13814,\"ProductID\":1,\"Barcode\":\"2820\",\"ColorName\":\"Siyah\",\"SizeNo\":\"S\"}]");
        ((DataGridView)findViewById(R.id.dataGrid)).setData(dataTable);
    }
}
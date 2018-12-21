package library.yugisoft.library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import library.yugisoft.library.Models.Database;
import library.yugisoft.library.Models.SayimModel;
import library.yugisoft.module.ComboBox;
import library.yugisoft.module.Data.DataGridAdapter;
import library.yugisoft.module.Data.DataGridTextView;
import library.yugisoft.module.Data.DataGridView;
import library.yugisoft.module.DataTable;
import library.yugisoft.module.DateTime;
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

        Database.Build(this);




    }


    @Override
    public void onClick(View v) {
        DataTable dataTable = new DataTable("[{\"_DateTime\":\"2010-10-27T11:58:22.973Z\",\"_double\":10.57,\"_int\":10,\"_long\":100000000000,\"_String\":\"asdasd\"},{\"_DateTime\":\"2010-10-27T11:58:22.973Z\",\"_double\":10.57,\"_int\":10,\"_long\":100000000000,\"_String\":\"asdasd\"},{\"_DateTime\":\"2010-10-27T11:58:22.973Z\",\"_double\":10.57,\"_int\":10,\"_long\":100000000000,\"_String\":\"asdasd\"}]");
        ((DataGridView)findViewById(R.id.dataGrid)).setData(dataTable);
    }


}

package library.yugisoft.library;



import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
import android.widget.Checkable;
import android.widget.ListView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

import library.yugisoft.library.EndOfDay.EndOfDay;
import library.yugisoft.module.BaseGridView.BaseDataGridView;
        import library.yugisoft.module.BaseGridView.BaseGridAdapter;
        import library.yugisoft.module.DataAdapter;
        import library.yugisoft.module.DateTime;
        import library.yugisoft.module.DialogBox;
import library.yugisoft.module.INTERFACES;
import library.yugisoft.module.Interfaces.IFormatter;
        import library.yugisoft.module.ItemLooper;
import library.yugisoft.module.LoopTextView;
import library.yugisoft.module.Utils.CustomBinding.BindProperty;
import library.yugisoft.module.Utils.CustomBinding.BindingGridView;
import library.yugisoft.module.Utils.CustomBinding.BindingReverseable;
import library.yugisoft.module.Utils.CustomBinding.CustomBindingAdapter;
import library.yugisoft.module.Utils.CustomBinding.IBindingItemLooper;
import library.yugisoft.module.Utils.CustomBinding.OnGridItemSelect;
import library.yugisoft.module.ViewPager;
        import library.yugisoft.module.parse;
        import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {


    View binding_bar;
    LoopTextView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<SelectableClass> list = new ArrayList<>();
        list.add(new SelectableClass(1,"v1"));
        list.add(new SelectableClass(2,"v2"));
        list.add(new SelectableClass(3,"v3"));
        gridView = findViewById(R.id.gridView);
        gridView.getItemLooper().setList(list);
        binding_bar = findViewById(R.id.binding_bar);
        Testcalas testcalas = new Testcalas();
        new CustomBindingAdapter(binding_bar,testcalas).bind();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            switch (parse.toInt(((TextView) v).getText()) - 1) {
                case 0:

                    break;
                case 1:

                    break;
            }
        }
    }

    public static class SelectableClass implements Checkable , IBindingItemLooper {
        public SelectableClass() { }
        public SelectableClass(int id ,String name) {
            this.id = id;
            this.name = name;
        }


        private int id;
        @BindProperty(DisplayIdName = "vListText")
        private String name;
        private boolean select;

        @Override
        public void setChecked(boolean checked) {
            select = checked;
        }
        @Override
        public boolean isChecked() {
            return select;
        }
        @Override
        public void toggle() {
            setChecked(!isChecked());
        }


        private String Test = "Test";

        @Override
        public String IdFieldName() {
            return "id";
        }

        @Override
        public boolean Compare(Object value) {
            return parse.toInt(value)== id || String.valueOf(value).equals(name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Testcalas {
        @BindProperty(DisplayIdName = "gridView")
        private int gridView = 2;
    }
}

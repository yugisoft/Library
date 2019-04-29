package library.yugisoft.library;



import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
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
        import library.yugisoft.module.Interfaces.IFormatter;
        import library.yugisoft.module.ItemLooper;
import library.yugisoft.module.Utils.CustomBinding.BindingReverseable;
import library.yugisoft.module.Utils.CustomBinding.CustomBindingAdapter;
import library.yugisoft.module.ViewPager;
        import library.yugisoft.module.parse;
        import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {


    View binding_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testClass = new TestClass();
        binding_bar = findViewById(R.id.binding_bar);


    }
    TestClass testClass;
    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            switch (parse.toInt(((TextView) v).getText()) - 1) {
                case 0:
                    new CustomBindingAdapter(binding_bar,testClass).bind();
                    break;
                case 1:
                    new CustomBindingAdapter(binding_bar,testClass).reverse();
                    break;
            }
        }
    }
}
class TestClass
{
    @BindingReverseable
    public String PersonName = "Yusuf AYDIN";
    @BindingReverseable
    public double Amount = 999.45;
}

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
import library.yugisoft.module.Utils.CustomBinding.BindingGridView;
import library.yugisoft.module.Utils.CustomBinding.BindingReverseable;
import library.yugisoft.module.Utils.CustomBinding.CustomBindingAdapter;
import library.yugisoft.module.Utils.CustomBinding.OnGridItemSelect;
import library.yugisoft.module.ViewPager;
        import library.yugisoft.module.parse;
        import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {


    View binding_bar;
    BindingGridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = ((BindingGridView)findViewById(R.id.gridView));
        List<SelectableClass> list = new ArrayList<>();
        list.add(new SelectableClass());
        list.add(new SelectableClass());
        list.add(new SelectableClass());

        gridView.setData(list);


        gridView.setOnGridItemSelect(new OnGridItemSelect<SelectableClass>() {
            @Override
            public void OnSelect(INTERFACES.OnResponse<Boolean> isSelect, int positon, SelectableClass item, View view)
            {
                if (!item.isChecked())
                    DialogBox.showYESNO("Seçilsin mi ?", new DialogBox.IDialogResult() {
                    @Override
                    public void onResult(DialogBox.EDialogButtons buttons, String result)
                    {
                        isSelect.onResponse(buttons.equals(DialogBox.EDialogButtons.YES));
                    }
                });
                else
                    DialogBox.showYESNO("Seçim Kaldırılsın mı ?", new DialogBox.IDialogResult() {
                        @Override
                        public void onResult(DialogBox.EDialogButtons buttons, String result)
                        {
                            isSelect.onResponse(!buttons.equals(DialogBox.EDialogButtons.YES));
                        }
                    });
            }
        });


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

    public static class SelectableClass implements Checkable
    {

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
    }
}

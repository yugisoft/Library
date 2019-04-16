package library.yugisoft.library;



import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ListView;
        import android.widget.TextView;

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


            testDialog t1;
            testDialog2 t2;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                t1 = new testDialog(this);
                t2 = new testDialog2(this);
            }

            @Override
            public void onClick(View v) {
                if (v instanceof TextView)
                {
                    switch (parse.toInt(((TextView)v).getText()) -1 )
                    {
                        case 0:
                            t1.show();
                            break;
                        case 1:
                            t2.show();
                            break;
                    }
                }
            }
        }

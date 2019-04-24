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

                EndOfDay endOfDay = parse.jsonTo("[{\"Title\":\"Toplam Bilgileri\",\"Title1\":\"Hareket \",\"Title2\":\"Belge Sayısı\",\"Title3\":\"Tutarı\",\"Title4\":\"\",\"Title5\":\"\",\"Values\":[{\"Title1\":\"Perakende Satış Faturası (251)\",\"Title2\":\"3\",\"Title3\":\"-638.33\",\"Title4\":\"\",\"Title5\":\"\"}]},{\"Title\":\"Ödeme Bilgileri\",\"Title1\":\"Ödeme Methodu\",\"Title2\":\"Ödeme Tipi\",\"Title3\":\"PB\",\"Title4\":\"Tutar\",\"Title5\":\"Yerel Tutar\",\"Values\":[{\"Title1\":\"Nakit\",\"Title2\":\"Nakit\",\"Title3\":\"TL\",\"Title4\":\"350.00\",\"Title5\":\"350.00\"},{\"Title1\":\"Kredi Kartı\",\"Title2\":\"Yapı Kredi Tek\",\"Title3\":\"TL\",\"Title4\":\"288.33\",\"Title5\":\"288.33\"},{\"Title1\":\"Diğer\",\"Title2\":\"Puan\",\"Title3\":\"TL\",\"Title4\":\"0.00\",\"Title5\":\"0.00\"}]}]",EndOfDay.class);

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

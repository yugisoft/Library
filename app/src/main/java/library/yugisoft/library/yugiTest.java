package library.yugisoft.library;
    import android.graphics.Color;
    import android.graphics.PorterDuff;
    import android.graphics.drawable.Drawable;

    import android.os.Handler;
    import android.support.v7.widget.Toolbar;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.support.v7.widget.SearchView;
    import android.widget.TextView;


    import library.yugisoft.library.Activitys.UI.FirmList;
    import library.yugisoft.library.Activitys.UI.UI_activity_main;
    import library.yugisoft.module.DateTime;
    import library.yugisoft.module.Json;
    import library.yugisoft.module.Utils.JsonConverter;
    import library.yugisoft.module.Utils.pGoldGridView.GoldGridView;
    import library.yugisoft.module.ViewPager;
    import library.yugisoft.module.vList;
    import library.yugisoft.module.yugi;

public class yugiTest extends UI_activity_main implements View.OnClickListener {

    EditText txt_format,txt_date;
    TextView txt_sonuc;

    @Override
    public void Ready()
    {
        txt_format = findViewById(R.id.txt_format);
        txt_date = findViewById(R.id.txt_date);
        txt_sonuc = findViewById(R.id.txt_sonuc);
    }


    @Override
    public void onClick(View v)
    {

        String sonuc = "";

        DateTime dt = DateTime.fromDateTime(txt_date.getText().toString(),txt_format.getText().toString());

        sonuc += dt.toShortDateTimeString();
        sonuc += "\n";
        sonuc += dt.toLongDateTimeString();
        txt_sonuc.setText(sonuc);

    }

}

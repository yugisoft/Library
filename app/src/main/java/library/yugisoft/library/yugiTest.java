package library.yugisoft.library;
    import android.os.Bundle;
    import android.view.View;
    import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ist.soft.source.kotlin.MessageBox.MessageBox("","").show(this);


    }

    @Override
    public void onClick(View v) {

    }


}

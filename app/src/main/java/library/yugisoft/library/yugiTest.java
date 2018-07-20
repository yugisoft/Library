package library.yugisoft.library;

import android.os.Bundle;
import android.view.View;

import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



     //  Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
     //  ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
     //  spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
     //  spinner2.setAdapter(spinnerArrayAdapter2);




    }

    @Override
    public void onClick(View view) {

    }
}

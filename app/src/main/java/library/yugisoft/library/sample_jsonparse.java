package library.yugisoft.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import library.yugisoft.module.DialogBox;
import library.yugisoft.module.parse;
import library.yugisoft.module.yugi;

public class sample_jsonparse extends AppCompatActivity {




    //region DECLARE

    public Button sampleJsonButton;
    public EditText editJson;
    public TextView mPublicIntField;
    public TextView mPublicTextField;
    public TextView mPrivateIntField;
    public TextView mPrivateTextField;
    public Button parseButton;


    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_jsonparse);

        setTitle("YugiSoft - Json to Model");

        sampleJsonButton = (Button)findViewById(R.id.sampleJsonButton);
        editJson = (EditText)findViewById(R.id.editJson);
        mPublicIntField = (TextView)findViewById(R.id.mPublicIntField);
        mPublicTextField = (TextView)findViewById(R.id.mPublicTextField);
        mPrivateIntField = (TextView)findViewById(R.id.mPrivateIntField);
        mPrivateTextField = (TextView)findViewById(R.id.mPrivateTextField);
        parseButton = (Button)findViewById(R.id.parseButton);

        sampleJsonButton.setOnClickListener(p-> SampleJson());
        parseButton.setOnClickListener(p-> ParseJson());
    }


    private void ParseJson() {
        TestModel testModel = parse.jsonTo(editJson.getText().toString(),TestModel.class);
        if (testModel!=null)
        {
            mPublicIntField.setText("mPublicIntField : "+testModel.mPublicIntField);
            mPublicTextField.setText("mPublicIntField : "+testModel.mPublicTextField);
            mPrivateIntField.setText("mPublicIntField : "+testModel.getmPrivateIntField());
            mPrivateTextField.setText("mPublicIntField : "+testModel.getmPrivateTextField());
        }
        else
        {
            DialogBox.showOK("Json Exception!",null);
            mPublicIntField.setText("mPublicIntField : ");
            mPublicTextField.setText("mPublicIntField : ");
            mPrivateIntField.setText("mPublicIntField : ");
            mPrivateTextField.setText("mPublicIntField : ");
        }

    }
    private void SampleJson() {
        editJson.setText("{\n" +
                "  \"mPublicIntField\": 123,\n" +
                "  \"mPublicTextField\": \"abc\",\n" +
                "  \"mPrivateIntField\": 321,\n" +
                "  \"mPrivateTextField\": \"cba\"\n" +
                "}");
    }
    public static class TestModel {
        public int mPublicIntField;
        public String mPublicTextField;

        private int mPrivateIntField;
        private String mPrivateTextField;

        public int getmPrivateIntField() {
            return mPrivateIntField;
        }

        public String getmPrivateTextField() {
            return mPrivateTextField;
        }
    }


}

package library.yugisoft.library;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.Color;
    import android.graphics.RectF;
    import android.os.Bundle;
    import android.support.v7.widget.RecyclerView;
    import android.support.v7.widget.helper.ItemTouchHelper;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.GridView;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;

    import library.yugisoft.module.DialogBox;
    import library.yugisoft.module.ItemAdapter;

    import library.yugisoft.module.Json;
    import library.yugisoft.module.Utils.CustomBinding.BindProperty;
    import library.yugisoft.module.Utils.CustomBinding.CustomBindingAdapter;
    import library.yugisoft.module.Utils.JsonConverter;
    import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonConverter<Deneme> converter = new JsonConverter<>("[{\"Field1\":\"Value1\",\"Field2\":2,\"Item\":{\"Field1\":\"Value1\",\"Field2\":2},\"ListItem\":[{\"Field1\":\"Value1\",\"Field2\":2},{\"Field1\":\"Value1\",\"Field2\":2}]},{\"Field1\":\"Value1\",\"Field2\":2,\"Item\":{\"Field1\":\"Value1\",\"Field2\":2},\"ListItem\":[{\"Field1\":\"Value1\",\"Field2\":2},{\"Field1\":\"Value1\",\"Field2\":2}]}]",Deneme.class);
        converter.setKey("Item");
        Deneme deneme = converter.convertToClass();

        DialogBox.showOK("",null);

    }

    @Override
    public void onClick(View v)
    {

    }



    public static class Deneme
    {

        //region Field1
        @Json(name = "Field1")
        private String field1;
        public String getField1(){
            return field1;
        }
        public void setField1( String field1 )
        {
            this.field1= field1;
        }
        //endregion
        //region Field2

        private int Field2;
        public int getField2(){
            return Field2;
        }
        public void setField2( int field2 )
        {
            this.Field2= field2;
        }
        //endregion

        public List<Deneme> ListItem;

    }


}

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

    import library.yugisoft.module.ItemAdapter;

    import library.yugisoft.module.Utils.CustomBinding.BindProperty;
    import library.yugisoft.module.Utils.CustomBinding.CustomBindingAdapter;
    import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomBindingAdapter bindingAdapter = new CustomBindingAdapter(this,MainView);
        bindingAdapter.bind(new Deneme());
    }

    @Override
    public void onClick(View v) {

    }



    public static class Deneme
    {

        @BindProperty(DisplayIdName = "Text1")
        private String deneme1 = "Deneme1";
        @BindProperty(DisplayIdName = "Text2",Format = "${deneme1} Alanı")
        private String deneme2 = "Deneme2";


        public String getDeneme1() {
            return deneme1;
        }

        public void setDeneme1(String deneme1) {
            this.deneme1 = deneme1;
        }

        public String getDeneme2() {
            return deneme2;
        }

        public void setDeneme2(String deneme2) {
            this.deneme2 = deneme2;
        }
    }


}

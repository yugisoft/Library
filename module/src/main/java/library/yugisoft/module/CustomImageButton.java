package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Yusuf on 19.02.2018.
 */


public class CustomImageButton extends myViews {
    AttributeSet _attrs;
    TextView tv;
    ImageView iv;
    LinearLayout view;
    float tsize=0;
    int ImageWeight_W=50;
    int ImageWeight_H=50;
    public CustomImageButton(Context context) {
        super(context);
        init(context,null,0);
    }

    public CustomImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
        _attrs=attrs;
    }

    public CustomImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
        _attrs=attrs;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs,defStyleAttr);
        _attrs=attrs;
    }


    private void init(Context context ,@Nullable AttributeSet attrs,int defStyle) {

        view =  (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_image_button, null);
        view.setOnClickListener(onClickListener);
        view.setId(this.getId());
        iv = (ImageView) view.findViewById(R.id.cib_resim);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.myViews, defStyle, 0);


        tv = (TextView)view.findViewById(R.id.cib_baslik);
        String str =a.getString(R.styleable.myViews_baslik);
        tv.setText(str);
        tsize=a.getDimension(R.styleable.myViews_fontsize,10);
        tsize = yugi.convertPixelToDp(tsize,getContext());
        tv.setTextSize(tsize);
        tv.setTextColor(a.getColor(R.styleable.myViews_forecolor, Color.BLACK));
        try {
            // tv.setGravity(super.getGravity());
        }catch (Exception e){}
        tv.setPadding(5,0,0,0);

        ImageWeight_W =(int)a.getDimension(R.styleable.myViews_image_w,50);
        ImageWeight_H =(int)a.getDimension(R.styleable.myViews_image_h,50);
       if(ImageWeight_W!=50) ImageWeight_W = (int)yugi.convertPixelToDp(ImageWeight_W,getContext());
       if(ImageWeight_H!=50) ImageWeight_H = (int)yugi.convertPixelToDp(ImageWeight_H,getContext());

        if (a.hasValue(R.styleable.myViews_image))
        {
            iv.setImageDrawable(a.getDrawable(R.styleable.myViews_image));
            iv.setColorFilter(a.getColor(R.styleable.myViews_TintColor,Color.WHITE),android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        if (a.hasValue(R.styleable.myViews_backgroundimage)) {

            this.setBackground(a.getDrawable(R.styleable.myViews_backgroundimage));
        }
        setWillNotDraw(false);

        this.addView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        float contentWidth = getWidth() - paddingLeft - paddingRight;
        float contentHeight = getHeight() - paddingTop - paddingBottom;
        // int new_w=(int)giz.convertPixelToDp(((contentWidth/100)*ImageWeight_W),getContext());
        // int new_h=(int)giz.convertPixelToDp(((contentHeight/100)*ImageWeight_H),getContext());
        float new_w=(((contentWidth/100)*ImageWeight_W));
        float f =(contentHeight/100);
        float new_h=(f*ImageWeight_H);
        iv.setLayoutParams(new LinearLayout.LayoutParams((int)new_w,(int)new_h));

    }

    View.OnClickListener onClickListener;
    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {

        if(view!=null)
        view.setOnClickListener(l);
        onClickListener=l;
        //super.setOnClickListener(l);
    }

}


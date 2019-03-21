package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

    Context context; AttributeSet attrs; int defStyleAttr;TypedArray typedArray;
    public CustomImageButton(Context context) {
        this(context,null,0);
    }
    public CustomImageButton(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }
    public CustomImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs=attrs;
        this.defStyleAttr=defStyleAttr;
        initialize();
    }



    private void initialize()
    {
        typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.myViews, defStyleAttr, 0);
        if (typedArray!=null)
        {
            setContentViewID(typedArray.getResourceId(R.styleable.myViews_contentView,R.layout.custom_image_button));
        }
        else
            setContentViewID(R.layout.custom_image_button);
    }

    //region DECLARE
    public TextView cib_baslik;
    public ImageView cib_resim;

    private View contentView;
    private int contentViewID = R.layout.custom_image_button;
    private float fontSize = 14;
    private String baslik;

    private int textColor
            ,image_h
            ,image_w
            ,tintColor;

    private Drawable image;




    //endregion

    public int getContentViewID() {
        return contentViewID;
    }
    public void setContentViewID(int contentViewID) {
        this.contentViewID = contentViewID;
        isIdSetting = true;
        setContentView(((ViewGroup)inflate(context,contentViewID,this)).getChildAt(0));
        isIdSetting =  false;
    }
    boolean isIdSetting = false;
    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
        this.contentView.setOnClickListener(onClickListener);
        this.contentView.setId(this.getId());
        this.contentView.setBackground(context.getResources().getDrawable(R.drawable.ripple1));
        if (this.getChildCount() > 0 && !isIdSetting)
        {
            if (!this.getChildAt(0).equals(contentView))
            {
                this.removeAllViews();
                this.addView(contentView);
            }
        }

        cib_baslik = (TextView)findViewById(R.id.cib_baslik);
        cib_resim = (ImageView)findViewById(R.id.cib_resim);

        if (typedArray!=null)
        {
            setBaslik(typedArray.getString(R.styleable.myViews_baslik));
            setFontSize(yugi.convertPixelToDp(typedArray.getDimension(R.styleable.myViews_fontsize,(int)yugi.convertDpToPixel(12,context)),getContext()));
            setTextColor(typedArray.getColor(R.styleable.myViews_forecolor,Color.BLACK));
            cib_baslik.setPadding(5,0,0,0);
            setImage_w(typedArray.getInteger(R.styleable.myViews_image_w,50));
            setImage_h(typedArray.getInteger(R.styleable.myViews_image_h,50));
            setImage(typedArray.getDrawable(R.styleable.myViews_image));
            setTintColor(typedArray.getColor(R.styleable.myViews_TintColor,-1));

        }
        setWillNotDraw(false);

    }

    public String getBaslik() {
        return baslik;
    }
    public void setBaslik(String baslik) {
        this.baslik = baslik;
        cib_baslik.setText(baslik);
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
        this.cib_baslik.setTextSize(fontSize);
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        cib_baslik.setTextColor(textColor);
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
        cib_resim.setImageDrawable(image);
    }

    public int getImage_h() {
        return image_h;
    }

    public void setImage_h(int image_h) {
        this.image_h = image_h;
    }

    public int getImage_w() {
        return image_w;
    }

    public void setImage_w(int image_w) {
        this.image_w = image_w;
    }


    public int getTintColor() {
        return tintColor;
    }

    public void setTintColor(int tintColor) {
        this.tintColor = tintColor;
        if (image!=null & tintColor != -1)
            image.setColorFilter(tintColor, PorterDuff.Mode.MULTIPLY);
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
        float new_w=(((contentWidth/100)*getImage_w()));
        float f =(contentHeight/100);
        float new_h=(f*getImage_h());
        cib_resim.setLayoutParams(new LinearLayout.LayoutParams((int)new_w,(int)new_h));

    }


    View.OnClickListener onClickListener;
    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {

        if(contentView!=null)
            contentView.setOnClickListener(l);
        onClickListener=l;
        //super.setOnClickListener(l);
    }
}


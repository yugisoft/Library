package library.yugisoft.module.Utils.pBottomIntegrator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import library.yugisoft.module.R;
import library.yugisoft.module.ViewPager;
import library.yugisoft.module.parse;
import library.yugisoft.module.yugi;

public class BottomIntegrator2 extends LinearLayout implements ViewPager.OnPageChangeListener , View.OnClickListener{

    public BottomIntegrator2(Context context) {
        this(context,null,0);
    }
    public BottomIntegrator2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public BottomIntegrator2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BottomIntegrator, defStyleAttr, 0);

        setUnfocusedSize(14);
        if (a!=null)
        {
            setFocusedSize(parse.DpToPixel(a.getDimension(R.styleable.BottomIntegrator_focusedSize, parse.PixelToDp(14))));
            setUnfocusedSize(parse.DpToPixel(a.getDimension(R.styleable.BottomIntegrator_unFocusedSize,parse.PixelToDp(12))));
        }
        setWillNotDraw(false);

    }

    private ViewPager viewPager;

    public ViewPager getViewPager() {
        return viewPager;
    }
    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        onPageChangeListener = this.viewPager.mOnPageChangeListener;
        this.viewPager.setOnPageChangeListener(this);
        if (viewPager.getChildCount()>0) {
            setIndex(viewPager.getCurrentItem());
        }
    }

    public void setCurrentItem(int item)
    {
        if (getViewPager()!=null)
            getViewPager().setCurrentItem(item);
        setIndex(item);
        if (onPageChangeListener!=null)
            onPageChangeListener.onPageSelected(item);
        this.invalidate();
    }
    public int getCurrentItem()
    {
        if (getViewPager()!=null)
            return getViewPager().getCurrentItem();
        return  -1;
    }

    private int focusedSize,unfocusedSize;

    TextView focusedTextView = null;


    private void setIndex(int index) {
        TextView textView = null;
        int count = this.getChildCount();
        BottomIntegrator2.this.invalidate();
        for (int i = 0; i < count; i++)
        {
            View v = this.getChildAt(i);
            if (v instanceof TextView)
                textView = (TextView)v;
            else if (v instanceof ViewGroup)
                textView = filterTextView((ViewGroup)v);
            if (textView!=null)
            {
                if (i == index) {
                    textView.setTextSize(focusedSize);
                    focusedTextView = textView;
                }
                else
                    textView.setTextSize(unfocusedSize);

                if (textView.getTag() == null)
                {
                    textView.setTag(i);
                    textView.setOnClickListener(this);
                }
            }
        }
        BottomIntegrator2.this.invalidate();
    }

    private TextView filterTextView(ViewGroup view)
    {
        TextView tv = null;
        View v = null;
        int count = view.getChildCount(),i=0;
        while (!(v instanceof TextView && count > 0 || i< count))
        {
            v = view.getChildAt(i);
            if (v instanceof TextView) {
                tv = (TextView) v;
                i++;
            }
            else if (v instanceof ViewGroup)
            {
                view = (ViewGroup)v;
                count = view.getChildCount();
                i = 0;
            }

        }
        return  tv;
    }

    public int getFocusedSize() {
        return focusedSize;
    }

    public void setFocusedSize(int focusedSize) {
        this.focusedSize = focusedSize;
    }

    public int getUnfocusedSize() {
        return unfocusedSize;
    }

    public void setUnfocusedSize(int unfocusedSize) {
        this.unfocusedSize = unfocusedSize;
    }


    private ViewPager.OnPageChangeListener onPageChangeListener;





    float x1=0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (focusedTextView != null)
            try
            {
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setColor(yugi.activity.getResources().getColor(R.color.BottomIntegrator));
                paint.setStrokeWidth(5);
                float y1 = this.getHeight();
                float y2 = this.getHeight();
                canvas.drawLine(x1, y1, x1+focusedTextView.getWidth(), y2, paint);
            } catch (Exception ex) {

            }
    }

    private float lastPositionAndOffsetSum = 0f,xpositionOffset;
    int isScroll = 0;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

        lastPositionAndOffsetSum = position + (positionOffset);
        isScroll = lastPositionAndOffsetSum == 1.0 ? 0 : 1;
        xpositionOffset = positionOffsetPixels - positionOffset;
        if (isScroll == 1)
        {
            float po = (position * this.getWidth() + xpositionOffset) / this.getWidth();

            x1 =  po * focusedTextView.getWidth();
            this.invalidate();
        }
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        try {
            int i = Integer.parseInt(v.getTag().toString());
            setCurrentItem(i);
        }
        catch (Exception ex)
        {}
    }
}

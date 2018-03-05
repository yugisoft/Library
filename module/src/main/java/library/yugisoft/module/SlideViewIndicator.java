package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Created by Yusuf on 05.03.2018.
 */

public class SlideViewIndicator extends LinearLayout implements SlideView.OnSlideViewListener {

    public static final int DEFAULT_INDICATOR_SPACING = 5;

    private int activePosition;

    private int indicatorSpacing;

    private ViewPager.OnPageChangeListener userDefinedPageChangeListener;

    public SlideViewIndicator(Context context) {
        this(context, null);
    }

    public SlideViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideViewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CirclePageIndicator, 0, 0);
        try {
            indicatorSpacing = a.getDimensionPixelSize(
                    R.styleable.CirclePageIndicator_indicator_spacing, DEFAULT_INDICATOR_SPACING);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        if (!(getLayoutParams() instanceof FrameLayout.LayoutParams)) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.BOTTOM | Gravity.START;
            setLayoutParams(params);
        }
    }

    public void setViewPager(SlideView pager)
    {
        removeAllViews();
        pager.setOnSlideViewListener(this);
        addIndicator(pager.getCount());
    }

    private void addIndicator(int count) {
        for (int i = 0;  i < count; i++) {
            ImageView img = new ImageView(getContext());
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = indicatorSpacing;
            params.rightMargin = indicatorSpacing;
            img.setImageResource(R.drawable.circle_indicator_stroke);
            addView(img, params);
        }

        if (count > 0) {
            ((ImageView) getChildAt(0)).setImageResource(R.drawable.circle_indicator_solid);
        }
    }

    private void updateIndicator(int position) {
        if (activePosition != position)
        {
            try {
                ((ImageView) getChildAt(activePosition)).setImageResource(R.drawable.circle_indicator_stroke);
            }
            catch (Exception e){}
            try {
                ((ImageView) getChildAt(position)).setImageResource(R.drawable.circle_indicator_solid);
            }
            catch (Exception e){}
            activePosition = position;
        }
    }

    private ViewPager.OnPageChangeListener getOnPageChangeListener(ViewPager pager) {
        try {
            Field f = pager.getClass().getDeclaredField("mOnPageChangeListener");
            f.setAccessible(true);
            return (ViewPager.OnPageChangeListener) f.get(pager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int dp2px(float dpValue) {
        return (int) (dpValue * getContext().getResources().getDisplayMetrics().density);
    }

    @Override
    public void onViewChaned(SlideView slideView, int currentViewIndex, View view) {
        updateIndicator(currentViewIndex);
    }
}


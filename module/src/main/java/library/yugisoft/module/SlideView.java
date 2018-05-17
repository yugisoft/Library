package library.yugisoft.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf on 05.03.2018.
 */

public class SlideView extends LinearLayout {

    private boolean RollSlide = false;
    //region CONST
    @SuppressLint("NewApi")
    public SlideView(Context context) {
        super(context);
        Init();
    }
    public SlideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Init();    }
    public SlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();}
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SlideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);Init();
    }
    //endregion
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void  Init()
    {
        setCurrentViewIndex(0);
    }
    public int animUp = R.anim.right_in;
    public int animDown = R.anim.left_out;
    View v = null;
    public void ScaleFrame(final View view) {

        for (int i = 0; i < this.getChildCount(); i++)
        {
            v=this.getChildAt(i);

            if (v.getVisibility() == View.VISIBLE && !RollSlide) {

                Animation scaleDown = AnimationUtils.loadAnimation(getContext().getApplicationContext(), animDown);
                final int finalI = i;
                scaleDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        SlideView.this.getChildAt(finalI).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                this.getChildAt(i).startAnimation(scaleDown);
            }
            else if (RollSlide)
            {

                Animation scaleDown = AnimationUtils.loadAnimation(getContext().getApplicationContext(), animDown);
                final int finalI = i;
                scaleDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        SlideView.this.getChildAt(finalI).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                this.getChildAt(i).startAnimation(scaleDown);
            }

        }

        Animation scaleDown = AnimationUtils.loadAnimation(getContext().getApplicationContext(), animUp);
        scaleDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(scaleDown);

    }
    //region GET&SET
    public int getCount()
    {
        return this.getChildCount();
    }
    //region CurrentViewIndex
    private int CurrentViewIndex=-1;
    public int getCurrentViewIndex() {
        return CurrentViewIndex;
    }

    public void setCurrentViewIndex(int currentViewIndex) {
        setCurrentViewIndex(currentViewIndex,scaleType);
    }

    public void setCurrentViewIndex(int currentViewIndex, ScaleType type) {

        int count =this.getChildCount();

        if(CurrentViewIndex==currentViewIndex)return;

        View v = this.getChildAt(currentViewIndex);

        if (v!=null) {
            if (currentViewIndex>CurrentViewIndex)
            {
                animUp = (type==ScaleType.Horizantal ? R.anim.right_in : R.anim.down_in);
                animDown = (type==ScaleType.Horizantal ? R.anim.left_out : R.anim.top_out);
            }
            else
            {
                animUp = (type==ScaleType.Horizantal ? R.anim.left_in : R.anim.top_in);
                animDown = (type == ScaleType.Horizantal ? R.anim.right_out : R.anim.down_out);
            }
            CurrentViewIndex = currentViewIndex;
            ScaleFrame(v);

            for (OnSlideViewListener l:onSlideViewListener) {
                l.onViewChaned(this,currentViewIndex,v);
            }
        }
    }
    //endregion
    //region onSlideViewListener
    private List<OnSlideViewListener> onSlideViewListener = new ArrayList<>();

    public void setOnSlideViewListener(OnSlideViewListener onSlideViewListener)
    {
        this.onSlideViewListener.remove(onSlideViewListener);
        this.onSlideViewListener .add(onSlideViewListener);
    }
    //endregion
    //region scaleType
    private ScaleType scaleType= ScaleType.Horizantal;

    public ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public boolean isRollSlide() {
        return RollSlide;
    }

    public void setRollSlide(boolean rollSlide) {
        RollSlide = rollSlide;
    }
    //endregion




    //endregion
    //region INTERFACE
    public  interface  OnSlideViewListener {
        public void onViewChaned(SlideView slideView, int currentViewIndex, View view);
    }
    //endregion
    //region ENUM
    public enum ScaleType {
        Horizantal,Vertical
    }
    //endregion
}

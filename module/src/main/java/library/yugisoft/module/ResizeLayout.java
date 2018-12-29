package library.yugisoft.module;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class ResizeLayout extends LinearLayout
{

    boolean show = true;
    boolean ischange = false;
    private boolean touchOnSlide = false;
    int state = 1;
    int w=-99,h=-99,nw=0,nh=0;
    float g=-99;


    public ResizeLayout(Context context) {
        this(context,null,0);
    }
    public ResizeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public ResizeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ResizeLayout, defStyleAttr, 0);
        if (a!=null)
        {
            state = (a.getInt(R.styleable.ResizeLayout__State,1));
            setTouchOnSlide(a.getBoolean(R.styleable.ResizeLayout__touchOnSlide,false));
        }
        LayoutTransition lt = new LayoutTransition();
        lt.disableTransitionType(LayoutTransition.CHANGING);
        this.setLayoutTransition(lt);

    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if (w==-99 && h== -99 && g == -99)
        {
            w= params.width;
            h=params.height;
            if (params instanceof LayoutParams)
                g = ((LayoutParams) params).weight;
        }
        super.setLayoutParams(params);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!ischange)
        {
            Object parent = this.getParent();
            while (parent!=null)
            {
                if (parent instanceof ViewGroup)
                {
                    ViewGroup group =((ViewGroup) parent);

                    if (group.getLayoutTransition()==null) {
                        LayoutTransition lt = new LayoutTransition();
                        lt.disableTransitionType(LayoutTransition.CHANGING);
                        group.setLayoutTransition(lt);
                    }
                    else
                        group.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

                    ischange = true;
                }
                if (parent instanceof View)
                    parent = ((View)parent).getParent();
                else
                    parent =null;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setState(state);
                }
            },100);
        }
    }
    public boolean isShow()
    {
        return show;//hh!=this.getMinimumHeight();
    }

    //region TOGGLE
    public void Toogle() {
        if (isShow())
            Close();
        else
            Open();
    }

    public void Open()
    {
        if (!isShow())
        {
            setParams(w,h,g,true);
            setimage(true);
            if (getOnResizeToogle()!=null)
                getOnResizeToogle().onToogle(this,true);
        }
        show = true;
    }

    private void setimage(boolean s)
    {
        if (getImageView() != null)
        {
            if (getOpenResouce()!=0 && getCloseResouce() !=0) { getImageView().setImageDrawable(getContext().getResources().getDrawable(!s ? getOpenResouce() : getCloseResouce())); }
            try {
                Animatable animatable = (Animatable) imageView.getDrawable();
                animatable.start();
            }
            catch (Exception ex){}
        }
    }

    public void Close()
    {
        if (isShow())
        {
            setParams(w,getMinimumHeight(),0,true);
            setimage(false);
            if (getOnResizeToogle()!=null)
                getOnResizeToogle().onToogle(this,false);
        }
        show = false;
    }

    private void open()
    {
        setParams(w,h,g,false);
        show = true;
    }

    private void close()
    {
        setParams(w,getMinimumHeight(),0,false);
        show = false;
    }

    void setParams(int _w,int _h, float _g,boolean set)
    {
        boolean ch = isShow();
        if(getLayoutParams() instanceof LayoutParams)
        {
            LayoutParams lp = (LayoutParams) this.getLayoutParams();
            lp.weight = _g;
            lp.width  = _w;
            lp.height = _h;
            this.setLayoutParams(lp);
        }
        else if(getLayoutParams() instanceof RelativeLayout.LayoutParams)
        {
            RelativeLayout.LayoutParams rp = (RelativeLayout.LayoutParams) this.getLayoutParams();
            rp.width = _w;
            rp.height = _h;
            this.setLayoutParams(rp);
        }
        if (set)
        {
            nw = this.getWidth();
            nh = this.getHeight();
        }

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        if (state==0)
            Close();
        else
            Open();
    }
    //endregion
    //region onTouchEvent
    private float x1,x2 , deltaX;;
    int hh = 0;
    static final int MIN_DISTANCE = 150;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isTouchOnSlide())
        {
            switch(event.getAction())
            {

                case MotionEvent.ACTION_DOWN:
                    x1 = event.getY();
                    hh= getHeight();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    x2 = event.getY();
                    setParams(w, (int) (hh + (x2-x1)),0,false);
                    return true;
                case MotionEvent.ACTION_UP:
                    x2 = event.getY();
                    deltaX = x2 - x1;
                    if (deltaX > 0)
                    {
                        //top to down
                        if (!isShow())
                        {
                            if (Math.abs(deltaX) > MIN_DISTANCE)
                            {
                                Open();
                            }
                            else
                            {
                                close();
                            }
                        }
                        else
                            open();
                    }
                    else
                    {
                        //down to up
                        if (isShow())
                        {
                            if (Math.abs(deltaX) > MIN_DISTANCE)
                            {
                                Close();
                            }
                            else
                            {
                                open();
                            }
                        }
                        else
                            close();

                    }
                    return false;
                default:
                    return super.onTouchEvent(event);
            }
        }
        else return super.onTouchEvent(event);
    }


    public boolean isTouchOnSlide() {
        return touchOnSlide;
    }

    public void setTouchOnSlide(boolean touchOnSlide) {
        this.touchOnSlide = touchOnSlide;
    }
    //endregion

    private int
            closeResouce ,
            openResouce ;

    private ImageView imageView;
    public ImageView getImageView() {
        return imageView;
    }
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
        imageView.setOnClickListener(p-> Toogle());
        if (openResouce!=0 && closeResouce !=0)
        {
            imageView.setImageDrawable(yugi.activity.getResources().getDrawable(isShow() ? closeResouce : openResouce));
        }

    }

    public int getOpenResouce() {
        return openResouce;
    }

    public void setOpenResouce(int openResouce) {
        this.openResouce = openResouce;
        setImageView(getImageView());
    }

    public int getCloseResouce() {
        return closeResouce;
    }

    public void setCloseResouce(int closeResouce) {
        this.closeResouce = closeResouce;
        setImageView(getImageView());

    }

    private OnResizeToogle onResizeToogle =null;

    public OnResizeToogle getOnResizeToogle() {
        return onResizeToogle;
    }

    public void setOnResizeToogle(OnResizeToogle onResizeToogle) {
        this.onResizeToogle = onResizeToogle;
    }

    public interface OnResizeToogle
    {
        void onToogle(ResizeLayout resizeLayout, boolean State);
    }
}

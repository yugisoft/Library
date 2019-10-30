package library.yugisoft.module.Utils.pSmartGridView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import library.yugisoft.module.Base.BaseCustomLayout;
import library.yugisoft.module.R;

/* Created By Android Resurce Manager Of Yusuf AYDIN 18.10.2019 - 11:58*/

public abstract class SmartGridViewHolder extends BaseCustomLayout
{
    public SmartGridViewHolder(Context context) { super(context); }
    public SmartGridViewHolder(Context context, @Nullable AttributeSet attrs) { super(context, attrs); }
    public SmartGridViewHolder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }
    
    @Override
    protected int getDefaultContentViewID() {
        return R.layout.view_smart_grid_view;
    }

    //region DECLARE
    
public LinearLayout bar_header;
public android.support.v4.widget.SwipeRefreshLayout swipe;
public ScrollView scrollViewv;
public HorizontalScrollView scrollViewh;
public LinearLayout contentPanel;
public LinearLayout bar_detail;
public LinearLayout bar_footer;
    //endregion

    @Override
    public void init(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super.init(context, attrs, defStyle);

        bar_header = (LinearLayout) findViewById(R.id.bar_header);
        swipe = (android.support.v4.widget.SwipeRefreshLayout) findViewById(R.id.swipe);
        scrollViewv = (ScrollView) findViewById(R.id.scrollViewv);
        scrollViewh = (HorizontalScrollView) findViewById(R.id.scrollViewh);
        contentPanel = (LinearLayout) findViewById(R.id.contentPanel);
        bar_detail = (LinearLayout) findViewById(R.id.bar_detail);
        bar_footer = (LinearLayout) findViewById(R.id.bar_footer);
    }

    
}
package library.yugisoft.module;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.List;

/**
 * Created by Yusuf on 08.06.2018.
 */

public class NestedListView extends ScrollView implements INTERFACES.OnAdapterDataLoad
{
    public NestedListView(Context context) {
        this(context, null, 0);
    }
    public NestedListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public NestedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    LinearLayout layout,subLayout;
    private int numColumns=1;
    public int getNumColumns() {
        return numColumns;
    }
    public void setNumColumns(int numColumns) {
        if (numColumns<=0)numColumns=1;
        this.numColumns = numColumns;
    }
    private void init() {
        layout = new LinearLayout(getContext());
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        addView(layout);
    }


    private ItemAdapter adapter;
    public ItemAdapter getAdapter() {
        return adapter;
    }
    public void setAdapter(ItemAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setOnAdapterDataLoad(this::onLoad);
        notifyDataSetChanged();
    }



    public void createSubLayout() {
        subLayout = new LinearLayout(getContext());
        subLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) );
    }
    public void addSubItem(View view) {

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT , 1.0f);
        view.setLayoutParams(param);
        subLayout.addView(view);
    }
    public void notifyDataSetChanged() {
        layout.removeAllViews();
        createSubLayout();
        for (int i = 0;i<adapter.getCount();i++)
        {
            addSubItem(adapter.getView(i, null, null));
            if ( (i+1)%numColumns==0){layout.addView(subLayout); createSubLayout();}
        }
    }

    @Override
    public void onLoad(List list) {
        notifyDataSetChanged();
    }
}
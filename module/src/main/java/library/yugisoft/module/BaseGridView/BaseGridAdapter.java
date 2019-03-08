package library.yugisoft.module.BaseGridView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.List;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.DateTime;
import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.R;
import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

public class BaseGridAdapter extends  IGridAdapter
{


    public BaseGridAdapter(BaseDataGridView viewGroup) { super(viewGroup); }

    LinearLayout RowLayout()
    {
        LinearLayout  rowLayout = new LinearLayout(yugi.activity);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rowLayout.setMinimumHeight((int) yugi.convertDpToPixel(40,yugi.activity));
        rowLayout.setBackground(context.getResources().getDrawable(R.drawable.ripple));
        return  rowLayout;
    }
    LinearLayout RowLayout(int i)
    {
        LinearLayout  rowLayout =  RowLayout();
        cellLayouts(i,rowLayout);

        rowLayout.getBackground().setColorFilter((i % 2 != 0) ? getRowColor2() : getRowColor1(), PorterDuff.Mode.MULTIPLY);

        return  rowLayout;
    }
    LinearLayout cellLayout(BaseGridCell cell)
    {
        LinearLayout  layout = new LinearLayout(yugi.activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(cell.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT,cell.isAutoSize() ? 1: 0));
        layout.setMinimumHeight((int) yugi.convertDpToPixel(40,yugi.activity));
        BaseGridTextView textView = new BaseGridTextView(yugi.activity,null,0);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
        textView.setBaseGridCell(cell);
        layout.addView(textView);
        return layout;
    }
    LinearLayout cellLayouts(int i, LinearLayout  layout)
    {
        for(BaseGridCell bCell : getColumns())
        {
            BaseGridCell cell = bCell.Clone();
            Object object = list.get(i);
            cell.setObject(object);
            layout.addView(cellLayout(cell));
        }
        return  layout;
    }

    LinearLayout hLayouts()
    {
        LinearLayout  layout = RowLayout();
        for(BaseGridCell bCell : getColumns())
        {
            BaseGridCell cell = bCell.Clone();
            cell.setObject(vList.Filter(data.ColumnInfo,p-> p.Name.equals(cell.getFieldName())).get(0));
            cell.setFormat("${Value}");
            cell.setForeColor(getHeaderTextColor());

            layout.addView(cellLayout(cell));
        }
        layout.setBackgroundColor(getHeaderBackColor());
        return  layout;
    }

    @Override
    public View getView(int i)
    {
        return RowLayout(i);
    }

    @Override
    public View getView(int i, View view) {


        List<DataTable.DataColumn> cells = getList().get(i).Cells;

        for (DataTable.DataColumn cell : cells)
        {
            int id = context.getResources().getIdentifier("txt_"+cell.Name,"id",context.getPackageName());
            if (id>0)
            {
                BaseGridTextView textView = (BaseGridTextView)view.findViewById(id);
                if (textView!=null)
                {
                    textView.getBaseGridCell().setObject(cell);
                    textView.getBaseGridCell().setValue(cell.Value);
                }
            }
        }

        return  view;

    }

    @Override
    public View getHeaderView()
    {
        return hLayouts();
    }


    @Override
    public int getHeaderTextColor() {
        return Color.WHITE;
    }

    @Override
    public int getHeaderBackColor() {
        return Color.parseColor("#003366");
    }
}

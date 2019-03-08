package library.yugisoft.module.BaseGridView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.parse;
import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

public abstract class IGridAdapter extends ItemAdapter<DataTable.DataRow> {

    BaseDataGridView viewGroup;

    public IGridAdapter(BaseDataGridView view) {
        super(yugi.activity);
        viewGroup = view;
    }

    List<BaseGridCell> cols = new ArrayList<>();

    public List<BaseGridCell> getColumns() {
        return cols;
    }

    //region DATA
    protected DataTable data;

    public DataTable getData() {
        return data;
    }

    public void setData(String data) {
        setData(new DataTable(data));
    }

    public void setData(DataTable data) {
        this.data = data;
        setData(data.Rows);
    }

    public void setData(List<DataTable.DataRow> data) {
        setList(data);
    }
    //endregion

    BaseGridCell headerCell;
    void calculateLenght()
    {
        if (data != null && data.Columns.size() > 0 )
        {
            if (getColumns().size() == 0) {
                for (int i = 0; i < data.Columns.size(); i++) {
                    DataTable.DataColumn column = data.ColumnInfo.get(i);
                    BaseGridCell cell = new BaseGridCell();
                    cell.setFieldName(column.Name);
                    cell.setValue(column.Value);
                    cell.setType(column.objectValue.getClass());
                    cell.setTextAlign(column.gravity);
                    cell.setFormat("${" + cell.getFieldName() + "}");
                    cols.add(cell);
                }
            }

            if (getColumns().size() > 0)
            {
                for (int i = 0; i < getColumns().size(); i++) {
                    BaseGridCell cell = cols.get(i);
                    //if (cell.getFormatString().length()>0 && !cell.getFormatString().equals("${value}")) { }
                    String formatTmp = formats.get(cell.getFieldName());
                    formatTmp = formatTmp==null || formatTmp.length() == 0 ? cell.getFormatString() : formatTmp;
                    cell.setFormat(formatTmp);
                    String finalFormatTmp = formatTmp;
                    DataTable.DataRow r = vList.Max(getList(), p -> parse.Formatter.get(finalFormatTmp,p).length()).item;
                    String s = parse.Formatter.get(finalFormatTmp,r);
                    String h = data.Columns.get(data.Columns.indexOf(cell.getFieldName()));
                    cell.setWidth(getTextLenght(cell, s.length() > h.length() ? s:h ));
                }
            }


            if (vList.Sum(getColumns(), p -> p.getWidth()) < viewGroup.getWidth() && vList.Min(getColumns(), p -> p.getWidth()).value > 0) {
                BaseGridCell cell = vList.Max(getColumns(), p -> p.getWidth()).item;
                cell.setWidth(-1);
                cell.setAutoSize(true);
            }
        }
    }

    protected int getTextLenght(BaseGridCell cell, String text) {

        try {
            text = "_" + text + "_";
            Paint paint = new Paint();
            paint.setTextSize(yugi.convertDpToPixel(cell.getTextSize() + 2, yugi.activity));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setColor(cell.getForeColor());
            paint.setStyle(Paint.Style.FILL);
            Rect result = new Rect();
            paint.getTextBounds(text, 0, text.length(), result);
            cell.setHeight(cell.getHeight() < result.height() ? result.height() : cell.getHeight());
            return result.width();
        } catch (Exception ex) {
            return -2;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if (viewGroup.getWidth() == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            }, 100);
        } else {
            calculateLenght();
            super.notifyDataSetChanged();
        }
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (i < 0)
        {
            return getHeaderView();
        }
        else
        {
            if (getHeaderLayout() == null) {
                i--;
            }
            else if (i == 0)
            {
                getHeaderLayout().addView(getHeaderView());
            }
            if (getContentView() == 0)
                return getView(i);//;
            else
                return  getView(i,super.getView(i, view, viewGroup));
        }

    }


    public abstract View getView(int i);

    public View getView(int i,View view)
    {
        return view;
    }

    @Override
    public void setList(List<DataTable.DataRow> listt) {
        super.setList(listt);
    }


    private int
            hedaerViewID,
            detailViewID;
    private ViewGroup headerView;

    public int getHedaerViewID() {
        return hedaerViewID;
    }

    public void setHedaerViewID(int hedaerViewID) {
        this.hedaerViewID = hedaerViewID;
        if (hedaerViewID > 0)
            setHeaderView((ViewGroup) yugi.activity.getLayoutInflater().inflate(hedaerViewID, null));
        else
            setHeaderView(null);
    }

    public int getDetailViewID() {
        return detailViewID;
    }

    public void setDetailViewID(int detailViewID) {
        this.detailViewID = detailViewID;
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(ViewGroup headerView) {
        this.headerView = headerView;
        if (headerView!=null)
        cols = getTextViewInLayout(headerView);
        else
            cols.clear();
    }


    List<BaseGridCell> getTextViewInLayout(ViewGroup viewGroup) {
        List<BaseGridCell> list = new ArrayList<>();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof BaseGridTextView)
                list.add(((BaseGridTextView) view).getBaseGridCell());
            else if (view instanceof ViewGroup)
                list.addAll(getTextViewInLayout((ViewGroup) view));
        }
        return list;
    }

    @Override
    public int getCount() {
        if (super.getCount() == 0)
            return 0;
        else
            return super.getCount() + (getHeaderLayout() ==null ? 1 : 0);
    }


    public abstract int getHeaderTextColor();
    public abstract int getHeaderBackColor();

    private Hashtable<String,String> formats = new Hashtable<>();

    public void addFormat(String field,String format)
    {
        if (format.contains("$"))
            formats.put(field,format);
        else
            formats.put(field,yugi.Join("${{0}:{1}}",field,format));

        calculateLenght();
    }
    public void removeFormat(String field)
    {
        formats.remove(field);
        calculateLenght();
    }


    private LinearLayout headerLayout;

    public LinearLayout getHeaderLayout() {
        return headerLayout;
    }

    public void setHeaderLayout(LinearLayout headerLayout) {
        this.headerLayout = headerLayout;
    }



    private int rowColor1 = Color.WHITE , rowColor2 = Color.parseColor("#E6FFF3");


    public int getRowColor1() {
        return rowColor1;
    }

    public void setRowColor1(int rowColor1) {
        this.rowColor1 = rowColor1;
    }

    public int getRowColor2() {
        return rowColor2;
    }

    public void setRowColor2(int rowColor2) {
        this.rowColor2 = rowColor2;

    }



    public int getContentView() {
        return  contentView;
    }
}

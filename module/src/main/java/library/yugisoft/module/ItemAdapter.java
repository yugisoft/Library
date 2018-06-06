package library.yugisoft.module;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf on 16.02.2018.
 */

public class ItemAdapter<T> extends BaseAdapter
{

    public int PageLevel = 1;
    public final Context context;
    private List<ViewItem> Items = new ArrayList<>();
    private View view;
    private LayoutInflater layoutInflater;
    public int contentView;
    private String YerelParaBirimi="TL";

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
    public ItemAdapter(Context context)
    {
        this.context = context;
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public ItemAdapter(Context context, int id) {
        this(context);
        contentView=id;
        view = layoutInflater.inflate(id,null);
    }
    public ItemAdapter(Context context, View view) {
        this(context);
        this.view=view;
    }
    //region list
    protected List<T> list = new ArrayList<>();

    public void setList(final List<T> listt)
    {

        this.list = new ArrayList<>();
        this.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                ItemAdapter. this.list=listt;
                ItemAdapter.this.notifyDataSetChanged();
            }
        }, 500);

    }

    public List<T> getList()
    {
        return list;
    }


    //endregion
    //region  PrimaryCellIndex
    private int PrimaryCellIndex;
    public int getPrimaryCellIndex() {
        return PrimaryCellIndex;
    }

    public void setPrimaryCellIndex(int primaryCellIndex) {
        PrimaryCellIndex = primaryCellIndex;
    }
    //endregion
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }
    @Override
    public long getItemId(int i) {
        try
        {
            return Long.parseLong(list.get(getPrimaryCellIndex()).toString());
        }
        catch (Exception e)
        {
            Log.e("|E|ItemAdapter",e.getMessage());
            return  -99;
        }
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=layoutInflater.inflate(contentView,null);
        if(view!=null)
        {
            if (Items.size()>0)
            {
                for (ViewItem item:Items) {
                    item.setViewRun(view,list.get(i));
                }
            }
        }
        return view;
    }


    public List<ViewItem> getItems() {
        return Items;
    }




    public void setItems(List<ViewItem> items) {
        Items = items;
    }

    public void addItem(int id, String s, ItemAdapter.ItemType type) {
        Items.add(new ViewItem(id,s,type));
    }

    public void addItem(ViewItem item) {
        Items.add(item);
    }



    public enum ItemType {
        Label,
        Currency,
        Text,
        Image
    }

    class ViewItem {
        public int id;
        String Member;
        ItemAdapter.ItemType Type;
        ViewItem(int id, String s, ItemAdapter.ItemType type) {
            this.id=id;
            Member=s;
            Type=type;
        }

        public void setViewRun(View view, T c)
        {
            try
            {
                String value = c.getClass().getField(Member).get(c).toString();
                switch (Type)
                {

                    case Label:
                        ((TextView)view.findViewById(id)).setText(value);
                        break;
                    case Currency:

                        ((TextView)view.findViewById(id)).setText(yugi.NF2(value)+" "+ YerelParaBirimi);
                        break;
                    case Text:
                        ((EditText)view.findViewById(id)).setText(value);
                        break;
                    case Image:
                        break;
                }
            }
            catch (Exception e)
            {

            }
        }
    }
}

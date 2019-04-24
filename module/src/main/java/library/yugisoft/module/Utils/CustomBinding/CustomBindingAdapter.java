package library.yugisoft.module.Utils.CustomBinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Hashtable;

import library.yugisoft.module.Utils.CustomUtil;
import library.yugisoft.module.parse;
import library.yugisoft.module.yugi;

public class CustomBindingAdapter
{
    private String idTag = "v";
    private Context context;

    public Context getContext() {
        return context;
    }

    public String getIdTag() { return idTag; }
    public CustomBindingAdapter setIdTag(String idTag) { this.idTag = idTag;  return  this;}

    private int bindingViewID;
    public int getBindingViewID() { return bindingViewID; }
    public CustomBindingAdapter setBindingViewID(int bindingViewID)
    {
        this.bindingViewID = bindingViewID;
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setBindingView(layoutInflater.inflate(bindingViewID,null));
        return this;
    }

    private View bindingView;
    public View getBindingView() { return bindingView; }
    public CustomBindingAdapter setBindingView(View bindingView) { this.bindingView = bindingView;return this; }

    private Object bindingObject;
    public Object getBindingObject() { return bindingObject; }
    public CustomBindingAdapter setBindingObject(Object bindingObject) {
        this.bindingObject = bindingObject;
        if (bindingObject != null && getBindingView()!= null )
        {

            if (IDs == null) {
                IDs = new Hashtable<>();
                for (Field field : CustomUtil.getFields(bindingObject))
                {
                    int ID = context.getResources().getIdentifier("v" + field.getName(), "id", context.getPackageName());
                    View itemView = bindingView.findViewById(ID);
                    if (itemView != null) {
                        IDs.put(ID,field.getName());
                    }
                }
            }
        }
        else
        {
            IDs= null;
        }
        return  this;
    }


    public CustomBindingAdapter(){this(yugi.activity);}

    public CustomBindingAdapter(Context context) { this.context = context; }
    public CustomBindingAdapter(Context context, int bindingViewID) {
        this(context);
        setBindingViewID(bindingViewID);
    }
    public CustomBindingAdapter(Context context, int bindingViewID, Object bindingObject) {
        this(context);
        setBindingViewID(bindingViewID);
        setBindingObject(bindingObject);
    }
    public CustomBindingAdapter(Context context, View bindingView) {
        this(context);
        setBindingView(bindingView);
    }
    public CustomBindingAdapter(Context context, View bindingView, Object bindingObject) {
        this(context);
        setBindingView(bindingView);
        setBindingObject(bindingObject);
    }

    public CustomBindingAdapter(int bindingViewID)
    {
        this(yugi.activity,bindingViewID);
    }
    public CustomBindingAdapter(int bindingViewID, Object bindingObject) {
        this(yugi.activity,bindingViewID,bindingObject);
    }

    public CustomBindingAdapter(View bindingView)
    {
        this(yugi.activity,bindingView);
    }
    public CustomBindingAdapter(View bindingView, Object bindingObject) {
        this(yugi.activity,bindingView,bindingObject);
    }

    Hashtable<Integer, String> IDs;

    void setViewValue(View view) {
        Object pValue = null;
        if (getBindingObject() != null)
            pValue = getBindingObject() instanceof IBindableModel ? ((IBindableModel)getBindingObject()).getValue(IDs.get(view.getId())):CustomUtil.getValue(getBindingObject(),IDs.get(view.getId()));
        String sValue = pValue ==null ? "" : String.valueOf(pValue);

        if (view instanceof TextView)
        {
            TextView tView = (TextView)view;

            String format = tView.getContentDescription() != null ? tView.getContentDescription().toString() : "";
            if (format.length()>0)
            {
                try{sValue = parse.Formatter.purify(format,getBindingObject());}catch (Exception ignored){}
            }
            tView.setText(sValue);
        }
        else if (view instanceof ImageView)
        {
            if (pValue != null) {
                ImageView iView = (ImageView) view;
                yugi.imageLoader.displayImage(pValue.toString(), iView, yugi.options, null);
            }
        }
        else if (view instanceof Checkable)
        {
            Checkable cView = (Checkable) view;
            cView.setChecked(parse.toBoolean(pValue));
        }

    }


    public void bind()
    {
        bind(getBindingObject());
    }
    public void bind(Object bindingObject)
    {
        setBindingObject(bindingObject);
        if (bindingObject != null)
        {
            for (int id: IDs.keySet())
            {
                setViewValue(getBindingView().findViewById(id));
            }
        }
    }


}

package library.yugisoft.module.Utils.CustomBinding;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import library.yugisoft.module.Utils.CustomUtil;
import library.yugisoft.module.parse;
import library.yugisoft.module.yugi;

public class CustomBindingAdapter
{
    private String idTag = "v";
    private Context context;
    private View layoutController;
    private int row = -1;

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
                    BindProperty property = CustomUtil.getFieldProperty(field);
                    int ID = context.getResources().getIdentifier(property.DisplayIdName().length()> 0 ? property.DisplayIdName() : ("v" +  field.getName()), "id", context.getPackageName());
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
    public CustomBindingAdapter(Context context, int bindingViewID,Object bindingObject) {
        this(context);
        setBindingViewID(bindingViewID);
        setBindingObject(bindingObject);
    }
    public CustomBindingAdapter(Context context, View bindingView) {
        this(context);
        setBindingView(bindingView);
    }
    public CustomBindingAdapter(Context context, View bindingView,Object bindingObject) {
        this(context);
        setBindingView(bindingView);
        setBindingObject(bindingObject);
    }

    public CustomBindingAdapter(int bindingViewID)
    {
        this(yugi.activity,bindingViewID);
    }
    public CustomBindingAdapter(int bindingViewID,Object bindingObject) {
        this(yugi.activity,bindingViewID,bindingObject);
    }

    public CustomBindingAdapter(View bindingView)
    {
        this(yugi.activity,bindingView);
    }
    public CustomBindingAdapter(View bindingView,Object bindingObject) {
        this(yugi.activity,bindingView,bindingObject);
    }

    Hashtable<Integer, String> IDs;

    void setViewValue(View view,String fieldName)
    {
        BindProperty property = CustomUtil.getFieldProperty(getBindingObject(), fieldName);
        if (getLayoutController()!=null)
        {
            View layoutView = getLayoutController().findViewById(view.getId());
            if (layoutView!= null)
            {
                new Handler().postDelayed(()->
                {
                    view.getLayoutParams().width = layoutView.getWidth();
                },500);
                //view.getLayoutParams().height = layoutView.getHeight();
            }

        }
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

            if (tView.getText().length() == 0 && property.isEmptySetInvisible())
                tView.setVisibility(View.GONE);
            else
                tView.setVisibility(View.VISIBLE);

        }
        else if (view instanceof ImageView)
        {
            if (pValue != null) {
                ImageView iView = (ImageView) view;
                yugi.imageLoader.displayImage(pValue.toString(), iView, yugi.options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) { }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        iView.setImageDrawable(null);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        iView.setImageDrawable(null);
                    }
                });
            }
        }
        else if (view instanceof Checkable)
        {
            Checkable cView = (Checkable) view;
            cView.setChecked(parse.toBoolean(pValue));
        }

        if (onViewDrawings.get(fieldName) != null)
            onViewDrawings.get(fieldName).onDraw(getRow(),view,fieldName,pValue);


    }


    List<View> viewList;
    public void bind()
    {
        bind(getBindingObject());
    }
    public void bind(Object bindingObject)
    {
        setBindingObject(bindingObject);
        if (bindingObject != null)
        {
            viewList = new ArrayList<>();
            for (int id: IDs.keySet())
            {
                viewList.add(getBindingView().findViewById(id));
                setViewValue(getBindingView().findViewById(id),IDs.get(id));
            }
            if (getOnRowDrawing()!=null)
            {
                getOnRowDrawing().onDraw(getRow(),getBindingView(),getBindingObject());
                getOnRowDrawing().onDraw(getRow(),getBindingView(),getBindingObject(),viewList);
            }
        }
    }


    public void setLayoutController(View layoutController) {
        this.layoutController = layoutController;
    }

    public View getLayoutController() {
        return layoutController;
    }


    private Hashtable<String, OnViewDrawing> onViewDrawings = new Hashtable<>();

    public <T> CustomBindingAdapter addOnViewDrawing (String name , OnViewDrawing<T> onViewDrawing) {
        onViewDrawings.put(name,onViewDrawing);
        return  this;
    }

    public <T> OnViewDrawing<T> getOnViewDrawing(String name)
    {
        return onViewDrawings.get(name);
    }

    public void RemoveOnViewDrawing(String name)
    {
        onViewDrawings.remove(name);
    }

    public void setOnViewDrawing(Hashtable<String, OnViewDrawing> onViewDrawings) {
        this.onViewDrawings = onViewDrawings;
    }

    public CustomBindingAdapter setOnViewDrawings(Hashtable<String, OnViewDrawing> onViewDrawings) {
        this.onViewDrawings = onViewDrawings;
        return this;
    }

    public int getRow() {
        return row;
    }

    public CustomBindingAdapter setRow(int row) {
        this.row = row;
        return  this;
    }

    public interface OnViewDrawing<Type>
    {
        void onDraw(int index,View view,String fieldName,Type value);
    }

    private BindingGridView.OnRowDrawing onRowDrawing;

    public <T> BindingGridView.OnRowDrawing<T> getOnRowDrawing() {
        return onRowDrawing;
    }

    public <T> CustomBindingAdapter setOnRowDrawing(BindingGridView.OnRowDrawing<T> onRowDrawing) {
        this.onRowDrawing = onRowDrawing;
        return  this;

    }

    public interface OnRowDrawing<T>
    {
        default void onDraw(int index,View view,T item) {}
        default void onDraw(int index,View view,T item,View[] views) {}
    }
}
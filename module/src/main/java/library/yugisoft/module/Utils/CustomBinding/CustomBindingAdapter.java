package library.yugisoft.module.Utils.CustomBinding;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import library.yugisoft.module.CurrencyTextView;
import library.yugisoft.module.DateTextView;
import library.yugisoft.module.DateTime;
import library.yugisoft.module.LoopTextView;
import library.yugisoft.module.PhoneTextEdit;
import library.yugisoft.module.Utils.CustomUtil;
import library.yugisoft.module.parse;
import library.yugisoft.module.yugi;

public class CustomBindingAdapter
{
    Hashtable<Integer, Field> IDs;
    List<View> viewList;
    private Hashtable<String, OnViewDrawing> onViewDrawings = new Hashtable<>();
    private BindingGridView.OnRowDrawing onRowDrawing;

    //region Order
    private String idTag = "v";
    private Context context;
    private View layoutController;
    private int row = -1;
    private int bindingViewID;
    private View bindingView;
    private Object bindingObject;

    public Context getContext() {
        return context;
    }
    public String getIdTag() { return idTag; }
    public int getBindingViewID() { return bindingViewID; }
    public View getBindingView() { return bindingView; }
    public Object getBindingObject() { return bindingObject; }
    public int getRow() {
        return row;
    }

    public CustomBindingAdapter setIdTag(String idTag) { this.idTag = idTag;  return  this;}
    public CustomBindingAdapter setBindingViewID(int bindingViewID) {
        this.bindingViewID = bindingViewID;
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setBindingView(layoutInflater.inflate(bindingViewID,null));
        return this;
    }
    public CustomBindingAdapter setBindingView(View bindingView) {
        if (this.bindingView != bindingView)
        {
            IDs = null;
            setBindingObject(getBindingObject());
        }
        this.bindingView = bindingView;
        return this;
    }
    public CustomBindingAdapter setBindingObject(Object bindingObject) {

        if (bindingObject != null && getBindingView()!= null )
        {
         if (this.bindingObject ==null || this.bindingObject.getClass() .equals(bindingObject.getClass()))
             IDs = null;
            if (IDs == null) {
                IDs = new Hashtable<>();
                for (Field field : CustomUtil.getFields(bindingObject))
                {
                    field.setAccessible(true);
                    BindProperty property = CustomUtil.getFieldProperty(field);
                    String viewIdName = property.DisplayIdName().length()> 0 ? property.DisplayIdName() : (getIdTag() +  field.getName());
                    int ID = context.getResources().getIdentifier(viewIdName, "id", context.getPackageName());
                    View itemView = bindingView.findViewById(ID);
                    if (itemView != null) {
                        IDs.put(ID,field);
                    }
                    field.setAccessible(false);
                }
            }
        }
        else
        {
            IDs= null;
        }
        this.bindingObject = bindingObject;
        return  this;
    }
    public CustomBindingAdapter setLayoutController(View layoutController) {
        this.layoutController = layoutController;
        return this;
    }
    public View getLayoutController() {
        return layoutController;
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
    public CustomBindingAdapter setRow(int row) {
        this.row = row;
        return  this;
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

    public CustomBindingAdapter setOnViewDrawing(Hashtable<String, OnViewDrawing> onViewDrawings) {
        this.onViewDrawings = onViewDrawings;
        return this;
    }
    public CustomBindingAdapter setOnViewDrawings(Hashtable<String, OnViewDrawing> onViewDrawings) {
        this.onViewDrawings = onViewDrawings;
        return this;
    }

    public <T> OnViewDrawing<T> getOnViewDrawing(String name)
    {
        return onViewDrawings.get(name);
    }

    public <T> CustomBindingAdapter addOnViewDrawing (String name , OnViewDrawing<T> onViewDrawing) {
        onViewDrawings.put(name,onViewDrawing);
        return  this;
    }

    public void RemoveOnViewDrawing(String name)
    {
        onViewDrawings.remove(name);
    }


    public interface OnViewDrawing<Type>
    {
        void onDraw(int index,View view,String fieldName,Type value);
    }
    public interface OnRowDrawing<T>
    {
        default void onDraw(int index,View view,T item) {}
        default void onDraw(int index,View view,T item,View[] views) {}
    }


    public <T> BindingGridView.OnRowDrawing<T> getOnRowDrawing() {
        return onRowDrawing;
    }

    public <T> CustomBindingAdapter setOnRowDrawing(BindingGridView.OnRowDrawing<T> onRowDrawing) {
        this.onRowDrawing = onRowDrawing;
        return  this;

    }

    //endregion



    void setViewValue(View view,Field field)
    {
        String fieldName = field.getName();
        BindProperty property = CustomUtil.getFieldProperty(field);
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
            pValue = getBindingObject() instanceof IBindableModel ? ((IBindableModel)getBindingObject()).getValue(fieldName):CustomUtil.getValue(getBindingObject(),fieldName);
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
        else if (view instanceof CurrencyTextView)
        {
            ((CurrencyTextView)view).setTutar(parse.toDouble(pValue));
        }
        if (onViewDrawings.get(fieldName) != null)
            onViewDrawings.get(fieldName).onDraw(getRow(),view,fieldName,pValue);


    }

    public void bind()
    {
        bind(getBindingObject());
    }
    public void bind(Object bindingObject) {
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
    public void reverse()
    {
        reverse(getBindingView());
    }

    public void reverse(View view) {
        setBindingView(view);
        if (getBindingObject() != null)
        {
            viewList = new ArrayList<>();
            for (int id: IDs.keySet())
            {
                setFieldValue(getBindingView().findViewById(id),IDs.get(id));
            }
        }
    }

    private void setFieldValue(View view, Field field)
    {
        field.setAccessible(true);
        if (field.isAnnotationPresent(BindingReverseable.class))
        {
            Object value = null;
            if (view instanceof PhoneTextEdit)
                value = ((PhoneTextEdit) view).toString();
            else if (view instanceof EditText)
                value = ((EditText) view).getText().toString();
            else if (view instanceof CurrencyTextView)
                value = ((CurrencyTextView)view).Tutar;
            else if (view instanceof DateTextView)
                value = ((DateTextView) view).getDateTime();
            else if (view instanceof LoopTextView) {
                Object obj = ((LoopTextView) view).getSelectedObject();
                if (obj != null) {
                    if (obj.getClass().isAnnotationPresent(BindingItemLooper.class)) {
                        String fn = obj.getClass().getAnnotation(BindingItemLooper.class).IdFieldName();
                        obj = CustomUtil.getValue(obj, fn);
                    } else
                        value = obj;
                }
            } else if (view instanceof TextView)
                value = ((TextView) view).getText().toString();
            else if (view instanceof Checkable)
                value = ((Checkable) view).isChecked();
            field.setAccessible(false);
            setFieldValue(field, value);
        }
    }
    private void setFieldValue(Field f,Object value)
    {
        try
        {
            f.setAccessible(true);
            Class<?> clazz = f.getType();

            if (clazz.equals(Integer.class) || clazz.equals(int.class))
                f.setInt(getBindingObject(), parse.toInt(value));
             else if (clazz.equals(Long.class) || clazz.equals(long.class))
                f.setLong(getBindingObject(), parse.toLong(value));
             else if (clazz.equals(Double.class) || clazz.equals(double.class))
                f.setDouble(getBindingObject(), parse.toDouble(value));
            else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
                f.setBoolean(getBindingObject(), parse.toBoolean(value));
            else if (clazz.equals(DateTime.class) )
                f.set(getBindingObject(),value);
            else
                f.set(getBindingObject(),value);

        }
        catch (Exception ex){}
        finally {
            f.setAccessible(false);
        }
    }

}
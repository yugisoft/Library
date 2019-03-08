package library.yugisoft.module.BaseGridView;

import android.graphics.Color;
import android.print.PrintAttributes;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.DateTime;
import library.yugisoft.module.INTERFACES;
import library.yugisoft.module.Interfaces.IFormatter;
import library.yugisoft.module.Interfaces.ISerializable;
import library.yugisoft.module.parse;

public class BaseGridCell implements IFormatter, ISerializable
{
    private String
            fieldName = "", format = "${value}";
    private Object
            value = "";
    private Class<?>
            type = String.class;
    private int
            backColor = Color.TRANSPARENT, foreColor = Color.BLACK, textAlign = Gravity.LEFT, padding = 5, paddingLeft = 5, paddingRight = 5, paddingTop = 5, paddingBottom = 5, width = WRAP_CONTENT, height = WRAP_CONTENT
            ,textSize = 14;

    private boolean
             autoSize = false
            ,visible = true
            ;

    public static final int
             MATCH_PARENT = -1
            ,WRAP_CONTENT = -2
            ,TYPE_STRING = 0
            ,TYPE_INT = 1
            ,TYPE_DOUBLE = 2
            ,TYPE_DATE = 3
            ,TYPE_LONG = 4
            ,TYPE_BOOLEAN = 5
            ;



    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFormat(String format) {
        this.format = format == null ? "": format;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        trigListener();
    }




    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
    public void setType(int type) {
        switch (type)
        {
            case TYPE_STRING:
                this.type = String.class;
                break;
            case TYPE_INT:
                this.type = Integer.class;
                break;
            case TYPE_DOUBLE:
                this.type = Double.class;
                break;
            case TYPE_DATE:
                this.type = DateTime.class;
                break;
            case TYPE_LONG:
                this.type = Long.class;
                break;
            case TYPE_BOOLEAN:
                this.type = Boolean.class;
                break;
        }

    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public int getForeColor() {
        return foreColor;
    }

    public void setForeColor(int foreColor) {
        this.foreColor = foreColor;
    }

    public int getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(int textAlign) {
        this.textAlign = textAlign;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String getFormatString() {
        return format;
    }

    @Override
    public String getFormatValue() {
        return parse.Formatter.get(getFormatString(),getFormatObject());
       //Object args = getFormatObject();
       //String format = args instanceof DataTable.DataRow ? parse.Formatter.purifyDR(getFormatString(), (DataTable.DataRow) args) : parse.Formatter.purify(getFormatString(),args);
       //return  String.format(format,args);
    }

    private List<INTERFACES.OnResponse<String>> onValueChangeListener = new ArrayList<>();

    public void addListner(INTERFACES.OnResponse<String> l)
    {
        onValueChangeListener.remove(l);
        onValueChangeListener.add(l);
    }

    public void trigListener()
    {
        for (INTERFACES.OnResponse<String> l : onValueChangeListener)
            l.onResponse(getFormatValue());
    }


    public boolean isAutoSize() {
        return autoSize;
    }

    public void setAutoSize(boolean autoSize) {
        this.autoSize = autoSize;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }




    public BaseGridCell Clone()
    {
        String Json = parse.toJson(this);
        return parse.jsonTo(Json,this.getClass());
    }

    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public Object getFormatObject() {
        return object;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}

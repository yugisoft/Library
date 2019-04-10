package library.yugisoft.module.BaseGridView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import library.yugisoft.module.R;

public class BaseGridTextView extends android.support.v7.widget.AppCompatTextView
{
    private BaseGridCell baseGridCell;
    public BaseGridTextView(Context context) { this(context,null,0); }
    public BaseGridTextView(Context context, AttributeSet attrs) { this(context, attrs,0); }
    public BaseGridTextView(Context context, AttributeSet attrs, int defStyleAttr) {

        this(context, attrs, defStyleAttr, null);

        BaseGridCell baseGridCell = new BaseGridCell();
        TypedArray typed = getContext().obtainStyledAttributes(attrs, R.styleable.BaseGridTextView, defStyleAttr, 0);
        if (typed!=null)
        {
            baseGridCell.setFormat(typed.getString(R.styleable.BaseGridTextView_format));
            baseGridCell.setFieldName(typed.getString(R.styleable.BaseGridTextView_fieldName));
            baseGridCell.setType(typed.getInt(R.styleable.BaseGridTextView_dataType,0));
            baseGridCell.setAutoSize(typed.getBoolean(R.styleable.BaseGridTextView_autoWidth,false));

        }
        baseGridCell.setForeColor(this.getTextColors().getDefaultColor());
        baseGridCell.setTextAlign(getGravity());
        baseGridCell.setVisible(this.getVisibility() == VISIBLE);
        baseGridCell.setPaddingLeft(this.getPaddingLeft());
        baseGridCell.setPaddingRight(this.getPaddingRight());
        baseGridCell.setPaddingTop(this.getPaddingTop());
        baseGridCell.setPaddingBottom(this.getPaddingBottom());

        setBaseGridCell(baseGridCell);
    }
    public BaseGridTextView(Context context, AttributeSet attrs, int defStyleAttr,BaseGridCell baseGridCell)  {
        super(context, attrs, defStyleAttr);
        setBaseGridCell(baseGridCell);
    }
    public BaseGridCell getBaseGridCell()
    {
        return baseGridCell;
    }
    public void setBaseGridCell(BaseGridCell baseGridCell) {
        this.baseGridCell = baseGridCell;
        if (baseGridCell!=null)
        {
           baseGridCell.setFormat(baseGridCell.getFormatString().length() == 0 ? "${"+baseGridCell.getFieldName()+"}" : baseGridCell.getFormatString());
             baseGridCell.addListner(p-> this.setText(baseGridCell.getFormatValue()));

            this.setBackgroundColor(baseGridCell.getBackColor());
            this.setTextColor(baseGridCell.getForeColor());

            this.setPadding(baseGridCell.getPaddingLeft(),baseGridCell.getPaddingTop(),baseGridCell.getPaddingRight(),baseGridCell.getPaddingBottom());
            this.setPaddingRelative(baseGridCell.getPaddingLeft(),baseGridCell.getPaddingTop(),baseGridCell.getPaddingRight(),baseGridCell.getPaddingBottom());
            this.setGravity(baseGridCell.getTextAlign());

            this.setText(baseGridCell.getFormatValue());
            this.setVisibility(baseGridCell.isVisible() ? VISIBLE : GONE);

            this.setWidth(baseGridCell.getWidth());

            setLayoutParams(this,BaseGridCell.MATCH_PARENT,BaseGridCell.MATCH_PARENT,1);

        }
    }

    public static void setLayoutParams(View view,int w,int h , int s) {
        ViewGroup.LayoutParams param = view.getLayoutParams();
        if (param != null) {
            param.width = w;
            param.height = h;
            if (view.getLayoutParams() instanceof LinearLayout.LayoutParams)
                ((LinearLayout.LayoutParams) param).weight = s;
            view.setLayoutParams(param);
        }
    }
}

package library.yugisoft.module.popwindow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import library.yugisoft.module.Base.BaseDialog;
import library.yugisoft.module.LoopView;
import library.yugisoft.module.R;

public class TimePickerPopWin extends PopupWindow implements View.OnClickListener {

    private Button cancelBtn;
    private Button confirmBtn;
    private LoopView hourLoopView;
    private LoopView minuteLoopView;
    private View pickerContainerV;
    private View contentView;

    private int hourPos = -1;
    private int minutePos = -1;


    private Context mContext;
    private String textCancel;
    private String textConfirm;
    private int colorCancel;
    private int colorConfirm;
    private int btnTextsize;
    private int viewTextSize;

    List<String> hourList = new ArrayList();
    List<String> minList = new ArrayList();



    public static class Builder {
        private Context context;
        private OnTimePickListener listener;

        public Builder(Context context, OnTimePickListener listener) {
            this.context = context;
            this.listener = listener;
        }


        //Optional Parameters
        private String textCancel = "Cancel";
        private String textConfirm = "Confirm";
        private int colorCancel = Color.parseColor("#999999");
        private int colorConfirm = Color.parseColor("#303F9F");
        private int btnTextSize = 16;//text btnTextsize of cancel and confirm button
        private int viewTextSize = 25;
        private int hour,minute;

        public Builder textCancel(String textCancel){
            this.textCancel = textCancel;
            return this;
        }

        public Builder textConfirm(String textConfirm){
            this.textConfirm = textConfirm;
            return this;
        }

        public Builder colorCancel(int colorCancel){
            this.colorCancel = colorCancel;
            return this;
        }

        public Builder colorConfirm(int colorConfirm){
            this.colorConfirm = colorConfirm;
            return this;
        }

        public Builder btnTextSize(int textSize){
            this.btnTextSize = textSize;
            return this;
        }

        public Builder viewTextSize(int textSize){
            this.viewTextSize = textSize;
            return this;
        }

        public TimePickerPopWin build(){
            return new TimePickerPopWin(this);
        }

        public Builder setHour(int hour) {
            this.hour = hour;
            return  this;
        }

        public Builder setMinute(int minute) {
            this.minute = minute;
            return  this;
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }
    }

    public TimePickerPopWin(Builder builder){
        this.textCancel = builder.textCancel;
        this.textConfirm = builder.textConfirm;
        this.mContext = builder.context;
        this.mListener = builder.listener;
        this.colorCancel = builder.colorCancel;
        this.colorConfirm = builder.colorConfirm;
        this.btnTextsize = builder.btnTextSize;
        this.viewTextSize = builder.viewTextSize;
        this.hourPos = builder.getHour();
        this.minutePos= builder.getMinute();
        initView();
    }

    private OnTimePickListener mListener;

    private void initView(){
        contentView= LayoutInflater.from(mContext).inflate(R.layout.layout_time_picker,null);
        cancelBtn=(Button)contentView.findViewById(R.id.btn_cancel);
        cancelBtn.setTextColor(colorCancel);
        cancelBtn.setTextSize(btnTextsize);
        confirmBtn=(Button)contentView.findViewById(R.id.btn_confirm);
        confirmBtn.setTextColor(colorConfirm);
        confirmBtn.setTextSize(btnTextsize);
        hourLoopView = (LoopView) contentView.findViewById(R.id.picker_hour);
        minuteLoopView = (LoopView) contentView.findViewById(R.id.picker_minute);

        pickerContainerV = contentView.findViewById(R.id.container_picker);


        hourLoopView.setLoopListener(new LoopView.LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                hourPos=item;
            }
        });

        minuteLoopView.setLoopListener(new LoopView.LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                minutePos=item;
            }
        });


        initPickerViews();  // init hour and minute loop view


        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        contentView.setOnClickListener(this);

        if(!TextUtils.isEmpty(textConfirm)){
            confirmBtn.setText(textConfirm);
        }

        if(!TextUtils.isEmpty(textCancel)){
            cancelBtn.setText(textCancel);
        }

        setTouchable(true);
        setFocusable(true);

        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.FadeInPopWin);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }



    private void initPickerViews(){

        if (hourPos<0 || minutePos < 0)
        {
            hourPos = Calendar.getInstance().get(Calendar.HOUR);
            minutePos= Calendar.getInstance().get(Calendar.MINUTE);
        }


        for (int i = 0; i <=23; i++) {
            hourList.add(format2LenStr(i));
        }

        for (int j = 0; j <60; j++) {
            minList.add(format2LenStr(j));
        }



        hourLoopView.setDataList(hourList);
        hourLoopView.setInitPosition(hourPos);

        minuteLoopView.setDataList( minList);
        minuteLoopView.setInitPosition(minutePos);


    }


    @Override
    public void onClick(View v) {

        if (v == contentView || v == cancelBtn) {
            dismissPopWin();
        } else if (v == confirmBtn) {

            if (null != mListener) {


                StringBuffer sb = new StringBuffer();
                sb.append(String.valueOf(hourList.get(hourPos)));
                sb.append(":");
                sb.append(String.valueOf(minList.get(minutePos)));
                mListener.onTimePickCompleted(hourPos,minutePos,sb.toString());
            }
            dismissPopWin();
        }
    }

    /**
     * Show time picker popWindow
     *
     * @param activity
     */
    Dialog  dialog;
    public void showPopWin(Activity activity) {

        if (null != activity) {

            TranslateAnimation trans = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0);



            if (BaseDialog.isShowing())
            {
                try{((ViewGroup)pickerContainerV.getParent()).removeView(pickerContainerV);}catch (Exception ignored){}
                dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(pickerContainerV);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
                wmlp.gravity = Gravity.BOTTOM;
                dialog.show();
            }
            else
            {
                try{((ViewGroup)pickerContainerV.getParent()).removeView(pickerContainerV);}catch (Exception ignored){}
                ((ViewGroup)getContentView()).addView(pickerContainerV);
                showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }

            trans.setDuration(400);
            trans.setInterpolator(new AccelerateDecelerateInterpolator());

            pickerContainerV.startAnimation(trans);
        }
    }

    /**
     * Dismiss time picker popWindow
     */
    public void dismissPopWin() {

        TranslateAnimation trans = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        trans.setDuration(400);
        trans.setInterpolator(new AccelerateInterpolator());
        trans.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                dismiss();
            }
        });

        pickerContainerV.startAnimation(trans);

        if (BaseDialog.isShowing())
            dialog.dismiss();
    }

    /**
     * Transform int to String with prefix "0" if less than 10
     * @param num
     * @return
     */
    public static String format2LenStr(int num) {

        return (num < 10) ? "0" + num : String.valueOf(num);
    }

    public interface OnTimePickListener {

        /**
         * Listener when date been selected
         *
         * @param time
         */
         void onTimePickCompleted(int hour, int minute,  String time);
    }
}

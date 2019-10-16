package library.yugisoft.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import library.yugisoft.module.Base.BaseDialog;

import static android.util.DisplayMetrics.DENSITY_DEFAULT;

/**
 * Created by Yusuf on 27.02.2018.
 */

public class yugi
{
    //region STATİC
    public static vActivity activity = null;
    public static ImageLoader imageLoader;
    public static DisplayImageOptions options;
    public static boolean TestMode = true;
    public static String culture_Ondalik_ayrac=".";
    public static FloatingNumPadView floatingNumPadView;
    /**
     * Finds the first child in #rootView that is an instance of #clazz
     *
     * @param rootView The View whose hierarchy should be examined for instances of #clazz.
     * @param clazz    The Class to search for within #rootView.
     * @param <T>      The type of View subclass to search for.
     * @return The first child in #rootView this is an instance of #clazz.
     */
    public static <T extends View> T findViewByClassReference(View rootView, Class<T> clazz) {
        if(clazz.isInstance(rootView)) {
            return clazz.cast(rootView);
        }
        if(rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) rootView;
            for(int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                T match = findViewByClassReference(child, clazz);
                if(match != null) {
                    return match;
                }
            }
        }
        return null;
    }

    /**
     * Returns a Collection of View subclasses instances of type T found within #rootView.
     *
     * @param rootView The View whose hierarchy should be examined for instances of #clazz.
     * @param clazz    The Class to search for within #rootView.
     * @param out      A Collection of View subclasses of type T that will be populated with matches found in #rootView.
     * @param <T>      The type of View subclass to search for.
     * @return A Collection of View subclasses instances of type T found within #rootView.
     */
    public static <T extends View> Collection<T> findViewsByClassReference(View rootView, Class<T> clazz, Collection<T> out) {
        if(out == null) {
            out = new HashSet<>();
        }
        if(clazz.isInstance(rootView)) {
            out.add(clazz.cast(rootView));
        }
        if(rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) rootView;
            for(int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                findViewsByClassReference(child, clazz, out);
            }
        }
        return out;
    }

    /**
     * Returns a Collection of View subclasses instances of type T found within #rootView.
     *
     * @param rootView The View whose hierarchy should be examined for instances of #clazz.
     * @param clazz    The Class to search for within #rootView.
     * @param <T>      The type of View subclass to search for.
     * @return A Collection of View subclasses instances of type T found within #rootView.
     */
    public static <T extends View> Collection<T> findViewsByClassReference(View rootView, Class<T> clazz) {
        return findViewsByClassReference(rootView, clazz, null);
    }
    //endregion
    //region APP

    public static class vActivity extends AppCompatActivity {

        public static boolean LoadMainConfig  = false;
        public static View MainView;
        public boolean   isFinisForCatchExtras(){return false;}

        public  boolean isLoad=false;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            setConfig(this);
            super.onCreate(savedInstanceState);
            isLoad=false;
            if (!LoadMainConfig)
            {
                LocaleManager.setLocale(this);
                LoadMainConfig=true;
            }
        }

        @Override
        protected void onResume() {
            setConfig(this);
            super.onResume();
        }

        public void closeKeyboard()
        {
            yugi.closeKeyboard(this);
        }

        //region ActivityResult
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (vactivityResultListener!=null)
                vactivityResultListener.onvActivityResult(requestCode, resultCode, data);
        }

        private vActivityResultListener vactivityResultListener;

        public vActivity.vActivityResultListener getvActivityResultListener() {
            return vactivityResultListener;
        }

        public void setvActivityResultListener(vActivity.vActivityResultListener vActivityResultListener) {
            this.vactivityResultListener = vActivityResultListener;
        }

        public interface vActivityResultListener {
            void onvActivityResult(int requestCode, int resultCode, Intent data);
        }
        //endregion

        @Override
        public void setContentView(int layoutResID) {
            super.setContentView(layoutResID);
            MainView = (View)findViewById(android.R.id.content);
            MainView.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent ev)
                {
                    hideKeyboard(view);
                    return false;
                }
            });
        }

        public void hideKeyboard()
        {
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            if (BaseDialog.isShowing())
                in.hideSoftInputFromWindow(BaseDialog.mDialog.getWindow().peekDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        public void hideKeyboard(View view)
        {
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            if (BaseDialog.isShowing())
                in.hideSoftInputFromWindow(BaseDialog.mDialog.getWindow().peekDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        public Object get(String key) {
            try
            {
                Bundle extras = getIntent().getExtras();
                return  extras.getString(key);
            } catch (Exception ex) {
                if(isFinisForCatchExtras())
                this.finish();
            }
            return  "";
        }
        public int getInt(String key)
        {
            return  getInt(key,0);
        }
        public int getInt(String key,int def) {
            try
            {
                return Integer.parseInt(getString(key).replace(".0",""));
            }
            catch (Exception ex)
            {
                return def;
            }
        }
        public String getString(String key) {
            try
            {
                return String.valueOf(get(key));
            }
            catch (Exception ex)
            {
                return "";
            }
        }
        public long getLong(String key)
        {
            return  getLong(key,0);
        }
        public long getLong(String key,long def) {
            try
            {
                return Long.parseLong(getString(key).replace(".0",""));
            }
            catch (Exception ex)
            {
                return def;
            }
        }
    }

    //endregion
    //region Dialog
    public static class Dialog
    {
        public static class mDialog {
            public mDialog(Activity activity, int view_id, boolean TopMust) {
                //region Header
                final android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(activity,R.style.MyDialogTheme);
                b.setCancelable(!TopMust);
                final AlertDialog builder = b.create();
                LayoutInflater layoutInflater =(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout vi=(LinearLayout)layoutInflater.inflate(view_id,null);
                //endregion
                View btnkapat =vi.findViewById(R.id.DialogKapat);
                if(btnkapat!=null)
                {
                    btnkapat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            builder.dismiss();
                        }
                    });
                }
                View btnkapat2 = vi.findViewById(R.id.DialogKapatTop);
                if(btnkapat2!=null)
                {
                    btnkapat2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            builder.dismiss();
                        }
                    });
                }
                //region Footer

                builder.setView(vi);
                dialog=builder;
                view=vi;

                //endregion
            }
            public  AlertDialog dialog;
            public  View view;
            public void DialogMax()
            {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(lp);

            }
        }
        public static  mDialog Loading(Activity activity) {
            mDialog dialog = new mDialog(activity, R.layout.ly_loading,true);
           ImageView im = (ImageView) dialog.view.findViewById(R.id.img_loading);
           final AnimationDrawable animationDrawable = (AnimationDrawable) im.getDrawable();
           animationDrawable.start();
            return dialog;
        }
    }

    //endregion
    //region MessageBox
    public static class MessageBox {
        static ProgressDialog gdialog , tdialog;
        static boolean Top=false;
        static Dialog.mDialog ldialog;
        public static void Show(Context context, String message) {
            if(!Top)
            {
                Close();
                gdialog = new ProgressDialog(context);
                gdialog.setCanceledOnTouchOutside(false);
                gdialog.setCancelable(false);
                gdialog.setMessage(message);
                gdialog.show();
            }
        }
        public static void ShowTop(Context context, String message) {
            Top=true;
            Close();
            tdialog = new ProgressDialog(context);
            tdialog.setCanceledOnTouchOutside(false);
            tdialog.setCancelable(false);
            tdialog.setMessage(message);
            tdialog.show();
        }
        public static void CloseTop() {
            try
            {

                gdialog.dismiss();
            } catch (Exception e) {
            }
            try
            {

                tdialog.dismiss();

            } catch (Exception e) {
            }
            Top=false;
        }
        public static AlertDialog.Builder Show(Context context, String Message, boolean TopMust) {
            Close();
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(context);
            }

            builder.setTitle("Uyarı")
                    .setMessage(Message)
                    .setCancelable(TopMust)
                    .setPositiveButton("kapat",null)
                    .show();
            return  builder;
        }
        public static AlertDialog.Builder Dialog(Context context, String Message, boolean TopMust) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(context);
            }

            builder.setTitle("Uyarı")
                    .setMessage(Message)
                    .setCancelable(TopMust);
            return  builder;
        }
        public static void Close() {
            try
            {
                gdialog.dismiss();
            } catch (Exception e) {
            }
            try
            {
                ldialog.dialog.dismiss();
            } catch (Exception e) {
            }
        }

        public static void ShowLoading() {
            ShowLoading(null);
        }
        public static void ShowLoading(@Nullable Activity context) {
            if (context==null)context=activity;
            MessageBox.Close();
            ldialog = Dialog.Loading(context);
            ldialog.dialog.show();
            ldialog.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }
    //endregion
    //region TARİH
    public static class Tarih {
        public static String dateSeparator=".";

        public static void Date(@Nullable final OnDateSelectedListener listener) {
            Date(activity,"",listener);
        }
        public static void Date(String defdate,@Nullable final OnDateSelectedListener listener) {
            Date(activity,defdate,listener);
        }
        public static void Date(Activity activity, String defdate,@Nullable final OnDateSelectedListener listener) {
            Calendar mcurrentTime = Calendar.getInstance();
            int year = mcurrentTime.get(Calendar.YEAR);
            int month = mcurrentTime.get(Calendar.MONTH);
            int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
            if(defdate.length()==10)
            {
                try
                {
                    String[] s =defdate.split(Pattern.quote(dateSeparator));
                    day= Integer.parseInt(s[0]);
                    month=(Integer.parseInt(s[1])-1);
                    year= Integer.parseInt(s[2]);
                }catch (Exception e){ year = mcurrentTime.get(Calendar.YEAR);
                    month = mcurrentTime.get(Calendar.MONTH);
                    day = mcurrentTime.get(Calendar.DAY_OF_MONTH);}
            }
            DatePickerDialog datePicker;
            datePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    //tview.setText( year +"-"+((monthOfYear+1)<10?"0"+(monthOfYear+1):(monthOfYear+1))+"-"+(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth) );
                    if (listener!=null)
                    {
                        listener.onDataSelectedListener((""+(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth)+dateSeparator+((monthOfYear+1)<10?"0"+(monthOfYear+1):(monthOfYear+1))+dateSeparator+year));
                    }
                }
            },year,month,day);
            datePicker.setTitle("Tarih Seçiniz");
            datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);
            datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePicker);
            datePicker.show();
        }
        public static String getDate() {
            Calendar mcurrentTime = Calendar.getInstance();
            int year = mcurrentTime.get(Calendar.YEAR);
            int month = mcurrentTime.get(Calendar.MONTH);
            int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
            return (""+(day<10?"0"+day:day)+dateSeparator+((month+1)<10?"0"+(month+1):(month+1))+dateSeparator+year);
        }


    }
    //endregion
    //region INTERFACES
    public  interface  OnDateSelectedListener {
        void onDataSelectedListener(String Date);
    }
    //endregion
    //region Yardımcı Methodlar
    public static String NF2Replace(Object ob) {
        return  NF(ob,true,2);
    }
    public static String NF2(Object ob) {
        return  NF(ob,false,2);
    }
    public static String NFReplace(Object ob, int bas) {
        return  NF(ob,true,bas);
    }
    public static String NF(Object ob, int bas) {
        return  NF(ob,false,bas);
    }
    public static String NF(Object ob, boolean Replace, int bas) {
        double d =0;
        try{d= Double.parseDouble(ob.toString().replace(",","."));}
        catch (Exception e){}
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(bas);
        nf.setMinimumFractionDigits(bas);
        String s = nf.format(d);

        if (Replace && culture_Ondalik_ayrac.equals("."))
            return  s.replace(".","").replace(",",".");
        else if (Replace && culture_Ondalik_ayrac.equals(","))
            return  s.replace(",","").replace(".",",");
        else
            return  s;

    }


    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DENSITY_DEFAULT);
        return px;
    }
    public static float convertPixelToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
    public static void FullScreen(Activity mdı) {
        mdı.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public static void  closeKeyboard()
    {
        closeKeyboard(yugi.activity);
    }
    public static void closeKeyboard(Activity activity) {
        View view =  activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Log.i("|G|gizModule","closeKeyboard "+ activity.getLocalClassName());
        // Pos.ActiveContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //  if(!((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).isAcceptingText())
        //       ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }
    //endregion
    //region HTTP

    private static httpHata isException(int statusCode) {
        httpHata Hata = new httpHata();
        Hata.HataKodu=statusCode;
        Log.e("|G|gzm|statusCode", "" + statusCode);
        switch (statusCode) {
            case 400:
                Hata.isException = true;
                Hata.HataAciklama="Sunucuya Yapılan İstek Hatalıdır";
                break;
            case 401:
                Hata.isException = true;
                Hata.HataAciklama="Oturumunuzun Süresi Dolmuş! Lütfen Tekrar Giriş Yapınız.";
                break;
            case 404:
                Hata.isException = true;
                Hata.HataAciklama="İstek Yapılan Kaynak Veya Sayfa Bulunamadı! Lütfen Server Adresini Doğru Girdiğinizden Emin Olun!";
                break;
            case 408:
                Hata.isException = true;
                Hata.HataAciklama="İstek Zaman Aşımına Uğradı! Lütfen İnternet Bağlantınızı Kontrol Edin.";
                break;
            case 410:
                Hata.isException = true;
                Hata.HataAciklama="Ulaşmaya Çalıştığınız Sayfa Veya Kaynak Artık Mevcut Değil!";
                break;
            case 413:
                Hata.isException = true;
                Hata.HataAciklama="İsteğin boyutu çok büyük olduğu için işlenemedi!";
                break;
            case 414:
                Hata.isException = true;
                Hata.HataAciklama="İstek Adresi Fazla Uzun!";
                break;
            default:
                Hata.isException = false;
                Hata.HataAciklama="Success";
                Hata.HataKodu=0;
                break;
        }
        return Hata;
    }
    public static httpHata GET(String url) {
        InputStream inputStream = null;
        String result = "";
        httpHata hata=null;
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);

            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Authorization","bearer "+getToken());
            if (addHttpHedaerDeviceInfo && myDevice != null)
                httpPost.setHeader("ziraDeviceInfo",myDevice.getJson());
            if (ConstHttpHeader != null)
                httpPost.setHeader("ConstHttpHeader",DataTable.ToTable(ConstHttpHeader).getJsonData(0).replace("\n",""));
            if (TempHttpHeader != null)
                httpPost.setHeader("ConstHttpHeader",DataTable.ToTable(TempHttpHeader).getJsonData(0).replace("\n",""));
            //  httpPost.setHeader("host", gzm.TestLink);
            //httpPost.setHeader("Authorization", gzm.Setup.getUserToken());
            HttpResponse httpResponse = httpclient.execute(httpPost);
            StatusLine st= httpResponse.getStatusLine();
            hata = isException(st.getStatusCode());
            if(!hata.isException || st.getStatusCode()==400) {
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null) {
                    hata.Data = convertInputStreamToString(inputStream);
                    if (st.getStatusCode() == 400)
                    {
                        DataTable dt = new DataTable(hata.Data);
                        hata.HataAciklama = (dt.get(0, "Message").equals("")?dt.get(0, "error_description"):dt.get(0, "Message"));
                    }
                }
                else
                    hata.Data = "Beklenmeyen Bir Hata Oluştu!";
            }
            else
            {
                result=hata.HataAciklama;
            }

        }
        catch (Exception e)
        {
            hata = new httpHata();
            hata.HataAciklama = "Beklenmeyen Bir Hata Oluştu!";
            hata.Data="{\n" +
                    "  \"HatKodu\":\""+600+"\",\n" +
                    "  \"HataAciklama\":\" Beklenmeyen Bir Hata Oluştu! : \n "+e.getMessage()+" \"\n" +
                    "}";
            Log.d("InputStream", e.getLocalizedMessage());
            result=e.getLocalizedMessage();
        }

        // 11. return result
        Log.e("|G|gizGetResult",url+ " | "+hata.Data);

        TempHttpHeader = null;
        return hata;
    }

    public static httpHata POST(String url, List<PostBody> hedars, List<PostBody> bodys) {
        InputStream inputStream = null;
        httpHata hata=null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            for(int i =0;i<bodys.size();i++)
            {
                if(i>0) json+="&";
                json+=bodys.get(i).Name+"="+bodys.get(i).Value;
            }
            StringEntity se = new StringEntity(json, HTTP.UTF_8);
            httpPost.setEntity(se);
            for(int i =0;i<hedars.size();i++)
            {
                httpPost.setHeader(hedars.get(i).Name, hedars.get(i).Value);
            }
            if (addHttpHedaerDeviceInfo && myDevice != null)
                httpPost.setHeader("ziraDeviceInfo",myDevice.getJson());
            if (ConstHttpHeader != null)
                httpPost.setHeader("ConstHttpHeader",DataTable.ToTable(ConstHttpHeader).getJsonData(0).replace("\n",""));
            if (TempHttpHeader != null)
                httpPost.setHeader("ConstHttpHeader",DataTable.ToTable(TempHttpHeader).getJsonData(0).replace("\n",""));

            HttpResponse httpResponse = httpclient.execute(httpPost);
            StatusLine st= httpResponse.getStatusLine();
            hata = isException(st.getStatusCode());
            if(!hata.isException || st.getStatusCode()==400) {
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null) {
                    hata.Data = convertInputStreamToString(inputStream);
                    if (st.getStatusCode() == 400)
                    {
                        DataTable dt = new DataTable(hata.Data);
                        hata.HataAciklama = (dt.get(0, "Message").equals("")?dt.get(0, "error_description"):dt.get(0, "Message"));
                    }
                    Log.e("|G|gizPOST/Result",url+ " | "+hata.Data);
                }
                else
                    hata.Data = "Beklenmeyen Bir Hata Oluştu!";
            }

        }
        catch (Exception e)
        {
            hata = new httpHata();
            hata.HataAciklama = "Beklenmeyen Bir Hata Oluştu!";
            hata.Data="{\n" +
                    "  \"HatKodu\":\""+600+"\",\n" +
                    "  \"HataAciklama\":\" Beklenmeyen Bir Hata Oluştu! : \n "+e.getMessage()+" \"\n" +
                    "}";
            Log.d("InputStream", e.getLocalizedMessage());
            Log.e("|G|gizPOST/Result",url+ " | "+hata.HataAciklama);
        }
        TempHttpHeader=null;
        return hata;
    }
    public static httpHata POST(String url, List<PostBody> hedars, String Json) {
        InputStream inputStream = null;
        String result = "";
        httpHata hata=null;
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            Log.e("|G|POSTJSON",Json.toString());
            StringEntity se = new StringEntity(Json.toString(), HTTP.UTF_8);
            se.setContentType("application/json");
            httpPost.setEntity(se);
            for(int i =0;i<hedars.size();i++)
            {

                httpPost.setHeader(hedars.get(i).Name, hedars.get(i).Value);
            }
            if (addHttpHedaerDeviceInfo && myDevice != null)
                httpPost.setHeader("ziraDeviceInfo",myDevice.getJson());
            if (ConstHttpHeader != null)
                httpPost.setHeader("ConstHttpHeader",DataTable.ToTable(ConstHttpHeader).getJsonData(0).replace("\n",""));
            if (TempHttpHeader != null)
                httpPost.setHeader("ConstHttpHeader",DataTable.ToTable(TempHttpHeader).getJsonData(0).replace("\n",""));

            HttpResponse httpResponse = httpclient.execute(httpPost);
            StatusLine st= httpResponse.getStatusLine();
            hata = isException(st.getStatusCode());
            if(!hata.isException || st.getStatusCode()==400) {
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null)
                {
                    hata.Data = convertInputStreamToString(inputStream);
                    if (st.getStatusCode() == 400)
                    {
                        DataTable dt = new DataTable(hata.Data);
                        hata.HataAciklama = (dt.get(0, "Message").equals("")?dt.get(0, "error_description"):dt.get(0, "Message"));
                    }
                    Log.e("|G|gizPOST/Result",url+ " | "+hata.Data);
                }
                else
                    hata.Data = "Beklenmeyen Bir Hata Oluştu!";
            }

        }
        catch (Exception e)
        {

            hata = new httpHata();
            hata.HataAciklama = "Beklenmeyen Bir Hata Oluştu!";
            hata.Data="{\n" +
                    "  \"HatKodu\":\""+600+"\",\n" +
                    "  \"HataAciklama\":\" Beklenmeyen Bir Hata Oluştu! : \n "+e.getMessage()+" \"\n" +
                    "}";
            Log.d("InputStream", e.getLocalizedMessage());
        }
        Log.e("|G|gizPOST/Result",url+ " | "+hata.Data);
        TempHttpHeader=null;
        return hata;
    }

    public static httpHata DELETE(String url, List<PostBody> hedars, String Json) {
        InputStream inputStream = null;
        String result = "";
        httpHata hata=null;
        try {

            HttpClient httpclient = new DefaultHttpClient();


            HttpPost httpPost = new HttpPost(url);
            Log.e("|G|POSTJSON",Json.toString());
            JSONObject job = new JSONObject();
            StringEntity se = new StringEntity(Json.toString(), HTTP.UTF_8);
            se.setContentType("application/json");
            httpPost.setEntity(se);
            for(int i =0;i<hedars.size();i++)
            {

                httpPost.setHeader(hedars.get(i).Name, hedars.get(i).Value);
            }
            if (addHttpHedaerDeviceInfo && myDevice != null)
                httpPost.setHeader("ziraDeviceInfo",myDevice.getJson());
            if (ConstHttpHeader != null)
                httpPost.setHeader("ConstHttpHeader",DataTable.ToTable(ConstHttpHeader).getJsonData(0).replace("\n",""));
            if (TempHttpHeader != null)
                httpPost.setHeader("ConstHttpHeader",DataTable.ToTable(TempHttpHeader).getJsonData(0).replace("\n",""));

            HttpResponse httpResponse = httpclient.execute(httpPost);
            StatusLine st= httpResponse.getStatusLine();
            hata = isException(st.getStatusCode());
            if(!hata.isException || st.getStatusCode()==400) {
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null)
                {
                    hata.Data = convertInputStreamToString(inputStream);
                    if (st.getStatusCode() == 400)
                    {
                        DataTable dt = new DataTable(hata.Data);
                        hata.HataAciklama = (dt.get(0, "Message").equals("")?dt.get(0, "error_description"):dt.get(0, "Message"));
                    }
                    Log.e("|G|gizPOST/Result",url+ " | "+hata.Data);
                }
                else
                    hata.Data = "Beklenmeyen Bir Hata Oluştu!";
            }

        }
        catch (Exception e)
        {

            hata = new httpHata();
            hata.HataAciklama = "Beklenmeyen Bir Hata Oluştu!";
            hata.Data="{\n" +
                    "  \"HatKodu\":\""+600+"\",\n" +
                    "  \"HataAciklama\":\" Beklenmeyen Bir Hata Oluştu! : \n "+e.getMessage()+" \"\n" +
                    "}";
            Log.d("InputStream", e.getLocalizedMessage());
        }
        Log.e("|G|gizPOST/Result",url+ " | "+hata.Data);
        TempHttpHeader=null;
        return hata;
    }



    public static class gizGET extends AsyncTask<String, Void, String> {
        public static String _JSON = "";
        public  httpHata myResponse;
        public static DataTable dataTable= new DataTable("");
        @Override
        protected String doInBackground(String... urls) {
            JSONObject jsonObject;
            myResponse=GET(urls[0]);
            String jsonData = myResponse.Data;
            if(!myResponse.isException)
            {
                try {
                    _JSON = jsonData;
                    Log.e("|G|JSONDATA", _JSON);
                    dataTable = new DataTable(_JSON);
                    if (dataTable.Rows.size() > 0)
                    {
                        if (dataTable.get(0, "HataKodu").equals("") || dataTable.get(0, "HataKodu").equals("0")) {
                            myResponse.isException=false;
                            return "Success";
                        }
                        else
                        {
                            myResponse.HataAciklama=dataTable.get(0, "HataAciklama");
                            myResponse.isException=true;
                            return dataTable.get(0, "HataAciklama");
                        }
                    } else {
                        return myResponse.HataAciklama;
                    }

                } catch (Exception e) {
                    return "";
                }
            }
            else
            {
                _JSON="{\n" +
                        "  \"HatKodu\":\""+myResponse.HataKodu+"\",\n" +
                        "  \"HataAciklama\":\" "+myResponse.HataAciklama+" \"\n" +
                        "}";
                dataTable = new DataTable(_JSON);
                return myResponse.HataAciklama;
            }
        }
    }
    public static class gizPOST extends AsyncTask<String, Void, String> {
        List<PostBody> Hedars;
        List<PostBody> Bodys = new ArrayList<>();
        public static  httpHata myResponse;
        boolean usejson=false;
        String prmJson;
        public static String _JSON = "";
        public static  DataTable dataTable= new DataTable("");
        public gizPOST(List<PostBody> _Hedars, List<PostBody> _Bodys)
        {
            Hedars=_Hedars;
            Bodys=_Bodys;
            usejson=false;
        }
        public  gizPOST(List<PostBody> _Hedars, String Json)
        {
            Hedars=_Hedars;
            prmJson=Json;
            usejson=true;
        }
        public gizPOST(List<PostBody> _Hedars) {
            Hedars=_Hedars;
            usejson=false;
        }
        public void addBody(String key, String value)
        {
            Bodys.add(new PostBody(key,value));
        }

        @Override
        protected String doInBackground(String... urls) {

            String jsonData;

            if(usejson)
                myResponse = POST(urls[0], Hedars, prmJson);
            else
                myResponse= POST(urls[0],Hedars,Bodys);

            jsonData = myResponse.Data;
            try {
                _JSON = jsonData;
                dataTable = new DataTable(_JSON);
                if (dataTable.Rows.size() > 0)
                {
                    if((dataTable.get(0,"HataKodu").equals("0")||dataTable.get(0,"HataKodu").equals("")) && dataTable.get(0,"error").equals("") )
                    {
                        return "Success";
                    }
                    else
                    {
                        return (dataTable.get(0,"HataAciklama").equals("")?dataTable.get(0,"error_description"):dataTable.get(0,"HataAciklama"));
                    }
                }
                else
                {
                    _JSON="{\n" +
                            "  \"HatKodu\":\""+myResponse.HataKodu+"\",\n" +
                            "  \"HataAciklama\":\" "+myResponse.HataAciklama+" \"\n" +
                            "}";
                    dataTable = new DataTable(_JSON);
                    return myResponse.HataAciklama;
                }

            }
            catch (Exception e) {
                return "";
            }
        }
    }
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    public static class httpHata {
        public int HataKodu;
        public String HataAciklama,Data;
        public boolean isException;
    }
    public static class PostBody {
        public PostBody(String _Name, Object _Value) {
            Name=_Name;Value= String.valueOf(_Value);
        }
        String Name,Value;
    }
    public static List<PostBody> PostHeder() {
        List<PostBody> head = new ArrayList<>();
        head.add(new PostBody("Content-Type","application/x-www-form-urlencoded"));
        head.add(new PostBody("Accept","application/json"));
        head.add(new PostBody("Authorization","bearer "+getToken()));
        return head;
    }
    public static List<PostBody> PostHeder2() {
        List<PostBody> head = new ArrayList<>();
        head.add(new PostBody("Content-Type","application/json"));
        head.add(new PostBody("Authorization","bearer "+getToken()));
        return head;
    }
    public static void setToken(String access_token) {

        SetSetup(activity,"Token",access_token);
    }
    public  static  String getToken()
    {
        return  GetSetup(activity,"Token");
    }
    public static String GetSetup(Context context, Object Setup_name) {
        return GetSetup(context,Setup_name,"");
    }

    private static String setupTag = "HobiSatis";

    public static void setSetupTag(String setupTag) {
        yugi.setupTag = setupTag;
    }

    public static String getSetupTag() {
        return setupTag;
    }

    public static String GetSetup(String Setup_name, String Defult) {  return GetSetup(yugi.activity,Setup_name,Defult); }
    public static String GetSetup(Context context, Object Setup_name,String Defult) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            String str;
            str = sharedPreferences.getString(getSetupTag()+"." + Setup_name, null);
            if (str.isEmpty() || str.length()==0)
                return Defult;
            else return str;
        } catch (Exception e) {
            return Defult;
        }
    }
    public static void SetSetup(String Setup_name, Object Value) { SetSetup(yugi.activity,Setup_name,Value); }
    public static void SetSetup(Context context,String Setup_name, Object Value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getSetupTag()+"." + Setup_name, String.valueOf(Value));
        editor.commit();
    }


    //endregion
    //region NUMPAD
    public static class mNumpad implements View.OnClickListener {
        //region DECLARE
        Dialog.mDialog dialog;
        Activity activity;
        int decimal = 2;
        String[] _p;

        public String p1="0",p2="0",p3="0",Currency="TL";

        public Double Ucret=0.0,totalUcret=0.0;

        boolean virgul=false,clear = true;

        public View view;
        TextView txt_ucret;
        TextView npTotal;
        TextView npBack;
        TextView np1;
        TextView np2;
        TextView np3;
        TextView np4;
        TextView np5;
        TextView np6;
        TextView np7;
        TextView np8;
        TextView np9;
        TextView npclear;
        TextView np0;
        TextView npvirgul;
        TextView lu_kapat;
        TextView npOk;
        //endregion
        public void Init() {



            txt_ucret=(TextView)view.findViewById(R.id.txt_ucret);
            npTotal=(TextView)view.findViewById(R.id.npTotal);
            npBack=(TextView)view.findViewById(R.id.npBack);
            np1=(TextView)view.findViewById(R.id.np1);
            np2=(TextView)view.findViewById(R.id.np2);
            np3=(TextView)view.findViewById(R.id.np3);
            np4=(TextView)view.findViewById(R.id.np4);
            np5=(TextView)view.findViewById(R.id.np5);
            np6=(TextView)view.findViewById(R.id.np6);
            np7=(TextView)view.findViewById(R.id.np7);
            np8=(TextView)view.findViewById(R.id.np8);
            np9=(TextView)view.findViewById(R.id.np9);
            npclear=(TextView)view.findViewById(R.id.npclear);
            np0=(TextView)view.findViewById(R.id.np0);
            npvirgul=(TextView)view.findViewById(R.id.npvirgul);
            lu_kapat=(TextView)view.findViewById(R.id.ly_kapat);
            npOk=(TextView)view.findViewById(R.id.npOk);



            _p= new String[decimal+1];


            np1.setOnClickListener(this);
            np2.setOnClickListener(this);
            np3.setOnClickListener(this);
            np4.setOnClickListener(this);
            np5.setOnClickListener(this);
            np6.setOnClickListener(this);
            np7.setOnClickListener(this);
            np8.setOnClickListener(this);
            np9.setOnClickListener(this);
            np0.setOnClickListener(this);
            npvirgul.setOnClickListener(this);
            npclear.setOnClickListener(this);
            npTotal.setOnClickListener(this);
            npBack.setOnClickListener(this);
            npOk.setOnClickListener(this);
            lu_kapat.setOnClickListener(this);




        }
        public INTERFACES.OnNumpadListener mlistener;

        public  mNumpad(Activity ma,int contentID) {
            activity=ma;
            dialog = new Dialog.mDialog(activity,contentID,false);
            view=dialog.view;
            dialog.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Init();
        }
        public  mNumpad(Activity ma) {
            this(ma,R.layout.ly_numpad);
        }

        public  mNumpad(Activity ma,INTERFACES.OnNumpadListener listener) {
            this(ma,R.layout.ly_numpad,listener);
        }
        public  mNumpad(Activity ma,int contentID,INTERFACES.OnNumpadListener listener) {
            this(ma,contentID);
            mlistener=listener;
        }

        public void setDecimal(int decimal) {
            this.decimal = decimal;
            _p = new String[decimal+1];
            UcretToString();
        }

        public void StringToUcret()
        {
            //String s = p1+"."+p2+p3;
            String s = _p[0]+".";
            for (int i = 1; i <_p.length;i++)
                s+=_p[i];


            Ucret=Double.parseDouble(s);
            UcretToString();
        }
        public void UcretToString() {
            String sucret = yugi.NFReplace(Ucret,decimal).replace(".","-");
            String s[] = sucret.split("-");
            p1=s[0];
            p2=s[1].substring(0,1);
            p3=s[1].substring(1,2);
            _p[0] = s[0];
            for (int i = 1 ; i< _p.length; i++)
            {
                _p[i] = s[1].substring(i-1,i);
            }
            txt_ucret.setText(yugi.NFReplace(Ucret,decimal)+" "+Currency);
        }


        @Override
        public void onClick(View v)
        {
                if (clear)
                {
                    for (String p : _p) { p = "0"; }
                    p1="0";
                    p2=p1;
                    p3=p1;
                    clear=false;
                }
            try
            {
                switch (v.getTag().toString())
                {
                    case "artipara":
                        Ucret+=Double.parseDouble(((TextView)v).getText().toString());
                        UcretToString();
                        break;
                    case "numpad":
                        String s = ((TextView)v).getText().toString();
                        if (virgul)
                        {

                            for (int i = 1; i< _p.length;i++)
                            {
                                if (_p[i].equals("0"))
                                {
                                    _p[i]= s;
                                    break;
                                }
                            }

                            if(p2.equals("0"))p2=s;
                            else if(p3.equals("0"))p3=s;
                            StringToUcret();
                        }
                        else
                        {
                            p1+=s;
                            _p[0]+= s;
                            StringToUcret();
                        }
                        break;

                }
            }
            catch (Exception e){}
            finally {
                int i = v.getId();
                if (i == R.id.npvirgul)
                {
                    String p_= _p[0];
                    for (String p : _p) { p = "0"; }
                    _p[0] = p_;

                    StringToUcret();
                    virgul = true;

                } else if (i == R.id.npclear) {
                    Ucret = 0.0;
                    UcretToString();
                    virgul = false;

                } else if (i == R.id.npTotal) {
                    Ucret = totalUcret;
                    UcretToString();

                } else if (i == R.id.npBack) {
                    if (true)
                    {
                        virgul = true;

                        for (int k = _p.length -1 ;k >= 0 ;k--)
                        {
                            if (!_p[k].equals("0"))
                            {

                               if (k == 0 )
                               {
                                   virgul = false;
                                   if (_p[k].length() > 1) _p[k] = _p[k].substring(0, _p[k].length() - 1);
                                   else _p[k] = "0";
                               }
                               else
                               {
                                   _p[k] = "0";
                               }
                                break;
                            }
                        }

                        if (!p3.equals("0")) {
                            p3 = "0";
                        } else if (!p2.equals("0")) {
                            p2 = "0";
                        }
                        else
                        {
                            virgul = false;
                            if (p1.length() > 1) p1 = p1.substring(0, p1.length() - 1);
                            else p1 = "0";
                        }
                        StringToUcret();
                    }
                    else {
                        if (p1.length() > 1) p1 = p1.substring(0, p1.length() - 1);
                        else p1 = "0";
                        StringToUcret();
                    }

                } else if (i == R.id.npOk) {
                    if (mlistener != null) mlistener.onResultOK(totalUcret, Ucret);
                    dialog.dialog.dismiss();

                }
                else if(i==R.id.ly_kapat)
                {
                    dialog.dialog.dismiss();
                }
            }
        }
        public void Show() {
            npTotal.setText(yugi.NFReplace(totalUcret,decimal)+" "+Currency);
            UcretToString();

            dialog.dialog.show();
        }




    }
    //endregion
    //region ImageLoader
    public static   void setConfig(vActivity a) {
        activity = a;
        if (imageLoader == null || options == null) {
            imageLoader = ImageLoader.getInstance();
            ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(activity).defaultDisplayImageOptions(options).imageDownloader(new AuthDownloader(activity)).build());
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading)
                    .showImageForEmptyUri(null)
                    .showImageOnFail(null)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            imageLoader = ImageLoader.getInstance();
        }
        switch (Locale.getDefault().getLanguage().toLowerCase())
        {
            case "en":
                culture_Ondalik_ayrac=",";
                break;
            default:
                culture_Ondalik_ayrac=".";
                break;
        }
    }
    //endregion
    //region Print
    public static void Print(String error) {Print(activity,"i","",error);}
    public static void Print(String type,String error) {Print(activity,type,"",error);}
    public static void Print(String type,String tag,String error) {Print(activity,type,tag,error);}
    public static void Print(Activity activity,String type,String tag,String error) {
        if (!TestMode) return;
        String form = "|->"+tag;
        if (activity != null)
            form=activity.getLocalClassName()+"\n";
        if (tag.length()>0)
            form+=tag+"\n";

        form += error;
        tag="|GizLog|";
        switch (type.toLowerCase())
        {
            case "e":
                Log.e(tag,form);
                break;
            case "i":
                Log.i(tag,form);
                break;
            default:
                Log.d(tag,form);
                break;
        }
    }
    //endregion

    public static Field[] getDeclaredFields(Class clazz, boolean recursively) {
        List<Field> fields = new LinkedList<Field>();
        Field[] declaredFields = clazz.getDeclaredFields();
        Collections.addAll(fields, declaredFields);

        Class superClass = clazz.getSuperclass();

        if(superClass != null && recursively) {
            Field[] declaredFieldsOfSuper = getDeclaredFields(superClass, recursively);
            if(declaredFieldsOfSuper.length > 0)
                Collections.addAll(fields, declaredFieldsOfSuper);
        }

        return fields.toArray(new Field[fields.size()]);
    }
    public static Field[] getAnnotatedDeclaredFields(Class clazz, Class<? extends Annotation> annotationClass, boolean recursively) {
        Field[] allFields = getDeclaredFields(clazz, recursively);
        List<Field> annotatedFields = new LinkedList<Field>();

        for (Field field : allFields) {
            if(field.isAnnotationPresent(annotationClass))
                annotatedFields.add(field);
        }

        return annotatedFields.toArray(new Field[annotatedFields.size()]);
    }



    public static String Join(String string , Object... params) {
        try {
            int i = 0;
            for (Object item : params)
            {
                if (item != null)
                {
                    if (item.getClass() == Object[].class)
                    {
                        for (Object item2 : ((Object[]) item))
                        {
                            if (item2 != null)
                                string = string.replace("{" + i + "}", String.valueOf(item2));
                            else
                                string = string.replace("{" + i + "}", String.valueOf("null"));
                            i++;
                        }
                    }
                    else
                    {
                        string = string.replace("{" + i + "}", String.valueOf(item));
                        i++;
                    }
                }
                else
                {
                    string = string.replace("{" + i + "}", String.valueOf("null"));
                    i++;
                }
            }


        } catch (Exception ex) {
        }
        return string;
    }


    public static boolean addHttpHedaerDeviceInfo=true;

    public static String DeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String DeviceNamee() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


    public static MobileDevice myDevice = new MobileDevice();

    public static class  MobileDevice
    {
        public  String DeviceID ;
        public  String DeviceName;
        public  String FireBaseToken;
        public  int CarikartID;


        public  static MobileDevice Build() {
            MobileDevice device = new MobileDevice();

            device.DeviceID = DeviceID(yugi.activity);
            device.DeviceName = DeviceNamee();
            return device;
        }

        public String getJson()
        {
            return  DataTable.ToTable(this).getJsonData(0).replace("\n","");
        }


    }

    public static Object ConstHttpHeader = null,TempHttpHeader = null;



}

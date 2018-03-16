package library.yugisoft.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    //endregion
    //region APP

    public static class vActivity extends AppCompatActivity {

        public  boolean isLoad=false;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            setConfig(this);
            super.onCreate(savedInstanceState);
            isLoad=false;
        }

        @Override
        protected void onResume() {
            setConfig(this);
            super.onResume();
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
        public static void ShowTop(Context context, String message)
        {
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
                String[] s =defdate.split(dateSeparator);
                day= Integer.parseInt(s[0]);
                month=(Integer.parseInt(s[1])-1);
                year= Integer.parseInt(s[2]);
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
        nf.setMaximumFractionDigits(bas);      // Burada virgülden sonra maksimum 2 karakter olacağı belirtiliyor.
        nf.setMinimumFractionDigits(bas);      // Burada virgülden sonra minimum 2 karakter olacağı belirtiliyor.
        String s = nf.format(d);

        if (Replace)
            return  s.replace(".","").replace(",",".");
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
    public static void FullScreen(Activity mdı)
    {
        mdı.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
            JSONObject job = new JSONObject();
            StringEntity se = new StringEntity(Json.toString(), HTTP.UTF_8);
            se.setContentType("application/json");
            httpPost.setEntity(se);
            for(int i =0;i<hedars.size();i++)
            {

                httpPost.setHeader(hedars.get(i).Name, hedars.get(i).Value);
            }

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
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            String str;
            str = sharedPreferences.getString("HobiSatis." + Setup_name, null);
            if (str.isEmpty())
                return "";
            else return str;
        } catch (Exception e) {
            return "";
        }
    }
    public static void SetSetup(Context context,String Setup_name, Object Value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("HobiSatis." + Setup_name, String.valueOf(Value));
        editor.commit();
    }


    //endregion
    //region NUMPAD
    public static class mNumpad implements View.OnClickListener {
        //region DECLARE
        Dialog.mDialog dialog;
        Activity activity;
        public String p1="0",p2="0",p3="0",Currency="";
        public Double Ucret=0.0,totalUcret=0.0;
        boolean virgul=false;

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




        }
        public INTERFACES.OnNumpadListener mlistener;

        public  mNumpad(Activity ma) {
            activity=ma;
            dialog = new Dialog.mDialog(activity,R.layout.ly_numpad,false);
            view=dialog.view;
            Init();
        }

        public  mNumpad(Activity ma,INTERFACES.OnNumpadListener listener) {
            this(ma);
            mlistener=listener;
        }


        public void StringToUcret()
        {
            String s = p1+"."+p2+p3;
            Ucret=Double.parseDouble(s);
            UcretToString();
        }
        public void UcretToString() {
            String sucret = yugi.NF2Replace(Ucret).replace(".","-");
            String s[] = sucret.split("-");
            p1=s[0];
            p2=s[1].substring(0,1);
            p3=s[1].substring(1,2);
            txt_ucret.setText(yugi.NF2Replace(Ucret)+" "+Currency);
        }


        @Override
        public void onClick(View v)
        {

            try
            {
                switch (v.getTag().toString())
                {
                    case "artipara":
                        Ucret+=Double.parseDouble(((TextView)view).getText().toString());
                        UcretToString();
                        break;
                    case "numpad":
                        String s = ((TextView)view).getText().toString();
                        if (virgul)
                        {
                            if(p2.equals("0"))p2=s;
                            else if(p3.equals("0"))p3=s;
                            StringToUcret();
                        }
                        else
                        {
                            p1+=s;
                            StringToUcret();
                        }
                        break;

                }
            }
            catch (Exception e){}
            finally {
                int i = v.getId();
                if (i == R.id.npvirgul) {
                    p2 = "0";
                    p3 = "0";
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
                    if (true) {
                        virgul = true;
                        if (!p3.equals("0")) {
                            p3 = "0";
                        } else if (!p2.equals("0")) {
                            p2 = "0";
                        } else {
                            virgul = false;
                            if (p1.length() > 1) p1 = p1.substring(0, p1.length() - 1);
                            else p1 = "0";
                        }
                        StringToUcret();
                    } else {
                        if (p1.length() > 1) p1 = p1.substring(0, p1.length() - 1);
                        else p1 = "0";
                        StringToUcret();
                    }

                } else if (i == R.id.npOk) {
                    if (mlistener != null) mlistener.onResultOK(totalUcret, Ucret);
                    dialog.dialog.dismiss();

                }
            }
        }
        public void Show() {
            npTotal.setText(yugi.NF2Replace(totalUcret)+" "+Currency);
            UcretToString();
            dialog.DialogMax();
            dialog.dialog.show();
        }




    }
    //endregion
    //region ImageLoader
    public static   void setConfig(vActivity a)
    {
        activity =a;
        if(imageLoader==null|| options==null)
        {
            imageLoader = ImageLoader.getInstance();
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity));
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading2)
                    .showImageForEmptyUri(R.drawable.loading2)
                    .showImageOnFail(R.drawable.loading2)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            imageLoader = ImageLoader.getInstance();
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
}

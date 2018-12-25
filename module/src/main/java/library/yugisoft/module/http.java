package library.yugisoft.module;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;


public class http
{


    public static class Response {
        public int HataKodu;
        public String HataAciklama, Data;
        public boolean isException;

        @Override
        public String toString() {
            return Data;
        }

        public String getMessage()
        {
            return HataKodu < 0 ? "Server Bağlantısı Kurulumadı." : HataAciklama;
        }

    }
    public static class Headers {
        public static String DeviceInfoKey = "ziraDeviceInfo",ConstInfoKey="Config";


        public static void urlencoded(HttpRequestBase req) {
            req.setHeader("Content-Type", "application/x-www-form-urlencoded");
            req.setHeader("Accept", "application/json");
            req.setHeader("Authorization", "bearer " + yugi.getToken());
        }

        public static void json(HttpRequestBase req) {
            req.setHeader("Content-Type", "application/json");
            req.setHeader("Accept", "application/json");
            req.setHeader("Authorization", "bearer " + yugi.getToken());
        }

        private static void DeviceInfo(HttpRequestBase req) {
            try
            {
                if (yugi.addHttpHedaerDeviceInfo && yugi.myDevice != null)
                    req.setHeader(DeviceInfoKey, yugi.myDevice.getJson());
            }
            catch (Exception ex)
            {}
        }

        private static void ConstHttpHeader(HttpRequestBase req) {
            try
            {
                if (yugi.ConstHttpHeader != null)
                    req.setHeader(ConstInfoKey, DataTable.ToTable(yugi.ConstHttpHeader).getJsonData(0).replace("\n", ""));
            }
            catch (Exception ex)
            {

            }
        }

        private static void TempHttpHeader(HttpRequestBase req) {
            try
            {
                if (yugi.TempHttpHeader != null)
                    req.setHeader("TempHttpHeader", DataTable.ToTable(yugi.TempHttpHeader).getJsonData(0).replace("\n", ""));
            }
            catch (Exception ex)
            {}
        }

        public static void Add(HttpRequestBase req, Hashtable headers) {
            if (headers==null)return;;
            try
            {
                Iterator<String> keys = headers.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();

                    req.setHeader(key, headers.get(key).toString());
                }
            }
            catch (Exception ex)
            {}
        }


    }
    public static class Bodys {
        public static void Add(HttpEntityEnclosingRequestBase req, Hashtable bodys) {
            if (bodys==null)return;;
            try
            {
                Iterator<String> keys = bodys.keySet().iterator();

                String body = "";
                int i = 0;
                while (keys.hasNext())
                {
                    String key = keys.next();
                    if (i > 0) body += "&";
                    body += key + "=" + bodys.get(key).toString();
                    i++;
                }
                StringEntity se = null;
                try {
                    se = new StringEntity(body, HTTP.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                req.setEntity(se);
            }
            catch (Exception Ex)
            {

            }
        }
        public static void Add(HttpEntityEnclosingRequestBase req, String bodys) {

            try
            {
                StringEntity se = new StringEntity(bodys.toString(), HTTP.UTF_8);
                se.setContentType("application/json");
                req.setEntity(se);
            }
            catch (Exception ex)
            {

            }
        }
    }

    public static Response GET(String url, Hashtable headers) {
        Response response = null;
        HttpGet httpGet = new HttpGet(url);
        Headers.urlencoded(httpGet);
        Headers.ConstHttpHeader(httpGet);
        Headers.TempHttpHeader(httpGet);
        Headers.DeviceInfo(httpGet);
        Headers.Add(httpGet, headers);
        response = httpExecute(httpGet,"http-GET");
        yugi.TempHttpHeader = null;
        return response;
    }
    public static Response DELETE(String url, Hashtable headers) {
        Response response = null;
        HttpDelete httpDelete = new HttpDelete(url);
        Headers.urlencoded(httpDelete);
        Headers.ConstHttpHeader(httpDelete);
        Headers.TempHttpHeader(httpDelete);
        Headers.DeviceInfo(httpDelete);
        Headers.Add(httpDelete, headers);
        response = httpExecute(httpDelete,"http-DELETE");
        yugi.TempHttpHeader = null;
        return response;
    }
    public static Response POST(String url,Hashtable body,Hashtable headers) {
        Response response = null;
        HttpPost httpPost = new HttpPost(url);

        //Content Type Belirlendi!
        Headers.urlencoded(httpPost);

        //Headerda Gonderilmesi Gereken Sabit Bilgiler Varmı Kontrol Edildi! var ise eklendi.
        Headers.ConstHttpHeader(httpPost);

        //Headerda Gonderilmesi Gereken Temp Bilgiler Varmı Kontrol Edildi! var ise eklendi.
        Headers.TempHttpHeader(httpPost);

        //Headerda Gonderilmesi Gereken Cihaz Bilgisi Varmı Kontrol Edildi. var ise eklendi.
        Headers.DeviceInfo(httpPost);

        //Headerda Gonderilmesi Gereken Ekxtra Veri Varmı Kontrol Edildi. var ise eklendi.
        Headers.Add(httpPost, headers);

        //Body'e eklenmesi gereken bilgiler eklendi.
        Bodys.Add(httpPost,body);

        response = httpExecute(httpPost,"http-POST");
        yugi.TempHttpHeader = null;
        return response;
    }
    public static Response POST(String url,String body,Hashtable headers) {
        Response response = null;
        HttpPost httpPost = new HttpPost(url);

        //Content Type Belirlendi!
        Headers.json(httpPost);

        //Headerda Gonderilmesi Gereken Sabit Bilgiler Varmı Kontrol Edildi! var ise eklendi.
        Headers.ConstHttpHeader(httpPost);

        //Headerda Gonderilmesi Gereken Temp Bilgiler Varmı Kontrol Edildi! var ise eklendi.
        Headers.TempHttpHeader(httpPost);

        //Headerda Gonderilmesi Gereken Cihaz Bilgisi Varmı Kontrol Edildi. var ise eklendi.
        Headers.DeviceInfo(httpPost);

        //Headerda Gonderilmesi Gereken Ekxtra Veri Varmı Kontrol Edildi. var ise eklendi.
        Headers.Add(httpPost, headers);

        //Body'e eklenmesi gereken bilgiler eklendi.
        Bodys.Add(httpPost,body);

        response = httpExecute(httpPost,"http-POST");
        yugi.TempHttpHeader = null;
        return response;
    }
    public static Response PUT(String url,Hashtable body,Hashtable headers) {
        Response response = null;
        HttpPut httpPut = new HttpPut(url);

        //Content Type Belirlendi!
        Headers.urlencoded(httpPut);

        //Headerda Gonderilmesi Gereken Sabit Bilgiler Varmı Kontrol Edildi! var ise eklendi.
        Headers.ConstHttpHeader(httpPut);

        //Headerda Gonderilmesi Gereken Temp Bilgiler Varmı Kontrol Edildi! var ise eklendi.
        Headers.TempHttpHeader(httpPut);

        //Headerda Gonderilmesi Gereken Cihaz Bilgisi Varmı Kontrol Edildi. var ise eklendi.
        Headers.DeviceInfo(httpPut);

        //Headerda Gonderilmesi Gereken Ekxtra Veri Varmı Kontrol Edildi. var ise eklendi.
        Headers.Add(httpPut, headers);

        //Body'e eklenmesi gereken bilgiler eklendi.
        Bodys.Add(httpPut,body);

        response = httpExecute(httpPut,"http-PUT");

        yugi.TempHttpHeader = null;
        return response;
    }
    public static Response PUT(String url,String body,Hashtable headers) {
        Response response = null;
        HttpPut httpPut = new HttpPut(url);

        //Content Type Belirlendi!
        Headers.json(httpPut);

        //Headerda Gonderilmesi Gereken Sabit Bilgiler Varmı Kontrol Edildi! var ise eklendi.
        Headers.ConstHttpHeader(httpPut);

        //Headerda Gonderilmesi Gereken Temp Bilgiler Varmı Kontrol Edildi! var ise eklendi.
        Headers.TempHttpHeader(httpPut);

        //Headerda Gonderilmesi Gereken Cihaz Bilgisi Varmı Kontrol Edildi. var ise eklendi.
        Headers.DeviceInfo(httpPut);

        //Headerda Gonderilmesi Gereken Ekxtra Veri Varmı Kontrol Edildi. var ise eklendi.
        Headers.Add(httpPut, headers);

        //Body'e eklenmesi gereken bilgiler eklendi.
        Bodys.Add(httpPut,body);

        response = httpExecute(httpPut,"http-PUT");

        yugi.TempHttpHeader = null;
        return response;
    }
    private static Response httpExecute(HttpRequestBase httpGet,String log) {
        String LOG ="httpExecuteResponse \n";
        Response response =null;
        try
        {
            InputStream inputStream = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(httpGet);

            StatusLine st = httpResponse.getStatusLine();

            response = isException(st.getStatusCode());
            if (onAuthenticationFailed!=null && response.HataKodu == 401)
                onAuthenticationFailed.onFailed(response,httpGet.getURI().toURL().toString());
            LOG+= "HttpURL : "+httpGet.getURI().toURL()+"\n";
            LOG+=log+"\n";
            LOG+= "HttpReponseStatusCode : "+st.getStatusCode()+"\n";
            if (!response.isException || st.getStatusCode() == 400)
            {
                try
                {
                    inputStream = httpResponse.getEntity().getContent();
                    if (inputStream != null)
                    {
                        response.Data = convertInputStreamToString(inputStream);
                        if (st.getStatusCode() == 400)
                        {
                            DataTable dt = new DataTable(response.Data);
                            String error = (dt.get(0, "Message").equals("") ? dt.get(0, "error_description") : dt.get(0, "Message"));
                            error = error.length()==0 ? dt.get(0, "error") : error;
                            response.HataAciklama = error.length()>0 ?error : response.HataAciklama;
                        }
                    }
                    else
                    {
                        response.Data = "Beklenmeyen Bir Hata Oluştu!";

                    }
                }
                catch (Exception Ex)
                {

                }
            }
            LOG += "Response : "+(response.Data.length() == 0 ? response.Data : response.getMessage());
            yugi.Print("I", "httpExecuteResponse", LOG);
            return  response;
        }
        catch (Exception ex)
        {
            response = new Response();
            response.HataKodu = -999;
            response.isException = true;
            response.HataAciklama=ex.getMessage();
        }
        yugi.Print("I", "httpExecute", response.Data);
        return  response;
    }


    public interface OnAuthenticationFailed {
        void onFailed(Response response,String url);
    }
    public interface OnHttpResponse {
        void onResponse(Response response);
    }
    public interface OnHttpResponseTable {
        void onResponse(DataTable response);
    }

    private static OnAuthenticationFailed onAuthenticationFailed;
    public static OnAuthenticationFailed getOnAuthenticationFailed() {
        return onAuthenticationFailed;
    }
    public static void setOnAuthenticationFailed(OnAuthenticationFailed AuthenticationFailed) {
        onAuthenticationFailed = AuthenticationFailed;
    }

    //region Private
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    private static Response isException(int statusCode) {

        Response response = new Response();
        response.HataKodu = statusCode;
        response.isException = !(statusCode >= 200 && statusCode < 300);


       switch (statusCode)
       {
       //     case 400:
       //         Hata.isException = true;
       //         Hata.HataAciklama = "Sunucuya Yapılan İstek Hatalıdır";
       //         break;
       //     case 401:
       //         Hata.isException = true;
       //         Hata.HataAciklama = "Oturumunuzun Süresi Dolmuş! Lütfen Tekrar Giriş Yapınız.";
       //         break;
                 case 404:
                     response.isException = true;
                     response.HataAciklama = "İstek Yapılan Kaynak Veya Sayfa Bulunamadı! Lütfen Server Adresini Doğru Girdiğinizden Emin Olun!";
                     break;
       //     case 408:
       //         Hata.isException = true;
       //         Hata.HataAciklama = "İstek Zaman Aşımına Uğradı! Lütfen İnternet Bağlantınızı Kontrol Edin.";
       //         break;
       //     case 410:
       //         Hata.isException = true;
       //         Hata.HataAciklama = "Ulaşmaya Çalıştığınız Sayfa Veya Kaynak Artık Mevcut Değil!";
       //         break;
       //     case 413:
       //         Hata.isException = true;
       //         Hata.HataAciklama = "İsteğin boyutu çok büyük olduğu için işlenemedi!";
       //         break;
       //     case 414:
       //         Hata.isException = true;
       //         Hata.HataAciklama = "İstek Adresi Fazla Uzun!";
       //         break;
       //     default:
       //         Hata.isException = false;
       //         Hata.HataAciklama = "Success";
       //         Hata.HataKodu = 0;
       //         break;
       //
        }
        return response;
    }
    //endregion


    public static class httpGET extends AsyncTask<String,Void,Response> {

        OnHttpResponse onHttpResponse = null;
        Hashtable headers=null;

        public httpGET(OnHttpResponse pOnHttpResponse)
        {
            onHttpResponse=pOnHttpResponse;
        }

        public httpGET(OnHttpResponse pOnHttpResponse,Hashtable pheaders) {
            onHttpResponse=pOnHttpResponse;
            headers=pheaders;
        }

        @Override
        protected Response doInBackground(String... strings)
        {
            if (strings.length>0)
            {
                String Url = strings[0];
                return http.GET(Url,headers);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (onHttpResponse!=null)
                onHttpResponse.onResponse(response);
        }
    }
    public static class httpGETTable extends AsyncTask<String,Void,DataTable> {

        OnHttpResponseTable onHttpResponseTable = null;
        Hashtable headers=null;

        public httpGETTable(OnHttpResponseTable pOnHttpResponse)
        {
            onHttpResponseTable=pOnHttpResponse;
        }

        public httpGETTable(OnHttpResponseTable pOnHttpResponseTable,Hashtable pheaders) {
            onHttpResponseTable=pOnHttpResponseTable;
            headers=pheaders;
        }

        @Override
        protected DataTable doInBackground(String... strings)
        {
            if (strings.length>0)
            {
                String Url = strings[0];
                return new DataTable(http.GET(Url,headers).Data);
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataTable response) {
            super.onPostExecute(response);
            if (onHttpResponseTable!=null)
                onHttpResponseTable.onResponse(response);
        }
    }
    public static class httpDELETE extends AsyncTask<String,Void,Response> {

        OnHttpResponse onHttpResponse = null;
        Hashtable headers=null;

        public httpDELETE(OnHttpResponse pOnHttpResponse)
        {
            onHttpResponse=pOnHttpResponse;
        }

        public httpDELETE(OnHttpResponse pOnHttpResponse,Hashtable pheaders) {
            onHttpResponse=pOnHttpResponse;
            headers=pheaders;
        }

        @Override
        protected Response doInBackground(String... strings)
        {
            if (strings.length>0)
            {
                String Url = strings[0];
                return http.DELETE(Url,headers);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (onHttpResponse!=null)
                onHttpResponse.onResponse(response);
        }
    }
    public static class httpDELETETable extends AsyncTask<String,Void,DataTable> {

        OnHttpResponseTable onHttpResponseTable = null;
        Hashtable headers=null;

        public httpDELETETable(OnHttpResponseTable pOnHttpResponse) {
            onHttpResponseTable=pOnHttpResponse;
        }

        public httpDELETETable(OnHttpResponseTable pOnHttpResponseTable,Hashtable pheaders) {
            onHttpResponseTable=pOnHttpResponseTable;
            headers=pheaders;
        }

        @Override
        protected DataTable doInBackground(String... strings) {
            if (strings.length>0)
            {
                String Url = strings[0];
                return new DataTable(http.DELETE(Url,headers).Data);
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataTable response) {
            super.onPostExecute(response);
            if (onHttpResponseTable!=null)
                onHttpResponseTable.onResponse(response);
        }
    }
    public static class httpPOST extends AsyncTask<String,Void,Response> {

        OnHttpResponse onHttpResponse = null;
        Hashtable headers=null,bodys=null;
        String sbody="";
        boolean Json = false;

        public httpPOST(OnHttpResponse pOnHttpResponse,Hashtable pBodys)
        {
            onHttpResponse=pOnHttpResponse;
            bodys=pBodys;
        }

        public httpPOST(OnHttpResponse pOnHttpResponseTable,String pBodys,Hashtable pheaders) {
            onHttpResponse=pOnHttpResponseTable;
            sbody=pBodys;
            headers=pheaders;
            Json=true;
        }

        public httpPOST(OnHttpResponse pOnHttpResponse,String pBodys)
        {
            onHttpResponse=pOnHttpResponse;
            sbody=pBodys;
            Json=true;
        }

        public httpPOST(OnHttpResponse pOnHttpResponseTable,Hashtable pBodys,Hashtable pheaders) {
            onHttpResponse=pOnHttpResponseTable;
            bodys=pBodys;
            headers=pheaders;
        }

        @Override
        protected Response doInBackground(String... strings)
        {
            if (strings.length>0)
            {
                String Url = strings[0];
                if (Json)
                    return http.POST(Url,sbody,headers);
                else
                    return http.POST(Url,bodys,headers);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (onHttpResponse!=null)
                onHttpResponse.onResponse(response);
        }
    }
    public static class httpPOSTTable extends AsyncTask<String,Void,DataTable> {

        OnHttpResponseTable onHttpResponseTable = null;
        Hashtable headers=null,bodys=null;
        String sbody="";
        boolean Json = false;
        public httpPOSTTable(OnHttpResponseTable pOnHttpResponse,Hashtable pBodys)
        {
            onHttpResponseTable=pOnHttpResponse;
            bodys=pBodys;
        }

        public httpPOSTTable(OnHttpResponseTable pOnHttpResponseTable,Hashtable pBodys,Hashtable pheaders) {
            onHttpResponseTable=pOnHttpResponseTable;
            bodys=pBodys;
            headers=pheaders;
        }


        public httpPOSTTable(OnHttpResponseTable pOnHttpResponseTable,String pBodys,Hashtable pheaders) {
            onHttpResponseTable=pOnHttpResponseTable;
            sbody=pBodys;
            headers=pheaders;
            Json=true;
        }

        public httpPOSTTable(OnHttpResponseTable pOnHttpResponse,String pBodys)
        {
            onHttpResponseTable=pOnHttpResponse;
            sbody=pBodys;
            Json=true;
        }

        @Override
        protected DataTable doInBackground(String... strings)
        {
            if (strings.length>0)
            {
                String Url = strings[0];
                if (Json)
                    return new DataTable(http.POST(Url,sbody,headers).Data);
                else
                    return new DataTable(http.POST(Url,bodys,headers).Data);
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataTable response) {
            super.onPostExecute(response);
            if (onHttpResponseTable!=null)
                onHttpResponseTable.onResponse(response);
        }
    }
    public static class httpPUT extends AsyncTask<String,Void,Response> {

        OnHttpResponse onHttpResponse = null;
        Hashtable headers=null,bodys=null;
        String sbody="";
        boolean Json = false;

        public httpPUT(OnHttpResponse pOnHttpResponse,Hashtable pBodys)
        {
            onHttpResponse=pOnHttpResponse;
            bodys=pBodys;
        }

        public httpPUT(OnHttpResponse pOnHttpResponseTable,String pBodys,Hashtable pheaders) {
            onHttpResponse=pOnHttpResponseTable;
            sbody=pBodys;
            headers=pheaders;
            Json=true;
        }

        public httpPUT(OnHttpResponse pOnHttpResponse,String pBodys)
        {
            onHttpResponse=pOnHttpResponse;
            sbody=pBodys;
            Json=true;
        }

        public httpPUT(OnHttpResponse pOnHttpResponseTable,Hashtable pBodys,Hashtable pheaders) {
            onHttpResponse=pOnHttpResponseTable;
            bodys=pBodys;
            headers=pheaders;
        }

        @Override
        protected Response doInBackground(String... strings)
        {
            if (strings.length>0)
            {
                String Url = strings[0];
                if (Json)
                    return http.PUT(Url,sbody,headers);
                else
                    return http.PUT(Url,bodys,headers);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (onHttpResponse!=null)
                onHttpResponse.onResponse(response);
        }
    }
    public static class httpPUTTable extends AsyncTask<String,Void,DataTable> {

        OnHttpResponseTable onHttpResponseTable = null;
        Hashtable headers=null,bodys=null;
        String sbody="";
        boolean Json = false;
        public httpPUTTable(OnHttpResponseTable pOnHttpResponse,Hashtable pBodys)
        {
            onHttpResponseTable=pOnHttpResponse;
            bodys=pBodys;
        }

        public httpPUTTable(OnHttpResponseTable pOnHttpResponseTable,Hashtable pBodys,Hashtable pheaders) {
            onHttpResponseTable=pOnHttpResponseTable;
            bodys=pBodys;
            headers=pheaders;
        }


        public httpPUTTable(OnHttpResponseTable pOnHttpResponseTable,String pBodys,Hashtable pheaders) {
            onHttpResponseTable=pOnHttpResponseTable;
            sbody=pBodys;
            headers=pheaders;
            Json=true;
        }

        public httpPUTTable(OnHttpResponseTable pOnHttpResponse,String pBodys)
        {
            onHttpResponseTable=pOnHttpResponse;
            sbody=pBodys;
            Json=true;
        }

        @Override
        protected DataTable doInBackground(String... strings)
        {
            if (strings.length>0)
            {
                String Url = strings[0];
                if (Json)
                    return new DataTable(http.PUT(Url,sbody,headers).Data);
                else
                    return new DataTable(http.PUT(Url,bodys,headers).Data);
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataTable response) {
            super.onPostExecute(response);
            if (onHttpResponseTable!=null)
                onHttpResponseTable.onResponse(response);
        }
    }


}

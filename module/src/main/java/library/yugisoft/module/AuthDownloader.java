package library.yugisoft.module;

import android.app.Activity;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;

public class AuthDownloader extends BaseImageDownloader {

    public AuthDownloader(Activity context){
        super(context);
    }

    @Override
    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        HttpURLConnection conn = super.createConnection(url, extra);
        conn.setRequestProperty("Authorization","bearer "+ yugi.getToken());
        return conn;
    }
}
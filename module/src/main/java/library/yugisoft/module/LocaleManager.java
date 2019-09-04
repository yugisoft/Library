package library.yugisoft.module;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LocaleManager {
    public static void setLocale(Context c) {

        String language = yugi.GetSetup(c,"Language",getLanguage(c));
        Locale myLocale = new Locale(language);
        Resources res = c.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    public static void setLocale(Context c, String language) {

        Locale myLocale = new Locale(language);
        Resources res = c.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        yugi.SetSetup(c,"Language",getLanguage(c));
    }
    public static String getLanguage(Context c) {
        return c.getResources().getConfiguration().locale.getLanguage();
    }
}
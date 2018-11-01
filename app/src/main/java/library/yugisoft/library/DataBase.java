package library.yugisoft.library;

import android.content.Context;

import library.yugisoft.module.YgSql;

public class DataBase extends YgSql
{

    private static final int DbVersiyon = 1;
    private Context context;

    public DataBase(Context context) {

        menu = new TABLE<xMenu>(context);
    }


    public YgSql.TABLE<xMenu> menu = null;
    @Override
    public Context context() {
        return context;
    }
}

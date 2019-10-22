package library.yugisoft.module.Utils.pSmartGridView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

public class SmartAdapter<T>
{
    //region SmartAdapter

    public SmartAdapter(){}

    public SmartAdapter(vList<T> data)
    {
        setData(data);
    }
    public SmartAdapter(List<T> data)
    {
        setData(data);
    }


    //endregion
    //region Declare

    protected vList<T> vData = new vList<>();
    protected List<T> sData = new ArrayList<>();

    //region SETTER
    public SmartAdapter setData(vList<T> data)
    {
        this.vData = data;
        this.sData = this.vData.list;
        return this;
    }
    public SmartAdapter setData(List<T> data)
    {
        this.vData = new vList<T>();
        this.sData = data;
        this.vData.list = this.sData;
        return this;
    }



    //endregion

    //region GETTER
    public vList<T> getData() {
        return vData;
    }
    //endregion


    //endregion
    //region METHOD

    public int getCount()
    {
        return getData().size();
    }
    public void foreach(@NonNull ISmartAdapterController<T> controller) {
        if (getData().size() ==0 )
            return;
        if (yugi.activity == null)
            return;


        controller.onStart();
        new AsyncTask<Void, View, String>() {

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(Void... voids)
            {
                for ( int i = 0;i< getCount();i++)
                {
                    T item = getData().get(i);
                    View view ;
                    if (controller.detailViewID(item)!=0)
                        view = yugi.activity.getLayoutInflater().inflate(controller.detailViewID(getData().get(i)),null);
                    else
                        view = controller.detailView(item);

                    onProgressUpdate(view,item,i);
                }

                return null;
            }
            @Override
            protected void onPostExecute(String s)
            {
                controller.onFinish();
            }
            public  void onProgressUpdate(View view ,T item,int index) {
                yugi.activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        controller.setView(view,item,index);
                    }
                });
            }

        }.execute();
    }

    //endregion
}

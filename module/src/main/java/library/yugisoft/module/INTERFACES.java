package library.yugisoft.module;

import android.support.annotation.Nullable;

import java.util.List;


/**
 * Created by Yusuf on 26.10.2017.
 */
public class INTERFACES
{
    //region DataTable
    public interface DataTableListener {
        void onEowsAdded(int index, DataTable.DataRow row);
        void onCellValueChaned(int rowIndex, int cellIndex, Object beforeValue, Object afterValue);
    }
    //endregion
    //region MaskedEditText
    public interface OnPhoneTextListener {
        void onPhoneText(String phonetext);
    }
    //endregion
    //region DateText
    public  interface  OnDateSelectedListener {
        void onDataSelectedListener(String Date);
    }
    //endregion
    //region HesapKoduList
    public interface OnDialogSelected
    {
        void onSelected(Object Item, @Nullable DataTable.DataRow Row);
    }
    //endregion
    //region ComboBox
    public interface OnComboBoxListener
    {
        void onLoaded();
        void onSelected(int position, long id);
    }
    //endregion
    //region onClassListener
    public interface onClassListener {
        public void onLoad();
        public void onSave(yugi.httpHata res);
    }
    //endregion
    //region vMüşteriListner

    public interface OnvMusteriListener {
     void onSelectCustomer(long customerID);
    }

    //endregion
    public interface OnNumpadListener {
        void onResultOK(double Before, double After);
    }
    //region CLASSINTERFACE
    public interface OnLoadListner {
        void onLoad();
    }
    //endregion



    public interface IitemAdapter<T>
    {
        void setOnAdapterDataLoad(OnAdapterDataLoad<T> li);
        OnAdapterDataLoad<T> getOnAdapterDataLoad();
    }
    public interface OnAdapterDataLoad<T>
    {
        void onLoad(List<T> data);
    }

}

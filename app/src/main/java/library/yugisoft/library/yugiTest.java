package library.yugisoft.library;

import android.content.Context;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import library.yugisoft.module.ComboBox;
import library.yugisoft.module.Data.DataGridAdapter;
import library.yugisoft.module.Data.DataGridView;
import library.yugisoft.module.DataTable;
import library.yugisoft.module.DialogBox;
import library.yugisoft.module.Generic;
import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.SmartList;
import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test11);

        DataTable dataTable = new DataTable("[{\"Product ID\":1702,\"Sku ID\":13817,\"Product Code\":\"125\",\"Product Name\":\"BİLGİSAYAR\",\"Barcode\":\"2823\",\"Color Name\":\"\",\"Size No\":\"\",\"Price Value\":200.6,\"Currency ID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"1001\",\"ColorName\":\"Açık mavi\",\"SizeNo\":\"XS\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":2,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"1002\",\"ColorName\":\"Açık Mor\",\"SizeNo\":\"S\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":3,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"1003\",\"ColorName\":\"Açık Sarı\",\"SizeNo\":\"M\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1803,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2800\",\"ColorName\":\"Gök mavisi\",\"SizeNo\":\"44\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1813,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2805\",\"ColorName\":\"Bordo\",\"SizeNo\":\"42\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1814,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2806\",\"ColorName\":\"Gök mavisi\",\"SizeNo\":\"45\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1815,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2807\",\"ColorName\":\"Fuşya\",\"SizeNo\":\"42\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1816,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2808\",\"ColorName\":\"Ekru\",\"SizeNo\":\"3XL\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":13814,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2820\",\"ColorName\":\"Siyah\",\"SizeNo\":\"S\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":2,\"SkuID\":4,\"ProductCode\":\"161AB2036\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1004\",\"ColorName\":\"Antrasit\",\"SizeNo\":\"45\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":2,\"SkuID\":5,\"ProductCode\":\"161AB2036\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1005\",\"ColorName\":\"Bakır rengi\",\"SizeNo\":\"44\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":2,\"SkuID\":6,\"ProductCode\":\"161AB2036\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1006\",\"ColorName\":\"Bej\",\"SizeNo\":\"43\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1813,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2805\",\"ColorName\":\"Bordo\",\"SizeNo\":\"42\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1814,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2806\",\"ColorName\":\"Gök mavisi\",\"SizeNo\":\"45\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1815,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2807\",\"ColorName\":\"Fuşya\",\"SizeNo\":\"42\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":1816,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2808\",\"ColorName\":\"Ekru\",\"SizeNo\":\"3XL\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":1,\"SkuID\":13814,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"Barcode\":\"2820\",\"ColorName\":\"Siyah\",\"SizeNo\":\"S\",\"PriceValue\":75.6,\"CurrencyID\":\"TRY\"},{\"ProductID\":2,\"SkuID\":4,\"ProductCode\":\"161AB2036\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1004\",\"ColorName\":\"Antrasit\",\"SizeNo\":\"45\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":2,\"SkuID\":5,\"ProductCode\":\"161AB2036\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1005\",\"ColorName\":\"Bakır rengi\",\"SizeNo\":\"44\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":2,\"SkuID\":6,\"ProductCode\":\"161AB2036\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1006\",\"ColorName\":\"Bej\",\"SizeNo\":\"43\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":2,\"SkuID\":1800,\"ProductCode\":\"161AB2036\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"2797\",\"ColorName\":\"\",\"SizeNo\":\"47½\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":2,\"SkuID\":3310,\"ProductCode\":\"161AB2036\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"2813\",\"ColorName\":\"Antrasit\",\"SizeNo\":\"\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":3,\"SkuID\":7,\"ProductCode\":\"161AB2037\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1007\",\"ColorName\":\"\",\"SizeNo\":\"\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":3,\"SkuID\":8,\"ProductCode\":\"161AB2037\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1008\",\"ColorName\":\"\",\"SizeNo\":\"\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":3,\"SkuID\":9,\"ProductCode\":\"161AB2037\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1009\",\"ColorName\":\"\",\"SizeNo\":\"\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":4,\"SkuID\":10,\"ProductCode\":\"161AB2038\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1010\",\"ColorName\":\"\",\"SizeNo\":\"\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"},{\"ProductID\":4,\"SkuID\":11,\"ProductCode\":\"161AB2038\",\"ProductName\":\"BAYAN ÇANTA\",\"Barcode\":\"1011\",\"ColorName\":\"\",\"SizeNo\":\"\",\"PriceValue\":10.8,\"CurrencyID\":\"TRY\"}]");
        ((DataGridView)findViewById(R.id.dataGrid)).getDataGridAdapter().setData(dataTable);



    }


}
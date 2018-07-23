package library.yugisoft.library;

import android.os.Bundle;
import android.view.View;

import library.yugisoft.module.yugi;

public class yugiTest extends yugi.vActivity implements View.OnClickListener {

String jsondata = "{\"Products\":[{\"SkuID\":1813,\"ProductCode\":\"161AB2035\",\"ProductName\":\"BAYAN ÇANTASI\",\"ProductFullName\":\"BAYAN ÇANTASI // Bordo // 42\",\"BuyingVatRate\":8,\"SalesVatRate\":8,\"PriceValue\":70,\"VatInclude\":false,\"ProductID\":1,\"Barcode\":\"1\",\"Qty\":2,\"Price\":70,\"PriceVatInclude\":75.6,\"DiscountAmountVatInclude\":0,\"TotalAmount\":151.2,\"CurrencyID\":\"TL\"}],\"Customer\":{\"CustomerID\":39,\"FirmTitle\":\"Yusuf AYDIN\",\"CustomerName\":\"YUSUF\",\"CustomerSurName\":\"AYDIN\",\"MobilePhone\":\"5355353535\"},\"InvoiceAdress\":{\"StockTransactionID\":100101,\"FirmTitle\":\"Yusuf AYDIN\",\"CustomerName\":\"YUSUF\",\"CustomerSurName\":\"AYDIN\",\"CountryID\":\"TR\",\"CityID\":\"34\",\"TownID\":\"77\",\"TownName\":\"ÜSKÜDAR\",\"Address\":\"Mehmet Akif Ersoy Mahallesi Vuslat Sokak No 46/3 Çenelgöy / Üsküdar\",\"PostCode\":\"jsksk@jsjd\",\"TaxNumber\":\"53525251\",\"TaxOffice\":\"ÜSKÜDAR\",\"Phone1\":\"5355353535\",\"Phone2\":\"5355353535\",\"MobilePhone\":\"5355353535\",\"Fax\":\"5355353535\",\"Email\":\"jsksk@jsjd.com\",\"AddressType\":\"\\u0000\"},\"ShipAdress\":{\"StockTransactionID\":100101,\"FirmTitle\":\"Yusuf AYDIN\",\"CustomerName\":\"YUSUF\",\"CustomerSurName\":\"AYDIN\",\"CountryID\":\"TR\",\"CityID\":\"34\",\"TownID\":\"77\",\"TownName\":\"ÜSKÜDAR\",\"Address\":\"Mehmet Akif Ersoy Mahallesi Vuslat Sokak No 46/3 Çenelgöy / Üsküdar\",\"PostCode\":\"jsksk@jsjd\",\"TaxNumber\":\"53525251\",\"TaxOffice\":\"ÜSKÜDAR\",\"Phone1\":\"5355353535\",\"Phone2\":\"5355353535\",\"MobilePhone\":\"5355353535\",\"Fax\":\"5355353535\",\"Email\":\"jsksk@jsjd.com\",\"AddressType\":\"\\u0000\"},\"Payments\":[],\"SalesID\":100101,\"Subtotal\":140,\"VAT\":11.2,\"TotalPrice\":151.2,\"TotalCount\":2,\"RemainingPrice\":151.2,\"TotalPayed\":0,\"StockTransactionTypeID\":9251,\"PriceTypeID\":\"PSF\",\"Discount\":0}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onClick(View view) {

    }
}

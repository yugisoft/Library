package library.yugisoft.library.Models;

import library.yugisoft.module.yugi;

public class ConfigModel {
    public int GPOS_CompanyID ;
    public int GPOS_LocationID ;
    public int GPOS_PriceTypeID ;
    public String GPOS_CurrencyID ;
    public String MemberID ;
    public String UserID ;
    public short StockTransactionTypeID ;



    public static void DEMO()
    {

        yugi.setToken("ARQ3KJItrdG8BNv2tz3eEUNZT6ppcRpPRkrufg0RtRgE5yoCb8mzHqCYjpkUg4_C0GyeH6WQEKZRX0QW6B37M4IcylmRH2zPrjF0t9FIhVh95aqmh0BGjt9cxMXzS-sdSI0a7qEuLb1LRPrBuHK1vnKVqTl7hzuH5cJflPtEH4ixdjcY88dvNTu13W32w95p2wcdK7_aS6hW83MLb2HoYMShw15LIrb6I802P1mpPfTlpHMubuAyW_OWcz1WJ6J3");
        ConfigModel model = new ConfigModel();

        model.GPOS_CompanyID=29;
        model.GPOS_CurrencyID="TL";
        model.GPOS_LocationID=10;
        model.GPOS_PriceTypeID=1;
        model.MemberID="DEMO";
        model.StockTransactionTypeID=9251;
        model.UserID="4";

        yugi.ConstHttpHeader = model;

    }
}

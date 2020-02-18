package library.yugisoft.library;
    import android.graphics.Color;
    import android.graphics.PorterDuff;
    import android.graphics.drawable.Drawable;

    import android.os.Handler;
    import android.support.v7.widget.Toolbar;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.support.v7.widget.SearchView;
    import android.widget.TextView;


    import library.yugisoft.library.Activitys.UI.FirmList;
    import library.yugisoft.library.Activitys.UI.UI_activity_main;
    import library.yugisoft.module.Json;
    import library.yugisoft.module.Utils.JsonConverter;
    import library.yugisoft.module.Utils.pGoldGridView.GoldGridView;
    import library.yugisoft.module.ViewPager;
    import library.yugisoft.module.vList;
    import library.yugisoft.module.yugi;

public class yugiTest extends UI_activity_main implements View.OnClickListener {




    //region jsondata
    String jsonData = "{\"TotalCount\":43,\"PageCount\":1,\"Page\":1,\"Data\":[{\"FirmID\":100100003499,\"FirmTitle\":\"YİĞİT ÇANTA 17\"},{\"FirmID\":100100002976,\"FirmTitle\":\"YİĞİT ÇANTA\"},{\"FirmID\":100100001155,\"FirmTitle\":\"YILMAZ SARAÇOĞLU-LADUELLA\"},{\"FirmID\":100100001381,\"FirmTitle\":\"VİTRİN ÇANTA\"},{\"FirmID\":100100001091,\"FirmTitle\":\"VAKKO TEKSTİL VE HAZIR GİYİM SAN.İŞL.AŞ\"},{\"FirmID\":100100003165,\"FirmTitle\":\"URAL ÇANTA - İSMAİL URAL\"},{\"FirmID\":100100003065,\"FirmTitle\":\"SHENZ FOREİGN TRADE ASİA-BOOST INDUSTRIAL CO. LTD\"},{\"FirmID\":999920017857,\"FirmTitle\":\"SATICI KONSINYE (TL)\"},{\"FirmID\":999920017856,\"FirmTitle\":\"SATICI KESİN (TL)\"},{\"FirmID\":100100002207,\"FirmTitle\":\"SABO AYAKKABI\"},{\"FirmID\":100100003020,\"FirmTitle\":\"PRV TEKSTİL SANAYİ TİCARET A.Ş.\"},{\"FirmID\":999920017853,\"FirmTitle\":\"POYRAZ SATICI A.Ş.\"},{\"FirmID\":100100003498,\"FirmTitle\":\"ORAY ÇANTA 17\"},{\"FirmID\":100100002903,\"FirmTitle\":\"ORAY ÇANTA\"},{\"FirmID\":100100003489,\"FirmTitle\":\"NET ÇANTA AYAK.SAN.TİC.LTD.ŞTİ\"},{\"FirmID\":100100003254,\"FirmTitle\":\"NEJAT İNAN\"},{\"FirmID\":100100002938,\"FirmTitle\":\"NATURAL PARFÜMERİ İTH.İHR.SAN.VE TİC.A.Ş.\"},{\"FirmID\":100100000373,\"FirmTitle\":\"MEŞİN DERİ VE TEKSTİL ÜRÜN.SAN.TİC.LTD.ŞTİ.\"},{\"FirmID\":100100002696,\"FirmTitle\":\"MEDA ÇANTA - ERSEN ÇUBUKÇUOĞLU\"},{\"FirmID\":100100003509,\"FirmTitle\":\"KOLUKISAIAN ÇANTA - VARTAN KOLUKISAYAN\"},{\"FirmID\":100100003412,\"FirmTitle\":\"KEMAL ÇANTA\"},{\"FirmID\":100100002204,\"FirmTitle\":\"KANUOER INTERNATIONAL LIMITED\"},{\"FirmID\":100100000420,\"FirmTitle\":\"ITP (HEYUAN) LUGGAGE CO., LTD\"},{\"FirmID\":999920017767,\"FirmTitle\":\"Giz Satıcı\"},{\"FirmID\":100100002813,\"FirmTitle\":\"Giz Konsinye Satıcı\"},{\"FirmID\":100100001814,\"FirmTitle\":\"FARO TEKSTİL TASARIM HİZ. SAN. VE TİC. LTD. ŞTİ.\"},{\"FirmID\":100100000383,\"FirmTitle\":\"EMRE SARACİYE SAN. VE TİC. A.Ş.\"},{\"FirmID\":100000343051,\"FirmTitle\":\"ELASU  A.Ş.\"},{\"FirmID\":999920017777,\"FirmTitle\":\"ELA SU RESTAURANT\"},{\"FirmID\":100100000382,\"FirmTitle\":\"EFOR DERİ ÜRÜNLERİ - CENGİZ PAKEL\"},{\"FirmID\":100100003180,\"FirmTitle\":\"EBRÜ KÜRK  - ABDURRAHİM KOYUNCU\"},{\"FirmID\":100100003253,\"FirmTitle\":\"ÇALIK DERİ MAML. İTH. VE İHR. PAZARLAMA LTD. ŞTİ.\"},{\"FirmID\":999920017867,\"FirmTitle\":\"CEM TEST\"},{\"FirmID\":100100001980,\"FirmTitle\":\"BRİDGE OF TRADES LLC\"},{\"FirmID\":100100000370,\"FirmTitle\":\"BERKA DERİ ÜRÜNLERİ SAN. TİC. LTD. ŞTİ.\"},{\"FirmID\":100100003497,\"FirmTitle\":\"BARIŞ SARAÇOĞLU 17\"},{\"FirmID\":100100003078,\"FirmTitle\":\"BARIŞ SARAÇOĞLU\"},{\"FirmID\":999920017862,\"FirmTitle\":\"ATAKAN ÇANTA 19\"},{\"FirmID\":100100003496,\"FirmTitle\":\"ATAKAN ÇANTA 17\"},{\"FirmID\":100100002950,\"FirmTitle\":\"ATAKAN ÇANTA 16\"},{\"FirmID\":100100000565,\"FirmTitle\":\"ARTO KEMER DERİ MAM.PAZ.SAN.TİC LTD ŞTİ (RİNO)\"},{\"FirmID\":100100001687,\"FirmTitle\":\"ARPEL ÇANTA - HAYATİ PEKER\"},{\"FirmID\":100100001142,\"FirmTitle\":\"AKİN ÇANTA - OSMAN AKIN\"}]}";
    //endregion

    GoldGridView gridView;
    FirmListResponse Data;



    @Override
    public void Ready()
    {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        gridView = findViewById(R.id.gridView);
        Data = JsonConverter.convertJsonToModel(jsonData,FirmListResponse.class);
        gridView.setData(Data.getData());
    }



    /*
     if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try {
                InputStream is = yugi.activity.getAssets().open("JsonData.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonData = new String(buffer);
                if ((int)jsonData.charAt(0) == '\uFEFF')
                    jsonData = jsonData.replace(jsonData.substring(0,1),"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    */

    @Override
    public void onClick(View v)
    {


    }


    public static class FirmListResponse   {


        //region TotalCount
        @Json(name = "TotalCount")
        private int totalCount;
        public int getTotalCount() {
            return totalCount;
        }
        //endregion
        //region PageCount
        @Json(name = "PageCount")
        private int pageCount;
        public int getPageCount() {
            return pageCount;
        }
        //endregion
        //region Page
        @Json(name = "Page")
        private int page;
        public int getPage() {
            return page;
        }
        //endregion


        //region Data
        @Json(name = "Data")
        private vList<FirmList> data = new vList<>();

        //endregion

        public vList<FirmList> getData() {
            return data;
        }
    }


    SearchView searchView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list_activity, menu);


        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();


        EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(Color.WHITE);
        for (Drawable d:searchEditText.getCompoundDrawables()             )
        {
            if (d!=null)
                d.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText)
            {
                runnableFilter.canWork = false;
                runnableFilter = new RunnableFilter();
                new Handler().postDelayed(runnableFilter,200);

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

        });

        return super.onCreateOptionsMenu(menu);
    }

    String lastFilter = "";
    int i = 0;

    class RunnableFilter implements Runnable
    {
        public boolean canWork = true;
        @Override
        public void run() {
            if (canWork)
                onFilter(searchView.getQuery().toString().toLowerCase());
        }
    }

    RunnableFilter runnableFilter = new RunnableFilter();

    private void onFilter(String newText)
    {
        if (!lastFilter.equals(newText))
        {
            gridView.setData(Data.data.Filter(f-> f.getFirmTitle().toLowerCase().contains(newText)));
            lastFilter = (searchView.getQuery().toString());
            yugi.Print("e","gridView.setDa",""+gridView.getData().size());

        }
    }

    private void onFilter()
    {
            gridView.setData(Data.getData());
    }


}

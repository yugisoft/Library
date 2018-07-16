package library.yugisoft.library.Models.CartModel;


import java.util.List;


import library.yugisoft.module.DataTable;
import library.yugisoft.module.SmartList;
import library.yugisoft.module.http;
import library.yugisoft.module.yugi;


public class Cart implements IShoppingCart
{
    public long SalesID ;
    public double AraToplam ;
    public double Kdv ;
    public double TotalPrice ;
    public double TotalCount ;
    public double RemainingPrice ;
    public double TotalPayed ;
    public double TotalPay ;
    public int StockTransactionTypeID ;
    public String PriceTypeID ;
    public double Indirim ;

    public List<CartPayment> Payments = new SmartList(CartPayment.class);
    public List<Product> ProductList = new SmartList(Product.class);
    public CartCustomerAdress InvoiceAdres = new CartCustomerAdress();
    public CartCustomerAdress ShipAdres = new CartCustomerAdress();
    public CartCustomer Customer = new CartCustomer();


    @Override
    public void Load() { Load(0); }

    @Override
    public void Load(long id)
    {
        new http.httpGET(new http.OnHttpResponse() {
            @Override
            public void onResponse(http.Response response) {
                if (response.isException)
                {
                    yugi.MessageBox.Show(yugi.activity,response.HataAciklama,true);
                }
                else
                {
                    DataTable dataTable = new DataTable(response.Data);
                    dataTable.ToClass(Cart.this,0);
                    long i = SalesID;
                }
            }
        }).execute("http://10.34.41.58:/IstePosAPI/api/Cart");


    }

    @Override
    public void UrunEkle(String Barkod, int Adet) {

    }

    @Override
    public void UrunCikar(String Barkod, int Adet) {

    }

    @Override
    public void MusteriSec(long CustomerID) {

    }

    @Override
    public void MusteriSec(long CustomerID, boolean showDialog) {

    }

    @Override
    public void RemoveCart() {

    }

    @Override
    public void AskiyaAl() {

    }

    @Override
    public void AskiyadanAl(long id) {

    }

    @Override
    public void Pay(Double tutar, String paraBirimi, int tip, String name) {

    }

    @Override
    public void RemovePay(Object sira) {

    }

    @Override
    public void Tamamla() {

    }

    @Override
    public void DeleteRecord() {

    }



    @Override
    public void CustomerAdres(CartCustomerAdress adress, String type) {

    }
}




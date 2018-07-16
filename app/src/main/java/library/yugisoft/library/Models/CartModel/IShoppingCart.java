package library.yugisoft.library.Models.CartModel;

public interface IShoppingCart
{
    void Load();
    void Load(long id);
    void UrunEkle(String Barkod, int Adet);
    void UrunCikar(String Barkod, int Adet);
    void MusteriSec(long CustomerID);
    void MusteriSec(long CustomerID, boolean showDialog);
    void CustomerAdres(CartCustomerAdress adress, String type);
    void RemoveCart();
    void AskiyaAl();
    void AskiyadanAl(long id);
    void Pay(Double tutar, String paraBirimi, int tip, String name);
    void RemovePay(Object sira);
    void Tamamla();
    void DeleteRecord();
}

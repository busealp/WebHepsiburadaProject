Example Spec
===========


Seneryo 1
-----------
* HomePage den LoginPage yönel
* "dene.me@gmail.com" ve "deneme123" ile login ol
* "Deneme Deneme" başarılı login kontrol
//* Sepetin "0" boş olduğu kontrol et değilse boşalt

* random kategori tıklanır "btn_categori"
* random alt kategori tıklanır "btn_altCategori"
* random marka tılanır "btn_brand"
* "10" - "4000" fiyat aralığında ürün listelenir
* Wait "1" milliseconds
* "btn_ProductName" "btn_ProductPrice" urun random tıklanır, csv dosyasına yazır
* Ürün detay ekranından "btn_detayProductName" isim "tb_price" "tb_priceKuruş" "tb_tl"fiyat kontrolü yapılır
* Ürün sepete eklenir, gerekli doğruluklar kontrol edilir, sepete gidilir
* Ürün sepetinde ürün adet sayısı iki "btn_cartAddProduct" artılır "tb_cartPrice"tutar bilgisi kontrol edilir
* Ürün toplamı değeri "tb_cartPrice"ve kargo tutarı "btn_shippingPrice" csv dosyasına yazdırılır, "btn_completeShopping"alışveriş tamamlanır.
* Ürün yeni adres bilgileri girilir ve ilerlenir
* Ürün ödeme tipi kredi kartı seçilir, kredi kartı bilgileri girilir ve anasayfaya dönülür
//* "cartCount" Eğer sepet doluysa boşaltılır  "btn_cartDelete"
* Adreslerim alanına gidilir, eklenen adresler silinir ve test sonlandırılır



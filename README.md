OPTIMARKET - Mobil Alışveriş ve Stok Yönetim Simülasyonu

OptiMarket, kullanıcıların ürün satın alabildiği, bakiye ve stok yönetimi yapabildiği, ayrıca OOP prensiplerine dayalı gelişmiş bir satış simülasyonu sunan Android tabanlı bir mobil uygulamadır.

👥 Proje Ekibi

Enes İnançlı


Mehmet Köksal


Zeynep Yamaç 


Berra Yörüsün

🚀 Proje Hakkında
Bu uygulama, yazılım mimarisi olarak Nesne Yönelimli Programlama (OOP) ve çeşitli tasarım desenlerini (Design Patterns) temel alarak geliştirilmiştir. Kullanıcılar gerçekçi bir alışveriş deneyimi yaşarken, arka planda ürün yönetimi, kâr hesaplama ve dinamik stok takibi süreçleri işletilir.

🛠️ Teknik Detaylar

IDE: Android Studio Meerkat 2024.3.1 


Dil: Java (OpenJDK 21) 


Veritabanı: SQLite (DatabaseHelper) 


Minimum Gereksinim: Android API level 36 (Android 13) ve üzeri 

🏗️ Yazılım Mimarisi ve Tasarım Desenleri
💎 OOP Prensipleri

Encapsulation (Kapsülleme): Ürün verileri private değişkenlerle korunmuş; erişim kontrollü getter ve setter metodlarıyla sağlanmıştır.


Inheritance (Kalıtım): Product ana sınıfı, Drinks, Snacks ve StapleFood gibi alt sınıflar tarafından miras alınarak genişletilmiştir.


Polymorphism (Çok Biçimlilik): getCategory() gibi metodlar, her ürün kategorisi için farklı sonuç dönecek şekilde geçersiz kılınmıştır (Override).


Abstraction (Soyutlama): Product sınıfı soyut (abstract) olarak tanımlanarak temel ürün şablonu oluşturulmuştur.

🧩 Tasarım Desenleri (Design Patterns)

Factory Pattern: ProductCreator sınıfı, kategori ismine göre uygun ürün nesnesini merkezi olarak oluşturur.


Strategy Pattern: ProductFilter arayüzü ile kategori veya fiyat bazlı esnek filtreleme stratejileri uygulanmıştır.


Singleton Pattern: AdminPanel sınıfı, tüm stok ve ürün yönetimini tek bir örnek üzerinden kontrol eder.

📱 Uygulama Görselleri
1. Ana Ekran ve Ürün Kataloğu
Uygulama $5000 başlangıç bakiyesi ile açılır. Kullanıcılar 21 farklı ürün arasından seçim yapabilir.

2. Satın Alma ve Stok Düzenleme
Ürünler miktar girilerek satın alınır. Satın alınan ürünlerin fiyatı ve indirim oranı sonradan "Product List" üzerinden güncellenebilir.

3. Haftalık Satış Simülasyonu
Simülasyon motoru; kâr marjı, indirimler ve rastgele talep faktörlerini kullanarak haftalık satışları hesaplar ve detaylı rapor sunar.

(Şekil: Toplam Gelir, Satılan Ürünler ve Kâr Analizi Raporu)

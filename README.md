# OPTIMARKET - Mobil Alışveriş ve Stok Yönetim Simülasyonu

**OptiMarket**, kullanıcıların ürün satın alabildiği, bakiye ve stok yönetimi yapabildiği, ayrıca Nesne Yönelimli Programlama (OOP) prensiplerine dayalı gelişmiş bir satış simülasyonu sunan Android tabanlı bir mobil uygulamadır.

## 👥 Proje Ekibi
* **Enes İnançlı** - 23118080051 
* **Mehmet Köksal** - 23118080060 
* **Zeynep Yamaç** - 23118080080 
* **Berra Yörüsün** - 23118080078 

---

## 🚀 Proje Hakkında
Bu uygulama, yazılım mimarisi olarak Nesne Yönelimli Programlama (OOP) ve çeşitli tasarım desenlerini (Design Patterns) temel alarak geliştirilmiştir. Kullanıcılar gerçekçi bir alışveriş deneyimi yaşarken, arka planda ürün yönetimi, kâr hesaplama ve dinamik stok takibi süreçleri işletilir.

### 🛠️ Teknik Detaylar
* **IDE:** Android Studio Meerkat 2024.3.1
* **Dil:** Java (OpenJDK 21)
* **Veritabanı:** SQLite (DatabaseHelper)
* **Gereksinim:** Android API level 36 ve üzeri

---

## 🏗️ Yazılım Mimarisi ve Tasarım Desenleri

### 💎 OOP Prensipleri
* **Encapsulation (Kapsülleme):** Ürün verileri `private` değişkenlerle korunmuş; erişim kontrollü `getter` ve `setter` metodlarıyla sağlanmıştır.
* **Inheritance (Kalıtım):** `Product` ana sınıfı, `Drinks`, `Snacks` gibi alt sınıflar tarafından miras alınarak genişletilmiştir.
* **Polymorphism (Çok Biçimlilik):** `getCategory()` metodu, her ürün kategorisi için farklı sonuç dönecek şekilde geçersiz kılınmıştır.
* **Abstraction (Soyutlama):** `Product` sınıfı soyut (abstract) olarak tanımlanarak temel ürün şablonu oluşturulmuştur.

### 🧩 Tasarım Desenleri (Design Patterns)
* **Factory Pattern:** `ProductCreator` sınıfı, kategori ismine göre uygun ürün nesnesini merkezi olarak oluşturur.
* **Strategy Pattern:** `ProductFilter` arayüzü ile kategori veya fiyat bazlı esnek filtreleme stratejileri uygulanmıştır.
* **Singleton Pattern:** `AdminPanel` sınıfı, tüm stok ve ürün yönetimini tek bir örnek üzerinden kontrol eder.

---

## 📱 Uygulama Ekranları

### 1. Ana Ekran ve Ürün Kataloğu
Uygulama $5000 başlangıç bakiyesi ile açılır. Kullanıcılar 21 farklı ürün arasından seçim yapabilir.

| Ana Menü | Ürün Filtreleme |
| :---: | :---: |
| ![Ana Ekran](images/fig1_main.png) | ![Filtreleme](images/fig2_3_4_filters.png) |
| *Bakiye ve Navigasyon* | *Fiyat ve Kategori Bazlı Arama* |

### 2. Satın Alma ve Stok Düzenleme
Ürünler miktar girilerek satın alınır. Satın alınan ürünlerin fiyatı ve indirim oranı sonradan güncellenebilir.

| Satın Alma Onayı | Ürün Düzenleme |
| :---: | :---: |
| ![Satın Alma](images/fig5_buy.png) | ![Düzenleme](images/fig6_7_8_edit.png) |
| *Miktar ve Onay Ekranı* | *Fiyat ve İndirim Güncelleme* |

### 3. Haftalık Satış Simülasyonu
Simülasyon motoru; kâr marjı, indirimler ve rastgele talep faktörlerini kullanarak haftalık satışları hesaplar.

![Satış Sonuçları](images/fig11_simulation.png)
*(Şekil: Toplam Gelir, Satılan Ürünler ve Kâr Analizi Raporu)*

---

## 📚 Kaynakça
* [Refactoring Guru - Design Patterns](https://refactoring.guru/design-patterns)
* [GeeksforGeeks - Software Design Patterns](https://www.geeksforgeeks.org/software-design-patterns/)

package com.example.optimarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OptiMarket.db";
    private static final int DATABASE_VERSION = 3;

    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE products (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "category TEXT," +
            "price REAL," +
            "cost REAL," +
            "profit REAL," +
            "discountAmount REAL," +
            "stock INTEGER" +
            ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCTS);
        Log.d("DB_HELPER", "products tablosu oluşturuldu");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("category", product.getCategory());
        values.put("price", product.getPrice());
        values.put("cost", product.getCost());

        // Karı burada hesaplayıp ekleyebilirsin
        double profit = product.getPrice() - product.getCost();
        values.put("profit", profit);

        values.put("discountAmount", product.getDiscountAmount());
        values.put("stock", product.getStock());

        long id = db.insert("products", null, values);
        if (id == -1) {
            Log.d("DB_HELPER", "Ürün eklenemedi");
        } else {
            Log.d("DB_HELPER", "Ürün eklendi, id: " + id);
        }

        db.close();
    }




    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products", null);

        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));

                // Doğru subclass'ı oluşturmak için ProductCreator kullan
                ProductCreator creator = new ProductCreator();
                Product product = creator.CreateProduct(category);

                if (product != null) {
                    product.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
                    product.setCost(cursor.getDouble(cursor.getColumnIndexOrThrow("cost")));
                    product.setStock(cursor.getInt(cursor.getColumnIndexOrThrow("stock")));
                    product.setDiscountAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("discountAmount")));
                    product.calculateProfit();
                    productList.add(product);
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("stock", product.getStock());
        values.put("discountAmount", product.getDiscountAmount());

        // Ürünü isme göre güncelliyoruz (eşsiz olduğunu varsayıyoruz)
        db.update("products", values, "name = ?", new String[]{product.getName()});
        db.close();
    }


    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
        db.close();
    }



}

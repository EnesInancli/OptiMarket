package com.example.optimarket;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;


    // EDİTTEN STOK/İNDİRİM DEĞİŞTİRİNCE NORMAL FİYATINA DİREKT ETKİ ETMİYOR O ANKİ TUTARINA ETKİ
    // EDİYOR GİR ÇIK YAPILDIĞINDA GERÇEK FİYATINA ETKİ EDİYOR


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Veritabanından ürünleri al
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        productList = dbHelper.getAllProducts();

        // Adapter'e ürünleri ver
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);

        Log.d("PRODUCT_LIST", "Ürün sayısı: " + productList.size());
        for (Product p : productList) {
            Log.d("PRODUCT_LIST", "Ürün: " + p.getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        productList.clear();
        productList.addAll(dbHelper.getAllProducts());

        productAdapter.notifyDataSetChanged();
    }
}
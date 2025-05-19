package com.example.optimarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Ürün listesine gitme butonu
        Button productlistbutton = findViewById(R.id.productListButton);
        productlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

        //Ürün ekleme sekmesine gitme butonu
        Button addproductbutton = findViewById(R.id.addProductButton);
        addproductbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        //Stok yönetimi sekmesine gitme butonu
        Button stockmanagementbutton = findViewById(R.id.stockManagementButton);
        stockmanagementbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StockManagementActivity.class);
                startActivity(intent);
            }
        });

        //Fiyat optimizasyonu sekmesine gitme butonu
        Button optimizepricesbutton = findViewById(R.id.optimizePricesButton);
        optimizepricesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OptimizePricesActivity.class);
                startActivity(intent);
            }
        });

        //İstatistik sekmesine gitme butonu
        Button statisticsbutton = findViewById(R.id.statisticsButton);
        statisticsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });
    }
}
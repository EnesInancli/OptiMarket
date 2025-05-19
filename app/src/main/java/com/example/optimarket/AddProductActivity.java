package com.example.optimarket;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        EditText nameEditText = findViewById(R.id.namePlainText);
        EditText priceEditText = findViewById(R.id.pricePlainText);
        EditText costEditText = findViewById(R.id.costPlainText);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);

        // Kategoriler
        String[] categories = {"Drinks","Food Of Animal Origin","Fruits And Vegetables","Household Items","Selfcare","Snacks","Staple Food"};

        // Spinner'a (kategorilerin seçileceği liste) adapter ayarla
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        String category = categorySpinner.getSelectedItem().toString();

        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();

            double price = 0.0;
            double cost = 0.0;

            try {
                price = Double.parseDouble(priceEditText.getText().toString());
                cost = Double.parseDouble(costEditText.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid Price/Cost!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ürün ekle
            AdminPanel.getInstance().createAndAddProduct(getApplicationContext(), name, price, cost, 0, category);

            finish();
        });

    }
}
package com.example.optimarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TextView statusText, balanceText;
    private ProgressBar progressBar;
    private Button startSimulationButton;
    private RecyclerView resultsRecyclerView;
    private SimulationResultAdapter resultsAdapter;
    private List<SimulationResult> simulationResults;

    private double currentBalance;
    private double totalRevenue = 0;
    private int totalItemsSold = 0;
    private int deletedProductsCount = 0; // Silinen ürün sayısı

    private Handler handler = new Handler();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        initializeViews();

        databaseHelper = new DatabaseHelper(this);
        currentBalance = getIntent().getDoubleExtra("balance", 0.0);
        balanceText.setText("Current Balance: $" + String.format("%.2f", currentBalance));

        simulationResults = new ArrayList<>();
        setupRecyclerView();

        startSimulationButton.setOnClickListener(v -> startSimulation());
    }

    private void initializeViews() {
        statusText = findViewById(R.id.statusText);
        balanceText = findViewById(R.id.balanceText);
        progressBar = findViewById(R.id.progressBar);
        startSimulationButton = findViewById(R.id.startSimulationButton);
        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
    }

    private void setupRecyclerView() {
        resultsAdapter = new SimulationResultAdapter(simulationResults);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsRecyclerView.setAdapter(resultsAdapter);
    }

    private void startSimulation() {
        List<Product> allProducts = databaseHelper.getAllProducts();

        if (allProducts.isEmpty()) {
            Toast.makeText(this, "No products found for sale!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Stokta olan ürünleri filtrele
        List<Product> availableProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getStock() > 0) {
                availableProducts.add(product);
            }
        }

        if (availableProducts.isEmpty()) {
            Toast.makeText(this, "No products in stock!", Toast.LENGTH_SHORT).show();
            return;
        }

        // UI'ı simülasyon moduna geçir
        startSimulationButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        statusText.setText("Starting weekly sales simulation...");
        resultsRecyclerView.setVisibility(View.GONE);

        // Simülasyon verilerini sıfırla
        simulationResults.clear();
        totalRevenue = 0;
        totalItemsSold = 0;
        deletedProductsCount = 0;

        simulateWeek(availableProducts, 0);
    }

    private void simulateWeek(List<Product> products, int currentStep) {
        final int totalSteps = products.size() * 3;

        if (currentStep >= totalSteps) {
            finishSimulation();
            return;
        }

        int progress = (currentStep * 100) / totalSteps;
        progressBar.setProgress(progress);

        String[] statusMessages = {
                "Analyzing customer traffic...",
                "Calculating product demands...",
                "Processing sales transactions...",
                "Updating stocks...",
                "Finalizing revenue calculations..."
        };

        statusText.setText(statusMessages[currentStep % statusMessages.length]);

        if (currentStep % 3 == 2) {
            int productIndex = currentStep / 3;
            if (productIndex < products.size()) {
                simulateProductSales(products.get(productIndex));
            }
        }

        handler.postDelayed(() -> simulateWeek(products, currentStep + 1),
                300 + random.nextInt(500));
    }

    private void simulateProductSales(Product product) {
        double profitMargin = (product.getPrice() - product.getCost()) / product.getCost();
        double discountEffect = product.getDiscountAmount() / product.getPrice();

        double baseDemand = 0.7;

        double marginEffect;
        if (profitMargin <= 0.1) {
            marginEffect = 0.4;
        } else if (profitMargin <= 0.2) {
            marginEffect = 0.2;
        } else if (profitMargin <= 0.5) {
            marginEffect = 0.0;
        } else if (profitMargin <= 1.0) {
            marginEffect = -0.2;
        } else {
            marginEffect = -0.4;
        }

        double finalDiscountEffect = discountEffect * 0.5;

        double randomFactor = (random.nextDouble() - 0.5) * 0.6;

        double finalDemand = Math.max(0.1, Math.min(1.0,
                baseDemand + marginEffect + finalDiscountEffect + randomFactor));

        int maxSellable = (int) Math.ceil(product.getStock() * finalDemand);
        int quantitySold = Math.max(0, Math.min(maxSellable, product.getStock()));

        if (quantitySold > 0) {
            double quantityRandomness = 0.8 + (random.nextDouble() * 0.4);
            quantitySold = (int) Math.max(1, quantitySold * quantityRandomness);
            quantitySold = Math.min(quantitySold, product.getStock());
        }

        if (quantitySold > 0) {
            double unitPrice = product.getPrice() - product.getDiscountAmount();
            double revenue = quantitySold * unitPrice;

            int newStock = product.getStock() - quantitySold;
            product.setStock(newStock);

            if (newStock <= 0) {
                try {
                    long productId = getProductId(product);
                    boolean deleted = databaseHelper.deleteProduct(productId);
                    if (deleted) {
                        deletedProductsCount++;
                        System.out.println("Product deleted: " + product.getName() + " (Stock: 0)");
                    }
                } catch (Exception e) {
                    System.out.println("Product could not be deleted, updated as stock 0: " + product.getName());
                    databaseHelper.updateProduct(product);
                }
            } else {
                databaseHelper.updateProduct(product);
            }

            totalRevenue += revenue;
            totalItemsSold += quantitySold;
            currentBalance += revenue;

            String stockStatus = (newStock <= 0) ? "OUT OF STOCK - DELETED" : String.valueOf(newStock);

            SimulationResult result = new SimulationResult(
                    product.getName(),
                    product.getCategory(),
                    quantitySold,
                    unitPrice,
                    revenue,
                    newStock,
                    String.format("%.1f%%", finalDemand * 100),
                    newStock <= 0
            );
            simulationResults.add(result);
        }
    }

    private long getProductId(Product product) {
        return databaseHelper.getProductIdByName(product.getName());
    }

    private void finishSimulation() {
        progressBar.setVisibility(View.GONE);
        statusText.setText("Simulation completed!");
        balanceText.setText("New Balance: $" + String.format("%.2f", currentBalance));

        TextView summaryText = findViewById(R.id.summaryText);
        summaryText.setVisibility(View.VISIBLE);
        summaryText.setText(String.format(
                "📊 WEEKLY SALES SUMMARY\n" +
                        "💰 Total Revenue: $%.2f\n" +
                        "📦 Products Sold: %d items\n" +
                        "🏪 Active Products: %d types\n" +
                        "🗑️ Out Products: %d types",
                totalRevenue, totalItemsSold, simulationResults.size(), deletedProductsCount
        ));

        resultsRecyclerView.setVisibility(View.VISIBLE);
        resultsAdapter.notifyDataSetChanged();

        startSimulationButton.setEnabled(true);
        startSimulationButton.setText("Start New Simulation");

        String toastMessage = String.format("Simulation completed! Revenue: $%.2f", totalRevenue);
        if (deletedProductsCount > 0) {
            toastMessage += String.format("\n%d products deleted due to stock depletion!", deletedProductsCount);
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }

    private void finishWithResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("updatedBalance", currentBalance);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishWithResult();
    }

    public static class SimulationResult {
        private String productName;
        private String category;
        private int quantitySold;
        private double unitPrice;
        private double totalRevenue;
        private int remainingStock;
        private String demandRate;
        private boolean isDeleted;

        public SimulationResult(String productName, String category, int quantitySold,
                                double unitPrice, double totalRevenue, int remainingStock,
                                String demandRate, boolean isDeleted) {
            this.productName = productName;
            this.category = category;
            this.quantitySold = quantitySold;
            this.unitPrice = unitPrice;
            this.totalRevenue = totalRevenue;
            this.remainingStock = remainingStock;
            this.demandRate = demandRate;
            this.isDeleted = isDeleted;
        }

        public String getProductName() { return productName; }
        public String getCategory() { return category; }
        public int getQuantitySold() { return quantitySold; }
        public double getUnitPrice() { return unitPrice; }
        public double getTotalRevenue() { return totalRevenue; }
        public int getRemainingStock() { return remainingStock; }
        public String getDemandRate() { return demandRate; }
        public boolean isDeleted() { return isDeleted; }

        public String getStockStatus() {
            return isDeleted ? "OUT OF STOCK - DELETED" : String.valueOf(remainingStock);
        }
    }
}

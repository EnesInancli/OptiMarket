package com.example.optimarket;

import android.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    // Constructor
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    // ViewHolder:xml'deki ögeleri bağlar
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, cost, profit, stock, discount, category;
        Button editButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            cost = itemView.findViewById(R.id.productCost);
            profit = itemView.findViewById(R.id.productProfit);
            stock = itemView.findViewById(R.id.productStock);
            discount = itemView.findViewById(R.id.productDiscount);
            category = itemView.findViewById(R.id.productCategory);
            editButton = itemView.findViewById(R.id.editButton);
        }

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product p = productList.get(position);

        holder.name.setText("Name: " + p.getName());
        holder.price.setText("Price: " + p.getPrice() + "₺");
        holder.cost.setText("Cost: " + p.getCost() + "₺");
        holder.profit.setText("Profit: " + p.getProfit() + "₺");
        holder.stock.setText("Stock: " + p.getStock());
        holder.discount.setText("Discount: " + p.getDiscountAmount());
        holder.category.setText("Category: " + p.getCategory());

        holder.editButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Update Stock & Discount");

            LinearLayout layout = new LinearLayout(v.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 40, 50, 10);

            EditText stockInput = new EditText(v.getContext());
            stockInput.setHint("Stock");
            stockInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            layout.addView(stockInput);

            EditText discountInput = new EditText(v.getContext());
            discountInput.setHint("Discount Amount");
            discountInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            layout.addView(discountInput);

            builder.setView(layout);

            builder.setPositiveButton("Update", (dialog, which) -> {
                String stockStr = stockInput.getText().toString();
                String discountStr = discountInput.getText().toString();

                if (!stockStr.isEmpty() && !discountStr.isEmpty()) {
                    int newStock = Integer.parseInt(stockStr);
                    double newDiscount = Double.parseDouble(discountStr);

                    // Ürün güncelleniyor
                    Product selectedProduct = productList.get(holder.getAdapterPosition());
                    selectedProduct.setStock(newStock);
                    selectedProduct.setDiscountAmount(newDiscount);

                    // DATABASE’E GÜNCELLE
                    DatabaseHelper dbHelper = new DatabaseHelper(v.getContext());
                    dbHelper.updateProduct(selectedProduct);   //stok ve indirimi güncelle

                    notifyItemChanged(holder.getAdapterPosition()); // Ekranı yenile
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

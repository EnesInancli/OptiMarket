package com.example.optimarket;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SimulationResultAdapter extends RecyclerView.Adapter<SimulationResultAdapter.ResultViewHolder> {

    private List<SimulationActivity.SimulationResult> results;

    public SimulationResultAdapter(List<SimulationActivity.SimulationResult> results) {
        this.results = results;
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView productName, category, quantitySold, unitPrice, totalRevenue, remainingStock, demandRate, profitIndicator;

        public ResultViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.resultProductName);
            category = itemView.findViewById(R.id.resultCategory);
            quantitySold = itemView.findViewById(R.id.resultQuantitySold);
            unitPrice = itemView.findViewById(R.id.resultUnitPrice);
            totalRevenue = itemView.findViewById(R.id.resultTotalRevenue);
            remainingStock = itemView.findViewById(R.id.resultRemainingStock);
            demandRate = itemView.findViewById(R.id.resultDemandRate);
            profitIndicator = itemView.findViewById(R.id.profitIndicator);
        }
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simulation_result_item, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        SimulationActivity.SimulationResult result = results.get(position);

        holder.productName.setText(result.getProductName());
        holder.category.setText(result.getCategory().toUpperCase());
        holder.quantitySold.setText(String.valueOf(result.getQuantitySold()));
        holder.unitPrice.setText("$" + String.format("%.2f", result.getUnitPrice()));
        holder.totalRevenue.setText("$" + String.format("%.2f", result.getTotalRevenue()));
        holder.remainingStock.setText(String.valueOf(result.getRemainingStock()));
        holder.demandRate.setText(result.getDemandRate());

        // Performance indicator
        if (result.getTotalRevenue() >= 10.0) {
            holder.profitIndicator.setText("🔥 HOT");
            holder.profitIndicator.setTextColor(Color.parseColor("#FF4444"));
        } else if (result.getTotalRevenue() >= 5.0) {
            holder.profitIndicator.setText("📈 GOOD");
            holder.profitIndicator.setTextColor(Color.parseColor("#44AA44"));
        } else if (result.getTotalRevenue() > 0) {
            holder.profitIndicator.setText("📊 OK");
            holder.profitIndicator.setTextColor(Color.parseColor("#FFAA00"));
        } else {
            holder.profitIndicator.setText("📉 LOW");
            holder.profitIndicator.setTextColor(Color.parseColor("#888888"));
        }

        // Stock warning
        if (result.getRemainingStock() == 0) {
            holder.remainingStock.setTextColor(Color.parseColor("#FF4444"));
            holder.remainingStock.setText("0 (OUT)");
        } else if (result.getRemainingStock() <= 5) {
            holder.remainingStock.setTextColor(Color.parseColor("#FFAA00"));
        } else {
            holder.remainingStock.setTextColor(Color.parseColor("#333333"));
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
package com.example.start;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PillAdapter extends RecyclerView.Adapter<PillAdapter.PillHolder> {
    private List<Pill> pills = new ArrayList<>();

    @NonNull
    @Override
    public PillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pill_item, parent, false);
        return new PillHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PillHolder holder, int position) {
        Pill currentPill = pills.get(position);
        holder.name.setText(currentPill.name);
        holder.details.setText("Дозировка: " + currentPill.dose + ", Время: " + currentPill.time);
    }

    @Override
    public int getItemCount() {
        return pills.size();
    }

    public void setPills(List<Pill> pills) {
        this.pills = pills;
        notifyDataSetChanged();
    }

    static class PillHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView details;

        public PillHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            details = itemView.findViewById(R.id.details);
        }
    }
}
package com.example.cafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {

    private List<FoodModel> foodItemList;
    private Context context;

    public FoodItemAdapter(Context context, List<FoodModel> foodItemList) {
        this.context = context;
        this.foodItemList = foodItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodModel foodItem = foodItemList.get(position);

        holder.itemNameTextView.setText(foodItem.getItemName());
        holder.itemDescriptionTextView.setText(foodItem.getItemdesc());
        holder.itemPriceTextView.setText(foodItem.getItemprice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click
                // You can open a detailed activity or perform other actions
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemDescriptionTextView;
        TextView itemPriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.foodname);
            itemDescriptionTextView = itemView.findViewById(R.id.fooddesc);
            itemPriceTextView = itemView.findViewById(R.id.foodprice);
        }
    }
}

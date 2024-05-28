// CartlistAdapter.java
package com.example.cafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartlistAdapter extends RecyclerView.Adapter<CartlistAdapter.CartItemViewHolder> {

    private List<CartItem> cartItems;

    public CartlistAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // ViewHolder for the RecyclerView
    public class CartItemViewHolder extends RecyclerView.ViewHolder {

        private TextView cartItemNameTextView;
        private TextView cartItemDescriptionTextView;
        private TextView cartItemPriceTextView;
        private ImageView cartItemRemoveImageView;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemNameTextView = itemView.findViewById(R.id.cartItemNameTextView);
            cartItemDescriptionTextView = itemView.findViewById(R.id.cartItemDescriptionTextView);
            cartItemPriceTextView = itemView.findViewById(R.id.cartItemPriceTextView);
            cartItemRemoveImageView = itemView.findViewById(R.id.cartItemRemoveImageView);
        }

        public void bind(CartItem cartItem) {
            // Bind data to views
            cartItemNameTextView.setText(cartItem.getName());
            cartItemDescriptionTextView.setText(cartItem.getDescription());
            cartItemPriceTextView.setText(String.format("$%.2f", cartItem.getPrice()));

            // You can add click listeners or any other logic here
            // For example, to remove an item when the remove button is clicked:
            cartItemRemoveImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Remove the item from the cart
                    Cart.getInstance().removeItem(cartItem);

                    // Notify the adapter that the data has changed
                    notifyDataSetChanged();

                    // Call the method using the activity context
                    ((Cartlist) v.getContext()).updateTotalPrice();
                }
            });
        }
    }
}

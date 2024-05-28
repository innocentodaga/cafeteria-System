package com.example.cafe;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    TextView textView, item1addtocart, newprice, mealdescription, mealname;
    ImageView mealimg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.formerprice);
        ImageView imageView = view.findViewById(R.id.fav_icon);

        mealimg = view.findViewById(R.id.homefavimg1);
        mealdescription = view.findViewById(R.id.homefavdesc1);
        mealname = view.findViewById(R.id.homefavname1);
        newprice = view.findViewById(R.id.homefavprice1);
        item1addtocart = view.findViewById(R.id.deepchickenaddtocart);

        // Adding to cart
        HorizontalScrollView favCardsScrollView = view.findViewById(R.id.fav_cards);

        // Inside the onClickListener for the "Add to cart" button (deepchickenaddtocart, for example)
        TextView addToCartButton = view.findViewById(R.id.deepchickenaddtocart);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iterate through the child views of the LinearLayout inside the HorizontalScrollView
                LinearLayout favCardsLayout = favCardsScrollView.findViewById(R.id.fav_cards_layout);
                for (int i = 0; i < favCardsLayout.getChildCount(); i++) {
                    View childView = favCardsLayout.getChildAt(i);

                    // Extract information from the child view and create a CartItem
                    String itemName = ((TextView) childView.findViewById(R.id.homefavname1)).getText().toString();
                    String itemDescription = ((TextView) childView.findViewById(R.id.homefavdesc1)).getText().toString();
                    double itemPrice = Double.parseDouble(((TextView) childView.findViewById(R.id.homefavprice1)).getText().toString().replace("$", ""));
                    int itemImageResourceId = R.drawable.fav1; // You may need to change this based on how you store image resources

                    // Create a CartItem based on the extracted information
                    CartItem cartItem = new CartItem(itemName, itemDescription, itemPrice, itemImageResourceId);

                    // Add the item to the Cart
                    Cart.getInstance().addItem(cartItem);
                    addToCart();
                }

                // Optionally, you can update your UI or show a message indicating the items were added to the cart
                Toast.makeText(getContext(), "Items added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        // Traversing to the fav activities
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Favourites.class);
            startActivity(intent);
        });

        Button button = view.findViewById(R.id.order_now);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrderNow.class);
            startActivity(intent);
        });

        ImageView newlyadded = view.findViewById(R.id.newly_icon);
        newlyadded.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewlyAdded.class);
            startActivity(intent);
        });

        item1addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add any actions you want to perform when item1addtocart is clicked
                showSuccessDialog();
            }
        });

        return view;
    }

    private void addToCart() {
        CartItem cartItem = new CartItem("Coffee", "Each cup of coffee is a journey through a world of flavors and sensations.", 3.99, R.drawable.coff);
        Cart.getInstance().addItem(cartItem);

        // Show a toast to indicate that the coffee is added to the cart
        Toast.makeText(getContext(), "Added succesfully", Toast.LENGTH_SHORT).show();
    }

    private void showSuccessDialog() {
        // Initialize view before accessing its ID
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.sucess_dialog, null);
        ConstraintLayout constraintLayout = view.findViewById(R.id.success_dialog);
        Button success_done = view.findViewById(R.id.success_done);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        success_done.findViewById(R.id.success_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }
}

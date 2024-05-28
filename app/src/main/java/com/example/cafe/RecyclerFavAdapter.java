package com.example.cafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFavAdapter extends RecyclerView.Adapter<RecyclerFavAdapter.ViewHolder> {

    Context context;
    List<FavModel> favModelList;
   public RecyclerFavAdapter(Context context, List<FavModel> favModelList){
        this.context = context;
        this.favModelList = favModelList;
    }

    @NonNull
    @Override
    public RecyclerFavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View favmeals = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_card,parent,false);
        return new ViewHolder(favmeals);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerFavAdapter.ViewHolder holder, int position) {
       FavModel favModel = favModelList.get(position);
        holder.favmealname.setText(favModel.getName());
        holder.favmealdesc.setText(favModel.getDescription());
        holder.favmealprice.setText(favModel.getNewPrice());
        holder.favmealtype.setText(favModel.getMealtype());

    }

    @Override
    public int getItemCount() {
        return favModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView favmealname, favmealdesc, favmealprice, favmealtype;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favmealname = itemView.findViewById(R.id.favfood1);
            favmealdesc = itemView.findViewById(R.id.favdescreiption1);
            favmealprice = itemView.findViewById(R.id.newPrice1);
            favmealtype = itemView.findViewById(R.id.favmealtype);

        }
    }

}


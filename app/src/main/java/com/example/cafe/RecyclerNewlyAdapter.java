package com.example.cafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerNewlyAdapter extends RecyclerView.Adapter<RecyclerNewlyAdapter.NewMealsHolder>{
    Context context;
    List<NewlyModel> modelList;

    public RecyclerNewlyAdapter(Context context, List<NewlyModel> modelList){
        this.context = context;
        this.modelList= modelList;
    }

    @NonNull
    @Override
    public NewMealsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newmeals = LayoutInflater.from(parent.getContext()).inflate(R.layout.newlyadded_card,parent, false);
        return new NewMealsHolder(newmeals);
    }

    @Override
    public void onBindViewHolder(@NonNull NewMealsHolder holder, int position) {
        NewlyModel newlyModel = modelList.get(position);
        holder.newmealname.setText(newlyModel.getName());
        holder.newmealdesc.setText(newlyModel.getDescription());
        holder.newmealprice.setText(newlyModel.getNewPrice());
        holder.newmealtype.setText(newlyModel.getMealtype());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class NewMealsHolder extends RecyclerView.ViewHolder{
        TextView newmealname, newmealdesc, newmealprice, newmealtype;
        public NewMealsHolder(@NonNull View itemView) {
            super(itemView);
            newmealname = itemView.findViewById(R.id.newlyfood1);
            newmealdesc = itemView.findViewById(R.id.newlydescreiption1);
            newmealprice = itemView.findViewById(R.id.newlynewPrice1);
            newmealtype = itemView.findViewById(R.id.newlymealtype);

        }

    }

}


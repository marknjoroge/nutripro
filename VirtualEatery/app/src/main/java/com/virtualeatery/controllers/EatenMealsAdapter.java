package com.virtualeatery.controllers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualeatery.R;
import com.virtualeatery.models.db.DBHandler;
import com.virtualeatery.models.db.MealsTakenModel;
import com.virtualeatery.models.db.NutrientsModel;

import java.util.ArrayList;

public class EatenMealsAdapter extends RecyclerView.Adapter<EatenMealsAdapter.ViewHolder> {

    private final ArrayList<MealsTakenModel> mealsTakenModelArraylist;
    private final Context context;
    private final DBHandler dbHandler;


    public EatenMealsAdapter(ArrayList<MealsTakenModel> mealsTakenModelArraylist, Context context) {
        this.mealsTakenModelArraylist = mealsTakenModelArraylist;
        this.context = context;
        dbHandler = new DBHandler(context);
        String TAG = "EatenMealsAdapter";
        Log.v(TAG, mealsTakenModelArraylist.toString());
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meals_taken_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EatenMealsAdapter.ViewHolder holder,
                                 int position) {

        MealsTakenModel modal = mealsTakenModelArraylist.get(position);

        NutrientsModel nutrientsModel = dbHandler.getFoodInfo(modal.getMeal());

        holder.date.setText(modal.getTheDate());
        holder.mealsTaken.setText(modal.getMeal());

        holder.calories.setText(new StringBuilder().append(":  ").append(nutrientsModel.getCalories()).append(" cals").toString());
        holder.carbs.setText(new StringBuilder().append(":  ").append(nutrientsModel.getCarbs()).append(" g").toString());
        holder.fats.setText(new StringBuilder().append(":  ").append(nutrientsModel.getFats()).append(" g").toString());
        holder.proteins.setText(new StringBuilder().append(":  ").append(nutrientsModel.getProteins()).append(" g").toString());

        holder.edit.setOnClickListener(v -> {
            dbHandler.deleteFoodEntry(modal.getId());
            mealsTakenModelArraylist.remove(position);
            Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();
            notifyItemRemoved(position);
        });
    }

    public int getItemCount() {
        return mealsTakenModelArraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mealsTaken, calories, fats, proteins, date, carbs;
        private final Button edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            edit = itemView.findViewById(R.id.editMealInfo);
            mealsTaken = itemView.findViewById(R.id.mealsTaken);
            calories = itemView.findViewById(R.id.caloriesTaken);
            carbs = itemView.findViewById(R.id.carbsTaken);
            fats = itemView.findViewById(R.id.fatsTaken);
            proteins = itemView.findViewById(R.id.proteinsTaken);
            date = itemView.findViewById(R.id.mealDate);
        }
    }
}
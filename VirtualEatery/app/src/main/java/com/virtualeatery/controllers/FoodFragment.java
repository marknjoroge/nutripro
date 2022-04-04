package com.virtualeatery.controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.virtualeatery.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

    private String[] mealNames = { "Cabbage", "Fries", "Chicken",
            "Steak", "Crab", "Burger", "Water", "Fried Rice" };

    private String[] mealPrices = { "$5", "$5", "$10",
            "$20", "$30", "$5", "$5", "$8" };

    int[] images = {
            R.drawable.cabbage,
            R.drawable.fries,
            R.drawable.chicken,
            R.drawable.steak,
            R.drawable.crab,
            R.drawable.burger,
            R.drawable.water,
            R.drawable.rice
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container,false);

        Log.d("food", "started");

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("name", mealNames[i]);
            hm.put("price", mealPrices[i]);
            hm.put("image", Integer.toString(images[i]) );
            aList.add(hm);
        }

        String[] from = { "name","price","image" };

        int[] to = { R.id.foodName, R.id.foodPrice, R.id.foodPic };

        ListView list = (ListView) v.findViewById(R.id.mealsavailable);
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.meal_card, from, to);
        list.setAdapter(adapter);


        list.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getContext(), position + "   " + id, Toast.LENGTH_LONG).show();
            Log.d("frag1", "touched " + position);
        });
        return v;
    }
}
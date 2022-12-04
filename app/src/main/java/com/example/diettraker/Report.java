package com.example.diettraker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Report extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_report, container, false);

        DatabaseHandler db = new DatabaseHandler(view.getContext());
        List<Meal> meals = db.getSummary();

        ListView lvList = view.findViewById(R.id.lvListSummary);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            list.add(
                    meals.get(i).get_date()
                            + " | Cal: " + meals.get(i).get_calories()
                            + " | C: " + meals.get(i).get_carbohydrates()
                            + " | F: " + meals.get(i).get_fats()
                            + " | P: " + meals.get(i).get_proteins()
            );
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                view.getContext(),
                android.R.layout.simple_list_item_1,
                list );

        lvList.setAdapter(arrayAdapter);

        return view;
    }
}
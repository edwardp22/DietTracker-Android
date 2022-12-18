package com.example.diettraker;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment {
    ListView lvList;
    List<Meal> meals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_layout, container, false);

        DatabaseHandler db = new DatabaseHandler(view.getContext());

        meals = db.getAllMeals();

        lvList = view.findViewById(R.id.lvList);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            list.add(
                    meals.get(i).get_date()
                            + " " + meals.get(i).get_time()
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

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", meals.get(i).get_id() );

                Editor fragInfo = new Editor();
                fragInfo.setArguments(bundle);

                loadFragment(fragInfo);
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}

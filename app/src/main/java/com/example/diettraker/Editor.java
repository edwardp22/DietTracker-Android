package com.example.diettraker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Editor extends Fragment {
    Button btnUpdate, btnDelete, btnDate, btnTime;
    EditText edtCalories, edtCarbohydrates, edtFat, edtProteins, edtDate, edtTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_editor, container, false);

        int id = getArguments().getInt("id");

        DatabaseHandler db = new DatabaseHandler(view.getContext());
        Meal meal = db.getMeal(id);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnDate = view.findViewById(R.id.btnDateEditor);
        btnTime = view.findViewById(R.id.btnTimeEditor);
        edtCalories = view.findViewById(R.id.edtCaloriesEditor);
        edtCarbohydrates = view.findViewById(R.id.edtCarbohydratesEditor);
        edtFat = view.findViewById(R.id.edtFatEditor);
        edtProteins = view.findViewById(R.id.edtProteinsEditor);
        edtDate = view.findViewById(R.id.edtDateEditor);
        edtTime = view.findViewById(R.id.edtTimeEditor);

        edtCalories.setText(String.valueOf(meal.get_calories()));
        edtCarbohydrates.setText(String.valueOf(meal.get_carbohydrates()));
        edtFat.setText(String.valueOf(meal.get_fats()));
        edtProteins.setText(String.valueOf(meal.get_proteins()));
        edtDate.setText(meal.get_date());
        edtTime.setText(meal.get_time());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDate = edtDate.getText().toString();
                String strTime = edtTime.getText().toString();
                String strCalories = edtCalories.getText().toString();
                String strCarbohydrates = edtCarbohydrates.getText().toString();
                String strFat = edtFat.getText().toString();
                String strProteins = edtProteins.getText().toString();

                if (
                        !strCalories.equals("") &&
                                !strCarbohydrates.equals("") &&
                                !strFat.equals("") &&
                                !strProteins.equals("") &&
                                !strDate.equals("") &&
                                !strTime.equals("")
                ) {
                    int calories = Integer.parseInt(strCalories);
                    int carbohydrates = Integer.parseInt(strCarbohydrates);
                    int fat = Integer.parseInt(strFat);
                    int proteins = Integer.parseInt(strProteins);

                    Meal newMeal = new Meal(
                            meal.get_id(),
                            strDate,
                            strTime,
                            calories,
                            carbohydrates,
                            fat,
                            proteins
                    );
                    db.updateMeal(newMeal);

                    edtDate.setText("");
                    edtTime.setText("");
                    edtCalories.setText("");
                    edtCarbohydrates.setText("");
                    edtFat.setText("");
                    edtProteins.setText("");
                    Toast.makeText(view.getContext(), "Meal Saved", Toast.LENGTH_LONG).show();
                    loadFragment(new History());
                }
                else {
                    Toast.makeText(view.getContext(), "There are missing information, please fill all fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure to delete this record?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteMeal(meal);
                        Toast.makeText(view.getContext(), "Meal Deleted", Toast.LENGTH_LONG).show();
                        loadFragment(new History());
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day
                );

                datePickerDialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                edtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false
                );

                timePickerDialog.show();
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
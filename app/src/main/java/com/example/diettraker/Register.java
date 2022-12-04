package com.example.diettraker;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
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

public class Register extends Fragment {
    Button btnSaveMeal, btnDate, btnTime;
    EditText edtCalories, edtCarbohydrates, edtFat, edtProteins, edtDate, edtTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register, container, false);

        DatabaseHandler db = new DatabaseHandler(view.getContext());

        btnSaveMeal = view.findViewById(R.id.btnSaveMeal);
        btnDate = view.findViewById(R.id.btnDate);
        btnTime = view.findViewById(R.id.btnTime);
        edtCalories = view.findViewById(R.id.edtCalories);
        edtCarbohydrates = view.findViewById(R.id.edtCarbohydrates);
        edtFat = view.findViewById(R.id.edtFat);
        edtProteins = view.findViewById(R.id.edtProteins);
        edtDate = view.findViewById(R.id.edtDate);
        edtTime = view.findViewById(R.id.edtTime);

        btnSaveMeal.setOnClickListener(new View.OnClickListener() {
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
                            strDate,
                            strTime,
                            calories,
                            carbohydrates,
                            fat,
                            proteins
                    );
                    db.addMeal(newMeal);

                    edtDate.setText("");
                    edtTime.setText("");
                    edtCalories.setText("");
                    edtCarbohydrates.setText("");
                    edtFat.setText("");
                    edtProteins.setText("");
                    Toast.makeText(view.getContext(), "Meal Saved", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(view.getContext(), "There are missing information, please fill all fields.", Toast.LENGTH_LONG).show();
                }
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
}
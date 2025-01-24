package com.example.start;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AddPillActivity extends AppCompatActivity {

    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);

        EditText pillName = findViewById(R.id.pillNameEditText);
        EditText pillQuantity = findViewById(R.id.pillQuantityEditText);
        EditText pillDose = findViewById(R.id.pillDoseEditText);
        Button timePickerButton = findViewById(R.id.setTimeButton);
        Button saveButton = findViewById(R.id.saveButton);


        timePickerButton.setOnClickListener(v -> {
            TimePickerDialog timePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                timePickerButton.setText(selectedTime);
            }, 12, 0, true);
            timePicker.show();
        });


        saveButton.setOnClickListener(v -> {
            String name = pillName.getText().toString().trim();
            String quantityStr = pillQuantity.getText().toString().trim();
            String doseStr = pillDose.getText().toString().trim();

            if (name.isEmpty() ||  quantityStr.isEmpty() ||  doseStr.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                int dose = Integer.parseInt(doseStr);

                Pill pill = new Pill(name, quantity, dose, selectedTime);


                new Thread(() -> {
                    try {
                        PillDatabase.getInstance(this).pillDao().insert(pill);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Таблетка добавлена", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(this, "Ошибка при добавлении таблетки", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Количество и доза должны быть числами", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.start;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
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

        // Выбор времени
        timePickerButton.setOnClickListener(v -> {
            TimePickerDialog timePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                timePickerButton.setText(selectedTime);
            }, 12, 0, true);
            timePicker.show();
        });

        // Сохранение таблетки
        saveButton.setOnClickListener(v -> {
            String name = pillName.getText().toString().trim();
            String quantityStr = pillQuantity.getText().toString().trim();
            String doseStr = pillDose.getText().toString().trim();

            if (name.isEmpty() || quantityStr.isEmpty() || doseStr.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                int dose = Integer.parseInt(doseStr);

                // Сохранение таблетки в базу данных
                Pill pill = new Pill(name, quantity, dose, selectedTime);

                new Thread(() -> {
                    try {
                        PillDatabase.getInstance(this).pillDao().insert(pill);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Таблетка добавлена", Toast.LENGTH_SHORT).show();
                            scheduleNotification(pill); // Установка уведомления
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

    // Установка уведомления
    private void scheduleNotification(Pill pill) {
        String[] timeParts = pill.getTime().split(":");
        if (timeParts.length != 2) {
            Toast.makeText(this, "Неверный формат времени", Toast.LENGTH_SHORT).show();
            return;
        }

        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("PILL_NAME", pill.getName());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, pill.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}

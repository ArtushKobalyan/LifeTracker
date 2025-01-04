package com.example.start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Установите макет для анимации
        setContentView(R.layout.activity_splash);

        // Задержка на 3 секунды
        new Handler().postDelayed(() -> {
            // После завершения анимации переходим на MainActivity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Завершаем SplashActivity
        }, 2400); // 3000 миллисекунд = 3 секунды
    }
}

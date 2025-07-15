package com.example.screenctrl;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.app.AppOpsManager;
import android.provider.Settings;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Проверка, есть ли разрешение
        if (!checkForUsageStatsPermission()) {
            // Intent - намерение, механизм для взаимодействия между компонентами приложения или с системой
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }


    }

    // Осуществление перехода к перехода к статистике
    public void toAppStatisticsActivity(View view) {
        Intent intent = new Intent(this, AppStatisticsActivity.class);
        startActivity(intent);
    }

    // Проверка на разрешение получения информации об использовании приложений с устройства
    private boolean checkForUsageStatsPermission() {
        // AppOpsManager для проверки получения разрешений на статистику использования приложений
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        // Запрос на проверку разрешения без выброса исключения
        int mode = appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), // Получение UID процесса приложения
                getPackageName()
        );
        return mode == AppOpsManager.MODE_ALLOWED;
    }
}
package com.example.screenctrl;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppStatisticsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_layout);
    }

    // Получение информации об использовании
    private void printStatistics() {
        // Достаем данные об использовании
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1); // Статистика за последние 7 дней

        List<UsageStats> stats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                calendar.getTimeInMillis(),
                System.currentTimeMillis()
        );

//        TextView textViewStats = findViewById(R.id.textViewStats);
        StringBuilder statsText = new StringBuilder();

        for (UsageStats usageStats : stats) {
            if (usageStats.getTotalTimeInForeground() > 0) {
                String packageName = usageStats.getPackageName();
                long minutes = usageStats.getTotalTimeInForeground() / 1000 / 60;
//                int launches = usageStats.getLaunchCount();
                Date date = new Date(usageStats.getLastTimeUsed());
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String time = sdf.format(date); // "22.02.2023 09:51"

                statsText.append("Приложение: ").append(packageName)
                        .append("\nВремя: ").append(minutes).append(" мин")
                        .append("\nЗапуски: ").append(time)
                        .append("\n\n");
            }
        }
//        textViewStats.setText(statsText.toString());
    }

}

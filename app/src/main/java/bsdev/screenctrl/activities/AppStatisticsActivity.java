package bsdev.screenctrl.activities;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import bsdev.screenctrl.R;
import bsdev.screenctrl.entity.AppInfo;
import bsdev.screenctrl.adapters.AppInfoAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppStatisticsActivity extends AppCompatActivity {
    ArrayList<AppInfo> appsInfo;
    ListView appListView;

    protected void onCreate(Bundle savedInstanceState) {
        // Запуск активности
        super.onCreate(savedInstanceState);
        // Установка макета
        setContentView(R.layout.statistics_layout);

        appsInfo = new ArrayList<>();
        try {
            transferStatisticsToList();
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        // получаем элемент ListView
        appListView = findViewById(R.id.appList);
        // создаем адаптер
        AppInfoAdapter stateAdapter = new AppInfoAdapter(this, R.layout.app_info_layout, appsInfo);
        // устанавливаем адаптер
        appListView.setAdapter(stateAdapter);
    }

    // Получение информации об использовании
    private void transferStatisticsToList() throws PackageManager.NameNotFoundException {
        // Достаем данные об использовании
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        // Устанавливаем текущее время
        Calendar calendar = Calendar.getInstance();
        // Вычитаем неделю
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        // Берем статистику от недели назад до текущего времени
        List<UsageStats> stats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                calendar.getTimeInMillis(),
                System.currentTimeMillis()
        );

        for (UsageStats usageStats : stats) {
            if (usageStats.getTotalTimeInForeground() > 0) {
                // Собираем необходимые данные
                String packageName = usageStats.getPackageName();
                long periodTotalUse = usageStats.getTotalTimeInForeground();
                Date date = new Date(usageStats.getLastTimeUsed());
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String time = sdf.format(date);
                Drawable icon;
                try {
                    PackageManager packageManager = getPackageManager();
                    icon = packageManager.getApplicationIcon(packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    icon = ContextCompat.getDrawable(this, R.drawable.ic_launcher_background);
                }

                appsInfo.add(new AppInfo(packageName, icon));
//                appsInfo.addAppInfo(packageName, periodTotalUse, time);
            }
        }
    }
}

package bsdev.screenctrl.activities;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import bsdev.screenctrl.R;
import bsdev.screenctrl.container.AppInfo;
import bsdev.screenctrl.adapters.AppInfoAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AppStatisticsActivity extends AppCompatActivity {
    ArrayList<AppInfo> appsInfo;
    ListView appListView;

    protected void onCreate(Bundle savedInstanceState) {
        // Запуск активности
        super.onCreate(savedInstanceState);
        // Установка макета
        setContentView(R.layout.statistics_layout);
        // Меню выбора
        setSupportActionBar(findViewById(R.id.toolbar));

        appsInfo = new ArrayList<>();
        transferStatisticsToList("Неделя");
        // получаем элемент ListView
        appListView = findViewById(R.id.appList);
        // создаем адаптер
        AppInfoAdapter stateAdapter = new AppInfoAdapter(this, R.layout.app_info_layout, appsInfo);
        // устанавливаем адаптер
        appListView.setAdapter(stateAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistics_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        appsInfo.clear();
        transferStatisticsToList(item.getTitle().toString());
        AppInfoAdapter stateAdapter = new AppInfoAdapter(this, R.layout.app_info_layout, appsInfo);
        appListView.setAdapter(stateAdapter);
        return super.onOptionsItemSelected(item);
    }

    // Получение информации об использовании
    private void transferStatisticsToList(String period) {
        // Достаем данные об использовании
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        // Устанавливаем текущее время
        Calendar calendar = Calendar.getInstance();

        switch (period) {
            case "День":
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                break;
            case "Неделя":
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                break;
            case "Год":
                calendar.add(Calendar.YEAR, -1);
                break;
        }

        // Берем статистику
        Map<String, UsageStats> stats = usageStatsManager.queryAndAggregateUsageStats(
                calendar.getTimeInMillis(),
                System.currentTimeMillis()
        );

        for (String key : stats.keySet()) {
            UsageStats usageStats = stats.get(key);
            if (usageStats.getTotalTimeInForeground() > 0) {
                // Собираем необходимые данные
                String packageName = usageStats.getPackageName();
                long periodTotalUse = usageStats.getTotalTimeInForeground();
                Date date = new Date(usageStats.getLastTimeUsed());
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String datetime = sdf.format(date);
                Drawable icon;
                try {
                    PackageManager packageManager = getPackageManager();
                    icon = packageManager.getApplicationIcon(packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    icon = ContextCompat.getDrawable(this, R.drawable.ic_launcher_background);
                }

                appsInfo.add(new AppInfo(packageName, icon, periodTotalUse, datetime));
            }
        }
    }
}

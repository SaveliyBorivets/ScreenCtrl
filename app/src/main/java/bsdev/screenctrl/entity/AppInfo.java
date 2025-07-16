package bsdev.screenctrl.entity;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Date;

public class AppInfo {
    private String appName;
    private Drawable appIcon;
    private ArrayList<Long> appDayUse;
    private ArrayList<String> appUseDates;

    /**
     * Конструктор, запрашивающий название приложения
     */
    public AppInfo(String packageName, Drawable icon) {
        appName = packageName;
        appIcon = icon;
    }

    /**
     * Метод, добавляющий посещение
     *
     * @param dayUse использование за день
     * @param useDate дата использования
     */
    public void addDayInfo(Long dayUse, String useDate) {
        appDayUse.add(dayUse);
        appUseDates.add(useDate);
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() { return appIcon; }

    /**
     * Метод, возвращающий последнее посещение приложения
     *
     * @return Дата последнего посещения(Date)
     */
    public String getLastDateUsed() {
        return appUseDates.get(appUseDates.size() - 1);
    }
}

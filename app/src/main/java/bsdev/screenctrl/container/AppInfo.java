package bsdev.screenctrl.container;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String appName;
    private Drawable appIcon;
    private Long appUseTime;
    private String appLastDayUse;

    public AppInfo(String packageName, Drawable icon, Long useTime, String useDate) {
        appName = packageName;
        appIcon = icon;
        appUseTime = useTime;
        appLastDayUse = useDate;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() { return appIcon; }

    public Long getAppUseTimeInMinutes() { return appUseTime / 1000 / 60; }

    public String getAppLastDayUse() {
        return appLastDayUse;
    }
}

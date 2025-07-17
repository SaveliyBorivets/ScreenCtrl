package bsdev.screenctrl.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import bsdev.screenctrl.R;

import java.util.List;
import bsdev.screenctrl.container.AppInfo;

public class AppInfoAdapter extends ArrayAdapter<AppInfo> {
    private LayoutInflater inflater; // Для хранения созданного макета
    private int layout;
    private List<AppInfo> appInformation;

    public AppInfoAdapter(Context context, int resource, List<AppInfo> infos) {
        super(context, resource, infos);
        this.appInformation = infos;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView flagView = view.findViewById(R.id.appImage);
        TextView nameView = view.findViewById(R.id.appName);
        TextView description = view.findViewById(R.id.appInfo);

        AppInfo app = appInformation.get(position);

        flagView.setImageDrawable(app.getIcon());
        nameView.setText(app.getAppName());
        description.setText("Проведено: " + app.getAppUseTimeInMinutes() + " мин.\n" +
                            "Последнее посещение: " + app.getAppLastDayUse());

        return view;
    }
}
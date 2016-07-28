package me.navnit.deviceslist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.navnit.deviceslist.R;
import me.navnit.deviceslist.models.Device;

/**
 * Created by dpadmanavaneethan on 7/27/16.
 */
public class DevicesAdapter extends ArrayAdapter<Device> {

    public DevicesAdapter(Context context, List<Device> devices) {
        super(context, 0, devices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Device device = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.device_list_item, parent, false);
        }

        TextView textName = (TextView) convertView.findViewById(R.id.textView);

        textName.setText(device.getName());

        return convertView;
    }
}

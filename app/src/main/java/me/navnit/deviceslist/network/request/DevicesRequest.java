package me.navnit.deviceslist.network.request;

import android.content.Context;

import me.navnit.deviceslist.network.BaseRequest;

/**
 * Created by dpadmanavaneethan on 7/28/16.
 */
public class DevicesRequest extends BaseRequest {

    public DevicesRequest(Context context) {
        super(context);
        serviceEndPoint = "/devices.json";
    }

}

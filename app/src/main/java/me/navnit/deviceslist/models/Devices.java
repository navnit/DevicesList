package me.navnit.deviceslist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpadmanavaneethan on 7/27/16.
 */
public class Devices {

    @SerializedName("devices")
    @Expose
    private List<Device> devices = new ArrayList<>();

    /**
     * @return The devices
     */
    public List<Device> getDevices() {
        return devices;
    }

    /**
     * @param devices The devices
     */
    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

}
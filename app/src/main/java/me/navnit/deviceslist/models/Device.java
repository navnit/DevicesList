package me.navnit.deviceslist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dpadmanavaneethan on 7/27/16.
 */

public class Device {

    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("name")
    @Expose
    private String name;

    public Device() {
    }

    public Device(String name) {
        this.name = name;
    }

    /**
     * @return The deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType The deviceType
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return The model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model The model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

}
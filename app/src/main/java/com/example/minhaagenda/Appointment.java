package com.example.minhaagenda;

import java.util.UUID;

public class Appointment {
    private UUID mUUID;
    private String mData;
    private String mTime;
    private String mDescription;

    public Appointment(String data, String time, String description) {
        mData = data;
        mTime = time;
        mDescription = description;
        mUUID = UUID.randomUUID();
    }

    public Appointment(UUID uuid, String data, String time, String description) {
        mData = data;
        mTime = time;
        mDescription = description;
        mUUID = uuid;
    }

    public String getData() {
        return mData;
    }

    public String getTime() {
        return mTime;
    }

    public String getDescription() {
        return mDescription;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID uuid) {
        mUUID = uuid;
    }

    public void setData(String data) {
        mData = data;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}

package com.example.minhaagenda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Schedule {
    private ArrayList<Appointment> mAppointments;

    public Schedule() {
        mAppointments = new ArrayList<>();
    }

    public void addAppointment(Appointment appointment) {
        mAppointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        mAppointments.removeIf(a -> a.getUUID().equals(appointment.getUUID()));
    }

    public List<Appointment> getByDate(Date date) {
        return mAppointments.stream().filter(a -> a.getData().equals(date)).collect(Collectors.toList());
    }
}

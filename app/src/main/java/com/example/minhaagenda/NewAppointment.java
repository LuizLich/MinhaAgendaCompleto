package com.example.minhaagenda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class NewAppointment extends Fragment implements AutoCloseable {

    AppointmentDB db;

    EditText dateEdt;
    EditText timeEdt;
    EditText descriptionEdt;

    Button btnSave;

    public NewAppointment() { }

    public static NewAppointment newInstance() {
        NewAppointment fragment = new NewAppointment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new AppointmentDB(getContext());

        View view = inflater.inflate(R.layout.fragment_novo_agendamento, container, false);
        dateEdt = (EditText)view.findViewById(R.id.edt_data);
        timeEdt = (EditText)view.findViewById(R.id.edt_hora);
        descriptionEdt = (EditText)view.findViewById(R.id.descricao);
        btnSave = (Button)view.findViewById(R.id.btn_salvar);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Appointment appointment = new Appointment(dateEdt.getText().toString(), timeEdt.getText().toString(), descriptionEdt.getText().toString());
                    db.insertAppointment(appointment);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    ListAppointment listAppointments = null;
                    for(Fragment f : fm.getFragments()) {
                        if(f instanceof ListAppointment) {
                            listAppointments = (ListAppointment)f;
                            break;
                        }
                    }

                    if(listAppointments != null) {
                        listAppointments.searchAppointments();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateEdt.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear, year));
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        timeEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        timeEdt.setText(String.format("%02d:%02d", hour, minute));
                    }
                }, c.getTime().getHours(), c.getTime().getMinutes(), false);
                timePickerDialog.show();
            }
        });

        return view;
    }

    @Override
    public void close() throws Exception {
        db.close();
    }
}

package com.example.minhaagenda;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListAppointment#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

public class ListAppointment extends Fragment implements AutoCloseable {

    AppointmentDB db;
    ArrayList<String> appointments;

    EditText dateEdt;
    ListView appointmentList;
    Button searchButton;

    public ListAppointment() {
        appointments = new ArrayList<>();
    }

    public static ListAppointment newInstance() {
        ListAppointment fragment = new ListAppointment();
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

        View view = inflater.inflate(R.layout.fragment_listar_agendamentos, container, false);
        dateEdt = (EditText)view.findViewById(R.id.listar_edt_data);
        appointmentList = (ListView)view.findViewById(R.id.lst_agendamentos);
        searchButton = (Button)view.findViewById(R.id.btn_buscar);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAppointments();
            }
        });

        searchAppointments();

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
        return view;
    }

    public void searchAppointments() {
        appointments.clear();
        String date = dateEdt.getText().toString();

        ArrayList<Appointment> result = new ArrayList<>();

        if(date.length() > 0) {
            result = db.selectAppointments("date = ?", new String[]{ date });
        } else {
            result = db.selectAppointments();
        }

        for(int i = 0; i < result.size(); i++) {
            Appointment a = result.get(i);
            appointments.add(a.getDescription() + " - " + a.getData() + " - " + a.getTime());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.item_agendamento, appointments);
        appointmentList.setAdapter(adapter);
    }

    @Override
    public void close() throws Exception {
        db.close();
    }
}

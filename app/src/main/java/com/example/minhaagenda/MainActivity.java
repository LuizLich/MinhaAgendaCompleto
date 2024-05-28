package com.example.minhaagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ver fragmento 1
        getSupportFragmentManager().beginTransaction().add(R.id.container_novoAgendamento, new NewAppointment()).commit();
        //Ver fragmento 2
        getSupportFragmentManager().beginTransaction().add(R.id.container_listarAgendamento, new ListAppointment()).commit();
    }
}
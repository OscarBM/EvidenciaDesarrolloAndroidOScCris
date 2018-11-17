package com.tecmi.oscar.calendardemo3;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;

import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {
    //Marvel: https://marvelapp.com/45cj4d9/screen/48608874

    //Lista de abreviaciones
    /*
     -clv = ClaendarView
     -btn = Button
     -edt = EditText
     -tv = TextView
     */

    private CalendarView clvMain;//Se declara al calendari de la pantalla principal como variable
    //tal vez estas variables ya no sirvan
    private Button btnList;
    private Button btnAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Aqui se le da un valor al calendario de la pantalla principal y se le da la capacidad de poder hacer cosas al seleccionar cualquier dia
        clvMain = (CalendarView)findViewById(R.id.clvMain);
        clvMain.setOnDateChangeListener(this);


        //PRUEBA INICIO


        TextView tvMainToday = (TextView)findViewById(R.id.tvMainToday);
        //tvMainToday.setText(getMailId());
        tvMainToday.setText("que paso");
        //PRUEBA FIN

    }


    //Metodo para que se ejecuten acciones si se presiona cualquier dia en el calendario clvMain
    //@Override
    public void onSelectedDayChange (CalendarView calendarView, int i, int i1, int i2){

    }




    //Metodo para cuando se presione el botón + (el de añadir evento nuevo)
    //Sirve para a la pantalla de Agregar evento nuevo (AddEventActivity.java)
    public void onClickNewEvent(View v){
        Intent intent = new Intent(getApplication(), AddEventActivity.class);//Se crea un intent para poder pasar a la siguiente pantalla
        Bundle bundle = new Bundle();//Probablemente esta linea sea innecesaria

        intent.putExtras(bundle);//Probablemente esta linea sea innecesaria
        startActivity(intent);//Se inicia el intent para pasar a la pantalla de Agregar evento nuevo (AddEventActivity.java)
    }

    //Metodo para cuando se presione el botón LISTA DE EVENTOS (el de ver lista de todos los eventos)
    //Sirve para a la pantalla de Lista de eventos (ListActivity.java)
    public void onClickListEvents(View v){
        Intent intent = new Intent(getApplication(), ListActivity.class);//Se crea la intent para poder cambiar de pantalla
        startActivity(intent);//Se ejecuta la intent para pasar a la pantalla de Lista de eventos (ListActivity.java)
    }

}
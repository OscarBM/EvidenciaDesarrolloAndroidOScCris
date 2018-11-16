package com.tecmi.oscar.calendardemo3;

import android.Manifest;
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

public class MainActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener{

    //Marvel: https://marvelapp.com/45cj4d9/screen/48608874

    //Lista de abreviaciones
    /*
     -clv = ClaendarView
     -btn = Button
     -edt = EditText
     -tv = TextView
     */
    private CalendarView clvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clvMain = (CalendarView)findViewById(R.id.clvMain);
        clvMain.setOnDateChangeListener(this);
    }

    //@Override
    public void onSelectedDayChange (CalendarView calendarView, int i, int i1, int i2){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[3];
        items[0] = "Agregar evento";
        items[1] = "Ver eventos tuyos";
        items[2] = "Cancelar";

        final int day, month, year;
        day = i;
        month = i1+1;
        year = i2;

        builder.setTitle("Selecciona una tarea")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            //Actividad activity_add_event para añadir evento
                            Intent intent = new Intent(getApplication(), AddEventActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("day", day);
                            bundle.putInt("month", month);
                            bundle.putInt("year", year);

                            intent.putExtras(bundle);
                            startActivity(intent);

                        } else if (which == 1){
                            //Actividad activity_list para ver eventos



                            Intent intent = new Intent(getApplication(), ListActivity.class);

                            /*
                            Bundle bundle = new Bundle();
                            bundle.putInt("day", day);
                            bundle.putInt("month", month);
                            bundle.putInt("year", year);

                            intent.putExtras(bundle);*/
                            startActivity(intent);

                            Log.d("DEBUGOSCAR", "La pantalla si abre");

                        } else {
                            return;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }





}

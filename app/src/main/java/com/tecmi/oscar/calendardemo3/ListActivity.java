package com.tecmi.oscar.calendardemo3;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ListActivity extends AppCompatActivity /*implements View.OnLongClickListener*/{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TextView tvToday = (TextView)findViewById(R.id.tvToday);

        //Esto comprueba si la aplicción tiene permiso para leer el calendario
        try{

            //Este array de Strings permitira hacer consultas de eventos
            String[] mProjection =
                    {
                            "_id",
                            CalendarContract.Events.TITLE,
                            CalendarContract.Events.EVENT_LOCATION,
                            CalendarContract.Events.DTSTART,
                            //CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            CalendarContract.Events.DTEND,
                            CalendarContract.Events.DESCRIPTION
                    };

            ArrayList<String> arrPerm = new ArrayList<>();//Esta variable ayuda a añadir los permisos que no se hayan otorgado
            //Este if coprueba si la aplicación posee permiso para leer el calendario.
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
                arrPerm.add(Manifest.permission.READ_CALENDAR);
            }

            //En caso de que la app no tenga permiso de leer el calendario, le pregunta al usuario si le da ese permiso para leer el calendario
            if(!arrPerm.isEmpty()) {
                String[] permissions = new String[arrPerm.size()];
                permissions = arrPerm.toArray(permissions);
                ActivityCompat.requestPermissions(this, permissions, 1);
            }


            // Run query
            Cursor cur = null;//El cursor alamcenara la lista de eventos
            ContentResolver cr = getContentResolver();//El content Resolver correra la consulta y obtendra la lista de eventos
            Uri uri = CalendarContract.Events.CONTENT_URI;
            String selection = "(" + CalendarContract.Events.OWNER_ACCOUNT +" = ?)";
            String[] selectionArgs = new String[] {"oscarismaelbm98@gmail.com"};
            // Submit the query and get a Cursor object back.
            cur = cr.query(uri, mProjection, selection, selectionArgs, null);


            // Use the cursor to step through the returned records
            while (cur.moveToNext()) {

                String title = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
                String description = cur.getString(cur.getColumnIndex(CalendarContract.Events.DESCRIPTION));
                String sDate = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART));


                //String sDate = cur.getString(cur.getColumnIndex(CalendarContract.EXTRA_EVENT_BEGIN_TIME));

                Log.d("FORMATO", "el bueno " + CalendarContract.Events.DTSTART);
                tvToday.setText(description);


            }


            //Estas lineas son solo para probar
            //TextView tvToday = (TextView)findViewById(R.id.tvToday);
            //tvToday.setText("ya sirve");
            Log.d("SALIO BIEN", "Funciona");


        }catch (Exception e){
            Log.d("SALIO MAL", "Otra vez " + e.getMessage());//Solo sirve para ver si todo jala bien.

        }
        //FIN PRUEBA

        //ESTO DE AQUI SI SIRVE
        /*
        Cursor cursor = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "((" + CalendarContract.Events._ID+" "+" = ?))";

        String[] selectionArgs = new String[] {"0"};
        */

        //cursor = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);




        Log.d("DEBUGOSCAR2", "Todo bien 6"); //Solo es para comprobar si todo jala bien




    }




}

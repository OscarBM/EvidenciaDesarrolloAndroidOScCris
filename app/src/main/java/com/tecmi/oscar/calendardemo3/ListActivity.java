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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListActivity extends AppCompatActivity /*implements View.OnLongClickListener*/{

    ArrayList<String> myStringArray1 =  new ArrayList<String>();//
    public static ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TextView tvToday = (TextView)findViewById(R.id.tvToday);
        ListView lstvEvents = (ListView)findViewById(R.id.lstvEvents);

        //Esto comprueba si la aplicción tiene permiso para leer el calendario
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


        //PRUEBA
        adapter = null;
        Calendar cal = Calendar.getInstance();
        String monthLetter="";

        // Use the cursor to step through the returned records
        while (cur.moveToNext()) {

            String title = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
            String description = cur.getString(cur.getColumnIndex(CalendarContract.Events.DESCRIPTION));
            String sDate = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART));
            //String sDate = cur.getString(cur.getColumnIndex(CalendarContract.EXTRA_EVENT_BEGIN_TIME));


            //Se convierte el sDate a una variable de tipo fecha
            java.util.Date time=new java.util.Date((long) Long.parseLong(sDate));
            //Date stDate = stringToDate(sDate, "EEE MMM d HH:mm:ss zz yyyy");

            //Extraer el dia, mes y año de la fecha de inicio
            cal.setTime(time);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DAY_OF_MONTH);


            switch(month){
                case 1: monthLetter = "Ene"; break;
                case 2: monthLetter = "Feb"; break;
                case 3: monthLetter = "Mar"; break;
                case 4: monthLetter = "Abr"; break;
                case 5: monthLetter = "May"; break;
                case 6: monthLetter = "Jun"; break;
                case 7: monthLetter = "Jul"; break;
                case 8: monthLetter = "Ags"; break;
                case 9: monthLetter = "Sep"; break;
                case 10: monthLetter = "Oct"; break;
                case 11: monthLetter = "Nov"; break;
                default: monthLetter = "Dec"; break;
            }

            String itemContent = day+"-"+monthLetter+"  "+title + ": "+description;
            //String sDate = cur.getString(cur.getColumnIndex(CalendarContract.EXTRA_EVENT_BEGIN_TIME));

            //PRUEBA
            myStringArray1.add(itemContent);
            adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, myStringArray1);//CustomAdapter(getActivity(), R.layout.row, myStringArray1);

            Log.d("FORMATO", "el bueno " + CalendarContract.Events.DTSTART);
            tvToday.setText(sDate);


        }
        //PRUEBA
        lstvEvents.setAdapter(adapter);


        //Estas lineas son solo para probar
        //TextView tvToday = (TextView)findViewById(R.id.tvToday);
        //tvToday.setText("ya sirve");
        Log.d("SALIO BIEN", "Funciona");

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

    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }




}

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

        //Se declaran variables
        TextView tvToday = (TextView)findViewById(R.id.tvToday);//El textView que contendra la fecha actual. CAMBIOS PENDIENTES
        ListView lstvEvents = (ListView)findViewById(R.id.lstvEvents);//El listView que contendra la lista de eventos

        //Este array de Strings permitira hacer consultas de eventos
        String[] mProjection =
                {
                        "_id",
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.EVENT_LOCATION,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.DTEND,
                        CalendarContract.Events.DESCRIPTION
                };

        //Se declara una variable
        ArrayList<String> arrPerm = new ArrayList<>();//Esta variable ayuda a añadir los permisos que no se hayan otorgado

        //Este if coprueba si la aplicación posee permiso para leer el calendario.
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            arrPerm.add(Manifest.permission.READ_CALENDAR);//Aqui se añade el permiso de leer calendario a una lista de permisos pendientes
        }

        //En caso de que la app no tenga permiso de leer el calendario, le pregunta al usuario si le da ese permiso para leer el calendario
        if(!arrPerm.isEmpty()) {//Si la lista de permisos pendientes NO esta vacia, se ejecuta este if
            String[] permissions = new String[arrPerm.size()];
            permissions = arrPerm.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, 1);
        }


        // Ejecutar consulta de eventos para el listView lstvEvents
        Cursor cur = null;//El cursor alamcenara la lista de eventos
        ContentResolver cr = getContentResolver();//El content Resolver correra la consulta y obtendra la lista de eventos
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "(" + CalendarContract.Events.OWNER_ACCOUNT +" = ?)";
        //ESTA LINEA DE ABAJO ESTA PENDIENTE
        String[] selectionArgs = new String[] {"oscarismaelbm98@gmail.com"};//Aqui se fija la cuenta de la cual se quieren revisar eventos.
        // El cursor almacenara la consulta hecha por el contentResolver
        //selecctionArgs vendrian siendo los filtros. Aqui se encuentra el correo del usuario
        cur = cr.query(uri, mProjection, selection, selectionArgs, null);


        //Se declaran variables para ir construyendo la lista de eventos del listView lstvEvents
        adapter = null;//El adapter ira almacenando los eventos para luego pasarle la lista a lstvEvents
        Calendar cal = Calendar.getInstance();//El Calendar nos permitira extraer el dia, mes y año de la fecha de inicio
        String monthLetter="";//Esta variable es para poder representar a los meses con abreviaciones (ej. Ene, Feb, Mar, etc.) en lugar de usar numeros

        // El cursor comienza a recorrer los eventos de la consulta de uno en uno para obtener sus datos
        while (cur.moveToNext()) {

            //Se extraen los datos del evento en turno
            String title = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));//Se extrae el titulo del evento
            String description = cur.getString(cur.getColumnIndex(CalendarContract.Events.DESCRIPTION));//Se extrae la descripción del evento
            String sDate = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART));//Se extrae la fecha de inicio del evento.
            //sDate sale en un tipo de String llamado timeStamp, el cual ocupa traducirse a una variable de tipo Date

            //Se convierte el sDate a una variable de tipo fecha (Date)
            //Revisar si metodo stringToDate realmente se ocupa o si esta de mas
            java.util.Date time=new java.util.Date((long) Long.parseLong(sDate));


            //Extraer el dia, mes y año de la fecha de inicio
            cal.setTime(time);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;//NOTA: Los meses comienzan en 0 (y no en 1), por ello se le suma 1 al resultado.
            int day = cal.get(Calendar.DAY_OF_MONTH);

            //Aqui se traduce el mes de la fecha de inicio a su correspondiente abreviación
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

            //El contenido de cada item del listView lstvEvents serán Strings que contengan la fecha de inicio, titulo y descripción del evento.
            //Aqui se contruye el String que sera contenido de lstvEvents
            String itemContent = day+"-"+monthLetter+"  "+title + ": "+description;

            //Aqui se añade el item al listView lstvEvents
            myStringArray1.add(itemContent);
            adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, myStringArray1);
        }
        //Aqui el adapter le pasa la lista de eventos completa a lstvEvents
        lstvEvents.setAdapter(adapter);

        //Estas lineas quizas no se ocupen
        /*
        Cursor cursor = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "((" + CalendarContract.Events._ID+" "+" = ?))";

        String[] selectionArgs = new String[] {"0"};
        */
    }

    //Revisar si se ocupa o si esta demas este metodo
    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }




}

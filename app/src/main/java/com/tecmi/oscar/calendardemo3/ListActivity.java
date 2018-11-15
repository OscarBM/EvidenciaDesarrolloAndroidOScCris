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



    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT,                  // 3

    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;





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
            String[] selectionArgs = new String[] {"usersample@gmail.com"};
            // Submit the query and get a Cursor object back.
            cur = cr.query(uri, mProjection, selection, selectionArgs, null);


            // Use the cursor to step through the returned records
            while (cur.moveToNext()) {

                String title = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
                String description = cur.getString(cur.getColumnIndex(CalendarContract.Events.DESCRIPTION));
                String sDate = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART));
                //String sDate = cur.getString(cur.getColumnIndex(CalendarContract.EXTRA_EVENT_BEGIN_TIME));

                tvToday.setText(sDate);


                /*
                long calID = 0;
                String displayName = null;
                String accountName = null;
                String ownerName = null;
                String eventTitle = null;

                // Get the field values
                calID = cur.getLong(PROJECTION_ID_INDEX);
                displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);


                // Do something with the values...
                */
                //tvToday.setText(String.valueOf(calID));
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


        //createList(this);

        Log.d("DEBUGOSCAR2", "Todo bien 6"); //Solo es para comprobar si todo jala bien

        /*
        String[] mProjection =
                {
                        "_id",
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.EVENT_LOCATION,
                        CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        CalendarContract.EXTRA_EVENT_END_TIME,
                        CalendarContract.Events.DESCRIPTION
                };


        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = CalendarContract.Events.EVENT_LOCATION + " = ? ";

        String[] selectionArgs = new String[]{"London"};
        cur = cr.query(uri,mProjection, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            String title = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));

            TextView tvToday =  (TextView) findViewById(R.id.tvToday);
            tvToday.setText(title);
            //cont.addView(tvToday);
        }*/

        /*
        listView = (ListView) findViewById(R.id.lstvEvents);
        listView.setOnLongClickListener(this);

        //NO se si vayan a servir
        Bundle bundle = getIntent().getExtras();
        int day, month, year;
        day=month=year=0;

        //No se si vayan a servir
        day = bundle.getInt("day");
        month = bundle.getInt("month");
        year = bundle.getInt("year");*/
    }



    /*@Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[]items = new CharSequence[2];
        items[0]="Eliminar evento";
        items[1]="Cancelar";

        builder.setTitle("Eliminar evento")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0){

                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }*/
}

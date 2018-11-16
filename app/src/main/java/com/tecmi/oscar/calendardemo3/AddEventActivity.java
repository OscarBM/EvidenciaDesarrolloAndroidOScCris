package com.tecmi.oscar.calendardemo3;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import java.util.Calendar;
import android.icu.util.Calendar;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    //Declarar EditTexts
    private EditText eventName, eventLocation, startDate, endDate, startHour, endHour;
    private EditText description;

    //Declarar botones
    private Button btnAddEvent, btnCancelEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //asignar valores a los EditText
        eventName = (EditText)findViewById(R.id.edtTitle);
        eventLocation = (EditText)findViewById(R.id.edtLocation);
        startDate = (EditText)findViewById(R.id.edtStartDate);
        startHour = (EditText)findViewById(R.id.edtStartHour);
        endDate = (EditText)findViewById(R.id.edtEndtDate);
        endHour = (EditText)findViewById(R.id.edtEndHour);
        description = (EditText)findViewById(R.id.edtDescription);

        Bundle bundle = getIntent().getExtras();
        int day = 0, month = 0, year = 0;
        bundle.getInt("day");
        bundle.get("month");
        bundle.get("year");
        //startDate.setText(day + "/" + month+"/" + year);
        //endDate.setText(day + "/" + month+"/" + year);


        //Asignar valores a los botones
        btnAddEvent = (Button)findViewById(R.id.btnAddEvent);
        btnCancelEvent = (Button)findViewById(R.id.btnCancelEvent);

        //Fijar los listener de los botones
        btnAddEvent.setOnClickListener(this);
        btnCancelEvent.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnAddEvent.getId()){
            //Se hizo que BDSQLite bd NO fuera abstracta. Tener en mente eso.
            //Si hace falta cambiar eso ve a la declaración de clase de BDSQLite.java

            /*BDSQLite bd = new BDSQLite(getApplication(), "schedule",null, 1);
            SQLiteDatabase db = bd.getWritableDatabase();

            String sql = "insert into events" +
                    " (nameEvent, location, startDate, startHour, endDate, endHour, description) "+
                    "values(" +
                    eventName.getText()+", "+
                    eventLocation.getText()+", "+
                    startDate.getText()+", "+
                    startHour.getText()+", "+
                    endDate.getText()+", "+
                    endHour.getText()+", "+
                    description.getText()+", "+
                    ")";*/

            Calendar beginTime = Calendar.getInstance();
            //beginTime.set(2018,10,7,8,30);
            String startDate1[] = startDate.getText().toString().split("/");
            String startHour1[] = startHour.getText().toString().split(":");
            beginTime.set(Integer.parseInt(startDate1[2]),
                    Integer.parseInt(startDate1[1])-1,
                    Integer.parseInt(startDate1[0]),
                    Integer.parseInt(startHour1[0]),
                    Integer.parseInt(startHour1[1]));

            Calendar endTime = Calendar.getInstance();
            String endDate1[] = endDate.getText().toString().split("/");
            String endHour1[] = endHour.getText().toString().split(":");
            endTime.set(Integer.parseInt(endDate1[2]),
                    Integer.parseInt(endDate1[1])-1,
                    Integer.parseInt(endDate1[0]),
                    Integer.parseInt(endHour1[0]),
                    Integer.parseInt(endHour1[1]));
            //Calendar endTime = Calendar.getInstance();
            //endTime.set(2018, 10,7, 9, 30);
            //endTime.set(2018, 10,7, 9, 30);

            //PRUEBA INICIO


            //PRUEBA FINAL


            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    //.putExtra(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, eventName.getText().toString())
                    .putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString())
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation.getText().toString())
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    //.putExtra(CalendarContract.Calendars.CALENDAR_COLOR, Color.GREEN);
                    //.putExtra(Intent.EXTRA_EMAIL, "rowan@hotmail.com,trevor@hotmail.com");
            startActivity(intent);

            try{
                //db.execSQL(sql);

                eventName.setText("");
                eventLocation.setText("");
                startDate.setText("");
                startHour.setText("");
                endDate.setText("");
                endHour.setText("");
                description.setText("");
            }catch(Exception e){
                Toast.makeText(getApplication(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        } else {
            //Este else if es para cancelar guardar el evento.
            this.finish();
            return;
        }
    }
}

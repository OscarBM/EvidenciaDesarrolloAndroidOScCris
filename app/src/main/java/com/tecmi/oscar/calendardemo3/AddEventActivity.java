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

    //Declarar EditTexts. Estos almacenaran los datos del evento
    private EditText eventName, eventLocation, startDate, endDate, startHour, endHour;
    private EditText description;

    //Declarar botones
    private Button btnAddEvent, btnCancelEvent;
    private Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //asignar valores a los EditText. Aqui se almacenarán los datos del evento
        eventName = (EditText)findViewById(R.id.edtTitle);
        eventLocation = (EditText)findViewById(R.id.edtLocation);
        startDate = (EditText)findViewById(R.id.edtStartDate);
        startHour = (EditText)findViewById(R.id.edtStartHour);
        endDate = (EditText)findViewById(R.id.edtEndtDate);
        endHour = (EditText)findViewById(R.id.edtEndHour);
        description = (EditText)findViewById(R.id.edtDescription);

        //No estoy seguro si estas lineas al final se ocupen
        Bundle bundle = getIntent().getExtras();
        int day = 0, month = 0, year = 0;
        bundle.getInt("day");
        bundle.get("month");
        bundle.get("year");


        //Asignar valores a los botones. Aqui se ligan las variables con sus respectivos botones en el archivo xml correspondiente a esta actividad
        btnAddEvent = (Button)findViewById(R.id.btnAddEvent);
        btnCancelEvent = (Button)findViewById(R.id.btnCancelEvent);
        btnList = (Button)findViewById(R.id.btnList);

        //Fijar los listener de los botones. Esto permite que los botones puedan realizar acciones al ser presionados.
        btnAddEvent.setOnClickListener(this);
        btnCancelEvent.setOnClickListener(this);
        btnList.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        //Como hay más de un botón, el metodo OnCLick priemro revisara el id del botón que llamó a este metodo para realizar las acciones correspondientes
        if(v.getId() == btnAddEvent.getId()){//Este if se ejecuta cuando el botón presionado fue el de agregar el evento a la agenda
            Calendar beginTime = Calendar.getInstance();
            String startDate1[] = startDate.getText().toString().split("/");//Aqui se hace un array de strings donde se almacenarán el dia, mes y año de la fecha de inicio del evento
            String startHour1[] = startHour.getText().toString().split(":");//Aqui se hace un array de strings donde se almacenarán la hora y los minutos de la fecha de inicio del evento
            beginTime.set(Integer.parseInt(startDate1[2]),//Aqui se fija el valor del año
                    Integer.parseInt(startDate1[1])-1,//Aqui se fija el valor del mes
                    Integer.parseInt(startDate1[0]),//Aqui se fija el valor del dia
                    Integer.parseInt(startHour1[0]),//Aqui se fija el valor de la hora
                    Integer.parseInt(startHour1[1]));//Aqui se fija el valor de los minutos

            Calendar endTime = Calendar.getInstance();
            String endDate1[] = endDate.getText().toString().split("/");//Aqui se hace un array de strings donde se almacenarán el dia, mes y año de la fecha de termino del evento
            String endHour1[] = endHour.getText().toString().split(":");//Aqui se hace un array de strings donde se almacenarán la hora y los minutos de la fecha de termino del evento
            endTime.set(Integer.parseInt(endDate1[2]),//Aqui se fija el valor del año
                    Integer.parseInt(endDate1[1])-1,//Aqui se fija el valor del mes
                    Integer.parseInt(endDate1[0]),//Aqui se fija el valor del dia
                    Integer.parseInt(endHour1[0]),//Aqui se fija el valor de la hora
                    Integer.parseInt(endHour1[1]));//Aqui se fija el valor de los minutos

            //Esta intent es para añadir el evento nuevo a la agenda
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())//Aqui se fija la fecha (con hora y minutos) de inicio del evento
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())//Aqui se fija la fecha (con hora y minutos) de termino del evento
                    .putExtra(CalendarContract.Events.TITLE, eventName.getText().toString())//Aqui se fija el titulo del evento
                    .putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString())//Aqui se fija la descripción del evento
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation.getText().toString())//Aqui se fija la ubicación del evento
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    //.putExtra(CalendarContract.Events.CALENDAR_ID, 1);
                    //.putExtra(CalendarContract.Calendars.CALENDAR_COLOR, Color.GREEN); //Aqui se intento poder modificar el color del evento. PENDIENTE
            startActivity(intent);//Aqui se ejecuta el intent para añadir el evento a la agenda

            //Quizas este try-catch no sea necesario
            try{
                //Una vez que el evento fue agregado a la agenda, se procedera a limpiar los textEdit.
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


        } else if(v.getId() == btnList.getId()){
            Intent intent = new Intent(getApplication(), ListActivity.class);//Se crea la intent para poder cambiar de pantalla
            startActivity(intent);//Se ejecuta la intent para pasar a la pantalla de Lista de eventos (ListActivity.java)
        }

        else {
            //Este else if es para cancelar guardar el evento.
            this.finish();
            return;
        }
    }
}

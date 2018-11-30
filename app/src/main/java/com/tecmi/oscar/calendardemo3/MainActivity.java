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
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    public static TextView tvMainToday;

    //El ublic permite que cualquier otra clase de la app pueda accesar o alterar el elemento indicado
    //EL sttic es para que el valor de esa variable sea el mismo para todas las instancias de la clase
    public static String activeAccount;//Cuenta activa
    public static String searchWord;//Palabra de busqueda. Si es null no filtrara ningun evento
    private EditText edtSearch;//La barra de busqueda para buscar eventos que contengan determinada palabra

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activeAccount = setAccount();//S edefine la cuenta cuando se inicia la aplicación

        tvMainToday = (TextView)findViewById(R.id.tvMainToday);//Se asocia el textView que indicará la fecha actual con su respectiva variable en el .java
        tvMainToday.setText(activeAccount);//Solo para testing
        edtSearch = (EditText)findViewById(R.id.edtSearch);//Se asocia la barra de busqueda con su respectiva variable en el .java
        clvMain = (CalendarView)findViewById(R.id.clvMain);//Se asocia el calendario de la pantalla principal cno su respectiva variable en el .java

        clvMain.setOnDateChangeListener(this);//Aqui se le da al calendario la capacidad de hacer cosas cuadno se selecciona un dia determinada
    }

    //Este metodo comprueba si existe el archivo donde se almacenara la cuenta activa
    private boolean exists(String[] files, String fileSearch) {
        for (int f = 0; f < files.length; f++)
            if (fileSearch.equals(files[f]))
                return true;
        return false;
    }


    //Metodo para que se ejecuten acciones si se presiona cualquier dia en el calendario clvMain
    //@Override
    public void onSelectedDayChange (CalendarView calendarView, int year, int month, int day){
        Intent intent = new Intent(getApplication(), DayActivity.class);
        intent.putExtra("year", year)
                .putExtra("month", month+1)
                .putExtra("day", day);
        try{startActivity(intent);} catch(Exception e){ Log.d("MALOO", e.getMessage()); }
    }


    //Este metodo es para fijar la cuenta activa
    public String setAccount (){
        String[] files = fileList();
        String line = "";
        String all = "";
        if (exists(files, "account.txt"))
            try {
                InputStreamReader file = new InputStreamReader(
                        openFileInput("account.txt"));
                BufferedReader br = new BufferedReader(file);
                line = br.readLine();

                /*
                while (line != null) {
                    all = all + line;//+ "\n";
                    line = br.readLine();
                }*/
                br.close();
                file.close();
               // tvMainToday.setText(line+"@gmail.com");

            } catch (IOException e) {
            }
            return line+"@gmail.com";//La cuenta que se debe ingresar debe ser solo la parte previa a la @. Lo demas lo añadira la app de forma automatica, pero solo acpetara cuenta de gmail
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


    //Este metodo se activa cuando presionas el botón btnAccount. Es para lelvarte a la pantalla de AccountActivity
    public void onClickAccount(View v){

        Intent intent = new Intent(getApplication(), AccountActivity.class);//Se crea el intent
        activeAccount = setAccount();//Se fija la cuenta activa
        startActivity(intent);//Se inicia la actividad para ir a la pantalla de añadir cuenta nueva


    }

    //Metodo que se ejecuta cuando se presiona el botón de busqueda. Es para filtrar eventos por palabras
    public void onClickSearch(View v){

        try{
            Intent intent = new Intent(getApplication(),SearchActivity.class);//Se crea la intent para poder cambiar de pantalla
            searchWord = edtSearch.getText().toString();//Se obtiene la palabra que esta en la barra de busqueda. Si no hya nada no hay filtro de palabras
            startActivity(intent);
        } catch(Exception e){
            Log.d("SALIOMAL3", e.getMessage());
        }
    }

}
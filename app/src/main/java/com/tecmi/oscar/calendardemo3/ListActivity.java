package com.tecmi.oscar.calendardemo3;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ListActivity extends AppCompatActivity /*implements View.OnLongClickListener*/{

    /*
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;*/
/*
    Cursor cursor;

    TextView tvToday = (TextView) findViewById(R.id.tvToday);

   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        


        Log.d("DEBUGOSCAR2", "Todo bien");
        //createList(this);



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

package com.tecmi.oscar.calendardemo3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity implements View.OnLongClickListener{

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.lstvEvents);
        listView.setOnLongClickListener(this);

        //NO se si vayan a servir
        Bundle bundle = getIntent().getExtras();
        int day, month, year;
        day=month=year=0;

        //No se si vayan a servir
        day = bundle.getInt("day");
        month = bundle.getInt("month");
        year = bundle.getInt("year");
    }

    @Override
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
    }
}

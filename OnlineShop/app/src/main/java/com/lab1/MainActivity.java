package com.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends AppCompatActivity {

    public String[] itemList = new String[]{"telefon", "laptop"};
    public String[] itemDescriptions = new String[]{"Ai dat click pe telefon", "Ai dat click pe laptop"};
    ArrayAdapter<String> adapter;
    ListView lista;


    private AdapterView.OnItemClickListener messageClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Context context = getApplicationContext();
//            System.out.println(itemDescriptions.length);
//            for(String s : itemDescriptions){
//                System.out.println(s);
//            }
            CharSequence toastText = itemDescriptions[position];

            int duration = Toast.LENGTH_SHORT;

            Toast toast =  Toast.makeText(context, toastText, duration);
            toast.show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(savedInstanceState != null){
//            this.itemList = savedInstanceState.getStringArray("products");
//            this.itemDescriptions = savedInstanceState.getStringArray("descriptions");
//        }
        setContentView(R.layout.activity_main);
        this.lista = (ListView) findViewById(R.id.lista1);


        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.itemList);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(messageClickHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);

        return true;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putStringArray("products", this.itemList);
        outState.putStringArray("descriptions",this.itemDescriptions);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        String[] aux = savedInstanceState.getStringArray("products");
        this.itemList = aux;
        this.itemDescriptions = savedInstanceState.getStringArray("descriptions");

        this.adapter.notifyDataSetChanged();

        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onStart(){
        super.onStart();
        Log.e("[onStart]","callback onStart");
    }

    protected void onResume(){
        super.onResume();
        Log.e("[onResume]","callback onResume");
    }

    protected void onPause(){
        super.onPause();
        Log.e("[onPause]","callback onPause");
    }

    protected void onStop(){
        super.onStop();
        Log.e("[onStop]","callback onStop");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.e("[onDestroy]","callback onDestroy");
    }

    public String[] getItemList() {
        return itemList;
    }

    public void setItemList(String[] itemList) {
        this.itemList = itemList;
    }

    public String[] getItemDescriptions() {
        return itemDescriptions;
    }

    public void setItemDescriptions(String[] itemDescriptions) {
        this.itemDescriptions = itemDescriptions;
    }
}

package com.example.onlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshop.activities.SensorsActivity;
import com.example.onlineshop.activities.SettingsActivity;
import com.example.onlineshop.adapter.ProductAdapter;
import com.example.onlineshop.fragments.AddProductDialogFragment;
import com.example.onlineshop.model.Product;

import java.util.ArrayList;
import java.util.List;

import com.example.onlineshop.storage.Internal;

public class MainActivity extends AppCompatActivity implements AddProductDialogFragment.ProductDialogListener{

    private ListView listView;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.product_list);
        List<Product> products = new ArrayList<>();
        productAdapter = new ProductAdapter(this, android.R.layout.simple_list_item_1, products);
        this.getProductsFromStorage();
        listView.setAdapter(productAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                CharSequence toastText = productAdapter.getProducts().get(position).getDescription();

                int duration = Toast.LENGTH_SHORT;

                Toast toast =  Toast.makeText(context, toastText, duration);
                toast.show();
            }
        });

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductDialogFragment addProductDialogFragment = new AddProductDialogFragment();
                addProductDialogFragment.show(getSupportFragmentManager(), "product");
            }
        });



    }

    @Override
    public void PD_onDialogPositiveClick(String title, String description) {


        ArrayList<Product> products;
        try{
            products = Internal.getProducts(MainActivity.this);
            products.add(new Product(title, description));

            Internal.setProducts(MainActivity.this, products);
            this.getProductsFromStorage();
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
        }


    }

    @Override
    public void PD_onDialogNegativeClick() {

    }

    protected void getProductsFromStorage(){
        try{
            List<Product> products = new ArrayList<>();
            products = Internal.getProducts(this);
            this.productAdapter.setProducts(products);
        }catch(Exception e){
            Log.e("[ERROR]", e.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_dial:
                String phone = "tel:0745123456";
                Intent intentDial = new Intent(Intent.ACTION_DIAL);
                try {
                    intentDial.setData(Uri.parse(phone));
                    startActivity(intentDial);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "Operation could not be done. Try again.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case R.id.action_sensors:
                Intent intentSensors = new Intent(this, SensorsActivity.class);
                startActivity(intentSensors);
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("products", (ArrayList<? extends Parcelable>) this.productAdapter.getProducts());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.productAdapter.setProducts(savedInstanceState.<Product>getParcelableArrayList("products"));
        this.productAdapter.notifyDataSetChanged();
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

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    public void setProductAdapter(ProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }


}

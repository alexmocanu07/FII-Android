package com.example.onlineshop.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshop.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private Map<Integer, String> sensorList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_sensors);
        this.setTitle("Sensors Info");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorList = new HashMap<>();
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor s: sensors){
            sensorList.put(s.getType(), "");
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        for(Map.Entry<Integer, String> item: sensorList.entrySet()){
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(item.getKey()), SensorManager.SENSOR_DELAY_NORMAL);
        }
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void getData(){
        StringBuilder result = new StringBuilder();
        for(Map.Entry<Integer, String> item : sensorList.entrySet()){
            result.append(sensorManager.getDefaultSensor(item.getKey()).getName());
            result.append(" : ");
            result.append(item.getValue());
            result.append("\n");
        }
        ((TextView)findViewById(R.id.display)).setText(result);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        getData();
        sensorList.put(sensorEvent.sensor.getType(), Float.toString(sensorEvent.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}

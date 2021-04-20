package com.example.compasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    CompasView compasView;
    SensorManager sensorManager;
    SensorEventListener listener;
    Sensor magneticSensor, accelSensor;

    float [] accelValues, magneticValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compasView = findViewById(R.id.CompasView);
        sensorManager =(SensorManager)getSystemService(SENSOR_SERVICE);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER :
                    accelValues = event.values.clone(); break;
                case Sensor.TYPE_MAGNETIC_FIELD :
                    magneticValues = event.values.clone(); break;
            }
            if(magneticValues != null && accelValues != null){
                float [] R =new float[16];
                float [] I =new float[16];
                SensorManager.getRotationMatrix(R,I,accelValues,magneticValues);
                float[] vaules = new float[3];
                SensorManager.getOrientation(R,vaules);

                compasView.azimuth = (int)radian2degree(vaules[0]);
                compasView.invalidate();

            }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) { }
        };
        sensorManager.registerListener(listener,magneticSensor,sensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(listener,accelSensor,sensorManager.SENSOR_DELAY_UI);
    }

    private float radian2degree(float radian) {
        return radian * 180 / (float)Math.PI;
    }
}
package lab1_202_04;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import ca.uwaterloo.sensortoy.LineGraphView;
import lab1_202_04.AccelerometerEventListener;
import lab1_202_04.LightSensorEventListener;
import lab1_202_04.MagneticFieldEventListener;
import lab1_202_04.RotationVectorEventListener;
import lab1_202_04.uwaterloo.ca.lab1_202_04.R;

public class MainActivity extends Activity {

    LineGraphView measurementGraph;

    TextView currentLightSensor;
    TextView currentAccelerometer;
    TextView currentMagneticField;
    TextView currentRotationVector;

    LinearLayout linearLayout;

    public MySensorEventListeners[] sensors = new MySensorEventListeners[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLightSensor = (TextView) findViewById(R.id.current_light_value);
        currentAccelerometer = (TextView) findViewById(R.id.current_acc_value);
        currentMagneticField = (TextView) findViewById(R.id.current_mag_value);
        currentRotationVector = (TextView) findViewById(R.id.current_rot_value);


        measurementGraph = new LineGraphView(getApplicationContext(),100, Arrays.asList("x","y","z"));

        linearLayout = (LinearLayout)findViewById(R.id.main_layout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        currentLightSensor.setTextColor(ContextCompat.getColor(this.getApplicationContext(), android.R.color.black));
        currentAccelerometer.setTextColor(ContextCompat.getColor(this.getApplicationContext(),android.R.color.black));
        currentLightSensor.setTextColor(ContextCompat.getColor(this.getApplicationContext(),android.R.color.black));
        currentMagneticField.setTextColor(ContextCompat.getColor(this.getApplicationContext(),android.R.color.black));
        currentRotationVector.setTextColor(ContextCompat.getColor(this.getApplicationContext(),android.R.color.black));

        linearLayout.addView(measurementGraph);
        measurementGraph.setVisibility(View.VISIBLE);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        SensorEventListener ourLightSensor = new LightSensorEventListener(currentLightSensor);
        SensorEventListener ourAccelerometer = new AccelerometerEventListener(currentAccelerometer, measurementGraph);
        SensorEventListener ourMagneticField = new MagneticFieldEventListener(currentMagneticField);
        SensorEventListener ourRotationVector = new RotationVectorEventListener(currentRotationVector);

        sensors[0] = (MySensorEventListeners) ourAccelerometer; sensors[1] = (MySensorEventListeners)ourMagneticField; sensors[2] = (MySensorEventListeners)ourRotationVector ;

        sensorManager.registerListener(ourLightSensor, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(ourAccelerometer,accelerometer, SensorManager.SENSOR_DELAY_NORMAL );
        sensorManager.registerListener(ourMagneticField, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(ourRotationVector, rotationVector, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void clearTextViews(View view){
        measurementGraph.purge();
        for(MySensorEventListeners event : sensors){
            event.highestValue = 0; event.highestDisplay = "";
        }
    }
}

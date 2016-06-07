package lab2_202_04;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Arrays;

import ca.uwaterloo.sensortoy.LineGraphView;
import lab2_202_04.uwaterloo.ca.lab1_202_04.R;

public class MainActivity extends Activity {

    LineGraphView measurementGraph;
    TextView currentAccelerometer;
    LinearLayout linearLayout;
    TextView accelerationValues;
    TextView steps;

    public MySensorEventListeners[] sensors = new MySensorEventListeners[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentAccelerometer = (TextView) findViewById(R.id.current_acc_value);
        accelerationValues = (TextView) findViewById(R.id.accelerationValues);
        steps = (TextView) findViewById(R.id.steps);
        measurementGraph = new LineGraphView(getApplicationContext(),100, Arrays.asList("x","y","z"));

        steps.setText("0");

        linearLayout = (LinearLayout)findViewById(R.id.main_layout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        currentAccelerometer.setTextColor(ContextCompat.getColor(this.getApplicationContext(),android.R.color.black));

        linearLayout.addView(measurementGraph);
        measurementGraph.setVisibility(View.VISIBLE);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        SensorEventListener ourAccelerometer = new AccelerometerEventListener(currentAccelerometer, measurementGraph, accelerationValues, steps);

        sensors[0] = (MySensorEventListeners) ourAccelerometer;
        sensorManager.registerListener(ourAccelerometer,accelerometer, SensorManager.SENSOR_DELAY_FASTEST );

    }

    public void clearTextViews(View view){
        measurementGraph.purge();
        for(MySensorEventListeners event : sensors){
            event.highestValue = 0; event.highestDisplay = "";
        }
        accelerationValues.setText("");
        steps.setText("0");
    }


}

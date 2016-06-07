package lab1_202_04;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;

import ca.uwaterloo.sensortoy.LineGraphView;

public class AccelerometerEventListener extends MySensorEventListeners {

    LineGraphView graph;
    public AccelerometerEventListener(TextView outputView, LineGraphView lineGraph){
        super(outputView, Sensor.TYPE_ACCELEROMETER, "Accelerometer");
        graph = lineGraph;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        super.onSensorChanged(sensorEvent);
        graph.addPoint(sensorEvent.values);
    }
}

package lab1_202_04;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.text.Html;
import android.widget.TextView;

public abstract class MySensorEventListeners implements SensorEventListener {
    TextView output;
    String highestDisplay = null;
    String sensorDisplay = null;

    float checkValue = 0;
    float highestValue = 0;
    float sensorValueX = 0;
    float sensorValueY = 0;
    float sensorValueZ = 0;

    int sensorType = 0;
    String sensorName = null;

    public MySensorEventListeners(TextView outputView,int ourSensorType, String sensor_name){
        sensorType = ourSensorType;
        output = outputView;
        sensorName = sensor_name;
    }
    public MySensorEventListeners(TextView outputView){
        this.output = outputView;
    }

    public void onAccuracyChanged(Sensor sensor, int intValue){
    }

    public void onSensorChanged(SensorEvent sensorEvent){
        if(sensorEvent.sensor.getType() == sensorType){
            sensorValueX = sensorEvent.values[0];
            sensorValueY = sensorEvent.values[1];
            sensorValueZ = sensorEvent.values[2];
            sensorDisplay = String.format("(%.2f,%.2f,%.2f)",sensorValueX,sensorValueY,sensorValueZ);

            checkValue = (float)Math.sqrt(Math.pow(sensorValueX,2)+ Math.pow(sensorValueY,2) + Math.pow(sensorValueZ,2));
            if(Float.compare(checkValue,highestValue) > 0){
                highestValue = checkValue;
                highestDisplay = sensorDisplay;
            }
            output.setText(Html.fromHtml("Highest "+sensorName+" Value: <b>"+highestDisplay+"</b><br/>Current "+sensorName+" Values: <b>"+sensorDisplay+"<br/>"));
        }
    }
}

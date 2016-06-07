package lab2_202_04;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;
import android.widget.TextView;
import ca.uwaterloo.sensortoy.LineGraphView;

public class AccelerometerEventListener extends MySensorEventListeners {
    private final int idle = 0;
    private final int aboutToStep = 1;
    private final int midStep = 2;
    private final int finishStep = 3;

    private final float averageMovingAcceleration = (float)0.1;

    private int currentState = 0;

    float yComparisonValue = 0;
    float zComparisonValue = 0;

    LineGraphView graph;
    TextView accelerationValues;
    TextView steps;

    float oldAccelerationValues[] = new float[3];
    float currentAccelerationValues[]= new float[3];

    float xsmoothExcel = 0;
    float ysmoothExcel = 0;
    float zsmoothExcel = 0;

    String lastMessage = "";

    public AccelerometerEventListener(TextView outputView, LineGraphView lineGraph, TextView accelerationNumbers, TextView stepText){
        super(outputView, Sensor.TYPE_LINEAR_ACCELERATION, "Accelerometer");
        graph = lineGraph;
        this.accelerationValues = accelerationNumbers;
        this.steps = stepText;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        super.onSensorChanged(sensorEvent);

        xsmoothExcel += (sensorEvent.values[0] - xsmoothExcel)/550;
        ysmoothExcel += (sensorEvent.values[1] - ysmoothExcel)/550;
        zsmoothExcel += (sensorEvent.values[2] - zsmoothExcel)/550;

        currentAccelerationValues[0] = xsmoothExcel;
        currentAccelerationValues[1] = ysmoothExcel;
        currentAccelerationValues[2] = zsmoothExcel;

//        currentAccelerationValues[0] = lowpassFilter(sensorEvent.values[0], (float)0.2,oldAccelerationValues[0])[0];
//        currentAccelerationValues[1] = lowpassFilter(sensorEvent.values[1], (float)0.2,oldAccelerationValues[1])[0];
//        currentAccelerationValues[2] = lowpassFilter(sensorEvent.values[2], (float)0.2,oldAccelerationValues[2])[0];

        graph.addPoint(currentAccelerationValues);

        if(accelerationValues.getText().length()> 100){
            accelerationValues.setText("");
        }

        accelerationValues.setText("x: "+oldAccelerationValues[0]+"\n y: "+oldAccelerationValues[1]+"\n z: "+oldAccelerationValues[2]);

        oldAccelerationValues = currentAccelerationValues;
        stateMachine(currentState);

    }

    private float[] lowpassFilter( float input, float alpha, float old ) {
        float[] out = new float[1];
        for(int i = 0; i < 1; i++) {
            out[ i ] = alpha * input+ ( 1 - alpha ) * old;
        }
        return out;
    }


    public void stateMachine(int state){

        int movingForward = Float.compare(averageMovingAcceleration,currentAccelerationValues[2]);
        int aboutToStepBarrier = Float.compare((float)(yComparisonValue+0.05),currentAccelerationValues[1]);
        int midStepBarrier = Float.compare((float)(yComparisonValue+0.15),currentAccelerationValues[1]);
        int finishStepBarrier = Float.compare((float)(yComparisonValue-0.1),currentAccelerationValues[1]);

        switch(state){
            case idle:
                logMyState("idle");
                yComparisonValue = currentAccelerationValues[1];
                zComparisonValue = currentAccelerationValues[2];
                if(0 > movingForward){
                    currentState = aboutToStep;
                }
                break;
            case aboutToStep:
                logMyState("aboutToStep");
                if( 0 > aboutToStepBarrier  || 0 == aboutToStepBarrier && (0 > movingForward)){
                    currentState = midStep;
                    yComparisonValue = currentAccelerationValues[1];
                }
                break;
            case midStep:
                logMyState("midStep");
                if( 0 > midStepBarrier || 0 == midStepBarrier && (0 > movingForward)){
                    currentState = finishStep;
                    yComparisonValue = currentAccelerationValues[1];
                }
                break;
            case finishStep:
                logMyState("finishStep");
                if( (0 < finishStepBarrier ||  0 == finishStepBarrier) && (0 > movingForward)){
                    int currentSteps = Integer.parseInt(steps.getText().toString())+1;
                    steps.setText(currentSteps+"");
                    currentState = idle;
                }
                break;
            default: break;
        }
    }

    public void logMyState (String message){
        if(!message.equals(lastMessage)){
            lastMessage = message;
            Log.i("state", message);
        }
    }
}


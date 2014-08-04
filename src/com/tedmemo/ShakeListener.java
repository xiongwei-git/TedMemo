package com.tedmemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import com.android.TedFramework.util.LogUtil;

/**
 * Created by Ted on 14-8-4.
 */
public class ShakeListener implements SensorEventListener {

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;

        if (sensorType == Sensor.TYPE_ACCELEROMETER)
        {
			/*正常情况下，任意轴数值最大就在9.8~10之间，只有在突然摇动手机
			  的时候，瞬时加速度才会突然增大或减少。   监听任一轴的加速度大于17即可
			*/
            if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                    .abs(values[2]) > 17))
            {
                LogUtil.e("摇晃手机了");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

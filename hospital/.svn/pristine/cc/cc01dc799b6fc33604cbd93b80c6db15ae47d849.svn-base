package com.bairuitech.anychat;


import java.util.Date;

import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class AnyChatSensorHelper implements SensorEventListener{

	private AnyChatOrientationEventListener orientationListener = null;
	
	public void InitSensor(Context context) {
		int iDeviceType = ((context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) ? 2 : 1;
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_DEVICEMODE, iDeviceType);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int rotation = wm.getDefaultDisplay ().getRotation ();
		int degrees = 0 ;
		switch ( rotation ) {
			case Surface.ROTATION_0 : degrees = 0 ; break ;
			case Surface.ROTATION_90 : degrees = 90 ; break ;
			case Surface.ROTATION_180 : degrees = 180 ; break ;
			case Surface.ROTATION_270 : degrees = 270 ; break ;
		}
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_SURFACEROTATION, degrees);
		
		AnyChatCoreSDK.mCameraHelper.SetContext(context);
		
		if(orientationListener == null) {
			orientationListener = new AnyChatOrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL);
			orientationListener.enable();
		}

		SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		Sensor mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void DestroySensor() {
		orientationListener.disable();
	}

	private float LastXSpead = 0;
	private float LastYSpead = 0;
	private float LastZSpead = 0;
	
	private boolean bCameraNeedFocus = false;
	private Date LastSportTime = new Date(); 
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
            return;
        }	
		float X = event.values[0];
		float Y = event.values[1]; 
		float Z = event.values[2]; 
		if ((Math.abs(X - LastXSpead) <= 0.5) && (Math.abs(Y - LastYSpead) <= 0.5) && (Math.abs(Z - LastZSpead) <= 0.5)) 
		{
			Date now = new Date();
			long interval = now.getTime() - LastSportTime.getTime();
			if (bCameraNeedFocus && interval > 1000) {
				bCameraNeedFocus = false;
				if(AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA)
					AnyChatCoreSDK.mCameraHelper.CameraAutoFocus();
				else
					AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FOCUSCTRL, 1);
			}
		} else {
			bCameraNeedFocus = true;
			LastSportTime.setTime(System.currentTimeMillis());
		}
		LastXSpead = X;
		LastYSpead = Y;
		LastZSpead = Z;
	}
}


class AnyChatOrientationEventListener extends OrientationEventListener{
	public AnyChatOrientationEventListener(Context context, int rate) { 
		super(context, rate);  
	}
	   
	@Override public void onOrientationChanged(int degree) {
		int ANYCHAT_DEVICEORIENTATION_UNKNOW 			= 0;
		int ANYCHAT_DEVICEORIENTATION_FACEUP			= 1;		// Device oriented flat, face up
//		int ANYCHAT_DEVICEORIENTATION_FACEDOWN			= 2;		// Device oriented flat, face down
		int ANYCHAT_DEVICEORIENTATION_LANDSCAPELEFT		= 3;		// Device oriented horizontally, home button on the right
		int ANYCHAT_DEVICEORIENTATION_LANDSCAPERIGHT	= 4;		// Device oriented horizontally, home button on the left
		int ANYCHAT_DEVICEORIENTATION_PORTRAIT			= 5;		// Device oriented vertically, home button on the bottom
		int ANYCHAT_DEVICEORIENTATION_PORTRAITUPSIDE	= 6;		// Device oriented vertically, home button on the top
		
		int orientation = ANYCHAT_DEVICEORIENTATION_UNKNOW;
		if(degree == -1)
			orientation = ANYCHAT_DEVICEORIENTATION_FACEUP;
		else if(degree > 325 || degree <= 45){  
        	orientation = ANYCHAT_DEVICEORIENTATION_PORTRAIT;  
        }else if(degree > 45 && degree <= 135){  
        	orientation = ANYCHAT_DEVICEORIENTATION_LANDSCAPERIGHT;
        }else if(degree > 135 && degree < 225){  
        	orientation = ANYCHAT_DEVICEORIENTATION_PORTRAITUPSIDE; 
        }else {  
        	orientation = ANYCHAT_DEVICEORIENTATION_LANDSCAPELEFT;
        }		
        //Log.i("ANYCHAT", "onOrientationChanged: " + degree + "orientation:" +orientation );
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_ORIENTATION, orientation);
	}
}
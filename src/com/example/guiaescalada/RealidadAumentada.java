package com.example.guiaescalada;

import java.io.File;
import java.io.FileOutputStream;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.ArchitectView.ArchitectUrlListener;
import com.wikitude.architect.ArchitectView.CaptureScreenCallback;
import com.wikitude.architect.ArchitectView.SensorAccuracyChangeListener;
import com.wikitude.architect.StartupConfiguration.CameraPosition;

import com.example.guiaescalada.util.ArchitectViewHolderInterface;
import com.example.guiaescalada.util.LocationProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class RealidadAumentada extends RAAbstractArchitectCamActivity{
	
	private long lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
	public String WIKITUDE_SDK_KEY = "ifELP3ctx87JqRTFaBfEpQDfVGiqDnPSmpzR1zDZgHIb8ZBvg6Fcgf0L7XgbuY4BxUBAXyaPdekgtxuopiIjhmnKfNjcndWqO6rACXLtbh6Hy5L8MVdjvRinxCVrp2mA6AjMyg/K4OVGppOaArvuLpV1zvdXohGdK3LH02IxiYNTYWx0ZWRfX1V0/dzgYb5gizO4401Kwhh8YICXxWYzUYee5fXNTjCWJMx7NH0YeQaJg6BelCRUSust1qidpeeKL1BUVG2mYp4RYdeVdbt+C8W/t+1stmsXUbq0gOITcUfk5Ogak19xAoSsChQgDkJ2jQbh5adEdqdYcKkOmeQn3akVAxj2CJJPzVRhRaYlaU8gXU4rP43bL+DjzVYoKJ4jpTHifrJs8qvgJF1C8MHUujovf+aK0RHJcQUcvX+Dhc9aqH2CNFCliJepkS9Ye6UowbtaWQ4ayEArzZ3+0zhcPc2A98BAv4yMos3mHKaTXblP4PcgbZsT6iVNDAS73RhabcL2IbQUxOPAEYh71KtiXPg+uR16K5lT4W8VdrGwjYd0qn0m4p4hdb4gsqDkZy/joldRDqxBnxguXhGgco74IdRUopNSqR+kssPNpGxA7a6B276TE1MKJYyRdEATC1uAQcMV8CS0in0CPHbgNth3wLrpwujr8RzQfdGrHOjm09U=";
	

	@Override
	public String getARchitectWorldPath() {
		if(getIntent().getExtras().getBoolean("parametro")){
			return "imageontarget/index.html";
		}else{
			return "poiatlocation/index.html";
		}
	}

	@Override
	public int getContentViewId() {
		return R.layout.realidad_aumentada;
	}

	@Override
	public int getArchitectViewId() {
		return R.id.architectView;
	}
	
	@Override
	public String getWikitudeSDKLicenseKey() {
		return WIKITUDE_SDK_KEY;
	}
	
	@Override
	public SensorAccuracyChangeListener getSensorAccuracyListener() {
		return new SensorAccuracyChangeListener() {
			@Override
			public void onCompassAccuracyChanged( int accuracy ) {
				/* UNRELIABLE = 0, LOW = 1, MEDIUM = 2, HIGH = 3 */
				if ( accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM && RealidadAumentada.this != null && !RealidadAumentada.this.isFinishing() && System.currentTimeMillis() - RealidadAumentada.this.lastCalibrationToastShownTimeMillis > 5 * 1000) {
					Toast.makeText( RealidadAumentada.this, "Please re-calibrate compass by waving your device in a figure 8 motion.", Toast.LENGTH_LONG ).show();
					RealidadAumentada.this.lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
				}
			}
		};
	}

	@Override
	public ArchitectUrlListener getUrlListener() {
		return new ArchitectUrlListener() {

			@Override
			public boolean urlWasInvoked(String uriString) {
				return false;
			}
		};
	}

	@Override
	public ILocationProvider getLocationProvider(final LocationListener locationListener) {
		return new LocationProvider(this, locationListener);
	}
	
	@Override
	public float getInitialCullingDistanceMeters() {
		// you need to adjust this in case your POIs are more than 50km away from user here while loading or in JS code (compare 'AR.context.scene.cullingDistance')
		return ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS;
	}

	@Override
	protected boolean hasGeo() {
		if(getIntent().getExtras().getBoolean("parametro")){
			return false;
		}else{
			return true;
		}
	}
 
	@Override
	protected boolean hasIR() {
		if(getIntent().getExtras().getBoolean("parametro")){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected CameraPosition getCameraPosition() {
		return CameraPosition.DEFAULT;
	}
}

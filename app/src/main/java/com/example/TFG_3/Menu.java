package com.example.TFG_3;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class Menu extends Activity implements MonitorNotifier {
	protected static final String TAG = "Menu";
	private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
	private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		verifyBluetooth();
		requestPermissions();
		BeaconManager.getInstanceForApplication(this).addMonitorNotifier(this);

	}

	@Override
	public void didEnterRegion(Region region) { /*mostrarPorPantalla("didEnterRegion called");*/ }
	@Override
	public void didExitRegion(Region region) {/*mostrarPorPantalla("didExitRegion called");*/
	}
	@Override
	public void didDetermineStateForRegion(int state, Region region) {
		//mostrarPorPantalla("didDetermineStateForRegion called with state: " + (state == 1 ? "INSIDE ("+state+")" : "OUTSIDE ("+state+")"));
	}

	private void requestPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
					== PackageManager.PERMISSION_GRANTED) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
					if (this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
							!= PackageManager.PERMISSION_GRANTED) {
						if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
							final AlertDialog.Builder builder = new AlertDialog.Builder(this);
							builder.setTitle("This app needs background location access");
							builder.setMessage("Please grant location access so this app can detect beacons in the background.");
							builder.setPositiveButton(android.R.string.ok, null);
							builder.setOnDismissListener(dialog -> requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
									PERMISSION_REQUEST_BACKGROUND_LOCATION));
							builder.show();
						}
						else {
							final AlertDialog.Builder builder = new AlertDialog.Builder(this);
							builder.setTitle("Funcionalidad limitada");
							builder.setMessage("Es necesario que active los permisos de localización en segundo plano para el correcto uso de la aplicación. Vaya a Configuración -> Aplicaciones -> Permisos y permita el acceso a la ubicación a esta aplicación.");
							builder.setPositiveButton(android.R.string.ok, null);
							builder.setOnDismissListener(dialog -> {
							});
							builder.show();
						}
					}
				}
			} else {
				if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
					requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
									Manifest.permission.ACCESS_BACKGROUND_LOCATION},
							PERMISSION_REQUEST_FINE_LOCATION);
				}
				else {
					final AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Funcionalidad limitada");
					builder.setMessage("Es necesario que active los permisos de localización para el correcto uso de la aplicación. Vaya a Configuración -> Aplicaciones -> Permisos y permita el acceso a la ubicación a esta aplicación.");
					builder.setPositiveButton(android.R.string.ok, null);
					builder.setOnDismissListener(dialog -> {
					});
					builder.show();
				}

			}
		}
	}



	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case PERMISSION_REQUEST_FINE_LOCATION: {
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Log.d(TAG, "fine location permission granted");
				} else {
					final AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Funcionalidad limitada");
					builder.setMessage("Sin los permisos de localización, la aplacación no podrá detectar los beacons");
					builder.setPositiveButton(android.R.string.ok, null);
					builder.setOnDismissListener(dialog -> {
					});
					builder.show();
				}
				return;
			}
			case PERMISSION_REQUEST_BACKGROUND_LOCATION: {
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Log.d(TAG, "background location permission granted");
				} else {
					final AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Funcionalidad limitada");
					builder.setMessage("Sin los permisos de localización, la aplacación no podrá detectar los beacons");
					builder.setPositiveButton(android.R.string.ok, null);
					builder.setOnDismissListener(dialog -> {
					});
					builder.show();
				}
			}
		}
	}

	public void escanearClicked(View view) {
		Intent myIntent = new Intent(this, Escaner.class);
		this.startActivity(myIntent);
	}

	public void mapaClicked(View view) {
		Intent myIntent2 = new Intent(this, Monitorear.class);
		this.startActivity(myIntent2);
	}


	public void menuClicked(View view) {
		setContentView(R.layout.activity_menu);
	}

	private void verifyBluetooth() {
		try {
			if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Bluetooth not enabled");
				builder.setMessage("Please enable bluetooth in settings and restart this application.");
				builder.setPositiveButton(android.R.string.ok, null);
				builder.setOnDismissListener(dialog -> finishAffinity());
				builder.show();
			}
		}
		catch (RuntimeException e) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Bluetooth LE not available");
			builder.setMessage("Sorry, this device does not support Bluetooth LE.");
			builder.setPositiveButton(android.R.string.ok, null);
			builder.setOnDismissListener(dialog -> finishAffinity());
			builder.show();

		}

	}

	/*private String cumulativeLog = "";
	private void mostrarPorPantalla(String line) {
		cumulativeLog += line+"\n";
    	runOnUiThread(() -> {
			EditText editText = Menu.this
					.findViewById(R.id.monitoringText);
			   editText.setText(cumulativeLog);
		});
    }*/

}

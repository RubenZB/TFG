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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class Menu extends Activity implements MonitorNotifier {
	protected static final String TAG = "Menu";
	private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
	private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;

	@RequiresApi(api = Build.VERSION_CODES.Q)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		verificarBluetooth();
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

	@RequiresApi(api = Build.VERSION_CODES.Q)
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
										   @NonNull String[] permissions, @NonNull int[] grantResults) {
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

	public void tutorialClicked(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("¿Como funciona?");
		builder.setMessage("La aplicación detecta los sensores que se encuentran en el entorno y muestra la información asociada a cada uno de ellos.\n"+
				"Al pulsar el botón de iniciar, se mostrará un mapa con los distintos sitos de interes en el edificio.\n"+
				"Podemos filtrar los sitios que se muestran en el mapa, para encontrar alguno en especifico.\n"+
				"Al seleccionar un punto del mapa, se mostrará una ventana con dos opciones: Mostrar información y Mostrar ruta.\n"+
				"Si pulsamos en Mostrar información, se mostrará la información asociada a este lugar como horarios,eventos...\n"+
				"Si pulsamos en Mostrar ruta, mostrará la ruta desde nuestra posición actual hasta el punto seleccionado y se irá actualizando en tiempo real mientras avancemos.\n");
		builder.setCancelable(true);
		builder.setPositiveButton("Aceptar", null);
		builder.show();
	}

	public void mapaClicked(View view) {
		Intent myIntent2 = new Intent(this, Monitorear.class);
		this.startActivity(myIntent2);
	}


	private void verificarBluetooth() {
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


}

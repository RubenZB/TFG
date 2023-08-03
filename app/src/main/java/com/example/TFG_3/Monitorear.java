package com.example.TFG_3;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class Monitorear extends Activity implements MonitorNotifier {
	protected static final String TAG = "Monitorear";
	private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
	private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitorear);
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
		setContentView(R.layout.activity_mostrarmapa);
		ImageView foto = findViewById(R.id.imageView);
		foto.setImageResource(R.drawable.mapaaulario);

	    Button mostrar = findViewById(R.id.bmostrar);
		Spinner p = (Spinner) findViewById(R.id.p);

		Button baula1 = findViewById(R.id.baula1);
		Button baula2 = findViewById(R.id.baula2);
		Button baula3 = findViewById(R.id.baula3);
		Button baula4 = findViewById(R.id.baula4);
		Button baula5 = findViewById(R.id.baula5);
		Button baula6 = findViewById(R.id.baula6);
		Button baula7 = findViewById(R.id.baula7);
		Button baula8 = findViewById(R.id.baula8);
		Button baula9 = findViewById(R.id.baula9);
		Button baula10 = findViewById(R.id.baula10);
		Button baula11 = findViewById(R.id.baula11);
		Button baula12 = findViewById(R.id.baula12);
		Button baula13 = findViewById(R.id.baula13);
		Button baula14 = findViewById(R.id.baula14);
		Button bbano1_1 = findViewById(R.id.bbano1_1);
		Button bbano1_2 = findViewById(R.id.bbano1_2);
		Button bbano2_1 = findViewById(R.id.bbano2_1);
		Button bbano2_2 = findViewById(R.id.bbano2_2);
		Button bbano3_1 = findViewById(R.id.bbano3_1);
		Button bbano3_2 = findViewById(R.id.bbano3_2);
		Button bbano4_1 = findViewById(R.id.bbano4_1);
		Button bbano4_2 = findViewById(R.id.bbano4_2);
		Button brepo = findViewById(R.id.brepo);
		Button bcafeteria = findViewById(R.id.bcafeteria);
		Button blibrerria = findViewById(R.id.blibreria);
		Button bbanco = findViewById(R.id.bbanco);
		Button bcapilla = findViewById(R.id.bcapilla);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.mapa, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		p.setAdapter(adapter);

		mostrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ocultarBotones();
				String opcion = p.getSelectedItem().toString();
				if (opcion.equals("Aulas")) {
					baula1.setVisibility(View.VISIBLE);
					baula2.setVisibility(View.VISIBLE);
					baula3.setVisibility(View.VISIBLE);
					baula4.setVisibility(View.VISIBLE);
					baula5.setVisibility(View.VISIBLE);
					baula6.setVisibility(View.VISIBLE);
					baula7.setVisibility(View.VISIBLE);
					baula8.setVisibility(View.VISIBLE);
					baula9.setVisibility(View.VISIBLE);
					baula10.setVisibility(View.VISIBLE);
					baula11.setVisibility(View.VISIBLE);
					baula12.setVisibility(View.VISIBLE);
					baula13.setVisibility(View.VISIBLE);
					baula14.setVisibility(View.VISIBLE);

				} else if (opcion.equals("Baños")) {
					bbano1_1.setVisibility(View.VISIBLE);
					bbano1_2.setVisibility(View.VISIBLE);
					bbano2_1.setVisibility(View.VISIBLE);
					bbano2_2.setVisibility(View.VISIBLE);
					bbano3_1.setVisibility(View.VISIBLE);
					bbano3_2.setVisibility(View.VISIBLE);
					bbano4_1.setVisibility(View.VISIBLE);
					bbano4_2.setVisibility(View.VISIBLE);

				} else if (opcion.equals("Cafeteria")){
					bcafeteria.setVisibility(View.VISIBLE);

				} else if (opcion.equals("Libreria")){
					blibrerria.setVisibility(View.VISIBLE);

				} else if (opcion.equals("Banco")){
					bbanco.setVisibility(View.VISIBLE);

				} else if (opcion.equals("Capilla")){
					bcapilla.setVisibility(View.VISIBLE);

				} else if (opcion.equals("Reprografia")){
					brepo.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Baños 1")){
					bbano1_1.setVisibility(View.VISIBLE);
					bbano1_2.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Baños 2")){
					bbano2_1.setVisibility(View.VISIBLE);
					bbano2_2.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Baños 3")){
					bbano3_1.setVisibility(View.VISIBLE);
					bbano3_2.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Baños 4")){
					bbano4_1.setVisibility(View.VISIBLE);
					bbano4_2.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 001")){
					baula1.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 002")){
					baula2.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 003")){
					baula3.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 004")){
					baula4.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 005")){
					baula5.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 006")){
					baula6.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 007")){
					baula7.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 008")){
					baula8.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 009")){
					baula9.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 010")){
					baula10.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 011")){
					baula11.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 012")){
					baula12.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 013")){
					baula13.setVisibility(View.VISIBLE);
				}else if (opcion.equals("Aula 014")){
					baula14.setVisibility(View.VISIBLE);
				}

			}

			public void ocultarBotones(){
				baula1.setVisibility(View.INVISIBLE);
				baula2.setVisibility(View.INVISIBLE);
				baula3.setVisibility(View.INVISIBLE);
				baula4.setVisibility(View.INVISIBLE);
				baula5.setVisibility(View.INVISIBLE);
				baula6.setVisibility(View.INVISIBLE);
				baula7.setVisibility(View.INVISIBLE);
				baula8.setVisibility(View.INVISIBLE);
				baula9.setVisibility(View.INVISIBLE);
				baula10.setVisibility(View.INVISIBLE);
				baula11.setVisibility(View.INVISIBLE);
				baula12.setVisibility(View.INVISIBLE);
				baula13.setVisibility(View.INVISIBLE);
				baula14.setVisibility(View.INVISIBLE);
				bbano1_1.setVisibility(View.INVISIBLE);
				bbano1_2.setVisibility(View.INVISIBLE);
				bbano2_1.setVisibility(View.INVISIBLE);
				bbano2_2.setVisibility(View.INVISIBLE);
				bbano3_1.setVisibility(View.INVISIBLE);
				bbano3_2.setVisibility(View.INVISIBLE);
				bbano4_1.setVisibility(View.INVISIBLE);
				bbano4_2.setVisibility(View.INVISIBLE);
				brepo.setVisibility(View.INVISIBLE);
				bcafeteria.setVisibility(View.INVISIBLE);
				blibrerria.setVisibility(View.INVISIBLE);
				bbanco.setVisibility(View.INVISIBLE);
				bcapilla.setVisibility(View.INVISIBLE);
			}
		});


	}

	public void menuClicked(View view) {
		setContentView(R.layout.activity_monitorear);
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

	private String cumulativeLog = "";
	/*private void mostrarPorPantalla(String line) {
		cumulativeLog += line+"\n";
    	runOnUiThread(() -> {
			EditText editText = Monitorear.this
					.findViewById(R.id.monitoringText);
			   editText.setText(cumulativeLog);
		});
    }*/

}

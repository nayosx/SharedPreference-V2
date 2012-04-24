package com.nayosx.prefv2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Preferenciasv2Activity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	Button bPref;
	TextView mostrar, textSizeActual;
	SharedPreferences misPref;
	float tamanoTexto = 0;
	int colorTexto = 0;
	int [] listColores = {Color.CYAN, Color.RED, Color.GREEN };
	boolean guardado;
	StringBuilder texto;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        inicializar();
    }
    private void inicializar()
    {
    	texto = new StringBuilder();
    	
    	bPref = (Button) findViewById(R.id.bPref);
    	mostrar = (TextView) findViewById(R.id.mostrar);
    	textSizeActual = (TextView) findViewById(R.id.textSizeActual);
    	
    	misPref = PreferenceManager.getDefaultSharedPreferences(Preferenciasv2Activity.this);
    	
    	recupPref();
    	leerTexto();

    	mostrar.setText(texto.toString());
    	textSizeActual.setText(""+tamanoTexto);
    	
    	bPref.setOnClickListener(this);
    	
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		startActivity(new Intent(Preferenciasv2Activity.this, Preferencias.class));
	}
	
	private void leerTexto() {
		// TODO Auto-generated method stub
    	try {
	    	int id = R.raw.linux;
			InputStreamReader insr = new InputStreamReader(this.getResources().openRawResource(id ));
			BufferedReader bf = new BufferedReader(insr);
			String linea;
			
			while((linea = bf.readLine()) != null)
			{
					texto.append(linea);
					//la siguiente linea es por si son varias lineas de texto
					//texto.append("\n");
			}
			insr.close();
			bf.close();
			
	    } catch(IOException e)
	    {
	    	Toast.makeText(getApplication(), "No se pudo leer el archivo deseado", Toast.LENGTH_LONG).show();
	    }
	}
	
	
	private void recupPref()
	{
		guardado = misPref.getBoolean("carga_pref", false);
		if(guardado)
    	{
    		String letra = misPref.getString("tamano_letras", "10");
    		String color = misPref.getString("colores_lista", "Color.WHITE");
    		
    		colorTexto = Integer.parseInt(color);
    		tamanoTexto = Float.parseFloat(letra);
    		textSizeActual.setText(""+tamanoTexto);
    		mostrar.setTextSize(tamanoTexto);
    		mostrar.setTextColor(listColores[colorTexto]);
    	}
    	else
    	{
    		tamanoTexto = 10;
    		colorTexto = Color.WHITE;
    		textSizeActual.setText(""+tamanoTexto);
    		mostrar.setTextSize(tamanoTexto);
    		mostrar.setTextColor(colorTexto);
    	}
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		recupPref();
		
	}
	
	
}
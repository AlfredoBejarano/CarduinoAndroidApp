package me.alfredobejarano.carduinorc;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ListView lista;
    private Button botonBusqueda;
    private int backButtonCount = 0;
    private ArrayAdapter adaptadorLista;
    private Set<BluetoothDevice> emparejados;
    private ArrayList<BluetoothDevice> encontrados;
    private ArrayList<String> nombresEmparejados = new ArrayList<>();
    private ArrayList<String> nombresEncontrados = new ArrayList<>();
    private ArrayList<String> nombres = new ArrayList<>();
    private final BluetoothAdapter adaptadorBt = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.lista);
        botonBusqueda = (Button) findViewById(R.id.buttonSearch);
        Intent i;

        Intent gi =  getIntent();
        encontrados = obtenerEncontrados(gi);


        if(!adaptadorBt.isEnabled()) {
            i = new Intent(MainActivity.this, TurnOnBluetooth.class);
            startActivity(i);
        } else {
            emparejados = obtenerEmparejados(adaptadorBt);

            if(emparejados.size() > 0) {
                nombresEmparejados = llenarNombres(emparejados);
                nombres.addAll(nombresEmparejados);
            }

            if (encontrados != null) {
                if(encontrados.size() > 0) {
                    nombresEncontrados = llenarNombres(encontrados);
                    nombres.addAll(nombresEncontrados);
                }
            }

            if (nombres.size() <= 0) {
                nombres.add("No se encontrarón dispositivos :(");
            }


            adaptadorLista = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
            lista.setAdapter(adaptadorLista);

            botonBusqueda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, SearchDevices.class);
                    if(!adaptadorBt.isEnabled()) {
                        adaptadorBt.enable();
                    }
                    startActivity(i);
                }
            });
        }

    }

    private Set<BluetoothDevice> obtenerEmparejados(BluetoothAdapter adaptadorBt) {
        Set<BluetoothDevice> emparejados = adaptadorBt.getBondedDevices();
        return emparejados;
    }

    private ArrayList<String> llenarNombres(Set<BluetoothDevice> dispositivos) {
        ArrayList<String> nombres = new ArrayList<>();
        String nombre;

        for(BluetoothDevice dispositivo : dispositivos) {
            nombre = dispositivo.getName();

            if((nombre == null) || (nombre.isEmpty())) {
                nombre = "Dispositivo sin nombre";
            }

            nombres.add(nombre);
        }

        return nombres;
    }

    private ArrayList<String> llenarNombres(ArrayList<BluetoothDevice> dispositivos) {
        ArrayList<String> nombres = new ArrayList<>();
        String nombre;

        for(BluetoothDevice dispositivo : dispositivos) {
            nombre = dispositivo.getName();

            if((nombre == null) || (nombre.isEmpty())) {
                nombre = "Dispositivo sin nombre";
            }

            nombres.add(nombre);
        }

        return nombres;
    }

    private ArrayList<BluetoothDevice> obtenerEncontrados(Intent i) {
        ArrayList<BluetoothDevice> dispositivosEncontrados = i.getParcelableArrayListExtra("dispositivosEncontrados");
        return dispositivosEncontrados;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            finish();
            moveTaskToBack(true);
            System.exit(0);
        }
        else
        {
            Toast.makeText(this, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}

package me.alfredobejarano.carduinorc;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchDevices extends AppCompatActivity {

    private TextView titulo;
    private IntentFilter filtro = new IntentFilter();
    private final BluetoothAdapter adaptadorBt = BluetoothAdapter.getDefaultAdapter();
    private ArrayList<BluetoothDevice> dispositivosEncontrados = new ArrayList<>();
    private final BroadcastReceiver receptorBt = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            titulo = (TextView) findViewById(R.id.bluetoothdevices);
            String estado = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(estado)) {
                BluetoothDevice dispositivoEncontrado = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                titulo.setText(titulo.getText()+".");
                dispositivosEncontrados.add(dispositivoEncontrado);
            }

            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(estado)) {
                Toast.makeText(context, "Busqueda iniciada", Toast.LENGTH_SHORT);
            }

            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(estado)) {
                Toast.makeText(context,"Busqueda Finalizada", Toast.LENGTH_SHORT);
                Intent i = new Intent(SearchDevices.this, ListDevices.class);
                Log.d("","\n"+dispositivosEncontrados.size());
                i.putParcelableArrayListExtra("dispositivosEncontrados", dispositivosEncontrados);
                startActivity(i);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_devices);

        filtro.addAction(BluetoothDevice.ACTION_FOUND);
        filtro.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filtro.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(receptorBt, filtro);
        adaptadorBt.startDiscovery();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "No puedes regresar mientras una búsqueda esta en curso", Toast.LENGTH_SHORT).show();
    }
}

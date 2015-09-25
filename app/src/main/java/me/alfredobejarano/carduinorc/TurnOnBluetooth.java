package me.alfredobejarano.carduinorc;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class TurnOnBluetooth extends AppCompatActivity {

    private Switch switchBt;
    private int backButtonCount = 0;
    private IntentFilter filtro = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    private Intent i;
    private final BluetoothAdapter adaptadorBt = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_on_bluetooth);

        i = new Intent(TurnOnBluetooth.this, MainActivity.class);

        this.registerReceiver(receptorBc, filtro);
            adaptadorBt.enable();
    }

    private BroadcastReceiver receptorBc = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String estado = intent.getAction();
            if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(estado)) {
                startActivity(i);
            }
        }
    };

}

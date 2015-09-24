package me.alfredobejarano.carduinorc;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
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
    private Intent i;
    private final BluetoothAdapter adaptadorBt = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_on_bluetooth);

        i = new Intent(TurnOnBluetooth.this, MainActivity.class);

        if(!adaptadorBt.isEnabled()) {
            switchBt = (Switch) findViewById(R.id.switchBluetooth);
            switchBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        adaptadorBt.enable();
                        startActivity(i);
                    } else {
                        adaptadorBt.disable();
                    }
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
        else
        {
            Toast.makeText(this, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}

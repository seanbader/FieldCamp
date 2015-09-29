package sean.dataexchange;

/**
 * Created by Sean on 9/28/2015.
 */

import android.support.v7.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        /**
         * Link to Bluetooth Settings
         */
        Button settings;
        final Context context = this;
        settings = (Button) findViewById(R.id.settings);

        settings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, bt_settings.class);
                startActivity(intent);
            }
        });

    }
}

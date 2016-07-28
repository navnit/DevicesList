package me.navnit.deviceslist.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import me.navnit.deviceslist.R;
import me.navnit.deviceslist.adapters.DevicesAdapter;
import me.navnit.deviceslist.models.Devices;
import me.navnit.deviceslist.network.request.DevicesRequest;

public class MainActivity extends AppCompatActivity {


    private static final int DEVICES_RESULT = 1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDevicesDataAsync();

    }

    private void getDevicesDataAsync() {

        new Thread() {
            public void run() {

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Downloading content from web!");
                    }
                });

                DevicesRequest devicesRequest = new DevicesRequest(MainActivity.this);

                Message msg = Message.obtain();
                msg.what = DEVICES_RESULT;

                String result = devicesRequest.send();

                progressDialog.dismiss();

                Bundle b = new Bundle();
                b.putString("result", result);
                b.putInt("error", devicesRequest.getError());
                msg.setData(b);
                messageHandler.sendMessage(msg);
            }
        }.start();
    }

    private Handler messageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == DEVICES_RESULT) {

                int error = msg.getData().getInt("error");

                if (error == DevicesRequest.NONE) {

                    String result = msg.getData().getString("result");

                    Log.d(this.getClass().getName(), result);

                    Gson gson = new Gson();

                    Devices devices = null;
                    try {
                        devices = gson.fromJson(result, Devices.class);
                    } catch (JsonSyntaxException e) {
                        Toast.makeText(getApplicationContext(), "Invalid Json response. Server speaks Gibrish!", Toast.LENGTH_LONG).show();
                    } catch (JsonParseException e) {
                        Toast.makeText(getApplicationContext(), "Invalid Data. We expect apple, for an apple!", Toast.LENGTH_LONG).show();
                    }

                    if (devices != null) {
                        DevicesAdapter devicesAdapter = new DevicesAdapter(getApplicationContext(), devices.getDevices());

                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(devicesAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                    }
                } else if (error == DevicesRequest.INVALID_URL) {
                    Toast.makeText(getApplicationContext(), "Invalid URL. Better know what you are trying to reach!", Toast.LENGTH_LONG).show();
                } else if (error == DevicesRequest.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(), "No Network. You got to me connected!", Toast.LENGTH_LONG).show();
                } else if (error == DevicesRequest.SERVER_ERROR) {
                    Toast.makeText(getApplicationContext(), "Server Error. Sometimes, tihS happens!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown Error. Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
}

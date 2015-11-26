/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package sean.dataexchange;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;


import java.util.Calendar;

import sean.dataexchange.common.activities.SampleActivityBase;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";

    // GPS Stuff
    TextView textLatitude;
    TextView textLongitude;
    GPSTracker gps;
    SurveyPointHandler test;
    int flagnumber_before;
    int increment;
    int flagnumber_after;

    // Collect Survey Point Button
    Button btnCollectSurveyPt;

    // Method Type Drop Down Menu
    private Spinner method_spinner;

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = new SurveyPointHandler("TestFile.txt", this.getApplicationContext());


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

        flagnumber_before = 0;

        // -------------------------------------- Start Lat Long------------------------------------------------//
        // TURN OFF ONCE SURVEY BUTTON WORKS
        // Latitude and Longitude are stored to TextView files to be used in SurveyFileHandler
        // Use textLatitude and textLongitude
        initComponents();
        //---------------------------------------End Lat Long --------------------------------------------------//


        //---------------------------------------- Start Time/Day ----------------------------------------------//

        Calendar c = Calendar.getInstance();
        //int millisec = c.get(Calendar.MILLISECOND);
        //int seconds = c.get(Calendar.SECOND);
        //int minutes = c.get(Calendar.MINUTE);
        //int hours = c.get(Calendar.HOUR);
        //int day = c.get(Calendar.DAY_OF_MONTH);
        //int month = c.get(Calendar.MONTH);
        //int year = c.get(Calendar.YEAR);

        // Integer to String
        // Integer.toString(millisec)

        //----------------------------------------- End Time/Day -----------------------------------------------//


        //----------------------------------------- Start User Input-------------------------------------------//

        // The Name, Surveytype and Flagnumber are stored to TextView Files for use in the SurveyFileHandler
        // Use: textName, textSurveyType, and textFlagNumber

        EditText editTextName = (EditText) findViewById(R.id.editName);
        EditText editFlagNumber = (EditText) findViewById(R.id.editFlagNumber);
        final TextView textName = (TextView) findViewById(R.id.textName);
        final TextView textFlagNumber = (TextView) findViewById(R.id.textFlagNumber);

        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE)

                {
                    String inputText = textView.getText().toString();
                    Toast.makeText(MainActivity.this, "Name: " + inputText, Toast.LENGTH_SHORT).show();
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    textName.setText(inputText);

                    handled = true;
                }

                return handled;
            }
        });


        editFlagNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE)

                {
                    String inputText = textView.getText().toString();
                    Toast.makeText(MainActivity.this, "Flag Number: " + inputText, Toast.LENGTH_SHORT).show();
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    textFlagNumber.setText(inputText);

                    handled = true;
                }

                return handled;
            }
        });

        // -----------------------------------End User Input Work---------------------------------------------------------------//

        //------------------------------------Select Survey Type------------------------------------------------------------//

        method_spinner = (Spinner) findViewById(R.id.method_spinner);


        //------------------------------------Select Survey Type------------------------------------------------------------//


        //---------------------------------------Start Survey Data Point--------------------------------------------------//

        btnCollectSurveyPt = (Button) findViewById(R.id.btnGetSurveyPoint);

        btnCollectSurveyPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Make Sure User Input Fields are completed
                if ( !textFlagNumber.getText().toString().matches("\\d+") || textName.getText().toString()== "" ) {

                    if(!textFlagNumber.getText().toString().matches("\\d+"))
                    {
                        Toast.makeText(MainActivity.this, "Enter Numeric Flag", Toast.LENGTH_SHORT).show();
                    } else if(textName.getText().toString()== "") {
                        Toast.makeText(MainActivity.this, "Enter Missing User Name", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Enter Missing Survey Type", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    // Collect User Input
                    int flagnumber = Integer.parseInt(textFlagNumber.getText().toString());
                    String user = textName.getText().toString();
                    String method = String.valueOf(method_spinner.getSelectedItem());
                    double latitude = 0.0;
                    double longitude = 0.0;

                    // Collect Lat/Long
                    gps = new GPSTracker(MainActivity.this);
                    if (gps.canGetLocation()) {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                        textLatitude.setText("\nLatitude: " + "" + latitude);
                        textLongitude.setText("\nLongitude: " + "" + longitude);
                    } else {
                        gps.showSettingsAlert();
                    }

                    // Tell the User We have done something
                    Toast.makeText(MainActivity.this, method + " Point collected at Flag Number: " + textFlagNumber.getText().toString(), Toast.LENGTH_SHORT).show();

                    // USE SURVEY POINT HANDLER HERE
                    test.recordSurveyPoint(latitude, longitude, flagnumber, user, method);

                    // Set Flag Number to zero to make people enter next flag
                    flagnumber_after = flagnumber;

                    increment = flagnumber_after - flagnumber_before;
                    textFlagNumber.setText(Integer.toString(flagnumber + increment));
                    flagnumber_before = flagnumber_after;

                }
            }
        });





        //---------------------------------------Start Survey Data Point----------------------------------------//



        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }


    //---------------------------------------- Begin Lat/Long ------------------------------------------//
    private void initComponents(){
        textLatitude = (TextView) findViewById(R.id.textLatitude);
        textLongitude = (TextView) findViewById(R.id.textLongitude);
    }

//---------------------------------------- End Lat/Long -------------------------------------------------//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setTitle(mLogShown ? R.string.survey : R.string.map);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.menu_toggle_log:
                startActivity(new Intent(this, MapActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


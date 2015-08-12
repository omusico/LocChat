package me.zujko.locchat.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zujko.locchat.R;
import me.zujko.locchat.fragments.ChatroomFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //Checks if GPS is enabled
        if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if(savedInstanceState != null) {
                currentFragment = getSupportFragmentManager().getFragment(savedInstanceState,"fragment");
            } else {
                currentFragment = new ChatroomFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.chatroom_fragment_container, currentFragment).commit();
            }
        } else {
            createNoGpsAlert();
        }
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

    /**
     * Creates an alert dialog alerting the user that they must enable location services.
     */
    private void createNoGpsAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.error_title_gps_disabled))
                .setMessage(getString(R.string.error_message_gps_disabled))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
        .setCancelable(false);

        AlertDialog mNoGpsAlertDialog = builder.create();
        mNoGpsAlertDialog.show();
    }
}

package org.nukkuu.debtcalc;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        *fab.setOnClickListener(new View.OnClickListener() {
         *   @Override
         *   public void onClick(View view) {
          *      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
           *             .setAction("Action", null).show();
         *   }
        }); */

        // get all elements
        final Button btn_calculate = (Button) findViewById(R.id.btn_calculate);
        final EditText f_totaldebt = (EditText) findViewById(R.id.f_totaldebt);
        final EditText f_intrate = (EditText) findViewById(R.id.f_intrate);
        final EditText f_payamount = (EditText) findViewById(R.id.f_payamount);
        final TextView results = (TextView) findViewById(R.id.results);
        Button btn_clear = (Button) findViewById(R.id.btn_clear);

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_totaldebt.setText("");
                f_intrate.setText("");
                f_payamount.setText("");
                results.setText("Results");
            }
        });


        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TAG = "BTN";
                double totaldebt;
                double intrate;
                double payamount;

                try {
                    totaldebt = Double.parseDouble(f_totaldebt.getText().toString());
                    intrate = Double.parseDouble(f_intrate.getText().toString());
                    payamount = Double.parseDouble(f_payamount.getText().toString());


                Log.i(TAG, "Continuing to error checking..");

                // check for NaN values
                if (Double.isNaN(totaldebt) || Double.isNaN(intrate) || Double.isNaN(payamount)) {
                    // Toast error
                    Toast.makeText(MainActivity.this, "All values must be numbers", Toast.LENGTH_SHORT).show();
                } else if (totaldebt <= 0 || intrate <= 0 || payamount <= 0) {
                    // Toast error
                    Toast.makeText(MainActivity.this, "All values must be over 0", Toast.LENGTH_LONG).show();
                } else if ( f_totaldebt.getText().toString().trim().length() == 0 || f_intrate.getText().toString().trim().length() == 0 || f_payamount.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(MainActivity.this, "All values must be given", Toast.LENGTH_LONG).show();

                } else {


                    double currdebt = totaldebt;
                    int payments = 0;
                    double l_intrate = (intrate / 100);
                    double total_interests = 0;

                    while (currdebt >= payamount) {
                        currdebt = currdebt - payamount;
                        double intr = currdebt * l_intrate;
                        Log.i(TAG, "Current interest " + Double.toString(intr));
                        total_interests = total_interests + intr;
                        currdebt = currdebt + intr;
                        payments++;
                    }

                    // what do we have after whole payments

                    boolean leftover_debt = false;

                    if (currdebt > 0) {
                        leftover_debt = true;
                    }

                    if (leftover_debt) {
                        total_interests = total_interests + (currdebt * l_intrate - currdebt);
                        payments = payments + 1;
                    }

                    // set up a line break
                    String newline = System.getProperty("line.separator");
                    // generate the results string
                    String result_s = "";
                    result_s = result_s + "Total payments " + Integer.toString(payments) + newline;
                    if (leftover_debt) {
                        result_s = result_s + "Last payment only " + Double.toString(currdebt) + newline;
                    } else {
                        result_s = result_s + "All payments were with full amount" + newline;
                    }
                    // one empty line in between
                    result_s = result_s + newline;

                    result_s = result_s + "Amount of interests paid " + Double.toString(total_interests);

                    results.setText(result_s);


                }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "An error occured, please check your input", Toast.LENGTH_LONG).show();
                }
            }

        });

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
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}

package com.londonappbrewery.bitcointicker;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://api.coinbase.com/v2/prices/spot";
    private final String API_KEY = "ODcyZjk3YmRjMTYyNDU2ZDkwOTc4NzEzZThmOGM0ODQ";
    private final String TAG = "Bitcoin Tracker TAG";
    public BTCExchangePrice[] btcExchangePriceGlobalVar = new BTCExchangePrice[20];


    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");
        System.out.println(TAG + "onCreate");

        btcExchangePriceGlobalVar[0]= new BTCExchangePrice("BTC","1");

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCurrency;
                selectedCurrency = parent.getItemAtPosition(position).toString();
                RequestParams params=new RequestParams();
                params.put("currency",selectedCurrency);
                getExchangePrice(params);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(TAG + " onResume:");
    }

    // TODO: complete the letsDoSomeNetworking() method
    private void getExchangePrice(RequestParams params){
        
        AsyncHttpClient client = new AsyncHttpClient();


        client.get(BASE_URL , params , new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                BTCExchangePrice btcExchangePrice = new BTCExchangePrice(response);
                System.out.println(TAG + btcExchangePrice.toString());
                mPriceTextView.setText(btcExchangePrice.getExchangeRate(2));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                System.out.println(TAG + "onFailure: status: " + statusCode + "Error Response: " + e.toString());
            }
        });
    }
}

package samples.rajesh.com.mvp_rxjava.services.retrofitImpl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import samples.rajesh.com.mvp_rxjava.R;
import samples.rajesh.com.mvp_rxjava.services.Network.APIResponse;
import samples.rajesh.com.mvp_rxjava.services.Network.APIS;
import samples.rajesh.com.mvp_rxjava.services.Network.RetrofitClient;

/**
 * Created by Rajesh Kumar on 02-05-2018.
 */
public class RetrofitImplementation extends AppCompatActivity {
    String lat = "16.989065", lng = "82.247465";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        callingGet();
        callingPost();
    }



    private void callingGet(){
        RetrofitClient.getInstance().getEndPoint(this,"").getResult(getParams(), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response is ","<>success<>><"+res);
            }

            @Override
            public void onFailure(String res) {
                Log.e("response is ","<>failure<>><"+res);
            }
        });
    }

    private void callingPost(){
        RetrofitClient.getInstance().getEndPoint(this,"").postResult(postParams(), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("post response is ","<><success><"+res);
            }

            @Override
            public void onFailure(String res) {
                Log.e("post response is ","<><fail><"+res);
            }
        });
    }


    private Map<String ,String > getParams(){
        Map<String ,String> params = new HashMap<>();
        params.put("action", APIS.GettingResDataBasedOnLat);
        params.put("LatLng", lat + "," + lng);

        return params;
    }



    private Map<String ,String > postParams(){
        Map<String ,String> params = new HashMap<>();
        params.put("go",APIS.uccfm_app_common_dtls);
        params.put("action", APIS.uccfm_app_common_dtls);
        params.put("field_executive_id", ""+227);
        params.put("api_id", "uccfm2018v1.0");

        return params;
    }
}

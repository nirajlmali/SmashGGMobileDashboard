package com.example.smashggmobiledashboard;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class SmashGG implements Runnable {
    private volatile String data;
    SmashGG(){

    }

    //JSON String
    //Check format in smash.gg documentation: https://smashgg-developer-portal.netlify.app/docs/sending-requests/
    /* {
          "query": "...",
          "operationName": "...",
          "variables": { "myVariable": "someValue", ... }
        }
     */

    public JSONObject reqEx(){
        JSONObject postData = new JSONObject();
        try {
            postData.put("query", this.exQuery());
//            postData.put("operationName", "");
            postData.put("variables", exVars());
        }catch (Exception e){
            Log.e("ERROR", "testRequest: JSONObject error");
        }
        Log.d("JSONObject", postData.toString());
        return postData;
    }

    public String exQuery(){
        return "query TournamentsByCountry($slug : String!) {" +
                "  tournament(slug : $slug) {" +
                "    id" +
                "  }" +
                "}";
    }
    public JSONObject exVars(){
        JSONObject vars = new JSONObject();
        try {
            vars.put("slug", "everest-s-evenings");
        }catch (Exception e){
            Log.e("ERROR", "testRequest: var errors");
        }
        return vars;
    }


    public void run() {

        String url = "https://api.smash.gg/gql/alpha";
        Log.d("SENDING", "sendReq: SENDING REQ...");
        HttpURLConnection httpURLConnection  = null;
        JSONObject postReq = reqEx();
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.addRequestProperty("Authorization", "Bearer 27120996ed4a41387f5c4eada0e11693");

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(postReq.toString());
            wr.flush();
            wr.close();

            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char current = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();
                data += current;
            }
            Log.i("STATUS", String.valueOf(httpURLConnection.getResponseCode()));
            Log.i("MSG", httpURLConnection.getResponseMessage());
            Log.i("MSG", data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    public String getData(){
        return data;
    }
}



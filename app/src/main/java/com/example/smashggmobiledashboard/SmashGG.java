package com.example.smashggmobiledashboard;

import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SmashGG implements Runnable {
    private volatile JSONObject data;
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

    public void run() {

        String url = "https://api.smash.gg/gql/alpha";
        Log.d("SENDING", "sendReq: SENDING REQ...");
        HttpURLConnection con  = null;
        JSONObject postReq = reqEx();
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.addRequestProperty("Authorization", "Bearer " + Constants.APIKey);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postReq.toString());
            wr.flush();
            wr.close();

            /*
            Code used from https://chillyfacts.com/java-send-http-getpost-request-and-read-json-response/
            */
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject myResponse = new JSONObject(response.toString());
            /*
            End code
             */

            Log.i("STATUS", String.valueOf(con.getResponseCode()));
            Log.i("MSG", con.getResponseMessage());
            Log.i("MSG", myResponse.toString());
            data = myResponse;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public JSONObject getData(){
        return data;
    }


    /*
    * Example request items
    * */

    private JSONObject reqEx(){
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

    private String exQuery(){
        return "query TournamentsByCountry($slug : String!) {" +
                "  tournament(slug : $slug) {" +
                "    id" +
                "  }" +
                "}";
    }
    private JSONObject exVars(){
        JSONObject vars = new JSONObject();
        try {
            vars.put("slug", "everest-s-evenings");
        }catch (Exception e){
            Log.e("ERROR", "testRequest: var errors");
        }
        return vars;
    }


}



package com.ucab.servimovil.web;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPConnection {

	public static String  url;
    public static final int     TIME_OUT_CONNECTION    = 5000;
    public static final int     TIME_OUT_SOCKET    = 5000;

    public static InputStream GetRequest(String url) {

        InputStream content = null;

        try {
            HttpResponse response = buildHttpClient().execute(new HttpGet(url));
            content = response.getEntity().getContent();
        }
        catch (Exception e) {
            Log.e("E_GET_REQUEST",e.toString());
        }

        return content;
    }

    private static HttpClient buildHttpClient() {

        HttpClient httpClient = null;

        try {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,TIME_OUT_CONNECTION);
            HttpConnectionParams.setSoTimeout(httpParams,TIME_OUT_SOCKET);
            httpClient = new DefaultHttpClient(httpParams);
        }
        catch (Exception e) {
            Log.e("E-BUILD_HTTP",e.toString());
        }

        return httpClient;
    }

    public static String parseInputStreamToString(InputStream in) {
        String response = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            in.close();
            response = sb.toString();
        } catch(Exception e) {
            Log.e("E_PARSE_INPUT", e.toString());
        }

        return response;
    }
    
    public static boolean verificaConexion(Context ctx) {
        boolean isConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < 2; i++) {
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                isConectado = true;
            }
        }
        return isConectado;
    }
}

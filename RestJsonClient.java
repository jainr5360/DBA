package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RestJsonClient {

	private Context _context;

	
	public static JSONObject connect(String url) {
		// TODO Auto-generated method stub
		System.out.println("url in restjsonclien is " + url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		JSONObject json = new JSONObject();
		System.out.println("httpget is " + httpget);

		try {
			response = httpclient.execute(httpget);
			System.out.println("response in restjoson cleis- " + response);
			  Log.v("response code ", response.getStatusLine()
                      .getStatusCode() + ""); 
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				json = new JSONObject(result);
				instream.close();
			}

			
		} catch (ClientProtocolException e) {
			System.out.println("client");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			System.out.println("illegal");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ioexcepiton");
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("jsonex");
			e.printStackTrace();
		}

		return json;
	}
	

	/**
	 * 
	 * @param is
	 * @return String
	 */
	public static  String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	
	@SuppressLint("LongLogTag")
	public static boolean isNetworkAvailable1(Activity activity) {
		   {
			   Log.i("Context", "value of Activity is "+activity);
			   System.out.println("hi eerror");
		        boolean haveConnectedWifi = false;
		        boolean haveConnectedMobile = false;

		        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		        for (NetworkInfo ni : netInfo)
		        {
		            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            {
		                if (ni.isConnected())
		                {
		                    haveConnectedWifi = true;
		                    Log.v("WIFI CONNECTION ", "AVAILABLE");
		                } else
		                {
		                    Log.v("WIFI CONNECTION ", "NOT AVAILABLE");
		                }
		            }
		            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            {
		                if (ni.isConnected())
		                {
		                    haveConnectedMobile = true;
		                    Log.i("MOBILE INTERNET CONNECTION ", "AVAILABLE");
		                } else
		                {
		                    Log.d("MOBILE INTERNET CONNECTION ", "NOT AVAILABLE");
		                }
		            }
		        }
		        return haveConnectedWifi || haveConnectedMobile;
		    }
		   
		   
	    
	    }
	
}



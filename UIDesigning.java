package com.example.myapplication;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class UIDesigning extends AppCompatActivity {
    ListView listView;
    ArrayList<ListModel> listArrlist;
    CustomImageAdapter customImageAdapter;
    private SqlLiteAdpter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uidesigning);

        db = new SqlLiteAdpter(UIDesigning.this);
        db.openToWrite();

        listArrlist = new ArrayList<ListModel>();

        listView = (ListView) findViewById(R.id.add_itemlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (RestJsonClient.isNetworkAvailable1(UIDesigning.this))
            new getsampradaaydata().execute();
        else
            getData();

    }


    /////////////Asynctask Android ////////////////////////////////////////


    class getsampradaaydata extends AsyncTask<Void, Void, ArrayList<ListModel>> {

        JSONObject job = new JSONObject();
        String str;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ListModel> doInBackground(Void... params) {

            try {
                String url = "https://www.bargaincry.com/apurl/deal/deals_by_category/41187/1";

                JSONObject jsonObject = new JSONObject();

                JSONObject response = RestJsonClient.connect(url + URLEncoder.encode(jsonObject.toString(), "utf-8"));
                listArrlist = new ArrayList<ListModel>();


                if ((response.getString("status")).equals("done")) {
                    JSONArray jarr = response.getJSONArray("deals");

                    for (int i = 0; i < jarr.length(); i++) {

                      /*  ListModel list = new ListModel();
                        list.setImageUrl(jarr.getJSONObject(i).getString("image_url"));
                        list.setName(jarr.getJSONObject(i).getString("name"));
                        list.setMerchantName(jarr.getJSONObject(i).getString("merchant_name"));
                        list.setMerchantAddress(jarr.getJSONObject(i).getString("Merchant_address"));
                        list.setDiscountedPrice(jarr.getJSONObject(i).getString("discounted_price"));
                        listArrlist.add(list);*/

                        long no = db.insertintoTable(jarr.getJSONObject(i).getString("name"), "merchandid", " cat_id", "  sub_id", " deal_id", jarr.getJSONObject(i).getString("image_url"), "city", "validet_frm", "valid_to",
                                "ac_price", jarr.getJSONObject(i).getString("discounted_price"), " dis_per", jarr.getJSONObject(i).getString("merchant_name"), jarr.getJSONObject(i).getString("Merchant_address"), "lat", "lng");
                    }


                }


            } catch (Exception e) {

                e.printStackTrace();
            }


            return listArrlist;
        }


        @Override
        protected void onPostExecute(ArrayList<ListModel> listModels) {

            super.onPostExecute(listModels);
            getData();

        }
    }

    void getData() {

        Cursor cursor = db.queueArticlesDetails();

        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {

            ListModel list = new ListModel();
            list.setImageUrl(cursor.getString(cursor.getColumnIndex(SqlLiteAdpter.KEY_IMG)));
            list.setName(cursor.getString(cursor.getColumnIndex(SqlLiteAdpter.KEY_NAME)));
            list.setMerchantName(cursor.getString(cursor.getColumnIndex(SqlLiteAdpter.KEY_MSER_NAME)));
            list.setMerchantAddress(cursor.getString(cursor.getColumnIndex(SqlLiteAdpter.KEY_MSER_ADD)));
            list.setDiscountedPrice(cursor.getString(cursor.getColumnIndex(SqlLiteAdpter.KEY_DIS_PRISCE)));
            listArrlist.add(list);
        }

        try {
            customImageAdapter = new CustomImageAdapter(getApplicationContext(), listArrlist);
            listView.setAdapter(customImageAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

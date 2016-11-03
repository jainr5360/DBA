package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Hogo on 10/26/2016.
 */
public class CustomImageAdapter extends BaseAdapter
{


    private Context activity;

    private LayoutInflater inflater = null;

    ArrayList<ListModel> arrayList;




    public CustomImageAdapter(Context a,ArrayList<ListModel>arrayList)
    {
       this.activity=a;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount()
    {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //////view holder class

    public static class ViewHolder
    {
        public TextView text2;
        public TextView text1;
        public TextView text4;
        public TextView text3;
        public TextView text5;
        public ImageView img1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View vi=convertView;
        ViewHolder holder;

        inflater =(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        vi=inflater.inflate(R.layout.row_item,null);

        holder =new ViewHolder();
        holder.img1=(ImageView) vi.findViewById(R.id.shpp_image);
        holder.text1=(TextView) vi.findViewById(R.id.product_price);
        holder.text2=(TextView)vi.findViewById(R.id.product_email);
        holder.text3=(TextView)vi.findViewById(R.id.product_text);
        holder.text4=(TextView)vi.findViewById(R.id.product_rupes);
        holder.text5=(TextView)vi.findViewById(R.id.ProductCancelRs);

        Picasso.with(activity).load(arrayList.get(position).getImageUrl()).placeholder(R.drawable.search).into(holder.img1);
        holder.text1.setText(arrayList.get(position).getName());
        holder.text2.setText(arrayList.get(position).getMerchantName());
        holder.text3.setText(arrayList.get(position).getMerchantAddress());
        holder.text4.setText(arrayList.get(position).getDiscountedPrice());
        return vi;
    }
}

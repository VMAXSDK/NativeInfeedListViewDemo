package com.vmax.demo.nativeInfeedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prathameshs on 07-04-2017.
 */

public class listviewadapter extends BaseAdapter {
    ArrayList<BlogModel> list=new ArrayList<>();
    Context theContext;
    RelativeLayout theAd;

    public listviewadapter(Context c,ArrayList<BlogModel>list,RelativeLayout adContainer)
    {
        this.theContext=c;
        this.list=list;
        this.theAd=adContainer;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vw;
        TextView textView;
        if (i==4)
        {
/** Ad Will be displayed on the fourth position in your list*/
            vw=theAd;
        }
        else {
            LayoutInflater layoutInflater = (LayoutInflater) theContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             vw = layoutInflater.inflate(R.layout.item_listview, null);
            textView = (TextView) vw.findViewById(R.id.blog_title);
            textView.setText(list.get(i).getTitle());
            textView=(TextView)vw.findViewById(R.id.blog_date);
            textView.setText(list.get(i).getDate());
            textView=(TextView)vw.findViewById(R.id.blog_author);
            textView.setText(list.get(i).getAuthor());
            ImageView blog_img = (ImageView) vw.findViewById(R.id.blog_img);
            blog_img.setImageDrawable(list.get(i).getBlogImage());
        }
        return vw;
    }
}

package com.kylins.videoplayer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kylins.videoplayer.R;
import com.kylins.videoplayer.bean.Video;
import com.kylins.videoplayer.bean.VideoGroup;
import com.kylins.videoplayer.services.ApiClent;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private List<VideoGroup> data;

    private ArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.list);
        adapter = new ArrayAdapter<VideoGroup>(this,0){
            @Override
            public int getCount() {
                if(data==null)
                    return 0;
                return data.size();
            }

            @Override
            public VideoGroup getItem(int position) {
                return data.get(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView==null){
                    convertView =   LayoutInflater.from(getContext()).inflate(R.layout.grou_item,null);
                    ViewHolder viewHolder = new ViewHolder();
                    viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                    convertView.setTag(viewHolder);
                }


                ViewHolder holder = (ViewHolder) convertView.getTag();
                Picasso.with(getContext()).load(getItem(position).getFace()).into(holder.image);
                holder.title.setText(getItem(position).getTitle());
                return convertView;
            }

            class ViewHolder{
                TextView  title;
                ImageView image;
            }
        };

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),VideoListActivity.class);
                intent.putExtra("cid",data.get(position).getId());
                startActivity(intent);
            }
        });
        ApiClent.getInstance().getDataApi().getMenuList().enqueue(new Callback<List<VideoGroup>>() {
            @Override
            public void onResponse(Call<List<VideoGroup>> call, Response<List<VideoGroup>> response) {
                data =  response.body();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<VideoGroup>> call, Throwable t) {

            }
        });
    }
}

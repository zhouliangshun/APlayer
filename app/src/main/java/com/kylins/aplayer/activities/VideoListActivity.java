package com.kylins.aplayer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kylins.aplayer.R;
import com.kylins.aplayer.bean.Video;
import com.kylins.aplayer.bean.VideoGroup;
import com.kylins.aplayer.services.ApiClent;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListActivity extends AppCompatActivity {

    private GridView gridView;
    private List<Video> data;

    private ArrayAdapter adapter = null;

    private String cid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        cid = getIntent().getStringExtra("cid");

        gridView = (GridView) findViewById(R.id.list);
        adapter = new ArrayAdapter<Video>(this,0){
            @Override
            public int getCount() {
                if(data==null)
                    return 0;
                return data.size();
            }

            @Override
            public Video getItem(int position) {
                return data.get(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView==null){
                    convertView =   LayoutInflater.from(getContext()).inflate(R.layout.video_item,null);
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
                Video video = data.get(position);
                JCFullScreenActivity.toActivity(view.getContext(),video.getUrl(),JCVideoPlayerStandard.class,video.getTitle());
            }
        });

        ApiClent.getInstance().getDataApi().getVidoList(cid).enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                data =  response.body();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });

    }
}

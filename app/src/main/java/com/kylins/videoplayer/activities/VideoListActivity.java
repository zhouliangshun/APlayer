package com.kylins.videoplayer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kylins.videoplayer.R;
import com.kylins.videoplayer.bean.Video;
import com.kylins.videoplayer.bean.VideoGroup;
import com.kylins.videoplayer.services.ApiClent;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListActivity extends AppCompatActivity {

    private ListView listView;

    private List<Video> data;

    private String cid = "";

    private ArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        cid = getIntent().getStringExtra("cid");

        listView = (ListView)findViewById(R.id.list);
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
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video video = data.get(position);
                JCFullScreenActivity.toActivity(view.getContext(),video.getUrl(),null);
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

package com.jcy.mokaijun.lect10Net.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jcy.mokaijun.lect10Net.HttpProxy;
import com.jcy.mokaijun.lect10Net.R;
import com.jcy.mokaijun.lect10Net.SignActivity;
import com.jcy.mokaijun.lect10Net.bean.VideoInfo;
import com.jcy.mokaijun.lect10Net.bean.VideoListResponse;
import com.jcy.mokaijun.lect10Net.lect09SeniorView.NetInputUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NetListActivity extends AppCompatActivity {
    private static final String TAG = "NetListActivity";
    private static final int REQUEST_CODE_EDT = 10;
    private VideoAdapter mAdapter;
    private Handler mHandler = new Handler();
    private List<VideoInfo> mDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_list);
        ListView mListView = findViewById(R.id.lv);

        View headLayout = buildLsitHeader();
        mListView.addHeaderView(headLayout);

        mDataList = new ArrayList<>();
        mAdapter = new VideoAdapter(mDataList, this);
        mListView.setAdapter(mAdapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                VideoInfo videoInfo = mDataList.get(position-1);
//                Intent intent=new Intent(NetListActivity.this, WebActivity.class);
//                intent.putExtra(WebActivity.WEB_URL,videoInfo.getFilePath());
//                startActivity(intent);
//            }
//        });


        initData();
    }

    private View buildLsitHeader() {
        View headLayout = LayoutInflater.from(this).inflate(R.layout.layout_header, null);
        TextView nameTV = headLayout.findViewById(R.id.txt_sign);

        nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NetListActivity.this, "去设置签名", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NetListActivity.this, SignActivity.class);
                startActivityForResult(intent, REQUEST_CODE_EDT);
            }
        });

        return headLayout;
    }


    private void initData() {
        String raUrl = "http://ramedia.sinaapp.com/videolist.json";
        String movieUrl="http://ramedia.sinaapp.com/res/DouBanMovie2.json";
        HttpProxy.getInstance().load(movieUrl, new HttpProxy.NetInputCallback() {
            @Override
            public void onSuccess(InputStream inputStream) {
                String respJson = null;
                try {
                    respJson = NetInputUtils.readString(inputStream);
                    Log.i(TAG, "---response json:\n" + respJson);
                    VideoListResponse videoListResponse = conVertJsonToBean(respJson);
                    final List<VideoInfo> list = videoListResponse.getList();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private VideoListResponse conVertJsonToBean(String json) {
        Gson gson=new Gson();
        VideoListResponse response=gson.fromJson(json, VideoListResponse.class);
        return  response;
    }


}
package com.wei.mypic.aty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wei.mypic.R;
import com.wei.mypic.adapter.MainAdapter;
import com.wei.mypic.bean.LocalPicBean;
import com.wei.mypic.bean.PermissionBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 只有两个按钮的首页
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        mainAdapter = new MainAdapter(this, null);
        recyclerView.setAdapter(mainAdapter);


        //相册
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });
        //拍照 这里暂时未实现
        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initGetPermission();
    }

    /**
     * 初始化获取权限
     */
    private void initGetPermission() {

        List<PermissionBean> permissionBeenList = new ArrayList<>();

        //写入内存卡权限
        permissionBeenList.add(new PermissionBean(false, Manifest.permission.WRITE_EXTERNAL_STORAGE));
        List<String> needRequestPermission = new ArrayList<>();

        //检测有没有授权
        for (int i = 0; i < permissionBeenList.size(); i++) {
            if (ContextCompat.checkSelfPermission(this, permissionBeenList.get(i).getName())
                    == PackageManager.PERMISSION_GRANTED) {
                permissionBeenList.get(i).setGranted(true);
            }
        }

        //遍历出 需要的权限
        for (int i = 0; i < permissionBeenList.size(); i++) {
            if (!permissionBeenList.get(i).isGranted()) {
                needRequestPermission.add(permissionBeenList.get(i).getName());
            }
        }

        Log.v("Lin", needRequestPermission.toString());

        if (needRequestPermission.size() == 0) {
            Log.v("Lin", "全部已经授权");
        } else {
            Log.v("Lin", "还有没有授权的");
            ActivityCompat.requestPermissions(this, needRequestPermission.toArray(new String[needRequestPermission.size()]), 200);
        }

    }

    //指定的位置获取权限
    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.v("Lin", "没授权");
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "没有获取相机权限", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        } else {
            Log.v("Lin", "已经授权");
            startActivityForResult((PicAty.getStartIntent(getApplicationContext(), 5)), 1001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v("Lin", "权限已被授予");
                startActivityForResult((PicAty.getStartIntent(getApplicationContext(), 5)), 1001);
            } else {
                Log.v("Lin", "权限没被授予");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            if (data != null) {
                List<LocalPicBean> localPicBeans = data.getParcelableArrayListExtra("list");
                Log.v("Lin2", "MainAty收到反馈的图片大小:" + localPicBeans.size());
                //对图片的一个排序
                Collections.sort(localPicBeans, new Comparator<LocalPicBean>() {
                    @Override
                    public int compare(LocalPicBean o1, LocalPicBean o2) {
                        return o1.getSelectedSign() - o2.getSelectedSign();
                    }
                });

                mainAdapter.setData(localPicBeans);
            } else {
                Toast.makeText(this, "并未选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

package com.wei.mypic.aty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wei.mypic.R;

public class ImageInfoAty extends AppCompatActivity{

   private TextView tv_info;
   private String info;

    public static Intent getStartIntent(Context context,String info){
        Intent intent=new Intent(context,ImageInfoAty.class);
        intent.putExtra("info",info);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_img_info);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        info=getIntent().getStringExtra("info");
        tv_info=findViewById(R.id.tv_info);
        tv_info.setText(info);

    }
}

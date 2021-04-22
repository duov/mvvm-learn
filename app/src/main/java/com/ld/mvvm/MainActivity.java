package com.ld.mvvm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ld.baselibrary.arouter.ARouterPath;

public class MainActivity extends AppCompatActivity {
    private int REQUEST_CODE = 0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tvTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
                ARouter.getInstance()
                        .build(ARouterPath.UserActivity)
                        .withTransition(R.anim.in_from_left, R.anim.out_from_right)
                        .navigation(MainActivity.this, REQUEST_CODE);
            }
        });

        findViewById(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test network
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            Toast.makeText(this, "User Back", Toast.LENGTH_SHORT);
        }
    }
}

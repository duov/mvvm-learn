package com.ld.model_user.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ld.baselibrary.arouter.ARouterPath;
import com.ld.model_user.R;

/**
 * @author: liangduo
 * @date: 2020/7/22 8:54 PM
 */
@Route(path = ARouterPath.UserActivity)
public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        findViewById(R.id.user_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}

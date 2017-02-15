package com.zhy.demo.mynews;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zhy.demo.mynews.databinding.ActivityMainBinding;
import com.zhy.demo.mynews.model.User;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mUser = new User("张三","15",false);
        mBinding.setModel(mUser);
    }

    public void onClick(View view) {
        Toast.makeText(this, "你点击了按钮", Toast.LENGTH_SHORT).show();
       mUser.setFlag(!mUser.isFlag());
        mUser.setName("李四");
        mUser.setAge("22");
        mBinding.setModel(mUser);
    }
}

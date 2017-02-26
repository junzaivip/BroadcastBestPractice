package com.junzaivip.broadcastbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017-2-25.
 */

public class BaseActivity  extends AppCompatActivity{
    private ForceOffLineReceiver receive;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityConllector.addActivity(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityConllector.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.junzaivip.broadcastbestpractice.FORCE_OFFLINE");
        receive = new ForceOffLineReceiver();
        registerReceiver(receive, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receive!= null){
            unregisterReceiver(receive);
            receive = null;
        }
    }

    class ForceOffLineReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, final Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("警告");
            builder.setMessage("您被强制下线, 请您重新登录!");
            builder.setCancelable(false); // 将对话框设置为不可取消
            // 给按钮添加注册监听
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 点击按钮所调用的方法
                    ActivityConllector.finishAll();//销毁所有的活动
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            });
            builder.show();
        }
    }

}

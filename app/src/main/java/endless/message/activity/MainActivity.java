package endless.message.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import endless.message.R;

public class MainActivity extends Activity {
    private ListView listview;
    private List<GetMassages>getMassages;
    private  static final String SMS_ALL ="content://sms/sent";
    private static final  int READ_SMS=0;
    private static final  int  RECEIVE_SMS=1;
    boolean is_exit=false;
    //第一次点击退出的时间戳
    long  l_firstClickTime;
    //第二次点击的时间戳
    long l_secondClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击退出
        doubleClickExit(keyCode, event);
        return true;
    }


    /**
     * 双击退出函数
     */
    private  void doubleClickExit(int keyCode, KeyEvent event){
        //当用户第一次点击按钮时
        if(keyCode==KeyEvent.KEYCODE_BACK && is_exit==false){
            //设置第一次点击后的布尔值，改为true
            is_exit=true;
            l_firstClickTime=System.currentTimeMillis();
            //显示再次点击退出函数
            Toast.makeText(this,"再次点击退出",Toast.LENGTH_SHORT).show();
        }else if(keyCode==KeyEvent.KEYCODE_BACK && is_exit==true ){
            l_secondClickTime=System.currentTimeMillis();
            //时间差小于2000ms
            if(l_secondClickTime-l_firstClickTime<2000){
                finish();//结束程序
            }else{
                is_exit=false;//超过2000秒 重置第一次点击前的布尔值
                doubleClickExit(keyCode,event);//时间超过后 点击重新调用方法
            }
        }


    }


    private  void iniView(){
        Uri uri = Uri.parse(SMS_ALL);
        ReadMessages rm = new ReadMessages(this, uri);
        getMassages = rm.MassageType();
        listview = (ListView) this.findViewById(R.id.lv_messages);
        listview.setAdapter(new SmsListAdapter(this));
    }



    class SmsListAdapter extends BaseAdapter {
        private LayoutInflater layoutinflater;
        private View myview;

        public SmsListAdapter(Context c) {
            layoutinflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return getMassages.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                myview = layoutinflater.inflate(R.layout.item_message, null);
            }
            final TextView body = (TextView) myview
                    .findViewById(R.id.tv_message);
            TextView name = (TextView) myview
                    .findViewById(R.id.tv_person);
            body.setText(" 内容 ："+getMassages.get(position).getSmsbody());
            name.setText(" 发件人： "+getMassages.get(position).getPhoneNumber());
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(MainActivity.this,MassageContext.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("number",getMassages.get(position).getPhoneNumber());
                    bundle.putString("context",getMassages.get(position).getSmsbody());
                    intent.putExtra("key",bundle);
                    startActivity(intent);
                }
            });


            return myview;
        }

    }

    /**
     * @description 申请运行时权限
     */
    private  void requestPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            //申请获取用户精确位置的权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},
                    READ_SMS);
        }else{
            iniView();
        }
        //检查是否拥有读取通讯录的权限
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS)
                !=PackageManager.PERMISSION_GRANTED){
            //申请READ_CONTACTS的权限
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.RECEIVE_SMS},RECEIVE_SMS);
        }else{
            iniView();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[]permissions,int []grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        //判断申请码
        switch(requestCode){
            case READ_SMS :
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //申请的第一个权限成功后
                    iniView();

                }else{
                    //申请的第二个权限成功后
                    finish();
                }
                break;

            case RECEIVE_SMS:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //申请的第一个权限成功后
                }else{
                    //申请的第二个权限成功后
                    finish();
                }
                break;


        }



    }
}
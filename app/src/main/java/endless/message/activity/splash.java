package endless.message.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import endless.message.R;

public class splash extends Activity {

    private ImageView iv_splash_background;
    private Drawable[] drawables;
    private AlphaAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new InitTask().execute();
    }

    /**
     * 初始化控件
     */
    private  void  intiView(){
        //加载整个布局界面
        iv_splash_background=(ImageView)findViewById(R.id.iv_splash_background);

    }

    /**
     * 页面背景素材加载
     * 并随开机设置为背景
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private  void loading(){
        drawables=new Drawable[4];
        drawables[0] = getResources().getDrawable(R.mipmap.bg1);
        drawables[1] = getResources().getDrawable(R.mipmap.bg2);
        drawables[2] = getResources().getDrawable(R.mipmap.bg3);
        drawables[3] = getResources().getDrawable(R.mipmap.bg4);
        int i= (int)(Math.random()*4);
        iv_splash_background.setBackground(drawables[i]);
    }
    /**
     * 给背景加入淡入动画效果
     */

    private  void  intiAnim(){
        animation=new AlphaAnimation(0.5f,1f);
        animation.setDuration(3000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /**
             * 动画播放完毕后执行跳转界面并且销毁动画
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(splash.this,MainActivity.class));
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        iv_splash_background.startAnimation(animation);
    }
    /**
     *内部类，操作异步任务，保证运行时，先加载再调用  不出现运行时异常
     */


    class InitTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            intiView();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loading();
            intiAnim();
        }
    }
}

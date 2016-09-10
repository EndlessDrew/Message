package endless.message.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import endless.message.R;

public class MassageContext extends Activity {
    TextView tv_content_number;
    TextView tv_content_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage_context);
        initView();
        insertText();

    }
    private  void  initView(){
        tv_content_text=(TextView)findViewById(R.id.tv_content_text);
        tv_content_number=(TextView)findViewById(R.id.tv_content_number);
    }

    private void insertText(){

        Bundle bundle=getIntent().getBundleExtra("key");
        tv_content_number.setText( bundle.getString("number"));
        tv_content_text.setText(bundle.getString("context"));

    }




}

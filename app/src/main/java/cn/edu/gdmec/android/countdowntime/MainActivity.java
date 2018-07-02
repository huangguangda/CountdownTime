package cn.edu.gdmec.android.countdowntime;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    /*
    * 倒计时标记handle code.
    * */
    public static final int COUNTDOWN_TIME_CODE = 100001;
    //时间间隔
    public static final int DELAY_MILLIS = 1000;
    public static final int MAX_COUNT = 10;

    private TextView countdownTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //得到控件
        countdownTimeTextView = findViewById(R.id.countdownTimeTextView);

        //创建了一个静态的handler
        CountdownTimeHandler handler = new CountdownTimeHandler(this)/*{
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg != null && msg.what == COUNTDOWN_TIME_CODE){
                    countdownTimeTextView.setText(String.valueOf(9));
                }
            }
        }*/;
        //新建了一个message

        //发消息
        Message message = Message.obtain();
        //消息池
        //标识
        message.arg1= MAX_COUNT;
        message.what = COUNTDOWN_TIME_CODE;

        //第一次发送message
        handler.sendMessageDelayed(message, DELAY_MILLIS);
    }

    public static class CountdownTimeHandler extends Handler{
        public static final int MIN_COUNT = 0;
        final WeakReference<MainActivity> mWeakReference;

        public CountdownTimeHandler(MainActivity activity){
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mWeakReference.get();

            switch (msg.what){
                case COUNTDOWN_TIME_CODE:
                    int value=msg.arg1;
                    activity.countdownTimeTextView.setText(String.valueOf(value--));

                    if (value>= MIN_COUNT){
                        Message message = Message.obtain();
                        message.what = COUNTDOWN_TIME_CODE;
                        message.arg1=value;
                        sendMessageDelayed(message,DELAY_MILLIS);
                    }

                    break;
            }
        }
    }


}



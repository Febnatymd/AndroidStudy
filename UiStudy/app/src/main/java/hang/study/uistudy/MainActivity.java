package hang.study.uistudy;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private long mStart = 0;
    private long mTime = 0;
    private TimeHandler mHandler;

    private String logTag = "hangDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(logTag,"onCreate");
        mHandler = new TimeHandler(this);
        //
        TextView tvCounter = (TextView)findViewById(R.id.counter);
        tvCounter.setText(DateUtils.formatElapsedTime(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){

        super.onDestroy();
    }

    private void startTimer(){
        mStart = System.currentTimeMillis();
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessage(0);

    }

    private void stopTime(){
        mHandler.removeMessages(0);
    }

    private boolean isTimerRunning(){
        return mHandler.hasMessages(0);
    }

    private void resetTimer(){
        //TODO resetTimer
    }

    @Override
    public void onClick(View v) {
        Log.d(logTag,"enter onClick "+v.getId());
        if(v.getId() == R.id.start_stop){
            //Log.d(logTag,"enter onClick ");
            Button btn = (Button)findViewById(R.id.start_stop);
            if(isTimerRunning()){
                stopTime();
                btn.setText(R.string.start);

            }else{
                startTimer();
                btn.setText(R.string.stop);
            }
        }else if(v.getId() == R.id.reset){
            //
        }

    }

    private class TimeHandler extends Handler{
        private WeakReference<MainActivity> mActivityRef;

        public TimeHandler(MainActivity activity){
            mActivityRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg){
            MainActivity activity = mActivityRef.get();
            if(activity != null) {
                long current = System.currentTimeMillis();
                activity.mTime = current-activity.mStart;
                activity.mStart = current;

                TextView counter = (TextView)activity.findViewById(R.id.counter);
                counter.setText(DateUtils.formatElapsedTime(activity.mTime/1000));

                sendEmptyMessageDelayed(0, 250);
            }
        }

    }
}

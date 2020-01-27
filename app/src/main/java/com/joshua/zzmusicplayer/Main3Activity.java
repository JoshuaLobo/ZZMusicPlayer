package com.joshua.zzmusicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class Main3Activity extends AppCompatActivity {
    boolean playing = false;
    private double startTime = 0;
    private double finalTime = 0;
    private int forwardTime = 10000;
    private int backwardTime = 10000;
    private Handler myHandler = new Handler();
    SeekBar sbar;
    MediaPlayer mp;
    TextView tx1,tx2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ImageButton play = (ImageButton) findViewById(R.id. button_play);
        ImageButton pause = (ImageButton) findViewById(R.id. button_pause);
        ImageButton stop = (ImageButton) findViewById(R.id. button_stop);
        ImageButton frwd10=(ImageButton) findViewById(R.id.button_frwd10);
        ImageButton back10=(ImageButton) findViewById(R.id.button_back10);

        // MediaPlayer mp=MediaPlayer.create(getBaseContext(),R.raw.sample);
        sbar=(SeekBar)findViewById(R.id.seekBar2);
        mp=MediaPlayer.create(getBaseContext(),R.raw.track3);



        tx1=(TextView)findViewById(R.id.tx1);
        tx2=(TextView)findViewById(R.id.tx2);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!playing){
                    mp.start();
                    playing=true;
                    finalTime = mp.getDuration();
                    startTime = mp.getCurrentPosition();
                    sbar.setMax((int) finalTime);
                    sbar.setProgress((int)startTime);
                    myHandler.postDelayed(UpdateSongTime,100);
                }
                tx1.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );
                tx2.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

            }
        });
        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(playing){
                    mp.pause();
                    playing=false;


                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (playing) {
                    mp.stop();
                    playing = false;
                }

            }
        });
        frwd10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;

                if ((temp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mp.seekTo((int) startTime);
                }
            }
        });

        back10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mp.seekTo((int) startTime);

                }
            }
        });



    }
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mp.getCurrentPosition();
            tx1.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );


            sbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    public void nextsong(View view)
    {
        Toast.makeText(this,"Playing next song",Toast.LENGTH_SHORT).show();
        try
        {TimeUnit.SECONDS.sleep(1);}
        catch(InterruptedException e)
        {e.printStackTrace();}
        stopsong(view);
        stop();
        Intent i=new Intent(this,Main2Activity.class);
        startActivity(i);
        finish();

    }
    public void stop()
    {
        if (playing) {
            mp.stop();
            playing = false;
        }
    }
    public void stopsong(View view)
    {
        if (playing) {
            mp.stop();
            playing = false;
        }
    }

    public void prevsong(View view) {
    }
}

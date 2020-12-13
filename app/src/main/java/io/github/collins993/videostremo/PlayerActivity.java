package io.github.collins993.videostremo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;


import static io.github.collins993.videostremo.RecAdapter.VIDEO_DATA_EXTRA;

public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    ProgressBar progressBar;
    ImageView fullScreen;
    FrameLayout frameLayout;
    VideoView videoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        progressBar = findViewById(R.id.progressBar);
        fullScreen = findViewById(R.id.fullScreen);
        frameLayout = findViewById(R.id.frameLayout);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        Video video = (Video) data.getSerializable(VIDEO_DATA_EXTRA);
        getSupportActionBar().setTitle(video.getTitle());

        TextView title = findViewById(R.id.videoTitle);
        TextView description = findViewById(R.id.videoDescription);
        videoPlayer = findViewById(R.id.videoView);

        title.setText(video.getTitle());
        description.setText(video.getDescription());
        Uri videoUrl = Uri.parse(video.getVideoUrl());
        videoPlayer.setVideoURI(videoUrl);
        MediaController mediaController = new MediaController(this);
        videoPlayer.setMediaController(mediaController);
        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoPlayer.start();
                progressBar.setVisibility(View.GONE);
            }
        });

        setFullScreen();
        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().show();
        fullScreen.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int heightValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 220, getResources().getDisplayMetrics());
        frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightValue)));
        videoPlayer.setLayoutParams(new FrameLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightValue)));

    }

    public void setFullScreen() {
        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                fullScreen.setVisibility(View.GONE);
                getSupportActionBar().hide();
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)));
                videoPlayer.setLayoutParams(new FrameLayout.LayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));

            }
        });
    }
}
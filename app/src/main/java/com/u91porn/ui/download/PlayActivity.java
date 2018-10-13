package com.u91porn.ui.download;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.u91porn.R;

import top.bingoz.player.StandardPlayer;
import top.bingoz.player.utils.CommonUtil;
import top.bingoz.player.utils.OrientationUtils;

/**
 * @author su
 * @date 2018/6/2
 * @description
 */
public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TITLE = "title";
    public static final String PATH = "path";
    OrientationUtils mOrientationUtils;
    private StandardPlayer videoPlayer;
    private Context context;
    private int mSystemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_play);
        getWindow().getDecorView().setSystemUiVisibility(mSystemUiVisibility);
        String title = getIntent().getStringExtra(TITLE);
        String filePath = getIntent().getStringExtra(PATH);

        mOrientationUtils = new OrientationUtils(this);

        videoPlayer = findViewById(R.id.video_view);
        View titleContainerView = View.inflate(this, R.layout.item_top_default, null);

        TextView titleView = titleContainerView.findViewById(R.id.title);
        titleView.setText(title);

        titleContainerView.findViewById(R.id.fullscreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrientationUtils.resolveByClick();
                if (mOrientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    CommonUtil.showNavKey(context, mSystemUiVisibility);
                } else {
                    CommonUtil.hideNavKey(context);
                }
            }
        });

        videoPlayer.setTopContainerView(titleContainerView);

        videoPlayer.setVideoPath(filePath);
        videoPlayer.start();
    }

    @Override
    protected void onPause() {
        videoPlayer.savePlayerState();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.restorePlayerState();

    }

    @Override
    protected void onDestroy() {
        if (mOrientationUtils != null) {
            mOrientationUtils.releaseListener();
        }
        videoPlayer.destroy();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

    @Override
    public void onBackPressed() {
        if (mOrientationUtils != null && mOrientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mOrientationUtils.resolveByClick();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}

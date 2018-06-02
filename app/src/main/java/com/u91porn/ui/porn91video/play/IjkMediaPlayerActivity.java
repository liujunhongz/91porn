package com.u91porn.ui.porn91video.play;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.u91porn.R;
import com.u91porn.utils.GlideApp;

import java.io.File;

import tv.lycam.player.StandardPlayer;
import tv.lycam.player.utils.OrientationUtils;

/**
 * @author flymegoc
 */
public class IjkMediaPlayerActivity extends BasePlayVideo {

    private static final String TAG = IjkMediaPlayerActivity.class.getSimpleName();
    private StandardPlayer videoPlayer;
    private View mTitleContainerView;
    private ImageView mThumbnailView;
    OrientationUtils mOrientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOrientationUtils = new OrientationUtils(this);
    }

    @Override
    public void initPlayerView() {
        View view = LayoutInflater.from(this).inflate(R.layout.playback_engine_exo_media, videoplayerContainer, true);
        videoPlayer = view.findViewById(R.id.video_view);
        mTitleContainerView = View.inflate(this, R.layout.item_top_default, null);
        videoPlayer.setTopContainerView(mTitleContainerView);
        mThumbnailView = new ImageView(this);
        videoPlayer.setThumbImageView(mThumbnailView);

    }

    @Override
    public void playVideo(String title, String videoUrl, String name, String thumImgUrl) {

        mTitleContainerView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (!TextUtils.isEmpty(thumImgUrl)) {
            GlideApp.with(this).load(Uri.parse(thumImgUrl)).transition(new DrawableTransitionOptions().crossFade(300)).into(mThumbnailView);
        }
        //加载本地下载好的视频
        if (unLimit91PornItem.getStatus() == FileDownloadStatus.completed) {
            File downloadFile = new File(unLimit91PornItem.getDownLoadPath());
            if (downloadFile.exists()) {
                videoPlayer.setVideoPath(downloadFile.getAbsolutePath());
            } else {
                String proxyUrl = httpProxyCacheServer.getProxyUrl(videoUrl);
                videoPlayer.setVideoPath(proxyUrl);
            }
        } else {
            String proxyUrl = httpProxyCacheServer.getProxyUrl(videoUrl);
            videoPlayer.setVideoPath(proxyUrl);
        }
        videoPlayer.start();
        TextView titleView = mTitleContainerView.findViewById(R.id.title);
        titleView.setText(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.restorePlayerState();
    }

    @Override
    protected void onPause() {
        videoPlayer.savePlayerState();
        super.onPause();

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
    protected void onDestroy() {
        if (mOrientationUtils != null) {
            mOrientationUtils.releaseListener();
        }
        videoplayerContainer.removeView(videoPlayer);
        videoPlayer.destroy();
        super.onDestroy();
    }
}

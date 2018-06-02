package com.u91porn.utils;

import android.content.Context;
import android.content.Intent;

import com.u91porn.ui.download.PlayActivity;
import com.u91porn.ui.porn91video.play.IjkMediaPlayerActivity;

import static com.u91porn.ui.download.PlayActivity.PATH;
import static com.u91porn.ui.download.PlayActivity.TITLE;

/**
 * 播放引擎切换
 *
 * @author flymegoc
 * @date 2018/1/2
 */

public class PlaybackEngine {
    public static final String[] PLAY_ENGINE_ITEMS = new String[]{"Google Exoplayer Engine", "JiaoZiPlayer Engine",};
    private static final int IJKMEDIAPLAYER_ENGINE = 0;
    private static final int JIAOZIVIDEOPLAYER_ENGINE = 1;
    public static final int DEFAULT_PLAYER_ENGINE = IJKMEDIAPLAYER_ENGINE;

    /**
     * 获取播放引擎
     *
     * @param context 上下文
     * @return intent
     */
    public static Intent getPlaybackEngineIntent(Context context, int engine) {

        Intent intent = new Intent();
        switch (engine) {
            case IJKMEDIAPLAYER_ENGINE:
                intent.setClass(context, IjkMediaPlayerActivity.class);
                break;
            case JIAOZIVIDEOPLAYER_ENGINE:
                intent.setClass(context, IjkMediaPlayerActivity.class);
//                intent.setClass(context, JiaoZiVideoPlayerActivity.class);
                break;
            default:
        }
        return intent;
    }


    /**
     * 直接播放
     * @param context
     * @param title
     * @param path
     */
    public static void play(Context context, String title, String path) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(PATH, path);
        context.startActivity(intent);
    }

}

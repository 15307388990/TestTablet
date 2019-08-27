package com.cx.testtablet.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Max on 2018-07-18 20:03.
 */
public class PlayMusicUtils {
    private static PlayMusicUtils mPlayMusicUtils;
    private MediaPlayer mMediaPlay = null;
    private AssetFileDescriptor fileDescriptor;
    private AssetManager mAssetManager;
    private String musicPath = "";

    private PlayMusicUtils() {
    }

    public static PlayMusicUtils getmPlayMusicUtils() {
        if (mPlayMusicUtils == null) {
            synchronized (PlayMusicUtils.class) {
                if (mPlayMusicUtils == null) {
                    mPlayMusicUtils = new PlayMusicUtils();
                }
            }
        }
        return mPlayMusicUtils;
    }

    public void toPlayWithListener(String fileName, PlayMusicListener listener) {
        if (mMediaPlay == null) {
            mMediaPlay = new MediaPlayer();
        }

        if (musicPath.equals(fileName)) {
            if (mMediaPlay.isPlaying()) {
                mMediaPlay.pause();
                musicPath = "";
                listener.onCompletion();
            } else {
                mMediaPlay.start();
                listener.onStart();
            }
        } else {
            musicPath = fileName;
            try {
                mMediaPlay.reset();
                mMediaPlay.setDataSource(fileName);
                mMediaPlay.prepare();
                mMediaPlay.setOnPreparedListener(mp -> {
                    mMediaPlay.start();
                    listener.onStart();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mMediaPlay != null) {
            mMediaPlay.setOnCompletionListener(mediaPlayer -> {
                listener.onCompletion();
                musicPath = "";
            });
            mMediaPlay.setOnErrorListener((mediaPlayer, i, i1) -> {
                listener.onError();
                return false;
            });
        }
    }

    public void toPlay(Context context) {
        if (mMediaPlay == null) {
            mMediaPlay = new MediaPlayer();
        }

        try {
            if (mMediaPlay.isPlaying())
                return;

            mMediaPlay.reset();
            fileDescriptor = context.getAssets().openFd("volume.mp3");
            mMediaPlay.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mMediaPlay.prepare();
            mMediaPlay.setOnPreparedListener(mp -> {
                mMediaPlay.start();
            });

            mMediaPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.e("-----播放完毕：","-------------");
                    mMediaPlay.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toDestroy() {
        if (mMediaPlay == null)
            return;

        if (mMediaPlay.isPlaying()) {
            mMediaPlay.stop();
            mMediaPlay.release();
            mMediaPlay = null;
            musicPath = "";
        }
    }

    public void stopPlay() {
        if (mMediaPlay != null && mMediaPlay.isPlaying()) {
            mMediaPlay.pause();
        }
    }

    public void start() {
        if (mMediaPlay != null)
            mMediaPlay.start();
    }
}

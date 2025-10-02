package com.example.horseracing.data;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.horseracing.R;

public class AudioPlayer {
    private static MediaPlayer bgmPlayer;
    private static MediaPlayer sfxPlayer;
    private static boolean isBgmEnabled = true;
    private static boolean isSfxEnabled = true;

    // Background Music Controls
    public static synchronized void playBgm(Context context) {
        if (!isBgmEnabled) return;
        
        if (bgmPlayer == null) {
            bgmPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.backgroudmusic); // Note: File name has typo but keeping for compatibility
            if (bgmPlayer != null) {
                bgmPlayer.setLooping(true);
            }
        }
        if (bgmPlayer != null && !bgmPlayer.isPlaying()) {
            bgmPlayer.start();
        }
    }

    public static synchronized void stopBgm() {
        if (bgmPlayer != null) {
            try {
                if (bgmPlayer.isPlaying()) bgmPlayer.stop();
            } catch (Exception ignored) {}
            try {
                bgmPlayer.release();
            } catch (Exception ignored) {}
            bgmPlayer = null;
        }
    }

    public static synchronized void pauseBgm() {
        if (bgmPlayer != null && bgmPlayer.isPlaying()) {
            try {
                bgmPlayer.pause();
            } catch (Exception ignored) {}
        }
    }

    public static synchronized void resumeBgm() {
        if (bgmPlayer != null && !bgmPlayer.isPlaying() && isBgmEnabled) {
            try {
                bgmPlayer.start();
            } catch (Exception ignored) {}
        }
    }

    // Sound Effects
    public static synchronized void playButtonClick(Context context) {
        if (!isSfxEnabled) return;
        playOneShot(context, R.raw.touchbuttonsound);
    }

    public static synchronized void playHorseRacing(Context context) {
        if (!isSfxEnabled) return;
        playOneShot(context, R.raw.horsesoundracing);
    }

    public static synchronized void playWinner(Context context) {
        if (!isSfxEnabled) return;
        playOneShot(context, R.raw.winnersound);
    }

    public static synchronized void playGameOver(Context context) {
        if (!isSfxEnabled) return;
        playOneShot(context, R.raw.gameoversound);
    }

    // Legacy method for compatibility
    public static synchronized void playClick(Context context) {
        playButtonClick(context);
    }

    // Audio Settings
    public static void setBgmEnabled(boolean enabled) {
        isBgmEnabled = enabled;
        if (!enabled) {
            stopBgm();
        }
    }

    public static void setSfxEnabled(boolean enabled) {
        isSfxEnabled = enabled;
    }

    public static boolean isBgmEnabled() {
        return isBgmEnabled;
    }

    public static boolean isSfxEnabled() {
        return isSfxEnabled;
    }

    // Utility Methods
    public static boolean isBgmPlaying() {
        return bgmPlayer != null && bgmPlayer.isPlaying();
    }

    private static void playOneShot(Context context, int resId) {
        try {
            if (sfxPlayer != null) {
                try { sfxPlayer.release(); } catch (Exception ignored) {}
            }
            sfxPlayer = MediaPlayer.create(context.getApplicationContext(), resId);
            if (sfxPlayer != null) {
                sfxPlayer.setOnCompletionListener(mp -> {
                    try { mp.release(); } catch (Exception ignored) {}
                });
                sfxPlayer.start();
            }
        } catch (Exception ignored) {}
    }

    // Cleanup method
    public static synchronized void cleanup() {
        stopBgm();
        if (sfxPlayer != null) {
            try { sfxPlayer.release(); } catch (Exception ignored) {}
            sfxPlayer = null;
        }
    }
}



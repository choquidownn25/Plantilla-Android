package com.example.plantilla.youtube;

import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by choqu_000 on 07/03/2017.
 */

public class MyPlaybackEventListener  implements YouTubePlayer.PlaybackEventListener {
    @Override
    public void onPlaying() {
        // Called when playback starts, either due to user action or call to play().
        showMessage("Playing");
    }

    @Override
    public void onPaused() {
        // Called when playback is paused, either due to user action or call to pause().
        showMessage("Paused");
    }

    @Override
    public void onStopped() {
        // Called when playback stops for a reason other than being paused.
        showMessage("Stopped");
    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}

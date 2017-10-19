package com.kis.shufflegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class Sound {
	public static MediaPlayer player;
	private Context view;

	public Sound(Context view) {
		this.view = view;

	}

	public void play(int sound, Boolean loop) {
		player = MediaPlayer.create(view, sound);
		if (player.isPlaying() || player.isLooping()) {
			stop();

		} else {
			if (Home.music && loop) {
				player.setLooping(loop);
				player.start();
			}
			if (Home.button) {
				player.start();
			}
		}
	}

	public void stop() {
		if (player.isPlaying() || player.isLooping()) {
			player.stop();
			player.release();
		}
	}
}

package com.kis.shufflegame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends Activity implements Runnable {

	ImageView sp;
	Thread t;
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		init();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			t.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			mp.release();
			Intent intent = new Intent(this, Home.class);
			startActivity(intent);
			Splash.this.finish();
		}
	}
	private void init()
	{
		mp = MediaPlayer.create(this, R.raw.start);
		mp.start();
		t = new Thread(this);
		t.start();
		
		sp = (ImageView) findViewById(R.id.splash);
		
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.animation);
		sp.startAnimation(anim);
		
		sp.setBackgroundResource(R.anim.anim);
		AnimationDrawable anims = (AnimationDrawable) sp.getBackground();
		anims.start();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mp.release();
		this.finish();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.release();
		this.finish();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mp.release();
		this.finish();
	}
	

}

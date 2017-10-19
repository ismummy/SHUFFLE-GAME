package com.kis.shufflegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;


public class Home extends Activity implements OnClickListener{

	ImageButton tech, education, sport, business,politics,history,random,score,settings,about;
	Sound sound;
	public static Boolean music, button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComp();
    }

    private void initComp()
    {
    	tech = (ImageButton) findViewById(R.id.tech);
    	education  = (ImageButton) findViewById(R.id.education);
    	sport  = (ImageButton) findViewById(R.id.sport);
    	business  = (ImageButton) findViewById(R.id.business);
    	politics  = (ImageButton) findViewById(R.id.politics);
    	history  = (ImageButton) findViewById(R.id.history);
    	random = (ImageButton) findViewById(R.id.random);
    	score = (ImageButton) findViewById(R.id.score);
    	settings = (ImageButton) findViewById(R.id.settings);
    	about = (ImageButton) findViewById(R.id.about);
    	
    	
    	
    	tech.setOnClickListener(this);
    	education.setOnClickListener(this);
    	sport.setOnClickListener(this);
    	business.setOnClickListener(this);
    	politics.setOnClickListener(this);
    	history.setOnClickListener(this);
    	random.setOnClickListener(this);
    	score.setOnClickListener(this);
    	settings.setOnClickListener(this);
    	about.setOnClickListener(this);
    	
    	music = button = true;
    	
    	sound = new Sound(Home.this);
    	sound.play(R.raw.start, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		int id = arg0.getId();
		final String cat = arg0.getTag().toString();
		sound.play(R.raw.click, false);
		
		if(id==tech.getId() || id == education.getId() || id == sport.getId() || id == business.getId() || id == politics.getId() || id == history.getId() || id == random.getId())
		{
			//dialog box
			final EditText name = new EditText(this);
			name.setHint("Player Name");
			AlertDialog.Builder ad = new AlertDialog.Builder(this);
			ad.setTitle("Emter Player Name");
			ad.setView(name);
			ad.setPositiveButton("Start", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if(!name.getText().toString().equals(""))
					{
						Intent intent = new Intent(Home.this, Game.class);
						intent.putExtra("player", name.getText().toString());
						intent.putExtra("category",cat);
						startActivity(intent);
					}
				}
			});
			ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			ad.show();
	    	
		}else if(id == about.getId())
		{
			Dialog d = new Dialog(this);
			d.setTitle("About Shuffle Game I");
			d.setContentView(R.layout.activity_about);
			d.show();
		}else if(id == settings.getId())
		{
			
			Dialog d = new Dialog(this);
			d.setTitle("SHUFFLE GAME Settings");
			d.setContentView(R.layout.activity_settings);
			
			final ToggleButton ms = (ToggleButton) d.findViewById(R.id.music);
			final ToggleButton s = (ToggleButton) d.findViewById(R.id.sound);
			
			ms.setChecked(music);
			s.setChecked(button);
			
			d.show();
			ms.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					music = ms.isChecked();
				}
			});
			s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					button = s.isChecked();
				}
			});
			
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sound.stop();
		finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sound.stop();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		sound.stop();
	}
}

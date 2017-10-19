package com.kis.shufflegame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Game extends Activity implements OnClickListener, OnKeyListener,
		Runnable {

	String name, category;
	TextView player, cat, c, d, e, f, g;
	EditText input;
	ProgressBar live;
	ImageButton previous, reshuffle, submit, next, enter;
	Sound sound;

	Thread timer;

	int option = 0;
	char[] a = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z' };
	int[] b = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
			R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
			R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l,
			R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p,
			R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t,
			R.drawable.u, R.drawable.v, R.drawable.w, R.drawable.x,
			R.drawable.y, R.drawable.z };

	String[] random, uWord;

	String[] tech = { "compiler", "interpreter", "java", "programming",
			"debugging", "exception", "thread", "translator", "application",
			"software", "hardware", "applet", "computer", "technology",
			"network", "firmware", "internet", "transmission", "protocol",
			"markup", "hypertext", "module", "processor", "harddisk",
			"flashdrive", "scanner", "caches", "register", "diode", "circuit",
			"analog", "digital", "hybrid", "memory", "binary", "printer",
			"cable", "router", "bridge", "wireless", "bluetooth", "ethernet",
			"calculator", "eniac", "edvac", "univac", "pseudocode", "tradic",
			"conductor", "fortran", "pascal", "prolog" };
	String[] education = { "education", "teacher", "lecture", "lecturer",
			"student", "academic", "academicain", "academist", "scholarship",
			"novel", "textbook", "journal", "diary", "biography", "classroom",
			"chancellor", "dean", "librarian", "library", "laboratory",
			"dormitory", "hostel", "college", "university", "department",
			"faculty" };
	String[] sport = { "sport", "referee", "football", "goal", "golf",
			"racquet", "racquetball", "game", "handball", "court", "rackets",
			"player", "umpire", "golfbag", "golfcart", "golfer", "linksman",
			"soccer", "coach", "field", "helmet", "league", "stadium",
			"skateboard", "skater", "skating", "swimmer", "volleyball" };
	String[] politics = { "politics", "power", "law", "executive", "bulwark",
			"shrewdest", "feudal", "patriarchal", "politicial", "assembly",
			"ministration", "justice", "libertarians", "royal", "kingship",
			"country", "treason", "petition", "custom", "constitution",
			"parliament", "judiciary", "democracy", "politique",
			"egalitarianism", "ideology", "enactment", "adopted", "illegal",
			"affairs", "stateless", "official", "secede", "weal" };
	String[] business = { "inventory", "interest", "labor", "intern",
			"partner", "policy", "portfolio", "profitable", "receipt",
			"retailer", "venture", "warranty", "waybill", "transaction",
			"trainee", "tariff", "treasurer", "treasury", "stock", "market",
			"staff", "signature", "shipping", "shareholder", "salesman",
			"overdraft", "inflation", "insurance", "deflation", "demand",
			"discount", "advertise", "accountant", "budget", "business",
			"copyright" };
	String[] history = { "hidtorical", "epoch", "story", "chronicle",
			"cultural", "bibliography", "biography", "naturalist", "sinology",
			"biogeny", "sphragistics", "hagiography", "myth", "prehistoric",
			"cliometrics", "ancient", "faure", "topology", "contemporary",
			"period", "antecedent", "dialectic", "evolution", "genesis",
			"iconology", "province", "trevelyan", "africana", "century",
			"travelyan", "restoration", "record", "pageant", "nation",
			"memory", "modern", "milestone", "domain", "period", "renaissance",
			"ontogeny", "old", "history" };

	ArrayList<String> game;
	ArrayList<char[]> shuffled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Bundle intent = getIntent().getExtras();
		name = intent.getString("player").toUpperCase();
		category = intent.getString("category");

		initComp();

		player.setText(name);
		cat.setText("GAME CATEGORY: " + category.toUpperCase());

	}

	private void initComp() {

		player = (TextView) findViewById(R.id.player);
		cat = (TextView) findViewById(R.id.cat);
		input = (EditText) findViewById(R.id.input);
		previous = (ImageButton) findViewById(R.id.previous);
		reshuffle = (ImageButton) findViewById(R.id.reshuffle);
		submit = (ImageButton) findViewById(R.id.submit);
		next = (ImageButton) findViewById(R.id.next);

		enter = (ImageButton) findViewById(R.id.enter);
		c = (TextView) findViewById(R.id.a);
		d = (TextView) findViewById(R.id.b);
		e = (TextView) findViewById(R.id.c);
		f = (TextView) findViewById(R.id.d);
		g = (TextView) findViewById(R.id.e);

		live = (ProgressBar) findViewById(R.id.live);
		live.setProgress(100);

		previous.setOnClickListener(this);
		reshuffle.setOnClickListener(this);
		submit.setOnClickListener(this);
		next.setOnClickListener(this);
		enter.setOnClickListener(this);

		input.setOnKeyListener(this);

		sound = new Sound(this);

		// initialize word bank

		random();
		game = new ArrayList<String>();
		shuffled = new ArrayList<char[]>();

		category();

		timer = new Thread(Game.this);
		timer.start();
		uWord = new String[5];
		uWord[0] = uWord[1] = uWord[2] = uWord[3] = uWord[4]= "";
	}

	private void random() {
		ArrayList<String> rand = new ArrayList<String>();

		for (String word : tech)
			rand.add(word);
		for (String word : education)
			rand.add(word);
		for (String word : sport)
			rand.add(word);
		for (String word : business)
			rand.add(word);
		for (String word : history)
			rand.add(word);

		for (String word : politics)
			rand.add(word);

		random = rand.toArray(new String[rand.size()]);
	}

	private void loadWord(String[] words) {
		ArrayList<String> word = new ArrayList<String>(Arrays.asList(words));
		Collections.shuffle(word);

		shuffled.clear();
		for (int i = 0; i < 5; i++) {
			game.add(word.get(i));
			char[] wor = word.get(i).toCharArray();
			shuffled.add(wor);
		}
		shuffle();
	}

	private void shuffle() {
		Random rand = new Random();

		for (int i = 0; i < 5; i++) {
			char temp;
			for (int j = 0; j < shuffled.get(i).length; j++) {
				int a = rand.nextInt(shuffled.get(i).length);
				temp = shuffled.get(i)[j];
				shuffled.get(i)[j] = shuffled.get(i)[a];
				shuffled.get(i)[a] = temp;
			}
		}
		printWord();
	}

	private void category() {
		if (category.equalsIgnoreCase("tech")) {
			loadWord(tech);
		} else if (category.equalsIgnoreCase("education")) {
			loadWord(education);
		} else if (category.equalsIgnoreCase("sport")) {
			loadWord(sport);
		} else if (category.equalsIgnoreCase("business")) {
			loadWord(business);
		} else if (category.equalsIgnoreCase("politics")) {
			loadWord(politics);
		} else if (category.equalsIgnoreCase("history")) {
			loadWord(history);
		} else if (category.equalsIgnoreCase("random")) {
			loadWord(random);
		}
	}

	private void printWord() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.word);
		layout.removeAllViews();
		Random ran = new Random();

		for (int i = 0; i < shuffled.get(option).length; i++) {
			ImageView letter = new ImageView(this);
			letter.setId(i);
			int image = b[Arrays.binarySearch(a, shuffled.get(option)[i])];
			letter.setImageResource(image);
			letter.setPadding(ran.nextInt(5), ran.nextInt(30), ran.nextInt(5),
					ran.nextInt(30));
			layout.addView(letter);
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id = view.getId();
		if (id == previous.getId()) {
			if (option > 0) {
				sound.play(R.raw.prev, false);
				--option;
				printWord();
				input.setText(uWord[option]);
			}
		} else if (id == next.getId()) {
			if (option < 4) {
				sound.play(R.raw.next, false);
				++option;
				printWord();
				input.setText(uWord[option]);
			}

		} else if (id == enter.getId()) {
			if (!input.getText().toString().equals("")) {
				sound.play(R.raw.enter, false);
				if (option == 0) {
					c.setText(input.getText().toString());
					uWord[option] = input.getText().toString();
					input.setText("");
				} else if (option == 1) {
					d.setText(input.getText().toString());
					uWord[option] = input.getText().toString();
					input.setText("");
				} else if (option == 2) {
					e.setText(input.getText().toString());
					uWord[option] = input.getText().toString();
					input.setText("");
				} else if (option == 3) {
					f.setText(input.getText().toString());
					uWord[option] = input.getText().toString();
					input.setText("");
				} else if (option == 4) {
					g.setText(input.getText().toString());
					uWord[option] = input.getText().toString();
					input.setText("");
				}
			}
		} else if (id == submit.getId()) {
			submit();
		} else if (id == reshuffle.getId()) {
			int cLive = live.getProgress();
			if (cLive >= 10) {
				sound.play(R.raw.shuf, false);
				live.setProgress(cLive - 10);
				shuffle();
			}
		}

	}

	@Override
	public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		sound.play(R.raw.type, false);
		return false;

	}

	private void submit() {
		sound.play(R.raw.over, false);
		double score = 0;
		for(int i=0; i<5; i++)
		{
			if(uWord[i].equalsIgnoreCase(game.get(i)))
				score += 50;
		}
		score += (live.getProgress()/10)*5;
		
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setCancelable(false);
		ad.setTitle("GAME OVER");
		ad.setMessage("SCORE: " + score);
		ad.setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				category();
				live.setProgress(100);
			}
		});
		ad.setNegativeButton("HOME", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Game.this, Home.class);
				startActivity(intent);
			}
		});
		ad.show();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				int cLive = live.getProgress();
				if (cLive == 0) {
					// submit
					submit();
				}
				timer.sleep(500 * 30);

				if (cLive >= 10) {
					live.setProgress(cLive - 10);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

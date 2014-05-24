package com.mj.swahiliproject;

import java.util.Random;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public  class Methali extends Fragment {

	public static final String NEXT_NUMBER_STRING = "Number";
	public static final String NEXT_KISW_STRING = "Kiswahili";
	public static final String NEXT_ENG_STRING = "English";
	public static final String JE_ILIPENDWA = "true";
	public Methali() {}//CONSTRUCTOR BABY...



	TextView tv1, tv2, tvBar;
	LinearLayout tvHolder, makekeHolder;
	ImageView favImage, shareBtn;
	Button btn;
	StartSwahili classMama = new StartSwahili();
	Typeface segoe, calibri, gothic ;

	String kisw, eng;
	int rn; boolean imependwa;
	SharedPreferences prefs2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_start_swahili_dummy, container, false);
		tv1 = (TextView) rootView.findViewById(R.id.textView1);
		tv2 = (TextView) rootView.findViewById(R.id.textView2);
		tvBar = (TextView)rootView.findViewById(R.id.textViewBar);
		btn = (Button) rootView.findViewById(R.id.button1);
		tvHolder = (LinearLayout) rootView.findViewById(R.id.innerLayout);
		favImage = (ImageView) rootView.findViewById(R.id.fav);
		shareBtn = (ImageView) rootView.findViewById(R.id.share);

		prefs2 = this.getActivity().getSharedPreferences("com.mj.swahiliproject",Context.MODE_PRIVATE );

		segoe = Typeface.createFromAsset(getActivity().getAssets(), "fonts/segoe.ttf");
		gothic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/gothic.ttf");
		calibri = Typeface.createFromAsset(getActivity().getAssets(), "fonts/calibri.ttf");

		tvBar.setTypeface(calibri);
		tv2.setTypeface(segoe);
		tv1.setTypeface(gothic);
		btn.setTypeface(calibri);

		btn.setText("NEXT RANDOM");
		shareBtn.setOnClickListener(new Mibofyo());
		favImage.setOnClickListener(new Mibofyo()); 
		btn.setOnClickListener(new Mibofyo()); 



		//getng args from bando
		kisw = getArguments().getString(NEXT_KISW_STRING);
		eng = getArguments().getString(NEXT_ENG_STRING);
		rn = getArguments().getInt(NEXT_NUMBER_STRING);

		imependwa = prefs2.contains("FAV:"+rn);
		tvBar.setText("Methali "+(rn-1));
		tv1.setText(kisw);
		tv2.setText(eng);
		setFavColor();
		if (rn==1) {firstQuote(); btn.setTextSize(18f);} else {btn.setTextSize(22f);}


		return rootView;
	}

	public void firstQuote() {
		tvBar.setText("#FrankCharlz");

		tv1.setText("If you talk to a man in a language he understands, that goes to his head."+
				" If you talk to him in his language, that goes to his heart.");

		tv2.setText("-Nelson Mandela");
		favImage.setVisibility(View.GONE);
		btn.setText("Swipe kulia na kushoto au bofya.");
	}


	public void setFavColor() {
		if (imependwa) {
			favImage.setImageResource(R.drawable.heart2);
		} else {favImage.setImageResource(R.drawable.heart);}
		
	}
	
	public void shareMethali() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Shared from FrankMagoti:\n"+kisw+"\n("+eng+")");
		sendIntent.setType("text/plain");
		//startActivity(sendIntent);
		startActivity(Intent.createChooser(sendIntent, "Share na Washikaji:"));
	}

	public void setFav(int x) {
		if(imependwa) {prefs2.edit().remove("FAV:"+x).commit();
		}
		else {
			prefs2.edit().putInt("FAV:"+x, x).commit();
		}
	}

	public class Mibofyo implements OnClickListener {
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.fav: 	setFav(rn);
			imependwa = prefs2.contains("FAV:"+rn);
			setFavColor();
			break;

			case R.id.button1: 
				Toast.makeText(getActivity(), "Keep calm men at work..", Toast.LENGTH_SHORT).show();
				/*
				Random rand = new Random();
				int next_random = 1+rand.nextInt(200);
				//((StartSwahili)getActivity()).mSectionsPagerAdapter.getItem(next_random);
				Fragment secFrag = new Methali();
				Bundle args = new Bundle();
				int nextPos = (next_random);
				args.putInt(Methali.NEXT_NUMBER_STRING, nextPos ); //forwads pos+1 to frgmnt thu next_nmb+str
				args.putString(Methali.NEXT_KISW_STRING, "world");
				args.putString(Methali.NEXT_ENG_STRING, "hello");
				secFrag.setArguments(args);
				android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
				                    transaction.replace(R.id.pager,secFrag );
				                    transaction.addToBackStack(null);
				                    transaction.commit();
				*/
				break;
				
			case R.id.share: shareMethali();


			default:
				break;
			}
		}
	}


}
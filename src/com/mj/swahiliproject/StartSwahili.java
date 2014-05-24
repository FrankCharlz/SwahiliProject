package com.mj.swahiliproject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.TabHost;

public class StartSwahili extends FragmentActivity implements ActionBar.TabListener {
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	public static final int IDADI = 200;
	Random rand = new Random();
	public String[][] meth = new String[IDADI][2];
	SharedPreferences prefs;
	ArrayList<Integer> favorites;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_swahili);
		
		prefs = this.getSharedPreferences("com.mj.swahiliproject",Context.MODE_PRIVATE );
		
		
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false); 
		actionBar.hide();
		
		

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
				
		}
		
		/**********populate the array with methali by calling forXml***************/
		try {
			forXml();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_swahili, menu);
		return true;
		
		//getResources().openRawResource(R.raw.);
		//getResources().openRawResource(R.drawable.)
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {//returns fragment
			Fragment fragment = new Methali();
			Bundle args = new Bundle();
			int nextPos = (position + 1)% IDADI;
			args.putInt(Methali.NEXT_NUMBER_STRING, nextPos ); //forwads pos+1 to frgmnt thu next_nmb+str
			args.putString(Methali.NEXT_KISW_STRING, meth[nextPos][0]);
			args.putString(Methali.NEXT_ENG_STRING, meth[nextPos][1]);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show <idadi> total pages.
			return IDADI; //=IDADI YA METHALI NA NAHAU BADAE....
		}

		@Override
		public String getPageTitle(int position) {
			Locale l = Locale.getDefault();
			return ""; //siifuti basi tu
		}
	}
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context ctx, Intent intent) {
			 String action = intent.getAction();
		        if (action != null) {
		            if (action.equals("ONGEZA_FAV")) {
		                // Do stuff
		            }
		        }
			
		}
	};

	/*********************************************************************************************/
	/*********** STUPID CODE FOR XML INALODISHA SAAANA NAHC ********************************/
	
	public void forXml() throws XmlPullParserException, IOException {

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser passa = factory.newPullParser();

		AssetManager ass = getAssets();
		InputStream file = ass.open("methali.xml");
		passa.setInput(file, null);


		int event = passa.getEventType();
		int i = 0;
		while (i<IDADI && event!=XmlPullParser.END_DOCUMENT) { //preparing 50 methalis
			event = passa.next();

			switch (event) {
			case XmlPullParser.START_TAG:
				String name = passa.getName();
				if (name.equalsIgnoreCase("kisw")) {
					meth[i][0]=passa.nextText();
					passa.nextTag();
					meth[i][1]=passa.nextText();
					i++;
				}
				break;

			default: break;

			}
		}


	}

}

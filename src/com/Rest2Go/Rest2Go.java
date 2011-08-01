package com.Rest2Go;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class Rest2Go extends TabActivity {

	private LocalActivityManager mlam;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_layout);
		Resources res = getResources();
	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    Intent intent;
	    
        mlam = new LocalActivityManager(this, false);
        mlam.dispatchCreate(savedInstanceState);
        tabHost.setup(mlam);

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, LocationActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("By Location").setIndicator("By Location",
	                      res.getDrawable(android.R.drawable.ic_menu_mylocation))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, SearchActivity.class);
	    spec = tabHost.newTabSpec("Search").setIndicator("Search",
	                      res.getDrawable(android.R.drawable.ic_menu_search))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		mlam.dispatchResume();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		mlam.dispatchResume();
	}
}

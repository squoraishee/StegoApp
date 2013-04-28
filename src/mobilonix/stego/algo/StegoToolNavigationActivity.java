package mobilonix.stego.algo;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class StegoToolNavigationActivity extends TabActivity {

	Resources res;
	TabHost tabHost;
	Intent intent;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stego_tool_nav_activity);

        res = getResources();
        tabHost = getTabHost();
        TabHost.TabSpec spec;


        intent = new Intent().setClass(this, StegoEncodeActivity.class);
        spec = tabHost.newTabSpec("calendar").setIndicator("Calendar", res.getDrawable(R.drawable.ic_action_search)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, StegoToolDecodeActivity.class);
        spec = tabHost.newTabSpec("profile").setIndicator("Profile", res.getDrawable(R.drawable.ic_action_search)).setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
 }
	
}

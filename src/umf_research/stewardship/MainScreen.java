package umf_research.stewardship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button woodsAndWildlifeButton;
        woodsAndWildlifeButton = (Button)findViewById(R.id.button1);
        woodsAndWildlifeButton.setBackgroundResource(R.drawable.btn_01_72px);
        woodsAndWildlifeButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent mainscreenIntent = new Intent();
				mainscreenIntent.putExtra("theme", 1);
				mainscreenIntent.setClass(getApplicationContext(),MapScreen.class);
	        	startActivity(mainscreenIntent);
			}
	    });	    

        Button waterButton;
        waterButton = (Button)findViewById(R.id.button2);
        waterButton.setBackgroundResource(R.drawable.btn_03_72px);
        waterButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent mainscreenIntent = new Intent();
				mainscreenIntent.putExtra("theme", 2);
				mainscreenIntent.setClass(getApplicationContext(),MapScreen.class);
	        	startActivity(mainscreenIntent);
			}
	    });
        
        Button communityButton;
        communityButton = (Button)findViewById(R.id.button3);
        communityButton.setBackgroundResource(R.drawable.btn_02_72px);
        communityButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent mainscreenIntent = new Intent();
				mainscreenIntent.putExtra("theme", 3);
				mainscreenIntent.setClass(getApplicationContext(),MapScreen.class);
	        	startActivity(mainscreenIntent);
			}
	    });	    
        
        
        
    }
}
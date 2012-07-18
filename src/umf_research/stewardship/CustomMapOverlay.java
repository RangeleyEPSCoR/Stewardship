package umf_research.stewardship;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;




public class CustomMapOverlay extends ItemizedOverlay {
	
	DbAdapter db;

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public CustomMapOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected boolean onTap(int index) {	  
	  Dialog dialog = new Dialog(mContext);
	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	  dialog.setContentView(R.layout.detail_dialog);
	   
	  Button shareButton = (Button) dialog.findViewById(R.id.share_button);
	  TextView titleText = (TextView) dialog.findViewById(R.id.title_text);
	  TextView text = (TextView) dialog.findViewById(R.id.text);
	  ImageView image = (ImageView) dialog.findViewById(R.id.icon);
	  RelativeLayout titleLayout = (RelativeLayout) dialog.findViewById(R.id.title_bar_layout);
	  RelativeLayout layoutRoot = (RelativeLayout) dialog.findViewById(R.id.layout_root);
	  
	  //shareButton.setBackgroundResource(R.drawable.arrow_share_button);
    
	  
	  
	  // Open database and initialize cursor
	  db = new DbAdapter(mContext);
	  db.open();
	  Cursor dialogInfo = db.getTitleTextImageAndTheme((int)Double.parseDouble(mOverlays.get(index).getTitle()));
	  dialogInfo.moveToFirst();
	  
	  // Set the title, body text, and theme icon for the dialog
	  titleText.setText(dialogInfo.getString(0));
	  text.setText(dialogInfo.getString(1));
	  image.setImageResource(dialogInfo.getInt(2));
	  
	  // Set color for the dialog box depending on theme
	  if (dialogInfo.getInt(3) == 1) {
		  titleLayout.setBackgroundColor(0xFFC8E0B0);
		  layoutRoot.setBackgroundColor(0xFFC8E0B0);
	  }
	  
	  else if (dialogInfo.getInt(3) == 2) {
		  titleLayout.setBackgroundColor(0xFFA8C0E0);
		  layoutRoot.setBackgroundColor(0xFFA8C0E0);
	  }
	  
	  else {
		  titleLayout.setBackgroundColor(0xFFFFC048);
		  layoutRoot.setBackgroundColor(0xFFFFC048);
	  }
	  
	  // Close cursor and database
	  dialogInfo.close();
	  db.close();
	  dialog.show();
	  
	  
	  return true;
	}
}

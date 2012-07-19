package umf_research.stewardship;

import java.util.List;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapScreen extends MapActivity{
	
	MapController mapController;
	DbAdapter db;
	CustomMapOverlay cmo;
	List<Overlay> mapOverlays;
	Dialog dialog;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);	
		MapView mapView = (MapView)findViewById(R.id.map);
		mapView.setBuiltInZoomControls(true);
		
		
		
		
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.detail_dialog);
		TextView text = (TextView) dialog.findViewById(R.id.text);
		ImageView image = (ImageView) dialog.findViewById(R.id.icon);
		
		db = new DbAdapter(this);
		
		mapController = mapView.getController();		
		mapController.setZoom(17);
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LOCATION_SERVICE)){
			Toast.makeText(getApplicationContext(),
 			       "GPS Disabled (LocationManager)",
 			       Toast.LENGTH_SHORT).show();
		}
		
		try {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationUpdateManager());
		} catch (Exception e) {
			//Toast.makeText(getApplicationContext(),"GPS Disabled (LocationManager)", Toast.LENGTH_LONG).show();
		}
		
		mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
		cmo = new CustomMapOverlay(drawable, this);
		
		Bundle extras = getIntent().getExtras();
		db.open();
		
		
		
		// Set title, text, and image for the dialog pop-up
		Cursor dialogInfo = db.getTitleTextAndTheme(1);
		dialogInfo.moveToFirst();
		dialog.setTitle(dialogInfo.getString(0));
		text.setText(dialogInfo.getString(1));
		image.setImageResource(dialogInfo.getInt(2));
		dialogInfo.close();
		
		
	    Cursor overlays = db.getLatAndLonAndTheme();
	    overlays.moveToFirst();
	    //Toast.makeText(getApplicationContext(),Integer.toString(extras.getInt("theme")), Toast.LENGTH_LONG).show();
	    for (int i = 0; i < overlays.getCount(); i++) {
	    	if (overlays.getInt(3) == extras.getInt("theme")) {
	    		//Toast.makeText(getApplicationContext(),overlays.getFloat(1) + ", " + overlays.getFloat(2), Toast.LENGTH_LONG).show();
	    		makeCustomOverlay(overlays.getFloat(1), overlays.getFloat(2), overlays.getFloat(3)+"", overlays.getFloat(1)+"'");
	    	}
	    	else {
	    		//Toast error?
	    	}
	    }
		
		
	    overlays.close();
		
		
		
		
		
		
		
		
		
	    db.close();
		
	}
	
	private class LocationUpdateManager implements LocationListener {
		@Override
	    public void onLocationChanged(Location location) {
		//Toast.makeText(getApplicationContext(),location.getLatitude()+", "+location.getLongitude(), Toast.LENGTH_LONG).show();
		double x = location.getLatitude()*1000000, y = location.getLongitude()*1000000;
		GeoPoint geoPoint = new GeoPoint((int)x, (int)y);
	    mapController.animateTo(geoPoint);
	    } 

	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {}
	    
	    @Override 
	    public void onProviderEnabled(String provider) {}
	    
	    @Override
	    public void onProviderDisabled(String provider) {
	        Toast.makeText(getApplicationContext(),"GPS Disabled (LocationUpdateManager)", Toast.LENGTH_LONG).show();
	    }
	  }
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public void makeCustomOverlay(float lat, float lon, String text1, String text2) {
		float x = lat*1000000, y = lon*1000000;
		GeoPoint gp = new GeoPoint((int)x, (int)y);
		OverlayItem overlayitem = new OverlayItem(gp, text1, text2);
		cmo.addOverlay(overlayitem);
		mapOverlays.add(cmo);
		return;
	}
}
package net.wigle.wigleandroid;

import java.util.Map;

import org.andnav.osm.util.GeoPoint;
import org.andnav.osm.views.OpenStreetMapView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;

/**
 * wrap the open street map view, to allow setting overlays
 */
public class OpenStreetMapViewWrapper extends OpenStreetMapView {
  
	private Paint trailPaint = new Paint();
	private Paint trailBackPaint = new Paint();
  
  /**
   * XML Constructor (uses default Renderer)
   */
  public OpenStreetMapViewWrapper(Context context, AttributeSet attrs) {
    super( context, attrs );
    int color = Color.argb( 200, 200, 128, 200 );
    trailPaint.setColor( color );
    color = Color.argb( 200, 224, 224, 224 );
    trailBackPaint.setColor( color );
  }

	@Override
  public void onDraw( Canvas c ) {
    super.onDraw( c );
    
		synchronized( WigleAndroid.lameStatic.trail ) {
    	for ( Map.Entry<GeoPoint,Integer> entry : WigleAndroid.lameStatic.trail.entrySet() ) {
				GeoPoint geoPoint = entry.getKey();
				int nets = entry.getValue();
				// WigleAndroid.info( "nets: " + nets + " point: " + geoPoint );
				if ( nets > 0 ) {
    	  	final Point point = this.getProjection().toMapPixels( geoPoint, null );
    	  	c.drawCircle(point.x, point.y, nets + 1, trailBackPaint);
				}
    	}
    	for ( Map.Entry<GeoPoint,Integer> entry : WigleAndroid.lameStatic.trail.entrySet() ) {
        GeoPoint geoPoint = entry.getKey();
        int nets = entry.getValue();
        // WigleAndroid.info( "nets: " + nets + " point: " + geoPoint );
        if ( nets > 0 ) {
          final Point point = this.getProjection().toMapPixels( geoPoint, null );
          c.drawCircle(point.x, point.y, nets, trailPaint);
        }
      }
		}
    
    // draw center crosshairs
    final GeoPoint center = this.getMapCenter();
    final Point centerPoint = this.getProjection().toMapPixels( center, null );
    c.drawLine( centerPoint.x, centerPoint.y - 9, centerPoint.x, centerPoint.y + 9, mPaint );
    c.drawLine( centerPoint.x - 9, centerPoint.y, centerPoint.x + 9, centerPoint.y, mPaint );
  }
}

package com.hereiam.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.TilesOverlay;
import org.osmdroid.ResourceProxy;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.controller.adapter.AlertDialogAdapter;
import com.hereiam.model.Environment;
import com.hereiam.model.Place;
import com.hereiam.model.User;
import com.hereiam.wsi.EnvironmentWSI;
import com.hereiam.wsi.PlaceWSI;
import com.hereiam.wsi.UserWSI;

public class MapViewController extends BaseActivity implements Runnable, OnClickListener, OnItemClickListener{
	
	private static final int ALERTROUTE = 1;
	private static final int ALERTENVIRONMENTS = 2;
	private static final int ALERTPLACES = 3;
	private static final int ALERTFAVORITES = 4;
	private static final int ALERTIMPORTANTS = 5;
	
	protected int selectedMenu;
	
	private Context context;
	private MapView mapView;
	private MapController mapController;
	private MapOverlay mapOverlay;
	private RoadManager roadManager;
	private Drawable nodeIcon;// = getResources().getDrawable(R.drawable.marker_node);
	private Marker nodeMarker;
		
	//	
	private EditText editTextSearch;
    private ListView listViewResults;
    private ArrayList<String> array_sort;
    private AlertDialog alertDialog;
    private AlertDialogAdapter alertDialogAdapter;
    private Builder alertDialogBuilder;
    private LinearLayout layout;
    private double latitude;
    private double longitude;
    private String currentEnvironment;
    private String currentPlace;
    private String nextPlace;
    private EnvironmentWSI environmentWSI;
	private ArrayList<Environment> environments;
	private PlaceWSI placeWSI;
	private ArrayList<Place> places;
	private ArrayList<String> listItens = new ArrayList<String>();
	private String placeA;
	private String placeB;
	private GeoPoint positionA;
	private GeoPoint positionB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);
        
        context = this;
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        mapView.setMinZoomLevel(16);
        mapView.setMaxZoomLevel(19);
        mapController = (MapController) mapView.getController();
        nodeMarker = new Marker(mapView);
        mapView.setMapListener(new DelayedMapListener(new MapListener(){  		    
        	@Override
        	public boolean onScroll(ScrollEvent e) {
		    	if (mapView.getZoomLevel()>18){
		    		mapView.getController().zoomOut();//getController().setZoom(18);		    		
		    	}		    	
		        return true;
		    }

			@Override
			public boolean onZoom(ZoomEvent arg0) {
				if(mapView.getZoomLevel() == 19){
					
					
				}
				return true;
			}}, 500 ));
        
        /*mapOverlay = new MapOverlay(context);        
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay); */                       
        
        if(getIntent().hasExtra("SHOWMAP")){        	             	
        	if(getIntent().getBooleanExtra("SHOWMAP", true)){
            	latitude = Double.parseDouble(getIntent().getStringExtra("LATITUDE"));
            	longitude = Double.parseDouble(getIntent().getStringExtra("LONGITUDE"));
        		setTo(latitude, longitude);
        		
        		if(getIntent().hasExtra("ENVIRONMENT")){
                	currentEnvironment = getIntent().getStringExtra("ENVIRONMENT");
                }
                
                if(getIntent().hasExtra("PLACE")){
                	nodeIcon = getResources().getDrawable(R.drawable.marker_node);
                	currentPlace = getIntent().getStringExtra("PLACE");                	
                    nodeMarker.setPosition(new GeoPoint(latitude, longitude));
                    nodeMarker.setIcon(nodeIcon);
                    nodeMarker.setTitle(currentPlace);
                    mapView.getOverlays().add(nodeMarker);
                }
            }
        }
        
        if(getIntent().hasExtra("ROUTE")){
        	if(getIntent().getBooleanExtra("ROUTE", true)){
        		placeA = getIntent().getStringExtra("PLACE_A");
        		placeB = getIntent().getStringExtra("PLACE_B");
        		positionA = new GeoPoint(Double.parseDouble(getIntent().getStringExtra("LATITUDE_A")), Double.parseDouble(getIntent().getStringExtra("LONGITUDE_A")));
            	positionB = new GeoPoint(Double.parseDouble(getIntent().getStringExtra("LATITUDE_B")), Double.parseDouble(getIntent().getStringExtra("LONGITUDE_B")));
            	mapController = (MapController) mapView.getController();
                mapController.setZoom(15);
                mapController.setCenter(positionA);
            	startProgressDialog("teste", "teste");
            	
            	new ShowRouteTask().execute(positionA, positionB);
            	//new Thread(this).start();
        	}
        }
        
        if(getIntent().hasExtra("NFC_WITHOUT_ROUTE")){
        	setToSelectedPlace(/*getIntent().getStringExtra("NFC_WITHOUT_ROUTE"));*/);            
        }

        mapView.invalidate();     
    }

	@Override
	public void run() {		
		try{
			GeoPoint position = new GeoPoint(-29.7962346, -51.1514861);        
	        GeoPoint position2 = new GeoPoint(-29.7949435, -51.1522033);
	        
	        
	        mapController.setZoom(17);
	        mapController.setCenter(position); 	       	        
	        roadManager = getMapQuestKey();
	        roadManager.addRequestOption("routeType=pedestrian");        
	        //roadManager.addRequestOption("routeType=fastest");
	        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
	        waypoints.add(positionA);
	        waypoints.add(positionB); //end point        
	        
	        Road road = roadManager.getRoad(waypoints);
	        
	        if (road.mStatus == Road.STATUS_DEFAULT){
	        	// Mensagem de sinal baixo aqui
	        } else if(road.mStatus == Road.STATUS_OK){
	        	
	        	Polyline roadOverlay = RoadManager.buildRoadOverlay(road, this);
	        	roadOverlay.setColor(Color.BLACK);
	        	roadOverlay.setWidth(8);
	            
	            mapView.getOverlays().add(roadOverlay);            
	            
	            Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
	            for (int i=0; i<road.mNodes.size(); i++){
                    RoadNode node = road.mNodes.get(i);
                    Marker nodeMarker = new Marker(mapView);
                    nodeMarker.setPosition(node.mLocation);
                    nodeMarker.setIcon(nodeIcon);
                    nodeMarker.setTitle("Step "+i);
                    
                    mapView.getOverlays().add(nodeMarker);
	            }
	            mapView.postInvalidate();
	        }else {		
	        	//
	        }
		} finally {
			finishProgressDialog();
        }
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }
	    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){	        	        	
	        case R.id.action_update:
	        	////////////////////chamar NFC, verificar se está roteando
	        	return true;
	        case R.id.action_route:
	        	createAlertDialog();
	        	startProgressDialog("teste", "message");
	        	selectedMenu = ALERTROUTE;
	            //new AlertDialogFeedTask().execute(ALERTROUTE);
	        	return true;
	        case R.id.action_listenvironments:
	        	createAlertDialog();
	            startProgressDialog(getString(R.string.progresst_environment_list), getString(R.string.progressm_environment_list));
	            selectedMenu = ALERTPLACES;
	            new SelectEnvironemntAlertDialogFeedTask().execute();	            																		          	           
	        	return true;
	        case R.id.action_listplaces:
	        	createAlertDialog();
	            startProgressDialog(getString(R.string.progresst_places_list), getString(R.string.progressm_places_list));
	            selectedMenu = ALERTPLACES;
	            new SelectPlaceAlertDialogFeedTask().execute();	            																		          	           
	        	return true;
	        case R.id.action_listfavorites:
	        	createAlertDialog();
	        	startProgressDialog(getString(R.string.progresst_favorites_list), getString(R.string.progressm_favorites_list));
	        	selectedMenu = ALERTFAVORITES;
	            //new AlertDialogFeedTask().execute(ALERTFAVORITES);
	        	return true;
	        case R.id.action_listimportants:
	        	createAlertDialog();
	        	startProgressDialog(getString(R.string.progresst_importants_list), getString(R.string.progressm_importants_list));
	        	selectedMenu = ALERTIMPORTANTS;
	            //new AlertDialogFeedTask().execute(ALERTIMPORTANTS);
	        	return true;
	        case R.id.action_logout:
        		clearPreferences();
        		startActivity(LoginAuthController.class);        		
        		return true;
	        default:
        		return super.onOptionsItemSelected(item);
        }	
    }
    
	@Override
	public void onItemClick(AdapterView av, View v, int position, long id) {		 
		
		alertDialog.dismiss();
		switch(getSelectedMenu()){
			case ALERTROUTE:
				// redireciona para o mapa novamente mais com a adição da ação selecionada
				break;
			case ALERTENVIRONMENTS:
				
				break;
			case ALERTPLACES:
				
				break;
			case ALERTFAVORITES:
				
				break;
			case ALERTIMPORTANTS:
				
				break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public RoadManager getMapQuestKey(){
		return new MapQuestRoadManager("Fmjtd%7Cluur2luan5%2Ca2%3Do5-901auz");
	}
    
    public void setTo(double latitude, double longitude){
    	GeoPoint position = new GeoPoint(latitude, longitude);
    	mapController = (MapController) mapView.getController();
        mapController.setZoom(15);
        mapController.setCenter(position);  
    }
    
    // pegar as coordenadas
    public void setToSelectedPlace(){
    	GeoPoint position = new GeoPoint(-29.6849347, -51.45922);
    	mapController = (MapController) mapView.getController();
        mapController.setZoom(15);
        mapController.setCenter(position);  
    }
    
    public int getSelectedMenu(){
    	return selectedMenu;
    }
    
    public void createAlertDialog(){
    	alertDialogBuilder = new AlertDialog.Builder(context);
    	editTextSearch = new EditText(context);
    	listViewResults = new ListView(context);
    	layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editTextSearch);
        layout.addView(listViewResults);
        listViewResults.setOnItemClickListener(this);
        alertDialogBuilder.setView(layout);	            
        alertDialogBuilder.setNegativeButton(getStringResource(R.string.cancel), new DialogInterface.OnClickListener() {	            	 
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
    
    public class MapOverlay extends org.osmdroid.views.overlay.Overlay {

        public MapOverlay(Context ctx) {super(ctx);}

        @Override
        protected void draw(Canvas c, MapView osmv, boolean shadow) { }

        @Override
        public boolean onTouchEvent(MotionEvent e, MapView mapView) {
        	int maskedAction = e.getActionMasked();
        	
        	if(mapView.getZoomLevel()>18){            	
        		if(e.getAction() == MotionEvent.ACTION_MOVE){
        			mapController.setZoom(18);                	
        		}
        	}
        return false;
        }
    }
    
    private class SelectEnvironemntAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			try{
				environmentWSI = new EnvironmentWSI();
				environments = environmentWSI.getListEnvironment();	
				listItens.clear();				
				for (int i = 0; i < environments.size(); i++) {
					listItens.add(environments.get(i).getEnvtName());
				}
				
				alertDialogAdapter = new AlertDialogAdapter(context, listItens);
			    listViewResults.setAdapter(alertDialogAdapter);
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
	        alertDialog = alertDialogBuilder.show(); 
        }
    }

    private class SelectPlaceAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

    	@Override
    	protected Void doInBackground(Void... arg0) {
			try{
				placeWSI = new PlaceWSI();
				places = placeWSI.getListPlace();	
				listItens.clear();				
				for (int i = 0; i < places.size(); i++) {
					listItens.add(places.get(i).getPlaceName());
				}
				
				alertDialogAdapter = new AlertDialogAdapter(context, listItens);
			    listViewResults.setAdapter(alertDialogAdapter);
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
	        alertDialog = alertDialogBuilder.show(); 
        }
    }

    private class ShowRouteTask extends AsyncTask<GeoPoint, Void, Void>{

    	@Override
    	protected Void doInBackground(GeoPoint... geopoints) {
            
            ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
            waypoints.add(geopoints[0]);
            waypoints.add(geopoints[1]);
            RoadManager roadManager = null;
           
            
            roadManager = new MapQuestRoadManager("Fmjtd%7Cluur2luan5%2Ca2%3Do5-901auz");
            
            roadManager.addRequestOption("routeType=pedestrian");
            
        	Road road = roadManager.getRoad(waypoints);
	        
	        if (road.mStatus == Road.STATUS_DEFAULT){
	        	// Mensagem de sinal baixo aqui
	        } else if(road.mStatus == Road.STATUS_OK){
	        	
	        	Polyline roadOverlay = RoadManager.buildRoadOverlay(road, context);
	        	roadOverlay.setColor(Color.BLACK);
	        	roadOverlay.setWidth(8);
	            
	            mapView.getOverlays().add(roadOverlay);            
	            
	            Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
	            for (int i=0; i<road.mNodes.size(); i++){
                    RoadNode node = road.mNodes.get(i);
                    Marker nodeMarker = new Marker(mapView);
                    nodeMarker.setPosition(node.mLocation);
                    nodeMarker.setIcon(nodeIcon);
                    nodeMarker.setTitle("Step "+i);
                    
                    mapView.getOverlays().add(nodeMarker);
	            }
	            mapView.postInvalidate();
	        }
			return null;
    }
    	
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
			mapView.invalidate(); 
        }
    }
}

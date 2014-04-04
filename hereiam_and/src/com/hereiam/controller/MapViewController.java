package com.hereiam.controller;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Marker.OnMarkerClickListener;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.controller.adapter.AlertDialogAdapter;
import com.hereiam.model.Environment;
import com.hereiam.model.FavoritePlace;
import com.hereiam.model.Place;
import com.hereiam.wsi.EnvironmentWSI;
import com.hereiam.wsi.FavoritePlaceWSI;
import com.hereiam.wsi.PlaceWSI;

public class MapViewController extends BaseActivity implements Runnable, OnClickListener, OnItemClickListener{
	
	private static final int ALERT_ROUTE_A = 1;
	private static final int ALERT_ROUTE_B = 2;
	private static final int ALERT_ENVIRONMENT = 3;
	private static final int ALERT_PLACE = 4;
	private static final int ALERT_FAVORITE = 5;
	private static final int ALERT_IMPORTANT = 6;
	
	protected int selectedMenu;
		
	private Context context;
	private int userId;
	private String userName;
	private MapView mapView;
	private MapController mapController;
	private RoadManager roadManager;
	private Drawable nodeIconP;
	private Drawable nodeIconE;
	private Marker nodeMarker;
	private Marker nodeMarkerRouteA;
	private Marker nodeMarkerRouteB;
	private String currentMarker;
	private Menu menu;
	private Intent navIntent;
	private ArrayList<Marker> nodeMarkerEnvironments = new ArrayList<Marker>();
	private MapOverlay mapOverlay;
	private boolean showMap = false;
	private boolean route = false;	
    private ListView listViewResults;    
    private AlertDialog alertDialog;
    private AlertDialogAdapter alertDialogAdapter;
    private Builder alertDialogBuilder;    
    private double latitude;
    private double longitude;    
    private String currentEnvironment;
    private String currentPlace;    
    private Environment environment;
    private EnvironmentWSI environmentWSI;
	private ArrayList<Environment> environments;
	private Place place;
	private PlaceWSI placeWSI;
	private ArrayList<Place> places;
	private ArrayList<String> listItens = new ArrayList<String>();	
	private ArrayList<String> routeA = new ArrayList<String>();
	private ArrayList<String> routeB = new ArrayList<String>();
	private FavoritePlaceWSI favoritePlaceWSI;
	private FavoritePlace favoritePlace;
	private ArrayList<String> currentActions = new ArrayList<String>();
	private String idNfc;
	private boolean nfc;
	private boolean nfcEnvironment;
	private boolean nfcPlace;
	private boolean updateNfc;
	private boolean routeNfc;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private JSONArray jsonArray;
    private JSONObject json;
    private String action;
	private Environment mapOverlayEnvironment;
	private Environment environmentExtra = null;
	private ArrayList<Environment> environmentsExtra = new ArrayList<Environment>();
	private Place mapOverlayPlace;
	private Place placeExtra;
	private ArrayList<Place> mapOverlayPlaces = new ArrayList<Place>();
	private ArrayList<Place> placesExtra = new ArrayList<Place>();
	
	private NfcManager nfcManager;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);
        
        context = this;
                
//        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        nfcAdapter.disableForegroundDispatch(this);
        
//        pendingIntent = PendingIntent.getActivity( this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        
        userId = getUserId();
        userName = getUserName();
        preferences = getApplicationContext().getSharedPreferences("STATE", MODE_PRIVATE);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        mapView.setMinZoomLevel(16);
        mapView.setMaxZoomLevel(19);
        //mapView.setMultiTouchControls(true);    
        mapController = (MapController) mapView.getController();
        
        nodeMarker = new Marker(mapView);
       
        mapOverlay = new MapOverlay(context);        
        mapView.getOverlays().add(mapOverlay);
        
        if(getIntent().hasExtra("NFC")){
        	nfc = getIntent().getBooleanExtra("NFC", true);
        	if(getIntent().hasExtra("UPDATE_ROUTE_NFC")){
        		routeNfc = getIntent().getBooleanExtra("UPDATE_ROUTE_NFC", true);
        	}
        	
        	if(getIntent().hasExtra("UPDATE_POSITION_NFC")){
        		updateNfc = getIntent().getBooleanExtra("UPDATE_POSITION_NFC", true);
        	}
        	
        	       		
    		idNfc = getIntent().getStringExtra("NFC_ID");
    		if(idNfc.split("-").length == 3){        		
        		nfcEnvironment = true;
        		nfcPlace = false;
        	}
        	
        	if(idNfc.split("-").length == 4){        		
        		nfcEnvironment = false;
        		nfcPlace = true;
        	}
    	}else {
    		nfc = false;
    	}
                
        try {
        	json = new JSONObject(preferences.getString("MAP_OVERLAY", ""));
        	
        	if(nfc){
        		if(nfcEnvironment){
        			if(routeNfc){
//        				alerta rota entre ambiente e local não é suportado
        				nfc = false;
        				nfcEnvironment = false;
        				nfcPlace = false;
        				updateNfc = false;
        				idNfc = "";
        			}
        		}
        	}
        	
        	if(!nfc){
	        	json = new JSONObject(preferences.getString("MAP_OVERLAY", ""));
	
	        	if(json.has("SHOWMAP")){
	        		showMap = json.getBoolean("SHOWMAP");
	        	}else{
	        		showMap = false;
	        	}
	        	       	        		        	       
		        if(showMap){
		        	action = json.getString("ACTION");
		        	if(action.equals("ENVIRONMENT")){
		        		mapOverlayEnvironment = new Environment();
		        		mapOverlayEnvironment.setEnvtId(json.getInt("ENVIRONMENT_ID"));
		        		mapOverlayEnvironment.setEnvtName(json.getString("ENVIRONMENT_NAME"));
		        		mapOverlayEnvironment.setEnvtInfo(json.getString("ENVIRONMENT_INFO"));
		        		mapOverlayEnvironment.setEnvtLatitude(json.getString("ENVIRONMENT_LATITUDE"));
		        		mapOverlayEnvironment.setEnvtLongitude(json.getString("ENVIRONMENT_LONGITUDE"));
		        		environmentExtra = mapOverlayEnvironment;
		        		setTo(Double.parseDouble(mapOverlayEnvironment.getEnvtLatitude()), 
		        				Double.parseDouble(mapOverlayEnvironment.getEnvtLongitude()));
		        	}
		        	
		        	if(action.equals("PLACE")){
		        		mapOverlayPlace = new Place();
		        		mapOverlayPlace.setPlaceId(json.getInt("PLACE_ID"));
		        		mapOverlayPlace.setPlaceName(json.getString("PLACE_NAME"));
		        		mapOverlayPlace.setPlaceInfo(json.getString("PLACE_INFO"));
		        		mapOverlayPlace.setPlaceLatitude(json.getString("PLACE_LATITUDE"));
		        		mapOverlayPlace.setPlaceLongitude(json.getString("PLACE_LONGITUDE"));
		        		placeExtra = mapOverlayPlace;
		        		
		        		setTo(Double.parseDouble(mapOverlayPlace.getPlaceLatitude()), 
		        				Double.parseDouble(mapOverlayPlace.getPlaceLongitude()));
		        	}
		        	
		        	if(action.equals("IMPORTANT")){
		        		mapOverlayPlace = new Place();
		        		mapOverlayPlace.setPlaceId(json.getInt("IMPORTANT_ID"));
		        		mapOverlayPlace.setPlaceName(json.getString("IMPORTANT_NAME"));
		        		mapOverlayPlace.setPlaceInfo(json.getString("IMPORTANT_INFO"));
		        		mapOverlayPlace.setPlaceLatitude(json.getString("IMPORTANT_LATITUDE"));
		        		mapOverlayPlace.setPlaceLongitude(json.getString("IMPORTANT_LONGITUDE"));
		        		placeExtra = mapOverlayPlace;
		        		
		        		setTo(Double.parseDouble(mapOverlayPlace.getPlaceLatitude()), 
		        				Double.parseDouble(mapOverlayPlace.getPlaceLongitude()));
		        	}
		        	
		        	if(action.equals("FAVORITE")){
		        		mapOverlayPlace = new Place();
		        		mapOverlayPlace.setPlaceId(json.getInt("FAVORITE_ID"));
		        		mapOverlayPlace.setPlaceName(json.getString("FAVORITE_NAME"));
		        		mapOverlayPlace.setPlaceInfo(json.getString("FAVORITE_INFO"));
		        		mapOverlayPlace.setPlaceLatitude(json.getString("FAVORITE_LATITUDE"));
		        		mapOverlayPlace.setPlaceLongitude(json.getString("FAVORITE_LONGITUDE"));
		        		placeExtra = mapOverlayPlace;
		        		
		        		setTo(Double.parseDouble(mapOverlayPlace.getPlaceLatitude()), 
		        				Double.parseDouble(mapOverlayPlace.getPlaceLongitude()));
		        	}
		        }
		        
		        if(json.has("ROUTE")){
	        		route = json.getBoolean("ROUTE");
	        		action = json.getString("ACTION");
	        		        		
	        		Place getJsonA = new Place();
	        		getJsonA.setPlaceId(json.getInt("PLACE_A_ID"));
	        		getJsonA.setPlaceName(json.getString("PLACE_A_NAME"));
	        		getJsonA.setPlaceInfo(json.getString("PLACE_A_INFO"));
	        		getJsonA.setPlaceLatitude(json.getString("PLACE_A_LATITUDE"));
	        		getJsonA.setPlaceLongitude(json.getString("PLACE_A_LONGITUDE"));
	        		
	        		mapOverlayPlaces.add(getJsonA);
	        		
	        		Place getJsonB = new Place();
	        		getJsonB.setPlaceId(json.getInt("PLACE_B_ID"));
	        		getJsonB.setPlaceName(json.getString("PLACE_B_NAME"));
	        		getJsonB.setPlaceInfo(json.getString("PLACE_B_INFO"));
	        		getJsonB.setPlaceLatitude(json.getString("PLACE_B_LATITUDE"));
	        		getJsonB.setPlaceLongitude(json.getString("PLACE_B_LONGITUDE"));
	        		
	        		mapOverlayPlaces.add(getJsonB);
	        		
	        		setTo(Double.parseDouble(getJsonA.getPlaceLatitude()), 
	        				Double.parseDouble(getJsonA.getPlaceLongitude()));	        		
	        	}else{
	        		route = false;
	        	}	        
        	}   
		
			mapView.invalidate();           
	        startProgressDialog(getString(R.string.progresst_loading_map), getString(R.string.progressm_loading_map));
	        new InitialFeedTask().execute();
		} catch (JSONException e) {			
			e.printStackTrace();
		}		        
    }	
	
	@Override
	public void run(){
	}
		
	@Override
	protected void onDestroy(){
		mapView.setBuiltInZoomControls(false);
		mapView.setClickable(false);		
		super.onDestroy();
	}
		
	@Override
	protected void onPause(){
		mapView.setBuiltInZoomControls(false);
		mapView.setClickable(false);		
		super.onPause();
	}
			
	@Override
	public void onBackPressed(){
		navIntent = new Intent(context, DashBoardController.class);
		startActivity(navIntent);
	}
	
	public void getEnvironments(){
		nodeIconE = getResources().getDrawable(R.drawable.marker_node_blue);
		environmentWSI = new EnvironmentWSI();
		environments = environmentWSI.getListEnvironment();	
						
		for (int i = 0; i < environments.size(); i++) {
			nodeMarkerEnvironments.add(new Marker(mapView));
			nodeMarkerEnvironments.get(i).setTitle(environments.get(i).getEnvtName());
			nodeMarkerEnvironments.get(i).setPosition(new GeoPoint(Double.parseDouble(environments.get(i).getEnvtLatitude()), 
					Double.parseDouble(environments.get(i).getEnvtLongitude())));
			nodeMarkerEnvironments.get(i).setIcon(nodeIconE);
			addListenerOnMarkerEnvironment(nodeMarkerEnvironments.get(i));
			mapView.getOverlays().add(nodeMarkerEnvironments.get(i));
			
			environmentsExtra.add(environments.get(i));
		}		
	}
		
	public void getRoute(GeoPoint positionA, GeoPoint positionB){
		ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(positionA);
        waypoints.add(positionB);
        
        RoadManager roadManager = null;                      
        roadManager = new MapQuestRoadManager("Fmjtd%7Cluur2luan5%2Ca2%3Do5-901auz");            
        roadManager.addRequestOption("routeType=pedestrian");
        
    	Road road = roadManager.getRoad(waypoints);
        
        if (road.mStatus == Road.STATUS_DEFAULT){
        	// Mensagem de sinal baixo aqui
        } else if(road.mStatus == Road.STATUS_OK){	        	
        	
        	route = true;
        	
        	Polyline roadOverlay = RoadManager.buildRoadOverlay(road, context);
        	roadOverlay.setColor(Color.BLACK);
        	roadOverlay.setWidth(6);
            
            mapView.getOverlays().add(roadOverlay);            
            
            Drawable nodeIconA = getResources().getDrawable(R.drawable.marker_node_a);
            RoadNode node = road.mNodes.get(0);
            nodeMarkerRouteA = new Marker(mapView);
            nodeMarkerRouteA.setPosition(node.mLocation);
            nodeMarkerRouteA.setIcon(nodeIconA);
            nodeMarkerRouteA.setTitle(mapOverlayPlaces.get(0).getPlaceName());
            addListenerOnMarkerPlace(nodeMarkerRouteA);                        
                        
            Drawable nodeIconB = getResources().getDrawable(R.drawable.marker_node_b);
            node = road.mNodes.get(1);
            nodeMarkerRouteB = new Marker(mapView);
            nodeMarkerRouteB.setPosition(node.mLocation);
            nodeMarkerRouteB.setIcon(nodeIconB);
            nodeMarkerRouteB.setTitle(mapOverlayPlaces.get(1).getPlaceName());
            addListenerOnMarkerPlace(nodeMarkerRouteB);                       
                        
            mapView.getOverlays().add(nodeMarkerRouteA);
            mapView.getOverlays().add(nodeMarkerRouteB);
            
            placesExtra.add(mapOverlayPlaces.get(0));
            placesExtra.add(mapOverlayPlaces.get(1));
        }
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
        this.menu = menu;
		getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }
	
	@Override

	public boolean onPrepareOptionsMenu(Menu menu) {

		if(route){
			MenuItem menuStopRoute = menu.findItem(R.id.action_route_stop);
			menuStopRoute.setVisible(true);
			MenuItem menuUpdateRoute = menu.findItem(R.id.action_route_update);
			menuUpdateRoute.setVisible(true);
		} else {
			MenuItem menuStopRoute = menu.findItem(R.id.action_route_stop);
			menuStopRoute.setVisible(false);
			MenuItem menuUpdateRoute = menu.findItem(R.id.action_route_update);
			menuUpdateRoute.setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){	        	        	
	        case R.id.action_update:
	        	navIntent = new Intent(context, NFCReaderController.class);	        				
	        	navIntent.putExtra("UPDATE_POSITION_NFC", true);
	        	startActivity(navIntent);
	        	return true;
	        case R.id.action_route:	        	
	        	startProgressDialog(getString(R.string.progresst_places_list), getString(R.string.progressm_places_list));
	        	createAlertDialog(ALERT_ROUTE_A);
	        	selectedMenu = ALERT_ROUTE_A;
	            new SelectPlaceRouteAAlertDialogFeedTask().execute();
	        	return true;
	        case R.id.action_list_environments:	        	
	            startProgressDialog(getString(R.string.progresst_environment_list), getString(R.string.progressm_environment_list));
	            createAlertDialog(ALERT_ENVIRONMENT);
	            selectedMenu = ALERT_ENVIRONMENT;
	            new SelectEnvironemntAlertDialogFeedTask().execute();	            																		          	           
	        	return true;
	        case R.id.action_list_places:	        	
	            startProgressDialog(getString(R.string.progresst_places_list), getString(R.string.progressm_places_list));
	            createAlertDialog(ALERT_PLACE);
	            selectedMenu = ALERT_PLACE;
	            new SelectPlaceAlertDialogFeedTask().execute();	            																		          	           
	        	return true;
	        case R.id.action_list_favorites:	        	
	        	startProgressDialog(getString(R.string.progresst_favorites_list), getString(R.string.progressm_favorites_list));
	        	createAlertDialog(ALERT_FAVORITE);
	        	selectedMenu = ALERT_FAVORITE;
	        	new SelectFavoritePlaceAlertDialogFeedTask().execute();
	        	return true;
	        case R.id.action_list_importants:	        	
	        	startProgressDialog(getString(R.string.progresst_importants_list), getString(R.string.progressm_importants_list));
	        	createAlertDialog(ALERT_IMPORTANT);
	        	selectedMenu = ALERT_IMPORTANT;
	            new SelectPlaceByImportanceAlertDialogFeedTask().execute();
	        	return true;
	        case R.id.action_info_environment:	        	
	        	navIntent = new Intent(context, ShowInfoController.class);
	        	
	        	if(environmentsExtra.size() > 0){
	        		for(int i = 0; i < environmentsExtra.size(); i++){
	        			if(environmentsExtra.get(i).getEnvtName().equals(currentMarker)){
	        				navIntent.putExtra("ENVIRONMENT", true);
	    	        		navIntent.putExtra("ENVIRONMENT_NAME", environmentsExtra.get(i).getEnvtName());
	    		        	navIntent.putExtra("ENVIRONMENT_INFO", environmentsExtra.get(i).getEnvtInfo());
	    		        	navIntent.putExtra("ENVIRONMENT_ID", environmentsExtra.get(i).getEnvtId());
	        			}
	        		}
	        	}else {
	        		navIntent.putExtra("ENVIRONMENT", true);
	        		navIntent.putExtra("ENVIRONMENT_NAME", environmentExtra.getEnvtName());
		        	navIntent.putExtra("ENVIRONMENT_INFO", environmentExtra.getEnvtInfo());
		        	navIntent.putExtra("ENVIROMENT_ID", environmentExtra.getEnvtId());
	        	}
	        	
	        	startActivity(navIntent);
	        	
	        	//startProgressDialog(getString(R.string.progresst_info_environment), getString(R.string.progressm_info_environment));
	        	//new GetEnvironmentInfoFeedTask().execute(currentMarker);
	        	return true;
	        case R.id.action_info_place:	        	
	        	navIntent = new Intent(context, ShowInfoController.class);
	        	
	        	if(route){
	        		for(int i = 0; i < placesExtra.size(); i++){
	        			if(placesExtra.get(i).getPlaceName().equals(currentMarker)){
	        				navIntent.putExtra("PLACE", true);
	    	        		navIntent.putExtra("PLACE_NAME", placesExtra.get(i).getPlaceName());
	    		        	navIntent.putExtra("PLACE_INFO", placesExtra.get(i).getPlaceInfo());
	    		        	navIntent.putExtra("PLACE_ID", placesExtra.get(i).getPlaceId());
	        			}
	        		}
	        	}else {
		        	navIntent.putExtra("PLACE", true);
	        		navIntent.putExtra("PLACE_NAME", placeExtra.getPlaceName());
		        	navIntent.putExtra("PLACE_INFO", placeExtra.getPlaceInfo());
		        	navIntent.putExtra("PLACE_ID", placeExtra.getPlaceId());
	        	}
	        		        	
	        	startProgressDialog(getString(R.string.progresst_info_place), getString(R.string.progressm_info_place));
	        	new GetPlaceInfoFeedTask().execute(currentMarker);
	        	return true;
	        case R.id.action_route_stop:
	        	route = false;	        	
	        	for(int i = 0; i < mapView.getOverlays().size(); i++){
	        		if(mapView.getOverlays().get(i).getClass().equals(Polyline.class)){
	        			mapView.getOverlays().remove(i);
	        		}
	        	}
	        		        	
	        	int i = 0;
	        	boolean removed = false;	        		        	
	        	while (i < mapView.getOverlays().size()) {
					removed = false;
					
					if(mapView.getOverlays().get(i).getClass().equals(Marker.class)){
						Marker removeMarker = (Marker) mapView.getOverlays().get(i);
						for(int x = 0; x < placesExtra.size(); x++){							
							if(removeMarker.getTitle().equals(placesExtra.get(x).getPlaceName())){
								mapView.getOverlays().remove(i);
								removed = true;
							}
							if(removed){
								x = placesExtra.size();
							}
						}						
					}
					if(!removed){
						i++;
					}					
				}
	        		        		
	        	mapView.invalidate();
	        	return true;
	        case R.id.action_route_update:
	        	navIntent = new Intent(context, NFCReaderController.class);	        				
	        	navIntent.putExtra("UPDATE_ROUTE_NFC", true);
	        	startActivity(navIntent);
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
	public void onItemClick(AdapterView av, View v, int position, long id){		 
		
		alertDialog.dismiss();
		switch(getSelectedMenu()){
			case ALERT_ROUTE_A:
				if(routeA.size() > 1) {
					routeA.clear();
				}
				
				routeA.add(listItens.get(position));
				routeA.add(places.get(position).getPlaceLatitude());
				routeA.add(places.get(position).getPlaceLongitude());
				
				editor = preferences.edit();
			    jsonArray = new JSONArray();
			    json = new JSONObject();
			    try {
					json.put("ROUTE", true);
					json.put("ACTION", "ROUTE");
					json.put("PLACE_A_ID", places.get(position).getPlaceId());
				    json.put("PLACE_A_NAME", places.get(position).getPlaceName());
				    json.put("PLACE_A_INFO", places.get(position).getPlaceInfo());			    
				    json.put("PLACE_A_LATITUDE", places.get(position).getPlaceLatitude());
				    json.put("PLACE_A_LONGITUDE", places.get(position).getPlaceLongitude());
				    jsonArray.put(json);
				} catch (JSONException e) {				
					e.printStackTrace();
				}
			    		    
			    editor.putString("MAP_OVERLAY", json.toString());
			    //editor.commit();	
				placesExtra.add(places.get(position));
				listItens.remove(position);
				places.remove(position);			
				alertDialog.dismiss();				
				
				selectedMenu = ALERT_ROUTE_B;
				createAlertDialog(ALERT_ROUTE_B);	
				break;
			case ALERT_ROUTE_B:
				if(routeB.size() > 1) {
					routeB.clear();
				}
				
				routeB.add(listItens.get(position));
				routeB.add(places.get(position).getPlaceLatitude());
				routeB.add(places.get(position).getPlaceLongitude());
				
				editor = preferences.edit();
			    jsonArray = new JSONArray();
			    //json = new JSONObject();
			    try {
					json.put("PLACE_B_ID", places.get(position).getPlaceId());
				    json.put("PLACE_B_NAME", places.get(position).getPlaceName());
				    json.put("PLACE_B_INFO", places.get(position).getPlaceInfo());			    
				    json.put("PLACE_B_LATITUDE", places.get(position).getPlaceLatitude());
				    json.put("PLACE_B_LONGITUDE", places.get(position).getPlaceLongitude());
				    jsonArray.put(json);
				} catch (JSONException e) {				
					e.printStackTrace();
				}
			    		    
			    editor.putString("MAP_OVERLAY", json.toString());
			    editor.commit();	
			    
			    route = true;
			    placesExtra.add(places.get(position));
				
				alertDialog.dismiss();

				GeoPoint positionA = new GeoPoint(Double.parseDouble(routeA.get(1)), Double.parseDouble(routeA.get(2)));
        		GeoPoint positionB = new GeoPoint(Double.parseDouble(routeB.get(1)), Double.parseDouble(routeB.get(2)));
				
				mapView.getOverlays().clear();			
				mapView.getOverlays().add(mapOverlay);
				
				startProgressDialog(getString(R.string.progresst_make_route), getString(R.string.progressm_make_route));
				new ShowRouteTask().execute(positionA, positionB);
				break;
			case ALERT_ENVIRONMENT:
				currentEnvironment = environments.get(position).getEnvtName();
				latitude = Double.parseDouble(environments.get(position).getEnvtLatitude());
            	longitude = Double.parseDouble(environments.get(position).getEnvtLongitude());            	
            	
            	environmentExtra = environments.get(position);
            	
            	mapView.getOverlays().clear();
            	
            	mapView.getOverlays().add(mapOverlay);
            	
            	for (int i = 0; i < environments.size(); i++) {
					nodeMarkerEnvironments.add(new Marker(mapView));
					nodeMarkerEnvironments.get(i).setTitle(environments.get(i).getEnvtName());
					nodeMarkerEnvironments.get(i).setPosition(new GeoPoint(Double.parseDouble(environments.get(i).getEnvtLatitude()), 
							Double.parseDouble(environments.get(i).getEnvtLongitude())));
					nodeMarkerEnvironments.get(i).setIcon(nodeIconE);
					addListenerOnMarkerEnvironment(nodeMarkerEnvironments.get(i));
					mapView.getOverlays().add(nodeMarkerEnvironments.get(i));
				}
            	
            	editor = preferences.edit();
    		    jsonArray = new JSONArray();
    		    json = new JSONObject();
    		    try {
    				json.put("SHOWMAP", true);
    				json.put("ACTION", "ENVIRONMENT");
    				json.put("ENVIRONMENT_ID", environments.get(position).getEnvtId());
    			    json.put("ENVIRONMENT_NAME", environments.get(position).getEnvtName());
    			    json.put("ENVIRONMENT_INFO", environments.get(position).getEnvtInfo());			    
    			    json.put("ENVIRONMENT_LATITUDE", environments.get(position).getEnvtLatitude());
    			    json.put("ENVIRONMENT_LONGITUDE", environments.get(position).getEnvtLongitude());
    			    jsonArray.put(json);
    			} catch (JSONException e) {				
    				e.printStackTrace();
    			}
    		    		    
    		    editor.putString("MAP_OVERLAY", json.toString());
    		    editor.commit();	
            	
    		    route = false;
    		    
            	setTo(latitude, longitude);
            	mapView.invalidate(); 
				break;
			case ALERT_PLACE:
				nodeIconP = getResources().getDrawable(R.drawable.marker_node);
            	currentPlace = places.get(position).getPlaceName();
				latitude = Double.parseDouble(places.get(position).getPlaceLatitude());
            	longitude = Double.parseDouble(places.get(position).getPlaceLongitude());
                nodeMarker.setPosition(new GeoPoint(latitude, longitude));
                nodeMarker.setIcon(nodeIconP);
                nodeMarker.setTitle(currentPlace);
                
                placeExtra = places.get(position);                
                
                mapView.getOverlays().clear();
                
                mapView.getOverlays().add(mapOverlay);
                                
                addListenerOnMarkerPlace(nodeMarker);
                mapView.getOverlays().add(nodeMarker);
                
                for (int i = 0; i < environments.size(); i++) {
					nodeMarkerEnvironments.add(new Marker(mapView));
					nodeMarkerEnvironments.get(i).setTitle(environments.get(i).getEnvtName());
					nodeMarkerEnvironments.get(i).setPosition(new GeoPoint(Double.parseDouble(environments.get(i).getEnvtLatitude()), 
							Double.parseDouble(environments.get(i).getEnvtLongitude())));
					nodeMarkerEnvironments.get(i).setIcon(nodeIconE);
					addListenerOnMarkerEnvironment(nodeMarkerEnvironments.get(i));
					mapView.getOverlays().add(nodeMarkerEnvironments.get(i));
				}
                
                editor = preferences.edit();
    		    jsonArray = new JSONArray();
    		    json = new JSONObject();
    		    try {
    				json.put("SHOWMAP", true);
    				json.put("ACTION", "PLACE");
    				json.put("PLACE_ID", places.get(position).getPlaceId());
    			    json.put("PLACE_NAME", places.get(position).getPlaceName());
    			    json.put("PLACE_INFO", places.get(position).getPlaceInfo());			    
    			    json.put("PLACE_LATITUDE", places.get(position).getPlaceLatitude());
    			    json.put("PLACE_LONGITUDE", places.get(position).getPlaceLongitude());
    			    jsonArray.put(json);
    			} catch (JSONException e) {				
    				e.printStackTrace();
    			}
    		    		    
    		    editor.putString("MAP_OVERLAY", json.toString());
    		    editor.commit();
    		    
    		    route = false;
                
                setTo(latitude, longitude);
                mapView.invalidate();
				break;
			case ALERT_FAVORITE:
				nodeIconP = getResources().getDrawable(R.drawable.marker_node_fav);
            	currentPlace = places.get(position).getPlaceName();
				latitude = Double.parseDouble(places.get(position).getPlaceLatitude());
            	longitude = Double.parseDouble(places.get(position).getPlaceLongitude());
                nodeMarker.setPosition(new GeoPoint(latitude, longitude));
                nodeMarker.setIcon(nodeIconP);
                nodeMarker.setTitle(currentPlace);
                
                placeExtra = places.get(position);
                
                mapView.getOverlays().clear();
                
                mapView.getOverlays().add(mapOverlay);
                                
                addListenerOnMarkerPlace(nodeMarker);
                mapView.getOverlays().add(nodeMarker);
                
				for (int i = 0; i < environments.size(); i++) {
					nodeMarkerEnvironments.add(new Marker(mapView));
					nodeMarkerEnvironments.get(i).setTitle(environments.get(i).getEnvtName());
					nodeMarkerEnvironments.get(i).setPosition(new GeoPoint(Double.parseDouble(environments.get(i).getEnvtLatitude()), 
							Double.parseDouble(environments.get(i).getEnvtLongitude())));
					nodeMarkerEnvironments.get(i).setIcon(nodeIconE);
					addListenerOnMarkerEnvironment(nodeMarkerEnvironments.get(i));
					mapView.getOverlays().add(nodeMarkerEnvironments.get(i));
				}
				
				editor = preferences.edit();
			    jsonArray = new JSONArray();
			    json = new JSONObject();
			    try {
					json.put("SHOWMAP", true);
					json.put("ACTION", "FAVORITE");
					json.put("FAVORITE_ID", places.get(position).getPlaceId());
				    json.put("FAVORITE_NAME", places.get(position).getPlaceName());
				    json.put("FAVORITE_INFO", places.get(position).getPlaceInfo());			    
				    json.put("FAVORITE_LATITUDE", places.get(position).getPlaceLatitude());
				    json.put("FAVORITE_LONGITUDE", places.get(position).getPlaceLongitude());
				    jsonArray.put(json);
				} catch (JSONException e) {				
					e.printStackTrace();
				}
			    		    
			    editor.putString("MAP_OVERLAY", json.toString());
			    editor.commit();
				
			    route = false;
			    
				setTo(latitude, longitude);
                mapView.invalidate();
				break;
			case ALERT_IMPORTANT:
				nodeIconP = getResources().getDrawable(R.drawable.marker_node_b);
            	currentPlace = places.get(position).getPlaceName();
				latitude = Double.parseDouble(places.get(position).getPlaceLatitude());
            	longitude = Double.parseDouble(places.get(position).getPlaceLongitude());
                nodeMarker.setPosition(new GeoPoint(latitude, longitude));
                nodeMarker.setIcon(nodeIconP);
                nodeMarker.setTitle(currentPlace);
                
                placeExtra = places.get(position);
                
                mapView.getOverlays().clear();
                
                mapView.getOverlays().add(mapOverlay);
                                
                addListenerOnMarkerPlace(nodeMarker);
                mapView.getOverlays().add(nodeMarker);
                
                for (int i = 0; i < environments.size(); i++) {
					nodeMarkerEnvironments.add(new Marker(mapView));
					nodeMarkerEnvironments.get(i).setTitle(environments.get(i).getEnvtName());
					nodeMarkerEnvironments.get(i).setPosition(new GeoPoint(Double.parseDouble(environments.get(i).getEnvtLatitude()), 
							Double.parseDouble(environments.get(i).getEnvtLongitude())));
					nodeMarkerEnvironments.get(i).setIcon(nodeIconE);
					addListenerOnMarkerEnvironment(nodeMarkerEnvironments.get(i));
					mapView.getOverlays().add(nodeMarkerEnvironments.get(i));
				}
                
                editor = preferences.edit();
    		    jsonArray = new JSONArray();
    		    json = new JSONObject();
    		    try {
    				json.put("SHOWMAP", true);
    				json.put("ACTION", "IMPORTANT");
    				json.put("IMPORTANT_ID", places.get(position).getPlaceId());
    			    json.put("IMPORTANT_NAME", places.get(position).getPlaceName());
    			    json.put("IMPORTANT_INFO", places.get(position).getPlaceInfo());
    			    json.put("IMPORTANT_LATITUDE", places.get(position).getPlaceLatitude());
    			    json.put("IMPORTANT_LONGITUDE", places.get(position).getPlaceLongitude());
    			    jsonArray.put(json);
    			} catch (JSONException e) {				
    				e.printStackTrace();
    			}
    		    		    
    		    editor.putString("MAP_OVERLAY", json.toString());
    		    editor.commit();	
    			
    		    route = false;
                
                setTo(latitude, longitude);
                mapView.invalidate();
				break;
		}
	}

	
    @Override
	public void onClick(View v) {			
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
       
    public void addListenerOnMarkerPlace(Marker marker){
    	marker.setOnMarkerClickListener(new OnMarkerClickListener() {
            
			@Override
			public boolean onMarkerClick(final Marker arg0, final MapView arg1) {								
				//arg0.showInfoWindow();
				MenuItem menuPlace = menu.findItem(R.id.action_info_place);
				menuPlace.setTitle("INFO - " + arg0.getTitle());
				currentMarker = arg0.getTitle();
				menuPlace.setVisible(true);
				return false;
			}
        });
    }
    
    public void addListenerOnMarkerEnvironment(Marker marker){
    	marker.setOnMarkerClickListener(new OnMarkerClickListener() {
            
			@Override
			public boolean onMarkerClick(Marker arg0, MapView arg1) {
				//arg0.showInfoWindow();
				MenuItem menuEnvironment = menu.findItem(R.id.action_info_environment);
				menuEnvironment.setTitle("INFO - " + arg0.getTitle());
				currentMarker = arg0.getTitle();
				menuEnvironment.setVisible(true);
				return false;
			}
        });
    }
    
    public int getSelectedMenu(){
    	return selectedMenu;
    }
    
    public void createAlertDialog(int type){
    	alertDialogBuilder = new AlertDialog.Builder(context);    	
    	listViewResults = new ListView(context);
    	LinearLayout layout = new LinearLayout(context);    	
        layout.setOrientation(LinearLayout.VERTICAL);        
        layout.addView(listViewResults);   
        listViewResults.setOnItemClickListener(this);
        switch (type) {
		case ALERT_ENVIRONMENT:
	        alertDialogBuilder.setTitle(R.string.alertt_environment_list);
			break;
		case ALERT_PLACE:        
	        alertDialogBuilder.setTitle(R.string.alertt_place_list);
			break;
		case ALERT_ROUTE_A:		
	        alertDialogBuilder.setTitle(R.string.alertt_route_A_list);			
			break;
		case ALERT_ROUTE_B:			
	        alertDialogBuilder.setTitle(R.string.alertt_route_B_list);
	        new SelectPlaceRouteBAlertDialogFeedTask().execute();
	        break;
		case ALERT_FAVORITE:
			alertDialogBuilder.setTitle(R.string.alertt_favorites_list);			
			break;
		case ALERT_IMPORTANT:
			alertDialogBuilder.setTitle(R.string.alertt_importants_list);
			break;
		default:
			break;
		}
        alertDialogBuilder.setView(layout);	            
        alertDialogBuilder.setNegativeButton(getStringResource(R.string.cancel), new DialogInterface.OnClickListener() {	            	 
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
    
    public class MapOverlay extends org.osmdroid.views.overlay.Overlay {

        public MapOverlay(Context ctx) {
        	super(ctx);
        }

        @Override
        protected void draw(Canvas c, MapView osmv, boolean shadow) {
        	
        }

        @Override
        public boolean onTouchEvent(MotionEvent e, MapView mapView) {
        	
        	if(e.getAction() == MotionEvent.ACTION_DOWN){
        		MenuItem menuPlace = menu.findItem(R.id.action_info_place);
        		MenuItem menuEnvironment = menu.findItem(R.id.action_info_environment);
        		
        		if(nodeMarker != null){
        			if(nodeMarker.isInfoWindowShown()){
        				nodeMarker.hideInfoWindow();
        			}
        		}
        		
        		if(nodeMarkerRouteA != null){
        			if(nodeMarkerRouteA.isInfoWindowShown()){
        				nodeMarkerRouteA.hideInfoWindow();
        			}
        		}
        		
        		if(nodeMarkerRouteB != null){
        			if(nodeMarkerRouteB.isInfoWindowShown()){
        				nodeMarkerRouteB.hideInfoWindow();
        			}
        		}
        		
        		if(nodeMarkerEnvironments.size() > 0){
        			for (int i = 0; i < nodeMarkerEnvironments.size(); i++){
        				if(nodeMarkerEnvironments.get(i).isInfoWindowShown()){
        					nodeMarkerEnvironments.get(i).hideInfoWindow();
        				}
        			}
        		}
        		
        		if(menuPlace.isVisible()){
        			menuPlace.setVisible(false);
        		}
        		
        		if(menuEnvironment.isVisible()){
        			menuEnvironment.setVisible(false);
        		}
        	}
        	        	
        	if(mapView.getZoomLevel() > 18){   
        		BoundingBoxE6 boundingBox = new BoundingBoxE6(0, 0, 0, 0);
        		mapView.setScrollableAreaLimit(boundingBox);
        	}
        return false;
        }           
    }
    
    private class InitialFeedTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
					        
	        if(showMap){        	             	
	        	getEnvironments();            	        		
	        	                
                if(action.equals("PLACE")){
                	nodeIconP = getResources().getDrawable(R.drawable.marker_node);
                	currentPlace = mapOverlayPlace.getPlaceName();
                    nodeMarker.setPosition(new GeoPoint(Double.parseDouble(mapOverlayPlace.getPlaceLatitude()), 
	        				Double.parseDouble(mapOverlayPlace.getPlaceLongitude())));
                    nodeMarker.setIcon(nodeIconP);
                    nodeMarker.setTitle(currentPlace);  
                    addListenerOnMarkerPlace(nodeMarker);
                    mapView.getOverlays().add(nodeMarker);
                }
	                
                if(action.equals("IMPORTANT")){
                	nodeIconP = getResources().getDrawable(R.drawable.marker_node_b);
                	currentPlace = mapOverlayPlace.getPlaceName();
     
                    nodeMarker.setPosition(new GeoPoint(Double.parseDouble(mapOverlayPlace.getPlaceLatitude()), 
	        				Double.parseDouble(mapOverlayPlace.getPlaceLongitude())));
                    nodeMarker.setIcon(nodeIconP);
                    nodeMarker.setTitle(currentPlace);    
                    addListenerOnMarkerPlace(nodeMarker);
                    mapView.getOverlays().add(nodeMarker);
                }	
	                
                if(action.equals("FAVORITE")){
                	nodeIconP = getResources().getDrawable(R.drawable.marker_node_fav);
                	currentPlace = mapOverlayPlace.getPlaceName();
                	nodeMarker.setPosition(new GeoPoint(Double.parseDouble(mapOverlayPlace.getPlaceLatitude()), 
	        				Double.parseDouble(mapOverlayPlace.getPlaceLongitude())));
                    nodeMarker.setIcon(nodeIconP);
                    nodeMarker.setTitle(currentPlace);    
                    addListenerOnMarkerPlace(nodeMarker);
                    mapView.getOverlays().add(nodeMarker);
                }
    	        finishProgressDialog();
	        }
	        
	        if(route){
	        	getEnvironments();
        		
        		GeoPoint positionA = new GeoPoint(Double.parseDouble(mapOverlayPlaces.get(0).getPlaceLatitude()), 
        				Double.parseDouble(mapOverlayPlaces.get(0).getPlaceLongitude()));
        		GeoPoint positionB = new GeoPoint(Double.parseDouble(mapOverlayPlaces.get(1).getPlaceLatitude()), 
        				Double.parseDouble(mapOverlayPlaces.get(1).getPlaceLongitude()));
            	
            	getRoute(positionA, positionB);	                	        	
	        }
	        
	        if(nfc){
	        	if(!routeNfc){
	        	
		        	getEnvironments();
		        	if(nfcEnvironment){
		        		environmentWSI = new EnvironmentWSI();
		        		environment = environmentWSI.getEnvironmentByNfc(idNfc);
		        		
		        		editor = preferences.edit();
		    		    jsonArray = new JSONArray();
		    		    json = new JSONObject();
		    		    try {
		    				json.put("SHOWMAP", true);
		    				json.put("ACTION", "ENVIRONMENT");
		    				json.put("ENVIRONMENT_ID", environment.getEnvtId());
		    			    json.put("ENVIRONMENT_NAME", environment.getEnvtName());
		    			    json.put("ENVIRONMENT_INFO", environment.getEnvtInfo());
		    			    json.put("ENVIRONMENT_LATITUDE", environment.getEnvtLatitude());
		    			    json.put("ENVIRONMENT_LONGITUDE", environment.getEnvtLongitude());
		    			    jsonArray.put(json);
		    			} catch (JSONException e) {				
		    				e.printStackTrace();
		    			}
		    		    		    
		    		    editor.putString("MAP_OVERLAY", json.toString());
		    		    editor.commit();
		        		
		        		nodeIconP = getResources().getDrawable(R.drawable.marker_node_b);
		            	currentEnvironment = environment.getEnvtName();
		            	latitude = Double.parseDouble(environment.getEnvtLatitude());
		            	longitude = Double.parseDouble(environment.getEnvtLongitude());
		                nodeMarker.setPosition(new GeoPoint(latitude, longitude));
		                nodeMarker.setIcon(nodeIconP);
		                nodeMarker.setTitle(currentEnvironment); 
		                addListenerOnMarkerPlace(nodeMarker);
		                mapView.getOverlays().add(nodeMarker);	                
		        	}
	        	
		        	if(nfcPlace){
		        		placeWSI = new PlaceWSI();
		        		place = placeWSI.getPlaceByNfc(idNfc);	        		
		        		
		        		placeExtra = place;
		        		
		        		editor = preferences.edit();
		    		    jsonArray = new JSONArray();
		    		    json = new JSONObject();
		    		    try {
		    				json.put("SHOWMAP", true);
		    				json.put("ACTION", "PLACE");
		    				json.put("PLACE_ID", place.getPlaceId());
		    			    json.put("PLACE_NAME", place.getPlaceName());
		    			    json.put("PLACE_INFO", place.getPlaceInfo());
		    			    json.put("PLACE_LATITUDE", place.getPlaceLatitude());
		    			    json.put("PLACE_LONGITUDE", place.getPlaceLongitude());
		    			    jsonArray.put(json);
		    			} catch (JSONException e) {				
		    				e.printStackTrace();
		    			}
		    		    		    
		    		    editor.putString("MAP_OVERLAY", json.toString());
		    		    editor.commit();
		        		
		        		
			        	nodeIconP = getResources().getDrawable(R.drawable.marker_node);
		            	currentPlace = place.getPlaceName();
		            	latitude = Double.parseDouble(place.getPlaceLatitude());
		            	longitude = Double.parseDouble(place.getPlaceLongitude());
		                nodeMarker.setPosition(new GeoPoint(latitude, longitude));
		                nodeMarker.setIcon(nodeIconP);
		                nodeMarker.setTitle(currentPlace);  
		                addListenerOnMarkerPlace(nodeMarker);
		                mapView.getOverlays().add(nodeMarker);		                
		        	}
	        	}else{
	        		placeWSI = new PlaceWSI();
	        		Place placeA = placeWSI.getPlaceByNfc(idNfc);	        		
	        			        			        			        			        		        
	        		try {
	        			mapOverlayPlaces.clear();
	        			mapOverlayPlaces.add(placeA);
		        		
		        		Place getJsonB = new Place();
		        		getJsonB.setPlaceId(json.getInt("PLACE_B_ID"));
		        		getJsonB.setPlaceName(json.getString("PLACE_B_NAME"));
		        		getJsonB.setPlaceInfo(json.getString("PLACE_B_INFO"));
		        		getJsonB.setPlaceLatitude(json.getString("PLACE_B_LATITUDE"));
		        		getJsonB.setPlaceLongitude(json.getString("PLACE_B_LONGITUDE"));
		        		
		        		mapOverlayPlaces.add(getJsonB);
		        		
		        		editor = preferences.edit();
					    
					    JSONObject jsonRouteNfc = new JSONObject();
					    try {
					    	jsonRouteNfc.put("ROUTE", true);
					    	jsonRouteNfc.put("ACTION", "ROUTE");
					    	jsonRouteNfc.put("PLACE_A_ID", mapOverlayPlaces.get(0).getPlaceId());
					    	jsonRouteNfc.put("PLACE_A_NAME", mapOverlayPlaces.get(0).getPlaceName());
					    	jsonRouteNfc.put("PLACE_A_INFO", mapOverlayPlaces.get(0).getPlaceInfo());			    
					    	jsonRouteNfc.put("PLACE_A_LATITUDE", mapOverlayPlaces.get(0).getPlaceLatitude());
					    	jsonRouteNfc.put("PLACE_A_LONGITUDE", mapOverlayPlaces.get(0).getPlaceLongitude());
						    
					    	jsonRouteNfc.put("PLACE_B_ID", mapOverlayPlaces.get(1).getPlaceId());
					    	jsonRouteNfc.put("PLACE_B_NAME", mapOverlayPlaces.get(1).getPlaceName());
					    	jsonRouteNfc.put("PLACE_B_INFO", mapOverlayPlaces.get(1).getPlaceInfo());			    
					    	jsonRouteNfc.put("PLACE_B_LATITUDE", mapOverlayPlaces.get(1).getPlaceLatitude());
					    	jsonRouteNfc.put("PLACE_B_LONGITUDE", mapOverlayPlaces.get(1).getPlaceLongitude());
						    
						} catch (JSONException e) {				
							e.printStackTrace();
						}
					    		    
					    editor.putString("MAP_OVERLAY", jsonRouteNfc.toString());
					    editor.commit();	
		        		
	        		} catch (JSONException e) {
						
						e.printStackTrace();
					}
	        		setTo(Double.parseDouble(placeA.getPlaceLatitude()), 
	        				Double.parseDouble(placeA.getPlaceLongitude()));
	        			        		
	        		getEnvironments();
	        		
	        		GeoPoint positionA = new GeoPoint(Double.parseDouble(mapOverlayPlaces.get(0).getPlaceLatitude()), 
	        				Double.parseDouble(mapOverlayPlaces.get(0).getPlaceLongitude()));
	        		GeoPoint positionB = new GeoPoint(Double.parseDouble(mapOverlayPlaces.get(1).getPlaceLatitude()), 
	        				Double.parseDouble(mapOverlayPlaces.get(1).getPlaceLongitude()));
	            	
	            	getRoute(positionA, positionB);	  
	        	}
	        }
	        
	        /*if(getIntent().hasExtra("NFC_WITHOUT_ROUTE")){
	        	            
	        }*/
	        
			return null;
		}
		
	@Override
	protected void onPostExecute(Void res){
		
		if(nfcEnvironment){
			setTo(Double.parseDouble(environment.getEnvtLatitude()), Double.parseDouble(environment.getEnvtLongitude()));
		}
		if(nfcPlace){
			setTo(Double.parseDouble(place.getPlaceLatitude()), Double.parseDouble(place.getPlaceLongitude()));
		}
		mapView.postInvalidate();
		finishProgressDialog();	        
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
            
    		route = true;
    		
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
	            
	            Drawable nodeIconA = getResources().getDrawable(R.drawable.marker_node_a);
	            RoadNode node = road.mNodes.get(0);
	            nodeMarkerRouteA = new Marker(mapView);
	            nodeMarkerRouteA.setPosition(node.mLocation);
                nodeMarkerRouteA.setIcon(nodeIconA);
                nodeMarkerRouteA.setTitle(routeA.get(0).toString());
                addListenerOnMarkerPlace(nodeMarkerRouteA);
                
                Drawable nodeIconB = getResources().getDrawable(R.drawable.marker_node_b);
                node = road.mNodes.get(1);
	            nodeMarkerRouteB = new Marker(mapView);
	            nodeMarkerRouteB.setPosition(node.mLocation);
                nodeMarkerRouteB.setIcon(nodeIconB);
                nodeMarkerRouteB.setTitle(routeB.get(0).toString());
                addListenerOnMarkerPlace(nodeMarkerRouteB);
                
                mapView.getOverlays().add(nodeMarkerRouteA);
                mapView.getOverlays().add(nodeMarkerRouteB);
                
                for (int i = 0; i < environments.size(); i++) {
					nodeMarkerEnvironments.add(new Marker(mapView));
					nodeMarkerEnvironments.get(i).setTitle(environments.get(i).getEnvtName());
					nodeMarkerEnvironments.get(i).setPosition(new GeoPoint(Double.parseDouble(environments.get(i).getEnvtLatitude()), 
							Double.parseDouble(environments.get(i).getEnvtLongitude())));
					nodeMarkerEnvironments.get(i).setIcon(nodeIconE);
					addListenerOnMarkerEnvironment(nodeMarkerEnvironments.get(i));
					mapView.getOverlays().add(nodeMarkerEnvironments.get(i));
				}                                                            	
	        }
			return null;
    }
    	
		@Override
        protected void onPostExecute(Void res){								
			mapView.invalidate(); 
			finishProgressDialog();			
        }
    }
    
    private class SelectPlaceRouteAAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

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
	
    private class SelectPlaceRouteBAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			try{			
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
    
    private class SelectPlaceByImportanceAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

    	@Override
    	protected Void doInBackground(Void... arg0) {
			try{
				placeWSI = new PlaceWSI();
				places = placeWSI.getListPlaceByImportance();	
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
    
    private class SelectFavoritePlaceAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

    	@Override
    	protected Void doInBackground(Void... arg0) {
			try{
				favoritePlaceWSI = new FavoritePlaceWSI();
				places = favoritePlaceWSI.getListFavoritePlace(userId);	
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
    
    private class GetEnvironmentInfoFeedTask extends AsyncTask<String, Void, Void>{

    	@Override
    	protected Void doInBackground(String... environmentName) {
			try{
				environmentWSI = new EnvironmentWSI();
				environment = environmentWSI.getEnvironment(encodeUrl(environmentName[0]));	
				//navIntent.putExtra("INFO", environment.getEnvtInfo());	
				//navIntent.putExtra("ENVIRONMENT_ID", environment.getEnvtId());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
	        startActivity(navIntent);
        }
    }
    
    private class GetPlaceInfoFeedTask extends AsyncTask<String, Void, Void>{

    	@Override
    	protected Void doInBackground(String... placeName) {
//			try{
				//placeWSI = new PlaceWSI();
				//place = placeWSI.getPlace(encodeUrl(placeName[0]));	
				//navIntent.putExtra("INFO", place.getPlaceInfo());	
				//navIntent.putExtra("PLACE_ID", place.getPlaceId());
				
				favoritePlaceWSI = new FavoritePlaceWSI();
				favoritePlace = favoritePlaceWSI.findFavoritePlace(userId, navIntent.getIntExtra("PLACE_ID", 0));
				if(favoritePlace.getFpId() != 0){
					navIntent.putExtra("IS_FAVORITE", true);
				}
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}finally {
				
//			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
	        startActivity(navIntent);
        }
    }
}
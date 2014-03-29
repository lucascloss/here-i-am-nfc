package com.hereiam.controller;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.hereiam.model.Place;
import com.hereiam.wsi.EnvironmentWSI;
import com.hereiam.wsi.PlaceWSI;

public class MapViewController extends BaseActivity implements Runnable, OnClickListener, OnItemClickListener{
	
	private static final int ALERT_ROUTE_A = 1;
	private static final int ALERT_ROUTE_B = 2;
	private static final int ALERT_ENVIRONMENTS = 3;
	private static final int ALERT_PLACES = 4;
	private static final int ALERT_FAVORITES = 5;
	private static final int ALERT_IMPORTANTS = 6;
	
	protected int selectedMenu;
		
	private Context context;
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
        //mapView.setMultiTouchControls(true);
              
        mapController = (MapController) mapView.getController();
        
        nodeMarker = new Marker(mapView);
        //addListenerOnMarkerPlace(nodeMarker);
        
        /*mapView.setMapListener(new DelayedMapListener(new MapListener(){  		    
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
			}}, 500 ));*/
        
        mapOverlay = new MapOverlay(context);        
        mapView.getOverlays().add(mapOverlay);                        
        
        if(getIntent().hasExtra("SHOWMAP")){      
        	showMap = getIntent().getBooleanExtra("SHOWMAP", true);
        	getIntent().removeExtra("SHOWMAP");
        	if(showMap){            	        		
        		if(getIntent().hasExtra("ENVIRONMENT")){
    				latitude = Double.parseDouble(getIntent().getStringExtra("LATITUDE_ENVIRONMENT"));
                	longitude = Double.parseDouble(getIntent().getStringExtra("LONGITUDE_ENVIRONMENT"));
            		setTo(latitude, longitude);
        		}
        
        		if(getIntent().hasExtra("PLACE")){
    				latitude = Double.parseDouble(getIntent().getStringExtra("LATITUDE_PLACE"));
        			longitude = Double.parseDouble(getIntent().getStringExtra("LONGITUDE_PLACE"));
        			setTo(latitude, longitude);
        		}
        		
        		if(getIntent().hasExtra("IMPORTANTS")){
    				latitude = Double.parseDouble(getIntent().getStringExtra("LATITUDE_IMPORTANTS"));
        			longitude = Double.parseDouble(getIntent().getStringExtra("LONGITUDE_IMPORTANTS"));
        			setTo(latitude, longitude);
        		}
        	}
        }
        
        if(getIntent().hasExtra("ROUTE")){
        	route = getIntent().getBooleanExtra("ROUTE", true);
        	getIntent().removeExtra("ROUTE");
        	if(route){
        		latitude = Double.parseDouble(getIntent().getStringExtra("LATITUDE_A"));
    			longitude = Double.parseDouble(getIntent().getStringExtra("LONGITUDE_A"));
    			setTo(latitude, longitude);
        	}
        }
                
        mapView.invalidate();   
        startProgressDialog(getString(R.string.progresst_loading_map), getString(R.string.progressm_loading_map));
        new InitialFeedTask().execute();
    }	

	@Override
	public void run() {		
	}
	
	@Override
	protected void onDestroy(){
		mapView.setBuiltInZoomControls(false);
		mapView.setClickable(false);
		
		super.onDestroy();
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
            
            /*for (int i = 0; i < environments.size(); i++) {
				nodeMarkerEnvironments.add(new Marker(mapView));
				nodeMarkerEnvironments.get(i).setTitle(environments.get(i).getEnvtName());
				nodeMarkerEnvironments.get(i).setPosition(new GeoPoint(Double.parseDouble(environments.get(i).getEnvtLatitude()), 
						Double.parseDouble(environments.get(i).getEnvtLongitude())));
				nodeMarkerEnvironments.get(i).setIcon(nodeIconE);
				addListenerOnMarkerEnvironment(nodeMarkerEnvironments.get(i));
				mapView.getOverlays().add(nodeMarkerEnvironments.get(i));
			}*/ 
        }
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
		getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }
	    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){	        	        	
	        case R.id.action_update:
	        	//////////////////// chamar NFC, verificar se est� roteando
	        	/// atualizar rota
	        	return true;
	        case R.id.action_route:	        	
	        	startProgressDialog(getString(R.string.progresst_places_list), getString(R.string.progressm_places_list));
	        	createAlertDialog(ALERT_ROUTE_A);
	        	selectedMenu = ALERT_ROUTE_A;
	            new SelectPlaceRouteAAlertDialogFeedTask().execute();
	        	return true;
	        case R.id.action_list_environments:	        	
	            startProgressDialog(getString(R.string.progresst_environment_list), getString(R.string.progressm_environment_list));
	            createAlertDialog(ALERT_ENVIRONMENTS);
	            selectedMenu = ALERT_ENVIRONMENTS;
	            new SelectEnvironemntAlertDialogFeedTask().execute();	            																		          	           
	        	return true;
	        case R.id.action_list_places:	        	
	            startProgressDialog(getString(R.string.progresst_places_list), getString(R.string.progressm_places_list));
	            createAlertDialog(ALERT_PLACES);
	            selectedMenu = ALERT_PLACES;
	            new SelectPlaceAlertDialogFeedTask().execute();	            																		          	           
	        	return true;
	        case R.id.action_list_favorites:	        	
	        	startProgressDialog(getString(R.string.progresst_favorites_list), getString(R.string.progressm_favorites_list));
	        	createAlertDialog(ALERT_FAVORITES);
	        	selectedMenu = ALERT_FAVORITES;
	            //new AlertDialogFeedTask().execute(ALERTFAVORITES);
	        	return true;
	        case R.id.action_list_importants:	        	
	        	startProgressDialog(getString(R.string.progresst_importants_list), getString(R.string.progressm_importants_list));
	        	createAlertDialog(ALERT_IMPORTANTS);
	        	selectedMenu = ALERT_IMPORTANTS;
	            new SelectPlaceByImportanceAlertDialogFeedTask().execute();
	        	return true;
	        case R.id.action_info_environment:	        	
	        	navIntent = new Intent(context, ShowInfoController.class);
	        	navIntent.putExtra("ENVIRONMENT", currentMarker);
	        	startProgressDialog(getString(R.string.progresst_info_environment), getString(R.string.progressm_info_environment));
	        	new GetEnvironmentInfoFeedTask().execute(currentMarker);
	        	return true;
	        case R.id.action_info_place:	        	
	        	navIntent = new Intent(context, ShowInfoController.class);
	        	navIntent.putExtra("PLACE", currentMarker);
	        	startProgressDialog(getString(R.string.progresst_info_place), getString(R.string.progressm_info_place));
	        	new GetPlaceInfoFeedTask().execute(currentMarker);
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
			case ALERT_ROUTE_A:
				if(routeA.size() > 1) {
					routeA.clear();
				}
				
				routeA.add(listItens.get(position));
				routeA.add(places.get(position).getPlaceLatitude());
				routeA.add(places.get(position).getPlaceLongitude());
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
				alertDialog.dismiss();

				GeoPoint positionA = new GeoPoint(Double.parseDouble(routeA.get(1)), Double.parseDouble(routeA.get(2)));
        		GeoPoint positionB = new GeoPoint(Double.parseDouble(routeB.get(1)), Double.parseDouble(routeB.get(2)));
				
				mapView.getOverlays().clear();			
				mapView.getOverlays().add(mapOverlay);
				
				startProgressDialog(getString(R.string.progresst_make_route), getString(R.string.progressm_make_route));
				new ShowRouteTask().execute(positionA, positionB);
				break;
			case ALERT_ENVIRONMENTS:
				currentEnvironment = environments.get(position).getEnvtName();
				latitude = Double.parseDouble(environments.get(position).getEnvtLatitude());
            	longitude = Double.parseDouble(environments.get(position).getEnvtLongitude());
            	setTo(latitude, longitude);
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
            	
            	mapView.invalidate(); 
				break;
			case ALERT_PLACES:
				nodeIconP = getResources().getDrawable(R.drawable.marker_node);
            	currentPlace = places.get(position).getPlaceName();
				latitude = Double.parseDouble(places.get(position).getPlaceLatitude());
            	longitude = Double.parseDouble(places.get(position).getPlaceLongitude());
                nodeMarker.setPosition(new GeoPoint(latitude, longitude));
                nodeMarker.setIcon(nodeIconP);
                nodeMarker.setTitle(currentPlace);
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
                
                setTo(latitude, longitude);
                mapView.invalidate();
				break;
			case ALERT_FAVORITES:
				
				for (int i = 0; i < environments.size(); i++) {
					nodeMarkerEnvironments.add(new Marker(mapView));
					nodeMarkerEnvironments.get(i).setTitle(environments.get(i).getEnvtName());
					nodeMarkerEnvironments.get(i).setPosition(new GeoPoint(Double.parseDouble(environments.get(i).getEnvtLatitude()), 
							Double.parseDouble(environments.get(i).getEnvtLongitude())));
					nodeMarkerEnvironments.get(i).setIcon(nodeIconE);
					addListenerOnMarkerEnvironment(nodeMarkerEnvironments.get(i));
					mapView.getOverlays().add(nodeMarkerEnvironments.get(i));
				}
				
				
				break;
			case ALERT_IMPORTANTS:
				nodeIconP = getResources().getDrawable(R.drawable.marker_node);
            	currentPlace = places.get(position).getPlaceName();
				latitude = Double.parseDouble(places.get(position).getPlaceLatitude());
            	longitude = Double.parseDouble(places.get(position).getPlaceLongitude());
                nodeMarker.setPosition(new GeoPoint(latitude, longitude));
                nodeMarker.setIcon(nodeIconP);
                nodeMarker.setTitle(currentPlace);
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
		case ALERT_ENVIRONMENTS:
	        alertDialogBuilder.setTitle(R.string.alertt_environment_list);
			break;
		case ALERT_PLACES:        
	        alertDialogBuilder.setTitle(R.string.alertt_place_list);
			break;
		case ALERT_ROUTE_A:		
	        alertDialogBuilder.setTitle(R.string.alertt_route_A_list);			
			break;
		case ALERT_ROUTE_B:			
	        alertDialogBuilder.setTitle(R.string.alertt_route_B_list);
	        new SelectPlaceRouteBAlertDialogFeedTask().execute();
		case ALERT_FAVORITES:
			
			break;
		case ALERT_IMPORTANTS:
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
        	}
        return false;
        }
        
    }
    
    private class InitialFeedTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			//getEnvironments();
			        
	        if(showMap){        	             	
	        	getEnvironments();            	        		
	        		if(getIntent().hasExtra("ENVIRONMENT")){
	                	currentEnvironment = getIntent().getStringExtra("ENVIRONMENT");
	                	latitude = Double.parseDouble(getIntent().getStringExtra("LATITUDE_ENVIRONMENT"));
	                	longitude = Double.parseDouble(getIntent().getStringExtra("LONGITUDE_ENVIRONMENT"));
	                }
	                
	                if(getIntent().hasExtra("PLACE")){
	                	nodeIconP = getResources().getDrawable(R.drawable.marker_node);
	                	currentPlace = getIntent().getStringExtra("PLACE");
	                	latitude = Double.parseDouble(getIntent().getStringExtra("LATITUDE_PLACE"));
	                	longitude = Double.parseDouble(getIntent().getStringExtra("LONGITUDE_PLACE"));
	                    nodeMarker.setPosition(new GeoPoint(latitude, longitude));
	                    nodeMarker.setIcon(nodeIconP);
	                    nodeMarker.setTitle(currentPlace);  
	                    addListenerOnMarkerPlace(nodeMarker);
	                    mapView.getOverlays().add(nodeMarker);
	                }
	                
	                if(getIntent().hasExtra("IMPORTANTS")){
	                	nodeIconP = getResources().getDrawable(R.drawable.marker_node);
	                	currentPlace = getIntent().getStringExtra("IMPORTANTS");
	                	latitude = Double.parseDouble(getIntent().getStringExtra("LATITUDE_IMPORTANTS"));
	                	longitude = Double.parseDouble(getIntent().getStringExtra("LONGITUDE_IMPORTANTS"));
	                    nodeMarker.setPosition(new GeoPoint(latitude, longitude));
	                    nodeMarker.setIcon(nodeIconP);
	                    nodeMarker.setTitle(currentPlace);    
	                    addListenerOnMarkerPlace(nodeMarker);
	                    mapView.getOverlays().add(nodeMarker);
	                }	
    	        finishProgressDialog();
	        }
	        
	        if(route){
	        	getEnvironments();
        		routeA.add(getIntent().getStringExtra("PLACE_A"));
        		routeB.add(getIntent().getStringExtra("PLACE_B"));

        		routeA.add(getIntent().getStringExtra("LATITUDE_A"));
        		routeB.add(getIntent().getStringExtra("LATITUDE_B"));

        		routeA.add(getIntent().getStringExtra("LONGITUDE_A"));
        		routeB.add(getIntent().getStringExtra("LONGITUDE_B"));
        		
        		GeoPoint positionA = new GeoPoint(Double.parseDouble(routeA.get(1)), Double.parseDouble(routeA.get(2)));
        		GeoPoint positionB = new GeoPoint(Double.parseDouble(routeB.get(1)), Double.parseDouble(routeB.get(2)));
            	
            	getRoute(positionA, positionB);	                	        	
	        }
	        
	        if(getIntent().hasExtra("NFC_WITHOUT_ROUTE")){
	        	            
	        }	        
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			showMap = false;
			route = false;
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
    
    private class GetEnvironmentsTask extends AsyncTask<Void, Void, Void>{

    	@Override
    	protected Void doInBackground(Void... arg0) {
			try{
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
				}								
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){			
			mapView.invalidate();	
			//finishProgressDialog();
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
	            //mapView.postInvalidate();
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
    
    private class GetEnvironmentInfoFeedTask extends AsyncTask<String, Void, Void>{

    	@Override
    	protected Void doInBackground(String... environmentName) {
			try{
				environmentWSI = new EnvironmentWSI();
				environment = environmentWSI.getEnvironment(encodeUrl(environmentName[0]));	
				navIntent.putExtra("INFO", environment.getEnvtInfo());														
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
			try{
				placeWSI = new PlaceWSI();
				place = placeWSI.getPlace(encodeUrl(placeName[0]));	
				navIntent.putExtra("INFO", place.getPlaceInfo());														
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
}

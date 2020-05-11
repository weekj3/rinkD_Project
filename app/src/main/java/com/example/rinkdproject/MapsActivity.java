package com.example.rinkdproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import noman.googleplaces.Place;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener{

    private static final int REQUEST_CODE_PERMISSIONS = 1000;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    Marker mMarker;
    private EditText editPlace;
    private boolean boundaryIsOn = false;


    List<Marker>previous_marker=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        editPlace = findViewById(R.id.editPlace);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    //OnMapReadyCallback 인터페이스의 onMapReady 메소드 구현
    //맵이 사용할 준비가 되었을 때 호출되어짐
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(37.2664398, 126.9994077);
        mMap.addMarker(new MarkerOptions().position(sydney).title("it's me!!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //moveCamera 메소드를 사용해 카메라를 지정한 경도,위도로 이동 시킴
        //지정된 마커를 클릭하면 title을 이용해 it'me! 띄움

        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onLastLocationButtonClicked(View view) {
        //gps버튼을 눌렀을 때, 권한이 획득되었는지
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS);
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(myLocation).title("현재 위치").snippet(location.getLatitude() + "/" + location.getLongitude()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                }
                else{
//                    Toast.makeText(this,"권한 체크 거부됨",Toast.LENGTH_SHORT).show();
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            //만일 사용자가 요청을 거부하였다면, 토스트 메시지를 띄움
            case REQUEST_CODE_PERMISSIONS:
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"권한 체크 거부됨",Toast.LENGTH_SHORT).show();
                }
        }

    }


    public void searchButtonClicked(View view) {
        String place = editPlace.getText().toString();
        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> list = null;
        try{
            list = coder.getFromLocationName(place,1);
        } catch(IOException e){
            e.printStackTrace();
        }
        Address addr = list.get(0);
        double lat = addr.getLatitude();
        double lng = addr.getLongitude();
        LatLng geoPoint = new LatLng(lat,lng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(geoPoint, 15));

        MarkerOptions marker = new MarkerOptions();
        marker.position(geoPoint);
        marker.title(place).snippet(lat + "/" + lng);
        mMap.addMarker(marker);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        return true;
    }

    static int N = 0;
    static Point[] POINTS = new Point[20];

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void jugementClicked(View view) {
        //클릭할때마다 초기화
        POINTS = new Point[20];
        N = 0;
        //나의 현재 위치 받아오기
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS);
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    POINTS[N++] = new Point(location.getLatitude() * 10000, location.getLongitude()*10000);
                }
                else{
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                }
            }
        });

        Point MY_POINTS = POINTS[0];

        Arrays.sort(POINTS,0 , N, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                if (a.y != b.y) {
                    if(a.y < b.y){
                        return -1;
                    }
                    else{
                        return 1;
                    }
                }
                if(a.x < b.x)
                    return -1;
                return 1;
            }
        });

        for (int i = 1; i < N; i++) {
            POINTS[i].p = POINTS[i].x - POINTS[0].x;
            POINTS[i].q = POINTS[i].y - POINTS[0].y;
        }

        Arrays.sort(POINTS,1 , N-1, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                if(a.q*b.p != a.p*b.q){
                    if(a.q*b.p < a.p*b.q)
                        return -1;
                    else
                        return 1;
                }
                if (a.y != b.y) {
                    if(a.y < b.y){
                        return -1;
                    }
                    else{
                        return 1;
                    }
                }
                if(a.x < b.x)
                    return -1;
                return 1;
            }
        });

        Stack<Integer> stack = new Stack<>();
        stack.add(0);
        stack.add(1);

        for (int i = 2; i < N; i++) {
            while(stack.size() >= 2){
                int first = stack.pop();
                int second = stack.peek();

                long ccw = find_ccw(POINTS[first], POINTS[second], POINTS[i]);
                if (ccw > 0) {
                    stack.add(first);
                    break;
                }
            }
            stack.add(i);
        }


        boolean isInside = true;
        for(int i=0;i<stack.size();i++){
            if(POINTS[stack.get(i)].x == MY_POINTS.x && POINTS[stack.get(i)].y == MY_POINTS.y){
                isInside = false;
            }
        }

    }

    protected static long find_dist(Point a, Point b) {

        return (long)(a.x - b.x) * (a.x - b.x) + (long)(a.y - b.y) * (a.y - b.y);

    }

    protected static long find_ccw(Point a, Point b, Point c) {

        return (long)(b.x - a.x) * (long)(c.y - a.y) - (long)(c.x - a.x) * (long)(b.y - a.y);
    }


    static class Point {
        long x, y;
        //기준점으로부터의 상대 위치
        long p,q;

        public Point(double x, double y) {
            this.x = (long) x;
            this.y = (long) y;
            p=1;
            q=0;
        }

        public Point(double x, double y, long p, long q){
            this.x = (long) x;
            this.y = (long) y;
            this.p=p;
            this.q=q;
        }

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
            p=1;
            q=0;
        }
    }

}

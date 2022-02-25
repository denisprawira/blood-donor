package com.example.denisprawira.ta.User.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.View;
import androidx.core.view.GravityCompat;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.RetrofitHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Api.RetrofitServices;
import com.example.denisprawira.ta.User.Model.RetrofitResult;
import com.example.denisprawira.ta.User.Model.ServerResponse;
import com.example.denisprawira.ta.User.Model.User;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.Fragment.Joined.EventFragment;
import com.example.denisprawira.ta.User.UI.Fragment.Main.MainChatFragment;
import com.example.denisprawira.ta.User.UI.Fragment.Main.MainHomeFragment;
import com.example.denisprawira.ta.User.UI.Fragment.Main.MainNotificationFragment;
import com.example.denisprawira.ta.User.UI.Fragment.Main.MainProfileFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener {

    //first
    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;

    //first
    private static final int MY_PERMISSION_REQUEST_CODE = 7000;
    private static final int PLAY_SERVICE_RES_REQUEST = 7001;

    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;

    Fragment selectedFragment=null;
    Fragment mainEventFragment;

    TextView header_nama,header_email;
    ImageView header_image;
    BottomNavigationView bottomNavigationView ;
    String url ="http://"+GlobalHelper.url+"/donor_darah/index.php/api/user/loadUserData";
    String email, password ;
    SessionManager sessionManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    View header;
    RetrofitServices retrofitServices  = RetrofitHelper.getRetrofit(GlobalHelper.baseUrl).create(RetrofitServices.class);
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        sessionManager = new SessionManager(HomeActivity.this);
        int commit = getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MainHomeFragment()).commit();
        mainEventFragment = new Fragment();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header=navigationView.getHeaderView(0);
        header_image = header.findViewById(R.id.header_image);
        header_nama  = header.findViewById(R.id.header_nama);
        header_email = header.findViewById(R.id.header_email);

        loadUserData(email);

//        if(sessionManager.getLogStatus()){
//            Glide.with(HomeActivity.this).load(sessionManager.getPhoto()).into(header_image);
//            header_nama.setText(sessionManager.getName());
//            header_email.setText(sessionManager.getEmail());
//            updateToken(sessionManager.getChatId());
//        }else{
//            if(getIntent().getExtras()!=null){
//                email       = getIntent().getExtras().getString("email");
//                loadUserData(email);
//            }
//        }



        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        selectedFragment = new MainHomeFragment();
                        break;
                    case R.id.event:

                        selectedFragment = new EventFragment();
                        displayLocation();
                        break;
                    case R.id.chat:
                        Toast.makeText(HomeActivity.this, "chat", Toast.LENGTH_SHORT).show();
                        selectedFragment = new MainChatFragment();

                        break;
                    case R.id.notification:
                        Toast.makeText(HomeActivity.this, "notification", Toast.LENGTH_SHORT).show();
                        selectedFragment = new MainNotificationFragment();
                        break;
                    case R.id.settings:
                        Toast.makeText(HomeActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        selectedFragment = new MainProfileFragment();
                        break;
                }

                if(selectedFragment!=null){
                    Toast.makeText(HomeActivity.this, "fragment not null", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container,selectedFragment).commit();

                }
                return true;
            }
        });
        setUpLocation();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Keluar Dari Aplikasi")
                    .setMessage("Apaka anda ingin keluar dari aplikasi?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ya, Keluar..", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }})
                    .setNegativeButton("Tidak", null).show();

        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "id: "+sessionManager.getId()+"\n"+"name: "+sessionManager.getName()+"\n"+"phone: "+sessionManager.getPhone()+"\n", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "golDarah: "+sessionManager.getBlood()+"\n"+"gender: "+sessionManager.getGender()+"\n"+"email: "+sessionManager.getEmail()+"\n", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "photo: "+sessionManager.getPhoto(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, "token: "+sessionManager.getToken()+"\n"+"chatId: "+sessionManager.getChatId()+"\n", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_manage) {
            sessionManager.clearSession();
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void loadUserData(final String email){

    }

    public void stopLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    public void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            final double lat = lastLocation.getLatitude();
            final double lng = lastLocation.getLongitude();

            GlobalHelper.lat  = String.valueOf(lat);
            GlobalHelper.lng  = String.valueOf(lng);
            updateLocation(sessionManager.getId(), GlobalHelper.lat,GlobalHelper.lng);
        }

    }



    public void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);


    }




    @Override
    public void onConnected(Bundle bundle) {
        displayLocation();
        startLocationUpdate();

    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        displayLocation();
    }

    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}
                    , MY_PERMISSION_REQUEST_CODE);

        }else{
            if(checkPlayServices()){
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }
    }


    private void createLocationRequest(){
        locationRequest =  new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void buildGoogleApiClient(){
        googleApiClient =  new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode!=ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICE_RES_REQUEST).show();
            }else{
                Toast.makeText(this,"this device is not supported",Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;

        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_PERMISSION_REQUEST_CODE){
            if(checkPlayServices()){
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }
    }

    private void updateLocation(String idUser, String lat, String lng) {
        retrofitServices.updateLocation(idUser,lat,lng).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                /// Toast.makeText(HomeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateToken(final String chatId){

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( HomeActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                sessionManager.setToken(newToken);
                updateTokenFireStore(newToken,chatId);
                retrofitServices.updateToken(sessionManager.getId(),newToken).enqueue(new Callback<List<RetrofitResult>>() {
                    @Override
                    public void onResponse(Call<List<RetrofitResult>> call, retrofit2.Response<List<RetrofitResult>> response) {

                    }
                    @Override
                    public void onFailure(Call<List<RetrofitResult>> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void updateTokenFireStore(String token,String chatId){
        CollectionReference user = db.collection("Users");
        user.document(chatId).update("userToken",token).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }



        public void  loadUser(){
        CollectionReference users = db.collection("Users");
        users.whereEqualTo("userId",sessionManager.getId()).whereEqualTo("userStatus","user").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    sessionManager.setChatId(documentSnapshot.get("userChatId").toString());
                    Toast.makeText(HomeActivity.this, "chatId: "+sessionManager.getChatId(), Toast.LENGTH_SHORT).show();
                    updateToken(sessionManager.getChatId());               }
            }
        });
    }




}

package com.example.denisprawira.ta.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import android.os.IBinder;
import android.util.Log;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.denisprawira.ta.Api.ApiUtils;
import com.example.denisprawira.ta.Api.Model.UserFirestore;
import com.example.denisprawira.ta.Api.UserService;
import com.example.denisprawira.ta.Broadcast.LocationReceiver;
import com.example.denisprawira.ta.Interface.HomeActivityListener;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.Observer.LocationObserver;
import com.example.denisprawira.ta.Services.UserLocationServices;
import com.example.denisprawira.ta.SessionManager.UserSessionManager;
import com.example.denisprawira.ta.UI.Fragment.Main.MainChatFragment;
import com.example.denisprawira.ta.UI.Fragment.Main.MainEventFragment;
import com.example.denisprawira.ta.UI.Fragment.Main.MainHomeFragment;
import com.example.denisprawira.ta.UI.Fragment.Main.MainNotificationFragment;
import com.example.denisprawira.ta.UI.Fragment.Main.MainProfileFragment;
import com.example.denisprawira.ta.Values.GlobalValues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import io.getstream.chat.android.client.ChatClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,Observer,HomeActivityListener {


    //OBJECT
    //--permission
    private ActivityResultContracts.RequestMultiplePermissions requestMultiplePermissions;
    private ActivityResultLauncher<String[]> activityResultLauncher;

    //--location
    UserLocationServices localService = null;
    LocationReceiver locationReceiver;
    LocationObserver locationObserver;
    Location location = null;

    //--api
    UserService userService = ApiUtils.getUserService();
    UserSessionManager userSessionManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    //PRIMITIV DATA AND CONST
    private static final int MY_PERMISSION_REQUEST_CODE = 7000;
    private static final int PLAY_SERVICE_RES_REQUEST = 7001;
    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;
    private boolean mBound = false;

    final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    //FRAGMENT
    Fragment selectedFragment = null;
    Fragment mainEventFragment;

    //VIEW COMPONENT
    TextView header_nama, header_email;
    ImageView header_image;
    BottomNavigationView bottomNavigationView;
    View header;

    String TAG = "skripsibangke";



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getFirebaseToken();
        userSessionManager = new UserSessionManager(HomeActivity.this);

        int commit = getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MainHomeFragment()).commit();
        mainEventFragment = new Fragment();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);
        header_image = header.findViewById(R.id.header_image);
        header_nama = header.findViewById(R.id.header_nama);
        header_email = header.findViewById(R.id.header_email);

        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    selectedFragment = new MainHomeFragment();
                    break;
                case R.id.event:
                    selectedFragment = new MainEventFragment();
//                        displayLocation();
                    break;
                case R.id.chat:
                    selectedFragment = new MainChatFragment();

                    break;
                case R.id.notification:
                    Toast.makeText(this, userSessionManager.getIdFirebase(), Toast.LENGTH_SHORT).show();
                    selectedFragment = new MainNotificationFragment();
                    break;
                case R.id.settings:
                    Toast.makeText(HomeActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    selectedFragment = new MainProfileFragment();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if(selectedFragment!=null){
                transaction.replace(R.id.fragment_container,selectedFragment).commit();
            }
            return true;
        });

        locationReceiver = new LocationReceiver();
        locationObserver = new LocationObserver();

        checkSession();
        checkpermission();
        locationObserver.getInstance().addObserver(this);
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            UserLocationServices.LocalBinder binder = (UserLocationServices.LocalBinder) iBinder;
            localService = binder.getService();
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            localService = null;
            mBound = false;
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkpermission(){
        requestMultiplePermissions = new ActivityResultContracts.RequestMultiplePermissions();
        activityResultLauncher = registerForActivityResult(requestMultiplePermissions, result -> {
            Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
            Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
            Boolean writeExternalStorage = result.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);
            Boolean readExternalStorage = result.getOrDefault(Manifest.permission.READ_EXTERNAL_STORAGE, false);
            Log.e("permission",""+fineLocationGranted+coarseLocationGranted+writeExternalStorage+readExternalStorage);
        });
        activityResultLauncher.launch(PERMISSIONS);
    }

    public void checkDataExist(UserSessionManager userSessionManager){
        CollectionReference user = db.collection("Users");
        user.whereEqualTo("userId",userSessionManager.getId()).whereEqualTo("userStatus","user").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    //Toast.makeText(HomeActivity.this, "test", Toast.LENGTH_SHORT).show();
                    if(queryDocumentSnapshots.getDocuments().size()!=0){
                        userSessionManager.setIdFirebase(queryDocumentSnapshots.getDocuments().get(0).getId());
                        //Toast.makeText(HomeActivity.this, queryDocumentSnapshots.getDocuments().get(0).getId(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(HomeActivity.this, queryDocumentSnapshots.getDocuments().get(0).get("userName").toString(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(HomeActivity.this, "document empty", Toast.LENGTH_SHORT).show();
                    addUser(userSessionManager);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addUser(UserSessionManager userSessionManager){
        final CollectionReference notifs = db.collection("Users");
        UserFirestore userFirestore  = new UserFirestore();
        userFirestore.setUserId(userSessionManager.getId());
        userFirestore.setUserName(userSessionManager.getName());
        userFirestore.setUserPhoto(userSessionManager.getImg());
        userFirestore.setUserStatus("user");
        notifs.add(userFirestore).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    DocumentReference docRef = task.getResult();
                    String key = docRef.getId();
                    userSessionManager.setIdFirebase(key);
                    notifs.document(key).update("userUID",key).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.e("insert to firebase","berhasil gan");
                        }
                    });
                }
            }
        });
    }




    //###############################//
    //        OVERRIDE FUNCTION      //
    //###############################//

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, UserLocationServices.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkpermission();
        }else{
            if(localService!=null){
                Log.e("location","update location services Home activity");
                localService.requestLocationsUpdates();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UserLocationServices.ACTION_NAME);
        LocalBroadcastManager.getInstance(this).registerReceiver(locationReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(locationReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
        mBound = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void update(Observable observable, Object o) {
        Intent intent = (Intent) o;
        location = intent.getParcelableExtra(UserLocationServices.INTENT_NAME);
        Log.e("location: ",String.valueOf(location.getLatitude()));
        Log.e("location: ",String.valueOf(location.getLongitude()));
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
            userSessionManager.setTokenFirebase("this is token bla");
            Log.e("firebase token: ",userSessionManager.getTokenFirebase());
        } else if (id == R.id.nav_slideshow) {
            Log.e("id : ",userSessionManager.getId());
            Log.e("name : ",userSessionManager.getName());
            Log.e("email : ",userSessionManager.getEmail());
            Log.e("phone : ",userSessionManager.getPhone());
            Log.e("address : ",userSessionManager.getAddress());
            Log.e("blood Id : ",userSessionManager.getBloodId());
            Log.e("blood Type Name : ",userSessionManager.getBloodTypeName());
            Log.e("img : ",userSessionManager.getImg());
            Log.e("status : ",userSessionManager.getStatus());
            Log.e("role : ",userSessionManager.getRole());
            Log.e("token",userSessionManager.getTokenApi());

        } else if (id == R.id.nav_manage) {
            ChatClient chatClient = ChatClient.instance();
            chatClient.disconnect();
            userSessionManager.clearSession();
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //###############################//
    //        CUSTOMIZE FUNCTION     //
    //###############################//

    private void checkSession() {
        if(userSessionManager.getLogStatus()){
//            Glide.with(HomeActivity.this).load(sessionManager.getPhoto()).into(header_image);
//            header_nama.setText(sessionManager.getName());
//            header_email.setText(sessionManager.getEmail());
//            updateToken(sessionManager.getChatId());
            checkDataExist(userSessionManager);
        }else{
            if(getIntent().getExtras()!=null){
                String currentUserEmail = Objects.requireNonNull(getIntent().getExtras()).getString("email");
                String currentUserToken = Objects.requireNonNull(getIntent().getExtras()).getString("token");
                showCurrentUser(currentUserEmail,currentUserToken);
            }
        }
    }

    public void getFirebaseToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Log.e("result","failed to registered token");
                }
                String token = task.getResult();
                updateFirebaseToken(userSessionManager.getId(),token);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("result",e.getMessage());
            }
        });
    }

    public void updateFirebaseToken(String id, String token){
        userService.updateFirebaseToken(id,token).enqueue(new Callback<com.example.denisprawira.ta.Api.Model.Response>() {
            @Override
            public void onResponse(Call<com.example.denisprawira.ta.Api.Model.Response> call, Response<com.example.denisprawira.ta.Api.Model.Response> response) {
                Toast.makeText(HomeActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("update firebase token",response.body().getMessage());
            }

            @Override
            public void onFailure(Call<com.example.denisprawira.ta.Api.Model.Response> call, Throwable t) {
                Log.e("result",t.getMessage());
            }
        });
    }

    public void showCurrentUser(String email, String token){
        GlobalValues.setTokenApi(token);
        userService.showCurrentUser(GlobalValues.getTokenApi(),email).enqueue(new Callback<com.example.denisprawira.ta.Api.Model.User>() {
            @Override
            public void onResponse(Call<com.example.denisprawira.ta.Api.Model.User> call, Response<com.example.denisprawira.ta.Api.Model.User> response) {
                assert response.body() != null;
                userSessionManager.setSession(response.body());
                userSessionManager.setTokenApi(GlobalValues.getTokenApi());
                checkDataExist(userSessionManager);
            }

            @Override
            public void onFailure(Call<com.example.denisprawira.ta.Api.Model.User> call, Throwable t) {
                Log.e("klo error: ", t.getMessage());
            }
        });

    }

    @Override
    public Location getLocation() {
        if(location!=null) {
            return location;
        }
        return null;
    }



}

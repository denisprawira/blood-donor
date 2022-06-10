package com.example.denisprawira.ta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.graphics.Color;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.turf.TurfMeta;
import com.mapbox.turf.TurfTransformation;

import androidx.annotation.NonNull;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.turf.TurfConstants.UNIT_KILOMETERS;

public class trymap extends AppCompatActivity implements
    MapboxMap.OnMapClickListener{

        private static final String TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID
                = "TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID";
        private static final String TURF_CALCULATION_FILL_LAYER_ID = "TURF_CALCULATION_FILL_LAYER_ID";
        private static final String CIRCLE_CENTER_SOURCE_ID = "CIRCLE_CENTER_SOURCE_ID";
        private static final String CIRCLE_CENTER_ICON_ID = "CIRCLE_CENTER_ICON_ID";
        private static final String CIRCLE_CENTER_LAYER_ID = "CIRCLE_CENTER_LAYER_ID";
        private static final Point DOWNTOWN_KATHMANDU = Point.fromLngLat(85.323283875, 27.7014884022);
        private static final int RADIUS_SEEKBAR_DIFFERENCE = 1;
        private static final int STEPS_SEEKBAR_DIFFERENCE = 1;
        private static final int STEPS_SEEKBAR_MAX = 360;
        private static final int RADIUS_SEEKBAR_MAX = 500;

// Min is 4 because LinearRings need to be made up of 4 or more coordinates.
        private static final int MINIMUM_CIRCLE_STEPS = 4;
        private Point lastClickPoint = DOWNTOWN_KATHMANDU;
        private MapView mapView;
        private MapboxMap mapboxMap;

// Not static final because they will be adjusted by the seekbars and spinner menu
        private String circleUnit = UNIT_KILOMETERS;
        private int circleSteps = 180;
        private int circleRadius = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.trymap);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(new Style.Builder().fromUri(Style.MAPBOX_STREETS)
                        .withImage(CIRCLE_CENTER_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                                getResources().getDrawable(R.drawable.mapbox_marker_icon_default)))
                        .withSource(new GeoJsonSource(CIRCLE_CENTER_SOURCE_ID,
                                Feature.fromGeometry(DOWNTOWN_KATHMANDU)))
                        .withSource(new GeoJsonSource(TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID))
                        .withLayer(new SymbolLayer(CIRCLE_CENTER_LAYER_ID,
                                CIRCLE_CENTER_SOURCE_ID).withProperties(
                                iconImage(CIRCLE_CENTER_ICON_ID),
                                iconIgnorePlacement(true),
                                iconAllowOverlap(true),
                                iconOffset(new Float[] {0f, 0f})
                        )), new Style.OnStyleLoaded() {
                    @SuppressLint("StringFormatInvalid")
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        trymap.this.mapboxMap = mapboxMap;

                        initPolygonCircleFillLayer();

                        drawPolygonCircle(lastClickPoint);




                        mapboxMap.addOnMapClickListener(trymap.this);
                    }




                });
            }
        });
    }
    @Override
    public boolean onMapClick(@NonNull LatLng mapClickLatLng) {
        mapboxMap.easeCamera(CameraUpdateFactory.newLatLng(mapClickLatLng));
        lastClickPoint = Point.fromLngLat(mapClickLatLng.getLongitude(), mapClickLatLng.getLatitude());
        moveCircleCenterMarker(lastClickPoint);
        drawPolygonCircle(lastClickPoint);
        return true;
    }

    /**
     * Move the red marker icon to wherever the map was tapped on.
     *
     * @param circleCenter where the red marker icon will be moved to.
     */
    private void moveCircleCenterMarker(Point circleCenter) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
// Use Turf to calculate the Polygon's coordinates
                GeoJsonSource markerSource = style.getSourceAs(CIRCLE_CENTER_SOURCE_ID);
                if (markerSource != null) {
                    markerSource.setGeoJson(circleCenter);
                }
            }
        });
    }

    /**
     * Update the {@link FillLayer} based on the GeoJSON retrieved via
     * {@link #getTurfPolygon(Point, double, int, String)}.
     *
     * @param circleCenter the center coordinate to be used in the Turf calculation.
     */
    private void drawPolygonCircle(Point circleCenter) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
// Use Turf to calculate the Polygon's coordinates
                Polygon polygonArea = getTurfPolygon(circleCenter, circleRadius, circleSteps, circleUnit);
                GeoJsonSource polygonCircleSource = style.getSourceAs(TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID);
                if (polygonCircleSource != null) {
                    polygonCircleSource.setGeoJson(Polygon.fromOuterInner(
                            LineString.fromLngLats(TurfMeta.coordAll(polygonArea, false))));
                }
            }
        });
    }

    /**
     * Use the Turf library {@link TurfTransformation#circle(Point, double, int, String)} method to
     * retrieve a {@link Polygon} .
     *
     * @param centerPoint a {@link Point} which the circle will center around
     * @param radius the radius of the circle
     * @param steps  number of steps which make up the circle parameter
     * @param units  one of the units found inside {@link com.mapbox.turf.TurfConstants}
     * @return a {@link Polygon} which represents the newly created circle
     */
    private Polygon getTurfPolygon(@NonNull Point centerPoint, @NonNull double radius,
                                   @NonNull int steps, @NonNull String units) {
        return TurfTransformation.circle(centerPoint, radius, steps, units);
    }

    /**
     * Add a {@link FillLayer} to display a {@link Polygon} in a the shape of a circle.
     */
    private void initPolygonCircleFillLayer() {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
// Create and style a FillLayer based on information that will come from the Turf calculation
                FillLayer fillLayer = new FillLayer(TURF_CALCULATION_FILL_LAYER_ID,
                        TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID);
                fillLayer.setProperties(
                        fillColor(Color.parseColor("#f5425d")),
                        fillOpacity(.7f));
                style.addLayerBelow(fillLayer, CIRCLE_CENTER_LAYER_ID);
            }
        });
    }
}
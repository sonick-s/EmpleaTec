package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Maps extends BottomSheetDialogFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean zoomControlsEnabled = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        // Initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        setupBottomSheetControls(root);

        return root;
    }

    private void setupBottomSheetControls(View root) {
        Button buttonToggleZoom = root.findViewById(R.id.button_toggle_zoom);
        Button buttonCenterBogota = root.findViewById(R.id.button_center_bogota);
        Button buttonAnimatePyramids = root.findViewById(R.id.button_animate_pyramids);
        Button buttonShowCameraPosition = root.findViewById(R.id.button_show_camera_position);
        Button buttonAddParisMarker = root.findViewById(R.id.button_add_paris_marker);
        RadioGroup radioGroupStyles = root.findViewById(R.id.radio_group_styles);
        Spinner spinnerMapType = root.findViewById(R.id.spinner_map_type);

        buttonToggleZoom.setOnClickListener(v -> {
            if (mMap != null) {
                zoomControlsEnabled = !zoomControlsEnabled;
                mMap.getUiSettings().setZoomControlsEnabled(zoomControlsEnabled);
            }
        });

        buttonCenterBogota.setOnClickListener(v -> {
            if (mMap != null) {
                LatLng bogota = new LatLng(4.7110, -74.0721);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 8));
            }
        });

        buttonAnimatePyramids.setOnClickListener(v -> {
            if (mMap != null) {
                LatLng pyramids = new LatLng(29.9773, 31.1325);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(pyramids)
                        .zoom(15)
                        .bearing(50)
                        .tilt(65)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        buttonShowCameraPosition.setOnClickListener(v -> {
            if (mMap != null) {
                CameraPosition cameraPosition = mMap.getCameraPosition();
                String message = "Lat: " + cameraPosition.target.latitude + ", Lng: " + cameraPosition.target.longitude;
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        buttonAddParisMarker.setOnClickListener(v -> {
            if (mMap != null) {
                LatLng paris = new LatLng(48.8566, 2.3522);
                mMap.addMarker(new MarkerOptions().position(paris).title("Paris"));
            }
        });

        radioGroupStyles.setOnCheckedChangeListener((group, checkedId) -> {
            if (mMap != null) {
                if (checkedId == R.id.radio_style1) {
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_black));
                } else if (checkedId == R.id.radio_style2) {
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_white));
                }
            }
        });

        spinnerMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mMap != null) {
                    if (position == 0) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    } else if (position == 1) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    } else if (position == 2) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    } else if (position == 3) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}

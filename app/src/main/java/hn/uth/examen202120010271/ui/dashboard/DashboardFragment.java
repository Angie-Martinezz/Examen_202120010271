package hn.uth.examen202120010271.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import hn.uth.examen202120010271.R;
import hn.uth.examen202120010271.database.Lugar;
import hn.uth.examen202120010271.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment implements LocationListener {

    private static final int REQUEST_CODE_GPS = 844;
    private FragmentDashboardBinding binding;

    private LocationManager locationManager;
    private Lugar ubicacion;

    private Lugar lugarEditar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        ubicacion = null;

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try{
            lugarEditar = (Lugar) getArguments().getSerializable("lugar");
        }catch (Exception e) {
            lugarEditar = null;
        }
        MostrarlugarEditar();

        binding.btnUbicacion.setOnClickListener(v -> {
            solicitarPermisosGPS(this.getContext());
        });

        binding.btnGuardar.setOnClickListener(v -> {
            String nombre =binding.tlNombreL.getEditText().toString();
            String longitude = binding.tvLongitud.getText().toString();
            String latitude = binding.tvLatitud.getText().toString();
            double longitud = Double.parseDouble(longitude);
            double latitud = Double.parseDouble(latitude);

            Lugar nuevo = new Lugar(nombre,longitud,latitud);
            String mensaje = "Lugar agregado correctamente";
            if(lugarEditar == null){
                DashboardViewModel.insert(nuevo);
            }else{
                nuevo.setIdlugar(lugarEditar.getIdlugar());
                DashboardViewModel.update(nuevo);
                mensaje = "Lugar modificado correctamente";
            }

            Snackbar.make(binding.getRoot(), mensaje, Snackbar.LENGTH_LONG).show();
            limpiarCampos();
            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_home);
        });

        binding.btnAbrirM.setOnClickListener(v -> {
            abrirMapa();
        });

        binding.btnEliminar.setOnClickListener(v -> {
            DashboardViewModel.delete(lugarEditar);
            Snackbar.make(binding.getRoot(), "Lugar eliminado correctamente", Snackbar.LENGTH_LONG).show();
            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_home);
        } );
        return root;

    }

    private void limpiarCampos() {
        binding.tlNombreL.getEditText().setText("");
        binding.tvLongitud.setText("");
        binding.tvLatitud.setText("");
        binding.lbNombreC.getEditText();
    }

    private void MostrarlugarEditar() {
        if(lugarEditar == null){

            binding.btnGuardar.setText(R.string.btn_crear_lugar);
            binding.btnEliminar.setVisibility(View.INVISIBLE);
        }else{
            binding.cvData.setVisibility(View.VISIBLE);
            binding.tlNombreL.getEditText().setText(lugarEditar.getLugar());
            binding.tvLongitud.setText(lugarEditar.getLongitude());
            binding.tvLatitud.setText(lugarEditar.getLatitude());
            binding.btnGuardar.setText(R.string.btn_modificar_lugar);
            binding.btnEliminar.setVisibility(View.VISIBLE);

        }
    }

    private void abrirMapa() {
        if(ubicacion == null){
            Snackbar.make(binding.clHome, R.string.no_ubicacion, Snackbar.LENGTH_LONG).show();
        }else{
            Uri mapLocation = Uri.parse("geo:"+ubicacion.toText()+"?z=14");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapLocation);
            startActivity(mapIntent);
        }
    }
    private void solicitarPermisosGPS(Context contexto) {
        if(ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            useFineLocation();
        }else{
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_GPS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GPS){
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    useFineLocation();
                }else if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    useCoarseLocation();
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint({"ServiceCast", "MissingPermission"})
    private void useCoarseLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
    }
    @SuppressLint({"ServiceCast", "MissingPermission"})
    private void useFineLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L,  0, (LocationListener) this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        int calificacion = 0;
        ubicacion = new Lugar("",location.getLatitude(), location.getLongitude());

        binding.tvLatitud.setText(ubicacion.getLatitude());
        binding.tvLongitud.setText(ubicacion.getLongitude());

        binding.cvData.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}

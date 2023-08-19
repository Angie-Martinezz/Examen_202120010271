package hn.uth.examen202120010271.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hn.uth.examen202120010271.OnItemClickListener;
import hn.uth.examen202120010271.R;
import hn.uth.examen202120010271.database.Lugar;
import hn.uth.examen202120010271.databinding.FragmentHomeBinding;
import hn.uth.examen202120010271.ui.dashboard.DashboardViewModel;

public class HomeFragment extends Fragment implements OnItemClickListener<Lugar>{
        private LugarAdapter adaptador;
    private FragmentHomeBinding binding;

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        List<Lugar> lugarList = new ArrayList<>();
        adaptador = new LugarAdapter(lugarList, (OnItemClickListener) this);

        //
        homeViewModel.getAllLugar().observe(getViewLifecycleOwner(), lugares -> {
            if(lugares.isEmpty()){
                Snackbar.make(binding.RLugar,"No hay lugares creados", Snackbar.LENGTH_LONG).show();
            }else{
                adaptador.setItems(lugares);
            }
        });

        setupRecyclerView();
        return root;

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.RLugar.setLayoutManager(linearLayoutManager);
        binding.RLugar.setAdapter(adaptador);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onItemClick(Lugar data, int accion) {
        if(accion == 0){
            Bundle bundle = new Bundle();
            bundle.putSerializable("lugar", (Serializable) data);

            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_home, bundle);


    }
    }


}
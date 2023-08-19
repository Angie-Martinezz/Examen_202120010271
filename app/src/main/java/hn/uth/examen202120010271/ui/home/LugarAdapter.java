package hn.uth.examen202120010271.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hn.uth.examen202120010271.OnItemClickListener;
import hn.uth.examen202120010271.database.Lugar;

import java.util.List;

import hn.uth.examen202120010271.databinding.LugarItemBinding;

public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.ViewHolder>{
    List<Lugar> dataset;

    OnItemClickListener<Lugar> onItemClickLugar;

    public LugarAdapter(List<Lugar>dataset,OnItemClickListener onItemClickLugar ) {
        this.dataset=dataset;
        this.onItemClickLugar=onItemClickLugar;

    }

    @NonNull
    @Override
    public LugarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LugarItemBinding binding = LugarItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LugarAdapter.ViewHolder holder, int position) {
        Lugar lugar = dataset.get(position);
        holder.binding.tvLNombre.setText(lugar.getLugar());
        holder.setOnClickListener(lugar,onItemClickLugar);
    }

    @Override
    public int getItemCount() {

        return dataset.size();

    }

    public void setItems(List<Lugar> lugares){
        this.dataset = lugares;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LugarItemBinding binding;


        public ViewHolder(@NonNull  LugarItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }


        public void  setOnClickListener(Lugar nombreLugar, OnItemClickListener<Lugar> clickListener){
            this.binding.ivVerL.setOnClickListener(v -> clickListener.onItemClick(nombreLugar,0));

        }
    }

}

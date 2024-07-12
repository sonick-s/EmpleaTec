package com.omar.sani.empleatec.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.omar.sani.empleatec.R;
import com.omar.sani.empleatec.controlador.database.home.dbCrearPublicacion;

import java.util.List;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.PublicacionViewHolder> {

    private List<dbCrearPublicacion.Publicacion> publicaciones;

    public PublicacionAdapter(List<dbCrearPublicacion.Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_publicacion, parent, false);
        return new PublicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position) {
        dbCrearPublicacion.Publicacion publicacion = publicaciones.get(position);
        holder.textViewDescription.setText(publicacion.getDescription());
        holder.textViewCategory.setText(publicacion.getCategory());
        Glide.with(holder.itemView.getContext()).load(publicacion.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    public static class PublicacionViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDescription;
        public TextView textViewCategory;
        public ImageView imageView;

        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewCategory = itemView.findViewById(R.id.textViewCategoria);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

package com.omar.sani.empleatec.ui.gallery;

import java.util.List;

public class ValidarProyectos {

    private ProyectoListener proyectoListener;
    private List<Proyecto> proyectoList;

    // Constructor que acepta un ProyectoListener
    public ValidarProyectos(ProyectoListener listener) {
        this.proyectoListener = listener;
    }

    // Constructor sin listener
    public ValidarProyectos() {}

    public void setProyectoListener(ProyectoListener listener) {
        this.proyectoListener = listener;
    }

    public void notifyListener() {
        if (proyectoListener != null) {
            proyectoListener.onProyectoDataReceived(proyectoList);
        }
    }

    public void setProyectoList(List<Proyecto> proyectoList) {
        this.proyectoList = proyectoList;
    }

    public List<Proyecto> getProyectoList() {
        return proyectoList;
    }

    public interface ProyectoListener {
        void onProyectoDataReceived(List<Proyecto> proyectoList);
    }

    public static class Proyecto {
        private String tipoEmpresa;
        private String tipoUsuario;
        private String nombre;
        private String descripcion;
        private String url;
        private String categoria;

        public Proyecto(String tipoEmpresa, String tipoUsuario, String nombre, String descripcion, String url, String categoria) {
            this.tipoEmpresa = tipoEmpresa;
            this.tipoUsuario = tipoUsuario;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.url = url;
            this.categoria = categoria;
        }

        public String getTipoEmpresa() {
            return tipoEmpresa;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getUrl() {
            return url;
        }

        public String getCategoria() {
            return categoria;
        }
    }
}

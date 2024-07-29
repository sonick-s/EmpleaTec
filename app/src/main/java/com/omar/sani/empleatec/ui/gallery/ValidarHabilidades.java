package com.omar.sani.empleatec.ui.gallery;

import java.util.List;

public class ValidarHabilidades {

    private HabilidadListener habilidadListener;
    private List<Habilidad> habilidadList;

    // Constructor que acepta un HabilidadListener
    public ValidarHabilidades(HabilidadListener listener) {
        this.habilidadListener = listener;
    }

    // Constructor sin listener
    public ValidarHabilidades() {}

    public void setHabilidadListener(HabilidadListener listener) {
        this.habilidadListener = listener;
    }

    public void notifyListener() {
        if (habilidadListener != null) {
            habilidadListener.onHabilidadDataReceived(habilidadList);
        }
    }

    public void setHabilidadList(List<Habilidad> habilidadList) {
        this.habilidadList = habilidadList;
    }

    public List<Habilidad> getHabilidadList() {
        return habilidadList;
    }

    public interface HabilidadListener {
        void onHabilidadDataReceived(List<Habilidad> habilidadList);
    }

    public static class Habilidad {
        private String tipoEmpresa;
        private String tipoUsuario;
        private String habilidad;

        public Habilidad(String tipoEmpresa, String tipoUsuario, String habilidad) {
            this.tipoEmpresa = tipoEmpresa;
            this.tipoUsuario = tipoUsuario;
            this.habilidad = habilidad;
        }

        public String getTipoEmpresa() {
            return tipoEmpresa;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public String getHabilidad() {
            return habilidad;
        }
    }
}

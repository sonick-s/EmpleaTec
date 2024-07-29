package com.omar.sani.empleatec.ui.gallery;

import java.util.List;

public class ValidarExperienciaLaboral {

    private ExperienciaListener experienciaListener;
    private List<Experiencia> experienciaList;

    // Constructor que acepta un ExperienciaListener
    public ValidarExperienciaLaboral(ExperienciaListener listener) {
        this.experienciaListener = listener;
    }

    // Constructor sin listener
    public ValidarExperienciaLaboral() {}

    public void setExperienciaListener(ExperienciaListener listener) {
        this.experienciaListener = listener;
    }

    public void notifyListener() {
        if (experienciaListener != null) {
            experienciaListener.onExperienciaDataReceived(experienciaList);
        }
    }

    public void setExperienciaList(List<Experiencia> experienciaList) {
        this.experienciaList = experienciaList;
    }

    public List<Experiencia> getExperienciaList() {
        return experienciaList;
    }

    public interface ExperienciaListener {
        void onExperienciaDataReceived(List<Experiencia> experienciaList);
    }

    public static class Experiencia {
        private String tipoEmpresa;
        private String tipoUsuario;
        private String company;
        private String role;
        private String duration;
        private String description;

        public Experiencia(String tipoEmpresa, String tipoUsuario, String company, String role, String duration, String description) {
            this.tipoEmpresa = tipoEmpresa;
            this.tipoUsuario = tipoUsuario;
            this.company = company;
            this.role = role;
            this.duration = duration;
            this.description = description;
        }

        public String getTipoEmpresa() {
            return tipoEmpresa;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public String getCompany() {
            return company;
        }

        public String getRole() {
            return role;
        }

        public String getDuration() {
            return duration;
        }

        public String getDescription() {
            return description;
        }
    }
}

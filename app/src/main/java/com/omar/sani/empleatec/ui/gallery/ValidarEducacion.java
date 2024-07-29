package com.omar.sani.empleatec.ui.gallery;

import java.util.List;

public class ValidarEducacion {

    private EducacionListener educacionListener;
    private List<Educacion> educacionList;

    // Constructor que acepta un EducacionListener
    public ValidarEducacion(EducacionListener listener) {
        this.educacionListener = listener;
    }

    // Constructor sin listener
    public ValidarEducacion() {}

    public void setEducacionListener(EducacionListener listener) {
        this.educacionListener = listener;
    }

    public void notifyListener() {
        if (educacionListener != null) {
            educacionListener.onEducacionDataReceived(educacionList);
        }
    }

    public void setEducacionList(List<Educacion> educacionList) {
        this.educacionList = educacionList;
    }

    public List<Educacion> getEducacionList() {
        return educacionList;
    }

    public interface EducacionListener {
        void onEducacionDataReceived(List<Educacion> educacionList);
    }

    public static class Educacion {
        private String tipoEmpresa;
        private String tipoUsuario;
        private String institution;
        private String degree;
        private String years;
        private String category;

        public Educacion(String tipoEmpresa, String tipoUsuario, String institution, String degree, String years, String category) {
            this.tipoEmpresa = tipoEmpresa;
            this.tipoUsuario = tipoUsuario;
            this.institution = institution;
            this.degree = degree;
            this.years = years;
            this.category = category;
        }

        public String getTipoEmpresa() {
            return tipoEmpresa;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public String getInstitution() {
            return institution;
        }

        public String getDegree() {
            return degree;
        }

        public String getYears() {
            return years;
        }

        public String getCategory() {
            return category;
        }
    }
}

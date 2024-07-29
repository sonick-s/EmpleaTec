package com.omar.sani.empleatec.ui.gallery;

import java.util.List;

public class ValidarEncabezado {

    private EncabezadoListener encabezadoListener;
    private List<Encabezado> encabezadoList;

    // Constructor que acepta un EncabezadoListener
    public ValidarEncabezado(EncabezadoListener listener) {
        this.encabezadoListener = listener;
    }

    // Constructor sin listener
    public ValidarEncabezado() {}

    public void setEncabezadoListener(EncabezadoListener listener) {
        this.encabezadoListener = listener;
    }

    public void notifyListener() {
        if (encabezadoListener != null) {
            encabezadoListener.onEncabezadoDataReceived(encabezadoList);
        }
    }

    public void setEncabezadoList(List<Encabezado> encabezadoList) {
        this.encabezadoList = encabezadoList;
    }

    public List<Encabezado> getEncabezadoList() {
        return encabezadoList;
    }

    public interface EncabezadoListener {
        void onEncabezadoDataReceived(List<Encabezado> encabezadoList);
    }

    public static class Encabezado {
        private String tipoEmpresa;
        private String tipoUsuario;
        private String userName;
        private String userBio;
        private String userEmail;

        public Encabezado(String tipoEmpresa, String tipoUsuario, String userName, String userBio, String userEmail) {
            this.tipoEmpresa = tipoEmpresa;
            this.tipoUsuario = tipoUsuario;
            this.userName = userName;
            this.userBio = userBio;
            this.userEmail = userEmail;
        }

        public String getTipoEmpresa() {
            return tipoEmpresa;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserBio() {
            return userBio;
        }

        public String getUserEmail() {
            return userEmail;
        }
    }
}

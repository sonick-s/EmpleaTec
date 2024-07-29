package com.omar.sani.empleatec.ui.gallery;

import java.io.Serializable;
import java.util.List;

public class ValidarContacto {

    private ContactoListener contactoListener;
    private List<Contacto> contactoList;

    // Constructor que acepta un ContactoListener
    public ValidarContacto(ContactoListener listener) {
        this.contactoListener = listener;
    }

    // Constructor sin listener
    public ValidarContacto() {}

    public void setContactoListener(ContactoListener listener) {
        this.contactoListener = listener;
    }

    public void notifyListener() {
        if (contactoListener != null) {
            contactoListener.onContactoDataReceived(contactoList);
        }
    }

    public void setContactoList(List<Contacto> contactoList) {
        this.contactoList = contactoList;
    }

    public List<Contacto> getContactoList() {
        return contactoList;
    }

    public interface ContactoListener {
        void onContactoDataReceived(List<Contacto> contactoList);
    }

    public static class Contacto implements Serializable {
        private String tipoEmpresa;
        private String tipoUsuario;
        private String email;
        private String phone;

        // Constructor
        public Contacto(String tipoEmpresa, String tipoUsuario, String email, String phone) {
            this.tipoEmpresa = tipoEmpresa;
            this.tipoUsuario = tipoUsuario;
            this.email = email;
            this.phone = phone;
        }

        // Getters
        public String getTipoEmpresa() {
            return tipoEmpresa;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }
    }
}

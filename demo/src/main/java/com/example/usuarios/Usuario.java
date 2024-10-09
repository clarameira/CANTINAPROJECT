package com.example.usuarios;

public class Usuario {
    private String login;
    private String senha;
    private int tipoUsuario; 
    private boolean loginPermitido;
    

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = 0; 
        this.loginPermitido = false;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public boolean isLoginPermitido() {
        return loginPermitido;
    }

    public void permitirLogin() {
        loginPermitido = true;
    }

    public void setIdentificacao(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    
    public String toString() {
        
        return "login: "+ getLogin() + " senha: " + getSenha();
    }
}


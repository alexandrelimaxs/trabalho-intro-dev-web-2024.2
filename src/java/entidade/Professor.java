/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidade;

/**
 *
 * @author alexandre-colmenero
 */

public class Professor {

    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String senha;

    // Construtor com todos os campos (exceto o ID, pois este costuma ser gerado automaticamente)
    public Professor(String nome, String email, String cpf, String senha) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }

    // Construtor para login, por exemplo, usando CPF e senha
    public Professor(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    // Construtor padrão, inicializando com valores default
    public Professor() {
        this.id = 0;
        this.nome = "";
        this.email = "";
        this.cpf = "";
        this.senha = "";
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}

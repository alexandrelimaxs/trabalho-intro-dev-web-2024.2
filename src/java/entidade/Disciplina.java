/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidade;

/**
 *
 * @author alexandre-colmenero
 */

public class Disciplina {
    private int id;
    private String nome;
    private String requisito;
    private String ementa;
    private Integer cargaHoraria; // Pode ser null, então usamos Integer

    // Construtor completo (exceto ID, que é auto-increment)
    public Disciplina(String nome, String requisito, String ementa, Integer cargaHoraria) {
        this.nome = nome;
        this.requisito = requisito;
        this.ementa = ementa;
        this.cargaHoraria = cargaHoraria;
    }

    // Construtor padrão
    public Disciplina() {
        this.id = 0;
        this.nome = "";
        this.requisito = "";
        this.ementa = "";
        this.cargaHoraria = null;
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

    public String getRequisito() {
        return requisito;
    }
    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }

    public String getEmenta() {
        return ementa;
    }
    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }
    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
}

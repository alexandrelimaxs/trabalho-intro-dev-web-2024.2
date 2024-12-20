/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author alexandre-colmenero
 */


public class Relatorio {
    private String nomeDisciplina;
    private String nomeProfessor;
    private String nomeAluno;
    private String cpfAluno;
    private String codigoTurma;
    private Float nota;

    // Getters e Setters
    public String getNomeDisciplina() { return nomeDisciplina; }
    public void setNomeDisciplina(String nomeDisciplina) { this.nomeDisciplina = nomeDisciplina; }

    public String getNomeProfessor() { return nomeProfessor; }
    public void setNomeProfessor(String nomeProfessor) { this.nomeProfessor = nomeProfessor; }

    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }

    public String getCpfAluno() { return cpfAluno; }
    public void setCpfAluno(String cpfAluno) { this.cpfAluno = cpfAluno; }

    public String getCodigoTurma() { return codigoTurma; }
    public void setCodigoTurma(String codigoTurma) { this.codigoTurma = codigoTurma; }

    public Float getNota() { return nota; }
    public void setNota(Float nota) { this.nota = nota; }
}


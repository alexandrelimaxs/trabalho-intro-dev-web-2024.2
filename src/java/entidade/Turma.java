/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidade;

/**
 *
 * @author alexandre-colmenero
 */

public class Turma {
    private int id;
    private int professorId;
    private int disciplinaId;
    private int alunoId;
    private String codigoTurma;
    private float nota;

    public Turma(int professorId, int disciplinaId, int alunoId, String codigoTurma, float nota) {
        this.professorId = professorId;
        this.disciplinaId = disciplinaId;
        this.alunoId = alunoId;
        this.codigoTurma = codigoTurma;
        this.nota = nota;
    }

    public Turma() {
        this.id = 0;
        this.professorId = 0;
        this.disciplinaId = 0;
        this.alunoId = 0;
        this.codigoTurma = "";
        this.nota = 0f;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}

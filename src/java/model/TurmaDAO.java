/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author alexandre-colmenero
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.math.BigDecimal;
import entidade.Turma;
import model.Relatorio;

public class TurmaDAO implements Dao<Turma> {

    @Override
    public Turma get(int id) {
        Conexao conexao = new Conexao();
        Turma turma = new Turma();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "SELECT * FROM turmas WHERE id = ?"
            );
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null && resultado.next()) {
                turma.setId(resultado.getInt("id"));
                turma.setProfessorId(resultado.getInt("professor_id"));
                turma.setDisciplinaId(resultado.getInt("disciplina_id"));
                turma.setAlunoId(resultado.getInt("aluno_id"));
                turma.setCodigoTurma(resultado.getString("codigo_turma"));
                turma.setNota(resultado.getBigDecimal("nota"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return turma;
    }

    @Override
    public ArrayList<Turma> getAll() {
        ArrayList<Turma> minhasTurmas = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM turmas ORDER BY codigo_turma";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado != null && resultado.next()) {
                Turma turma = new Turma();
                turma.setId(resultado.getInt("id"));
                turma.setProfessorId(resultado.getInt("professor_id"));
                turma.setDisciplinaId(resultado.getInt("disciplina_id"));
                turma.setAlunoId(resultado.getInt("aluno_id"));
                turma.setCodigoTurma(resultado.getString("codigo_turma"));
                turma.setNota(resultado.getBigDecimal("nota"));
                minhasTurmas.add(turma);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar turmas: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return minhasTurmas;
    }

    @Override
    public void insert(Turma turma) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigo_turma, nota) VALUES (?,?,?,?,?)"
            );
            sql.setInt(1, turma.getProfessorId());
            sql.setInt(2, turma.getDisciplinaId());
            sql.setInt(3, turma.getAlunoId());
            sql.setString(4, turma.getCodigoTurma());
            sql.setBigDecimal(5, turma.getNota());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Turma turma) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "UPDATE turmas SET professor_id=?, disciplina_id=?, aluno_id=?, codigo_turma=?, nota=? WHERE id=?"
            );
            sql.setInt(1, turma.getProfessorId());
            sql.setInt(2, turma.getDisciplinaId());
            sql.setInt(3, turma.getAlunoId());
            sql.setString(4, turma.getCodigoTurma());
            sql.setBigDecimal(5, turma.getNota());
            sql.setInt(6, turma.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "DELETE FROM turmas WHERE id=?"
            );
            sql.setInt(1, id);
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir turma: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
    
    public ArrayList<Relatorio> getRelatorio() throws Exception {
    ArrayList<Relatorio> lista = new ArrayList<>();
    Conexao conexao = new Conexao();
    try {
        String sql = "SELECT d.nome AS disciplina, p.nome AS professor, a.nome AS aluno, a.cpf AS cpfAluno, t.codigo_turma, t.nota " +
                     "FROM turmas t " +
                     "JOIN disciplina d ON t.disciplina_id = d.id " +
                     "JOIN professores p ON t.professor_id = p.id " +
                     "JOIN alunos a ON t.aluno_id = a.id " +
                     "ORDER BY d.nome, a.nome";

        PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs != null && rs.next()) {
            Relatorio dr = new Relatorio();
            dr.setNomeDisciplina(rs.getString("disciplina"));
            dr.setNomeProfessor(rs.getString("professor"));
            dr.setNomeAluno(rs.getString("aluno"));
            dr.setCpfAluno(rs.getString("cpfAluno"));
            dr.setCodigoTurma(rs.getString("codigo_turma"));
            dr.setNota(rs.getBigDecimal("nota"));
            lista.add(dr);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
    return lista;
}

}


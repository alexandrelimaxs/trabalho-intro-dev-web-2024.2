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

import entidade.Disciplina;

public class DisciplinaDAO implements Dao<Disciplina> {

    @Override
    public Disciplina get(int id) {
        Conexao conexao = new Conexao();
        Disciplina disciplina = new Disciplina();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "SELECT * FROM disciplina WHERE id = ?"
            );
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null && resultado.next()) {
                disciplina.setId(resultado.getInt("id"));
                disciplina.setNome(resultado.getString("nome"));
                disciplina.setRequisito(resultado.getString("requisito"));
                disciplina.setEmenta(resultado.getString("ementa"));
                int carga = resultado.getInt("carga_horaria");
                disciplina.setCargaHoraria(resultado.wasNull() ? null : carga);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter disciplina: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return disciplina;
    }

    @Override
    public ArrayList<Disciplina> getAll() {
        ArrayList<Disciplina> minhasDisciplinas = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM disciplina ORDER BY nome";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado != null && resultado.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(resultado.getInt("id"));
                disciplina.setNome(resultado.getString("nome"));
                disciplina.setRequisito(resultado.getString("requisito"));
                disciplina.setEmenta(resultado.getString("ementa"));
                int carga = resultado.getInt("carga_horaria");
                disciplina.setCargaHoraria(resultado.wasNull() ? null : carga);
                minhasDisciplinas.add(disciplina);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar disciplinas: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return minhasDisciplinas;
    }

    @Override
    public void insert(Disciplina disciplina) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "INSERT INTO disciplina (nome, requisito, ementa, carga_horaria) VALUES (?,?,?,?)"
            );
            sql.setString(1, disciplina.getNome());
            sql.setString(2, disciplina.getRequisito());
            sql.setString(3, disciplina.getEmenta());
            if (disciplina.getCargaHoraria() != null) {
                sql.setInt(4, disciplina.getCargaHoraria());
            } else {
                sql.setNull(4, java.sql.Types.INTEGER);
            }
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir disciplina: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Disciplina disciplina) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "UPDATE disciplina SET nome=?, requisito=?, ementa=?, carga_horaria=? WHERE id=?"
            );
            sql.setString(1, disciplina.getNome());
            sql.setString(2, disciplina.getRequisito());
            sql.setString(3, disciplina.getEmenta());
            if (disciplina.getCargaHoraria() != null) {
                sql.setInt(4, disciplina.getCargaHoraria());
            } else {
                sql.setNull(4, java.sql.Types.INTEGER);
            }
            sql.setInt(5, disciplina.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar disciplina: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "DELETE FROM disciplina WHERE id=?"
            );
            sql.setInt(1, id);
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir disciplina: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }
}

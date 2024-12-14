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

import entidade.Professor;

public class ProfessorDAO {

    public void Inserir(Professor professor) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "INSERT INTO professores (nome, email, cpf, senha) VALUES (?,?,?,?)"
            );
            sql.setString(1, professor.getNome());
            sql.setString(2, professor.getEmail());
            sql.setString(3, professor.getCpf());
            sql.setString(4, professor.getSenha());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public Professor getProfessor(int id) throws Exception {
        Conexao conexao = new Conexao();
        Professor professor = new Professor();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "SELECT * FROM professores WHERE id = ?"
            );
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null && resultado.next()) {
                professor.setId(resultado.getInt("id"));
                professor.setNome(resultado.getString("nome"));
                professor.setEmail(resultado.getString("email"));
                professor.setCpf(resultado.getString("cpf"));
                professor.setSenha(resultado.getString("senha"));
            }
            return professor;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter professor por ID: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public void Alterar(Professor professor) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "UPDATE professores SET nome = ?, email = ?, cpf = ?, senha = ? WHERE id = ?"
            );
            sql.setString(1, professor.getNome());
            sql.setString(2, professor.getEmail());
            sql.setString(3, professor.getCpf());
            sql.setString(4, professor.getSenha());
            sql.setInt(5, professor.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public void Excluir(Professor professor) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "DELETE FROM professores WHERE id = ?"
            );
            sql.setInt(1, professor.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public ArrayList<Professor> ListaDeProfessores() throws Exception {
        ArrayList<Professor> meusProfessores = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM professores ORDER BY nome";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado != null && resultado.next()) {
                Professor professor = new Professor();
                professor.setId(resultado.getInt("id"));
                professor.setNome(resultado.getString("nome"));
                professor.setEmail(resultado.getString("email"));
                professor.setCpf(resultado.getString("cpf"));
                professor.setSenha(resultado.getString("senha"));
                meusProfessores.add(professor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return meusProfessores;
    }

    public Professor Logar(Professor professor) throws Exception {
        Conexao conexao = new Conexao();
        Professor professorObtido = new Professor();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "SELECT * FROM professores WHERE cpf=? AND senha=? LIMIT 1"
            );
            sql.setString(1, professor.getCpf());
            sql.setString(2, professor.getSenha());
            ResultSet resultado = sql.executeQuery();
            if (resultado != null && resultado.next()) {
                professorObtido.setId(resultado.getInt("id"));
                professorObtido.setNome(resultado.getString("nome"));
                professorObtido.setEmail(resultado.getString("email"));
                professorObtido.setCpf(resultado.getString("cpf"));
                professorObtido.setSenha(resultado.getString("senha"));
            }
            return professorObtido;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao logar professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

}

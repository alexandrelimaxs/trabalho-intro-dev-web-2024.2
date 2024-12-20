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

import entidade.Aluno;

public class AlunoDAO implements Dao<Aluno> {

    @Override
    public Aluno get(int id) {
        Conexao conexao = new Conexao();
        Aluno aluno = new Aluno();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "SELECT * FROM alunos WHERE id = ?"
            );
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null && resultado.next()) {
                aluno.setId(resultado.getInt("id"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setCelular(resultado.getString("celular"));
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setSenha(resultado.getString("senha"));
                aluno.setEndereco(resultado.getString("endereco"));
                aluno.setCidade(resultado.getString("cidade"));
                aluno.setBairro(resultado.getString("bairro"));
                aluno.setCep(resultado.getString("cep"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return aluno;
    }

    @Override
    public ArrayList<Aluno> getAll() {
        ArrayList<Aluno> meusAlunos = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM alunos ORDER BY nome";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado != null && resultado.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(resultado.getInt("id"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setCelular(resultado.getString("celular"));
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setSenha(resultado.getString("senha"));
                aluno.setEndereco(resultado.getString("endereco"));
                aluno.setCidade(resultado.getString("cidade"));
                aluno.setBairro(resultado.getString("bairro"));
                aluno.setCep(resultado.getString("cep"));
                meusAlunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return meusAlunos;
    }

    @Override
    public void insert(Aluno aluno) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "INSERT INTO alunos (nome, email, celular, cpf, senha, endereco, cidade, bairro, cep) VALUES (?,?,?,?,?,?,?,?,?)"
            );
            sql.setString(1, aluno.getNome());
            sql.setString(2, aluno.getEmail());
            sql.setString(3, aluno.getCelular());
            sql.setString(4, aluno.getCpf());
            sql.setString(5, aluno.getSenha());
            sql.setString(6, aluno.getEndereco());
            sql.setString(7, aluno.getCidade());
            sql.setString(8, aluno.getBairro());
            sql.setString(9, aluno.getCep());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Aluno aluno) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "UPDATE alunos SET nome=?, email=?, celular=?, cpf=?, senha=?, endereco=?, cidade=?, bairro=?, cep=? WHERE id=?"
            );
            sql.setString(1, aluno.getNome());
            sql.setString(2, aluno.getEmail());
            sql.setString(3, aluno.getCelular());
            sql.setString(4, aluno.getCpf());
            sql.setString(5, aluno.getSenha());
            sql.setString(6, aluno.getEndereco());
            sql.setString(7, aluno.getCidade());
            sql.setString(8, aluno.getBairro());
            sql.setString(9, aluno.getCep());
            sql.setInt(10, aluno.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "DELETE FROM alunos WHERE id=?"
            );
            sql.setInt(1, id);
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    // Método extra não definido na interface Dao, mantido caso necessário
    public Aluno Logar(Aluno aluno) {
        Conexao conexao = new Conexao();
        Aluno alunoObtido = new Aluno();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                "SELECT * FROM alunos WHERE cpf=? AND senha=? LIMIT 1"
            );
            sql.setString(1, aluno.getCpf());
            sql.setString(2, aluno.getSenha());
            ResultSet resultado = sql.executeQuery();
            if (resultado != null && resultado.next()) {
                alunoObtido.setId(resultado.getInt("id"));
                alunoObtido.setNome(resultado.getString("nome"));
                alunoObtido.setEmail(resultado.getString("email"));
                alunoObtido.setCelular(resultado.getString("celular"));
                alunoObtido.setCpf(resultado.getString("cpf"));
                alunoObtido.setSenha(resultado.getString("senha"));
                alunoObtido.setEndereco(resultado.getString("endereco"));
                alunoObtido.setCidade(resultado.getString("cidade"));
                alunoObtido.setBairro(resultado.getString("bairro"));
                alunoObtido.setCep(resultado.getString("cep"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao logar aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return alunoObtido;
    }

    
}

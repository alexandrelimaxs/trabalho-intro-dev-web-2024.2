package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Administrador;

/*
-- Estrutura da tabela `Administradors`

CREATE TABLE IF NOT EXISTS `Administrador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `senha` varchar(8) NOT NULL,
  `endereco` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

 */

public class AdministradorDAO implements Dao<Administrador> {

    @Override
    public Administrador get(int id) {
        Conexao conexao = new Conexao();
        Administrador administrador = new Administrador();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM administrador WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null && resultado.next()) {
                administrador.setId(resultado.getInt("ID"));
                administrador.setNome(resultado.getString("NOME"));
                administrador.setCpf(resultado.getString("CPF"));
                administrador.setEndereco(resultado.getString("ENDERECO"));
                administrador.setSenha(resultado.getString("SENHA"));
            }
            return administrador;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter administrador: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Administrador> getAll() {
        ArrayList<Administrador> meusAdministradores = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM administrador ORDER BY nome";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado != null && resultado.next()) {
                Administrador administrador = new Administrador(
                        resultado.getString("NOME"),
                        resultado.getString("CPF"),
                        resultado.getString("ENDERECO"),
                        resultado.getString("SENHA"));
                administrador.setId(resultado.getInt("ID"));
                meusAdministradores.add(administrador);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar administradores: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return meusAdministradores;
    }

    @Override
    public void insert(Administrador administrador) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                    "INSERT INTO administrador (nome, cpf, endereco, senha) VALUES (?,?,?,?)");
            sql.setString(1, administrador.getNome());
            sql.setString(2, administrador.getCpf());
            sql.setString(3, administrador.getEndereco());
            sql.setString(4, administrador.getSenha());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir administrador: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Administrador administrador) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                    "UPDATE administrador SET nome = ?, cpf = ?, endereco = ?, senha = ? WHERE ID = ?");
            sql.setString(1, administrador.getNome());
            sql.setString(2, administrador.getCpf());
            sql.setString(3, administrador.getEndereco());
            sql.setString(4, administrador.getSenha());
            sql.setInt(5, administrador.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar administrador: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM administrador WHERE ID = ?");
            sql.setInt(1, id);
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir administrador: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    // Método extra, não faz parte da interface Dao<T>, mas pode ser útil para lógica de login
    public Administrador Logar(Administrador administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM administrador WHERE cpf=? and senha=? LIMIT 1");
            sql.setString(1, administrador.getCpf());
            sql.setString(2, administrador.getSenha());
            ResultSet resultado = sql.executeQuery();
            Administrador administradorObtido = new Administrador();
            if (resultado != null) {
                while (resultado.next()) {
                    administradorObtido.setId(resultado.getInt("ID"));
                    administradorObtido.setNome(resultado.getString("NOME"));
                    administradorObtido.setCpf(resultado.getString("CPF"));
                    administradorObtido.setEndereco(resultado.getString("ENDERECO"));
                    administradorObtido.setSenha(resultado.getString("SENHA"));
                    administradorObtido.setAprovado(resultado.getString("APROVADO")); // carregar o campo aprovado
                }
            }
            return administradorObtido;
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }
    

}

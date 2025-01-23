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

import entidade.Turma;
import model.Relatorio;

public class TurmaDAO implements Dao<Turma> {

    @Override
    public Turma get(int id) {
        Conexao conexao = new Conexao();
        Turma turma = new Turma();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                    "SELECT * FROM turmas WHERE id = ?");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null && resultado.next()) {
                turma.setId(resultado.getInt("id"));
                turma.setProfessorId(resultado.getInt("professor_id"));
                turma.setDisciplinaId(resultado.getInt("disciplina_id"));
                turma.setAlunoId(resultado.getInt("aluno_id"));
                turma.setCodigoTurma(resultado.getString("codigo_turma"));
                turma.setNota(resultado.getFloat("nota"));
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
                turma.setNota(resultado.getFloat("nota"));
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
                    "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigo_turma, nota) VALUES (?,?,?,?,?)");
            sql.setInt(1, turma.getProfessorId());
            sql.setInt(2, turma.getDisciplinaId());
            sql.setInt(3, turma.getAlunoId());
            sql.setString(4, turma.getCodigoTurma());
            sql.setFloat(5, turma.getNota());
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
                    "UPDATE turmas SET professor_id=?, disciplina_id=?, aluno_id=?, codigo_turma=?, nota=? WHERE id=?");
            sql.setInt(1, turma.getProfessorId());
            sql.setInt(2, turma.getDisciplinaId());
            sql.setInt(3, turma.getAlunoId());
            sql.setString(4, turma.getCodigoTurma());
            sql.setFloat(5, turma.getNota());
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
                    "DELETE FROM turmas WHERE id=?");
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
            String sql = "SELECT d.nome AS disciplina, p.nome AS professor, a.nome AS aluno, a.cpf AS cpfAluno, t.codigo_turma, t.nota "
                    +
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
                dr.setNota(rs.getFloat("nota"));
                lista.add(dr);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return lista;
    }

    public int countByCodigoTurma(int idTurma) {
        int count = 0;
        Conexao conexao = new Conexao();
        try {
            // Supondo que você queira contar quantos registros têm o MESMO código_turma
            // Se for ID da turma principal, é outra lógica
            PreparedStatement ps = conexao.getConexao().prepareStatement(
                    "SELECT codigo_turma FROM turmas WHERE id = ?");
            ps.setInt(1, idTurma);
            ResultSet rs = ps.executeQuery();
            String codTurmaEncontrado = "";
            if (rs.next()) {
                codTurmaEncontrado = rs.getString("codigo_turma");
            }
            rs.close();

            PreparedStatement ps2 = conexao.getConexao().prepareStatement(
                    "SELECT COUNT(*) as total FROM turmas WHERE codigo_turma = ?");
            ps2.setString(1, codTurmaEncontrado);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                count = rs2.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar turmas por código: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return count;
    }

    public ArrayList<Turma> getTurmasPorAluno(int alunoId) {
        ArrayList<Turma> turmasDoAluno = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            PreparedStatement ps = conexao.getConexao().prepareStatement(
                    "SELECT * FROM turmas WHERE aluno_id = ? ORDER BY codigo_turma");
            ps.setInt(1, alunoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Turma t = new Turma();
                t.setId(rs.getInt("id"));
                t.setProfessorId(rs.getInt("professor_id"));
                t.setDisciplinaId(rs.getInt("disciplina_id"));
                t.setAlunoId(rs.getInt("aluno_id"));
                t.setCodigoTurma(rs.getString("codigo_turma"));
                t.setNota(rs.getFloat("nota"));
                turmasDoAluno.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar turmas por aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return turmasDoAluno;
    }

    public ArrayList<TurmaDisponivelDTO> getTurmasDisponiveis() {
        ArrayList<TurmaDisponivelDTO> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT t.id AS turmaId, t.codigo_turma, d.nome AS discName, p.nome AS profName "
                    + "FROM turmas t "
                    + "JOIN disciplina d ON t.disciplina_id = d.id "
                    + "JOIN professores p ON t.professor_id = p.id "
                    + "ORDER BY t.codigo_turma";

            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TurmaDisponivelDTO dto = new TurmaDisponivelDTO();
                dto.setIdTurma(rs.getInt("turmaId"));
                dto.setCodigoTurma(rs.getString("codigo_turma"));
                dto.setNomeDisciplina(rs.getString("discName"));
                dto.setNomeProfessor(rs.getString("profName"));
                lista.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar turmas com joins: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return lista;
    }

    public ArrayList<TurmaHistoricoDTO> getHistoricoAluno(int alunoId) {
        ArrayList<TurmaHistoricoDTO> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT t.codigo_turma, t.nota, d.nome AS discName, p.nome AS profName "
                    + "FROM turmas t "
                    + "JOIN disciplina d ON t.disciplina_id = d.id "
                    + "JOIN professores p ON t.professor_id = p.id "
                    + "WHERE t.aluno_id = ? "
                    + "ORDER BY t.codigo_turma";

            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, alunoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TurmaHistoricoDTO dto = new TurmaHistoricoDTO();
                dto.setCodigoTurma(rs.getString("codigo_turma"));
                dto.setNota(rs.getFloat("nota"));
                dto.setNomeDisciplina(rs.getString("discName"));
                dto.setNomeProfessor(rs.getString("profName"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter histórico do aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return lista;
    }

}

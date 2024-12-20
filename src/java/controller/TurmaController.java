/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author alexandre-colmenero
 */


import entidade.Turma;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.TurmaDAO;

@WebServlet(name = "TurmaController", urlPatterns = {"/admin/turmas"})
public class TurmaController extends HttpServlet {

    private String validarDados(int professorId, int disciplinaId, int alunoId, String codigoTurma, float nota) {
        if (professorId < 0) {
            return "Professor ID não pode ser negativo.";
        }
        if (disciplinaId < 0) {
            return "Disciplina ID não pode ser negativo.";
        }
        if (alunoId < 0) {
            return "Aluno ID não pode ser negativo.";
        }
        if (codigoTurma == null || codigoTurma.trim().isEmpty()) {
            return "Código da Turma não pode estar vazio.";
        }
        if (nota < 0) {
            return "Nota não pode ser negativa.";
        }
        return "";
    }

    private String validarId(int id) {
        if (id < 0) {
            return "ID não pode ser negativo.";
        }
        return "";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("administrador") == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        TurmaDAO dao = new TurmaDAO();
        RequestDispatcher rd;

        try {
            switch (acao) {
                case "Listar":
                    ArrayList<Turma> lista = dao.getAll();
                    request.setAttribute("listaTurmas", lista);
                    rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                    rd.forward(request, response);
                    break;

                case "Excluir":
                    int idExcluir = Integer.parseInt(request.getParameter("id"));
                    String msgErroId = validarId(idExcluir);
                    if (!msgErroId.isEmpty()) {
                        request.setAttribute("msg", msgErroId);
                        ArrayList<Turma> listaExc = dao.getAll();
                        request.setAttribute("listaTurmas", listaExc);
                        rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    dao.delete(idExcluir);
                    request.setAttribute("msg", "Turma excluída com sucesso!");
                    ArrayList<Turma> listaPosExclusao = dao.getAll();
                    request.setAttribute("listaTurmas", listaPosExclusao);
                    rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                    rd.forward(request, response);
                    break;

                case "AlterarForm":
                    int idAlterar = Integer.parseInt(request.getParameter("id"));
                    msgErroId = validarId(idAlterar);
                    if (!msgErroId.isEmpty()) {
                        request.setAttribute("msg", msgErroId);
                        ArrayList<Turma> listaAlt = dao.getAll();
                        request.setAttribute("listaTurmas", listaAlt);
                        rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    Turma turmaEdicao = dao.get(idAlterar);
                    if (turmaEdicao.getId() != 0) {
                        request.setAttribute("turmaEdicao", turmaEdicao);
                        ArrayList<Turma> listaAlteracao = dao.getAll();
                        request.setAttribute("listaTurmas", listaAlteracao);
                        rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                        rd.forward(request, response);
                    } else {
                        response.sendRedirect("/aplicacaoMVC/admin/turmas?acao=Listar");
                    }
                    break;

                default:
                    response.sendRedirect("/aplicacaoMVC/admin/turmas?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no TurmaController: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("administrador") == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        TurmaDAO dao = new TurmaDAO();
        RequestDispatcher rd;

        try {
            if ("Incluir".equals(acao)) {
                int professorId = Integer.parseInt(request.getParameter("professor_id"));
                int disciplinaId = Integer.parseInt(request.getParameter("disciplina_id"));
                int alunoId = Integer.parseInt(request.getParameter("aluno_id"));
                String codigoTurma = request.getParameter("codigo_turma");
                float nota = Float.parseFloat(request.getParameter("nota"));

                String msgErro = validarDados(professorId, disciplinaId, alunoId, codigoTurma, nota);
                if (!msgErro.isEmpty()) {
                    request.setAttribute("msg", msgErro);
                    ArrayList<Turma> listaTurmas = dao.getAll();
                    request.setAttribute("listaTurmas", listaTurmas);
                    rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                    rd.forward(request, response);
                    return;
                }

                Turma novaTurma = new Turma(professorId, disciplinaId, alunoId, codigoTurma, nota);
                dao.insert(novaTurma);
                request.setAttribute("msg", "Turma incluída com sucesso!");
                ArrayList<Turma> listaTurmas = dao.getAll();
                request.setAttribute("listaTurmas", listaTurmas);
                rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                rd.forward(request, response);

            } else if ("Alterar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                int professorId = Integer.parseInt(request.getParameter("professor_id"));
                int disciplinaId = Integer.parseInt(request.getParameter("disciplina_id"));
                int alunoId = Integer.parseInt(request.getParameter("aluno_id"));
                String codigoTurma = request.getParameter("codigo_turma");
                float nota = Float.parseFloat(request.getParameter("nota"));

                String msgErroId = validarId(id);
                if (!msgErroId.isEmpty()) {
                    request.setAttribute("msg", msgErroId);
                    ArrayList<Turma> listaTurmas = dao.getAll();
                    request.setAttribute("listaTurmas", listaTurmas);
                    rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                    rd.forward(request, response);
                    return;
                }

                String msgErro = validarDados(professorId, disciplinaId, alunoId, codigoTurma, nota);
                if (!msgErro.isEmpty()) {
                    request.setAttribute("msg", msgErro);
                    Turma turmaEdicao = new Turma(professorId, disciplinaId, alunoId, codigoTurma, nota);
                    turmaEdicao.setId(id);
                    ArrayList<Turma> listaTurmas = dao.getAll();
                    request.setAttribute("listaTurmas", listaTurmas);
                    request.setAttribute("turmaEdicao", turmaEdicao);
                    rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                    rd.forward(request, response);
                    return;
                }

                Turma turmaAlterada = new Turma(professorId, disciplinaId, alunoId, codigoTurma, nota);
                turmaAlterada.setId(id);
                dao.update(turmaAlterada);
                request.setAttribute("msg", "Turma alterada com sucesso!");
                ArrayList<Turma> listaTurmas = dao.getAll();
                request.setAttribute("listaTurmas", listaTurmas);
                rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                rd.forward(request, response);

            } else {
                response.sendRedirect("/aplicacaoMVC/admin/turmas?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no TurmaController POST: " + e.getMessage());
        }
    }
}
 

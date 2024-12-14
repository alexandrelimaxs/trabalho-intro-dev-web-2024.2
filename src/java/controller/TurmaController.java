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
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.TurmaDAO;

@WebServlet(name = "TurmaController", urlPatterns = {"/admin/turmas"})
public class TurmaController extends HttpServlet {

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
                    dao.delete(idExcluir);
                    request.setAttribute("msg", "Turma excluída com sucesso!");
                    ArrayList<Turma> listaPosExclusao = dao.getAll();
                    request.setAttribute("listaTurmas", listaPosExclusao);
                    rd = request.getRequestDispatcher("/views/admin/turma.jsp");
                    rd.forward(request, response);
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
                BigDecimal nota = new BigDecimal(request.getParameter("nota"));

                Turma novaTurma = new Turma(professorId, disciplinaId, alunoId, codigoTurma, nota);
                dao.insert(novaTurma);
                request.setAttribute("msg", "Turma incluída com sucesso!");

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


package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexandre-colmenero
 */

import entidade.Professor;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ProfessorDAO;

@WebServlet(name = "ProfessorController", urlPatterns = {"/admin/professores"})
public class ProfessorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("administrador") == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        ProfessorDAO dao = new ProfessorDAO();
        RequestDispatcher rd;

        try {
            switch (acao) {
                case "Listar":
                    ArrayList<Professor> lista = dao.getAll();
                    request.setAttribute("listaProfessores", lista);
                    rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                    rd.forward(request, response);
                    break;

                case "Excluir":
                    int idExcluir = Integer.parseInt(request.getParameter("id"));
                    dao.delete(idExcluir);
                    request.setAttribute("msg", "Professor excluído com sucesso!");
                    ArrayList<Professor> listaPosExclusao = dao.getAll();
                    request.setAttribute("listaProfessores", listaPosExclusao);
                    rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                    rd.forward(request, response);
                    break;

                default:
                    response.sendRedirect("/aplicacaoMVC/admin/professores?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no ProfessorController: " + e.getMessage());
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
        ProfessorDAO dao = new ProfessorDAO();
        RequestDispatcher rd;

        try {
            if ("Incluir".equals(acao)) {
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String cpf = request.getParameter("cpf");
                String senha = request.getParameter("senha");

                Professor novoProfessor = new Professor(nome, email, cpf, senha);
                dao.insert(novoProfessor);
                request.setAttribute("msg", "Professor incluído com sucesso!");

                ArrayList<Professor> listaProfessores = dao.getAll();
                request.setAttribute("listaProfessores", listaProfessores);
                rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("/aplicacaoMVC/admin/professores?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no ProfessorController POST: " + e.getMessage());
        }
    }
}

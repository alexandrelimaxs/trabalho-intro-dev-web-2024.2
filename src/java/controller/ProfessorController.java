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
import javax.servlet.http.*;

import model.ProfessorDAO;

@WebServlet(name = "ProfessorController", urlPatterns = {"/admin/professores"})
public class ProfessorController extends HttpServlet {

    // Método auxiliar para validação no servidor
    private String validarDados(String nome, String email, String cpf, String senha) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome não pode estar vazio.";
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return "Email inválido.";
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            return "CPF não pode estar vazio.";
        }
        if (senha == null || senha.trim().isEmpty()) {
            return "Senha não pode estar vazia.";
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
                    String msgErroId = validarId(idExcluir);
                    if (!msgErroId.isEmpty()) {
                        request.setAttribute("msg", msgErroId);
                        ArrayList<Professor> listaExc = dao.getAll();
                        request.setAttribute("listaProfessores", listaExc);
                        rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    dao.delete(idExcluir);
                    request.setAttribute("msg", "Professor excluído com sucesso!");
                    ArrayList<Professor> listaPosExclusao = dao.getAll();
                    request.setAttribute("listaProfessores", listaPosExclusao);
                    rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                    rd.forward(request, response);
                    break;

                case "AlterarForm":
                    int idAlterar = Integer.parseInt(request.getParameter("id"));
                    msgErroId = validarId(idAlterar);
                    if (!msgErroId.isEmpty()) {
                        request.setAttribute("msg", msgErroId);
                        ArrayList<Professor> listaAlt = dao.getAll();
                        request.setAttribute("listaProfessores", listaAlt);
                        rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    Professor professorEdicao = dao.get(idAlterar);
                    if (professorEdicao.getId() != 0) {
                        request.setAttribute("professorEdicao", professorEdicao);
                        ArrayList<Professor> listaAlteracao = dao.getAll();
                        request.setAttribute("listaProfessores", listaAlteracao);
                        rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                        rd.forward(request, response);
                    } else {
                        response.sendRedirect("/aplicacaoMVC/admin/professores?acao=Listar");
                    }
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

                String msgErro = validarDados(nome, email, cpf, senha);
                if (!msgErro.isEmpty()) {
                    request.setAttribute("msg", msgErro);
                    ArrayList<Professor> listaProfessores = dao.getAll();
                    request.setAttribute("listaProfessores", listaProfessores);
                    rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                    rd.forward(request, response);
                    return;
                }

                Professor novoProfessor = new Professor(nome, email, cpf, senha);
                dao.insert(novoProfessor);
                request.setAttribute("msg", "Professor incluído com sucesso!");

                ArrayList<Professor> listaProfessores = dao.getAll();
                request.setAttribute("listaProfessores", listaProfessores);
                rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                rd.forward(request, response);

            } else if ("Alterar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String cpf = request.getParameter("cpf");
                String senha = request.getParameter("senha");

                String msgErroId = validarId(id);
                if (!msgErroId.isEmpty()) {
                    request.setAttribute("msg", msgErroId);
                    ArrayList<Professor> listaProfessores = dao.getAll();
                    request.setAttribute("listaProfessores", listaProfessores);
                    rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                    rd.forward(request, response);
                    return;
                }

                String msgErro = validarDados(nome, email, cpf, senha);
                if (!msgErro.isEmpty()) {
                    request.setAttribute("msg", msgErro);
                    Professor professorEdicao = new Professor(nome, email, cpf, senha);
                    professorEdicao.setId(id);
                    request.setAttribute("professorEdicao", professorEdicao);
                    ArrayList<Professor> listaProfessores = dao.getAll();
                    request.setAttribute("listaProfessores", listaProfessores);
                    rd = request.getRequestDispatcher("/views/admin/professor.jsp");
                    rd.forward(request, response);
                    return;
                }

                Professor professorAlterado = new Professor(nome, email, cpf, senha);
                professorAlterado.setId(id);
                dao.update(professorAlterado);
                request.setAttribute("msg", "Professor alterado com sucesso!");

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

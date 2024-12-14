/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author alexandre-colmenero
 */

import entidade.Disciplina;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DisciplinaDAO;

@WebServlet(name = "DisciplinaController", urlPatterns = {"/admin/disciplinas"})
public class DisciplinaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("administrador") == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        DisciplinaDAO dao = new DisciplinaDAO();
        RequestDispatcher rd;

        try {
            switch (acao) {
                case "Listar":
                    ArrayList<Disciplina> lista = dao.getAll();
                    request.setAttribute("listaDisciplinas", lista);
                    rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                    rd.forward(request, response);
                    break;

                case "Excluir":
                    int idExcluir = Integer.parseInt(request.getParameter("id"));
                    dao.delete(idExcluir);
                    request.setAttribute("msg", "Disciplina excluída com sucesso!");
                    ArrayList<Disciplina> listaPosExclusao = dao.getAll();
                    request.setAttribute("listaDisciplinas", listaPosExclusao);
                    rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                    rd.forward(request, response);
                    break;

                default:
                    response.sendRedirect("/aplicacaoMVC/admin/disciplinas?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no DisciplinaController: " + e.getMessage());
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
        DisciplinaDAO dao = new DisciplinaDAO();
        RequestDispatcher rd;

        try {
            if ("Incluir".equals(acao)) {
                String nome = request.getParameter("nome");
                String requisito = request.getParameter("requisito");
                String ementa = request.getParameter("ementa");
                String cargaHorariaStr = request.getParameter("carga_horaria");
                Integer cargaHoraria = null;
                if (cargaHorariaStr != null && !cargaHorariaStr.trim().isEmpty()) {
                    cargaHoraria = Integer.parseInt(cargaHorariaStr);
                }

                Disciplina novaDisciplina = new Disciplina(nome, requisito, ementa, cargaHoraria);
                dao.insert(novaDisciplina);
                request.setAttribute("msg", "Disciplina incluída com sucesso!");

                ArrayList<Disciplina> listaDisciplinas = dao.getAll();
                request.setAttribute("listaDisciplinas", listaDisciplinas);
                rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("/aplicacaoMVC/admin/disciplinas?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no DisciplinaController POST: " + e.getMessage());
        }
    }
}


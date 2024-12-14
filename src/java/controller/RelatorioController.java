/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author alexandre-colmenero
 */

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.TurmaDAO;
import model.Relatorio;

@WebServlet(name = "RelatorioController", urlPatterns = {"/admin/relatorio"})
public class RelatorioController extends HttpServlet {

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
            if ("Gerar".equals(acao)) {
                ArrayList<Relatorio> listaRelatorio = dao.getRelatorio();
                request.setAttribute("listaRelatorio", listaRelatorio);
                rd = request.getRequestDispatcher("/views/admin/relatorio.jsp");
                rd.forward(request, response);
            } else {
                // Se a ação não for "Gerar", redireciona
                response.sendRedirect("/aplicacaoMVC/admin/relatorio?acao=Gerar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no RelatorioController: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Não há POST previsto para relatório (somente GET)
        response.sendRedirect("/aplicacaoMVC/admin/relatorio?acao=Gerar");
    }
}

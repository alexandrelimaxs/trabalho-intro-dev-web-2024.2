/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author alexandre-colmenero
 */

import entidade.Administrador;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdministradorDAO;

@WebServlet(name = "AdministradorController", urlPatterns = {"/admin/administradores"})
public class AdministradorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("administrador") == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        AdministradorDAO dao = new AdministradorDAO();
        RequestDispatcher rd;

        try {
            switch (acao) {
                case "Listar":
                    ArrayList<Administrador> lista = dao.getAll();
                    request.setAttribute("listaAdministradores", lista);
                    rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                    rd.forward(request, response);
                    break;

                case "Excluir":
                    int idExcluir = Integer.parseInt(request.getParameter("id"));
                    dao.delete(idExcluir);
                    request.setAttribute("msg", "Administrador excluído com sucesso!");
                    ArrayList<Administrador> listaPosExclusao = dao.getAll();
                    request.setAttribute("listaAdministradores", listaPosExclusao);
                    rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                    rd.forward(request, response);
                    break;

                default:
                    response.sendRedirect("/aplicacaoMVC/admin/administradores?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no AdministradorController: " + e.getMessage());
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
        AdministradorDAO dao = new AdministradorDAO();
        RequestDispatcher rd;

        try {
            if ("Incluir".equals(acao)) {
                String nome = request.getParameter("nome");
                String cpf = request.getParameter("cpf");
                String endereco = request.getParameter("endereco");
                String senha = request.getParameter("senha");

                Administrador novoAdmin = new Administrador(nome, cpf, endereco, senha);
                dao.insert(novoAdmin);
                request.setAttribute("msg", "Administrador incluído com sucesso!");

                ArrayList<Administrador> listaAdmins = dao.getAll();
                request.setAttribute("listaAdministradores", listaAdmins);
                rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("/aplicacaoMVC/admin/administradores?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no AdministradorController POST: " + e.getMessage());
        }
    }
}


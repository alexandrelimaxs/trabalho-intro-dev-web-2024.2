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
import javax.servlet.http.*;
import model.AdministradorDAO;

@WebServlet(name = "AdministradorController", urlPatterns = {"/admin/administradores"})
public class AdministradorController extends HttpServlet {

    private String validarDados(String nome, String cpf, String endereco, String senha) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome não pode estar vazio.";
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            return "CPF não pode estar vazio.";
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            return "Endereço não pode estar vazio.";
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
                    String msgErroId = validarId(idExcluir);
                    if (!msgErroId.isEmpty()) {
                        request.setAttribute("msg", msgErroId);
                        ArrayList<Administrador> listaExc = dao.getAll();
                        request.setAttribute("listaAdministradores", listaExc);
                        rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    dao.delete(idExcluir);
                    request.setAttribute("msg", "Administrador excluído com sucesso!");
                    ArrayList<Administrador> listaPosExclusao = dao.getAll();
                    request.setAttribute("listaAdministradores", listaPosExclusao);
                    rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                    rd.forward(request, response);
                    break;

                case "AlterarForm":
                    int idAlterar = Integer.parseInt(request.getParameter("id"));
                    msgErroId = validarId(idAlterar);
                    if (!msgErroId.isEmpty()) {
                        request.setAttribute("msg", msgErroId);
                        ArrayList<Administrador> listaAlt = dao.getAll();
                        request.setAttribute("listaAdministradores", listaAlt);
                        rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    Administrador adminEdicao = dao.get(idAlterar);
                    if (adminEdicao.getId() != 0) {
                        request.setAttribute("adminEdicao", adminEdicao);
                        ArrayList<Administrador> listaAlteracao = dao.getAll();
                        request.setAttribute("listaAdministradores", listaAlteracao);
                        rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                        rd.forward(request, response);
                    } else {
                        response.sendRedirect("/aplicacaoMVC/admin/administradores?acao=Listar");
                    }
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

                String msgErro = validarDados(nome, cpf, endereco, senha);
                if (!msgErro.isEmpty()) {
                    request.setAttribute("msg", msgErro);
                    ArrayList<Administrador> listaAdmins = dao.getAll();
                    request.setAttribute("listaAdministradores", listaAdmins);
                    rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                    rd.forward(request, response);
                    return;
                }

                Administrador novoAdmin = new Administrador(nome, cpf, endereco, senha);
                dao.insert(novoAdmin);
                request.setAttribute("msg", "Administrador incluído com sucesso!");

                ArrayList<Administrador> listaAdmins = dao.getAll();
                request.setAttribute("listaAdministradores", listaAdmins);
                rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                rd.forward(request, response);

            } else if ("Alterar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nome = request.getParameter("nome");
                String cpf = request.getParameter("cpf");
                String endereco = request.getParameter("endereco");
                String senha = request.getParameter("senha");

                String msgErroId = validarId(id);
                if (!msgErroId.isEmpty()) {
                    request.setAttribute("msg", msgErroId);
                    ArrayList<Administrador> listaAdmins = dao.getAll();
                    request.setAttribute("listaAdministradores", listaAdmins);
                    rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                    rd.forward(request, response);
                    return;
                }

                String msgErro = validarDados(nome, cpf, endereco, senha);
                if (!msgErro.isEmpty()) {
                    request.setAttribute("msg", msgErro);
                    Administrador adminEdicao = new Administrador(nome, cpf, endereco, senha);
                    adminEdicao.setId(id);
                    request.setAttribute("adminEdicao", adminEdicao);
                    ArrayList<Administrador> listaAdmins = dao.getAll();
                    request.setAttribute("listaAdministradores", listaAdmins);
                    rd = request.getRequestDispatcher("/views/admin/administrador.jsp");
                    rd.forward(request, response);
                    return;
                }

                Administrador adminAlterado = new Administrador(nome, cpf, endereco, senha);
                adminAlterado.setId(id);
                dao.update(adminAlterado);
                request.setAttribute("msg", "Administrador alterado com sucesso!");

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
 

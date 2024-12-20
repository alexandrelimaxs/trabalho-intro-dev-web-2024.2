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
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.DisciplinaDAO;

@WebServlet(name = "DisciplinaController", urlPatterns = {"/admin/disciplinas"})
public class DisciplinaController extends HttpServlet {

    private String validarDados(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome da disciplina não pode estar vazio.";
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
                    String msgErroId = validarId(idExcluir);
                    if (!msgErroId.isEmpty()) {
                        request.setAttribute("msg", msgErroId);
                        ArrayList<Disciplina> listaExc = dao.getAll();
                        request.setAttribute("listaDisciplinas", listaExc);
                        rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    dao.delete(idExcluir);
                    request.setAttribute("msg", "Disciplina excluída com sucesso!");
                    ArrayList<Disciplina> listaPosExclusao = dao.getAll();
                    request.setAttribute("listaDisciplinas", listaPosExclusao);
                    rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                    rd.forward(request, response);
                    break;

                case "AlterarForm":
                    int idAlterar = Integer.parseInt(request.getParameter("id"));
                    msgErroId = validarId(idAlterar);
                    if (!msgErroId.isEmpty()) {
                        request.setAttribute("msg", msgErroId);
                        ArrayList<Disciplina> listaAlt = dao.getAll();
                        request.setAttribute("listaDisciplinas", listaAlt);
                        rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    Disciplina discEdicao = dao.get(idAlterar);
                    if (discEdicao.getId() != 0) {
                        request.setAttribute("discEdicao", discEdicao);
                        ArrayList<Disciplina> listaAlteracao = dao.getAll();
                        request.setAttribute("listaDisciplinas", listaAlteracao);
                        rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                        rd.forward(request, response);
                    } else {
                        response.sendRedirect("/aplicacaoMVC/admin/disciplinas?acao=Listar");
                    }
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
            String nome = request.getParameter("nome");
            String requisito = request.getParameter("requisito");
            String ementa = request.getParameter("ementa");
            String cargaHorariaStr = request.getParameter("carga_horaria");
            Integer cargaHoraria = null;
            if (cargaHorariaStr != null && !cargaHorariaStr.trim().isEmpty()) {
                cargaHoraria = Integer.parseInt(cargaHorariaStr);
                if (cargaHoraria < 0) {
                    request.setAttribute("msg", "Carga Horária não pode ser negativa.");
                    ArrayList<Disciplina> listaDisciplinas = dao.getAll();
                    request.setAttribute("listaDisciplinas", listaDisciplinas);
                    rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                    rd.forward(request, response);
                    return;
                }
            }

            if ("Incluir".equals(acao)) {
                String msgErro = validarDados(nome);
                if (!msgErro.isEmpty()) {
                    request.setAttribute("msg", msgErro);
                    ArrayList<Disciplina> listaDisciplinas = dao.getAll();
                    request.setAttribute("listaDisciplinas", listaDisciplinas);
                    rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                    rd.forward(request, response);
                    return;
                }

                Disciplina novaDisciplina = new Disciplina(nome, requisito, ementa, cargaHoraria);
                dao.insert(novaDisciplina);
                request.setAttribute("msg", "Disciplina incluída com sucesso!");
                ArrayList<Disciplina> listaDisciplinas = dao.getAll();
                request.setAttribute("listaDisciplinas", listaDisciplinas);
                rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                rd.forward(request, response);

            } else if ("Alterar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String msgErroId = validarId(id);
                if (!msgErroId.isEmpty()) {
                    request.setAttribute("msg", msgErroId);
                    ArrayList<Disciplina> listaDisciplinas = dao.getAll();
                    request.setAttribute("listaDisciplinas", listaDisciplinas);
                    rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                    rd.forward(request, response);
                    return;
                }

                String msgErro = validarDados(nome);
                if (!msgErro.isEmpty()) {
                    request.setAttribute("msg", msgErro);
                    Disciplina discEdicao = new Disciplina(nome, requisito, ementa, cargaHoraria);
                    discEdicao.setId(id);
                    request.setAttribute("discEdicao", discEdicao);
                    ArrayList<Disciplina> listaDisciplinas = dao.getAll();
                    request.setAttribute("listaDisciplinas", listaDisciplinas);
                    rd = request.getRequestDispatcher("/views/admin/disciplina.jsp");
                    rd.forward(request, response);
                    return;
                }

                Disciplina discAlterada = new Disciplina(nome, requisito, ementa, cargaHoraria);
                discAlterada.setId(id);
                dao.update(discAlterada);
                request.setAttribute("msg", "Disciplina alterada com sucesso!");
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
 

 
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author alexandre-colmenero
 */

import entidade.Aluno;
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
import model.AlunoDAO;

@WebServlet(name = "AlunoController", urlPatterns = {"/admin/AlunoController"})
public class AlunoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Antes de executar qualquer ação, verificar se o admin está logado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("administrador") == null) {
            // não logado, redirecionar para login
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        AlunoDAO dao = new AlunoDAO();
        RequestDispatcher rd;
        
        try {
            switch (acao) {
                case "Listar":
                    ArrayList<Aluno> lista = dao.getAll();
                    request.setAttribute("listaAlunos", lista);
                    rd = request.getRequestDispatcher("/views/admin/alunos.jsp");
                    rd.forward(request, response);
                    break;

                case "Excluir":
                    int idExcluir = Integer.parseInt(request.getParameter("id"));
                    dao.delete(idExcluir);
                    request.setAttribute("msg", "Aluno excluído com sucesso!");
                    // após excluir, listar novamente
                    ArrayList<Aluno> listaPosExclusao = dao.getAll();
                    request.setAttribute("listaAlunos", listaPosExclusao);
                    rd = request.getRequestDispatcher("/views/admin/alunos.jsp");
                    rd.forward(request, response);
                    break;

                default:
                    response.sendRedirect("/aplicacaoMVC/admin/alunos?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no AlunoController: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Também verifica se o admin está logado no POST
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("administrador") == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        AlunoDAO dao = new AlunoDAO();
        RequestDispatcher rd;

        try {
            if ("Incluir".equals(acao)) {
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String celular = request.getParameter("celular");
                String cpf = request.getParameter("cpf");
                String senha = request.getParameter("senha");
                String endereco = request.getParameter("endereco");
                String cidade = request.getParameter("cidade");
                String bairro = request.getParameter("bairro");
                String cep = request.getParameter("cep");
                
                Aluno novoAluno = new Aluno(nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
                dao.insert(novoAluno);
                request.setAttribute("msg", "Aluno incluído com sucesso!");

                // após incluir, listar novamente
                ArrayList<Aluno> listaAlunos = dao.getAll();
                request.setAttribute("listaAlunos", listaAlunos);
                rd = request.getRequestDispatcher("/views/admin/alunos.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("/aplicacaoMVC/admin/alunos?acao=Listar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro no AlunoController POST: " + e.getMessage());
        }
    }
}


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
 
     // Método auxiliar para validação no servidor
     private String validarDados(String nome, String email, String celular, String cpf, String senha) {
         // Aqui podemos fazer validações simples, como checar se estão vazios.
         // Pode adicionar regex para formatar corretamente CPF, Email, etc.
         
         if (nome == null || nome.trim().isEmpty()) {
             return "Nome não pode estar vazio.";
         }
         if (email == null || email.trim().isEmpty() || !email.contains("@")) {
             return "Email inválido.";
         }
         if (celular == null || celular.trim().isEmpty()) {
             return "Celular não pode estar vazio.";
         }
         if (cpf == null || cpf.trim().isEmpty()) {
             return "CPF não pode estar vazio.";
         }
         if (senha == null || senha.trim().isEmpty()) {
             return "Senha não pode estar vazia.";
         }
         
         return ""; // Vazio significa que passou na validação
     }
 
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         // Verifica se o admin está logado
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
 
                 case "AlterarForm":
                     int idAlterar = Integer.parseInt(request.getParameter("id"));
                     Aluno alunoParaAlterar = dao.get(idAlterar);
                     if (alunoParaAlterar.getId() != 0) {
                         // Encaminha o aluno para a JSP
                         request.setAttribute("alunoEdicao", alunoParaAlterar);
                         // Também lista todos para a tabela
                         ArrayList<Aluno> listaAlteracao = dao.getAll();
                         request.setAttribute("listaAlunos", listaAlteracao);
                         rd = request.getRequestDispatcher("/views/admin/alunos.jsp");
                         rd.forward(request, response);
                     } else {
                         response.sendRedirect("/aplicacaoMVC/admin/AlunoController?acao=Listar");
                     }
                     break;
 
                 default:
                     response.sendRedirect("/aplicacaoMVC/admin/AlunoController?acao=Listar");
             }
         } catch (Exception e) {
             throw new RuntimeException("Erro no AlunoController: " + e.getMessage());
         }
     }
 
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         // Verifica se o admin está logado no POST
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
                 
                 String msgErro = validarDados(nome, email, celular, cpf, senha);
                 if (!msgErro.isEmpty()) {
                     request.setAttribute("msg", msgErro);
                     // Lista os alunos já existentes
                     ArrayList<Aluno> listaAlunos = dao.getAll();
                     request.setAttribute("listaAlunos", listaAlunos);
                     rd = request.getRequestDispatcher("/views/admin/alunos.jsp");
                     rd.forward(request, response);
                     return;
                 }
 
                 Aluno novoAluno = new Aluno(nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
                 dao.insert(novoAluno);
                 request.setAttribute("msg", "Aluno incluído com sucesso!");
 
                 // após incluir, listar novamente
                 ArrayList<Aluno> listaAlunos = dao.getAll();
                 request.setAttribute("listaAlunos", listaAlunos);
                 rd = request.getRequestDispatcher("/views/admin/alunos.jsp");
                 rd.forward(request, response);
 
             } else if ("Alterar".equals(acao)) {
                 int id = Integer.parseInt(request.getParameter("id"));
                 String nome = request.getParameter("nome");
                 String email = request.getParameter("email");
                 String celular = request.getParameter("celular");
                 String cpf = request.getParameter("cpf");
                 String senha = request.getParameter("senha");
                 String endereco = request.getParameter("endereco");
                 String cidade = request.getParameter("cidade");
                 String bairro = request.getParameter("bairro");
                 String cep = request.getParameter("cep");
                 
                 String msgErro = validarDados(nome, email, celular, cpf, senha);
                 if (!msgErro.isEmpty()) {
                     request.setAttribute("msg", msgErro);
                     // Lista os alunos já existentes e mantém o formulário de edição
                     Aluno alunoEdicao = new Aluno(nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
                     alunoEdicao.setId(id);
                     request.setAttribute("alunoEdicao", alunoEdicao);
                     ArrayList<Aluno> listaAlunos = dao.getAll();
                     request.setAttribute("listaAlunos", listaAlunos);
                     rd = request.getRequestDispatcher("/views/admin/alunos.jsp");
                     rd.forward(request, response);
                     return;
                 }
                 
                 Aluno alunoAlterado = new Aluno(nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
                 alunoAlterado.setId(id);
                 dao.update(alunoAlterado);
                 request.setAttribute("msg", "Aluno alterado com sucesso!");
 
                 ArrayList<Aluno> listaAlunos = dao.getAll();
                 request.setAttribute("listaAlunos", listaAlunos);
                 rd = request.getRequestDispatcher("/views/admin/alunos.jsp");
                 rd.forward(request, response);
 
             } else {
                 response.sendRedirect("/aplicacaoMVC/admin/AlunoController?acao=Listar");
             }
         } catch (Exception e) {
             throw new RuntimeException("Erro no AlunoController POST: " + e.getMessage());
         }
     }
 }
 
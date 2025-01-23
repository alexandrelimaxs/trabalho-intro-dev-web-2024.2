package controller;

import entidade.Administrador;
import entidade.Aluno;
import entidade.Professor;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdministradorDAO;
import model.AlunoDAO;
import model.ProfessorDAO;

/**
 *
 * @author Leonardo
 */
@WebServlet(name = "AutenticaController", urlPatterns = {"/AutenticaController"})
public class AutenticaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        RequestDispatcher rd;
        String cpf_user = request.getParameter("cpf");
        String senha_user = request.getParameter("senha");
        String cargo = request.getParameter("cargo"); // <-- CAPTURA O CARGO
    
        // Valida campos básicos
        if (cpf_user.isEmpty() || senha_user.isEmpty()) {
            request.setAttribute("msgError", "Usuário e/ou senha incorreto");
            rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);
            return;
        }
    
        try {
            // Verifica qual cargo
            if ("administrador".equalsIgnoreCase(cargo)) {
                // Lógica já existente para administrador
                AdministradorDAO dao = new AdministradorDAO();
                Administrador admin = new Administrador(cpf_user, senha_user);
                Administrador adminObtido = dao.Logar(admin);
                
                // Checagem de aprovado e etc... (como já existe no seu código)
                if (adminObtido.getId() != 0 && "s".equalsIgnoreCase(adminObtido.getAprovado())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("administrador", adminObtido);
                    // Redireciona pro dashboard do admin
                    rd = request.getRequestDispatcher("/admin/dashboard");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("msgError", "Usuário não aprovado ou usuário/senha incorreto");
                    rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
                    rd.forward(request, response);
                }
            }
            else if ("aluno".equalsIgnoreCase(cargo)) {
                // LOGIN DO ALUNO -> RF8
                AlunoDAO dao = new AlunoDAO();
                Aluno aluno = new Aluno(cpf_user, senha_user);
                Aluno alunoObtido = dao.Logar(aluno);
    
                if (alunoObtido.getId() != 0) {
                    // Achou o aluno
                    HttpSession session = request.getSession();
                    session.setAttribute("aluno", alunoObtido);
    
                    // Redireciona para área do aluno (controller ou JSP de “home” do aluno)
                    rd = request.getRequestDispatcher("/aluno/AlunoAreaController?acao=home");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("msgError", "Aluno não encontrado ou senha incorreta");
                    rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
                    rd.forward(request, response);
                }
            }
            else if ("professor".equalsIgnoreCase(cargo)) {
                // LOGIN DO PROFESSOR (RF11, mas já antecipa essa estrutura)
                ProfessorDAO dao = new ProfessorDAO();
                Professor prof = new Professor(cpf_user, senha_user);
                Professor profObtido = dao.Logar(prof);
    
                if (profObtido.getId() != 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("professor", profObtido);
    
                    // Redireciona para uma área do professor
                    rd = request.getRequestDispatcher("/professor/ProfessorAreaController?acao=home");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("msgError", "Professor não encontrado ou senha incorreta");
                    rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
                    rd.forward(request, response);
                }
            }
            else {
                // Cargo inválido? Volta para login
                request.setAttribute("msgError", "Cargo inválido");
                rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
                rd.forward(request, response);
            }
    
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Falha na query para Logar");
        }
    }
}

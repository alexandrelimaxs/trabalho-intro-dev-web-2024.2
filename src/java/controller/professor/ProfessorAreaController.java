package controller.professor;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import entidade.Professor;
import model.TurmaDAO;
import model.ProfessorTurmaDTO;

@WebServlet(name = "ProfessorAreaController", urlPatterns = {"/professor/ProfessorAreaController"})
public class ProfessorAreaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica se professor está logado
        HttpSession session = request.getSession(false);
        Professor profLogado = (session != null) ? (Professor) session.getAttribute("professor") : null;
        if (profLogado == null) {
            // não logado, redirecionar para login
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "home":
                // Página inicial da área do professor (RF11 concluído)
                rd = request.getRequestDispatcher("/views/professor/home.jsp");
                rd.forward(request, response);
                break;

            case "listarNotas":
                // RF13: Listar as notas de todos os alunos inscritos nas turmas sob
                // responsabilidade do professor
                ArrayList<ProfessorTurmaDTO> listaTurmas = turmaDAO.getAllByProfessor(profLogado.getId());
                request.setAttribute("listaTurmas", listaTurmas);
                rd = request.getRequestDispatcher("/views/professor/listarNotas.jsp");
                rd.forward(request, response);
                break;

            case "editarNotaForm":
                // RF12 (parte 1): carregar os dados de uma turma/aluno para editar nota
                int turmaId = Integer.parseInt(request.getParameter("id"));
                ProfessorTurmaDTO turmaAluno = turmaDAO.getOneByProfessor(profLogado.getId(), turmaId);
                // Verifica se existe e pertence mesmo a esse professor
                if (turmaAluno == null) {
                    request.setAttribute("msg", "Turma/Aluno não encontrado ou não pertence a você.");
                    rd = request.getRequestDispatcher("/professor/ProfessorAreaController?acao=listarNotas");
                } else {
                    request.setAttribute("turmaAluno", turmaAluno);
                    rd = request.getRequestDispatcher("/views/professor/editarNota.jsp");
                }
                rd.forward(request, response);
                break;

            default:
                // Se não bater nenhum case, volte ao home
                response.sendRedirect("/professor/ProfessorAreaController?acao=home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica se professor está logado
        HttpSession session = request.getSession(false);
        Professor profLogado = (session != null) ? (Professor) session.getAttribute("professor") : null;
        if (profLogado == null) {
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "atualizarNota":
                // RF12 (parte 2): O professor lança/edita a nota do aluno
                int turmaId = Integer.parseInt(request.getParameter("id"));
                float nota = Float.parseFloat(request.getParameter("nota"));

                // Atualiza no BD
                turmaDAO.updateNota(turmaId, nota);

                // Mensagem e redireciona para listarNotas
                request.setAttribute("msg", "Nota atualizada com sucesso!");
                ArrayList<ProfessorTurmaDTO> listaTurmas = turmaDAO.getAllByProfessor(profLogado.getId());
                request.setAttribute("listaTurmas", listaTurmas);

                rd = request.getRequestDispatcher("/views/professor/listarNotas.jsp");
                rd.forward(request, response);
                break;

            default:
                // Se não bater nenhum case, volte ao home
                response.sendRedirect("/professor/ProfessorAreaController?acao=home");
        }
    }
}

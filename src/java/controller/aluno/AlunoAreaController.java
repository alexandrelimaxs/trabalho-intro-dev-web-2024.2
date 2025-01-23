package controller.aluno;

import entidade.Aluno;
import entidade.Turma;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.TurmaDAO;
import model.TurmaDisponivelDTO;
import model.TurmaHistoricoDTO;

@WebServlet(name = "AlunoAreaController", urlPatterns = { "/aluno/AlunoAreaController" })
public class AlunoAreaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verifica se existe session e se o "aluno" está logado
        HttpSession session = request.getSession(false);
        Aluno alunoLogado = (session != null) ? (Aluno) session.getAttribute("aluno") : null;

        if (alunoLogado == null) {
            // Aluno não logado, manda para login
            response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            return;
        }

        String acao = request.getParameter("acao");
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "home":
                // Apenas mostra home do aluno
                rd = request.getRequestDispatcher("/views/aluno/home.jsp");
                rd.forward(request, response);
                break;

            case "listarTurmas":
                // Listar todas as turmas e verificar vagas
                ArrayList<TurmaDisponivelDTO> lista = turmaDAO.getTurmasDisponiveis();
                request.setAttribute("turmasDTO", lista);
                rd = request.getRequestDispatcher("/views/aluno/listarTurmas.jsp");
                rd.forward(request, response);
                break;

            case "inscrever":
                // RF9: Inscrever aluno na turma
                inscreverEmTurma(request, response, alunoLogado, turmaDAO);
                break;

            case "historico":
                // RF10: Listar turmas em que o aluno está inscrito + suas notas
                ArrayList<TurmaHistoricoDTO> historico = turmaDAO.getHistoricoAluno(alunoLogado.getId());
                request.setAttribute("historico", historico);
                rd = request.getRequestDispatcher("/views/aluno/historico.jsp");
                rd.forward(request, response);
                break;

            default:
                // Se não achar a ação, manda pra home do aluno
                response.sendRedirect("/aluno/AlunoAreaController?acao=home");
        }
    }

    private void inscreverEmTurma(HttpServletRequest request, HttpServletResponse response,
            Aluno alunoLogado, TurmaDAO turmaDAO)
            throws ServletException, IOException {
        // Lê parâmetros
        int turmaId = Integer.parseInt(request.getParameter("turmaId"));

        // 1) Verifica se essa turma já está com 2 alunos inscritos (RN3: max=2)
        int quantidade = turmaDAO.countByCodigoTurma(turmaId);
        // Exemplo: countByCodigoTurma contaria quantos alunos estão nessa "códigoTurma"
        // ou ID.
        // Ajuste conforme sua estrutura (se vc quiser contar por "codigo_turma" ou ID
        // exato)

        if (quantidade >= 2) {
            // Já lotada
            request.setAttribute("msg", "Não há vagas nessa turma (máximo 2).");
            // Redireciona para a listagem de turmas
            RequestDispatcher rd = request.getRequestDispatcher("/aluno/AlunoAreaController?acao=listarTurmas");
            rd.forward(request, response);
            return;
        }

        // 2) Insere na tabela `turmas` esse novo registro: professor_id, disciplina_id,
        // ALUNO_ID=alunoLogado
        Turma turmaAInscrever = turmaDAO.get(turmaId);

        // Garante que a turma foi encontrada
        if (turmaAInscrever.getId() == 0) {
            request.setAttribute("msg", "Turma não encontrada.");
            RequestDispatcher rd = request.getRequestDispatcher("/aluno/AlunoAreaController?acao=listarTurmas");
            rd.forward(request, response);
            return;
        }

        // Se for preciso duplicar uma linha, ou se seu design já permitir, você pode:
        // - set no TurmaDAO um Insert com (professor_id, disciplina_id, ALUNO_ID =
        // AlunoLogado, etc.)
        // OU se a "turma" for 1:1, você pode modificar o approach.
        // Aqui vamos supor que iremos inserir nova "turma" com mesmo
        // professor/disciplina/codigo, mas
        // outro "aluno_id".

        Turma novaInscricao = new Turma();
        novaInscricao.setProfessorId(turmaAInscrever.getProfessorId());
        novaInscricao.setDisciplinaId(turmaAInscrever.getDisciplinaId());
        novaInscricao.setAlunoId(alunoLogado.getId());
        novaInscricao.setCodigoTurma(turmaAInscrever.getCodigoTurma());
        novaInscricao.setNota(0.0f); // Nota inicial 0

        turmaDAO.insert(novaInscricao);

        // Mensagem de sucesso e volta para a listagem
        request.setAttribute("msg", "Inscrição realizada com sucesso!");
        RequestDispatcher rd = request.getRequestDispatcher("/aluno/AlunoAreaController?acao=listarTurmas");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="entidade.Administrador,entidade.Aluno,entidade.Professor"%>

<%
    // Verifica quem está logado
    HttpSession sessao = request.getSession(false);
    Administrador adminLogado = null;
    Aluno alunoLogado = null;
    Professor profLogado = null;

    if (sessao != null) {
        adminLogado = (Administrador) sessao.getAttribute("administrador");
        alunoLogado = (Aluno) sessao.getAttribute("aluno");
        profLogado = (Professor) sessao.getAttribute("professor");
    }
%>

<!-- Navbar que ocupa 100% da largura (container-fluid), removendo link de Comentários -->
<nav class="navbar navbar-expand-lg navbar-light bg-light w-100">
    <div class="container-fluid">
        <a class="navbar-brand" href="/aplicacaoMVC/home">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon">≡</span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav me-auto">
                <%
                    // Se for administrador logado, mostra links de admin
                    if (adminLogado != null) {
                %>
                    <a class="nav-link" href="/aplicacaoMVC/admin/dashboard">Dashboard</a>
                    <a class="nav-link" href="/aplicacaoMVC/admin/AlunoController?acao=Listar">Alunos</a>
                    <a class="nav-link" href="/aplicacaoMVC/admin/professores?acao=Listar">Professores</a>
                    <a class="nav-link" href="/aplicacaoMVC/admin/administradores?acao=Listar">Administradores</a>
                    <a class="nav-link" href="/aplicacaoMVC/admin/turmas?acao=Listar">Turmas</a>
                    <a class="nav-link" href="/aplicacaoMVC/admin/disciplinas?acao=Listar">Disciplinas</a>
                    <a class="nav-link" href="/aplicacaoMVC/admin/relatorio?acao=Gerar">Relatório</a>

                <%
                    // Se for aluno logado, mostra links de aluno
                    } else if (alunoLogado != null) {
                %>
                    <a class="nav-link" href="/aplicacaoMVC/aluno/AlunoAreaController?acao=home">Home Aluno</a>
                    <a class="nav-link" href="/aplicacaoMVC/aluno/AlunoAreaController?acao=listarTurmas">Listar Turmas</a>
                    <a class="nav-link" href="/aplicacaoMVC/aluno/AlunoAreaController?acao=historico">Histórico</a>

                <%
                    // Se for professor logado, mostra links de professor (caso ainda vá implementar)
                    } else if (profLogado != null) {
                %>
                    <a class="nav-link" href="/aplicacaoMVC/professor/ProfessorAreaController?acao=home">Home Professor</a>
                    <a class="nav-link" href="/aplicacaoMVC/professor/ProfessorAreaController?acao=listarNotas">Minhas Notas/Turmas</a>

                <%
                    // Senão, ninguém logado => exibe somente o link de login
                    } else {
                %>
                    <a class="nav-link" href="/aplicacaoMVC/AutenticaController?acao=Login">Login</a>
                <%
                    }
                %>
            </div>
            <div class="navbar-nav">
                <%
                    // Se QUALQUER um estiver logado (admin/aluno/prof), exibir logout
                    if (adminLogado != null || alunoLogado != null || profLogado != null) {
                %>
                    <a class="nav-link" href="/aplicacaoMVC/admin/logOut">Logout</a>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</nav>

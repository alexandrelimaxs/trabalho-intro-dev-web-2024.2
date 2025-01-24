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

    String homeLink;
    if (adminLogado != null) {
        homeLink = request.getContextPath() + "/admin/dashboard"; 
    } else if (alunoLogado != null) {
        homeLink = request.getContextPath() + "/aluno/AlunoAreaController?acao=home"; 
    } else if (profLogado != null) {
        homeLink = request.getContextPath() + "/professor/ProfessorAreaController?acao=home"; 
    } else {
        // Ninguém logado => redirecionar para home pública
        homeLink = request.getContextPath() + "/home?acao=home";
    }
%>

<!-- Navbar que ocupa 100% da largura (container-fluid), removendo link de Comentários -->
<nav class="navbar navbar-expand-lg navbar-light bg-light w-100">
    <div class="container-fluid">

        <!-- (1) ÚNICO link de Home -> o destino muda dependendo do cargo -->
        <a class="navbar-brand" href="<%= homeLink %>">Home</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon">≡</span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav me-auto">
                <%
                    // (2) Tira o link "Home Aluno"/"Home Professor" e mantém só as demais
                    if (adminLogado != null) {
                %>
                    <a class="nav-link" href="<%= request.getContextPath() %>/admin/AlunoController?acao=Listar">Alunos</a>
                    <a class="nav-link" href="<%= request.getContextPath() %>/admin/professores?acao=Listar">Professores</a>
                    <a class="nav-link" href="<%= request.getContextPath() %>/admin/administradores?acao=Listar">Administradores</a>
                    <a class="nav-link" href="<%= request.getContextPath() %>/admin/turmas?acao=Listar">Turmas</a>
                    <a class="nav-link" href="<%= request.getContextPath() %>/admin/disciplinas?acao=Listar">Disciplinas</a>
                    <a class="nav-link" href="<%= request.getContextPath() %>/admin/relatorio?acao=Gerar">Relatório</a>

                <%
                    } else if (alunoLogado != null) {
                %>
                    <a class="nav-link" href="<%= request.getContextPath() %>/aluno/AlunoAreaController?acao=listarTurmas">Listar Turmas</a>
                    <a class="nav-link" href="<%= request.getContextPath() %>/aluno/AlunoAreaController?acao=historico">Histórico</a>

                <%
                    } else if (profLogado != null) {
                %>
                    <a class="nav-link" href="<%= request.getContextPath() %>/professor/ProfessorAreaController?acao=listarNotas">Minhas Notas/Turmas</a>

                <%
                    } else {
                %>
                    <a class="nav-link" href="<%= request.getContextPath() %>/AutenticaController?acao=Login">Login</a>
                <%
                    }
                %>
            </div>
            <div class="navbar-nav">
                <%
                    // Se QUALQUER um estiver logado (admin/aluno/prof), exibir logout
                    if (adminLogado != null || alunoLogado != null || profLogado != null) {
                %>
                    <a class="nav-link" href="<%= request.getContextPath() %>/logOut">Logout</a>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</nav>


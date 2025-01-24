<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@page import="model.ProfessorTurmaDTO" %>
        <!DOCTYPE html>
        <html lang="pt-br">

        <head>
            <meta charset="UTF-8">
            <title>Editar Nota</title>
            <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>
            <jsp:include page="../comum/menu.jsp" />
            <div class="container">

                <h3 class="mt-4">Editar Nota do Aluno</h3>

                <% ProfessorTurmaDTO turmaAluno=(ProfessorTurmaDTO) request.getAttribute("turmaAluno"); if
                    (turmaAluno==null) { %>
                    <div class="alert alert-danger" role="alert">
                        Não foi possível encontrar os dados desta turma/aluno.
                    </div>
                    <% } else { %>
                        <form action="/aplicacaoMVC/professor/ProfessorAreaController" method="POST" class="mt-3">
                            <input type="hidden" name="acao" value="atualizarNota">
                            <input type="hidden" name="id" value="<%= turmaAluno.getTurmaId() %>">

                            <div class="mb-3">
                                <label class="form-label">Código da Turma</label>
                                <input type="text" class="form-control" value="<%= turmaAluno.getCodigoTurma() %>"
                                    readonly>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Disciplina</label>
                                <input type="text" class="form-control" value="<%= turmaAluno.getNomeDisciplina() %>"
                                    readonly>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Aluno</label>
                                <input type="text" class="form-control" value="<%= turmaAluno.getNomeAluno() %>"
                                    readonly>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Nota</label>
                                <input type="number" step="0.01" name="nota" class="form-control"
                                    value="<%= turmaAluno.getNota() %>" min="0" max="10">
                            </div>

                            <button type="submit" class="btn btn-primary">Salvar Nota</button>
                            <a href="/aplicacaoMVC/professor/ProfessorAreaController?acao=listarNotas"
                                class="btn btn-secondary">Cancelar</a>
                        </form>
                        <% } %>
            </div>

            <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
        </body>

        </html>
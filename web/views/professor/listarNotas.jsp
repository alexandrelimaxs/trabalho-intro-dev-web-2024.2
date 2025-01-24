<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@page import="java.util.ArrayList" %>
        <%@page import="model.ProfessorTurmaDTO" %>
            <!DOCTYPE html>
            <html lang="pt-br">

            <head>
                <meta charset="UTF-8">
                <title>Minhas Turmas - Notas dos Alunos</title>
                <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <jsp:include page="../comum/menu.jsp" />
                <div class="container">

                    <h3 class="mt-4">Notas de Alunos em Minhas Turmas</h3>

                    <% String msg=(String) request.getAttribute("msg"); if (msg !=null && !msg.isEmpty()) { %>
                        <div class="alert alert-info" role="alert">
                            <%= msg %>
                        </div>
                        <% } %>

                            <% ArrayList<ProfessorTurmaDTO> listaTurmas = (ArrayList<ProfessorTurmaDTO>)
                                    request.getAttribute("listaTurmas");
                                    if (listaTurmas == null) {
                                    listaTurmas = new ArrayList<>();
                                        }
                                        %>

                                        <table class="table table-bordered table-hover mt-3">
                                            <thead>
                                                <tr>
                                                    <th>Código Turma</th>
                                                    <th>Disciplina</th>
                                                    <th>Aluno</th>
                                                    <th>Nota</th>
                                                    <th>Ações</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% for (ProfessorTurmaDTO dto : listaTurmas) { %>
                                                    <tr>
                                                        <td>
                                                            <%= dto.getCodigoTurma() %>
                                                        </td>
                                                        <td>
                                                            <%= dto.getNomeDisciplina() %>
                                                        </td>
                                                        <td>
                                                            <%= dto.getNomeAluno() %>
                                                        </td>
                                                        <td>
                                                            <%= dto.getNota() %>
                                                        </td>
                                                        <td>
                                                            <!-- RF12: botão para editar a nota -->
                                                            <a href="/aplicacaoMVC/professor/ProfessorAreaController?acao=editarNotaForm&id=<%= dto.getTurmaId() %>"
                                                                class="btn btn-warning btn-sm">Editar Nota</a>
                                                        </td>
                                                    </tr>
                                                    <% } %>
                                            </tbody>
                                        </table>
                </div>

                <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
            </body>

            </html>
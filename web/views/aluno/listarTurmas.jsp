<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@page import="java.util.ArrayList" %>
        <%@page import="model.TurmaDisponivelDTO" %>
            <!DOCTYPE html>
            <html lang="pt-br">

            <head>
                <meta charset="UTF-8">
                <title>Turmas Disponíveis</title>
                <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <jsp:include page="../comum/menu.jsp" />
                <div class="container">

                    <h3 class="mt-4">Turmas Disponíveis</h3>

                    <% String msg=(String) request.getAttribute("msg"); if (msg !=null && !msg.isEmpty()) { %>
                        <div class="alert alert-info" role="alert">
                            <%= msg %>
                        </div>
                        <% } %>

                            <% ArrayList<TurmaDisponivelDTO> turmasDTO = (ArrayList<TurmaDisponivelDTO>)
                                    request.getAttribute("turmasDTO");
                                    if (turmasDTO == null) turmasDTO = new ArrayList<>();
                                        %>

                                        <table class="table table-bordered table-hover mt-3">
                                            <thead>
                                                <tr>
                                                    <th>Código da Turma</th>
                                                    <th>Disciplina</th>
                                                    <th>Professor</th>
                                                    <th>Ações</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% for (TurmaDisponivelDTO dto : turmasDTO) { %>
                                                    <tr>
                                                        <td>
                                                            <%= dto.getCodigoTurma() %>
                                                        </td>
                                                        <td>
                                                            <%= dto.getNomeDisciplina() %>
                                                        </td>
                                                        <td>
                                                            <%= dto.getNomeProfessor() %>
                                                        </td>
                                                        <td>
                                                            <!-- Botão de inscrição (RF9) -->
                                                            <a href="/aplicacaoMVC/aluno/AlunoAreaController?acao=inscrever&turmaId=<%= dto.getIdTurma() %>"
                                                                class="btn btn-success btn-sm">Inscrever</a>
                                                        </td>
                                                    </tr>
                                                    <% } %>
                                            </tbody>
                                        </table>
                </div>
                <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
            </body>

            </html>
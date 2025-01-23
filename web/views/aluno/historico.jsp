<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@page import="java.util.ArrayList" %>
        <%@page import="model.TurmaHistoricoDTO" %>
            <!DOCTYPE html>
            <html lang="pt-br">

            <head>
                <meta charset="UTF-8">
                <title>Meu Histórico</title>
                <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <jsp:include page="../comum/menu.jsp" />
                <div class="container">

                    <h3 class="mt-4">Meu Histórico de Notas</h3>

                    <% String msg=(String) request.getAttribute("msg"); if (msg !=null && !msg.isEmpty()) { %>
                        <div class="alert alert-info" role="alert">
                            <%= msg %>
                        </div>
                        <% } %>

                            <% ArrayList<TurmaHistoricoDTO> lista = (ArrayList<TurmaHistoricoDTO>)
                                    request.getAttribute("historico");
                                    if (lista == null) lista = new ArrayList<>();
                                        %>

                                        <table class="table table-bordered table-hover mt-3">
                                            <thead>
                                                <tr>
                                                    <th>Código Turma</th>
                                                    <th>Disciplina</th>
                                                    <th>Professor</th>
                                                    <th>Nota</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% for (TurmaHistoricoDTO dto : lista) { %>
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
                                                            <%= dto.getNota() %>
                                                        </td>
                                                    </tr>
                                                    <% } %>
                                            </tbody>
                                        </table>
                </div>
                <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
            </body>

            </html>
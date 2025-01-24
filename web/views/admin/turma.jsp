<%-- 
    Document   : turma
    Created on : 14 de dez. de 2024, 20:24:35
    Author     : alexandre-colmenero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Gerenciar Turmas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
    </head>
    <body>
        <jsp:include page="../comum/menu.jsp" />
        <div class="container">
            <h3 class="mt-5">Gerenciamento de Turmas</h3>

            <% String msg = (String) request.getAttribute("msg");
               if (msg != null && !msg.isEmpty()) { %>
               <div class="alert alert-info" role="alert">
                   <%= msg %>
               </div>
            <% } %>

            <% Turma turmaEdicao = (Turma) request.getAttribute("turmaEdicao"); %>

            <form action="/aplicacaoMVC/admin/turmas" method="POST" class="mb-4">
                <input type="hidden" name="acao" value="<%= (turmaEdicao != null) ? "Alterar" : "Incluir" %>">
                <% if (turmaEdicao != null) { %>
                    <input type="hidden" name="id" value="<%= turmaEdicao.getId() %>">
                <% } %>

                <div class="row">
                    <div class="col-sm-2">
                        <input type="number" name="professor_id" class="form-control" placeholder="Professor ID" required min="0"
                               value="<%= (turmaEdicao != null) ? turmaEdicao.getProfessorId() : "" %>">
                    </div>
                    <div class="col-sm-2">
                        <input type="number" name="disciplina_id" class="form-control" placeholder="Disciplina ID" required min="0"
                               value="<%= (turmaEdicao != null) ? turmaEdicao.getDisciplinaId() : "" %>">
                    </div>
                    <div class="col-sm-2">
                        <input type="number" name="aluno_id" class="form-control" placeholder="Aluno ID" required min="0"
                               value="<%= (turmaEdicao != null) ? turmaEdicao.getAlunoId() : "" %>">
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="codigo_turma" class="form-control" placeholder="Código Turma" required
                               value="<%= (turmaEdicao != null) ? turmaEdicao.getCodigoTurma() : "" %>">
                    </div>
                    <div class="col-sm-2">
                        <input type="number" step="0.01" name="nota" class="form-control" placeholder="Nota" required min="0"
                               value="<%= (turmaEdicao != null) ? turmaEdicao.getNota() : "" %>">
                    </div>
                    <div class="col-sm-2">
                        <input type="submit"
                               value="<%= (turmaEdicao != null) ? "Alterar Turma" : "Incluir Turma" %>"
                               class="btn btn-primary">
                    </div>
                </div>
            </form>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Professor ID</th>
                        <th>Disciplina ID</th>
                        <th>Aluno ID</th>
                        <th>Código Turma</th>
                        <th>Nota</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Turma> listaTurmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");
                        if (listaTurmas != null) {
                            for (Turma t : listaTurmas) {
                    %>
                    <tr>
                        <td><%= t.getId() %></td>
                        <td><%= t.getProfessorId() %></td>
                        <td><%= t.getDisciplinaId() %></td>
                        <td><%= t.getAlunoId() %></td>
                        <td><%= t.getCodigoTurma() %></td>
                        <td><%= t.getNota() %></td>
                        <td>
                            <a href="/aplicacaoMVC/admin/turmas?acao=Excluir&id=<%= t.getId() %>" class="btn btn-danger btn-sm">Excluir</a>
                            <a href="/aplicacaoMVC/admin/turmas?acao=AlterarForm&id=<%= t.getId() %>" class="btn btn-warning btn-sm">Alterar</a>
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>

        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>

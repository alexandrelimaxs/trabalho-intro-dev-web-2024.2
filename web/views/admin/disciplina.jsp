<%-- 
    Document   : disciplina
    Created on : 14 de dez. de 2024, 20:23:42
    Author     : alexandre-colmenero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Disciplina"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Gerenciar Disciplinas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
    </head>
    <body>
        <jsp:include page="../comum/menu.jsp" />
        <div class="container">
            <h3 class="mt-5">Gerenciamento de Disciplinas</h3>

            <% String msg = (String) request.getAttribute("msg");
               if (msg != null && !msg.isEmpty()) { %>
               <div class="alert alert-info" role="alert">
                   <%= msg %>
               </div>
            <% } %>

            <% Disciplina discEdicao = (Disciplina) request.getAttribute("discEdicao"); %>

            <form action="/aplicacaoMVC/admin/disciplinas" method="POST" class="mb-4">
                <input type="hidden" name="acao" value="<%= (discEdicao != null) ? "Alterar" : "Incluir" %>">
                <% if (discEdicao != null) { %>
                    <input type="hidden" name="id" value="<%= discEdicao.getId() %>">
                <% } %>

                <div class="row">
                    <div class="col-sm-3">
                        <input type="text" name="nome" class="form-control" placeholder="Nome" required
                               value="<%= (discEdicao != null) ? discEdicao.getNome() : "" %>">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="requisito" class="form-control" placeholder="Requisito"
                               value="<%= (discEdicao != null) ? discEdicao.getRequisito() : "" %>">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="ementa" class="form-control" placeholder="Ementa"
                               value="<%= (discEdicao != null) ? discEdicao.getEmenta() : "" %>">
                    </div>
                    <div class="col-sm-3">
                        <input type="number" name="carga_horaria" class="form-control" placeholder="Carga Horária (opcional)" min="0"
                               value="<%= (discEdicao != null && discEdicao.getCargaHoraria() != null) ? discEdicao.getCargaHoraria() : "" %>">
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-sm-3">
                        <input type="submit"
                               value="<%= (discEdicao != null) ? "Alterar Disciplina" : "Incluir Disciplina" %>"
                               class="btn btn-primary">
                    </div>
                </div>
            </form>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Requisito</th>
                        <th>Ementa</th>
                        <th>Carga Horária</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Disciplina> listaDisciplinas = (ArrayList<Disciplina>) request.getAttribute("listaDisciplinas");
                        if (listaDisciplinas != null) {
                            for (Disciplina d : listaDisciplinas) {
                    %>
                    <tr>
                        <td><%= d.getId() %></td>
                        <td><%= d.getNome() %></td>
                        <td><%= d.getRequisito() %></td>
                        <td><%= d.getEmenta() %></td>
                        <td><%= d.getCargaHoraria() != null ? d.getCargaHoraria() : "" %></td>
                        <td>
                            <a href="/aplicacaoMVC/admin/disciplinas?acao=Excluir&id=<%= d.getId() %>" class="btn btn-danger btn-sm">Excluir</a>
                            <a href="/aplicacaoMVC/admin/disciplinas?acao=AlterarForm&id=<%= d.getId() %>" class="btn btn-warning btn-sm">Alterar</a>
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


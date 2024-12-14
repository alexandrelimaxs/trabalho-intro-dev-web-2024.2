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
        <div class="container">
            <jsp:include page="../comum/menu.jsp" />
            <h3 class="mt-5">Gerenciamento de Disciplinas</h3>

            <% String msg = (String) request.getAttribute("msg");
               if (msg != null && !msg.isEmpty()) { %>
               <div class="alert alert-info" role="alert">
                   <%= msg %>
               </div>
            <% } %>

            <!-- Formulário para inclusão de nova disciplina -->
            <form action="/aplicacaoMVC/admin/disciplinas?acao=Incluir" method="POST" class="mb-4">
                <div class="row">
                    <div class="col-sm-3">
                        <input type="text" name="nome" class="form-control" placeholder="Nome" required>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="requisito" class="form-control" placeholder="Requisito">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="ementa" class="form-control" placeholder="Ementa">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="carga_horaria" class="form-control" placeholder="Carga Horária (opcional)">
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-sm-3">
                        <input type="submit" value="Incluir Disciplina" class="btn btn-primary">
                    </div>
                </div>
            </form>

            <!-- Listagem de disciplinas -->
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


<%-- 
    Document   : professor.jsp
    Created on : 14 de dez. de 2024, 20:18:39
    Author     : alexandre-colmenero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Professor"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Gerenciar Professores</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../comum/menu.jsp" />
            <h3 class="mt-5">Gerenciamento de Professores</h3>

            <% String msg = (String) request.getAttribute("msg");
               if (msg != null && !msg.isEmpty()) { %>
               <div class="alert alert-info" role="alert">
                   <%= msg %>
               </div>
            <% } %>

            <!-- Formulário para inclusão de novo professor -->
            <form action="/aplicacaoMVC/admin/professores?acao=Incluir" method="POST" class="mb-4">
                <div class="row">
                    <div class="col-sm-3">
                        <input type="text" name="nome" class="form-control" placeholder="Nome" required>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="email" class="form-control" placeholder="Email" required>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="cpf" class="form-control" placeholder="CPF" required>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="senha" class="form-control" placeholder="Senha" required>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-sm-3">
                        <input type="submit" value="Incluir Professor" class="btn btn-primary">
                    </div>
                </div>
            </form>

            <!-- Listagem de professores -->
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>CPF</th>
                        <th>Senha</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Professor> listaProfessores = (ArrayList<Professor>) request.getAttribute("listaProfessores");
                        if (listaProfessores != null) {
                            for (Professor p : listaProfessores) {
                    %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getNome() %></td>
                        <td><%= p.getEmail() %></td>
                        <td><%= p.getCpf() %></td>
                        <td><%= p.getSenha() %></td>
                        <td>
                            <a href="/aplicacaoMVC/admin/professores?acao=Excluir&id=<%= p.getId() %>" class="btn btn-danger btn-sm">Excluir</a>
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

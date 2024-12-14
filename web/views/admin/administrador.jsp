<%-- 
    Document   : administrador
    Created on : 14 de dez. de 2024, 20:22:25
    Author     : alexandre-colmenero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Administrador"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Gerenciar Administradores</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../comum/menu.jsp" />
            <h3 class="mt-5">Gerenciamento de Administradores</h3>

            <% String msg = (String) request.getAttribute("msg");
               if (msg != null && !msg.isEmpty()) { %>
               <div class="alert alert-info" role="alert">
                   <%= msg %>
               </div>
            <% } %>

            <!-- Formulário para inclusão de novo administrador -->
            <form action="/aplicacaoMVC/admin/administradores?acao=Incluir" method="POST" class="mb-4">
                <div class="row">
                    <div class="col-sm-3">
                        <input type="text" name="nome" class="form-control" placeholder="Nome" required>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="cpf" class="form-control" placeholder="CPF" required>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="endereco" class="form-control" placeholder="Endereço" required>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="senha" class="form-control" placeholder="Senha" required>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-sm-3">
                        <input type="submit" value="Incluir Administrador" class="btn btn-primary">
                    </div>
                </div>
            </form>

            <!-- Listagem de administradores -->
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>CPF</th>
                        <th>Endereço</th>
                        <th>Senha</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Administrador> listaAdmins = (ArrayList<Administrador>) request.getAttribute("listaAdministradores");
                        if (listaAdmins != null) {
                            for (Administrador a : listaAdmins) {
                    %>
                    <tr>
                        <td><%= a.getId() %></td>
                        <td><%= a.getNome() %></td>
                        <td><%= a.getCpf() %></td>
                        <td><%= a.getEndereco() %></td>
                        <td><%= a.getSenha() %></td>
                        <td>
                            <a href="/aplicacaoMVC/admin/administradores?acao=Excluir&id=<%= a.getId() %>" class="btn btn-danger btn-sm">Excluir</a>
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

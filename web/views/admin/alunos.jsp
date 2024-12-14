<%-- 
    Document   : alunos.jsp
    Created on : 14 de dez. de 2024, 20:01:05
    Author     : alexandre-colmenero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Aluno"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gerenciar Alunos</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../comum/menu.jsp" />
            <h3 class="mt-5">Gerenciamento de Alunos</h3>

            <!-- Mensagem de erro ou sucesso -->
            <% String msg = (String) request.getAttribute("msg");
               if (msg != null && !msg.isEmpty()) { %>
               <div class="alert alert-info" role="alert">
                   <%= msg %>
               </div>
            <% } %>

            <!-- Formulário para inclusão de novo aluno -->
            <form action="/aplicacaoMVC/admin/AlunoController?acao=Incluir" method="POST" class="mb-4">
                <div class="row">
                    <div class="col-sm-3">
                        <input type="text" name="nome" class="form-control" placeholder="Nome" required>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="email" class="form-control" placeholder="Email" required>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="celular" class="form-control" placeholder="Celular" required>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="cpf" class="form-control" placeholder="CPF" required>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="senha" class="form-control" placeholder="Senha" required>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-sm-3">
                        <input type="text" name="endereco" class="form-control" placeholder="Endereço">
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="cidade" class="form-control" placeholder="Cidade">
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="bairro" class="form-control" placeholder="Bairro">
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="cep" class="form-control" placeholder="CEP">
                    </div>
                    <div class="col-sm-3">
                        <input type="submit" value="Incluir Aluno" class="btn btn-primary">
                    </div>
                </div>
            </form>

            <!-- Listagem de alunos -->
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Celular</th>
                        <th>CPF</th>
                        <th>Endereço</th>
                        <th>Cidade</th>
                        <th>Bairro</th>
                        <th>CEP</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Aluno> listaAlunos = (ArrayList<Aluno>) request.getAttribute("listaAlunos");
                        if (listaAlunos != null) {
                            for (Aluno a : listaAlunos) {
                    %>
                    <tr>
                        <td><%= a.getId() %></td>
                        <td><%= a.getNome() %></td>
                        <td><%= a.getEmail() %></td>
                        <td><%= a.getCelular() %></td>
                        <td><%= a.getCpf() %></td>
                        <td><%= a.getEndereco() %></td>
                        <td><%= a.getCidade() %></td>
                        <td><%= a.getBairro() %></td>
                        <td><%= a.getCep() %></td>
                        <td>
                            <a href="/aplicacaoMVC/admin/AlunoController?acao=Excluir&id=<%= a.getId() %>" class="btn btn-danger btn-sm">Excluir</a>
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


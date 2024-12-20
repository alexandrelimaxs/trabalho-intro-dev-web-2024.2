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

            <% Aluno alunoEdicao = (Aluno) request.getAttribute("alunoEdicao"); %>

            <!-- Formulário para inclusão ou alteração de aluno -->
            <!-- Se alunoEdicao não for nulo, estamos editando, senão incluindo -->
            <form action="/aplicacaoMVC/admin/AlunoController" method="POST" class="mb-4">
                <input type="hidden" name="acao" value="<%= (alunoEdicao != null) ? "Alterar" : "Incluir" %>">
                <% if (alunoEdicao != null) { %>
                    <input type="hidden" name="id" value="<%= alunoEdicao.getId() %>">
                <% } %>

                <div class="row">
                    <div class="col-sm-3">
                        <input type="text" name="nome" class="form-control" placeholder="Nome" 
                               value="<%= (alunoEdicao != null) ? alunoEdicao.getNome() : "" %>" required>
                    </div>
                    <div class="col-sm-3">
                        <input type="email" name="email" class="form-control" placeholder="Email"
                               value="<%= (alunoEdicao != null) ? alunoEdicao.getEmail() : "" %>" required>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="celular" class="form-control" placeholder="Celular" 
                               value="<%= (alunoEdicao != null) ? alunoEdicao.getCelular() : "" %>" required>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="cpf" class="form-control" placeholder="CPF" 
                               value="<%= (alunoEdicao != null) ? alunoEdicao.getCpf() : "" %>" required>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="senha" class="form-control" placeholder="Senha"
                               value="<%= (alunoEdicao != null) ? alunoEdicao.getSenha() : "" %>" required>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-sm-3">
                        <input type="text" name="endereco" class="form-control" placeholder="Endereço"
                               value="<%= (alunoEdicao != null) ? alunoEdicao.getEndereco() : "" %>">
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="cidade" class="form-control" placeholder="Cidade"
                               value="<%= (alunoEdicao != null) ? alunoEdicao.getCidade() : "" %>">
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="bairro" class="form-control" placeholder="Bairro"
                               value="<%= (alunoEdicao != null) ? alunoEdicao.getBairro() : "" %>">
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="cep" class="form-control" placeholder="CEP"
                               value="<%= (alunoEdicao != null) ? alunoEdicao.getCep() : "" %>">
                    </div>
                    <div class="col-sm-3">
                        <input type="submit" value="<%= (alunoEdicao != null) ? "Alterar Aluno" : "Incluir Aluno" %>" class="btn btn-primary">
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
                            <a href="/aplicacaoMVC/admin/AlunoController?acao=AlterarForm&id=<%= a.getId() %>" class="btn btn-warning btn-sm">Alterar</a>
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

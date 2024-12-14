<%-- 
    Document   : relatorio.jsp
    Created on : 14 de dez. de 2024, 20:38:44
    Author     : alexandre-colmenero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Relatorio"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Relatório</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../comum/menu.jsp" />
            <h3 class="mt-5">Relatório de Disciplinas, Turmas e Alunos</h3>
            
            <!-- Tabela de relatório -->
            <table class="table table-bordered mt-3">
                <thead>
                    <tr>
                        <th>Disciplina</th>
                        <th>Professor</th>
                        <th>Aluno</th>
                        <th>CPF Aluno</th>
                        <th>Código da Turma</th>
                        <th>Nota</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Relatorio> listaRelatorio = (ArrayList<Relatorio>) request.getAttribute("listaRelatorio");
                        if (listaRelatorio != null && !listaRelatorio.isEmpty()) {
                            for (Relatorio dr : listaRelatorio) {
                    %>
                    <tr>
                        <td><%= dr.getNomeDisciplina() %></td>
                        <td><%= dr.getNomeProfessor() %></td>
                        <td><%= dr.getNomeAluno() %></td>
                        <td><%= dr.getCpfAluno() %></td>
                        <td><%= dr.getCodigoTurma() %></td>
                        <td><%= dr.getNota() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6">Nenhum dado encontrado</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>

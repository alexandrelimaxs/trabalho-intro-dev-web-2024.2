<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <title>Área do Aluno - Home</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <!-- Inclui um menu comum ou um menu exclusivo do aluno -->
            <jsp:include page="../comum/menu.jsp" />

            <h1 class="mt-5">Bem-vindo à Área do Aluno!</h1>
            <p>Aqui você pode se inscrever em disciplinas e verificar seu histórico de notas.</p>

            <ul class="list-group mt-4">
                <li class="list-group-item">
                    <a href="/aplicacaoMVC/aluno/AlunoAreaController?acao=listarTurmas">Listar Turmas Disponíveis</a>
                </li>
                <li class="list-group-item">
                    <a href="/aplicacaoMVC/aluno/AlunoAreaController?acao=historico">Visualizar meu Histórico</a>
                </li>
            </ul>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>

    </html>
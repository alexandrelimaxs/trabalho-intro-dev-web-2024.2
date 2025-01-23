<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Login</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="../comum/menu.jsp" />
        <div class="container d-flex flex-column justify-content-center align-items-center" style="height: 80vh;">
            <div class="card p-4 shadow-lg" style="width: 100%; max-width: 400px;">
                <h3 class="text-center mb-4">Login</h3>

                <%
                    String msgError = (String) request.getAttribute("msgError");
                    if ((msgError != null) && (!msgError.isEmpty())) { %>
                <div class="alert alert-danger text-center" role="alert">
                    <%= msgError %>
                </div>
                <% } %>

                <form action="/aplicacaoMVC/AutenticaController?acao=login" method="POST">
    
                    <!-- Adicione esse dropdown -->
                    <div class="mb-3">
                        <label for="cargo" class="form-label">Cargo</label>
                        <select name="cargo" class="form-select" id="cargo">
                            <option value="administrador" selected>Administrador</option>
                            <option value="aluno">Aluno</option>
                            <option value="professor">Professor</option>
                        </select>
                    </div>
                
                    <div class="mb-3">
                        <label for="cpf" class="form-label">CPF</label>
                        <input type="text" name="cpf" value="249.252.810-38" class="form-control" id="cpf">
                    </div>
                
                    <div class="mb-3">
                        <label for="senha" class="form-label">Senha</label>
                        <input type="password" name="senha" value="111" class="form-control" id="senha">
                    </div>
                    
                    <div class="d-flex justify-content-between align-items-center">
                        <input type="submit" value="Enviar" class="btn btn-primary">
                        <a href="/aplicacaoMVC/RegistrarController" class="text-decoration-none">Registre-se aqui</a>
                    </div>
                </form>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>

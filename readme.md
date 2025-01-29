# Documentação do Projeto – Aplicação MVC em Java (Servlet + JSP) -  Trabalho de Introdução ao Desenvolvimento Web - SI UFF - 2024.2

Este projeto foi desenvolvido como [trabalho final da matéria de Introdução ao Desenvolvimento Web](https://github.com/alexandrelimaxs/trabalho-intro-dev-web-2024.2/blob/9033b7e4d1f846be6ceacb3e351d9727d882a1d3/Trabalho%202024.2.pdf) com Leonardo Cruz, do curso de Sistemas de Informação UFF, com o objetivo de construir uma aplicação web para gerenciar uma **Escola**, contemplando as seguintes entidades: **Alunos**, **Professores**, **Administradores**, **Disciplinas**, **Turmas** e **Relatórios**. A arquitetura segue o padrão **MVC (Model–View–Controller)**, utilizando **Servlets** e **JSP** para a camada de controle e visualização, e o **MySQL** como banco de dados.

---

## 1. Visão Geral

A aplicação possui três tipos de usuários (stakeholders):

1. **Administrador**: Responsável por cadastrar e aprovar novos administradores, gerenciar alunos, professores, disciplinas, turmas e gerar relatórios.
2. **Aluno**: Pode se autenticar, visualizar disciplinas/turmas e efetuar inscrição nas turmas (respeitando as regras de vagas), além de visualizar notas (histórico).
3. **Professor**: Pode se autenticar, visualizar as turmas que leciona e lançar/alterar notas dos alunos.

Cada usuário acessa uma **área privada** após o login, com permissões específicas.

---

## 2. Estrutura de Pastas

Supondo um projeto NetBeans, temos a seguinte estrutura principal:

# Estrutura de Pastas - aplicacaoMVC

```plaintext
aplicacaoMVC/
├── src/
│   ├── java/
│   │   ├── controller/
│   │   │   ├── [Controllers .java]
│   │   │   ├── admin/
│   │   │   ├── aluno/
│   │   │   └── professor/
│   │   ├── entidade/
│   │   ├── model/
│   │   └── [outros arquivos .java]
│   └── [resources, etc.]
├── web/
│   ├── META-INF/
│   ├── WEB-INF/
│   ├── views/
│   │   ├── admin/
│   │   ├── aluno/
│   │   ├── professor/
│   │   ├── autenticacao/
│   │   ├── comum/
│   │   ├── registro/
│   │   └── ...
│   ├── index.jsp
│   └── [outros arquivos .jsp, css, bootstrap etc.]
└── pom.xml (caso seja Maven), ou build.xml (caso Ant)
```



### Principais Diretórios

- **controller/**: Armazena as classes Servlet (Controllers). Há subpastas `admin/`, `aluno/` e `professor/` para separar lógicas específicas de cada perfil.
- **entidade/**: Contém as classes de domínio (Entities): `Aluno`, `Administrador`, `Professor`, `Disciplina`, `Turma` etc.
- **model/**: Contém os DAOs (`AlunoDAO`, `ProfessorDAO`, `DisciplinaDAO`, etc.) responsáveis por acessar o banco de dados.
- **views/**: Pasta com as páginas JSP (subpastas para cada área, ex.: `admin/`, `aluno/`, `professor/` etc.), além de páginas comuns (`comum/`) como `menu.jsp`.

---

## 3. Banco de Dados

O projeto utiliza **MySQL** (banco `dbjava`) com tabelas padronizadas:

- **administrador** (`id`, `nome`, `cpf`, `senha`, `aprovado`, `endereco`)
- **alunos** (`id`, `nome`, `email`, `celular`, `cpf`, `senha`, `endereco`, `cidade`, `bairro`, `cep`)
- **professores** (`id`, `nome`, `email`, `cpf`, `senha`)
- **disciplina** (`id`, `nome`, `requisito`, `ementa`, `carga_horaria`)
- **turmas** (`id`, `professor_id`, `disciplina_id`, `aluno_id`, `codigo_turma`, `nota`)

As **FKs** ligam `turmas` com `professores`, `disciplina` e `alunos`. Assim, o projeto implementa:

- **Administrador** pré-cadastrado com `cpf="249.252.810-38"`, `senha="111"` e `aprovado="s"`.
- Tabelas de relação para gerenciar alunos, professores etc.

---

## 4. Camada Model

### 4.1. Entidades (pacote `entidade/`)

- **Administrador**  
  Atributos: `id, nome, cpf, endereco, senha, aprovado`.
- **Aluno**  
  Atributos: `id, nome, email, celular, cpf, senha, endereco, cidade, bairro, cep`.
- **Professor**  
  Atributos: `id, nome, email, cpf, senha`.
- **Disciplina**  
  Atributos: `id, nome, requisito, ementa, cargaHoraria`.
- **Turma**  
  Atributos: `id, professorId, disciplinaId, alunoId, codigoTurma, nota`.

Essas classes **espelham** a estrutura das tabelas no banco de dados.

### 4.2. DAOs (pacote `model/`)

Cada DAO (`AlunoDAO`, `ProfessorDAO`, `DisciplinaDAO`, etc.) implementa as operações **CRUD** (Create, Read, Update, Delete) no banco, além de métodos extras como `Logar(...)` para autenticação e geração de relatórios.

Exemplos:

- **AlunoDAO**  
  - `insert(Aluno a)`, `update(Aluno a)`, `delete(int id)`, `get(int id)`, `getAll()`, `Logar(...)`
- **ProfessorDAO**  
  - `insert(Professor p)`, `update(Professor p)`, `delete(int id)`, `get(int id)`, `getAll()`, `Logar(...)`
- **TurmaDAO**  
  - CRUD de `turmas`, métodos como `getRelatorio()`, `getAllByProfessor(...)`, `updateNota(...)`, etc.

Há também:

- **Conexao**: Responsável pela criação e fechamento de conexões com o MySQL.
- **Dao** (interface): Define métodos genéricos como `get(int id)`, `getAll()`, `insert(T t)`, `update(T t)`, `delete(int id)` para uniformizar os DAOs.

---

## 5. Camada Controller (Servlets)

As classes Servlet tratam o **fluxo** da aplicação, recebendo parâmetros (`request`) e repassando dados às DAOs, repassando resultados para as **Views**.

Principais Controllers:

- **AutenticaController**  
  Processa o **login** (RF1, RF8, RF11).  
  Verifica o *cargo* (administrador, aluno, professor) e redireciona para a área correspondente.
- **AdministradorController**  
  CRUD de administradores na área restrita.
- **AlunoController**  
  CRUD de alunos na área do administrador.
- **ProfessorController**  
  CRUD de professores na área do administrador.
- **DisciplinaController**  
  CRUD de disciplinas.
- **TurmaController**  
  CRUD de turmas.
- **RelatorioController**  
  Gera o relatório final para o administrador (RF7).
- **AlunoAreaController**  
  Funções do aluno: listar turmas, inscrever, ver histórico (RF9, RF10).
- **ProfessorAreaController**  
  Funções do professor: listar notas e editar notas (RF12, RF13).
- **LogoutController**  
  Encerra a sessão de qualquer usuário.

Dentro do pacote `controller.admin`, há também o `DashboardController` e o `CategoriaController` (caso o projeto inclua categorias), voltados para a parte administrativa.

---

## 6. Camada View (JSPs)

Organizadas em subpastas dentro de `web/views/`:

- **admin/**  
  `administrador.jsp`, `alunos.jsp`, `disciplina.jsp`, `professor.jsp`, `turma.jsp`, `relatorio.jsp`, etc., além de `dashboard/areaRestrita.jsp`.
- **aluno/**  
  `home.jsp`, `listarTurmas.jsp`, `historico.jsp`.
- **professor/**  
  `home.jsp`, `listarNotas.jsp`, `editarNota.jsp`.
- **autenticacao/**  
  `formLogin.jsp` (tela de login unificada).
- **comum/**  
  `menu.jsp` (barra de navegação superior), `showMessage.jsp` (layout de mensagens).

A navegação se dá chamando os **Servlets** (Controllers) via links do tipo:

/aplicacaoMVC/admin/AlunoController?acao=Listar /aplicacaoMVC/aluno/AlunoAreaController?acao=historico ...


ou usando `<jsp:include page="../comum/menu.jsp" />` para manter o layout padronizado.

---

## 7. Regras de Negócio Importantes

1. **RN3 (vagas=2)**: O Aluno só pode se inscrever em uma turma se houver vaga (máximo 2).  
2. **RN2 (não pode apagar inscrição após nota)**: Se a nota foi lançada (> 0), o aluno não pode mais remover a matrícula.  
3. **Administrador** precisa estar *aprovado* (`aprovado='s'`) para acessar.  
4. **Validações** no servidor e no cliente (HTML/JavaScript). Exemplos: nota 0–10, CPF não vazio etc.

---

## 8. Fluxo de Autenticação

1. O usuário acessa [`/AutenticaController`](#) via `formLogin.jsp`.
2. Preenche **CPF**, **senha** e **cargo** (Administrador, Aluno ou Professor).
3. O `AutenticaController` chama o DAO correspondente (`AdministradorDAO`, `AlunoDAO` ou `ProfessorDAO`) e verifica se existe.
4. Se bem-sucedido, cria sessão (`session.setAttribute(...)`) e redireciona para a dashboard do respectivo cargo:
   - `/admin/dashboard`, ou
   - `/aluno/AlunoAreaController`, ou
   - `/professor/ProfessorAreaController`.
5. Caso contrário, retorna mensagem de erro.

---

## 9. Como Executar

1. **Configurar** o MySQL com a base de dados `dbjava` e as tabelas definidas no script SQL fornecido (ver schema).
2. Ajustar a **classe** `Conexao.java` caso sua senha/usuário do banco sejam diferentes.
3. **Deploy** no Tomcat (ou outro container JavaEE) via NetBeans ou manualmente.
4. Acessar via navegador:

http://localhost:8080/aplicacaoMVC/home

- Entrar com **Administrador** padrão (`cpf="249.252.810-38"`, senha `"111"`) para gerenciar o sistema.
- Ou cadastrar e aprovar novos administradores, além de usuários Aluno/Professor.

---

## 10. Tecnologias Utilizadas

- **Java 8+** (Servlet/JSP)
- **MySQL** (Banco de Dados)
- **Tomcat 8.5+**
- **Bootstrap 5** (CSS/JS) para layout responsivo
- **NetBeans** (IDE) ou Eclipse para desenvolvimento

---

## 11. Conclusão

Este projeto demonstra uma **aplicação web MVC em Java** que atende a diversos **requisitos funcionais** (CRUD de entidades, login unificado, controle de vagas, lançamento de notas, relatórios etc.). Está organizado de forma a separar **model** (acesso ao banco), **controller** (Servlets) e **view** (JSPs), mantendo o código mais claro e modularizado.

Para mais detalhes de uso, consulte os comentários em cada DAO, Controller ou JSP.

![Thumb](https://github.com/raquelboaventura/colina-back/blob/master/img/Blue%20Yellow%20Modern%20Cube%20Book%20Store%20Logo%20(Twitch%20Banner).gif)

<p><img loading="lazy" src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge"/>
</p>

 <h1>COLINA API</h1>
<p>O Colina API é uma aplicação voltada para bibliotecas, que possibilita o cadastro de livros, alugueis de livros e clientes. A API permite o gerenciamento básico de uma biblioteca, facilitando o registro e organização das informações relacionadas aos livros e usuários.</p>
<h2>Índice</h2>
<ul>
        <li><a href="#introducao">1. Introdução</a></li>
        <!--<li><a href="#como-usar">2. Como Usar</a>
            <ul>
                <li><a href="#requisitos">2.1. Requisitos</a></li>
                <li><a href="#instalacao">2.2. Instalação</a></li>
                <li><a href="#exemplos-de-uso">2.3. Exemplos de Uso</a></li>
            </ul>
        </li>
        <li><a href="#documentacao">3. Documentação</a></li>
        <li><a href="#autenticacao">4. Autenticação</a></li>-->
        <li><a href="#endpoints">5. Endpoints</a></li>
        <!--<li><a href="#respostas-comuns">6. Respostas Comuns</a></li>
        <li><a href="#contribuicao">7. Contribuição</a></li>
        <li><a href="#licenca">8. Licença</a></li>-->
    </ul>
    <h2 id="introducao">1. Introdução</h2>

   <p>Principais Características:</p>
<ul>
  <li><strong>Cadastro de Livros: </strong>A API facilita o registro de novos livros na biblioteca, fornecendo campos específicos para detalhes como título, autor, gênero e ISBN.</li>
  <li><strong>Alugueis de Livros:</strong> Oferece a capacidade de gerenciar alugueis de livros, registrando informações sobre a data de retirada, data de devliução e cliente associado. (Em andamento)
  </li>
  <li><strong>Cadastro de Clientes:</strong> Permite o cadastro de clientes da biblioteca, incluindo informações como nome, endereço e histórico de alugueis.
  </li>
  <li><strong>Gerenciamento de registros:</strong> Proporciona uma estrutura para facilitar a busca, atualização e remoção de registros.
  </li>
  <li><strong>Segurança e Autenticação:</strong> possibilita a autenticação, garantindo que apenas usuários autorizados possam acessar e modificar informações. (Em andamento)
  </li>
</ul>

 <!-- <h2 id="como-usar">2. Como Usar</h2>

   <h3 id="requisitos">2.1. Requisitos</h3>
    <p>Lista de pré-requisitos necessários para usar a API, como linguagem de programação, versões de software, etc.</p>
    <h3 id="instalacao">2.2. Instalação</h3>
    <p>Instruções passo a passo sobre como instalar a API.</p>

   <h3 id="exemplos-de-uso">2.3. Exemplos de Uso</h3>
    <p>Exemplos práticos de como utilizar os principais recursos da API.</p>
    <h2 id="documentacao">3. Documentação</h2>
   <p>Link para a documentação completa da API, se estiver disponível online.</p>

   <h2 id="autenticacao">4. Autenticação</h2>
    <p>Explicação sobre como autenticar as requisições à API, se necessário.</p>-->
   
    
<h2 id="endpoints">5. Endpoints</h2>
<p>Lista dos endpoints disponíveis, junto com suas descrições e parâmetros.</p>
<table>
    <thead>
        <tr>
            <th>Método</th>
            <th>Endpoint</th>
            <th>Descrição</th>
            <th>Parâmetros</th>
            <th>Exemplo de Resposta</th>
        </tr>
    </thead>
    <tbody>
        <!-- Livros Endpoints -->
        <tr>
            <td>GET</td>
            <td>/livros/lista/all</td>
            <td>Obter todos os livros</td>
            <td>Nenhum</td>
            <td>{ "livros": [...] }</td>
        </tr>
        <tr>
            <td>GET</td>
            <td>/livros/atualiza/{id}</td>
            <td>Obter detalhes de um livro específico</td>
            <td>{ "id": "123" }</td>
            <td>{ "livro": { "id": "123", "titulo": "Título do Livro", ... } }</td>
        </tr>
        <tr>
            <td>POST</td>
            <td>/livros/cadastro</td>
            <td>Adicionar um novo livro</td>
            <td>{ "isbn": 0, "titulo": "Ensinando a transgredir: A educação como prática da liberdade", "autor": "bell hooks", "editora": "Editora WMF Martins Fontes", "paginas": 288, "publicacao": "2023-12-01 15:36:25", "preco": 49.90, "genero": "sociedade", "quantidade": 10 }</td>
            <td>{ "mensagem": "Livro adicionado com sucesso" }</td>
        </tr>
        <tr>
            <td>PUT</td>
            <td>/livros/atualiza/{id}</td>
            <td>Alterar detalhes de um livro específico</td>
            <td>{ "id": "123" }</td>
            <td>{ "livro": { "id": "123", "titulo": "Título do Livro", ... } }</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/livros/delete/{id}</td>
            <td>Excluir um livro específico</td>
            <td>{ "id": "123" }</td>
            <td>{ "mensagem": "Livro excluído com sucesso" }</td>
        </tr>
        <!-- Clientes Endpoints -->
        <tr>
            <td>GET</td>
            <td>/clientes/lista/all</td>
            <td>Obter todos os clientes</td>
            <td>Nenhum</td>
            <td>{ "clientes": [...] }</td>
        </tr>
        <tr>
            <td>GET</td>
            <td>/clientes/{id}</td>
            <td>Obter detalhes de um cliente específico</td>
            <td>{ "id": "123" }</td>
            <td>{ "cliente": { "id": "123", "nome": "João Silva", "cpf": 12345678901 } }</td>
        </tr>
        <tr>
            <td>POST</td>
            <td>/clientes/cadastro</td>
            <td>Adicionar um novo cliente</td>
            <td>{ "nome": "Maria Oliveira", "cpf": 98765432100 }</td>
            <td>{ "mensagem": "Cliente adicionado com sucesso" }</td>
        </tr>
        <tr>
            <td>PUT</td>
            <td>/clientes/atualiza/{id}</td>
            <td>Alterar detalhes de um cliente específico</td>
            <td>{ "id": "123", "nome": "Novo Nome", "cpf": 12345678901 }</td>
            <td>{ "cliente": { "id": "123", "nome": "Novo Nome", "cpf": 12345678901 } }</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/clientes/delete/{id}</td>
            <td>Excluir um cliente específico</td>
            <td>{ "id": "123" }</td>
            <td>{ "mensagem": "Cliente excluído com sucesso" }</td>
        </tr>
    </tbody>
</table>

 <!--  <h2 id="respostas-comuns">6. Respostas Comuns</h2>
    <p>Exemplos de respostas comuns que os usuários podem esperar ao interagir com a API.</p>
    <h2 id="contribuicao">7. Contribuição</h2>
    <p>Informações sobre como os desenvolvedores podem contribuir para o desenvolvimento da API.</p>
    <h2 id="licenca">8. Licença</h2>
    <p>Informações sobre a licença sob a qual a API está disponível.</p>--> 







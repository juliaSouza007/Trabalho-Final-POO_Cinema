# Trabalho Final POO - Cinema

Disciplina: Programação Orientada a Objetos Ano: 2024
Trabalho Prático Final - Cinema

Valor: 15 pontos

**Objetivo:**
- Aplicar os conceitos de polimorfismo, herança, sobrecarga, interfaces, modificadores de acesso, exceções, coleções e armazenamento secundário em Java para modelar um cinema simples.
**Enunciado:**
- Crie um programa em Java que simule um cinema. O cinema terá diferentes salas, cada sala com filmes em cartaz.

# Requisitos:
**1. Classes:**
* (ANA JULIA) Crie uma classe abstrata Cinema com os seguintes atributos e métodos:
* Atributos:
  id (int) – Identificador único para o cinema.
  nome (String) – Nome popular do cinema.
  local (String) – Endereço do cinema.
- Métodos básicos:
  criarSala(...) – Cria uma sala no cinema.
  listarSalas() – Lista as salas do cinema e suas informações.
  listarCinemas() – Lista todos os cinemas criados.

* (ANA JULIA) Crie classes concretas que herdem de Cinema: 
- O objetivo é trabalhar o conceito de herança, então crie cinemas que representem cinemas reais.
- Como cada classe representará um único cinema, não deve ser possível ter mais de uma instância da classe concreta.

* (HEITOR) Crie uma classe para representar uma sala de um cinema com os seguintes atributos e métodos:
- Atributos:
  nome (String) – Nome da sala, único no cinema.
  capacidade (int) – Capacidade da sala.
- Métodos básicos:
  criarSessao(Filme, DataHora) – Cria uma sessão para
  exibir um filme específico em determinado horário.
  listarSessoes() – Lista as sessões de uma sala.

* (HEITOR) Crie uma classe para representar um Filme com os seguintes atributos e métodos: 
- Atributos:
  id (int) – Identificador do filme.
  nome (String) – Nome do filme.
  duracao_s (long) – Duração do filme, em segundos.
- Métodos:
  Me surpreenda!!!

* (JÚLIA) Crie uma classe para representar uma sessão com os seguintes atributos e métodos: 
- Atributos:
  id (int) – Identificador da sessão.
  sala – Sala onde ocorrerá a sessão.
  filme – Filme em exibição.
  dataHora – Data e hora da sessão.
- Métodos:
  Me surpreenda!!!

* (JÚLIA) Crie uma classe para representar as vendas de ingressos. 
- Me surpreenda!!!
  
**2. Polimorfismo:**
o Sempre que for geral, deve-se utilizar Cinema. Contudo, crie itens específicos para cada cinema de forma a diferenciá-lo dos demais.

**3. Sobrecarga:**
o Crie métodos sobrecarregados com intuito de facilitar a vida do usuário.
- Exemplos:
  criarSessao(Filme) - cria uma sessão com horário de início 30 minutos depois do término do último filme em sessão na sala.
  criarSessao(Filme, Horario) – cria uma sessão para o filme em todos os dias da semana, sempre no mesmo horário.

**4. Modificadores de Acesso:**
* Utilize modificadores de acesso adequados para os atributos e métodos das classes.
* Utilize métodos get e set para acessar e modificar atributos privados.

**5. Interface:**
o Crie uma interface que representa cada entidade DAO e trabalhe sempre com essa interface.

**6. Exceções:**
- Crie exceções para cada operação que não possa ser concluída conforme o esperado.
- Exceções obrigatórias:
  SalaOcupadaException – Sempre que uma sessão tiver horário de início inferior ao término de outra sessão.
  IdExistenteException – Não devem existir dois cinemas com o mesmo identificador.
  NomeDuplicadoException – Não devem existir duas salas com o mesmo nome no mesmo cinema.

**7. Armazenamento Secundário:**
* Ao iniciar o sistema, carregue os dados do arquivo para memória.
* Ao finalizar o sistema, salve os dados da memória em arquivo.

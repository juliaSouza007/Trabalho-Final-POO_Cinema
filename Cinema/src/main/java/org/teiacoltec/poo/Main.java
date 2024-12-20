package org.teiacoltec.poo;

import org.teiacoltec.poo.Classes.*;
import org.teiacoltec.poo.ClassesDAO.*;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.List.*;

public class Main {

    public static void main(String[] args) throws FalhaConexaoException {
        Scanner scanner = new Scanner(System.in);

        // Cria uma instancia e adiciona cada cinema na lista de cinemas
        // isso é importante para a logica de carregar os cinemas do bd
       Cineart.obtemInstancia();
       Cinemark.obtemInstancia();
       Cinepolis.obtemInstancia();
       Cinesercla.obtemInstancia();

       // Insere alguns dados no bd
       // OBS: executar o programa com essa linha apenas uma vez para carregar os dados
       IniciaTabelas.obtemInstancia();

       // Carrega os cinemas do Banco de Dados
       Cinema.setListaCinemas(CinemaDAO.carregar(Cinema.obtemListaCinemas()));

       // Id para vendas e sesssao
        int idVendas = 0;
        int idSessao = 0;

        // Menu principal
        int opcao;
        do {
            System.out.println("\n<< MENU PRINCIPAL >>");
            System.out.println("1. Entrar como Administrador");
            System.out.println("2. Entrar como Usuário");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    // Menu Administrador
                    int opcaoAdmin;
                    do {
                        System.out.println("\n<< MENU ADMINISTRADOR >>");
                        System.out.println("1. Ver Cinemas");
                        System.out.println("2. Gerenciar Sessões");
                        System.out.println("3. Gerenciar Cinemas");
                        System.out.println("4. Acessar Vendas");
                        System.out.println("0. Voltar");
                        System.out.print("Escolha uma opção: ");
                        opcaoAdmin = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoAdmin) {
                            case 1:
                                // Ver Cinemas cadastrados
                                Cinema.listaCinemas();
                                break;
                            case 2:
                                // Gerenciar Sessões
                                int opcaoSessao;
                                do {
                                    System.out.println("\n*** Gerenciar Sessões ***");
                                    System.out.println("1. Adicionar Sessão");
                                    System.out.println("2. Listar Sessões");
                                    System.out.println("3. Atualizar Sessão");
                                    System.out.println("4. Deletar Sessão");
                                    System.out.println("5. Voltar");
                                    System.out.print("Escolha uma opção: ");
                                    opcaoSessao = scanner.nextInt();
                                    scanner.nextLine(); // Limpar o buffer

                                    switch (opcaoSessao) {
                                        case 1:
                                            System.out.println("------------------ Lista de Cinemas ------------------");
                                            for (Cinema cinema : Cinema.obtemListaCinemas()) {
                                                System.out.println("ID: " + cinema.getId() + ". " + cinema.getNome());
                                            }
                                            System.out.println("------------------------------------------------------");
                                            int c;
                                            System.out.println("Digite o ID do cinema em que quer adicionar a sessao: ");
                                            c = scanner.nextInt();
                                            scanner.nextLine();

                                            boolean achou = false;

                                            for (Cinema cinema : Cinema.obtemListaCinemas()) {
                                                if (cinema.getId() == c) {
                                                    achou = true;
                                                }
                                            }

                                            if (achou) {
                                                // Adicionar Sessão
                                                System.out.println("------------------ Salas do Cinema ------------------");
                                                for (Sala sala : Cinema.obtemListaCinemas().get(c).getSalas()) {
                                                    System.out.println("ID: " + sala.getId() + "\tNome: " + sala.getNome() + "\tCapacidade: " + sala.getCapacidade());
                                                }
                                                System.out.println("------------------------------------------------------");
                                                System.out.print("Digite o ID da sala: ");
                                                int idSala = scanner.nextInt();

                                                boolean achouSala = false;
                                                Sala salaSessao = null;
                                                for (Sala sala : Cinema.obtemListaCinemas().get(c).getSalas()) {
                                                    if (sala.getId() == idSala) {
                                                        salaSessao = sala;
                                                        achouSala = true;
                                                    }
                                                }

                                                if (achouSala) {
                                                    System.out.print("Digite o nome do filme: ");
                                                    String nomeFilme = scanner.nextLine();
                                                    scanner.nextLine();
                                                    System.out.print("Digite o ID do filme: ");
                                                    int idFilme = scanner.nextInt();
                                                    scanner.nextLine();
                                                    System.out.print("Digite a data da sessão (yyyy-mm-dd hh:mm:ss): ");
                                                    String dataSessao = scanner.nextLine();

                                                    // Adicionar lógica de criar uma sessão
                                                    Sessao novaSessao = new Sessao(idSessao, salaSessao, new org.teiacoltec.poo.Classes.Filmes(idFilme, 230, nomeFilme), java.sql.Timestamp.valueOf(dataSessao));
                                                    idSessao++;
                                                    new SessaoDAO().salvarSessao(novaSessao);
                                                    System.out.println("Sessão adicionada com sucesso!");

                                                } else {
                                                    System.out.println("Sala não encontrada.");
                                                }
                                            } else {
                                                System.out.println("Cinema não encontrado.");
                                            }
                                            break;
                                        case 2:
                                            // Listar Sessões
                                            List<Sessao> sessoes = new SessaoDAO().buscarTodasSessoes();
                                            for (Sessao sessao : sessoes) {
                                                System.out.println("Sessão ID: " + sessao.getId() + " | Sala: " + sessao.getSalaAssociada().getNome() + " | Filme: " + sessao.getFilmeExibido().getNome() + " | Data: " + sessao.getDataSessao());
                                            }
                                            break;
                                        case 3:
                                            break;
                                        case 4:
                                            // Deletar Sessão
                                            System.out.print("Digite o ID da sessão a ser deletada: ");
                                            int idSessaoDelete = scanner.nextInt();
                                            scanner.nextLine(); // Limpar o buffer
                                            Sessao sessaoDelete = new Sessao(idSessaoDelete, null, null, null);
                                            new SessaoDAO().deletarSessao(sessaoDelete);
                                            System.out.println("Sessão deletada com sucesso!");
                                            break;
                                        case 5:
                                            // Voltar ao menu principal
                                            System.out.println("Voltando ao menu principal...");
                                            break;
                                        default:
                                            System.out.println("Opção inválida, tente novamente.");
                                            break;
                                    }
                                } while (opcaoSessao != 5);
                                break;
                            case 3:
                                // Gerenciar Cinemas
                                int opcaoCinema;
                                do {
                                    System.out.println("\n*** Gerenciar Cinemas ***");
                                    System.out.println("1. Adicionar sala");
                                    System.out.println("2. Listar salas");
                                    System.out.println("3. Atualizar dados do cinema");
                                    System.out.println("4. Remover cinema");
                                    System.out.println("0. Voltar");
                                    System.out.println("------------------ Lista de Cinemas ------------------");
                                    for (Cinema cinema : Cinema.obtemListaCinemas()) {
                                        System.out.println("ID: " + cinema.getId() + ". " + cinema.getNome());
                                    }
                                    System.out.println("------------------------------------------------------");
                                    System.out.print("Escolha uma opção: ");
                                    opcaoCinema = scanner.nextInt();
                                    scanner.nextLine(); // Limpar o buffer

                                    if (opcao != 0) {
                                        int c;
                                        System.out.println("Digite o ID do cinema: ");
                                        c = scanner.nextInt();
                                        scanner.nextLine();

                                        boolean achou = false;

                                        for (Cinema cinema : Cinema.obtemListaCinemas()) {
                                            if (cinema.getId() == c) {
                                                achou = true;
                                            }
                                        }

                                        if (achou) {
                                            switch (opcaoCinema) {
                                                case 1:
                                                    System.out.println("Digite o nome da sala: ");
                                                    String nomeSala = scanner.nextLine();
                                                    scanner.nextLine();
                                                    System.out.println("Digite a capacidade da sala: ");
                                                    int capacidadeSala = scanner.nextInt();
                                                    scanner.nextLine();
                                                    try {
                                                        Cinema.obtemListaCinemas().get(c).criarSala(nomeSala, capacidadeSala);
                                                    } catch (LimiteSalasException e) {
                                                        System.out.println(e);
                                                    }
                                                    break;
                                                case 2:
                                                    Cinema.obtemListaCinemas().get(c).listarSalas();
                                                    break;
                                                case 3:
                                                    int escolha;
                                                    do {
                                                        System.out.println("\n*** Atulizar ***");
                                                        System.out.println("1. Nome");
                                                        System.out.println("2. Local");
                                                        System.out.println("0. Voltar");
                                                        System.out.print("Escolha uma opção: ");
                                                        escolha = scanner.nextInt();
                                                        scanner.nextLine(); // Limpar o buffer

                                                        switch (escolha) {
                                                            case 1:
                                                                System.out.println("Digite o novo nome do cinema: ");
                                                                String novoNome = scanner.nextLine();
                                                                scanner.nextLine();
                                                                Cinema.obtemListaCinemas().get(c).setNome(novoNome);
                                                                break;
                                                            case 2:
                                                                System.out.println("Digite o novo local do cinema: ");
                                                                String novoLocal = scanner.nextLine();
                                                                scanner.nextLine();
                                                                Cinema.obtemListaCinemas().get(c).setNome(novoLocal);
                                                                break;
                                                            case 0:
                                                                break;
                                                            default:
                                                                System.out.println("Opção inválida, tente novamente.");
                                                                break;
                                                        }
                                                    } while (escolha != 0);
                                                case 4:
                                                    try {
                                                        CinemaDAO.remove(Cinema.obtemListaCinemas().get(c));
                                                        Cinema.obtemListaCinemas().remove(c);
                                                        System.out.println("Cinema removido!");
                                                    } catch (CinemaNaoEncontradaException e) {
                                                        System.out.println(e);
                                                    }
                                                    break;
                                                case 0:
                                                    break;
                                                default:
                                                    System.out.println("Opção inválida, tente novamente.");
                                                    break;
                                            }
                                        } else {
                                            System.out.println("Cinema não encontrado");
                                        }
                                    }
                                } while (opcaoCinema != 0);
                                break;
                            case 4:
                                List<Vendas> vendas = VendasDAO.carregar(Cinema.obtemListaCinemas());
                                for (Vendas venda : vendas) {
                                    System.out.println("Venda ID: " + venda.getId() +
                                            " | Cinema: " + venda.getCinema().getNome() +
                                            " | Data: " + venda.getData() +
                                            " | Total: " + venda.getValorTotal() +
                                            " | Filmes: " + venda.getFilmes().size());
                                }
                                break;
                            case 0:
                                // Voltar ao menu principal
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida, tente novamente.");
                                break;
                        }
                    } while (opcaoAdmin != 0);
                    break;

                case 2:
                    // Menu Usuário
                    int opcaoUsuario;
                    do {
                        System.out.println("\n<< MENU USUÁRIO >>");
                        System.out.println("1. Ver Filmes em Cartaz");
                        System.out.println("2. Ver Sessões");
                        System.out.println("3. Ver cinemas");
                        System.out.println("4. Selecionar cinema");
                        System.out.println("5. Comprar Ingresso");
                        System.out.println("0. Voltar");
                        System.out.print("Escolha uma opção: ");
                        opcaoUsuario = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoUsuario) {
                            case 1:
                                ArrayList<Filmes> filmes = new FilmesDAO().buscarFilmes();
                                for (Filmes filme : filmes) {
                                    System.out.println("ID: " + filme.getId() + " | Duração: " + filme.getDuracao_s() + " | Filme: " + filme.getNome());
                                }
                                break;
                            case 2:
                                List<Sessao> sessoesUsuario = new SessaoDAO().buscarTodasSessoes();
                                for (Sessao sessao : sessoesUsuario) {
                                    System.out.println("Sessão ID: " + sessao.getId() + " | Sala: " + sessao.getSalaAssociada().getNome() + " | Filme: " + sessao.getFilmeExibido().getNome() + " | Data: " + sessao.getDataSessao());
                                }
                                break;
                            case 3:
                                Cinema.listaCinemas();
                                break;
                            case 4:
                                System.out.println("------------------ Lista de Cinemas ------------------");
                                for (int i = 0; i < Cinema.obtemListaCinemas().size(); i++) {
                                    System.out.println((i+1) + ". " + Cinema.obtemListaCinemas().get(i).getNome());
                                }
                                System.out.println("------------------------------------------------------");
                                int escolhaCinema;
                                System.out.println("Digite o número do cinema: ");
                                escolhaCinema = scanner.nextInt();
                                scanner.nextLine();

                                if (0 < escolhaCinema && escolhaCinema <= Cinema.obtemListaCinemas().size()) {
                                    Cinema cinema = Cinema.obtemListaCinemas().get(escolhaCinema);

                                    // Verifica qual cinema foi escolhido e mostra suas funcionalidades extras
                                    if (cinema instanceof Cineart) {
                                        int opcaoLanche;
                                        System.out.println("------------------ Lanches Cineart ------------------");
                                        System.out.println("1. Menu");
                                        System.out.println("2. Comprar");
                                        System.out.println("0. Voltar");
                                        System.out.println("-----------------------------------------------------");
                                        System.out.println("Opção: ");
                                        opcaoLanche = scanner.nextInt();
                                        scanner.nextLine();

                                        switch (opcaoLanche) {
                                            case 1:
                                                ((Cineart) cinema).listaLanches();
                                                break;
                                            case 2:
                                                String escolhaItem;
                                                System.out.println("Digite item que deseja comprar: ");
                                                escolhaItem = scanner.nextLine();
                                                scanner.nextLine();
                                                ((Cineart) cinema).comprarLanches(escolhaItem);
                                                break;
                                            case 0:
                                                break;
                                            default:
                                                System.out.println("Opção inválida, tente novamente.");
                                                break;
                                        }
                                    } else if (cinema instanceof Cinemark) {
                                        String escolha;
                                        System.out.println("Deseja fazer a reserva de uma sala para eventos? [s/n]");
                                        System.out.println("Opção: ");
                                        escolha = scanner.nextLine();
                                        scanner.nextLine();

                                        if (escolha.equals("s")) {
                                            String escolhaSala;
                                            System.out.println("Digite o nome da sala: ");
                                            escolhaSala = scanner.nextLine();
                                            scanner.nextLine();

                                            String data;
                                            System.out.println("Digite a data da reserva: ");
                                            data = scanner.nextLine();
                                            scanner.nextLine();

                                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                                            Date dataReserva = null;

                                            try {
                                                dataReserva = formato.parse(data);
                                            } catch (Exception e) {
                                                System.out.println("Erro ao formatar a data. Certifique-se de seguir o formato dd/MM/yyyy.");
                                            }

                                            ((Cinemark) cinema).reservarSala(escolhaSala, dataReserva);
                                        }
                                    } else if (cinema instanceof Cinesercla) {
                                        String escolha;
                                        System.out.println("Listar as salas 3D? [s/n]");
                                        System.out.println("Opção: ");
                                        escolha = scanner.nextLine();
                                        scanner.nextLine();

                                        if (escolha.equals("s")) {
                                            ((Cinesercla) cinema).listarSalas3D();
                                        }
                                    }
                                } else {
                                    System.out.println("Cinema não encontrado.");
                                }

                                break;
                            case 0:
                                // Voltar ao menu principal
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida, tente novamente.");
                                break;
                        }
                    } while (opcaoUsuario != 0);
                    break;
                case 5:
                    System.out.println("<< Comprar Ingresso >>");
                    List<Sessao> sessoesDisponiveis = new SessaoDAO().buscarTodasSessoes();

                    if (sessoesDisponiveis.isEmpty()) {
                        System.out.println("Não há sessões disponíveis no momento.");
                        break;
                    }

                    System.out.println("Escolha uma das sessões disponíveis:");
                    for (Sessao sessao : sessoesDisponiveis) {
                        System.out.println("ID: " + sessao.getId() +
                                " | Sala: " + sessao.getSalaAssociada().getNome() +
                                " | Filme: " + sessao.getFilmeExibido().getNome() +
                                " | Data: " + sessao.getDataSessao());
                    }

                    System.out.print("Digite o ID da sessão escolhida: ");
                    int idSessaoEscolhida = scanner.nextInt();
                    scanner.nextLine();

                    Sessao sessaoEscolhida = null;
                    for (Sessao sessao : sessoesDisponiveis) {
                        if (sessao.getId() == idSessaoEscolhida) {
                            sessaoEscolhida = sessao;
                            break;
                        }
                    }

                    if (sessaoEscolhida == null) {
                        System.out.println("Sessão não encontrada.");
                        break;
                    }

                    System.out.print("Digite a quantidade de ingressos que deseja comprar: ");
                    int quantidadeIngressos = scanner.nextInt();
                    scanner.nextLine();

                    if (quantidadeIngressos <= 0) {
                        System.out.println("Quantidade inválida.");
                        break;
                    }

                    // Registrar a compra
                    List<Filmes> filmes = Arrays.asList(sessaoEscolhida.getFilmeExibido());  // Criando a lista com um único filme
                    Vendas novaVenda = new Vendas(idVendas, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()), filmes, quantidadeIngressos);
                    idVendas++;

                    //VendasDAO.salvar(novaVenda);
                    System.out.println("Compra realizada com sucesso! Você comprou " + quantidadeIngressos + " ingressos para o filme " +
                            sessaoEscolhida.getFilmeExibido().getNome() + " na sala " + sessaoEscolhida.getSalaAssociada().getNome() + ".");
                    break;

                case 0:
                    // Sair do programa
                    System.out.println("Saindo do programa...");
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 0);


        // Salva os dados
        CinemaDAO.salvar(Cinema.obtemListaCinemas());

        scanner.close();
    }
}

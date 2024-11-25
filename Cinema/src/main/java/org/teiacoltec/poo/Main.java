package org.teiacoltec.poo;

import org.teiacoltec.poo.Classes.*;
import org.teiacoltec.poo.ClassesDAO.*;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FalhaConexaoException {
        Scanner scanner = new Scanner(System.in);
        Cinema cineart = Cineart.obtemInstancia();
        Cinema cinemark = Cinemark.obtemInstancia();
        Cinema cinepolis = Cinepolis.obtemInstancia();
        Cinema cinesercla = Cinesercla.obtemInstancia();

        List<Cinema> cinemasBD = CinemaDAO.carregar(Cinema.obtemListaCinemas());

        Filmes filme1 = new Filmes(1, 9600, "Wicked");
        new FilmesDAO().insere(filme1);
        Filmes filme2 = new Filmes(2, 8220, "Ainda Estou Aqui");
        new FilmesDAO().insere(filme2);
        Filmes filme3 = new Filmes(3, 6000, "Moana 2");
        new FilmesDAO().insere(filme3);

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
                        System.out.println("0. Voltar");
                        System.out.print("Escolha uma opção: ");
                        opcaoAdmin = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoAdmin) {
                            case 1:
                                // Ver Cinemas cadastrados
                                for (Cinema cinema : cinemasBD) {
                                    System.out.println("Nome: " + cinema.getNome());
                                }
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
                                            // Adicionar Sessão
                                            System.out.print("Digite o nome da sala: ");
                                            String nomeSala = scanner.nextLine();
                                            System.out.print("Digite o nome do filme: ");
                                            String nomeFilme = scanner.nextLine();
                                            scanner.nextLine();
                                            System.out.print("Digite o ID do filme: ");
                                            int idFilme = scanner.nextInt();
                                            scanner.nextLine();
                                            System.out.print("Digite a data da sessão (yyyy-mm-dd hh:mm:ss): ");
                                            String dataSessao = scanner.nextLine();

                                            // Adicionar lógica de criar uma sessão
                                            int id = 1;
                                            Sessao novaSessao = new Sessao(0, new Sala(id, nomeSala, 0), new org.teiacoltec.poo.Classes.Filmes(idFilme, 230, nomeFilme), java.sql.Timestamp.valueOf(dataSessao));
                                            id++;
                                            new SessaoDAO().salvarSessao(novaSessao);
                                            System.out.println("Sessão adicionada com sucesso!");
                                            break;
                                        case 2:
                                            // Listar Sessões
                                            ArrayList<Sessao> sessoes = new SessaoDAO().buscarTodasSessoes();
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
                                    }
                                } while (opcaoSessao != 5);
                                break;
                            case 0:
                                // Voltar ao menu principal
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida, tente novamente.");
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
                        System.out.println("0. Voltar");
                        System.out.print("Escolha uma opção: ");
                        opcaoUsuario = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoUsuario) {
                            case 1:
                                // Ver Filmes em Cartaz
                                System.out.println("Filmes em Cartaz: Em construção...");
                                break;
                            case 2:
                                ArrayList<Sessao> sessoesUsuario = new SessaoDAO().buscarTodasSessoes();
                                for (Sessao sessao : sessoesUsuario) {
                                    System.out.println("Sessão ID: " + sessao.getId() + " | Sala: " + sessao.getSalaAssociada().getNome() + " | Filme: " + sessao.getFilmeExibido().getNome() + " | Data: " + sessao.getDataSessao());
                                }
                                break;
                            case 0:
                                // Voltar ao menu principal
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida, tente novamente.");
                        }
                    } while (opcaoUsuario != 0);
                    break;

                case 0:
                    // Sair do programa
                    System.out.println("Saindo do programa...");
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}

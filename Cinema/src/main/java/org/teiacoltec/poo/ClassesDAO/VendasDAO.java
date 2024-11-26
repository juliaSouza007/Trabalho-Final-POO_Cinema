package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.Classes.Cinema;
import org.teiacoltec.poo.Classes.Filmes;
import org.teiacoltec.poo.Classes.Vendas;
import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendasDAO {

    private static final String TABELA = "Vendas";

    // Método para criar a tabela no banco de dados
    public static void criaTabela() throws FalhaConexaoException {
        String sql = """
            CREATE TABLE IF NOT EXISTS Vendas (
                id INT AUTO_INCREMENT PRIMARY KEY,
                data DATETIME NOT NULL,
                cinema_id INT NOT NULL,
                valor_total DOUBLE NOT NULL,
                FOREIGN KEY (cinema_id) REFERENCES Cinema(id)
            );
        """;
        try (Connection conexao = Conexao.obtemConexao();
             Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao criar tabela Vendas.");
        }
    }

    // Método para inserir uma venda no banco
    public static void inserir(Vendas venda) throws FalhaConexaoException {
        String sql = "INSERT INTO Vendas (data, cinema_id, valor_total) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.obtemConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(venda.getData().toLocalDateTime()));
            stmt.setInt(2, venda.getCinema().getId());
            stmt.setDouble(3, venda.getValorTotal());
            stmt.executeUpdate();

            // Obter o ID gerado automaticamente
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    venda.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao inserir venda.");
        }
    }

    // Método para buscar uma venda por ID, incluindo os detalhes do cinema
    public static Vendas buscarPorId(int id, List<Cinema> lista) throws FalhaConexaoException {
        String sql = "SELECT * FROM Vendas WHERE id = ?";
        try (Connection conexao = Conexao.obtemConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int cinemaId = rs.getInt("cinema_id");
                    Timestamp data = rs.getTimestamp("data");
                    int quantidadeIngressos= rs.getInt("valor_total");

                    // Aqui você deve carregar os filmes relacionados à venda (se necessário)
                    List<Filmes> filmes = carregarFilmesPorVenda(id);

                    return new Vendas(id, data, filmes, quantidadeIngressos);
                }
            }
        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao buscar venda por ID.");
        }
        return null;
    }

    // Método para buscar todas as vendas
    public static List<Vendas> buscarTodos(List<Cinema> lista) throws FalhaConexaoException {
        String sql = "SELECT * FROM Vendas";
        List<Vendas> vendas = new ArrayList<>();
        try (Connection conexao = Conexao.obtemConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int cinemaId = rs.getInt("cinema_id");
                Timestamp data = rs.getTimestamp("data");
                int valorTotal = rs.getInt("valor_total");

                // Aqui você deve carregar os filmes relacionados à venda (se necessário)
                List<Filmes> filmes = carregarFilmesPorVenda(id);

                vendas.add(new Vendas(id, data, filmes, valorTotal));
            }
        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao buscar todas as vendas.");
        }
        return vendas;
    }

    // Método auxiliar para obter o cinema pelo ID
    private static Cinema obterCinemaPorId(int cinemaId, List<Cinema> lista) throws CinemaNaoEncontradaException {
        for (Cinema cinema : lista) {
            if (cinema.getId() == cinemaId) {
                return cinema;
            }
        }
        throw new CinemaNaoEncontradaException(cinemaId);
    }

    // Método para carregar os filmes relacionados a uma venda
    private static List<Filmes> carregarFilmesPorVenda(int vendaId) throws FalhaConexaoException {
        List<Filmes> filmes = new ArrayList<>();
        String sql = "SELECT * FROM Filmes WHERE venda_id = ?";
        try (Connection conexao = Conexao.obtemConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, vendaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    long duracao = rs.getLong("duracao");
                    String nome = rs.getString("nome");
                    filmes.add(new Filmes(id, duracao, nome));
                }
            }
        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao carregar filmes por venda.");
        }
        return filmes;
    }

    // Método para usuário comprar ingressos
    public static Vendas comprarIngressos(int cinemaId, List<Filmes> filmesSelecionados, int quantidadeIngressos) throws FalhaConexaoException {
        // Obter a data e hora atual
        Timestamp agora = Timestamp.valueOf(LocalDateTime.now());
        Vendas novaVenda = null;

        // SQL para registrar a venda e os filmes associados
        String sqlVenda = "INSERT INTO Vendas (data, cinema_id, valor_total) VALUES (?, ?, ?)";
        String sqlVendaFilmes = "INSERT INTO VendaFilmes (venda_id, filme_id) VALUES (?, ?)";

        try (Connection conexao = Conexao.obtemConexao();
             PreparedStatement stmtVenda = conexao.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtVendaFilmes = conexao.prepareStatement(sqlVendaFilmes)) {

            // Registrar a venda
            stmtVenda.setTimestamp(1, agora);
            stmtVenda.setInt(2, cinemaId);
            stmtVenda.setDouble(3, quantidadeIngressos * 20.0);  // Valor total calculado diretamente aqui
            stmtVenda.executeUpdate();

            int idVenda;
            // Obter o ID da venda gerado automaticamente
            try (ResultSet rs = stmtVenda.getGeneratedKeys()) {
                if (rs.next()) {
                    idVenda = rs.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID da venda gerada.");
                }
            }

            // Associar os filmes à venda
            for (Filmes filme : filmesSelecionados) {
                stmtVendaFilmes.setInt(1, idVenda);
                stmtVendaFilmes.setInt(2, filme.getId());
                stmtVendaFilmes.executeUpdate();
            }

            // Criar a instância de Vendas com o cinema obtido
            novaVenda = new Vendas(idVenda, agora, filmesSelecionados, quantidadeIngressos);

        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao registrar a compra: " + e.getMessage());
        }

        return novaVenda;
    }


    // Método para carregar todas as vendas
    public static List<Vendas> carregar(List<Cinema> listaCinemas) throws FalhaConexaoException {
        String sql = "SELECT * FROM Vendas ORDER BY id";
        List<Vendas> listaVendas = new ArrayList<>();

        try (Connection conexao = Conexao.obtemConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int cinemaId = rs.getInt("cinema_id");
                Timestamp data = rs.getTimestamp("data");
                int valorTotal = rs.getInt("valor_total");

                Cinema cinema = obterCinemaPorId(cinemaId, listaCinemas);
                List<Filmes> filmes = carregarFilmesPorVenda(id);
                Vendas venda = new Vendas(id, data, filmes, valorTotal);

                listaVendas.add(venda);
            }

        } catch (SQLException | CinemaNaoEncontradaException e) {
            throw new FalhaConexaoException("Erro ao carregar as vendas: " + e.getMessage());
        }

        return listaVendas;
    }

}

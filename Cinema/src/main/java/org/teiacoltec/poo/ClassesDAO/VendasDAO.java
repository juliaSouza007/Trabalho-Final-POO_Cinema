package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.Classes.Vendas;
import org.teiacoltec.poo.Classes.Cinema;
import org.teiacoltec.poo.Classes.Filmes;
import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendasDAO {

    private static final String TABELA = "Vendas";

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

    public static void inserir(Vendas venda) throws FalhaConexaoException {
        String sql = "INSERT INTO Vendas (data, cinema_id, valor_total) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.obtemConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(venda.getData()));
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

    public static Vendas buscarPorId(int id) throws FalhaConexaoException {
        String sql = "SELECT * FROM Vendas WHERE id = ?";
        try (Connection conexao = Conexao.obtemConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cinema cinema = CinemaDAO.obtemCinema(rs.getInt("cinema_id"));
                    LocalDateTime data = rs.getTimestamp("data").toLocalDateTime();
                    double valorTotal = rs.getDouble("valor_total");
                    return new Vendas(id, data, cinema, null, valorTotal); // Lista de filmes pode ser carregada separadamente
                }
            }
        } catch (SQLException | CinemaNaoEncontradaException e) {
            throw new FalhaConexaoException("Erro ao buscar venda por ID.");
        }
        return null;
    }

    public static List<Vendas> buscarTodos() throws FalhaConexaoException {
        String sql = "SELECT * FROM Vendas";
        List<Vendas> vendas = new ArrayList<>();
        try (Connection conexao = Conexao.obtemConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Cinema cinema = CinemaDAO.obtemCinema(rs.getInt("cinema_id"));
                LocalDateTime data = rs.getTimestamp("data").toLocalDateTime();
                double valorTotal = rs.getDouble("valor_total");

                vendas.add(new Vendas(id, data, cinema, null, valorTotal));
            }
        } catch (SQLException | CinemaNaoEncontradaException e) {
            throw new FalhaConexaoException("Erro ao buscar todas as vendas.");
        }
        return vendas;
    }

    public static void remover(int id) throws FalhaConexaoException {
        String sql = "DELETE FROM Vendas WHERE id = ?";
        try (Connection conexao = Conexao.obtemConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao remover venda.");
        }
    }
}

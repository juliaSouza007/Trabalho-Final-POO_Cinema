package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.Classes.Filmes;
import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.Classes.Sala;
import org.teiacoltec.poo.Classes.Sessao;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessaoDAO {

    // Método para criar a tabela de sessões
    public static void criaTabela() throws FalhaConexaoException {
        String sql = """
            CREATE TABLE IF NOT EXISTS `sessao` (
                `id` INT AUTO_INCREMENT PRIMARY KEY,
                `idSala` VARCHAR(255) NOT NULL,
                `data` DATETIME NOT NULL,
                `idFilme` INT NOT NULL,
                FOREIGN KEY (`idFilme`) REFERENCES `filmes`(`id`)
            ) ENGINE = InnoDB;
        """;

        try (Connection conexao = Conexao.obtemConexao();
             Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao criar a tabela de sessões: " + e.getMessage());
        }
    }

    // Método para salvar uma sessão
    public static void salvarSessao(Sessao sessao) throws FalhaConexaoException {
        String sql = "INSERT INTO sessao (nomeSala, idFilme, data) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sessao.getSalaAssociada().getNome());
            stmt.setInt(2, sessao.getFilmeExibido().getId());
            stmt.setTimestamp(3, new Timestamp(sessao.getDataSessao().getTime()));

            stmt.executeUpdate();

            // Obter o ID gerado automaticamente
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sessao.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao salvar sessão: " + e.getMessage());
        }
    }

    // Método para buscar todas as sessões
    public static List<Sessao> buscarTodasSessoes() throws FalhaConexaoException {
        String sql = "SELECT s.id AS idSessao, s.idSala, s.data, f.id AS idFilme, f.nome AS nomeFilme, f.duracao\n" +
                    "FROM sessoes s\n" +
                    "JOIN filmes f ON s.idFilme = f.id;\n";
        List<Sessao> sessoes = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sala salaAssociada = new Sala(rs.getInt("idSala"), rs.getString("nomeSala"), rs.getInt("capacidade"));
                Filmes filmeExibido = new Filmes(rs.getInt("idFilme"), rs.getInt("duracao"), rs.getString("nomeFilme"));
                Date dataSessao = new Date(rs.getTimestamp("data").getTime());

                Sessao sessao = new Sessao(rs.getInt("idSessao"), salaAssociada, filmeExibido, dataSessao);
                sessoes.add(sessao);
            }

        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao buscar todas as sessões: " + e.getMessage());
        }
        return sessoes;
    }

    // Método para deletar uma sessão
    public static void deletarSessao(Sessao sessao) throws FalhaConexaoException {
        String sql = "DELETE FROM sessao WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessao.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao deletar sessão: " + e.getMessage());
        }
    }

    // Método para carregar a sessão
    public static List<Sessao> carregar() throws FalhaConexaoException {
        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * FROM sessoes ORDER BY id;");

            List<Sessao> lista = new ArrayList<Sessao>();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nomeSala = resultado.getString("idSala");
                int idFilme = resultado.getInt("idFilme");
                Timestamp dataSessaoTimestamp = resultado.getTimestamp("data");
                Date dataSessao = new Date(dataSessaoTimestamp.getTime());

                Sala salaAssociada = SalaDAO.buscarSala(Integer.parseInt(nomeSala));
                Filmes filmeExibido = FilmesDAO.buscarFilme(idFilme);

                Sessao sessao = new Sessao(id, salaAssociada, filmeExibido, dataSessao);

                lista.add(sessao);
            }

            // Retorna a lista com todas as sessões
            return lista;

        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao carregar as sessões: " + e.getMessage());
        }
    }
}

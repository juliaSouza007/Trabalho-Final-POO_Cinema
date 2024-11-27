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

        try {
            // Estabelece conexao
            Connection conexao = Conexao.obtemConexao();

            // Faz a consulta
            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE SCHEMA IF NOT EXISTS `coltec` DEFAULT CHARACTER SET utf8;");
            stmt.execute("CREATE TABLE IF NOT EXISTS `coltec`.`sessao` (" +
                    "`id` INT NOT NULL," +
                    "`idSala` INT NOT NULL," +
                    "`data` DATETIME NOT NULL," +
                    "`idFilme` INT NOT NULL," +
                    "PRIMARY KEY (`id`)), " +
                    "INDEX `fk_sessao_Filmes_idx` (`idFilme` ASC)," +
                    "CONSTRAINT `fk_sessao_Filmes` FOREIGN KEY (`idFilme`)" +
                    "REFERENCES `coltec`.`Filmes` (`id`) ON DELETE CASCADE)," +
                    "INDEX `fk_sessao_Salas_idx` (`idSala` ASC)," +
                    "CONSTRAINT `fk_sessao_Salas` FOREIGN KEY (`idSala`)" +
                    "REFERENCES `coltec`.`Salas` (`id`) ON DELETE CASCADE)," +
                    "ENGINE = InnoDB;");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }

    }

    // Método para salvar uma sessão
    public static void salvarSessao(Sessao sessao) throws FalhaConexaoException {
        String sql = "INSERT INTO sessao (id, idSala, idFilme, data) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, sessao.getSalaAssociada().getId());
            stmt.setInt(2, sessao.getSalaAssociada().getId());
            stmt.setInt(3, sessao.getFilmeExibido().getId());
            stmt.setTimestamp(4, new Timestamp(sessao.getDataSessao().getTime()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao salvar sessão: " + e.getMessage());
        }
    }

    // Método para buscar todas as sessões
    public static List<Sessao> buscarTodasSessoes() throws FalhaConexaoException {
        String sql = "SELECT s.id AS idSessao, s.idSala, s.data, f.id AS idFilme, f.nome AS nomeFilme, f.duracao, a.id_Cinema, a.nome AS nomeSala\n" +
                    "FROM sessoes s\n" +
                    "JOIN filmes f ON s.idFilme = f.id INNER JOIN Salas a ON s.idSala = a.id;\n";
        List<Sessao> sessoes = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sala salaAssociada = new Sala(rs.getInt("idSala"), rs.getString("nomeSala"), rs.getInt("capacidade"), rs.getInt("id_Cinema"));
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
                int idSala = resultado.getInt("idSala");
                int idFilme = resultado.getInt("idFilme");
                Timestamp dataSessaoTimestamp = resultado.getTimestamp("data");
                Date dataSessao = new Date(dataSessaoTimestamp.getTime());

                Sala salaAssociada = SalaDAO.buscarSala(idSala);
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

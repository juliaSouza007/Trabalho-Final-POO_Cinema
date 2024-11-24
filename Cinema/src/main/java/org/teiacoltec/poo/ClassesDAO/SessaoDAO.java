package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.Classes.Filmes;
import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.Classes.Sala;
import org.teiacoltec.poo.Classes.Sessao;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class SessaoDAO {

    // Método para salvar uma sessão
    public Sessao salvarSessao(Sessao sessao) {
        String sql = "INSERT INTO sessoes (nomeSala, idFilme, dataSessao) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sessao.getSalaAssociada().getNome());
            stmt.setInt(2, sessao.getFilmeExibido().getId());
            stmt.setTimestamp(3, new Timestamp(sessao.getDataSessao().getTime()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sessao.setId(rs.getInt(1));
            }
            return sessao;
        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao salvar sessão: " + e.getMessage());
            return null;
        }
    }

    // Método para buscar todas as sessões
    public ArrayList<Sessao> buscarTodasSessoes() {
        String sql = "SELECT * FROM sessoes";
        ArrayList<Sessao> sessoes = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sala salaAssociada = new Sala(rs.getInt("idSala"), "sala 1", 100); // Capacidade não disponível diretamente
                Filmes filmeExibido = new Filmes(rs.getInt("idFilme"), 230, "moana"); // Detalhes adicionais devem ser buscados
                Date dataSessao = new Date(rs.getTimestamp("dataSessao").getTime());
                sessoes.add(new Sessao(rs.getInt("id"), salaAssociada, filmeExibido, dataSessao));
            }
        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao buscar todas as sessões: " + e.getMessage());
        }
        return sessoes;
    }

    // Método para buscar sessões por filme
    public ArrayList<Sessao> buscarSessoesPorFilme(Filmes filme) {
        String sql = "SELECT * FROM sessoes WHERE idFilme = ?";
        ArrayList<Sessao> sessoes = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filme.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sala salaAssociada = new Sala(rs.getInt("idSala"), "nomeSala", 100); // Capacidade não disponível diretamente
                Date dataSessao = new Date(rs.getTimestamp("dataSessao").getTime());
                sessoes.add(new Sessao(rs.getInt("id"), salaAssociada, filme, dataSessao));
            }
        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao buscar sessões por filme: " + e.getMessage());
        }
        return sessoes;
    }

    // Método para atualizar uma sessão
    public void atualizarSessao(Sessao sessao) {
        String sql = "UPDATE sessoes SET nomeSala = ?, idFilme = ?, dataSessao = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sessao.getSalaAssociada().getNome());
            stmt.setInt(2, sessao.getFilmeExibido().getId());
            stmt.setTimestamp(3, new Timestamp(sessao.getDataSessao().getTime()));
            stmt.setInt(4, sessao.getId());
            stmt.executeUpdate();

        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao atualizar sessão: " + e.getMessage());
        }
    }

    // Método para deletar uma sessão
    public void deletarSessao(Sessao sessao) {
        String sql = "DELETE FROM sessoes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessao.getId());
            stmt.executeUpdate();

        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao deletar sessão: " + e.getMessage());
        }
    }
}

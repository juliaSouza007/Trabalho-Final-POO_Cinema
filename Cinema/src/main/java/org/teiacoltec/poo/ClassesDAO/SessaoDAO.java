package ClassesDAO;

import conexao.Conexao;
import Classes.Filmes;
import Classes.Sala;
import Classes.Sessao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public abstract class SessaoDAO implements MasterDAO<Sessao> {

    @Override
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
        } catch (SQLException e) {
            System.err.println("Erro ao salvar sessão: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Sessao> buscarTodasSessoes() {
        String sql = "SELECT * FROM sessoes";
        ArrayList<Sessao> sessoes = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sala salaAssociada = new Sala(rs.getString("nomeSala"), 0); // Capacidade não disponível diretamente
                Filme filmeExibido = new Filme(rs.getInt("idFilme"), "Placeholder", 0); // Detalhes adicionais devem ser buscados
                Date dataSessao = new Date(rs.getTimestamp("dataSessao").getTime());
                sessoes.add(new Sessao(rs.getInt("id"), salaAssociada, filmeExibido, dataSessao));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as sessões: " + e.getMessage());
        }
        return sessoes;
    }

    public ArrayList<Sessao> buscarSessoesPorFilme(Filme filme) {
        String sql = "SELECT * FROM sessoes WHERE idFilme = ?";
        ArrayList<Sessao> sessoes = new ArrayList<>();
    
        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, filme.getId());
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Sala salaAssociada = new Sala(rs.getString("nomeSala"), 0); // Capacidade não disponível diretamente
                Date dataSessao = new Date(rs.getTimestamp("dataSessao").getTime());
                sessoes.add(new Sessao(rs.getInt("id"), salaAssociada, filme, dataSessao));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar sessões por filme: " + e.getMessage());
        }
        return sessoes;
    }


    @Override
    public void atualizarSessao(Sessao sessao) {
        String sql = "UPDATE sessoes SET nomeSala = ?, idFilme = ?, dataSessao = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sessao.getSalaAssociada().getNome());
            stmt.setInt(2, sessao.getFilmeExibido().getId());
            stmt.setTimestamp(3, new Timestamp(sessao.getDataSessao().getTime()));
            stmt.setInt(4, sessao.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar sessão: " + e.getMessage());
        }
    }

    @Override
    public void deletarSessao(Sessao sessao) {
        String sql = "DELETE FROM sessoes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessao.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar sessão: " + e.getMessage());
        }
    }
}

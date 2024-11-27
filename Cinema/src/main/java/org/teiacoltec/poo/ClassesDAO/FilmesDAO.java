package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.Classes.Filmes;
import org.teiacoltec.poo.conexao.FalhaConexaoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmesDAO {

    public static void criaTabela() throws FalhaConexaoException {
        try {
            // Estabelece conexao
            Connection conexao = Conexao.obtemConexao();

            // Faz a consulta
            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE SCHEMA IF NOT EXISTS `coltec` DEFAULT CHARACTER SET utf8;");
            stmt.execute("CREATE TABLE IF NOT EXISTS `coltec`.`Filmes` (" +
                    "`id` INT NOT NULL," +
                    "`nome` VARCHAR(255) NOT NULL," +
                    "`duracao_s` VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (`id`)) ENGINE = InnoDB;");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }



    public static Filmes insere(Filmes filme) {
        String sql = "INSERT INTO Filmes (id, nome, duracao_s) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filme.getId());
            stmt.setString(2, filme.getNome());
            stmt.setLong(3, filme.getDuracao_s());
            stmt.executeUpdate();

            return filme;
        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao inserir filme: " + e.getMessage());
            return null;
        }
    }

    public static Filmes buscarFilme(int id) {

        String sql = "SELECT * FROM Filmes WHERE id = ?";
        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Filmes(rs.getInt("id"), rs.getLong("duracao_s"), rs.getString("nome"));
            }
        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao buscar filme: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Filmes> buscarFilmes() {

        String sql = "SELECT * FROM Filmes";
        ArrayList<Filmes> filmes = new ArrayList<>();
        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                filmes.add(new Filmes(rs.getInt("id"), rs.getLong("duracao_s"), rs.getString("nome")));
            }

        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao buscar filmes: " + e.getMessage());
        }
        return filmes;
    }

    public static void atualiza(Filmes filme) {

        String sql = "UPDATE Filmes SET nome = ?, duracao_s = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getNome());
            stmt.setLong(2, filme.getDuracao_s());
            stmt.setInt(3, filme.getId());
            stmt.executeUpdate();

        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao atualizar filme: " + e.getMessage());
        }
    }

    public static void remove(Filmes filme) {
        String sql = "DELETE FROM Filmes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filme.getId());
            stmt.executeUpdate();

        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao remover filme: " + e.getMessage());
        }
    }

    public static List<Filmes> carregar() throws FalhaConexaoException {
        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * FROM Salas ORDER BY id;");

            List<Filmes> lista = new ArrayList<Filmes>();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String  nome = resultado.getString("nome");
                long duracao_s = resultado.getLong("duracao_s");

                Filmes filme = new Filmes(id, duracao_s ,nome);

                lista.add(filme);
            }

            // Retorna a lista com todas as sessões
            return lista;

        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao carregar os filmes: " + e.getMessage());
        }
    }
}

package org.teicoltec.poo.ClassesDAO;

import org.teicoltec.poo.conexao.Conexao;
import org.teicoltec.poo.Classes.Filme;
import org.teiacoltec.poo.conexao.FalhaConexaoException;
import java.sql.*;
import java.util.ArrayList;

public abstract class FilmesDAO {

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



    public Filme insere(Filme filme) {
        String sql = "INSERT INTO Filmes (id, nome, duracao_s) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filme.getId());
            stmt.setString(2, filme.getNome());
            stmt.setInt(3, filme.getDuracao_s());
            stmt.executeUpdate();

            return filme;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir filme: " + e.getMessage());
            return null;
        }
    }

    public Filme buscarFilme(int id) {

        String sql = "SELECT * FROM Filmes WHERE id = ?";
        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Filme(rs.getInt("id"), rs.getString("nome"), rs.getLong("duracao_s"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar filme: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Filme> buscarFilmes() {

        String sql = "SELECT * FROM Filmes";
        ArrayList<Filme> filmes = new ArrayList<>();
        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                filmes.add(new Filme(rs.getInt("id"), rs.getString("nome"), rs.getLong("duracao_s")));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar filmes: " + e.getMessage());
        }
        return filmes;
    }

    public void atualiza(Filme filme) {

        String sql = "UPDATE Filmes SET nome = ?, duracao_s = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getNome());
            stmt.setLong(2, filme.getDuracao_s());
            stmt.setInt(3, filme.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar filme: " + e.getMessage());
        }
    }

    public void remove(Filme filme) {
        String sql = "DELETE FROM Filmes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filme.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao remover filme: " + e.getMessage());
        }
    }
}
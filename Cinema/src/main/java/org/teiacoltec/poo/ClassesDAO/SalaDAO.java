package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.Classes.Sala;
import org.teiacoltec.poo.conexao.FalhaConexaoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    public static void criaTabela() throws FalhaConexaoException {
        try {
            // Estabelece conexao
            Connection conexao = Conexao.obtemConexao();

            // Faz a consulta
            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE SCHEMA IF NOT EXISTS `coltec` DEFAULT CHARACTER SET utf8;");
            stmt.execute("CREATE TABLE IF NOT EXISTS `coltec`.`Salas` (" +
                    "`id` INT NOT NULL," +
                    "`nome` VARCHAR(255) NOT NULL," +
                    "`capacidade` INT NOT NULL," +
                    "`id_Cinema` INT NOT NULL," +
                    "PRIMARY KEY (`id`)) ENGINE = InnoDB;");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static Sala insere(Sala sala) {

        String sql = "INSERT INTO Salas (id, nome, capacidade, id_Cinema) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, sala.getId());
            stmt.setString(2, sala.getNome());
            stmt.setInt(3, sala.getCapacidade());
            stmt.setInt(4, sala.getId_Cinema());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sala.setNome(rs.getString(2)); // Define o nome da sala como chave gerada
            }

            return sala;
        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao inserir  sala: " + e.getMessage());
            return null;
        }
    }

    public static Sala buscarSala(int id) {

        String sql = "SELECT * FROM Salas WHERE id = ?";
        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Sala(rs.getInt("id"), rs.getString("nome"), rs.getInt("capacidade"), rs.getInt("id_Cinema"));
            }
        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao buscar sala: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Sala> buscarSalas() {
        
        String sql = "SELECT * FROM Salas";
        ArrayList<Sala> salas = new ArrayList<>();
        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                salas.add(new Sala(rs.getInt("id"), rs.getString("nome"), rs.getInt("capacidade"), rs.getInt("id_Cinema")));
            }
        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao buscar as salas: " + e.getMessage());
        }
        return salas;
    }

    public static void atualiza(Sala sala) {

        String sql = "UPDATE Salas SET capacidade = ? WHERE nome = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sala.getCapacidade());
            stmt.setString(2, sala.getNome());
            stmt.executeUpdate();

        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao atualizar sala: " + e.getMessage());
        }
    }

    public static void remove(Sala sala) {

        String sql = "DELETE FROM Salas WHERE nome = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getNome());
            stmt.executeUpdate();

        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao remover sala: " + e.getMessage());
        }
    }

    public static List<Sala> carregar() throws FalhaConexaoException {
        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * FROM Salas ORDER BY id;");

            List<Sala> lista = new ArrayList<Sala>();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String  nome = resultado.getString("nome");
                int capacidade = resultado.getInt("capacidade");
                int id_Cinema = resultado.getInt("id_Cinema");

                Sala sala = new Sala(id, nome, capacidade, id_Cinema);

                lista.add(sala);
            }

            // Retorna a lista com todas as sessões
            return lista;

        } catch (SQLException e) {
            throw new FalhaConexaoException("Erro ao carregar as salas: " + e.getMessage());
        }
    }
}

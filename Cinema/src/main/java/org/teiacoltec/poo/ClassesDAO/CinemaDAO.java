package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.Classes.Cinema;
import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CinemaDAO implements ICinemaDAO {
    private static final int ID_POSICAO_TABELA = 1;
    private static final int NOME_POSICAO_TABELA = 2;
    private static final int LOCAL_POSICAO_TABELA = 3;

    private static final String NOME_TABELA = "Cinema";

    static final int DUPLICATE_KEY_ERROR_CODE = 1062;

    public static void criaTabela() throws FalhaConexaoException {
        try {
            // Estabelece conexao
            Connection conexao = Conexao.obtemConexao();

            // Faz a consulta
            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE SCHEMA IF NOT EXISTS `coltec` DEFAULT CHARACTER SET utf8;");
            stmt.execute("CREATE TABLE IF NOT EXISTS `coltec`.`Cinema` (" +
                    "`id` INT NOT NULL," +
                    "`nome` VARCHAR(255) NOT NULL," +
                    "`local` VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (`id`)) ENGINE = InnoDB;");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void insere(Cinema cinema) throws IdExistenteException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("INSERT INTO " + NOME_TABELA + " VALUES(?,?,?);");
            stmt.setInt(1, cinema.getId());
            stmt.setString(2, cinema.getNome());
            stmt.setString(3, cinema.getLocal());
            stmt.execute();

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) throw new IdExistenteException(cinema.getId());
            throw new Error(e.getMessage());
        }
    }

    public static String[] obtemCinema(int id) throws CinemaNaoEncontradaException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * from " + NOME_TABELA + " WHERE id = " + id + ";");

            if (resultado.next()) {
                // Obtenho os dados
                String[] resul = new String[3];

                resul[0] = String.valueOf(resultado.getInt(ID_POSICAO_TABELA));
                resul[1] = resultado.getString(NOME_POSICAO_TABELA);
                resul[2] = resultado.getString(LOCAL_POSICAO_TABELA);

                return resul;
            }

            // Se chegou aqui é porque não tem agencia com esse numero
            throw new CinemaNaoEncontradaException(id);
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void atualiza(Cinema cinema) throws CinemaNaoEncontradaException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("UPDATE " + NOME_TABELA + " SET nome = ?, local = ? WHERE id = ?;");
            stmt.setString(1, cinema.getNome());
            stmt.setString(2, cinema.getLocal());
            stmt.setInt(3, cinema.getId());

            // Verifica a quantidade de registros alterados
            int nLinhasAlteradas = stmt.executeUpdate();

            // Se não alterou nenhuma linha é porque não tem cinema com esse id
            if (nLinhasAlteradas == 0) throw new CinemaNaoEncontradaException(cinema.getId());

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void remove(Cinema cinema) throws CinemaNaoEncontradaException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM " + NOME_TABELA + " WHERE id = ?;");
            stmt.setInt(1, cinema.getId());

            // Verifica a quantidade de registros alterados
            int nLinhasAlteradas = stmt.executeUpdate();

            // Se não alterou nenhuma linha é porque não tem cinema com esse id
            if (nLinhasAlteradas == 0) throw new CinemaNaoEncontradaException(cinema.getId());

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void salvar(List<Cinema> lista) throws FalhaConexaoException {

        for (Cinema cinema : lista) {
            try {
                insere(cinema);
            } catch (IdExistenteException e) {
                try {
                    // Verifica se é o mesmo cinema que já está no bd
                    String[] resul = obtemCinema(cinema.getId());
                    if (cinema.getId() == Integer.valueOf(resul[0]) &&
                            cinema.getNome().equals(resul[1]) &&
                            cinema.getLocal().equals(resul[2])) {

                        // Não adiciona o cinema no bd
                    } else {
                        // Se tiver o mesmo id, mas informações diferentes, atualiza o cinema
                        atualiza(cinema);
                    }
                } catch (CinemaNaoEncontradaException f) {
                    throw new Error(f.getMessage());
                }
            }
        }
    }

    public static List<Cinema> carregar() throws FalhaConexaoException {

        try {
            // Estabelece conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * from " + NOME_TABELA + " ORDER BY id;");

            // Crio a lista
            List<Cinema> lista = new ArrayList<>();

            while (resultado.next()) {
                // Obtenho os dados
//                Cinema cinemaTmp = new Cinema(resultado.getInt(ID_POSICAO_TABELA),
//                        resultado.getString(NOME_POSICAO_TABELA),
//                        resultado.getString(LOCAL_POSICAO_TABELA)
//                        );
                // Adiciono à lista
                // lista.add(cinemaTmp);
            }

            // Retorna a lista preenchida
            return lista;

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }
}

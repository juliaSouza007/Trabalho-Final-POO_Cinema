package org.teiacoltec.poo.Classes;

import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.ClassesDAO.SalaDAO;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Sala {

    private int id;
    private String nome;
    private int capacidade;
    private ArrayList<Sessao> sessao;
    private HashMap<Integer, Boolean> assentosDisponiveis;


    public Sala(int id, String nome, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.sessao = new ArrayList<>();
        this.assentosDisponiveis = new HashMap<>();
        for (int i = 1; i <= capacidade; i++) {
            this.assentosDisponiveis.put(i, true); // Todos os assentos começam como disponíveis
        }
    }


    public void criarSessao(int id, Sala salaAssociada, Filmes filmeExibido, Date dataSessao) {

        Sessao sessao = new Sessao(id, salaAssociada, filmeExibido, dataSessao);
        this.sessao.add(sessao);

        String sql = "INSERT INTO sessoes (id, sala_nome, filme_id, data) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, salaAssociada.getNome());
            stmt.setInt(3, filmeExibido.getId());
            stmt.setTimestamp(4, new Timestamp(dataSessao.getTime()));
            stmt.executeUpdate();

        } catch (SQLException | FalhaConexaoException e) {
            System.err.println("Erro ao criar sessão: " + e.getMessage());
        }

    }

    public void listarSessoes() {
        if (sessao.isEmpty()) {
            System.out.println("Não há sessões cadastradas.");
            return;
        }
        for (Sessao s : sessao) {
            System.out.println(s.toString());
        }
    }

    public boolean verificarCapacidade() {
        int assentosOcupados = 0;
        for (boolean ocupado : assentosDisponiveis.values()) {
            if (!ocupado) {
                assentosOcupados++;
            }
        }

        if (assentosOcupados < capacidade) {
            // retorna true caso a capacidade nao tenho sido atingida
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarAssentoDisponivel(int numeroAssento) {
        return assentosDisponiveis.getOrDefault(numeroAssento, false);
    }

    public void exibirMapaSala() {
        System.out.println("Mapa da Sala " + nome + ":");
        for (int i = 0; i < capacidade; i++) {
            System.out.print((assentosDisponiveis.get(i) ? "[Livre] " : "[Ocupado] "));
            if (i % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }



    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public int getCapacidade() {

        return capacidade;
    }

    public void setCapacidade(int capacidade) {

        this.capacidade = capacidade;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

}

package org.teiacoltec.poo.Classes;

import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.ClassesDAO.SalaDAO;

import java.util.ArrayList;
import java.util.Date;

public class Sala {

    private int id;
    private String nome;
    private int capacidade;
    private ArrayList<Sessao> sessao;


    public Sala(int id, String nome, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.sessao = new ArrayList<>();
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

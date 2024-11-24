import ClassesDAO.SessaoDAO;
import Sala;
import Filmes;
import java.util.Date;

public class Sessao {
    private int id;
    private Sala salaAssociada;
    private Filme filmeExibido;
    private Date dataSessao;
    private SessaoDAO sessaoDAO;

    public Sessao(int id, Sala salaAssociada, Filme filmeExibido, Date dataSessao) {
        this.id = id;
        this.salaAssociada = salaAssociada;
        this.filmeExibido = filmeExibido;
        this.dataSessao = dataSessao;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + '\n' +
               "Sala: " + this.salaAssociada.getNome() + '\n' +
               "Filme: " + this.filmeExibido.getNome() + '\n' +
               "Data: " + this.dataSessao;
    }

    //getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sala getSalaAssociada() {
        return salaAssociada;
    }

    public Filme getFilmeExibido() {
        return filmeExibido;
    }

    public Date getDataSessao() {
        return dataSessao;
    }
}

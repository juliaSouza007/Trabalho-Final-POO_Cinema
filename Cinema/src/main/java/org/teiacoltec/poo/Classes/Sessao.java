import ClassesDAO.SessaoDAO;
import Sala;
import Filmes;
import java.util.Date;

public class Sessao {
    private int identificador;
    private Sala salaAssociada;
    private Filme filmeExibido;
    private Date dataSessao;
    private SessaoDAO sessaoDAO;

    public Sessao(int identificador, Sala salaAssociada, Filme filmeExibido, Date dataSessao) {
        this.identificador = identificador;
        this.salaAssociada = salaAssociada;
        this.filmeExibido = filmeExibido;
        this.dataSessao = dataSessao;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
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

    @Override
    public String toString() {
        return "ID: " + this.identificador + '\n' +
               "Sala: " + this.salaAssociada.getNome() + '\n' +
               "Filme: " + this.filmeExibido.getNome() + '\n' +
               "Data: " + this.dataSessao;
    }
}

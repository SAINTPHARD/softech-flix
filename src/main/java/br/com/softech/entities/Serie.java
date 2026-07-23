package br.com.softech.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * (Entidade Principal): A classe pai que conterá os dados da série e a lista de episódios
 * mapeada com @OneToMany
 */

@Entity // Indica que a classe é uma entidade
@Table(name = "series") // Indica o nome da tabela no banco de dados
@Getter
@Setter
@NoArgsConstructor
public class Serie {

    // ID da série
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que o ID será gerado automaticamente pelo banco de dados
    private Long id;

    // Titulo da série
    @Column(unique = true, name = "title", nullable = false, length = 100) // Indica o nome da coluna no banco de dados
    private String title;

    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private String categoria;
    private String sinopse;

    // Relacionamento (pai-filho)com a classe Episode, com cascade e orphanRemoval pra gerenciar os episódios
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Episode> episodes = new ArrayList<>();

    // Construtor
    public Serie(String title,
                 Integer totalTemporadas,
                 Double avaliacao,
                 String categoria,
                 String sinopse) {
        this.title = title;
        this.totalTemporadas = totalTemporadas;
        this.avaliacao = avaliacao;
        this.categoria = categoria;
        this.sinopse = sinopse;
    }

    // Metodos auxiliares para manter a consistência do relacionamento
    public void addEpisode(Episode episode) {
        this.episodes.add(episode);
        episode.setSerie(this);
    }

    // Pra remover um episódio da lista
    public void removeEpisode(Episode episode) {
        this.episodes.remove(episode);
        episode.setSerie(null);
    }

    // Sub
    @Override
    public String toString() {
        return "Serie [id=" + id + ", " +
                "title=" + title + ", " +
                "totalTemporadas=" + totalTemporadas + ", " +
                "avaliacao=" + avaliacao + ", " +
                "categoria=" + categoria + ", " +
                "sinopse=" + sinopse + "]";
    }
}

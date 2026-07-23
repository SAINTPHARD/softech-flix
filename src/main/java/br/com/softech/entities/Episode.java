package br.com.softech.entities;

/**
 * (Entidade Filha): A classe filha que conterá os dados do episódio
 */
@Entity
@Table(name = "episodes")
@Getter
@Setter
@NoArgsConstructor
public class Episode {

    /**
     * Atributos da entidade Episode
     * ID do episódio
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que o ID será gerado automaticamente pelo banco de dados
    private Long id;

    // Titulo do episódio
    private String title;

    private Integer temporada;
    private Integer numeroEpisode;
    private Double avaliacao;
    private String sinopse;

    /**
     * Relacionamento de Serie com Episode
     * @ManyToOne : Muitos episódios para uma série
     * @FetchType.LAZY : Carrega os dados da série apenas quando necessário
     * @JoinColumn(name = "serie_id") : Representa a chave estrangeira na tabela episodes
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id")
    private Serie serie;

    public Episode(Integer temporada,
                   String title,
                   Integer numeroEpisode,
                   Double avaliacao) {
        this.temporada = temporada;
        this.title = title;
        this.numeroEpisode = numeroEpisode;
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", temporada=" + temporada +
                ", numeroEpisode=" + numeroEpisode +
                ", avaliacao=" + avaliacao +
                ", sinopse='" + sinopse + '\'' +
                ", serieId=" + (serie != null ? serie.getId() : null) +
                '}';
    }
}

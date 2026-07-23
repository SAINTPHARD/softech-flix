package br.com.softech.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * (DTO): Record que representa os dados de uma série desserializados do JSON da API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
        @JsonAlias("Title") String title,
        @JsonAlias({"totalSeasons", "TotalSeasons"}) Integer totalTemporadas,
        @JsonAlias("imdbRating") String avaliacao,
        @JsonAlias("Genre") String categoria,
        @JsonAlias({"Plot", "Overview"}) String sinopse
) {
    // Em Java Records os getters são gerados automaticamente com o nome do campo:
    // title(), totalTemporadas(), avaliacao(), categoria(), sinopse()

    @Override
    public String toString() {
        return "DadosSerie [" +
                "title='" + title + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao='" + avaliacao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ']';
    }
}
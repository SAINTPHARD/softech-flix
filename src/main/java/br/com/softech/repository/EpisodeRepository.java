package br.com.softech.repository;

import br.com.softech.entities.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * (Interface): Interface que define os métodos de acesso a dados do episódio
 * Interface para gerenciar os episódios, caso precise de buscas específicas por temporada ou avaliações.
 */
@Repository // Indica que a interface é um repositório
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    /**
     *
     */
}

package br.com.softech.repository;

import br.com.softech.entities.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * (Interface): Interface que define os métodos de acesso a dados da série
 * Interface que estende JpaRepository<Serie, Long>, onde você poderá colocar
 * consultas personalizadas (@Query ou Derived Queries).
 */

@Repository // Indica que a interface é um repositório
public interface SerieRepository extends JpaRepository<Serie, Long> {
    /**
     * Aqui pode ser adicionado possivél queries personalizadas, caso seja necessário.
     * Exemplo:
     * @Query("SELECT s FROM Serie s WHERE s.title = :title")
     * List<Serie> findByTitle(@Param("title") String title);
     *
     */
}

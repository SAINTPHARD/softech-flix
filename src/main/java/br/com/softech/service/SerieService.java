package br.com.softech.service;

import br.com.softech.entities.Serie;
import br.com.softech.repository.SerieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * SerieService: Onde as regras de negócio e as transações ficam isoladas.
 * Utiliza @Transactional e @Transactional(readOnly = true)
 * para otimizar as consultas e gerenciar o CRUD completo.
 */
@Service
public class SerieService {

    // Injeção de dependência via Construtor (Excelente prática!)
    private final SerieRepository serieRepository;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    // =====================================================
    // READ ALL: Busca todas as séries (Somente Leitura)
    // =====================================================
    @Transactional(readOnly = true)
    public List<Serie> listarTodas() {
        return serieRepository.findAll();
    }

    // =====================================================
    // READ BY ID: Busca uma série por ID (Somente Leitura)
    // =====================================================
    @Transactional(readOnly = true)
    public Optional<Serie> findById(Long id) {
        return serieRepository.findById(id);
    }

    // =====================================================
    // CREATE: Salvar uma nova série
    // =====================================================
    @Transactional
    public Serie salvar(Serie serie) {
        return serieRepository.save(serie);
    }

    // =====================================================
    // UPDATE: Atualizar uma série existente com CRUD completo
    // =====================================================
    @Transactional
    public Serie atualizar(Long id, Serie serieAtualizada) {
        return serieRepository.findById(id)
                .map(serieExistente -> {
                    // Atualiza os campos usando os setters corretos e a variável correspondente
                    serieExistente.setTitle(serieAtualizada.getTitle());
                    serieExistente.setTotalTemporadas(serieAtualizada.getTotalTemporadas());
                    serieExistente.setAvaliacao(serieAtualizada.getAvaliacao());
                    serieExistente.setCategoria(serieAtualizada.getCategoria());
                    serieExistente.setSinopse(serieAtualizada.getSinopse());

                    return serieRepository.save(serieExistente);
                })
                .orElseThrow(() -> new RuntimeException("Série não encontrada com ID: " + id));
    }

    // =====================================================
    // DELETE: Deletar uma série por ID
    // =====================================================
    @Transactional
    public void deletar(Long id) {
        if (!serieRepository.existsById(id)) {
            throw new RuntimeException("Série não encontrada para exclusão com ID: " + id);
        }
        serieRepository.deleteById(id);
    }
}
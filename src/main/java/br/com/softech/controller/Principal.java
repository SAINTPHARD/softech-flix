package br.com.softech.controller;

import br.com.softech.entities.Categoria;
import br.com.softech.entities.Episode;
import br.com.softech.entities.Serie;
import br.com.softech.service.SerieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * (Controller / Principal): Classe responsável por orquestrar o fluxo de execução
 * da aplicação no terminal via CommandLineRunner.
 * @Component: Transforma a classe em um componente Bean gerenciado pelo Spring
 * @CommandLineRunner: Interface que indica que a classe deve ser executada automaticamente
 * na inicialização do Spring Boot.
 */
@Component
public class Principal implements CommandLineRunner {

    // Injeção de dependência via Construtor (Excelente prática!)
    private final SerieService serieService;

    public Principal(SerieService serieService) {
        this.serieService = serieService;
    }

    // Método executado automaticamente na inicialização do Spring Boot
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n==========================================");
        System.out.println("   INICIALIZANDO APLICAÇÃO SOFTECH-FLIX   ");
        System.out.println("==========================================");

        // 1. Instanciando uma nova Série
        Serie serie = new Serie();
        serie.setTitle("Stranger Things");
        serie.setTotalTemporadas(1);
        serie.setAvaliacao(10.0);
        serie.setCategoria(Categoria.ACAO);
        serie.setSinopse("Sinopse de teste para o catálogo Softech-Flix.");

        // 2. Criando e adicionando um novo episódio
        // Parâmetros do construtor: (temporada = 1, titulo = "Pilot", numeroEpisodio = 1, avaliacao = 9.0)
        Episode ep1 = new Episode(1, "Pilot", 1, 9.0);
        serie.addEpisode(ep1);

        // 3. CREATE: Salvando via Service e capturando a entidade persistida
        Serie serieSalva = serieService.salvar(serie);
        System.out.println("\n-> Série salva com sucesso! ID: " + serieSalva.getId() + " | Título: " + serieSalva.getTitle());

        // 4. READ ALL: Listar todas as séries cadastradas
        System.out.println("\n--- LISTA DE SÉRIES CADASTRADAS ---");
        List<Serie> seriesCadastradas = serieService.listarTodas();
        seriesCadastradas.forEach(System.out::println);

        // 5. READ BY ID: Buscar série pelo ID gerado
        System.out.println("\n--- BUSCANDO SÉRIE PELO ID (" + serieSalva.getId() + ") ---");
        Optional<Serie> serieBuscada = serieService.findById(serieSalva.getId());
        serieBuscada.ifPresent(s -> System.out.println("Encontrada: " + s));

        System.out.println("\n==========================================");
        System.out.println("     FLUXO DE EXECUÇÃO CONCLUÍDO!        ");
        System.out.println("==========================================\n");
    }
}
package br.com.softech.controller;

import br.com.softech.dto.DadosSerie;
import br.com.softech.entities.Categoria;
import br.com.softech.entities.Episode;
import br.com.softech.entities.Serie;
import br.com.softech.service.ConsumoAPI;
import br.com.softech.service.ConverteDados;
import br.com.softech.service.SerieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * (Controller / Principal): Classe responsável por orquestrar o fluxo de execução
 * da aplicação no terminal via CommandLineRunner.
 * @Component: Transforma a classe em um componente Bean gerenciado pelo Spring
 * @CommandLineRunner: Interface que indica que a classe deve ser executada automaticamente
 * na inicialização do Spring Boot.
 */
@Component
public class Principal implements CommandLineRunner {

    // 1. Injeção de dependência via Construtor (Excelente prática!)
    private final SerieService serieService;

    // 2. Instâncias auxiliares para leitura do terminal, consumo da API e conversão JSON
    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConverteDados convert = new ConverteDados();

    // 3. Constantes de configuração da API OMDb
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=64165405";

    public Principal(SerieService serieService) {
        this.serieService = serieService;
    }

    // Método executado automaticamente na inicialização do Spring Boot
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n==========================================");
        System.out.println("   SEJA BEM-VINDO AO CATALOGO SOFTECH-FLIX   ");
        System.out.println("==========================================");

        // A- Pedir ao usuário o nome da série
        System.out.print("\nDigite o nome da série para pesquisar na OMDb: ");
        String nomeSerie = leitura.nextLine();

        // B- Consumir a API externa da OMDb com tratamento de espaço/caracteres especiais na URL
        String url = ENDERECO + URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8) + API_KEY;
        String json = consumoAPI.obterDados(url);
        System.out.println("\n-> Resposta JSON da API: ");
        System.out.println(json);

        // C- Converter o JSON recebido para o DTO DadosSerie
        DadosSerie dadosSerie = convert.obterDados(json, DadosSerie.class);

        // Validação caso a série não seja encontrada na OMDb
        if (dadosSerie.title() == null) {
            System.out.println(" Série não encontrada no catálogo da OMDb.");
            return;
        }

        // D- Tratamento da Avaliação e Categoria trazidas da API (a OMDb pode retornar "N/A")
        Double avaliacao;
        try {
            avaliacao = Double.valueOf(dadosSerie.avaliacao());
        } catch (NumberFormatException | NullPointerException e) {
            avaliacao = 0.0;
        }

        // Converte o primeiro gênero vindo da API ("Action, Drama" -> "Action" -> Enum Categoria)
        String primeiroGenero = dadosSerie.categoria().split(",")[0].trim();
        Categoria categoriaEnum = Categoria.fromString(primeiroGenero);

        // E- Mapear o DTO para a Entidade JPA Serie dinamicamente com os dados reais buscados
        Serie serie = new Serie(
                dadosSerie.title(),
                dadosSerie.totalTemporadas(),
                avaliacao,
                categoriaEnum,
                dadosSerie.sinopse()
        );

        // F- Criando e adicionando um episódio de teste na série para testar o relacionamento em cascata
        // Parâmetros do construtor: (temporada = 1, titulo = "Pilot", numeroEpisodio = 1, avaliacao = 9.0)
        Episode ep1 = new Episode(1, "Pilot", 1, 9.0);
        serie.addEpisode(ep1);

        // G- CREATE: Salvando a série no banco via Service e capturando a entidade persistida
        Serie serieSalva = serieService.salvar(serie);
        System.out.println("\n Série salva com sucesso! ID: " + serieSalva.getId() + " | Título: " + serieSalva.getTitle());

        // H- READ ALL: Listar todas as séries cadastradas no banco
        System.out.println("\n--- LISTA DE SÉRIES CADASTRADAS NO BANCO DE DADOS ---");
        List<Serie> seriesCadastradas = serieService.listarTodas();
        seriesCadastradas.forEach(System.out::println);

        // I- READ BY ID: Buscar série pelo ID gerado
        System.out.println("\n--- BUSCANDO SÉRIE PELO ID (" + serieSalva.getId() + ") ---");
        Optional<Serie> serieBuscada = serieService.findById(serieSalva.getId());
        serieBuscada.ifPresent(s -> System.out.println("Encontrada no Banco: " + s));

        System.out.println("\n==========================================");
        System.out.println("     FLUXO DE EXECUÇÃO CONCLUÍDO!        ");
        System.out.println("==========================================\n");
    }
}
```mermaid
classDiagram
    class Principal {
        +main()
    }

    class EjemplosStreams {
        +ejecutarEjemplos()
    }

    class Serie {
        -titulo: String
        -temporadas: List
        -categoria: Categoria
        +getPromedio()
        +getTopEpisodios()
    }

    class Episodio {
        -titulo: String
        -numero: int
        -evaluacion: double
    }

    class Categoria

    class DatosSerie {
        -titulo: String
        -totalTemporadas: int
    }

    class DatosTemporadas {
        -numero: int
        -episodios: List
    }

    class DatosEpisodio {
        -titulo: String
        -avaliacao: String
    }

    class ISerieRepository

    class ScreetmatchApplication

    class ConsumoAPI {
        +obtenerDatos(url)
    }

    class IConvierteDatos {
        +desdeJson(json)
    }

    class ConvierteDatos {
        +desdeJson(json)
    }

    class ConsultaChatGPT {
        +obtenerSugerencia()
    }

    Serie "1" --> "*" Episodio
    ConvierteDatos --|> IConvierteDatos
    Principal --> ConsumoAPI
    Principal --> ConvierteDatos
    Principal --> ISerieRepository
    Serie --> Categoria

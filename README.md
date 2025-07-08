# 🎬 Screetmatch

Screetmatch es una aplicación Java desarrollada en el contexto de los cursos de Alura Latam. Permite consultar, organizar y analizar series utilizando conceptos como consumo de APIs, manipulación de datos, programación orientada a objetos y uso de streams.

## 🚀 Funcionalidades principales

- 🔎 Búsqueda de series por nombre
- 📊 Consulta de datos de temporadas y episodios
- 🎥 Visualización de episodios destacados por evaluación
- 💡 Ejemplos de uso de Streams en Java
- 🧠 Consulta de recomendaciones usando ChatGPT (simulado)

---

## 🛠️ Tecnologías utilizadas

- ☕ Java 17
- 🌐 Consumo de APIs con `HttpClient`
- 📦 Maven (estructura de proyecto)
- 🧰 Librerías:
  - GSON para parseo JSON
  - Interfaces funcionales y Streams
- 🖥️ IDE sugerido: IntelliJ IDEA o Eclipse

---

## 📁 Estructura del proyecto



```
+---model
|       Categoria.java
|       DatosEpisodio.java
|       DatosSerie.java
|       DatosTemporadas.java
|       Episodio.java
|       Serie.java
|       
+---principal
|       EjemplosStreams.java
|       Principal.java
|       
+---repository
|       ISerieRepository.java
|       
+---screetmatch
|       ScreetmatchApplication.java
|       
\---service
        ConsultaChatGPT.java
        ConsumoAPI.java
        ConvierteDatos.java
        IConvierteDatos.java
```

---

## 👨‍🔧 Diagrama de clases
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

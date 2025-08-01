package com.aluracursos.model;

public enum Categoria {
    ACCION ("Action","Acción"),
    ROMANCE ("Romance" , "Romance"),
    COMEDIA ("Comedy", "Comedia"),
    DRAMA ("Drama", "Drama"),
    CRIMEN ("Crime", "Crimen");

    private String categoriaOmdb;
    private String categoriaEspañol;

    //Constructor
    Categoria(String categoriaOmdb, String categoriaEspañol){

        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspañol = categoriaEspañol;
    }

    //Zona de mensaje
    public static Categoria fromString(String text){
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada:" + text);
    }

    public static Categoria fromEspanol(String text){
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEspañol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada:" + text);
    }
}

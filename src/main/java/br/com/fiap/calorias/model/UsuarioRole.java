package br.com.fiap.calorias.model;

// ENUM = representa um grupo fixo de constantes;
// valores strings = valores das constantes que serão salvos no bd
// cada ENUM precisa de um local para armazenar o valor interno = role
// ao iniciar o valor da constante é passado para dentro do contrutor
// contrutor é implicidamente PRIVATE

// alternativa a este codigo é fazer sem imbutir valor string ao contrutor
// e usar metodo nativo NAME() na hora de chmar o metodo
// ele retornará o valor da constante criada em String

public enum UsuarioRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    UsuarioRole(String role){
        this.role = role;
    }

    public String getRole(){
        return  this.role;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

/**
 *
 * @author rafaelcadena
 */
public enum TipoTransacao {
    CRIACAO("Criação"), REMOVEU("Removeu"), EDICAO("Edição"), ADICIONOU("Adicionou");
    
    private String tipo;

    TipoTransacao(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author rafaeljcadena
 */
public class Auditoria {

    private int id;
    private Produto produto;
    private Usuario usuario;
    private int quantidadeProduto;
    private Timestamp dataOperacao;
    private String tipoTransacao;

    public Auditoria() {
        this.produto = new Produto();
        this.usuario = new Usuario();

    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(int quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public Date getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(Timestamp dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }
    
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

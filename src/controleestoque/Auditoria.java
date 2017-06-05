/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author rafaeljcadena
 */
public class Auditoria {

    private int id;
    private int usuarioId;
    private int produtoId;
    private int quantidadeProduto;

    private Timestamp dataOperacao;
    private String tipoTransacao;

    public Auditoria() {

    }

    public Auditoria(int id, int usuarioId, int produtoId, int quantidadeProduto, Timestamp dataOperacao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.produtoId = produtoId;
        this.quantidadeProduto = quantidadeProduto;
        this.dataOperacao = dataOperacao;
//        this.tipoTransacao = tipoTransacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

/**
 *
 * @author rafaeljcadena
 */
public class Produto {

    private int id;
    private String nome;
    private float preco;
    private int quantidade;
    private String descricao;
    private int quantidadeOperacao;

    public Produto() {

    }
//    public Produto(Produto produto){
//        descricao = produto.descricao;
//        id = produto.id;
//        nome = produto.nome;
//        preco = produto.preco;
//        quantidade = produto.quantidade;
//        quantidadeOperacao = produto.quantidadeOperacao;
//    }

    public Produto(String nome, float preco, int quantidade, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
    }

    public Produto(int id, String nome, float preco, int quantidade, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public float getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidadeOperacao() {
        return quantidadeOperacao;
    }
    
    

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantidadeOperacao(int quantidadeOperacao) {
        this.quantidadeOperacao = quantidadeOperacao;
    }
    
    

}

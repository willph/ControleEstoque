/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kubuntuhp
 */
public class NegocioProduto extends Conexao {
    

    public void cadastrar(Produto novoProduto) throws Exception {
        if (novoProduto.getNome().equals("")) {
            throw new Exception("Informar o nome do produto.");
        } else if (novoProduto.getPreco() <= 0.0) {
            throw new Exception("O preco deve ser maior que zero.");
        } else if (novoProduto.getDescricao().equals("")) {
            throw new Exception("Informar a descricao do produto.");
        } else if(!validarProduto(novoProduto.getNome())){
            throw new Exception("Produto já existe. Por favor escolha outro nome");
        } else {
            DadosProduto dadosProduto = new DadosProduto();
            dadosProduto.cadastrar(novoProduto);
        }
    }
    
    public void remover(int id, int quantidadeProduto) throws Exception {
        if (quantidadeProduto > 0) {
                throw new Exception("Operação não permitida: a quantidade precisa estar zerada.");
            } else {
                DadosProduto dados = new DadosProduto();
                dados.remover(id);
            }
    }
    
    public void atualizar(Produto produto, int quantidadeTotalInicial){
        int quantidadeTransacao = 0;
        DadosProduto dados = new DadosProduto();
        String tipoTransacao = "";
        DadosAuditoria dadosAuditoria = new DadosAuditoria(stmt);
//        UtilsDbAuditoria dadosAuditoria = new UtilsDbAuditoria(stmt)
        
        if (quantidadeTotalInicial >= produto.getQuantidade() && (quantidadeTotalInicial - produto.getQuantidade()) != 0) {
            quantidadeTransacao = quantidadeTotalInicial - produto.getQuantidade();
            tipoTransacao = "saida";
//            dadosAuditoria.cadastrarAuditoria(produto, "saída", quantidadeTransacao);
        } else if (quantidadeTotalInicial <= produto.getQuantidade() && (produto.getQuantidade() - quantidadeTotalInicial) != 0) {
            quantidadeTransacao = produto.getQuantidade() - quantidadeTotalInicial;
            tipoTransacao = "entrada";
//            dados.transacao(produto, "entrada", quantidadeTransacao);
        }
        dados.atualizar(produto);
        dadosAuditoria.cadastrarAuditoria(produto, tipoTransacao, quantidadeTransacao);
    }
    
    public void validaEntradaSaida(Produto produto, String comboBoxSelected, int quantidadeTransacao) throws Exception{
        if (comboBoxSelected.equals("Entrada") == true) {
            if (quantidadeTransacao < 0) {
                throw new Exception("A quantidade nao pode ser menor que zero.");
            }
            produto.setQuantidade(produto.getQuantidade() + quantidadeTransacao);
        } else {
            if (quantidadeTransacao < 0) {
                throw new Exception("A quantidade nao pode ser menor que zero.");
            }
            
            
            if (quantidadeTransacao <= produto.getQuantidade()) {
                produto.setQuantidade(produto.getQuantidade() - quantidadeTransacao);
            } else {
//                JOptionPane.showMessageDialog(null, "A quantidade de retirada não pode ser maior que a disponível.");
                throw new Exception("A quantidade de retirada não pode ser maior que a disponível.");
            }
        }
    }
    
    public boolean validarProduto(String nome) throws Exception{
        boolean valido = false;
        
        try {
            stmt = conectar();
            String sql = "SELECT * FROM produto WHERE nome='" + nome + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) {
                valido = true;
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valido;

    }

}

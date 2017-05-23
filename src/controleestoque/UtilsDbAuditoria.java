/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rafaeljcadena
 */
public class UtilsDbAuditoria {
    
    private Statement stmt;
    private int usuarioId;
    private  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    public UtilsDbAuditoria(Statement stmt) {
        this.stmt = stmt;
        this.usuarioId = Login.getUsuarioId();
    }
    
    public void cadastroAuditoria(Produto novoProduto, TipoTransacao tipo) {
        Date date = Date.from(Instant.now());
        String data = dateFormat.format(date);
        
        int produtoId = buscarId(novoProduto.getNome());
        try {
            //abrindo a conexão
            Statement conex = stmt;
            //instrução sql correspondente a inserção da auditoria
            String sql = "INSERT INTO auditoria (usuario_id, produto_id, data, quantidadeProdutoInicial, transacaoTipo) VALUES (%d, %d, '%s', %d, '%s');";
            String sqlFormated = String.format(sql, usuarioId, produtoId, data, novoProduto.getQuantidade(), tipo.getTipo());
            
            try {
                //executando a instrução sql
                conex.executeUpdate(sqlFormated);
            } catch (SQLException e) {
                //caso haja algum erro neste método será levantada esta execeção
                throw new Exception("Erro ao executar inserção: " + e.getMessage());
            }   
        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void remocaoAuditoria(Produto novoProduto, TipoTransacao tipo, Statement stmt) {
        Date date = Date.from(Instant.now());
        String data = dateFormat.format(date);
        
        int produtoId = buscarId(novoProduto.getNome());
        try {
            //abrindo a conexão
            Statement conex = stmt;
            //instrução sql correspondente a inserção da auditoria
            String sql = "INSERT INTO auditoria (usuario_id, produto_id, data, quantidadeProdutoInicial, transacaoTipo, quantidadeProdutoFinal) VALUES (%d, %d, '%s', %d, '%s', %d);";
            String sqlFormated = String.format(sql, usuarioId, produtoId, data, novoProduto.getQuantidade(), tipo.getTipo(), 0);
            
            try {
                //executando a instrução sql
                conex.executeUpdate(sqlFormated);
            } catch (SQLException e) {
                //caso haja algum erro neste método será levantada esta execeção
                throw new Exception("Erro ao executar inserção: " + e.getMessage());
            }   
        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void edicaoAuditoria(Produto novoProduto, int quantidadeInicial, TipoTransacao tipo, Statement stmt) {
        Date date = Date.from(Instant.now());
        String data = dateFormat.format(date);
        
        int produtoId = buscarId(novoProduto.getNome());
        try {
            //abrindo a conexão
            Statement conex = stmt;
            //instrução sql correspondente a inserção da auditoria
            String sql = "INSERT INTO auditoria (usuario_id, produto_id, data, quantidadeProdutoInicial, transacaoTipo, quantidadeProdutoFinal) VALUES (%d, %d, '%s', %d, '%s', %d);";
            String sqlFormated = String.format(sql, usuarioId, produtoId, data, quantidadeInicial, tipo.getTipo(), novoProduto.getQuantidade());
            
            try {
                //executando a instrução sql
                conex.executeUpdate(sqlFormated);
            } catch (SQLException e) {
                //caso haja algum erro neste método será levantada esta execeção
                throw new Exception("Erro ao executar inserção: " + e.getMessage());
            }   
        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public int buscarId(String nome) {
        int produtoId = 0;
        try {
            // TODO add your handling code here:
            
            //abrindo a conexão
            Statement conex = stmt;
            //instrução sql correspondente a seleção do produto
            String sql = "SELECT id FROM produto WHERE nome = '" + nome + "'";

            //executando a instrução sql
            ResultSet rs = conex.executeQuery(sql);
            while (rs.next()) {
                produtoId = rs.getInt("id");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return produtoId;
    }
    
}

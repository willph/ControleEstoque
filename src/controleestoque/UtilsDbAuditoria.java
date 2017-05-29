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

    private final Statement stmt;
    private final int usuarioId;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public UtilsDbAuditoria(Statement stmt) {
        this.stmt = stmt;
        usuarioId = Login.getId();
    }

    public void cadastrarAuditoria(Produto novoProduto, String transacaoTipo) {
        Date date = Date.from(Instant.now());
        String data = dateFormat.format(date);

        int produtoId = buscarId(novoProduto.getNome());
        try {
            //abrindo a conexão
            Statement conex = stmt;
            //instrução sql correspondente a inserção da auditoria
            String sql = "INSERT INTO auditoria (usuario_id, produto_id, data, quantidadeProduto, transacaoTipo) VALUES (%d, %d, '%s', %d, '%s');";
            String sqlFormated = String.format(sql, usuarioId, produtoId, data, novoProduto.getQuantidade(), transacaoTipo);

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

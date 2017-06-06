/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 *
 * @author rafaeljcadena
 */
public class UtilsDbAuditoria {

    private final Statement stmt;
    private final Usuario usuarioLogado;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public UtilsDbAuditoria(Statement stmt) {
        this.stmt = stmt;
        usuarioLogado = Login.getUsuarioLogado();
    }

    public void cadastrarAuditoria(Produto novoProduto, String transacaoTipo) {
        Date date = Date.from(Instant.now());
        String data = dateFormat.format(date);

//        int produtoId = new DadosProduto().buscarId(novoProduto.getNome());
        int produtoId = novoProduto.getId();
        try {
            //abrindo a conexão
            Statement conex = stmt;
            //instrução sql correspondente a inserção da auditoria
            String sql = "INSERT INTO auditoria (usuario_id, produto_id, data, quantidadeProduto, transacaoTipo) VALUES (%d, %d, '%s', %d, '%s');";
            String sqlFormated = String.format(sql, usuarioLogado.getId(), produtoId, data, novoProduto.getQuantidadeOperacao(), transacaoTipo);

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

}

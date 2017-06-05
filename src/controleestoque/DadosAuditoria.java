/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo
 */
public class DadosAuditoria extends Conexao {

    public ArrayList<Auditoria> consultaId(int produtoId, int usuarioId) throws Exception {
        ArrayList<Auditoria> retorno = new ArrayList<>();
        if ((produtoId != 0 || usuarioId != 0)) {
            String sql = "";
            //abrindo a conexão
            stmt = conectar();
            //instrução sql correspondente a seleção dos alunos
            if (usuarioId == 0) {
                if (produtoId != 0) {
                    sql = "SELECT * FROM auditoria WHERE produto_id = " + produtoId;
                }
            } else {
                if (produtoId != 0) {
                    sql = "SELECT * FROM auditoria WHERE produto_id = " + produtoId + " and usuario_id = " + usuarioId;
                } else {
                    sql = "SELECT * FROM auditoria WHERE usuario_id = " + usuarioId;
                }
            }
            //executando a instrução sql
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Auditoria auditoria = new Auditoria();
                auditoria.setId(rs.getInt("id"));
                auditoria.setUsuarioId(rs.getInt("usuario_id"));
                auditoria.setProdutoId(rs.getInt("produto_id"));
                auditoria.setQuantidadeProduto(rs.getInt("quantidadeProduto"));
                auditoria.setDataOperacao((rs.getTimestamp("data")));
                auditoria.setTipoTransacao(rs.getString("transacaoTipo"));
                retorno.add(auditoria);
            }
            desconectar();
        }
        //fechando a conexão com o banco de dados
        return retorno;
        
    }

    public ArrayList<Auditoria> listar() throws Exception {
        ArrayList<Auditoria> retorno = new ArrayList<>();
        //abrindo a conexão
        stmt = conectar();
        //instrução sql correspondente a seleção dos alunos
        String sql = "SELECT * FROM auditoria";
        //executando a instrução sql
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Auditoria a = new Auditoria();
            a.setId(rs.getInt("id"));
            a.setUsuarioId(rs.getInt("usuario_id"));
            a.setProdutoId(rs.getInt("produto_id"));
            a.setQuantidadeProduto(rs.getInt("quantidadeProduto"));
            a.setDataOperacao(rs.getTimestamp("data"));
            a.setTipoTransacao(rs.getString("transacaoTipo"));
            retorno.add(a);

        }
        //fechando a conexão com o banco de dados
        desconectar();
        return retorno;
    }
}

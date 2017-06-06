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

//    private Auditoria auditoria = new Auditoria();
    public ArrayList<Auditoria> consultaId(Produto produto, Usuario usuario) throws Exception {
        DadosProduto dadosProduto = new DadosProduto();
        DadosUsuario dadosUsuario = new DadosUsuario();
        ArrayList<Auditoria> retorno = new ArrayList<>();
        if ((produto.getId() != 0 || usuario.getId() != 0)) {
            String sql = "";
            //abrindo a conexão
            stmt = conectar();
            //instrução sql correspondente a seleção dos alunos
            if (usuario.getId() == 0) {
                if (produto.getId() != 0) {
                    sql = "SELECT * FROM auditoria WHERE produto_id = " + produto.getId();
                }
            } else {
                if (produto.getId() != 0) {
                    sql = "SELECT * FROM auditoria WHERE produto_id = " + produto.getId() + " and usuario_id = " + usuario.getId();
                } else {
                    sql = "SELECT * FROM auditoria WHERE usuario_id = " + usuario.getId();
                }
            }
            //executando a instrução sql
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Auditoria auditoria = new Auditoria();

                auditoria.setId(rs.getInt("id"));
//                auditoria.setUsuarioId(rs.getInt("usuario_id"));
                auditoria.setProduto(dadosProduto.buscarProdutoPorId(rs.getInt("produto_id")));
                auditoria.setUsuario(dadosUsuario.buscarUsuarioPorId(rs.getInt("usuario_id")));
//                auditoria.setProdutoId(rs.getInt("produto_id"));
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
        DadosProduto dadosProduto = new DadosProduto();
        DadosUsuario dadosUsuario = new DadosUsuario();
        ArrayList<Auditoria> retorno = new ArrayList<>();
        //abrindo a conexão
        stmt = conectar();
        //instrução sql correspondente a seleção dos alunos
        String sql = "SELECT * FROM auditoria";
        //executando a instrução sql
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Auditoria auditoria = new Auditoria();

            auditoria.setId(rs.getInt("id"));
//                auditoria.setUsuarioId(rs.getInt("usuario_id"));
            auditoria.setProduto(dadosProduto.buscarProdutoPorId(rs.getInt("produto_id")));
            auditoria.setUsuario(dadosUsuario.buscarUsuarioPorId(rs.getInt("usuario_id")));
//                auditoria.setProdutoId(rs.getInt("produto_id"));
            auditoria.setQuantidadeProduto(rs.getInt("quantidadeProduto"));
            auditoria.setDataOperacao((rs.getTimestamp("data")));
            auditoria.setTipoTransacao(rs.getString("transacaoTipo"));
            retorno.add(auditoria);

        }
        //fechando a conexão com o banco de dados
        desconectar();
        return retorno;
    }
}

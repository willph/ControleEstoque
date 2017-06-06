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
    
    private final DadosProduto dadosProduto = new DadosProduto();
    private final DadosUsuario dadosUsuario = new DadosUsuario();

    public ArrayList<Auditoria> consultaId(Produto produto, Usuario usuario) throws Exception {

        ArrayList<Auditoria> retorno = new ArrayList<>();
        if ((produto.getId() != 0 || usuario.getId() != 0)) {
            String sql = "";
            //abrindo a conexão
            stmt = conectar();
            
//            if (usuario.getId() == 0) {
//                if (produto.getId() != 0) {
//                    sql = "SELECT * FROM auditoria WHERE produto_id = " + produto.getId();
//                }
//            } else {
//                if (produto.getId() != 0) {
//                    sql = "SELECT * FROM auditoria WHERE produto_id = " + produto.getId() + " and usuario_id = " + usuario.getId();
//                } else {
//                    sql = "SELECT * FROM auditoria WHERE usuario_id = " + usuario.getId();
//                }
//            }
            
            if (usuario.getId() == 0) {
                if (produto.getId() != 0) {
                    sql = "select a.id as auditoria_id, a.quantidadeProduto as auditoria_quantidadeProduto, a.data as auditoria_data, a.transacaoTipo as auditoria_transacaoTipo, "
                            + "p.id as produto_id, p.nome as produto_nome, p.preco as produto_preco, p.qtd as produto_quantidade, p.tipoProduto as produto_descricao, "
                            + "u.id as usuario_id, u.nome as usuario_nome, u.login as usuario_login, u.senha as usuario_senha, u.privilegio as usuario_privilegio "
                            + "from auditoria as a inner join produto as p on p.id = a.produto_id inner join usuario as u on u.id = a.usuario_id "
                            + "where p.id = " + produto.getId() + " ;";
                }
            } else {
                if (produto.getId() != 0) {
                    sql = "select a.id as auditoria_id, a.quantidadeProduto as auditoria_quantidadeProduto, a.data as auditoria_data, a.transacaoTipo as auditoria_transacaoTipo, "
                            + "p.id as produto_id, p.nome as produto_nome, p.preco as produto_preco, p.qtd as produto_quantidade, p.tipoProduto as produto_descricao, "
                            + "u.id as usuario_id, u.nome as usuario_nome, u.login as usuario_login, u.senha as usuario_senha, u.privilegio as usuario_privilegio "
                            + "from auditoria as a inner join produto as p on p.id = a.produto_id inner join usuario as u on u.id = a.usuario_id "
                            + "where p.id = " + produto.getId() + " and u.id = " + usuario.getId() + " ;";
                } else {
                    sql = "select a.id as auditoria_id, a.quantidadeProduto as auditoria_quantidadeProduto, a.data as auditoria_data, a.transacaoTipo as auditoria_transacaoTipo, "
                            + "p.id as produto_id, p.nome as produto_nome, p.preco as produto_preco, p.qtd as produto_quantidade, p.tipoProduto as produto_descricao, "
                            + "u.id as usuario_id, u.nome as usuario_nome, u.login as usuario_login, u.senha as usuario_senha, u.privilegio as usuario_privilegio "
                            + "from auditoria as a inner join produto as p on p.id = a.produto_id inner join usuario as u on u.id = a.usuario_id "
                            + "where u.id = " + usuario.getId() + " ;";
                }
            }
            
            //executando a instrução sql
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Auditoria auditoria = new Auditoria();

                auditoria.setId(rs.getInt("auditoria_id"));
        //                auditoria.setUsuarioId(rs.getInt("usuario_id"));
                auditoria.setProduto(new Produto(rs.getInt("produto_id"), rs.getString("produto_nome"), rs.getFloat("produto_preco"), rs.getInt("produto_quantidade"), rs.getString("produto_descricao")));
                auditoria.setUsuario(new Usuario(rs.getInt("usuario_id"), rs.getString("usuario_nome"), rs.getString("usuario_login"), rs.getString("usuario_privilegio")));
        //                auditoria.setProdutoId(rs.getInt("produto_id"));
                auditoria.setQuantidadeProduto(rs.getInt("auditoria_quantidadeProduto"));
                auditoria.setDataOperacao((rs.getTimestamp("auditoria_data")));
                auditoria.setTipoTransacao(rs.getString("auditoria_transacaoTipo"));
                retorno.add(auditoria);
                
                
//                Auditoria auditoria = new Auditoria();
//
//                auditoria.setId(rs.getInt("id"));
////                auditoria.setUsuarioId(rs.getInt("usuario_id"));
//                auditoria.setProduto(dadosProduto.buscarProdutoPorId(rs.getInt("produto_id")));
//                auditoria.setUsuario(dadosUsuario.buscarUsuarioPorId(rs.getInt("usuario_id")));
////                auditoria.setProdutoId(rs.getInt("produto_id"));
//                auditoria.setQuantidadeProduto(rs.getInt("quantidadeProduto"));
//                auditoria.setDataOperacao((rs.getTimestamp("data")));
//                auditoria.setTipoTransacao(rs.getString("transacaoTipo"));
//                retorno.add(auditoria);                
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
//        String sql = "SELECT * FROM auditoria";
        String sql;
        sql = "select a.id as auditoria_id, a.quantidadeProduto as auditoria_quantidadeProduto, a.data as auditoria_data, a.transacaoTipo as auditoria_transacaoTipo, "
        + "p.id as produto_id, p.nome as produto_nome, p.preco as produto_preco, p.qtd as produto_quantidade, p.tipoProduto as produto_descricao, "
        + "u.id as usuario_id, u.nome as usuario_nome, u.login as usuario_login, u.senha as usuario_senha, u.privilegio as usuario_privilegio "
        + "from auditoria as a inner join produto as p on p.id = a.produto_id inner join usuario as u on u.id = a.usuario_id;";
        //executando a instrução sql
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Auditoria auditoria = new Auditoria();

            auditoria.setId(rs.getInt("auditoria_id"));
//                auditoria.setUsuarioId(rs.getInt("usuario_id"));
            auditoria.setProduto(new Produto(rs.getInt("produto_id"), rs.getString("produto_nome"), rs.getFloat("produto_preco"), rs.getInt("produto_quantidade"), rs.getString("produto_descricao")));
            auditoria.setUsuario(new Usuario(rs.getInt("usuario_id"), rs.getString("usuario_nome"), rs.getString("usuario_login"), rs.getString("usuario_privilegio")));
//                auditoria.setProdutoId(rs.getInt("produto_id"));
            auditoria.setQuantidadeProduto(rs.getInt("auditoria_quantidadeProduto"));
            auditoria.setDataOperacao((rs.getTimestamp("auditoria_data")));
            auditoria.setTipoTransacao(rs.getString("auditoria_transacaoTipo"));
            retorno.add(auditoria);

            
//            Auditoria auditoria = new Auditoria();
//
//            auditoria.setId(rs.getInt("id"));
////                auditoria.setUsuarioId(rs.getInt("usuario_id"));
//            auditoria.setProduto(dadosProduto.buscarProdutoPorId(rs.getInt("produto_id")));
//            auditoria.setUsuario(dadosUsuario.buscarUsuarioPorId(rs.getInt("usuario_id")));
////                auditoria.setProdutoId(rs.getInt("produto_id"));
//            auditoria.setQuantidadeProduto(rs.getInt("quantidadeProduto"));
//            auditoria.setDataOperacao((rs.getTimestamp("data")));
//            auditoria.setTipoTransacao(rs.getString("transacaoTipo"));
//            retorno.add(auditoria);
            
        }
        //fechando a conexão com o banco de dados
        desconectar();
        return retorno;
    }
}

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
    private Usuario usuarioLogado;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//    private Statement stmt;
    
    
    public DadosAuditoria(Statement stmt){
        this.stmt = stmt;
        this.usuarioLogado = Login.getUsuarioLogado();
    }
    
    public DadosAuditoria() {
        usuarioLogado = Login.getUsuarioLogado();
    }

    public ArrayList<Auditoria> consultaId(String produtoNome, String usuarioLogin) throws Exception {

        ArrayList<Auditoria> retorno = new ArrayList<>();
        if (!produtoNome.equals("") || !usuarioLogin.equals("")) {
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
            
            if (usuarioLogin.equals("")) {
                if (!produtoNome.equals("")) {
                    sql = "select a.id as auditoria_id, a.quantidadeProduto as auditoria_quantidadeProduto, a.data as auditoria_data, a.transacaoTipo as auditoria_transacaoTipo, "
                            + "p.id as produto_id, p.nome as produto_nome, p.preco as produto_preco, p.quantidade as produto_quantidade, p.descricao as produto_descricao, "
                            + "u.id as usuario_id, u.nome as usuario_nome, u.login as usuario_login, u.senha as usuario_senha, u.privilegio as usuario_privilegio "
                            + "from auditoria as a inner join produto as p on p.id = a.produto_id inner join usuario as u on u.id = a.usuario_id "
                            + "where p.nome = '" + produtoNome + "' ;";
                }
            } else {
                if (!produtoNome.equals("")) {
                    sql = "select a.id as auditoria_id, a.quantidadeProduto as auditoria_quantidadeProduto, a.data as auditoria_data, a.transacaoTipo as auditoria_transacaoTipo, "
                            + "p.id as produto_id, p.nome as produto_nome, p.preco as produto_preco, p.quantidade as produto_quantidade, p.descricao as produto_descricao, "
                            + "u.id as usuario_id, u.nome as usuario_nome, u.login as usuario_login, u.senha as usuario_senha, u.privilegio as usuario_privilegio "
                            + "from auditoria as a inner join produto as p on p.id = a.produto_id inner join usuario as u on u.id = a.usuario_id "
                            + "where p.nome = '" + produtoNome + "' and u.login = '" + usuarioLogin + "' ;";
                } else {
                    sql = "select a.id as auditoria_id, a.quantidadeProduto as auditoria_quantidadeProduto, a.data as auditoria_data, a.transacaoTipo as auditoria_transacaoTipo, "
                            + "p.id as produto_id, p.nome as produto_nome, p.preco as produto_preco, p.quantidade as produto_quantidade, p.descricao as produto_descricao, "
                            + "u.id as usuario_id, u.nome as usuario_nome, u.login as usuario_login, u.senha as usuario_senha, u.privilegio as usuario_privilegio "
                            + "from auditoria as a inner join produto as p on p.id = a.produto_id inner join usuario as u on u.id = a.usuario_id "
                            + "where u.login = '" + usuarioLogin + "';";
                }
            }
            
            //executando a instrução sql
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Auditoria auditoria = new Auditoria();

                auditoria.setId(rs.getInt("auditoria_id"));
                auditoria.setProduto(new Produto(rs.getInt("produto_id"), rs.getString("produto_nome"), rs.getFloat("produto_preco"), rs.getInt("produto_quantidade"), rs.getString("produto_descricao")));
                auditoria.setUsuario(new Usuario(rs.getInt("usuario_id"), rs.getString("usuario_nome"), rs.getString("usuario_login"), rs.getString("usuario_privilegio")));
                auditoria.setQuantidadeProduto(rs.getInt("auditoria_quantidadeProduto"));
                auditoria.setDataOperacao((rs.getTimestamp("auditoria_data")));
                auditoria.setTipoTransacao(rs.getString("auditoria_transacaoTipo"));
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
//        String sql = "SELECT * FROM auditoria";
        String sql;
        sql = "select a.id as auditoria_id, a.quantidadeProduto as auditoria_quantidadeProduto, a.data as auditoria_data, a.transacaoTipo as auditoria_transacaoTipo, "
        + "p.id as produto_id, p.nome as produto_nome, p.preco as produto_preco, p.quantidade as produto_quantidade, p.descricao as produto_descricao, "
        + "u.id as usuario_id, u.nome as usuario_nome, u.login as usuario_login, u.senha as usuario_senha, u.privilegio as usuario_privilegio "
        + "from auditoria as a inner join produto as p on p.id = a.produto_id inner join usuario as u on u.id = a.usuario_id;";
        //executando a instrução sql
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Auditoria auditoria = new Auditoria();

            auditoria.setId(rs.getInt("auditoria_id"));
            auditoria.setProduto(new Produto(rs.getInt("produto_id"), rs.getString("produto_nome"), rs.getFloat("produto_preco"), rs.getInt("produto_quantidade"), rs.getString("produto_descricao")));
            auditoria.setUsuario(new Usuario(rs.getInt("usuario_id"), rs.getString("usuario_nome"), rs.getString("usuario_login"), rs.getString("usuario_privilegio")));
            auditoria.setQuantidadeProduto(rs.getInt("auditoria_quantidadeProduto"));
            auditoria.setDataOperacao((rs.getTimestamp("auditoria_data")));
            auditoria.setTipoTransacao(rs.getString("auditoria_transacaoTipo"));
            retorno.add(auditoria);


            
        }
        //fechando a conexão com o banco de dados
        desconectar();
        return retorno;
    }
    
    public void cadastrarAuditoria(Produto novoProduto, String transacaoTipo, int quantidadeTransacao) {
        Date date = Date.from(Instant.now());
        String data = dateFormat.format(date);

        int produtoId = novoProduto.getId();
        try {
            //abrindo a conexão
            Statement conex = conectar();
            //instrução sql correspondente a inserção da auditoria
            String sql = "INSERT INTO auditoria (usuario_id, produto_id, data, quantidadeProduto, transacaoTipo) VALUES (%d, %d, '%s', %d, '%s');";
            String sqlFormated = String.format(sql, usuarioLogado.getId(), produtoId, data, quantidadeTransacao, transacaoTipo);
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

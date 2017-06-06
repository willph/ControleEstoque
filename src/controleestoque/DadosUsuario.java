/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo
 */
public class DadosUsuario extends Conexao {

    public void cadastrar(Usuario novoUsuario) {
        Conexao conexao = new Conexao();
        Statement conex;
        try {
            conex = conexao.conectar();
            String sql = "INSERT INTO usuario (nome, login, senha, privilegio) VALUES ('"
                    + novoUsuario.getNome() + "', '"
                    + novoUsuario.getLogin() + "', "
                    + novoUsuario.getSenha() + ", '"
                    + novoUsuario.getPrivilegio() + "' )";
            conex.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DadosUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int buscarId(String login) throws Exception {
        int usuarioId = 0;
        try {
            stmt = conectar();
            String sql = "SELECT id FROM usuario WHERE login LIKE '" + login + "'";
            //executando a instrução sql
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                usuarioId = rs.getInt("id");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DadosUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (usuarioId == 0 && login.equals("") == false) {
            throw new Exception("Usuário inexistente.");
        }
        return usuarioId;
    }

//    public String buscarLogin(int id) throws Exception {
//        String usuarioLogin = "";
//        try {
//            //abrindo a conexão
//            stmt = conectar();
//            //instrução sql correspondente a seleção do produto
//            String sql = "SELECT login FROM usuario WHERE id = " + id;
//
//            //executando a instrução sql
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                usuarioLogin = rs.getString("login");
//            }
//            desconectar();
//        } catch (ClassNotFoundException | SQLException ex) {
//            throw new Exception(ex.getMessage());
//        }
//        return usuarioLogin;
//    }

    public Usuario buscarUsuario(String login) {
        Usuario usuario = new Usuario();
        if (login.equals("") == false) {
            try {

                //abrindo a conexão
                stmt = conectar();
                //instrução sql correspondente a seleção do produto
                String sql = "SELECT * FROM usuario WHERE login = '" + login + "'";

                //executando a instrução sql
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {

                    usuario.setId(rs.getInt("id"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setNome(rs.getString("nome"));
//            usuario.setLogin(rs.getString("login"));
                    usuario.setPrivilegio(rs.getString("privilegio"));
                }
                desconectar();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(DadosUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            return usuario;
        }else{
            usuario.setId(0);
            return usuario;
        }
        
    }
    
    public Usuario buscarUsuarioPorId(int id) {
        Usuario usuario = new Usuario();
            try {
                stmt = conectar();
                String sql = "SELECT * FROM usuario WHERE id = " + id;
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
//                produtoNome = rs.getString("nome");
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPrivilegio(rs.getString("privilegio"));
                }
                desconectar();

            } catch (CommunicationsException ex) {
                throw new UnsupportedOperationException("Servidor RGBD pode estar off-line", ex);

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(DadosProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return usuario;
    }

}

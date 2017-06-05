/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

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
    
    public int buscarId(String login) {
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
        if (usuarioId==0 && login.equals("")==false){
            throw new UnsupportedOperationException("Usuário inexistente.");
        }
        return usuarioId;
    }
    
    public String buscarLogin(int id) throws Exception {
        String usuarioLogin = "";
        try {
            //abrindo a conexão
            stmt = conectar();
            //instrução sql correspondente a seleção do produto
            String sql = "SELECT login FROM usuario WHERE id = " + id;

            //executando a instrução sql
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                usuarioLogin = rs.getString("login");
            }
            desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            throw new Exception(ex.getMessage());
        }
        return usuarioLogin;
    }
}

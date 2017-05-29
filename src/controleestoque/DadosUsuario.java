/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

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
            String sql = "INSERT INTO usuario (login, senha, nome, privilegio) ";
            sql += "VALUES ('"
                    + novoUsuario.getLogin() + "', '"
                    + novoUsuario.getSenha() + "', '"
                    + novoUsuario.getNome() + "', '"
                    + novoUsuario.getPrivilegio() + "' )";
            
            conex.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DadosUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo
 */
public class Login extends Conexao {

    private static Usuario usuarioLogado;

    public boolean entrar (String nome, String senha) {

        try {
            Statement conex = this.conectar();
            String sql = "SELECT * FROM usuario WHERE login='" + nome + "'";
            ResultSet rs = conex.executeQuery(sql);

            if (!rs.first()) {
                throw new UnsupportedOperationException("Usuário incorreto.");

            } else if (rs.getString("senha").equals(senha) == false) {
                throw new UnsupportedOperationException("Senha incorreta.");
                
            } else {
                new FormPrincipal(rs.getString("privilegio")).setVisible(true);
                usuarioLogado = new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("login"), rs.getString("privilegio"));
            }
        }catch(CommunicationsException ex){
            throw new UnsupportedOperationException("Servidor RGBD pode estar off-line", ex);
            
        }catch (MySQLSyntaxErrorException ex) {
            throw new UnsupportedOperationException("Banco de dados \n" + ex.getMessage());

        }catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean validar(String login) {
        try {
            Statement conex = this.conectar();
            String sql = "SELECT * FROM usuario WHERE login='" + login + "'";
            ResultSet rs = conex.executeQuery(sql);

            if (!rs.first()) {

            } else {
                
                return false;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);

        }
        return true;

    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    
}

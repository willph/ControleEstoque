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
import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo
 */
public class Login extends Conexao{
    
    static String privilegio;
    static int id;
    
    public boolean entrar(String nome, String senha) {

         try {
            Statement conex = this.conectar();
            String sql = "SELECT * FROM usuario WHERE login='" + nome + "'";
            ResultSet rs = conex.executeQuery(sql);
            
            if (!rs.first()) {
                JOptionPane.showMessageDialog(null, "Usuário incorreto.");
                return false;
            } else if (rs.getString("senha").equals(senha) == false) {
                JOptionPane.showMessageDialog(null, "Senha incorreta.");
                return false;
            } else {
                new FormPrincipal(rs.getString("privilegio")).setVisible(true);
                privilegio = rs.getString("privilegio");
                id = rs.getInt("id");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
         return true;
    }
}

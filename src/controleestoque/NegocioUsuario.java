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
 * @author kubuntuhp
 */
public class NegocioUsuario extends Conexao{

    public void cadastra(Usuario usuario, FormNovoUsuario form) throws Exception {
        DadosUsuario dados = new DadosUsuario();


        if (usuario.getNome().equals("")) {
            throw new Exception("Informar o nome do usuario.");
            
        } else if (usuario.getLogin().equals("")) {
            throw new Exception("Informar o login do usuario.");
        } else if (usuario.getSenha().equals("")) {
            throw new Exception("Informar a senha do usuario.");
        } else if (!validar(usuario.getLogin())){
            throw new Exception("Login ja existe. Por favor escolha outro");
        } else  {
            DadosUsuario dadosUsuario = new DadosUsuario();
            dadosUsuario.cadastrar(usuario);
        }
    
        
        
        
//        if (login.validar(jTextFieldLogin.getText()) == true) {
//            if (jTextFieldNome.getText().equals("") == true || jTextFieldLogin.getText().equals("") == true || jTextFieldSenha.getText().equals("") == true) {
//                JOptionPane.showMessageDialog(rootPane, "Nenhum campo pode ficar em branco.");
//            } else {
//                Usuario usuario = new Usuario();
//                usuario.setNome(jTextFieldNome.getText());
//                usuario.setLogin(jTextFieldLogin.getText());
//                usuario.setSenha(jTextFieldSenha.getText());
//                usuario.setPrivilegio(jComboBox1.getSelectedItem().toString().toLowerCase());
//                DadosUsuario dados = new DadosUsuario();
//                dados.cadastrar(usuario);
//                JOptionPane.showMessageDialog(rootPane, "Usuário cadastrado.");
//                clearFields();
//                fechar();
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Login ja existe. Por favor escolha outro");
//            jTextFieldLogin.requestFocus();
//        }
    }
    
        public boolean validar(String login) {
        boolean valido = false;
        try {
            stmt = conectar();
            String sql = "SELECT * FROM usuario WHERE login='" + login + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) {
                valido = true;
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);

        }
        return valido;

    }
}

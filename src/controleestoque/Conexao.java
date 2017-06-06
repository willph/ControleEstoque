/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author aluno
 */
public class Conexao {

    protected Statement stmt;
    protected Connection conn;

    public Statement conectar() throws ClassNotFoundException, SQLException {
        try {

            String driver = "com.mysql.jdbc.Driver";
            String dataBaseName = "mercearia";
            String url = "jdbc:mysql://localhost:3306/";
            String usuario = "root";
            String senha = "";
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url + dataBaseName, usuario, senha);
            this.stmt = conn.createStatement();
            return stmt;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public void desconectar() throws SQLException {
        conn.close();
    }
}

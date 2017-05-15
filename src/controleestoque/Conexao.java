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
    private static Statement stmt;
    private static Connection conn;
    
     public static Statement conectar(String[] dataBaseConfig) throws ClassNotFoundException, SQLException {
        try {

//            String driver = "com.mysql.jdbc.Driver";
//            String dataBaseName = "merciaria";
//            String url = "jdbc:mysql://localhost:3306/";
//            String usuario = "root";
//            String senha = "";
            Class.forName(dataBaseConfig[0]).newInstance();
            conn = (Connection) DriverManager.getConnection(dataBaseConfig[2] + dataBaseConfig[1], dataBaseConfig[3], dataBaseConfig[4]);
            stmt = conn.createStatement();
            return stmt;
        } catch (InstantiationException ex) {
            throw new SQLException(ex.getMessage());
        } catch (IllegalAccessException ex) {
            throw new SQLException(ex.getMessage());
        }
    }
      public void desconectar() throws SQLException {
        conn.close();
    }
    
}

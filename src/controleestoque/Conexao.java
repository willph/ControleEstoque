/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aluno
 */
public class Conexao {
    private Statement stmt;
    private Connection conn;
    
     public Statement conectar() throws ClassNotFoundException, SQLException {
        try {

            String driver = "com.mysql.jdbc.Driver";
            String dataBaseName = "merciaria";
            String url = "jdbc:mysql://localhost:3306/";
            String usuario = "root";
            String senha = "";
            Class.forName(driver).newInstance();
            conn = (Connection) DriverManager.getConnection(url + dataBaseName, usuario, senha);
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
    
    public void gerarRelatorio() {

        // TODO add your handling code here:
        Document document = new Document();
//mudando a fonte, tamanho e colocando em negrito
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

        try {
            Statement conex = this.conectar();
            String sql = "SELECT * FROM produtos ORDER BY qtd";
            //ResultSet rs = stmt.executeQuery(sql);
            ResultSet rs = conex.executeQuery(sql);

            PdfWriter.getInstance(document, new FileOutputStream("PDF_Rlatorio.pdf"));
            document.open();

// adicionando um Titulo no documento
            Paragraph title = new Paragraph("Produtos Cadastrados", font1);

//alinhado o titulo na forma centralizada
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            //Paragraph paragraph2;
            Paragraph paragraphSpace = new Paragraph();

            paragraphSpace.setSpacingBefore(50);

            document.add(paragraphSpace);
            /*
            while (rs.next()) {
                paragraph2 = new Paragraph(rs.getString(1) + " - " + rs.getString(2));
                
                //paragraph2.setSpacingAfter(10);
                paragraph2.setSpacingBefore(20);
            document.add(paragraph2);

            }*/

//criando uma tabela
            PdfPTable table = new PdfPTable(2); // 2 colunas.
//adicionando as celulas
            PdfPCell cell1 = new PdfPCell(new Paragraph("Produto", font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Quantidade", font2));

//alinhamento do que tem dentro da celula
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

//finalizando as celulas nome, email na tabela
            table.addCell(cell1);
            table.addCell(cell2);

            while (rs.next()) {
//criando varias celulas dentro do while
                PdfPCell cell3 = new PdfPCell(new Paragraph(rs.getString(2)));
                PdfPCell cell4 = new PdfPCell(new Paragraph(rs.getString(4)));

                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

//finalizando as celulas do while na tabela
                table.addCell(cell3);
                table.addCell(cell4);

            }
//finalizando a tabela dentro do pdf
            document.add(table);

//fechando a conexão com o banco de dados
            this.desconectar();

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

//encerrando o documento pdf
        document.close();
        JOptionPane.showMessageDialog(null, "Relatorio gerado com sucesso!");
    }

    public void cadastrar(Produto novoProduto) {
        try {
            // TODO add your handling code here:
            //abrindo a conexão
            Statement conex = conectar();
            //instrução sql correspondente a inserção do aluno
            String sql = "INSERT INTO produtos (nome, preco, qtd, tipoProduto) VALUES (" + '';
            sql += "VALUES ("
                   + novoProduto.getNome() + "', "
                    + novoProduto.getPreco() + ", "
                    + novoProduto.getQuantidade() + ", '"
                    + novoProduto.getDescricao() + "' )";
            try {
                //executando a instrução sql
                conex.execute(sql);
            } catch (SQLException e) {
                //caso haja algum erro neste método será levantada esta execeção
                throw new Exception("Erro ao executar inserção: " + e.getMessage());
            }
            //fechando a conexão com o banco de dados
            desconectar();

            //Limpando os textos das caixas
//            jTextFieldID.setText("");
//            jTextFieldNome.setText("");
//            jTextFieldPreco.setText("");
//            jTextFieldQuantidade.setText("");
//            jTextFieldDescricao.setText("");

            //Mensagem de cadastro com sucesso
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void atualizar(Produto novoProduto) {
        try {
            int id = novoProduto.getId();
            //abrindo a conexão
            Statement conex = this.conectar();
            //instrução sql correspondente a atualização do aluno
            String sql = "update produtos set " + " nome = '" + novoProduto.getNome()
                    + "', preco = " + novoProduto.getPreco()
                    + ", qtd = " + novoProduto.getQuantidade()
                    + ", tipoProduto = '" + novoProduto.getDescricao()
                    + "' where id = " + id;

            //executando a instrução sql
            conex.execute(sql);

            //fechando a conexão com o banco de dados
            this.desconectar();

            //Limpando os textos das caixas
//            jTextFieldID.setText("");
//            jTextFieldNome.setText("");
//            jTextFieldPreco.setText("");
//            jTextFieldQuantidade.setText("");
//            jTextFieldDescricao.setText("");

            //Mensagem de atualização com sucesso
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void remover(int id) {
        try {
            //abrindo a conexão
            Statement conex = conectar();
            
            //instrução sql correspondente a remoção do aluno
            String sql = "delete from produtos where id = "
                    + id;

            //executando a instrução sql
            conex.execute(sql);
            //fechando a conexão com o banco de dados
            desconectar();

            //Limpando os textos das caixas
//            jTextFieldID.setText("");
//            jTextFieldNome.setText("");
//            jTextFieldPreco.setText("");
//            jTextFieldQuantidade.setText("");
//            jTextFieldDescricao.setText("");

            //Mensagem de removido com sucesso
            JOptionPane.showMessageDialog(null, "Produto removido com sucesso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public DefaultTableModel listar() {
        DefaultTableModel modelo = new DefaultTableModel();
        //atribuindo as colunas da tabela
        modelo.setColumnIdentifiers(new String[]{"ID", "Nome", "Preço", "Quantidade", "Descrição"});
        try {
            // TODO add your handling code here:

            //abrindo a conexão
            Statement conex = conectar();
            //instrução sql correspondente a seleção dos alunos
            String sql = "SELECT id, nome, preco, qtd, tipoProduto FROM produtos order by nome";

            //executando a instrução sql
            ResultSet rs = conex.executeQuery(sql);
            while (rs.next()) {
                modelo.addRow(new String[]{"" + rs.getInt("id"), rs.getString("nome"), "R$ "+
                    rs.getString("preco"), rs.getString("qtd"), rs.getString("tipoProduto")});
            }
            //fechando a conexão com o banco de dados
            desconectar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return modelo;
    }
     
}

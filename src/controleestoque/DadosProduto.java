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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author aluno
 */
public class DadosProduto extends Conexao {

    private UtilsDbAuditoria auditConection;

    public void gerarRelatorio() {

        // TODO add your handling code here:
        Document document = new Document();
//mudando a fonte, tamanho e colocando em negrito
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

        try {
            Statement conex = this.conectar();
            String sql = "SELECT * FROM produto ORDER BY qtd";
            //ResultSet rs = stmt.executeQuery(sql);
            ResultSet rs = conex.executeQuery(sql);

            PdfWriter.getInstance(document, new FileOutputStream("PDF_Relatorio.pdf"));
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

        // TODO add your handling code here:
        //abrindo a conexão
        try {
            stmt = conectar();
            //instrução sql correspondente a inserção do produto
            String sql = "INSERT INTO produto (nome, preco, tipoProduto) ";
            sql += "VALUES ('"
                    + novoProduto.getNome() + "', "
                    + novoProduto.getPreco() + ", '"
                    + novoProduto.getDescricao() + "' )";

            //executando a instrução sql
//                conex.execute(sql);
            if (stmt.executeUpdate(sql) > 0) {
                auditConection = new UtilsDbAuditoria(stmt);
                auditConection.cadastrarAuditoria(novoProduto, "cadastro");
            }

            //fechando a conexão com o banco de dados
            desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DadosProduto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void atualizar(Produto novoProduto) {

        int id = novoProduto.getId();
        try {
            //abrindo a conexão
            stmt = conectar();
            //instrução sql correspondente a atualização do aluno
            String sql = "update produto set " + " nome = '" + novoProduto.getNome()
                    + "', preco = " + novoProduto.getPreco()
                    + ", qtd = " + novoProduto.getQuantidade()
                    + ", tipoProduto = '" + novoProduto.getDescricao()
                    + "' where id = " + id;

            //executando a instrução sql
            stmt.execute(sql);
            //fechando a conexão com o banco de dados
            desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EntradaSaida.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void transacao(Produto p, String tipo) {
        try {
            //abrindo a conexão
            stmt = conectar();
            auditConection = new UtilsDbAuditoria(stmt);
            auditConection.cadastrarAuditoria(p, tipo);
            //fechando a conexão com o banco de dados
            desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DadosProduto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void remover(int id) throws Exception {

        //abrindo a conexão
        Statement conex = conectar();

        //instrução sql correspondente a remoção do aluno
        String sql = "delete from auditoria where produto_id = "
                + id;
        String sql2 = "delete from produto where id = " + id;

        //executando a instrução sql
        conex.execute(sql);
        conex.execute(sql2);
        //fechando a conexão com o banco de dados
        desconectar();

    }

    public ArrayList<Produto> listar() throws Exception {
        ArrayList<Produto> retorno = new ArrayList<>();
        //abrindo a conexão
        Statement conex = conectar();
        //instrução sql correspondente a seleção dos alunos
        String sql = "SELECT id, nome, preco, qtd, tipoProduto FROM produto order by nome";
        //executando a instrução sql
        ResultSet rs = conex.executeQuery(sql);
        while (rs.next()) {
            Produto p = new Produto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setPreco(rs.getFloat("preco"));
            p.setQuantidade(rs.getInt("qtd"));
            p.setDescricao(rs.getString("tipoProduto"));
            retorno.add(p);

        }
        //fechando a conexão com o banco de dados
        desconectar();
        return retorno;
    }

    public int buscarId(String nome) {
        int produtoId = 0;
        try {
            // TODO add your handling code here:

            //abrindo a conexão
            Statement conex = conectar();
            //instrução sql correspondente a seleção do produto
            String sql = "SELECT id FROM produto WHERE nome = '" + nome + "'";

            //executando a instrução sql
            ResultSet rs = conex.executeQuery(sql);
            while (rs.next()) {
                produtoId = rs.getInt("id");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return produtoId;
    }

    public ArrayList<Produto> consultar(String nome) throws Exception {
        ArrayList<Produto> retorno = new ArrayList<>();
        //abrindo a conexão
        Statement conex = conectar();
        //instrução sql correspondente a seleção dos alunos
        String sql = "SELECT * FROM produto WHERE nome LIKE '%" + nome+"%'";
        //executando a instrução sql
        ResultSet rs = conex.executeQuery(sql);
        while (rs.next()) {
            Produto p = new Produto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setPreco(rs.getFloat("preco"));
            p.setQuantidade(rs.getInt("qtd"));
            p.setDescricao(rs.getString("tipoProduto"));
            retorno.add(p);
        }
        //fechando a conexão com o banco de dados
        desconectar();
        return retorno;
    }
    /*public DefaultTableModel buscar(String nome) {
        DefaultTableModel modelo = new DefaultTableModel();
        //atribuindo as colunas da tabela
        modelo.setColumnIdentifiers(new String[]{"ID", "Nome", "Preço", "Quantidade", "Descrição"});
        try {
            // TODO add your handling code here:

            //abrindo a conexão
            Statement conex = conectar();
            //instrução sql correspondente a seleção dos alunos
            String sql = "SELECT id, nome, preco, qtd, tipoProduto FROM produto WHERE nome LIKE '%" + nome + "%'  ORDER by nome";

            //executando a instrução sql
            ResultSet rs = conex.executeQuery(sql);
            while (rs.next()) {
                modelo.addRow(new String[]{"" + rs.getInt("id"), rs.getString("nome"), "R$ "
                    + rs.getString("preco"), rs.getString("qtd"), rs.getString("tipoProduto")});
            }
            //fechando a conexão com o banco de dados
            desconectar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return modelo;
    }*/

}

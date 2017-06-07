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
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class DadosProduto extends Conexao {

    private UtilsDbAuditoria auditConection;

    public void gerarRelatorio() throws Exception {

        // TODO add your handling code here:
        Document document = new Document();
//mudando a fonte, tamanho e colocando em negrito
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

        try {
            Statement conex = this.conectar();
            String sql = sql = "select a.id as auditoria_id, a.quantidadeProduto as auditoria_quantidadeProduto, a.data as auditoria_data, a.transacaoTipo as auditoria_transacaoTipo, "
                    + "p.id as produto_id, p.nome as produto_nome, p.preco as produto_preco, p.qtd as produto_quantidade, p.tipoProduto as produto_descricao, "
                    + "u.id as usuario_id, u.nome as usuario_nome, u.login as usuario_login, u.senha as usuario_senha, u.privilegio as usuario_privilegio "
                    + "from auditoria as a inner join produto as p on p.id = a.produto_id inner join usuario as u on u.id = a.usuario_id";
            //ResultSet rs = stmt.executeQuery(sql);
            ResultSet rs = conex.executeQuery(sql);

            PdfWriter.getInstance(document, new FileOutputStream("PDF_Relatorio.pdf"));
            document.open();

// adicionando um Titulo no documento
            Paragraph title = new Paragraph("Relatorio de Produtos", font1);

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
            PdfPTable table = new PdfPTable(5); // 2 colunas.
//adicionando as celulas
            PdfPCell cellProduto = new PdfPCell(new Paragraph("Produto", font2));
            PdfPCell cellQuantidade = new PdfPCell(new Paragraph("Em Estoque", font2));
            PdfPCell cellEntrada = new PdfPCell(new Paragraph("Entrada/Saida", font2));
            PdfPCell cellData = new PdfPCell(new Paragraph("Data", font2));
            PdfPCell cellEstoquista = new PdfPCell(new Paragraph("Estoquista", font2));

//alinhamento do que tem dentro da celula
            cellProduto.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellEntrada.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellEstoquista.setHorizontalAlignment(Element.ALIGN_CENTER);

//finalizando as celulas nome, email na tabela
            table.addCell(cellProduto);
            table.addCell(cellQuantidade);
            table.addCell(cellEntrada);
            table.addCell(cellData);
            table.addCell(cellEstoquista);

            while (rs.next()) {
//criando varias celulas dentro do while
                PdfPCell cellProduto1 = new PdfPCell(new Paragraph(rs.getString(6)));
                PdfPCell cellQuantidade1 = new PdfPCell(new Paragraph(rs.getString(8)));
                PdfPCell cellEntrada1 = new PdfPCell(new Paragraph(rs.getString(2) + " - " + rs.getString(4)));
                PdfPCell cellData1 = new PdfPCell(new Paragraph(rs.getString(3)));
                PdfPCell cell1Estoquista1 = new PdfPCell(new Paragraph(rs.getString(11)));

                cellProduto1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellQuantidade1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellEntrada1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellData1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1Estoquista1.setHorizontalAlignment(Element.ALIGN_CENTER);

//finalizando as celulas do while na tabela
                table.addCell(cellProduto1);
                table.addCell(cellQuantidade1);
                table.addCell(cellEntrada1);
                table.addCell(cellData1);
                table.addCell(cell1Estoquista1);
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
        throw new Exception("Relatorio gerado com sucesso!");

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
            Logger.getLogger(FormEntradaSaida.class.getName()).log(Level.SEVERE, null, ex);
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
            desconectar();
        } catch (CommunicationsException ex) {
            throw new UnsupportedOperationException("Servidor RGBD pode estar off-line", ex);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DadosProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produtoId;
    }

    public String buscarNome(int id) {
        String produtoNome = "";
        try {
            stmt = conectar();
            String sql = "SELECT nome FROM produto WHERE id = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                produtoNome = rs.getString("nome");
            }
            desconectar();
        } catch (CommunicationsException ex) {
            throw new UnsupportedOperationException("Servidor RGBD pode estar off-line", ex);

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DadosProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produtoNome;
    }

    public Produto buscarProduto(String nome) {
        Produto produto = new Produto();
        if (nome.equals("") == false) {
            try {
                stmt = conectar();
                String sql = "SELECT * FROM produto WHERE nome LIKE '%" + nome + "%'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
//                produtoNome = rs.getString("nome");
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setPreco(rs.getFloat("preco"));
                    produto.setQuantidade(rs.getInt("qtd"));
                    produto.setDescricao(rs.getString("tipoProduto"));
                }
                desconectar();

            } catch (CommunicationsException ex) {
                throw new UnsupportedOperationException("Servidor RGBD pode estar off-line", ex);

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(DadosProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return produto;
    }

    public Produto buscarProdutoPorId(int id) {
        Produto produto = new Produto();
        try {
            stmt = conectar();
            String sql = "SELECT * FROM produto WHERE id = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
//                produtoNome = rs.getString("nome");
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getFloat("preco"));
                produto.setQuantidade(rs.getInt("qtd"));
                produto.setDescricao(rs.getString("tipoProduto"));
            }
            desconectar();

        } catch (CommunicationsException ex) {
            throw new UnsupportedOperationException("Servidor RGBD pode estar off-line", ex);

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DadosProduto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return produto;
    }

    public ArrayList<Produto> consultar(String nome) throws Exception {
        ArrayList<Produto> retorno = new ArrayList<>();
        //abrindo a conexão
        try {
            Statement conex = conectar();
            //instrução sql correspondente a seleção dos alunos
            String sql = "SELECT * FROM produto WHERE nome LIKE '%" + nome + "%'";
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

        } catch (CommunicationsException ex) {
            throw new Exception("Servidor RGBD pode estar off-line.\nPor favor comunicar ao suporte", ex);

        } catch (MySQLSyntaxErrorException ex) {
            throw new Exception("Banco de dados\n" + ex.getMessage());

        }
    }

    public boolean validarProduto(String nome) {
        try {
            Statement conex = this.conectar();
            String sql = "SELECT * FROM produto WHERE nome='" + nome + "'";
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
}

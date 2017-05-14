/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * FormPrincipal.java
 *
 * Created on 29/03/2012, 10:47:50
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
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Professor
 */
public class FormPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FormPrincipal
     */
    public FormPrincipal() {
        initComponents();
        listar();
    }

    Conexao con = new Conexao();

    private void gerarRelatorio() {

        // TODO add your handling code here:
        Document document = new Document();
//mudando a fonte, tamanho e colocando em negrito
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

        try {
            Statement conex = con.conectar();
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
            con.desconectar();

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

//encerrando o documento pdf
        document.close();
        JOptionPane.showMessageDialog(null, "Relatorio gerado com sucesso!");
    }

    private void cadastrar() {
        try {
            // TODO add your handling code here:
            //abrindo a conexão
            Statement conex = con.conectar();
            //instrução sql correspondente a inserção do aluno
            String sql = "INSERT INTO produtos (id, nome, preco, qtd, tipoProduto)";
            sql += "VALUES (" + jTextFieldID.getText() + ", '"
                    + jTextFieldNome.getText() + "', "
                    + jTextFieldPreco.getText() + ", "
                    + jTextFieldQuantidade.getText() + ", '"
                    + jTextFieldDescricao.getText() + "' )";
            try {
                //executando a instrução sql
                conex.execute(sql);
            } catch (SQLException e) {
                //caso haja algum erro neste método será levantada esta execeção
                throw new Exception("Erro ao executar inserção: " + e.getMessage());
            }
            //fechando a conexão com o banco de dados
            con.desconectar();

            //Limpando os textos das caixas
            jTextFieldID.setText("");
            jTextFieldNome.setText("");
            jTextFieldPreco.setText("");
            jTextFieldQuantidade.setText("");
            jTextFieldDescricao.setText("");

            //Mensagem de cadastro com sucesso
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void atualizar() {
        try {
            int id = Integer.parseInt(jTextFieldID.getText());
            //abrindo a conexão
            Statement conex = con.conectar();
            //instrução sql correspondente a atualização do aluno
            String sql = "update produtos set " + " nome = '" + jTextFieldNome.getText()
                    + "', preco = " + jTextFieldPreco.getText()
                    + ", qtd = " + jTextFieldQuantidade.getText()
                    + ", tipoProduto = '" + jTextFieldDescricao.getText()
                    + "' where id = " + id;

            //executando a instrução sql
            conex.execute(sql);

            //fechando a conexão com o banco de dados
            con.desconectar();

            //Limpando os textos das caixas
            jTextFieldID.setText("");
            jTextFieldNome.setText("");
            jTextFieldPreco.setText("");
            jTextFieldQuantidade.setText("");
            jTextFieldDescricao.setText("");

            //Mensagem de atualização com sucesso
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void remover() {
        try {
            //abrindo a conexão
            Statement conex = con.conectar();
            int id = Integer.parseInt(jTextFieldID.getText());
            //instrução sql correspondente a remoção do aluno
            String sql = "delete from produtos where id = "
                    + id;

            //executando a instrução sql
            conex.execute(sql);
            //fechando a conexão com o banco de dados
            con.desconectar();

            //Limpando os textos das caixas
            jTextFieldID.setText("");
            jTextFieldNome.setText("");
            jTextFieldPreco.setText("");
            jTextFieldQuantidade.setText("");
            jTextFieldDescricao.setText("");

            //Mensagem de removido com sucesso
            JOptionPane.showMessageDialog(null, "Produto removido com sucesso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void listar() {
        DefaultTableModel modelo = new DefaultTableModel();
        //atribuindo as colunas da tabela
        modelo.setColumnIdentifiers(new String[]{"ID", "Nome", "Preço", "Quantidade", "Descrição"});
        try {
            // TODO add your handling code here:

            //abrindo a conexão
            Statement conex = con.conectar();
            //instrução sql correspondente a seleção dos alunos
            String sql = "SELECT id, nome, preco, qtd, tipoProduto FROM produtos order by nome";

            //executando a instrução sql
            ResultSet rs = conex.executeQuery(sql);
            while (rs.next()) {
                modelo.addRow(new String[]{"" + rs.getInt("id"), rs.getString("nome"),
                    rs.getString("preco"), rs.getString("qtd"), rs.getString("tipoProduto")});
            }
            //fechando a conexão com o banco de dados
            con.desconectar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        jTable1.setModel(modelo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldID = new javax.swing.JTextField();
        jTextFieldNome = new javax.swing.JTextField();
        jButtonCadastrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonListar = new javax.swing.JButton();
        jButtonRemover = new javax.swing.JButton();
        jButtonAtualizar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonTestarConexao = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldQuantidade = new javax.swing.JTextField();
        jTextFieldDescricao = new javax.swing.JTextField();
        jLabeID = new javax.swing.JLabel();
        jTextFieldPreco = new javax.swing.JTextField();
        jButtonLogoff = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1Arquivo = new javax.swing.JMenu();
        jMenuItemGerarRelatorio = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Controle De Estoque - Copyright © ®");

        jTextFieldID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDActionPerformed(evt);
            }
        });

        jButtonCadastrar.setText("Cadastrar");
        jButtonCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButtonListar.setText("Listar");
        jButtonListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListarActionPerformed(evt);
            }
        });

        jButtonRemover.setText("Remover");
        jButtonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoverActionPerformed(evt);
            }
        });

        jButtonAtualizar.setText("Atualizar");
        jButtonAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtualizarActionPerformed(evt);
            }
        });

        jLabel1.setText("Preço:");

        jLabel2.setText("Nome:");

        jButtonTestarConexao.setText("Testar Conexão");
        jButtonTestarConexao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestarConexaoActionPerformed(evt);
            }
        });

        jLabel3.setText("Descrição:");

        jLabel4.setText("Quantidade:");

        jLabeID.setText("ID:");

        jButtonLogoff.setText("Logoff");
        jButtonLogoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoffActionPerformed(evt);
            }
        });

        jMenu1Arquivo.setText("Arquivo");

        jMenuItemGerarRelatorio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemGerarRelatorio.setText("Gerar Relatorio");
        jMenuItemGerarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerarRelatorioActionPerformed(evt);
            }
        });
        jMenu1Arquivo.add(jMenuItemGerarRelatorio);

        jMenuBar1.add(jMenu1Arquivo);

        jMenu2.setText("Imprimir");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabeID)
                                    .addGap(35, 35, 35))
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextFieldDescricao)
                            .addComponent(jTextFieldNome)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonCadastrar)
                        .addGap(113, 113, 113)
                        .addComponent(jButtonListar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                        .addComponent(jButtonRemover)
                        .addGap(91, 91, 91)
                        .addComponent(jButtonAtualizar)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(234, 234, 234)
                .addComponent(jButtonTestarConexao)
                .addGap(18, 18, 18)
                .addComponent(jButtonLogoff)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabeID)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCadastrar)
                    .addComponent(jButtonListar)
                    .addComponent(jButtonRemover)
                    .addComponent(jButtonAtualizar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonTestarConexao)
                    .addComponent(jButtonLogoff))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDActionPerformed

    private void jButtonCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarActionPerformed
        cadastrar();
        listar();
    }//GEN-LAST:event_jButtonCadastrarActionPerformed

    private void jButtonListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListarActionPerformed
        listar();
    }//GEN-LAST:event_jButtonListarActionPerformed

    private void jButtonRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoverActionPerformed
        // TODO add your handling code here:
        remover();
        listar();

    }//GEN-LAST:event_jButtonRemoverActionPerformed

    private void jButtonAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtualizarActionPerformed
        // TODO add your handling code here:
        atualizar();
        listar();
    }//GEN-LAST:event_jButtonAtualizarActionPerformed

    private void jButtonTestarConexaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestarConexaoActionPerformed
        try {
            con.conectar();
            JOptionPane.showMessageDialog(rootPane, "Conectou");
            con.desconectar();
            JOptionPane.showMessageDialog(rootPane, "Desconectou");
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_jButtonTestarConexaoActionPerformed

    private void jMenuItemGerarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerarRelatorioActionPerformed
        gerarRelatorio();
    }//GEN-LAST:event_jMenuItemGerarRelatorioActionPerformed


    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        int index = jTable1.getSelectedRow();

        TableModel model = jTable1.getModel();

        String id = model.getValueAt(index, 0).toString();
        String nome = model.getValueAt(index, 1).toString();
        String preco = model.getValueAt(index, 2).toString();
        String quantidade = model.getValueAt(index, 3).toString();
        String descricao = model.getValueAt(index, 4).toString();

        jTextFieldID.setText(id);
        jTextFieldNome.setText(nome);
        jTextFieldPreco.setText(preco);
        jTextFieldQuantidade.setText(quantidade);
        jTextFieldDescricao.setText(descricao);

        /*int column = 0;
        int row = jTable1.getSelectedRow();
        String value = jTable1.getModel().getValueAt(row, column).toString();
        jTextFieldID.setText(value);*/
    }//GEN-LAST:event_jTable1MouseClicked

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked

        MessageFormat header = new MessageFormat("Controle de Estoque");
        MessageFormat footer = new MessageFormat("Pagina {0,number,integer}");

        try {
            // TODO add your handling code here:

            jTable1.print(JTable.PrintMode.FIT_WIDTH, header, footer);

            /*Boolean printdata = jTable1.print();
            
            if(printdata){
                JOptionPane.showMessageDialog(null, "Impressão terminada");
            }else{
                JOptionPane.showMessageDialog(null, "Imprimindo...");
            }*/
        } catch (PrinterException ex) {
            //System.err.format("Impressora não encontrada %s%n", ex.getMessage());
            Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jButtonLogoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoffActionPerformed
        // TODO add your handling code here:
        fechar();
        new Login().setVisible(true);
        
    }//GEN-LAST:event_jButtonLogoffActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAtualizar;
    private javax.swing.JButton jButtonCadastrar;
    private javax.swing.JButton jButtonListar;
    private javax.swing.JButton jButtonLogoff;
    private javax.swing.JButton jButtonRemover;
    private javax.swing.JButton jButtonTestarConexao;
    private javax.swing.JLabel jLabeID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1Arquivo;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemGerarRelatorio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldDescricao;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldPreco;
    private javax.swing.JTextField jTextFieldQuantidade;
    // End of variables declaration//GEN-END:variables

    private void fechar() {
        WindowEvent windowsfechar = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowsfechar);
    }

}

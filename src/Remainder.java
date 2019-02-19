/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.Date;
import java.util.Timer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public final class Remainder extends javax.swing.JFrame {

    /**
     * Creates new form Remainder
     */
    Statement stmt;
    Connection con;
    int flag;
    public void makeconnection()
    {
        try{
            String url="jdbc:derby://localhost:1527/remainderdb";
            String uname="admin1";
            String password="admin";
        
            
        con =DriverManager.getConnection(url, uname, password);
        stmt = con.createStatement();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "make connection error"+e.toString());
        }
        
    }
    
    public void updatetable(ResultSet rs)
    {
        try{
                DefaultTableModel tm=(DefaultTableModel)Remtable.getModel();
                tm.setRowCount(0);
                flag=0;
                while (rs.next()){
                    Object o[]={rs.getString(1),rs.getString(2),rs.getString(3)};
                    tm.addRow(o);
                    flag=1;
                 }
                if(flag==1){
                    lbdate.setText(tm.getValueAt(0, 0).toString());
                    lbtime.setText(tm.getValueAt(0, 1).toString());
                    lbremainder.setText(tm.getValueAt(0, 2).toString());
                    lbdate.setVisible(true);
                    lbtime.setVisible(true);
                    lbremainder.setVisible(true);
                    jLabel1.setVisible(true);
                    jLabel2.setVisible(true);
                    jLabel3.setVisible(true);
                    
                }
                else
                {
                    jLabel4.setVisible(true);
                    jLabel1.setVisible(false);
                    jLabel2.setVisible(false);
                    jLabel3.setVisible(false);
                    jLabel4.setVisible(false);
                    lbdate.setVisible(false);
                    lbtime.setVisible(false);
                    lbremainder.setVisible(false);
                    
                }
                
                
            
            }
            catch(SQLException e)
                    {
                    JOptionPane.showMessageDialog(null, e.toString());
                    }
         
        
    }
    
    public void deltable()
    {
        try{
            String query ="SELECT TID FROM ADMIN1.TREMAINDER WHERE TDATE ='"+lbdate.getText()+"'AND TTIME ='"+lbtime.getText()+"'AND NOTES = '"+lbremainder.getText()+"'";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
            System.out.print(rs.getString(1));}
            int val=Integer.parseInt(rs.getString(1));
            String q2="DELETE FROM ADMIN1.TREMAINDER WHERE TID="+val+"";
            PreparedStatement ps=con.prepareStatement(q2);
            ps.executeUpdate();
            
            String q3 = "SELECT TDATE,TTIME,NOTES FROM ADMIN1.TREMAINDER";
            ResultSet rs1 = stmt.executeQuery(q3);
            updatetable(rs1);
           
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No Remainder available to delete");
            
        }
    }
    public Remainder() {
        initComponents();
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        lbdate.setVisible(false);
        lbtime.setVisible(false);
        lbremainder.setVisible(false);
       
        this.setLocationRelativeTo(null);
        try{
           
            makeconnection();
            String query = "SELECT TDATE,TTIME,NOTES FROM ADMIN1.TREMAINDER ORDER BY TDATE ASC,TTIME ASC";
            ResultSet rs = stmt.executeQuery(query);
            updatetable(rs);
            
         
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
            
        }
                   
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
                 public void run()
                {
                   
                    Date date = new Date();
                    String ddtime=dateformat.format(date);
                    String datetime[]=ddtime.split(" ");
                    currentdate.setText(datetime[0]);
                    currenttime.setText(datetime[1]);
                   
                    if(flag==1)
                    {
                    if(lbdate.getText().equals(currentdate.getText())){
                        if(lbtime.getText().equals(currenttime.getText())){
                            new Remainderpopup(lbremainder.getText()).setVisible(true);
                           
                           deltable();
                        }
                    }
                    }
                   
                }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Remtable = new javax.swing.JTable();
        AddBtn = new javax.swing.JButton();
        EditBtn = new javax.swing.JButton();
        DeleteBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbdate = new javax.swing.JLabel();
        lbtime = new javax.swing.JLabel();
        lbremainder = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        currentdate = new javax.swing.JLabel();
        currenttime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        Remtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Date", "Time", "Remainder"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Remtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RemtableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Remtable);

        AddBtn.setText("Add new remainder");
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });

        EditBtn.setText("Edit remiander");
        EditBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBtnActionPerformed(evt);
            }
        });

        DeleteBtn.setText("Delete remainder");
        DeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Date");

        jLabel2.setText("Time");

        jLabel3.setText("Remainder");

        lbdate.setText("jLabel4");

        lbtime.setText("jLabel5");

        lbremainder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbremainder.setText("jLabel6");

        jLabel4.setText("No Remainders available");

        currentdate.setText("jLabel5");

        currenttime.setText("jLabel5");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currentdate, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(currenttime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(currentdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(currenttime)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddBtn)
                .addGap(37, 37, 37)
                .addComponent(EditBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(DeleteBtn)
                .addGap(204, 204, 204))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbtime)
                            .addComponent(lbdate)
                            .addComponent(lbremainder, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lbdate))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lbtime))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lbremainder, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddBtn)
                    .addComponent(DeleteBtn)
                    .addComponent(EditBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
        // TODO add your handling code here:
        new Addrem().setVisible(true);
    }//GEN-LAST:event_AddBtnActionPerformed

    private void RemtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemtableMouseClicked
        // TODO add your handling code here:
        jLabel1.setVisible(true);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        lbdate.setVisible(true);
        lbtime.setVisible(true);
        lbremainder.setVisible(true);
        DefaultTableModel model= (DefaultTableModel)Remtable.getModel();
        int selectedrow=Remtable.getSelectedRow();
        
        lbdate.setText(model.getValueAt(selectedrow, 0).toString());
        lbtime.setText(model.getValueAt(selectedrow, 1).toString());
        lbremainder.setText(model.getValueAt(selectedrow, 2).toString());
        
    }//GEN-LAST:event_RemtableMouseClicked

    private void EditBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBtnActionPerformed
        // TODO add your handling code here:
        try{
            
            makeconnection();
            String query ="SELECT TID FROM ADMIN1.TREMAINDER WHERE TDATE ='"+lbdate.getText()+"'AND TTIME ='"+lbtime.getText()+"'AND NOTES = '"+lbremainder.getText()+"'";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
            System.out.print(rs.getString(1));}
            int val=Integer.parseInt(rs.getString(1));
            //stmt.close();
            new Addrem(val).setVisible(true);
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No Remainder avaliable to edit");
            
        }
        
        
        
    }//GEN-LAST:event_EditBtnActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
        // TODO add your handling code here:
        
      try{
            makeconnection();
            deltable();
      }
      catch(Exception e)
      {
          JOptionPane.showMessageDialog(null, "No Remainder availabel to delete");
      }
           
        
    }//GEN-LAST:event_DeleteBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Remainder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Remainder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Remainder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Remainder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Remainder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JButton EditBtn;
    private javax.swing.JTable Remtable;
    private javax.swing.JLabel currentdate;
    private javax.swing.JLabel currenttime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbdate;
    private javax.swing.JLabel lbremainder;
    private javax.swing.JLabel lbtime;
    // End of variables declaration//GEN-END:variables
}

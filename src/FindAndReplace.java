
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import org.omg.CORBA.IntHolder;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Asus
 */
public class FindAndReplace extends javax.swing.JDialog {

    /**
     * Creates new form FindAndReplace
     */
    MainFrame father;
    int fromIndex = 0;
    int pos = -1;
    int lastpos = -1;
    int beforepos = -1;
    int lastposToReplace = -1;
    int indexBefore = -1;
    IntHolder beforePosToPrevious = new IntHolder(0);
    LinkedList<Integer> posList = new LinkedList<>();
    boolean lastPrevious = false;
    String findText;
    String content;
    boolean matchCaseCheck=false;
    public FindAndReplace(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        father = (MainFrame) parent;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFind = new javax.swing.JLabel();
        txtFind = new javax.swing.JTextField();
        txtReplace = new javax.swing.JTextField();
        lblReplace = new javax.swing.JLabel();
        btnFind = new javax.swing.JButton();
        btnReplace = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        cbMatchCase = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblFind.setText("Find");

        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFindKeyPressed(evt);
            }
        });

        lblReplace.setText("Replace text");

        btnFind.setText("Find next");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnReplace.setText("Replace");
        btnReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReplaceActionPerformed(evt);
            }
        });

        btnPrevious.setText("Previous");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        cbMatchCase.setText("Match Case");
        cbMatchCase.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cbMatchCaseStateChanged(evt);
            }
        });
        cbMatchCase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMatchCaseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFind)
                    .addComponent(lblReplace))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbMatchCase)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtReplace, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(txtFind))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnFind)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPrevious))
                            .addComponent(btnReplace, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFind)
                    .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind)
                    .addComponent(btnPrevious))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbMatchCase)
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtReplace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReplace)
                    .addComponent(lblReplace))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        // TODO add your handling code here:
        if(cbMatchCase.isSelected()){
            matchCaseCheck=true;
        }
        else{
            matchCaseCheck=false;
        }
        if (lastPrevious == true) {
            ListIterator<Integer> iter = posList.listIterator(0);
            int caret = father.txtContent.getCaretPosition();
            int flag = 0;
            int count = 0;
            while (iter.hasNext() && flag == 0) {
                count += 1;
                int k = iter.next();
                System.out.println(k);
                if (k == caret - txtFind.getText().length() || k == caret - 1 - txtFind.getText().length()) {

                    int iterPre = -1;
                    //iter.next();
                    if (iter.hasNext()) {
                        iterPre = iter.next();
                        father.txtContent.select(iterPre, iterPre + txtFind.getText().length());
                        father.statusBar.loadStatus(iterPre, father);
                        if(iter.hasNext()){
                            fromIndex=iter.next();
                            iter.previous();
                        }
                    }
                    flag = 1;
                }
            }
             lastPrevious = false;
        } else {
            //System.out.println("----hello");
                    
            this.findText = txtFind.getText();
            this.content = father.txtContent.getText();
            pos = -1;
            if(matchCaseCheck==true){
                 pos = content.indexOf(findText, fromIndex);
            }
            else{
                pos = content.toUpperCase().indexOf(findText.toUpperCase(), fromIndex);
            }
            if (pos >= 0) {

                //posList.add(pos);
                father.txtContent.select(pos, pos + txtFind.getText().length());
                if(!posList.contains(pos)){
                    posList.add(pos);
                }
                
                fromIndex = pos + txtFind.getText().length();
                if(matchCaseCheck==true&&content.indexOf(findText, fromIndex) < 0){
                    lastpos = pos;
                   lastposToReplace = lastpos;
                 
                }
                else if(content.toUpperCase().indexOf(findText.toUpperCase(), fromIndex)<0&&matchCaseCheck==false){
                    lastpos = pos;
                    lastposToReplace = lastpos;
                }
                
                //int pos=father.txtContent.getCaretPosition();
                father.statusBar.loadStatus(pos, father);
            }
            else{
                JOptionPane.showMessageDialog(father,"not found "+txtFind.getText());
            }

            if (lastpos > 0) {
                fromIndex = 0;
                lastpos = 0;
                //System.out.println("hello");
            }
        }
        
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReplaceActionPerformed
        // TODO add your handling code here:
        //
        if (pos >= 0 && beforepos != pos) {
            beforepos = pos;
            father.txtContent.replaceRange(txtReplace.getText(), pos, pos + txtFind.getText().length());
            fromIndex = pos + 1;
            posList.clear();
        }
        //
//        if (lastposToReplace >= 0 && beforepos != lastposToReplace) {
//            beforepos = pos;
//            father.txtContent.replaceRange(txtReplace.getText(), lastposToReplace, lastposToReplace + txtFind.getText().length());
//
//        }
        //fath44er.txtContent.select(father.txtContent.getText().length()-1);

    }//GEN-LAST:event_btnReplaceActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        clear();

    }//GEN-LAST:event_formWindowClosing

    private void txtFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKeyPressed
        // TODO add your handling code here:
        clear();
        //father.txtContent.setCaretPosition(1);
        //System.out.println("hello");
    }//GEN-LAST:event_txtFindKeyPressed
    private void clear(){
        fromIndex = 0;
        pos = -1;
        lastpos = -1;
        beforepos = -1;
        lastposToReplace = -1;
        posList.clear();
       
        
    }
    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        // TODO add your handling code here:
        //father.txtContent.getCaretPosition();
        ListIterator<Integer> iter = posList.listIterator(0);
        int caret = father.txtContent.getCaretPosition();
        if (iter.next() == 0 && caret <= iter.next()) {
            iter.previous();
            for (int i = 0; i < posList.size()-1; i++) {
                if (iter.hasNext()) {
                    caret = iter.next();
                }
            }
            father.txtContent.select(caret, caret + txtFind.getText().length());
            father.statusBar.loadStatus(caret, father);
        } else {
            iter.previous();
            int flag = 0;
            int count = 0;
            int min = caret;
            int minK = -1;
            while (iter.hasNext()) {
                int k = iter.next();
                if (k < caret && caret - k <= min) {
                    min = caret - k;
                    minK = k;
                }
            }
            for (int i = 0; i < posList.size(); i++) {
                iter.previous();
            }
            while (iter.hasNext() && flag == 0) {
                int k = iter.next();
                if (k == minK) {
                    int iterPre = -1;
                    iter.previous();
                    if (iter.hasPrevious()) {
                        iterPre = iter.previous();
                        father.txtContent.select(iterPre, iterPre + txtFind.getText().length());
                        father.statusBar.loadStatus(iterPre, father);
                        lastPrevious = true;
                    }
                    flag = 1;
                }
            }
            if (iter.hasPrevious() == false) {
                for (int i = 0; i < posList.size(); i++) {
                    iter.next();
                }
            }
        }
        //iter.previous();
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void cbMatchCaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMatchCaseActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cbMatchCaseActionPerformed

    private void cbMatchCaseStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cbMatchCaseStateChanged
        // TODO add your handling code here:
       
    }//GEN-LAST:event_cbMatchCaseStateChanged

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
            java.util.logging.Logger.getLogger(FindAndReplace.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FindAndReplace.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FindAndReplace.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FindAndReplace.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FindAndReplace dialog = new FindAndReplace(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnReplace;
    private javax.swing.JCheckBox cbMatchCase;
    private javax.swing.JLabel lblFind;
    private javax.swing.JLabel lblReplace;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextField txtReplace;
    // End of variables declaration//GEN-END:variables
}

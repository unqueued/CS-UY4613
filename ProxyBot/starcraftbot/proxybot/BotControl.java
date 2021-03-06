/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package metabot;
package starcraftbot.proxybot;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

/**
 *
 * @author moonbot
 */
public class BotControl extends javax.swing.JPanel {
    
    private int[] selectedBots;
    
    /**
     * Creates new form BotControl
     */
    public BotControl() {
        initComponents();
        //connectionStatusText.setText("Not Connected");
        
        // Clear BotList
        //DefaultListModel listModel = new DefaultListModel();
        //botList.setModel(listModel);
        //listModel.clear();
        //botList.setSelectionModel(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        connectionStatusText = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        botList = new javax.swing.JList();

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setText("Bot control");

        jLabel2.setText("Status");

        jLabel3.setText("Connection");

        jLabel4.setText("Bot selection");

        botList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "FarmBot", "AttackBot", "OtherBot1", "OtherBot2", "OtherBot3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        botList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                botListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(botList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(connectionStatusText, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(connectionStatusText, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(140, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    //public int[] getActiveBots() {
    //    return selectedBots;
    //}
    
    //public JList getBotList() {
    //    return botList;
    //}
    
    private void botListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_botListValueChanged
        // TODO add your hndling code here:
        //System.out.println("Selected" + evt);
        JList jList = (JList) evt.getSource();
        
        selectedBots = jList.getSelectedIndices();
        
        for( int i : jList.getSelectedIndices()) {
            //System.out.print("" + i + " ");
        }
        //System.out.println();
        
        
        ListSelectionModel lsm = (ListSelectionModel)jList.getSelectionModel();
        
        
        for (int i = lsm.getMinSelectionIndex(); i <= lsm.getMaxSelectionIndex(); i++) {
            if (lsm.isSelectedIndex(i)) {
                //output.append(" " + i);
                //System.out.print("" + i + " ");
                //System.out.print("" + lsm.getElementAt(2));
                
            }
        }
        //System.out.println();
    }//GEN-LAST:event_botListValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList botList;
    private javax.swing.JLabel connectionStatusText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the botList
     */
    public javax.swing.JList getBotList() {
        return botList;
    }

    /**
     * @param botList the botList to set
     */
    public void setBotList(javax.swing.JList botList) {
        this.botList = botList;
    }
}

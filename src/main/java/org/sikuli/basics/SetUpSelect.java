/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sikuli.basics;

import java.awt.Color;

/**
 *
 * @author rhocke
 */
public class SetUpSelect extends javax.swing.JPanel {
  
  /**
   * Creates new form SetUpSelect
   */
  public SetUpSelect() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    textField1 = new java.awt.TextField();
    jLabel1 = new javax.swing.JLabel();
    jSeparator1 = new javax.swing.JSeparator();
    jLabel2 = new javax.swing.JLabel();
    suSystem = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    suJava = new javax.swing.JLabel();
    jSeparator2 = new javax.swing.JSeparator();
    jLabel4 = new javax.swing.JLabel();
    suFolder = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jSeparator4 = new javax.swing.JSeparator();
    jSeparator5 = new javax.swing.JSeparator();
    option1 = new javax.swing.JCheckBox();
    option4 = new javax.swing.JCheckBox();
    option3 = new javax.swing.JCheckBox();
    option2 = new javax.swing.JCheckBox();
    jSeparator6 = new javax.swing.JSeparator();
    ask1 = new javax.swing.JButton();
    ask2 = new javax.swing.JButton();
    ask3 = new javax.swing.JButton();
    ask4 = new javax.swing.JButton();
    setupNow = new javax.swing.JButton();
    option5 = new javax.swing.JCheckBox();
    ask5 = new javax.swing.JButton();
    option6 = new javax.swing.JCheckBox();
    ask6 = new javax.swing.JButton();
    jButton1 = new javax.swing.JButton();
    suVersion = new javax.swing.JLabel();

    textField1.setText("textField1");

    jLabel1.setText("SikuliX SetUp ");
    jLabel1.setAlignmentX(0.5F);

    jLabel2.setText("We are running on: ");

    suSystem.setText("...");

    jLabel3.setText("... using this Java: ");

    suJava.setText("...");

    jLabel4.setText("... in this folder");

    suFolder.setText("...");

    jLabel5.setText("Please check the appropriate options below:   (click the [ H ] buttons to get more specific information about the option)");

    option1.setText("1 - Pack1: I want to use all options: Sikuli IDE, running scripts from commandline (and optionally Java developement)");

    option4.setText("4 - Pack3: I only want to develop in Java or Jython using NetBeans, Eclipse or other IDE's (I do not need Pack1 nor Pack2)");
    option4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        option4ActionPerformed(evt);
      }
    });

    option3.setText("3 - Pack3: Additionally I want to develop and run scripts in Jython language using Eclipse or any other IDE or other methods");
    option3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        option3ActionPerformed(evt);
      }
    });

    option2.setText("2 - Pack2: I want to run Sikuli scripts from command line (usage as Pack 1, but I do not need the Sikuli IDE)");
    option2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        option2ActionPerformed(evt);
      }
    });

    ask1.setText("H");
    ask1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ask1ActionPerformed(evt);
      }
    });

    ask2.setText("H");
    ask2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ask2ActionPerformed(evt);
      }
    });

    ask3.setText("H");
    ask3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ask3ActionPerformed(evt);
      }
    });

    ask4.setText("H");
    ask4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ask4ActionPerformed(evt);
      }
    });

    setupNow.setText("Setup Now");
    setupNow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        setupNowActionPerformed(evt);
      }
    });

    option5.setText("5 - I want to use Tesseract based OCR features (You should know what you are doing and be experienced!)");
    option5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        option5ActionPerformed(evt);
      }
    });

    ask5.setText("H");
    ask5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ask5ActionPerformed(evt);
      }
    });

    option6.setText("6 - I want the packages to be useable on Windows, Mac and Linux (they contain the stuff for all systems - one pack for all)");
    option6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        option6ActionPerformed(evt);
      }
    });

    ask6.setText("H");
    ask6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ask6ActionPerformed(evt);
      }
    });

    jButton1.setText("Cancel");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    suVersion.setText("...");

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(jSeparator1)
          .add(jSeparator2)
          .add(jSeparator5)
          .add(layout.createSequentialGroup()
            .add(option1)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(ask1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(layout.createSequentialGroup()
            .add(option2)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(ask2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(layout.createSequentialGroup()
            .add(option3)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(ask3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
            .add(0, 0, Short.MAX_VALUE)
            .add(jButton1)
            .add(39, 39, 39)
            .add(setupNow))
          .add(org.jdesktop.layout.GroupLayout.TRAILING, jSeparator6)
          .add(layout.createSequentialGroup()
            .add(option5)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(ask4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(layout.createSequentialGroup()
            .add(option4)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(ask5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(suVersion))
              .add(layout.createSequentialGroup()
                .add(6, 6, 6)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                  .add(layout.createSequentialGroup()
                    .add(jLabel2)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                    .add(suSystem))
                  .add(layout.createSequentialGroup()
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                      .add(jLabel3)
                      .add(jLabel4))
                    .add(18, 18, 18)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                      .add(suFolder)
                      .add(suJava)))))
              .add(jLabel5))
            .add(0, 0, Short.MAX_VALUE))
          .add(layout.createSequentialGroup()
            .add(option6)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(ask6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
      .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(layout.createSequentialGroup()
          .addContainerGap()
          .add(jSeparator4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
          .addContainerGap()))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .add(14, 14, 14)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jLabel1)
          .add(suVersion))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jLabel2)
          .add(suSystem))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jLabel4)
          .add(suFolder))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(suJava)
          .add(jLabel3))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
        .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .add(18, 18, 18)
        .add(jLabel5)
        .add(18, 18, 18)
        .add(jSeparator5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .add(18, 18, 18)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(option1)
          .add(ask1))
        .add(18, 18, 18)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(option2)
          .add(ask2))
        .add(18, 18, 18)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(option3)
          .add(ask3))
        .add(18, 18, 18)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(option4)
          .add(ask5))
        .add(18, 18, 18)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(option5)
          .add(ask4))
        .add(18, 18, 18)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(ask6)
          .add(option6))
        .add(18, 18, 18)
        .add(jSeparator6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .add(18, 18, 18)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(setupNow)
          .add(jButton1))
        .addContainerGap(17, Short.MAX_VALUE))
      .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(layout.createSequentialGroup()
          .add(172, 172, 172)
          .add(jSeparator4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .addContainerGap(406, Short.MAX_VALUE)))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void option4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_option4ActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_option4ActionPerformed

  private void option3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_option3ActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_option3ActionPerformed

  private void option2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_option2ActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_option2ActionPerformed

  private void ask1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ask1ActionPerformed
    RunSetup.helpOption(1);
  }//GEN-LAST:event_ask1ActionPerformed

  private void ask2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ask2ActionPerformed
    RunSetup.helpOption(2);
  }//GEN-LAST:event_ask2ActionPerformed

  private void ask3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ask3ActionPerformed
    RunSetup.helpOption(3);
  }//GEN-LAST:event_ask3ActionPerformed

  private void ask4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ask4ActionPerformed
    RunSetup.helpOption(4);
  }//GEN-LAST:event_ask4ActionPerformed

  private void setupNowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupNowActionPerformed
    setBackground(Color.YELLOW);
  }//GEN-LAST:event_setupNowActionPerformed

  private void option5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_option5ActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_option5ActionPerformed

  private void ask5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ask5ActionPerformed
    RunSetup.helpOption(5);
  }//GEN-LAST:event_ask5ActionPerformed

  private void option6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_option6ActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_option6ActionPerformed

  private void ask6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ask6ActionPerformed
    RunSetup.helpOption(6);
  }//GEN-LAST:event_ask6ActionPerformed

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    System.exit(0);
  }//GEN-LAST:event_jButton1ActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  public javax.swing.JButton ask1;
  public javax.swing.JButton ask2;
  public javax.swing.JButton ask3;
  public javax.swing.JButton ask4;
  public javax.swing.JButton ask5;
  public javax.swing.JButton ask6;
  private javax.swing.JButton jButton1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JSeparator jSeparator4;
  private javax.swing.JSeparator jSeparator5;
  private javax.swing.JSeparator jSeparator6;
  protected javax.swing.JCheckBox option1;
  protected javax.swing.JCheckBox option2;
  protected javax.swing.JCheckBox option3;
  protected javax.swing.JCheckBox option4;
  protected javax.swing.JCheckBox option5;
  protected javax.swing.JCheckBox option6;
  private javax.swing.JButton setupNow;
  public javax.swing.JLabel suFolder;
  protected javax.swing.JLabel suJava;
  protected javax.swing.JLabel suSystem;
  public javax.swing.JLabel suVersion;
  private java.awt.TextField textField1;
  // End of variables declaration//GEN-END:variables
}

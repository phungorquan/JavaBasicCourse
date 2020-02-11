/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Shift + F11 to clean and build after add JDBC MySQL driver
 */
package doanjava;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Xiu
 */

class DTBFunc
{
    public static String Login_Register(String UsrN, String Psw, String Type)
    {
         try {
            Class.forName("com.mysql.jdbc.Driver");
            //here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/doanjava", "root", "")) {
            //here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from account");
                     
            String Input_UsrN;
            String Input_Psw;
            boolean Check_Exist = false;
            while(rs.next())
            {
              Input_UsrN = rs.getString(2);
              Input_Psw = rs.getString(3);
              if(UsrN.equals(Input_UsrN))   // If Username match with database
              {
                  if(Type.equals("Login"))  // If this is Login type
                  {
                    Check_Exist = true;     // Set Exist when match one of Username in database
                    if(Psw.equals(Input_Psw)) // If Passwork also match
                        return rs.getString(4); // Return Function of this account
                    else 
                        return "WrongPass"; // If not, return Wrongpassword
                  }
                  else return "Existed";    // If this is Registered type -> Existed account in database. Need register a new one
                  
              }
            }    
                if(Check_Exist == false)    // If not exist
                {
                    if(Type.equals("Login"))    // If this is type Login. 
                        return "NotExist";      // Return account Not Exist in database
                    else return "Successfully"; // Return Registered successfully
                }
                  
            }
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println(e);
    }
        return "Failed";
    }
    
    public static int Insert_Update_Delete(String req)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //here sonoo is database name, root is username and password
            try (Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/doanjava", "root", "")) {
            //here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(req);          // Insert Update or Delete
            if(rs > 0)  // If success
                return 1; // Return 1
            else return 0; // else Return 0
          
            }
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println(e);
    }
        return 0;
    }
        
}

public class MainJFrame extends javax.swing.JFrame {
    // Create two global model variable for two tables (support to add Row in table)
public class GlobalModelVariable {
   public DefaultTableModel DHT_Table = (DefaultTableModel) Table_DHT.getModel();
   public DefaultTableModel Account_Table = (DefaultTableModel) Table_Acc.getModel();
}
/**
     * Creates new form MainJFrame
     * @param type
     * @param req
     */
    
public void RequestDTB(String type,String req)
    {
        try {
   Class.forName("com.mysql.jdbc.Driver");
      //here sonoo is database name, root is username and password
      try (Connection con = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/doanjava", "root", "")) {
          //here sonoo is database name, root is username and password
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(req);
          
          if(type.equals("DHT"))
          {
            while (rs.next())
            {
                TxF_Temperature.setText(Float.toString(rs.getFloat(3)));    // Get Temp to display
                TxF_Hudmility.setText(Float.toString(rs.getFloat(4)));      // Get Hum to display
            }   
          }
          else if(type.equals("Control"))
          {
               while (rs.next())
            {
                if(rs.getInt(1) == 1)
                {
                    if(rs.getInt(3) == 1)
                    {
                        Tgl_LED1.setText("ON");         // Display ON Led1
                        Tgl_LED1.setSelected(true);     
                    }
                    else if(rs.getInt(3) == 0)          
                    {
                        Tgl_LED1.setText("OFF");        // Display OFF Led1
                        Tgl_LED1.setSelected(false);
                    }
                }
                else if(rs.getInt(1) == 2)
                {
                    if(rs.getInt(3) == 1)
                    {       
                        Tgl_LED2.setText("ON");         // Display ON Led2
                        Tgl_LED2.setSelected(true);
                    }
                    else if(rs.getInt(3) == 0)
                    {
                        Tgl_LED2.setText("OFF");        // Display OFF Led2
                        Tgl_LED2.setSelected(false);
                    }
                }
                else if(rs.getInt(1) == 3)
                {
                    if(rs.getInt(3) == 1)
                    {
                        Tgl_LED3.setText("ON");         // Display ON Led3
                        Tgl_LED3.setSelected(true);
                    }
                    else if(rs.getInt(3) == 0)
                    {
                        Tgl_LED3.setText("OFF");        // Display OFF Led3
                        Tgl_LED3.setSelected(false);
                    }
                }
                
            } 
          }
          else if(type.equals("TableDHT"))
          {
              GlobalModelVariable Mytable = new GlobalModelVariable(); 
              Mytable.DHT_Table.setRowCount(1);
              if(rs.next())
              {     
                  // Adđ value to row
                   Table_DHT.getModel().setValueAt(Float.toString(rs.getFloat(2)),0,0); // Value Row Column
                   Table_DHT.getModel().setValueAt(Float.toString(rs.getFloat(3)),0,1); // Value Row Column
                   Table_DHT.getModel().setValueAt(Float.toString(rs.getFloat(4)),0,2); // Value Row Column
                   Table_DHT.getModel().setValueAt(Float.toString(rs.getFloat(5)),0,3); // Value Row Column
                   Table_DHT.getModel().setValueAt(rs.getString(6),0,4); // Value Row Column                  
              }
              else 
              {
                   Table_DHT.getModel().setValueAt("null",0,0); // Value Row Column
                   Table_DHT.getModel().setValueAt("null",0,1); // Value Row Column
                   Table_DHT.getModel().setValueAt("null",0,2); // Value Row Column
                   Table_DHT.getModel().setValueAt("null",0,3); // Value Row Column
                   Table_DHT.getModel().setValueAt("null",0,4); // Value Row Column
              } 
          }
          else if(type.equals("AllTableDHT"))
          {
              GlobalModelVariable Mytable = new GlobalModelVariable(); 
              Mytable.DHT_Table.setRowCount(0);
              String HTemp,LTemp,HHum,LHum,Date;
              
              while(rs.next())
              {
                   HTemp = Float.toString(rs.getFloat(2));
                   LTemp = Float.toString(rs.getFloat(3));
                   HHum = Float.toString(rs.getFloat(4));
                   LHum = Float.toString(rs.getFloat(5));
                   Date = rs.getString(6);                  
                   Mytable.DHT_Table.addRow(new Object[]{HTemp, LTemp, HHum, LHum, Date});   // Display history data of DHT22 on table
              }
          }
          else if(type.equals("ListAccount"))
          {
            GlobalModelVariable Mytable = new GlobalModelVariable(); 
            Mytable.Account_Table.setRowCount(0);
            String Id,UsrName,Psword,Func;

            rs.next();
            while(rs.next())
            {
                Id = rs.getString(1);
                UsrName = rs.getString(2);
                Psword = rs.getString(3);
                Func = rs.getString(4);
                Mytable.Account_Table.addRow(new Object[]{Id, UsrName, Psword, Func});   // Display all account on table
            }
          }

      }
  } catch (ClassNotFoundException | SQLException e) {
    System.out.println(e);
  }
 }

public String Get_Function(String ID)
{
    try {
   Class.forName("com.mysql.jdbc.Driver");
      //here sonoo is database name, root is username and password
      try (Connection con = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/doanjava", "root", "")) {
          //here sonoo is database name, root is username and password
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery("select * from account where Id = " + ID);
          rs.next();    
          return rs.getString(4);
      }
  } catch (ClassNotFoundException | SQLException e) {
   System.out.println(e);
  }
    return "";
}

    
    public MainJFrame() {
        initComponents();
        Btn_Exit1.setVisible(false);
        
        Panel_Inside.setVisible(false); // Disable Panel Setting
        Panel_Outside.setVisible(true); // Enable Panel Account
        Panel_Account.setVisible(true); 
        Panel_AccEdit.setVisible(false);
        Panel_AccTable.setVisible(false);
  
        // Event to get value when use select row in Account table
        Table_Acc.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if(Table_Acc.getSelectedRow() >= 0)
            {
                TxF_EditID.setText(Table_Acc.getValueAt(Table_Acc.getSelectedRow(), 0) + "");
            }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel_Inside = new javax.swing.JPanel();
        Panel_TempHum = new javax.swing.JPanel();
        Lb_Temperature = new javax.swing.JLabel();
        Lb_Humidity = new javax.swing.JLabel();
        TxF_Temperature = new javax.swing.JTextField();
        Lb_DHTTittle = new javax.swing.JLabel();
        TxF_Hudmility = new javax.swing.JTextField();
        Panel_Control = new javax.swing.JPanel();
        Tgl_LED3 = new javax.swing.JToggleButton();
        Lb_LED3 = new javax.swing.JLabel();
        Tgl_LED2 = new javax.swing.JToggleButton();
        Tgl_LED1 = new javax.swing.JToggleButton();
        Lb_LED2 = new javax.swing.JLabel();
        Lb_Control = new javax.swing.JLabel();
        Lb_LED1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_DHT = new javax.swing.JTable();
        DatePicker_HisDHT22 = new org.jdesktop.swingx.JXDatePicker();
        Btn_ListAll = new javax.swing.JButton();
        Btn_Exit = new javax.swing.JButton();
        Panel_Outside = new javax.swing.JPanel();
        Panel_Account = new javax.swing.JPanel();
        Lb_AccUsrN = new javax.swing.JLabel();
        TxF_AccUsrN = new javax.swing.JTextField();
        Lb_Accpass = new javax.swing.JLabel();
        Btn_AccLogin = new javax.swing.JButton();
        TxF_AccPass = new javax.swing.JPasswordField();
        Lb_AccWarn = new javax.swing.JLabel();
        Btn_AccReg = new javax.swing.JButton();
        Panel_AccEdit = new javax.swing.JPanel();
        Panel_EditPass = new javax.swing.JPanel();
        Lb_NewPass = new javax.swing.JLabel();
        Lb_ConfirmPass = new javax.swing.JLabel();
        TxF_NewPass = new javax.swing.JPasswordField();
        TxF_ConfirmPass = new javax.swing.JPasswordField();
        Btn_OKEdit = new javax.swing.JButton();
        Btn_Goinside = new javax.swing.JButton();
        Panel_EditCommand = new javax.swing.JPanel();
        TxF_EditID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Cbb_Command = new javax.swing.JComboBox<>();
        Lb_EditWarn = new javax.swing.JLabel();
        Panel_AccTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Acc = new javax.swing.JTable();
        Btn_Refresh = new javax.swing.JButton();
        Btn_Exit1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Control and Monitor Temperature - Humidity");
        setMaximumSize(new java.awt.Dimension(400, 400));
        setMinimumSize(new java.awt.Dimension(365, 420));
        setPreferredSize(new java.awt.Dimension(250, 210));
        getContentPane().setLayout(new java.awt.CardLayout());

        Panel_TempHum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Lb_Temperature.setText("Temperature");

        Lb_Humidity.setText("Humidity");

        TxF_Temperature.setEditable(false);

        Lb_DHTTittle.setText("DHT");

        TxF_Hudmility.setEditable(false);

        javax.swing.GroupLayout Panel_TempHumLayout = new javax.swing.GroupLayout(Panel_TempHum);
        Panel_TempHum.setLayout(Panel_TempHumLayout);
        Panel_TempHumLayout.setHorizontalGroup(
            Panel_TempHumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_TempHumLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(Panel_TempHumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Panel_TempHumLayout.createSequentialGroup()
                        .addComponent(Lb_DHTTittle)
                        .addGap(10, 10, 10))
                    .addGroup(Panel_TempHumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Lb_Humidity)
                        .addComponent(TxF_Temperature, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TxF_Hudmility, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_TempHumLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Lb_Temperature)
                .addContainerGap())
        );
        Panel_TempHumLayout.setVerticalGroup(
            Panel_TempHumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_TempHumLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Lb_DHTTittle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Lb_Temperature)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxF_Temperature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(Lb_Humidity)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxF_Hudmility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Panel_Control.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Tgl_LED3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tgl_LED3ActionPerformed(evt);
            }
        });

        Lb_LED3.setText("LED3");

        Tgl_LED2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tgl_LED2ActionPerformed(evt);
            }
        });

        Tgl_LED1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tgl_LED1ActionPerformed(evt);
            }
        });

        Lb_LED2.setText("LED2");

        Lb_Control.setText("Control");

        Lb_LED1.setText("LED1");

        javax.swing.GroupLayout Panel_ControlLayout = new javax.swing.GroupLayout(Panel_Control);
        Panel_Control.setLayout(Panel_ControlLayout);
        Panel_ControlLayout.setHorizontalGroup(
            Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ControlLayout.createSequentialGroup()
                .addGroup(Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_ControlLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_ControlLayout.createSequentialGroup()
                                .addComponent(Lb_LED2)
                                .addGap(18, 18, 18)
                                .addComponent(Tgl_LED2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_ControlLayout.createSequentialGroup()
                                .addComponent(Lb_LED3)
                                .addGap(18, 18, 18)
                                .addComponent(Tgl_LED3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_ControlLayout.createSequentialGroup()
                                .addComponent(Lb_LED1)
                                .addGap(18, 18, 18)
                                .addComponent(Tgl_LED1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(Panel_ControlLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(Lb_Control)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_ControlLayout.setVerticalGroup(
            Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Lb_Control)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tgl_LED1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lb_LED1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_ControlLayout.createSequentialGroup()
                        .addComponent(Tgl_LED2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Tgl_LED3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Lb_LED3)))
                    .addComponent(Lb_LED2))
                .addGap(13, 13, 13))
        );

        Table_DHT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "HTemp", "LTemp", "HHum", "LHum", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_DHT.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setViewportView(Table_DHT);
        if (Table_DHT.getColumnModel().getColumnCount() > 0) {
            Table_DHT.getColumnModel().getColumn(0).setMinWidth(55);
            Table_DHT.getColumnModel().getColumn(0).setMaxWidth(55);
            Table_DHT.getColumnModel().getColumn(1).setMinWidth(55);
            Table_DHT.getColumnModel().getColumn(1).setMaxWidth(55);
            Table_DHT.getColumnModel().getColumn(2).setMinWidth(55);
            Table_DHT.getColumnModel().getColumn(2).setMaxWidth(55);
            Table_DHT.getColumnModel().getColumn(3).setMinWidth(55);
            Table_DHT.getColumnModel().getColumn(3).setMaxWidth(55);
            Table_DHT.getColumnModel().getColumn(4).setMinWidth(100);
            Table_DHT.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        DatePicker_HisDHT22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DatePicker_HisDHT22ActionPerformed(evt);
            }
        });

        Btn_ListAll.setText("List all data");
        Btn_ListAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_ListAllActionPerformed(evt);
            }
        });

        Btn_Exit.setBackground(new java.awt.Color(200, 50, 0));
        Btn_Exit.setText("X");
        Btn_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_ExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_InsideLayout = new javax.swing.GroupLayout(Panel_Inside);
        Panel_Inside.setLayout(Panel_InsideLayout);
        Panel_InsideLayout.setHorizontalGroup(
            Panel_InsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_InsideLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(Btn_Exit)
                .addGap(22, 22, 22)
                .addComponent(Panel_TempHum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Panel_Control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(456, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_InsideLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_InsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_InsideLayout.createSequentialGroup()
                        .addComponent(DatePicker_HisDHT22, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(Btn_ListAll, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
        Panel_InsideLayout.setVerticalGroup(
            Panel_InsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_InsideLayout.createSequentialGroup()
                .addGroup(Panel_InsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_InsideLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Panel_InsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Panel_Control, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Panel_TempHum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(Btn_Exit))
                .addGap(18, 18, 18)
                .addGroup(Panel_InsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DatePicker_HisDHT22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_ListAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        getContentPane().add(Panel_Inside, "card2");

        Panel_Outside.setPreferredSize(new java.awt.Dimension(360, 372));

        Lb_AccUsrN.setText("Username");

        Lb_Accpass.setText("Password");

        Btn_AccLogin.setText("Log in");
        Btn_AccLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AccLoginActionPerformed(evt);
            }
        });

        Lb_AccWarn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Btn_AccReg.setText("Register");
        Btn_AccReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AccRegActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_AccountLayout = new javax.swing.GroupLayout(Panel_Account);
        Panel_Account.setLayout(Panel_AccountLayout);
        Panel_AccountLayout.setHorizontalGroup(
            Panel_AccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AccountLayout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addGroup(Panel_AccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TxF_AccUsrN, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(Lb_AccUsrN)
                    .addComponent(Lb_Accpass)
                    .addComponent(TxF_AccPass))
                .addContainerGap(74, Short.MAX_VALUE))
            .addGroup(Panel_AccountLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Lb_AccWarn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(Panel_AccountLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(Btn_AccLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_AccReg, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_AccountLayout.setVerticalGroup(
            Panel_AccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AccountLayout.createSequentialGroup()
                .addComponent(Lb_AccWarn, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(Lb_AccUsrN)
                .addGap(1, 1, 1)
                .addComponent(TxF_AccUsrN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Lb_Accpass)
                .addGap(4, 4, 4)
                .addComponent(TxF_AccPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_AccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_AccLogin)
                    .addComponent(Btn_AccReg))
                .addGap(12, 12, 12))
        );

        Lb_NewPass.setText("New Pass");

        Lb_ConfirmPass.setText("Confirm Pass");

        javax.swing.GroupLayout Panel_EditPassLayout = new javax.swing.GroupLayout(Panel_EditPass);
        Panel_EditPass.setLayout(Panel_EditPassLayout);
        Panel_EditPassLayout.setHorizontalGroup(
            Panel_EditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_EditPassLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_EditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Lb_ConfirmPass)
                    .addComponent(Lb_NewPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_EditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TxF_NewPass, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(TxF_ConfirmPass))
                .addContainerGap())
        );
        Panel_EditPassLayout.setVerticalGroup(
            Panel_EditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_EditPassLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(Lb_ConfirmPass))
            .addGroup(Panel_EditPassLayout.createSequentialGroup()
                .addGroup(Panel_EditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Lb_NewPass)
                    .addComponent(TxF_NewPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TxF_ConfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Btn_OKEdit.setText("OK");
        Btn_OKEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_OKEditActionPerformed(evt);
            }
        });

        Btn_Goinside.setText("Go inside");
        Btn_Goinside.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_GoinsideActionPerformed(evt);
            }
        });

        jLabel1.setText("ID");

        jLabel2.setText("Command");

        Cbb_Command.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Edit Password", "Edit Function", "Delete" }));
        Cbb_Command.setToolTipText("");

        javax.swing.GroupLayout Panel_EditCommandLayout = new javax.swing.GroupLayout(Panel_EditCommand);
        Panel_EditCommand.setLayout(Panel_EditCommandLayout);
        Panel_EditCommandLayout.setHorizontalGroup(
            Panel_EditCommandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_EditCommandLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_EditCommandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(TxF_EditID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_EditCommandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Cbb_Command, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(15, 15, 15))
        );
        Panel_EditCommandLayout.setVerticalGroup(
            Panel_EditCommandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_EditCommandLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(Panel_EditCommandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_EditCommandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxF_EditID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cbb_Command, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        Lb_EditWarn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lb_EditWarn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout Panel_AccEditLayout = new javax.swing.GroupLayout(Panel_AccEdit);
        Panel_AccEdit.setLayout(Panel_AccEditLayout);
        Panel_AccEditLayout.setHorizontalGroup(
            Panel_AccEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AccEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AccEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_AccEditLayout.createSequentialGroup()
                        .addComponent(Btn_OKEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Lb_EditWarn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_Goinside, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Panel_AccEditLayout.createSequentialGroup()
                        .addComponent(Panel_EditCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Panel_EditPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        Panel_AccEditLayout.setVerticalGroup(
            Panel_AccEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AccEditLayout.createSequentialGroup()
                .addGroup(Panel_AccEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Panel_EditPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panel_EditCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_AccEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Lb_EditWarn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Panel_AccEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Btn_OKEdit)
                        .addComponent(Btn_Goinside))))
        );

        Table_Acc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "UserName", "PassWord", "Function"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Table_Acc);
        if (Table_Acc.getColumnModel().getColumnCount() > 0) {
            Table_Acc.getColumnModel().getColumn(0).setMinWidth(40);
            Table_Acc.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        Btn_Refresh.setText("REFRESH");
        Btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_RefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_AccTableLayout = new javax.swing.GroupLayout(Panel_AccTable);
        Panel_AccTable.setLayout(Panel_AccTableLayout);
        Panel_AccTableLayout.setHorizontalGroup(
            Panel_AccTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AccTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AccTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(Btn_Refresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        Panel_AccTableLayout.setVerticalGroup(
            Panel_AccTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AccTableLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_Refresh)
                .addContainerGap())
        );

        Btn_Exit1.setBackground(new java.awt.Color(200, 50, 0));
        Btn_Exit1.setText("X");
        Btn_Exit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Exit1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_OutsideLayout = new javax.swing.GroupLayout(Panel_Outside);
        Panel_Outside.setLayout(Panel_OutsideLayout);
        Panel_OutsideLayout.setHorizontalGroup(
            Panel_OutsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_OutsideLayout.createSequentialGroup()
                .addGroup(Panel_OutsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_OutsideLayout.createSequentialGroup()
                        .addComponent(Btn_Exit1)
                        .addGap(51, 51, 51)
                        .addComponent(Panel_Account, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(Panel_OutsideLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Panel_OutsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Panel_AccEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Panel_AccTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        Panel_OutsideLayout.setVerticalGroup(
            Panel_OutsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_OutsideLayout.createSequentialGroup()
                .addGroup(Panel_OutsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Panel_Account, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_Exit1))
                .addGap(1, 1, 1)
                .addComponent(Panel_AccEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_AccTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(Panel_Outside, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tgl_LED1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tgl_LED1ActionPerformed
        String getBtnText = Tgl_LED1.getText();
        if(getBtnText.equals("ON"))
            DTBFunc.Insert_Update_Delete("Update devices set Status = 0 where Id = 1");
        else DTBFunc.Insert_Update_Delete("Update devices set Status = 1 where Id = 1");
    }//GEN-LAST:event_Tgl_LED1ActionPerformed

    private void Tgl_LED2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tgl_LED2ActionPerformed
        String getBtnText = Tgl_LED2.getText();
        if(getBtnText.equals("ON"))
            DTBFunc.Insert_Update_Delete("Update devices set Status = 0 where Id = 2");
        else DTBFunc.Insert_Update_Delete("Update devices set Status = 1 where Id = 2");
    }//GEN-LAST:event_Tgl_LED2ActionPerformed

    private void Tgl_LED3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tgl_LED3ActionPerformed
        String getBtnText = Tgl_LED3.getText();
        if(getBtnText.equals("ON"))
            DTBFunc.Insert_Update_Delete("Update devices set Status = 0 where Id = 3");
        else DTBFunc.Insert_Update_Delete("Update devices set Status = 1 where Id = 3");
    }//GEN-LAST:event_Tgl_LED3ActionPerformed

    private void DatePicker_HisDHT22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DatePicker_HisDHT22ActionPerformed
        // TODO add your handling code here:
        
        int _date = DatePicker_HisDHT22.getDate().getDate();
        int _month = DatePicker_HisDHT22.getDate().getMonth() + 1;
        int _year = DatePicker_HisDHT22.getDate().getYear() - 100; // Because 2019 is 119 -> minus 100 to get 19
        
        String combinedate = Integer.toString(_year) + '-'+ Integer.toString(_month) + '-' + Integer.toString(_date) + '\'';
        
        RequestDTB("TableDHT","select * from hisdht22 where Date ='20"+combinedate); // Transfer String as character , don't use ""
        
    }//GEN-LAST:event_DatePicker_HisDHT22ActionPerformed

    private void Btn_ListAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ListAllActionPerformed

        RequestDTB("AllTableDHT","select * from hisdht22");
    }//GEN-LAST:event_Btn_ListAllActionPerformed

    private void Btn_AccLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AccLoginActionPerformed
   
        String UsrName = TxF_AccUsrN.getText();
        String PassWord = TxF_AccPass.getText();
        String GetnotiStr;
        if(UsrName.length() > 0 && PassWord.length() > 0)
        {
            GetnotiStr = DTBFunc.Login_Register(UsrName, PassWord,"Login");
            if(GetnotiStr.equals("Admin")) // If admin , display table account 
            {   
                Btn_Exit1.setVisible(true); 
                Panel_Account.setVisible(false);
                Panel_AccEdit.setVisible(true);
                Panel_EditCommand.setVisible(true);
                Panel_EditPass.setVisible(true);
                Panel_AccTable.setVisible(true);
                RequestDTB("ListAccount", "select * from account");
            }
            else if(GetnotiStr.equals("User")) // If user , hide table account
            {   
                Btn_Exit1.setVisible(true); 
                Panel_Account.setVisible(false);
                Panel_AccEdit.setVisible(true);
                Panel_EditCommand.setVisible(false);
                Panel_EditPass.setVisible(true);
                Panel_AccTable.setVisible(false);
            }
            else if(GetnotiStr.equals("WrongPass"))
                Lb_AccWarn.setText("Wrong Pass!!!");
            else if(GetnotiStr.equals("NotExist"))
                Lb_AccWarn.setText("Account not exist!!!");
        }
        else Lb_AccWarn.setText("Input is empty");
        


    }//GEN-LAST:event_Btn_AccLoginActionPerformed

    private void Btn_AccRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AccRegActionPerformed

        String UsrName = TxF_AccUsrN.getText();
        String PassWord = TxF_AccPass.getText();
        String GetnotiStr;
        if(UsrName.length() > 0 && PassWord.length() > 0)
        {
            GetnotiStr = DTBFunc.Login_Register(UsrName, PassWord,"Register");
            String Combine_Str = '\'' + UsrName + '\'' +','+ '\'' + PassWord + '\'' + ')';
            if(GetnotiStr.equals("Successfully"))
            {
                if(DTBFunc.Insert_Update_Delete("insert into account (Function,UserName,PassWord)values ('User'," + Combine_Str) > 0)
                   Lb_AccWarn.setText("Successfully Registered");         
                else Lb_AccWarn.setText("Registration Failed");
            }
            else if(GetnotiStr.equals("Existed"))
                Lb_AccWarn.setText("Registration Failed - Account is existed");
        }
        else Lb_AccWarn.setText("Input is empty");
        
    }//GEN-LAST:event_Btn_AccRegActionPerformed

    private void Btn_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ExitActionPerformed
        Btn_Exit1.setVisible(false);
        Panel_Inside.setVisible(false);
        Panel_Outside.setVisible(true);
        Panel_Account.setVisible(true);
        Panel_AccEdit.setVisible(false);
        Panel_AccTable.setVisible(false);

        Lb_AccWarn.setText("");
        Lb_EditWarn.setText("");
        TxF_AccPass.setText("");
        TxF_AccUsrN.setText("");
        TxF_NewPass.setText("");
        TxF_ConfirmPass.setText("");
        TxF_EditID.setText("");
    }//GEN-LAST:event_Btn_ExitActionPerformed

    private void Btn_GoinsideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_GoinsideActionPerformed

        Panel_Inside.setVisible(true);
        Panel_Outside.setVisible(false); 
        
        RequestDTB("Control","select * from devices");
        Timer timer=new Timer(5000, (ActionEvent e) -> {
           RequestDTB("DHT","select * from dht22");
           RequestDTB("Control","select * from devices");
           RequestDTB("AllTableDHT","select * from hisdht22");
        });
        timer.start();
    }//GEN-LAST:event_Btn_GoinsideActionPerformed

    private void Btn_OKEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_OKEditActionPerformed
            
        String Get_ID = TxF_EditID.getText();
        String Get_This_UserName = TxF_AccUsrN.getText();
        String Get_Combobox_Item = (String)Cbb_Command.getSelectedItem();
        
        if(Get_Combobox_Item.equals("Delete"))
        {   
            System.out.println("dELETE");
            if(Get_ID.length() > 0)
            {
                if(DTBFunc.Insert_Update_Delete("DELETE FROM account WHERE id = " + Get_ID)>0)
                    Lb_EditWarn.setText("Deleted Successfully");
                else Lb_EditWarn.setText("Failed");
            }
            else Lb_EditWarn.setText("ID is empty");
            
        }
        else if(Get_Combobox_Item.equals("Edit Password"))
        {   
            String New_Pass = TxF_NewPass.getText();
            String Confirm_Pass = TxF_ConfirmPass.getText();
            if(New_Pass.length() > 0 && Confirm_Pass.length() > 0 && Get_ID.length()>0)
            {
                if(New_Pass.equals(Confirm_Pass))
                {
                    if(DTBFunc.Insert_Update_Delete("Update account set PassWord = " + Confirm_Pass + " where Id = " + Get_ID)>0)
                        Lb_EditWarn.setText("Pass was changed");
                    else Lb_EditWarn.setText("Failed");
                }
                else Lb_EditWarn.setText("Pass doesn't match");
            }
            else if (New_Pass.length() > 0 && Confirm_Pass.length() > 0)
            {
                if(New_Pass.equals(Confirm_Pass))
                {
                    if(DTBFunc.Insert_Update_Delete("Update account set PassWord = " + Confirm_Pass + " where UserName = " + '\'' + Get_This_UserName + '\'') > 0)
                        Lb_EditWarn.setText("Pass was changed");
                    else Lb_EditWarn.setText("Failed");
                }
                else Lb_EditWarn.setText("Pass doesn't match");
            }
            else Lb_EditWarn.setText("Input is empty");
            
        } 
        else if(Get_Combobox_Item.equals("Edit Function"))
        {
            if(Get_ID.length() > 0)
            {
                String Get_Function = Get_Function(Get_ID);
                if(Get_Function.equals("Admin"))
                {
                    if(DTBFunc.Insert_Update_Delete("Update account set Function = 'User' where Id = " + Get_ID)>0)
                        Lb_EditWarn.setText("Func was changed");
                    else Lb_EditWarn.setText("Failed");
                }
                else
                {
                    if(DTBFunc.Insert_Update_Delete("Update account set Function = 'Admin' where Id = " + Get_ID)>0)
                        Lb_EditWarn.setText("Func was changed");
                    else Lb_EditWarn.setText("Failed");
                }
            }
            else 
            {
                System.out.println("EDIT FUNC");
                Lb_EditWarn.setText("ID is empty");
            }
        }
        RequestDTB("ListAccount", "select * from account");
    }//GEN-LAST:event_Btn_OKEditActionPerformed

    private void Btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_RefreshActionPerformed
       RequestDTB("ListAccount", "select * from account");
    }//GEN-LAST:event_Btn_RefreshActionPerformed

    private void Btn_Exit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Exit1ActionPerformed
        Btn_Exit1.setVisible(false);
        Panel_Inside.setVisible(false);
        Panel_Outside.setVisible(true);
        Panel_Account.setVisible(true);
        Panel_AccEdit.setVisible(false);
        Panel_AccTable.setVisible(false);

        Lb_AccWarn.setText("");
        Lb_EditWarn.setText("");
        TxF_AccPass.setText("");
        TxF_AccUsrN.setText("");
        TxF_NewPass.setText("");
        TxF_ConfirmPass.setText("");
        TxF_EditID.setText("");
    }//GEN-LAST:event_Btn_Exit1ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
        
       
                

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_AccLogin;
    private javax.swing.JButton Btn_AccReg;
    private javax.swing.JButton Btn_Exit;
    private javax.swing.JButton Btn_Exit1;
    private javax.swing.JButton Btn_Goinside;
    private javax.swing.JButton Btn_ListAll;
    private javax.swing.JButton Btn_OKEdit;
    private javax.swing.JButton Btn_Refresh;
    private javax.swing.JComboBox<String> Cbb_Command;
    private org.jdesktop.swingx.JXDatePicker DatePicker_HisDHT22;
    private javax.swing.JLabel Lb_AccUsrN;
    private javax.swing.JLabel Lb_AccWarn;
    private javax.swing.JLabel Lb_Accpass;
    private javax.swing.JLabel Lb_ConfirmPass;
    private javax.swing.JLabel Lb_Control;
    private javax.swing.JLabel Lb_DHTTittle;
    private javax.swing.JLabel Lb_EditWarn;
    private javax.swing.JLabel Lb_Humidity;
    private javax.swing.JLabel Lb_LED1;
    private javax.swing.JLabel Lb_LED2;
    private javax.swing.JLabel Lb_LED3;
    private javax.swing.JLabel Lb_NewPass;
    private javax.swing.JLabel Lb_Temperature;
    private javax.swing.JPanel Panel_AccEdit;
    private javax.swing.JPanel Panel_AccTable;
    private javax.swing.JPanel Panel_Account;
    private javax.swing.JPanel Panel_Control;
    private javax.swing.JPanel Panel_EditCommand;
    private javax.swing.JPanel Panel_EditPass;
    private javax.swing.JPanel Panel_Inside;
    private javax.swing.JPanel Panel_Outside;
    private javax.swing.JPanel Panel_TempHum;
    private javax.swing.JTable Table_Acc;
    private javax.swing.JTable Table_DHT;
    private javax.swing.JToggleButton Tgl_LED1;
    private javax.swing.JToggleButton Tgl_LED2;
    private javax.swing.JToggleButton Tgl_LED3;
    private javax.swing.JPasswordField TxF_AccPass;
    private javax.swing.JTextField TxF_AccUsrN;
    private javax.swing.JPasswordField TxF_ConfirmPass;
    private javax.swing.JTextField TxF_EditID;
    private javax.swing.JTextField TxF_Hudmility;
    private javax.swing.JPasswordField TxF_NewPass;
    private javax.swing.JTextField TxF_Temperature;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}

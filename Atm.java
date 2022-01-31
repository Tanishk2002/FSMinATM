package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Atm extends JLayeredPane implements Runnable, ActionListener{

    JTextField tf, cashtf, amount;
    JTextArea cardtxt, t1,t2, t3, t4, cashtxt, bigtxt, balancetxt, transtxt;
    JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bcancel,bclear,benter,bcard;
    JButton a1,a2,a3,a4;
    JProgressBar pb;
    JPasswordField pf, pinf;
    JLabel successlb, newPin, pinChanged, enterAmt, amtAdded, prompt;
    Thread thread1, thread2;
    boolean cardInserted = false;
    int cardpressed = 0; int a1pressed = 0; int a2pressed = 0; int a3pressed = 0; int a4pressed = 0;
    int rectY = 530, i = 0, amt;
    int triX[] = {360,370,380};
    int triY[] = {530,516,530};
    int X[] = {360,370,380};
    int Y[] = {530,516,530};
    boolean balanceEnquiry, pinChange, cashWithdrawal, depositMoney;
    String str = "500000";

    Atm(){
        tf = new JTextField();
        successlb = new JLabel();
        newPin = new JLabel();
        enterAmt = new JLabel();
        amtAdded = new JLabel();
        pinChanged = new JLabel();
        prompt = new JLabel();
        cashtf = new JTextField();
        amount = new JTextField();
        cardtxt = new JTextArea();
        cashtxt = new JTextArea();
        balancetxt = new JTextArea();
        bigtxt = new JTextArea();
        t1 = new JTextArea();
        t2 = new JTextArea();
        t3 = new JTextArea();
        t4 = new JTextArea();
        transtxt = new JTextArea();
        pb = new JProgressBar();
        pf = new JPasswordField();
        pinf = new JPasswordField();
        b1 = new JButton("1"); b2 = new JButton("2"); b3 = new JButton("3"); b4 = new JButton("4");
        b5 = new JButton("5"); b6 = new JButton("6"); b7 = new JButton("7"); b8 = new JButton("8");
        b9 = new JButton("9"); b0 = new JButton("0");
        bcancel = new JButton("CANCEL"); bclear = new JButton("CLEAR"); benter = new JButton("ENTER");
        bcard = new JButton(); a1 = new JButton(">>"); a2 = new JButton(">>");
        a3 = new JButton("<<"); a4 = new JButton("<<");


        this.setLayout(null);
        this.setPreferredSize(new Dimension(500,600));
        this.setOpaque(true);
        this.setBackground(new Color(40,40,40));

        thread1 = new Thread(this);
        thread1.start();

        setProp();
        addingListeners();
        addToPanel();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(!cardInserted) {
            g.setColor(Color.ORANGE);
            g.fillPolygon(triX, triY, 3);
            g.fillRect(367, rectY, 8, 15);
            for (int x = 0; x < 3; x++) {
                triY[x] -= 2;
            }
            rectY -= 2;
            if (triY[1] == 496) {
                for (int x = 0; x < 3; x++) {
                    triX[x] = X[x];
                    triY[x] = Y[x];
                }
                rectY = 530;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60);
            } catch (Exception e) {
                System.out.print(e);
            }
            repaint();
            if(cardInserted)
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == bcard) {
            ++cardpressed;
            if (cardpressed == 1) {
                bcard.setBackground(Color.ORANGE);
                cardInserted = true;
                tf.setText("");
                t1.setVisible(true);
                t2.setVisible(true);
                t3.setVisible(true);
                t4.setVisible(true);
            }
        }

        if (ev.getSource() == a1) {
            if (cardInserted && a2pressed == 0 && a3pressed == 0 && a4pressed == 0) {
                ++a1pressed;
                if (a1pressed == 1) {
                    balanceEnquiry = true;
                    pinChange = false;
                    cashWithdrawal = false;
                    depositMoney = false;
                    t1.setVisible(false);
                    t2.setVisible(false);
                    t3.setVisible(false);
                    t4.setVisible(false);
                    balancetxt.setVisible(true);
                }
            }
        }

        if (ev.getSource() == a2 && a1pressed == 0 && a3pressed == 0 && a4pressed == 0) {
            if (cardInserted) {
                ++a2pressed;
                if (a2pressed == 1) {
                    pinChange = true;
                    balanceEnquiry = false;
                    cashWithdrawal = false;
                    depositMoney = false;
                    t1.setVisible(false);
                    t2.setVisible(false);
                    t3.setVisible(false);
                    t4.setVisible(false);
                    newPin.setVisible(true);
                    pinf.setVisible(true);
                }
            }
        }

        if (ev.getSource() == a3 && a1pressed == 0 && a2pressed == 0 && a4pressed == 0) {
            if (cardInserted) {
                ++a3pressed;
                if (a3pressed == 1) {
                    cashWithdrawal = true;
                    balanceEnquiry = false;
                    pinChange = false;
                    depositMoney = false;
                    t1.setVisible(false);
                    t2.setVisible(false);
                    t3.setVisible(false);
                    t4.setVisible(false);
                    cashtxt.setVisible(true);
                    pf.setVisible(true);
                }
            }
        }

        if (ev.getSource() == a4 && a1pressed == 0 && a2pressed == 0 && a3pressed == 0) {
            if (cardInserted) {
                ++a4pressed;
                if (a4pressed == 1) {
                    depositMoney = true;
                    balanceEnquiry = false;
                    pinChange = false;
                    cashWithdrawal = false;
                    t1.setVisible(false);
                    t2.setVisible(false);
                    t3.setVisible(false);
                    t4.setVisible(false);
                    enterAmt.setVisible(true);
                    amount.setVisible(true);
                }
            }
        }

        if (ev.getSource() == b1) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "1");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "1");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "1");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "1");
        }

        if (ev.getSource() == b2) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "2");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "2");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "2");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "2");
        }

        if (ev.getSource() == b3) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "3");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "3");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "3");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "3");
        }

        if (ev.getSource() == b4) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "4");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "4");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "4");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "4");
        }

        if (ev.getSource() == b5) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "5");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "5");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "5");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "5");
        }

        if (ev.getSource() == b6) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "6");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "6");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "6");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "6");
        }

        if (ev.getSource() == b7) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "7");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "7");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "7");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "7");
        }

        if (ev.getSource() == b8) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "8");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "8");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "8");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "8");
        }

        if (ev.getSource() == b9) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "9");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "9");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "9");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "9");
        }

        if (ev.getSource() == b0) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if (pf.isVisible() && (new String(pf.getPassword())).length() < 4)
                pf.setText(new String(pf.getPassword()) + "0");

            else if(cashtf.isVisible())
                cashtf.setText(cashtf.getText() + "0");

            else if(pinf.isVisible() && (new String(pinf.getPassword())).length() < 4)
                pinf.setText(new String(pinf.getPassword()) + "0");

            else if(amount.isVisible())
                amount.setText(amount.getText() + "0");
        }

        if (ev.getSource() == bcancel) {
            if(prompt.isVisible())
                prompt.setVisible(false);
            if(balanceEnquiry){
                if(balancetxt.isVisible()){
                    balancetxt.setVisible(false);
                    a1pressed = 0;
                    t1.setVisible(true);
                    t2.setVisible(true);
                    t3.setVisible(true);
                    t4.setVisible(true);
                }
            }
            else if(pinChange){
                if(newPin.isVisible() && pinf.isVisible() && (!pinChanged.isVisible())){
                    newPin.setVisible(false);
                    pinf.setVisible(false);
                    pinf.setText("");
                    pinChange = false;
                    a2pressed = 0;
                    t1.setVisible(true);
                    t2.setVisible(true);
                    t3.setVisible(true);
                    t4.setVisible(true);
                }

            }
            else if(cashWithdrawal){
                if(cashtxt.isVisible() && pf.isVisible()){
                    cashtxt.setVisible(false);
                    pf.setVisible(false);
                    pf.setText("");
                    a3pressed = 0;
                    t1.setVisible(true);
                    t2.setVisible(true);
                    t3.setVisible(true);
                    t4.setVisible(true);
                }
                else if(bigtxt.isVisible() && cashtf.isVisible()){
                    bigtxt.setVisible(false);
                    cashtf.setVisible(false);
                    cashtf.setText("Rs. ");
                    a3pressed = 0;
                    t1.setVisible(true);
                    t2.setVisible(true);
                    t3.setVisible(true);
                    t4.setVisible(true);
                }
            }
            else if(depositMoney){
                if(enterAmt.isVisible() && amount.isVisible() && !amtAdded.isVisible()){
                    enterAmt.setVisible(false);
                    amount.setVisible(false);
                    amount.setText("Rs. ");
                    a4pressed = 0;
                    t1.setVisible(true);
                    t2.setVisible(true);
                    t3.setVisible(true);
                    t4.setVisible(true);
                }
            }
            else if(t1.isVisible() && t2.isVisible() && t3.isVisible() && t4.isVisible())
                startThread();
        }

        if (ev.getSource() == bclear) {
            if(pf.isVisible())
                pf.setText("");
            else if(pinf.isVisible() && !pinChanged.isVisible())
                pinf.setText("");
            else if(cashtf.isVisible())
                cashtf.setText("Rs. ");
            else if(amount.isVisible() && !amtAdded.isVisible())
                amount.setText("Rs. ");
        }

        if (ev.getSource() == benter) {
            if (balanceEnquiry) {
                balancetxt.setVisible(false);
                t1.setVisible(true);
                t2.setVisible(true);
                t3.setVisible(true);
                t4.setVisible(true);
                a1pressed = 0;
            }
            else if (pinChange) {
                if(newPin.isVisible() && ((new String(pinf.getPassword())).length() == 4) && (!pinChanged.isVisible()))
                    pinChanged.setVisible(true);
                else if(pinChanged.isVisible()){
                    newPin.setVisible(false);
                    pinf.setVisible(false);
                    pinf.setText("");
                    pinChanged.setVisible(false);
                    t1.setVisible(true);
                    t2.setVisible(true);
                    t3.setVisible(true);
                    t4.setVisible(true);
                    a2pressed = 0;
                }
            }
            else if (cashWithdrawal) {
                if(pf.isVisible() && (new String(pf.getPassword())).length() == 4){
                    cashtxt.setVisible(false);
                    pf.setVisible(false);
                    pf.setText("");
                    bigtxt.setVisible(true);
                    cashtf.setVisible(true);
                }
                else if(cashtf.isVisible() && cashtf.getText().length() != 4){
                    amt = Integer.parseInt(cashtf.getText().substring(4));
                    if(amt == 0){
                        prompt.setVisible(true);
                        cashtf.setText("Rs. ");
                    }
                    else if(amt % 500 !=0 && amt % 2000 !=0){
                        prompt.setVisible(true);
                        cashtf.setText("Rs. ");
                    }
                    else{
                        cashtf.setVisible(false);
                        cashtf.setText("Rs. ");
                        bigtxt.setVisible(false);
                        transtxt.setVisible(true);
                        pb.setVisible(true);
                        new Thread(new ProgBar()).start();
                    }
                }
            }
            else if (depositMoney) {
                if(enterAmt.isVisible() && !amtAdded.isVisible() && amount.getText().length() != 4){

                    amt = Integer.parseInt(amount.getText().substring(4));
                    if(amt == 0){
                        prompt.setVisible(true);
                        amount.setText("Rs. ");
                    }
                    else if(amt % 500 !=0 && amt % 2000 !=0){
                        prompt.setVisible(true);
                        amount.setText("Rs. ");
                    }
                    else {
                        amtAdded.setVisible(true);
                        str = Integer.toString(Integer.parseInt(str) + amt);
                        balancetxt.setText("ACCOUNT BALANCE\n\n        Rs. " + str);
                    }
                }

                else if(amtAdded.isVisible()){
                    enterAmt.setVisible(false);
                    amount.setVisible(false);
                    amount.setText("Rs. ");
                    amtAdded.setVisible(false);
                    t1.setVisible(true);
                    t2.setVisible(true);
                    t3.setVisible(true);
                    t4.setVisible(true);
                    a4pressed = 0;
                }
            }

        }
    }

    void setProp(){
        tf.setBounds(95,10,300,200);  tf.setEditable(false);
        tf.setText("Welcome"); tf.setForeground(Color.WHITE); tf.setFont(new Font("Elephant",Font.ITALIC,35));
        tf.setHorizontalAlignment(SwingConstants.CENTER); tf.setBackground(new Color(55,55,55));
        tf.setBorder(BorderFactory.createLineBorder(Color.WHITE,4));
        successlb.setBounds(150,150,190,20); successlb.setText("Transaction Successful !");
        successlb.setForeground(Color.ORANGE); successlb.setFont(new Font("Arial",Font.BOLD,15));
        successlb.setOpaque(false); successlb.setVisible(false);
        amtAdded.setBounds(173,170,150,20); amtAdded.setText("Amount Deposited !"); amtAdded.setVisible(false);
        amtAdded.setForeground(Color.ORANGE); amtAdded.setFont(new Font("Arial",Font.BOLD,15));
        pinChanged.setBounds(195,150,190,20); pinChanged.setText("PIN Changed !"); pinChanged.setVisible(false);
        pinChanged.setForeground(Color.ORANGE); pinChanged.setFont(new Font("Arial",Font.BOLD,15));
        enterAmt.setBounds(183,70,150,20); enterAmt.setFont(new Font("Arial",Font.BOLD,15));
        enterAmt.setText("ENTER AMOUNT"); enterAmt.setForeground(Color.ORANGE); enterAmt.setVisible(false);

        cardtxt.setBounds(338,430,65,30); cardtxt.setFont(new Font("Elephant",Font.BOLD,12));
        cardtxt.setText("INSERT\n   CARD");  cardtxt.setEditable(false); cardtxt.setOpaque(false); cardtxt.setForeground(Color.YELLOW);
        cashtxt.setBounds(180,70,130,20); cashtxt.setFont(new Font("Arial",Font.BOLD,15));
        cashtxt.setText("ENTER YOUR PIN"); cashtxt.setForeground(Color.ORANGE); cashtxt.setOpaque(false); cashtxt.setVisible(false);
        newPin.setBounds(185,70,130,20); newPin.setFont(new Font("Arial",Font.BOLD,15));
        newPin.setText("ENTER NEW PIN"); newPin.setForeground(Color.ORANGE); newPin.setVisible(false);
        bigtxt.setBounds(150,50,190,55); bigtxt.setForeground(Color.ORANGE); bigtxt.setOpaque(false);
        bigtxt.setFont(new Font("Arial",Font.BOLD,15)); bigtxt.setVisible(false);
        bigtxt.setText("ENTER YOUR AMOUNT IN\n  MULTIPLES OF RS. 500\n             OR RS. 2000");
        transtxt.setBounds(150,50,190,55); transtxt.setForeground(Color.ORANGE); transtxt.setOpaque(false);
        transtxt.setFont(new Font("Arial",Font.BOLD,15)); transtxt.setVisible(false);
        transtxt.setText("YOUR TRANSACTION IS\n  BEING PROCESSED...");
        pf.setBounds(195,95,100,20); pf.setBackground(new Color(90,90,90)); pf.setForeground(Color.ORANGE);
        pf.setFont(new Font("Copper",Font.BOLD,25)); pf.setEditable(false); pf.setVisible(false);
        pinf.setBounds(195,95,100,20); pinf.setBackground(new Color(90,90,90));
        pinf.setForeground(Color.ORANGE); pinf.setFont(new Font("Copper",Font.BOLD,25)); pinf.setVisible(false);
        cashtf.setBounds(170,110,150,30); cashtf.setBackground(new Color(90,90,90)); cashtf.setForeground((Color.ORANGE));
        cashtf.setFont(new Font("Copper",Font.BOLD,20)); cashtf.setText("Rs. "); cashtf.setEditable(false);
        cashtf.setVisible(false);
        amount.setBounds(170,110,150,30); amount.setBackground(new Color(90,90,90));
        amount.setForeground(Color.ORANGE); amount.setFont(new Font("Copper",Font.BOLD,20)); amount.setText("Rs. ");
        amount.setEditable(false); amount.setVisible(false);
        prompt.setBounds(125,180,250,20); prompt.setFont(new Font("Arial",Font.PLAIN,15));
        prompt.setForeground(Color.RED); prompt.setText("*Multiples of Rs.500 or Rs.2000 !"); prompt.setVisible(false);

        pb.setValue(0); pb.setBounds(170,110,140,20); pb.setStringPainted(true); pb.setVisible(false);
        pb.setBackground(Color.WHITE); pb.setForeground(Color.ORANGE); pb.setFont(new Font("Copper",Font.BOLD,15));
        balancetxt.setBounds(140,60,200,80); balancetxt.setFont(new Font("Arial",Font.BOLD,20));
        balancetxt.setText("ACCOUNT BALANCE\n\n        Rs. " + str); balancetxt.setForeground(Color.ORANGE);
        balancetxt.setOpaque(false); balancetxt.setVisible(false);

        t1.setBounds(100,35,95,40); t1.setForeground(Color.ORANGE); t1.setFont(new Font("Elephant",Font.BOLD,15));
        t1.setText("BALANCE\nENQUIRY"); t1.setOpaque(false); t1.setVisible(false);
        t2.setBounds(100,115,95,40); t2.setForeground(Color.ORANGE); t2.setFont(new Font("Elephant",Font.BOLD,15));
        t2.setText("      PIN\nCHANGE"); t2.setOpaque(false); t2.setVisible(false);
        t3.setBounds(273,35,115,40); t3.setForeground(Color.ORANGE); t3.setFont(new Font("Elephant",Font.BOLD,15));
        t3.setText("        CASH\nWITHDRAW"); t3.setOpaque(false); t3.setVisible(false);
        t4.setBounds(300,115,90,40); t4.setForeground(Color.ORANGE); t4.setFont(new Font("Elephant",Font.BOLD,15));
        t4.setText("DEPOSIT\n  MONEY"); t4.setOpaque(false); t4.setVisible(false);

        b1.setBounds(75,235,60,40); b1.setFont(new Font("Algerian",Font.BOLD,30));
        b1.setFocusPainted(false); b1.setBackground(new Color(60,60,60)); b1.setForeground(Color.ORANGE);
        b2.setBounds(155,235,60,40); b2.setFont(new Font("Algerian",Font.BOLD,30));
        b2.setForeground(Color.ORANGE);  b2.setFocusPainted(false); b2.setBackground(new Color(60,60,60));
        b3.setBounds(235,235,60,40); b3.setFont(new Font("Algerian",Font.BOLD,30));
        b3.setForeground(Color.ORANGE);  b3.setFocusPainted(false); b3.setBackground(new Color(60,60,60));
        b4.setBounds(75,310,60,40); b4.setFont(new Font("Algerian",Font.BOLD,30));
        b4.setForeground(Color.ORANGE);  b4.setFocusPainted(false); b4.setBackground(new Color(60,60,60));
        b5.setBounds(155,310,60,40); b5.setFont(new Font("Algerian",Font.BOLD,30));
        b5.setForeground(Color.ORANGE);  b5.setFocusPainted(false); b5.setBackground(new Color(60,60,60));
        b6.setBounds(235,310,60,40); b6.setFont(new Font("Algerian",Font.BOLD,30));
        b6.setForeground(Color.ORANGE); b6.setFocusPainted(false); b6.setBackground(new Color(60,60,60));
        b7.setBounds(75,385,60,40); b7.setFont(new Font("Algerian",Font.BOLD,30));
        b7.setForeground(Color.ORANGE);  b7.setFocusPainted(false); b7.setBackground(new Color(60,60,60));
        b8.setBounds(155,385,60,40); b8.setFont(new Font("Algerian",Font.BOLD,30));
        b8.setForeground(Color.ORANGE);  b8.setFocusPainted(false); b8.setBackground(new Color(60,60,60));
        b9.setBounds(235,385,60,40); b9.setFont(new Font("Algerian",Font.BOLD,30));
        b9.setForeground(Color.ORANGE);  b9.setFocusPainted(false); b9.setBackground(new Color(60,60,60));
        b0.setBounds(155,460,60,40); b0.setFont(new Font("Algerian",Font.BOLD,30));
        b0.setForeground(Color.ORANGE);  b0.setFocusPainted(false); b0.setBackground(new Color(60,60,60));

        bcancel.setBounds(320,235,100,40); bcancel.setFont(new Font("Algerian",Font.BOLD,17));
        bcancel.setFocusPainted(false); bcancel.setForeground(Color.ORANGE); bcancel.setBackground(new Color(60,60,60));
        bclear.setBounds(320,295,100,40); bclear.setFont(new Font("Algerian",Font.BOLD,17));
        bclear.setFocusPainted(false); bclear.setForeground(Color.ORANGE); bclear.setBackground(new Color(60,60,60));
        benter.setBounds(320,355,100,40); benter.setFont(new Font("Algerian",Font.BOLD,17));
        benter.setFocusPainted(false); benter.setForeground(Color.ORANGE); benter.setBackground(new Color(60,60,60));
        bcard.setBounds(340,470,60,20); bcard.setFont(new Font("Algerian",Font.BOLD,16));
        bcard.setBorder(BorderFactory.createLineBorder(Color.WHITE,4)); bcard.setBackground(Color.BLACK);

        a1.setBounds(10,40,70,30); a1.setFont(new Font("Arial Black",Font.BOLD,25));
        a1.setFocusPainted(false); a1.setBackground(new Color(60,60,60)); a1.setForeground(Color.ORANGE);
        a2.setBounds(10,120,70,30); a2.setFont(new Font("Arial Black",Font.BOLD,25));
        a2.setFocusPainted(false); a2.setBackground(new Color(60,60,60)); a2.setForeground(Color.ORANGE);
        a3.setBounds(410,40,70,30); a3.setFont(new Font("Arial Black",Font.BOLD,25));
        a3.setFocusPainted(false); a3.setBackground(new Color(60,60,60)); a3.setForeground(Color.ORANGE);
        a4.setBounds(410,120,70,30); a4.setFont(new Font("Arial Black",Font.BOLD,25));
        a4.setFocusPainted(false); a4.setBackground(new Color(60,60,60)); a4.setForeground(Color.ORANGE);
    }

    void addingListeners(){
        b1.addActionListener(this); b2.addActionListener(this); b3.addActionListener(this); b4.addActionListener(this);
        b5.addActionListener(this); b6.addActionListener(this); b7.addActionListener(this); b8.addActionListener(this);
        b9.addActionListener(this); b0.addActionListener(this); bcancel.addActionListener(this); benter.addActionListener(this);
        bclear.addActionListener(this); bcard.addActionListener(this); a1.addActionListener(this); a2.addActionListener(this);
        a3.addActionListener(this); a4.addActionListener(this);

        b1.addMouseListener(new MyMouse()); b2.addMouseListener(new MyMouse()); b3.addMouseListener(new MyMouse());
        b4.addMouseListener(new MyMouse()); b5.addMouseListener(new MyMouse()); b6.addMouseListener(new MyMouse());
        b7.addMouseListener(new MyMouse()); b8.addMouseListener(new MyMouse()); b9.addMouseListener(new MyMouse());
        b0.addMouseListener(new MyMouse()); bcancel.addMouseListener(new MyMouse()); bclear.addMouseListener(new MyMouse());
        benter.addMouseListener(new MyMouse()); a1.addMouseListener(new MyMouse()); a2.addMouseListener(new MyMouse());
        a3.addMouseListener(new MyMouse()); a4.addMouseListener(new MyMouse());
    }

    void addToPanel(){
        this.add(tf); this.add(cardtxt); this.add(b1); this.add(b2); this.add(b3); this.add(b4); this.add(b5); this.add(b6);
        this.add(b7); this.add(b8); this.add(b9); this.add(b0); this.add(bcancel); this.add(bclear); this.add(benter);
        this.add(bcard); this.add(a1); this.add(a2); this.add(a3); this.add(a4);

        this.addImpl(t1,1,0); this.addImpl(t2,1,0); this.addImpl(t3,1,0);
        this.addImpl(t4,1,0); this.addImpl(cashtxt,1,0);
        this.addImpl(pf,1,0); this.addImpl(bigtxt,1,0); this.addImpl(cashtf,1,0);
        this.addImpl(pb,1,0); this.addImpl(successlb,1,0);
        this.addImpl(balancetxt,1,0); this.addImpl(pinf,1,0);
        this.addImpl(newPin,1,0); this.addImpl(pinChanged,1,0);
        this.addImpl(amount,1,0); this.addImpl(enterAmt,1,0);
        this.addImpl(amtAdded,1,0); this.addImpl(transtxt,1,0);
        this.addImpl(prompt,1,0);
    }

    void startThread(){
        new Thread(this).start();
    }

    class ProgBar implements Runnable{
        @Override
        public void run(){
            while(i < 100){
                pb.setValue(i+10);
                i += 10;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            successlb.setVisible(true);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bcard.setBackground(Color.BLACK);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            transtxt.setVisible(false);
            pb.setVisible(false);
            successlb.setVisible(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startThread();
            tf.setText("Welcome");
            cardpressed = 0;
            cardInserted = false;
            balanceEnquiry = pinChange = cashWithdrawal = depositMoney = false;
            a3pressed = 0;
            i = 0;
            pb.setValue(0);
        }
    }

    class MyMouse extends MouseAdapter{
        @Override
        public void mouseEntered(MouseEvent ev){
            JButton b = (JButton) ev.getSource();
            b.setForeground(Color.WHITE);
        }
        @Override
        public void mouseExited(MouseEvent ev){
            JButton b = (JButton) ev.getSource();
            b.setForeground(Color.ORANGE);
        }
    }
}






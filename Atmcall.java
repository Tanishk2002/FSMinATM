package com.company;

import javax.swing.JFrame;

class Atmcall {
    JFrame f = new JFrame("ATM Simulator");

    Atmcall(){
        f.add(new Atm());
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        f.setSize(500,600);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
    }
    public static void main(String []args){
        new Atmcall();
    }
}

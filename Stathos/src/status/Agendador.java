/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package status;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author a1700596
 */
public class Agendador extends Thread {

    private JLabel lbAdministrador;
    private String txtAdministrador = "Administrador";
    private String txtVersaoWindows = "Windows 7";
    private JLabel lbAdobeReader;
    private JLabel lbAvast;
    private JLabel lbdotNet;
    private JLabel lbDropbox;
    private JLabel lbChrome;
    private JLabel lbKlite;
    private JLabel lbOffice;
    private JLabel lbWinrar;
    private JButton btnEnergia;
    private JButton btnUsuario;
    private JButton btnPorta;
    private JButton btnPostgres;
    private JButton btnWinUpdate;
    private JButton btnRelogio;
    private JLabel lbAtiveAdmin;
    private JLabel lbAthosPostgres;
    private JLabel lbAthosInstalado;
    private JLabel lbStatosPostgres;
    private JLabel lbStatosDNS;
    private JLabel lbStatosDHCP;
    private JLabel lbStatosFirewall;
    private JLabel lbStatosSpooler;
    private JLabel lbStatosWindowsUpdate;
    

    public Agendador(JLabel lbAdministrador, JLabel lbAdobeReader, JLabel lbAvast, JLabel lbdotNet, JLabel lbDropbox, JLabel lbChrome, JLabel lbKlite, JLabel lbOffice, JLabel lbWinrar,JButton btnEnergia,JButton btnUsuario,JButton btnPorta,JButton btnPostgres,JLabel lbAtiveAdmin,JLabel lbAthosPostgres,JLabel lbAthosInstalado,JLabel lbStatosPostgres, JLabel lbStatosDNS,JLabel lbStatosDHCP,JLabel lbStatosFirewall,JLabel lbStatosSpooler,JLabel lbStatosWindowsUpdate,JButton btnWinUpdate,JButton btnRelogio) {
        this.lbAdobeReader = lbAdobeReader;
        this.lbAvast = lbAvast;
        this.lbdotNet = lbdotNet;
        this.lbDropbox = lbDropbox;
        this.lbChrome = lbChrome;
        this.lbKlite = lbKlite;
        this.lbOffice = lbOffice;
        this.lbAdministrador = lbAdministrador;    
        this.lbWinrar = lbWinrar;    
        this.btnEnergia = btnEnergia;
        this.btnUsuario = btnUsuario;
        this.btnPorta = btnPorta;
        this.btnPostgres = btnPostgres;
        this.btnWinUpdate = btnWinUpdate;
        this.btnRelogio = btnRelogio;
        this.lbAtiveAdmin = lbAtiveAdmin;
        this.lbAthosPostgres = lbAthosPostgres;
        this.lbAthosInstalado = lbAthosInstalado;
        this.lbStatosPostgres = lbStatosPostgres;
        this.lbStatosDNS = lbStatosDNS;
        this.lbStatosDHCP = lbStatosDHCP;
        this.lbStatosFirewall = lbStatosFirewall;
        this.lbStatosSpooler = lbStatosSpooler;
        this.lbStatosWindowsUpdate = lbStatosWindowsUpdate;
 //       this.img = img;
    }

    @Override
    public void run() {
   //     Funcoes.trocaLight(lbModelo, img);
        
        while (true) {
            Funcoes.verificaDuplo(lbWinrar, "WinRAR");
            Funcoes.verificaDuplo(lbChrome, "Google\\Chrome");
            Funcoes.verificaDuplo(lbKlite, "K-Lite Codec Pack");
            Funcoes.verificaDuplo(lbOffice, "Microsoft Office");
            Funcoes.verificaDuplo(lbAvast, "AVAST Software");
            Funcoes.verificaDuplo(lbAdobeReader, "Adobe");
            Funcoes.verificaDuplo(lbAthosPostgres, "PostgreSQL");
            Funcoes.verificaUnico(lbAthosPostgres, "C:\\PostgreSQL");
            Funcoes.verificaUnico(lbAthosInstalado, "C:\\Athos");
            Funcoes.verificaUnico(lbDropbox, "C:\\Users\\Administrador\\Dropbox");
            Funcoes.verificaUnico(lbDropbox, "D:\\Dropbox");
            Funcoes.trocaAdministrador(lbAdministrador, Funcoes.testaAdministrador(txtAdministrador));
            Funcoes.trocaQuery(btnPorta, btnEnergia, btnUsuario,btnPostgres,btnWinUpdate,btnRelogio, Funcoes.testaAdministrador(txtAdministrador));
            Funcoes.ativeAdmin(lbAtiveAdmin, Funcoes.testaAdministrador(txtAdministrador));
            
            try {
                //Funcoes.dotNet(lbdotNet);
                Funcoes.statusServico(lbStatosPostgres, "postgresql-9.0");
                Funcoes.statusServico(lbStatosDNS, "Dnscache");
                Funcoes.statusServico(lbStatosDHCP, "Dhcp");
                Funcoes.statusServico(lbStatosFirewall, "MpsSvc");
                Funcoes.statusServico(lbStatosSpooler, "Spooler");
                Funcoes.statusServico(lbStatosWindowsUpdate, "wuauserv");
            } catch (IOException ex) {
                Logger.getLogger(Agendador.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Agendador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

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
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 *
 * @author Administrador
 */
public class Inicializador extends Thread{

    
    private JCheckBox jCheckBoxAthosBackup;
    private JCheckBox jCheckBoxCapicom;
    private JCheckBox jCheckBoxDesempenho;
    private JCheckBox jCheckBoxInternet;
    private JCheckBox jCheckBoxRede;
    private JCheckBox jCheckBoxSite;
    private JCheckBox jCheckBoxWallpaper;
    private JCheckBox jCheckBoxWindowsUpdate;
    private JLabel lbLightGreen;
    private JLabel lbLightRed;
    private JLabel lbLightYellow;
    private Image img;
    private JLabel lbVersaoWindows;
    private JLabel lbModelo;
    private JLabel lbProcessador;
    private JLabel lbMemoria;
    private JLabel lbTipoIP;
    private JLabel lbIP;
    

    public Inicializador(JCheckBox jCheckBoxAthosBackup, JCheckBox jCheckBoxCapicom, JCheckBox jCheckBoxDesempenho, JCheckBox jCheckBoxInternet, JCheckBox jCheckBoxRede, JCheckBox jCheckBoxSite, JCheckBox jCheckBoxWallpaper, JCheckBox jCheckBoxWindowsUpdate, JLabel lbVersaoWindows, JLabel lbModelo,JLabel lbProcessaodr,JLabel lbMemoria,JLabel lbTipoIP,JLabel lbIP) {
        this.jCheckBoxAthosBackup = jCheckBoxAthosBackup;
        this.jCheckBoxCapicom = jCheckBoxCapicom;
        this.jCheckBoxDesempenho = jCheckBoxDesempenho;
        this.jCheckBoxInternet = jCheckBoxInternet;
        this.jCheckBoxRede = jCheckBoxRede;
        this.jCheckBoxSite = jCheckBoxSite;
        this.jCheckBoxWallpaper = jCheckBoxWallpaper;
        this.jCheckBoxWindowsUpdate = jCheckBoxWindowsUpdate;
        this.lbLightGreen=lbLightGreen;
        this.lbLightRed = lbLightRed;
        this.lbLightYellow = lbLightYellow;
        this.img = img;
        this.lbVersaoWindows = lbVersaoWindows;
        this.lbModelo = lbModelo;
        this.lbProcessador = lbProcessador;
        this.lbMemoria = lbMemoria;
        this.lbTipoIP = lbTipoIP;
        this.lbIP = lbIP;
    }
    @Override
    public void run() { 
        //Funcoes.trocaLight(lbLightGreen, img);
        
        try {
            Funcoes.setModelo(lbModelo);
        } catch (IOException ex) {
            Logger.getLogger(Agendador.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            System.out.println("chegou aqui");
            Funcoes.testaSystemInfo(lbVersaoWindows,lbMemoria,lbTipoIP,lbIP);
        } catch (IOException ex) {
            Logger.getLogger(Agendador.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            
            Funcoes.iniciaChecklist(jCheckBoxAthosBackup,jCheckBoxCapicom,jCheckBoxDesempenho,jCheckBoxInternet,jCheckBoxRede,jCheckBoxSite,jCheckBoxWallpaper,jCheckBoxWindowsUpdate);
        } catch (IOException ex) {
            Logger.getLogger(Inicializador.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            Funcoes.habilitaChecklist(jCheckBoxAthosBackup,jCheckBoxCapicom,jCheckBoxDesempenho,jCheckBoxInternet,jCheckBoxRede,jCheckBoxSite,jCheckBoxWallpaper,jCheckBoxWindowsUpdate,Funcoes.testaAdministrador("Administrador"));
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Agendador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}

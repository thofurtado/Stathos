/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package status;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author a1700596
 */
public class Funcoes {

    private static JFrame frame;
    static void dotNet(JLabel lbdotNet) throws IOException{
        Process comando = Runtime.getRuntime().exec("wmic product get name,version");
        BufferedReader br = new BufferedReader(new InputStreamReader(comando.getInputStream()));
        String temp1;
        while ((temp1 = br.readLine()) != null) {
            if (!temp1.trim().isEmpty() && !temp1.contains("Microsoft .NET Framework")) {
                lbdotNet.setText("Sim"+" : "+temp1.subSequence(temp1.indexOf("Framework")+10,temp1.indexOf("Framework")+15));
            }
        }
    }
    static void trocaAdministrador(JLabel lbTexto, boolean b) {
        if (b) {
            lbTexto.setForeground(Color.decode("#27992A"));
            lbTexto.setText("Sim");
        } else {
            lbTexto.setForeground(Color.red);
            lbTexto.setText("Não");
        }
    }

    static void trocaLight(JLabel imagem, Image img) {
        imagem.setPreferredSize(new Dimension(35, 35));
        imagem.setIcon(new ImageIcon(img.getScaledInstance(35, 35, WIDTH)));
    }

    static void trocaQuery(JButton btnPorta, JButton btnEnergia, JButton btnUsuario, JButton btnPostgres,JButton btnWinUpdate,JButton btnRelogio,boolean b) {
        btnPorta.setEnabled(b);
        btnEnergia.setEnabled(b);
        btnUsuario.setEnabled(b);
        btnPostgres.setEnabled(b);
        btnWinUpdate.setEnabled(b);
        btnRelogio.setEnabled(b);
    }

    static void ativeAdmin(JLabel lbAtiveAdmin, boolean testaAdministrador) {
        lbAtiveAdmin.setEnabled(!testaAdministrador);
    }

    static void iniciaChecklist(JCheckBox jCheckBoxAthosBackup, JCheckBox jCheckBoxCapicom, JCheckBox jCheckBoxDesempenho, JCheckBox jCheckBoxInternet, JCheckBox jCheckBoxRede, JCheckBox jCheckBoxSite, JCheckBox jCheckBoxWallpaper, JCheckBox jCheckBoxWindowsUpdate) throws IOException {
        ArrayList<String> lista = getChecklist();
        ArrayList<String> listaNome = new ArrayList<String>();
        ArrayList<String> listaValor = new ArrayList<String>();
        int i;
        if (lista.size() > 1) {
            for (String nome : lista) {
                i = nome.toLowerCase().indexOf("=");
                listaNome.add((String) nome.subSequence(0, i));
                listaValor.add((String) nome.substring(i + 1));

            }
            for (int j = 0; j < listaNome.size(); j++) {

                if (listaNome.get(j).equals("athosbackup")) {
                    boolean escolha = false;
                    if (listaValor.get(j).equals("1")) {
                        escolha = true;
                    }
                    jCheckBoxAthosBackup.setSelected(escolha);
                }
                if (listaNome.get(j).equals("capicom")) {
                    boolean escolha = false;
                    if (listaValor.get(j).equals("1")) {
                        escolha = true;
                    }
                    jCheckBoxCapicom.setSelected(escolha);
                }
                if (listaNome.get(j).equals("perfilderede")) {
                    boolean escolha = false;
                    if (listaValor.get(j).equals("1")) {
                        escolha = true;
                    }
                    jCheckBoxRede.setSelected(escolha);
                }
                if (listaNome.get(j).equals("desempenho")) {
                    boolean escolha = false;
                    if (listaValor.get(j).contains("1")) {
                        escolha = true;
                    }
                    jCheckBoxDesempenho.setSelected(escolha);
                }
                if (listaNome.get(j).equals("opcoesdainternet")) {
                    boolean escolha = false;
                    if (listaValor.get(j).equals("1")) {
                        escolha = true;
                    }
                    jCheckBoxInternet.setSelected(escolha);
                }
                if (listaNome.get(j).equals("site")) {
                    boolean escolha = false;
                    if (listaValor.get(j).equals("1")) {
                        escolha = true;
                    }
                    jCheckBoxSite.setSelected(escolha);
                }
                if (listaNome.get(j).equals("wallpaper")) {
                    boolean escolha = false;
                    if (listaValor.get(j).equals("1")) {
                        escolha = true;
                    }
                    jCheckBoxWallpaper.setSelected(escolha);
                }
                if (listaNome.get(j).equals("windowsupdate")) {
                    boolean escolha = false;
                    if (listaValor.get(j).equals("1")) {
                        escolha = true;
                    }
                    jCheckBoxWindowsUpdate.setSelected(escolha);
                }
            }
        } else {
            Funcoes.alerta("Inicializar", "Arquivo de configuração não encontrado, Reset Padrão");
        }
    }

    public static void setChecklist(String nome) throws IOException {
        ArrayList<String> lista = getChecklist();
        String linhaAtual = nome + "=0";
        String arquivo = "";
        for (String linha : lista) {
            linhaAtual = linha;
            if (linha.contains(nome)) {
                if (linha.contains("0")) {
                    linhaAtual = linha.subSequence(0, linha.length() - 1) + "1";
                } else {
                    linhaAtual = linha.subSequence(0, linha.length() - 1) + "0";
                }
            }
            arquivo += linhaAtual + System.getProperty("line.separator");

        }
        PrintWriter pw = new PrintWriter("C:\\Stathos\\checklist.conf");
        pw.write(arquivo);
        pw.close();
    }

    public static ArrayList<String> getChecklist() throws IOException {
        String nomeArquivo = null;

        String nomeArquivoCompleto = "C:\\Stathos\\checklist.conf";

        nomeArquivo = nomeArquivoCompleto;

        FileInputStream fin = null;
        BufferedReader in = null;
        String linha = null;
        int qtddLinhas = 0;
        ArrayList<String> lista = new ArrayList<String>();

        String[] contatos = new String[qtddLinhas];

        fin = new FileInputStream(nomeArquivo);

        in = new BufferedReader(new InputStreamReader(fin));

        // IOException
        while (in.ready()) {
            // IOException
            linha = in.readLine();

            if (linha == null) {
                break;
            }

            linha = linha.trim();

            if (linha.length() != 0) {
                lista.add(linha);
                qtddLinhas++;

            }
        }
        // IOException
        fin.close();
        // IOException
        in.close();
        //return lista;
        return lista;
    }

    static void habilitaChecklist(JCheckBox jCheckBoxAthosBackup, JCheckBox jCheckBoxCapicom, JCheckBox jCheckBoxDesempenho, JCheckBox jCheckBoxInternet, JCheckBox jCheckBoxRede, JCheckBox jCheckBoxSite, JCheckBox jCheckBoxWallpaper, JCheckBox jCheckBoxWindowsUpdate, boolean b) {
        jCheckBoxAthosBackup.setEnabled(b);
        jCheckBoxCapicom.setEnabled(b);
        jCheckBoxDesempenho.setEnabled(b);
        jCheckBoxInternet.setEnabled(b);
        jCheckBoxRede.setEnabled(b);
        jCheckBoxSite.setEnabled(b);
        jCheckBoxWallpaper.setEnabled(b);
        jCheckBoxWindowsUpdate.setEnabled(b);
    }

    static void importEnergia() throws IOException {
        //POWERCFG -IMPORT "C:\Temp\teste.pow"
        Process comando = Runtime.getRuntime().exec("powercfg -import C:\\Stathos\\teste.pow "+consultaEnergia());
        while (comando.isAlive()) {
        }
        comando = Runtime.getRuntime().exec("powercfg -import C:\\Stathos\\teste.pow ");
        while(comando.isAlive()){
        }
    }

    private String usuario_sessao;
    private String versao_windows;
    private static File athos = new File("C://Athos//Athos.exe");

    public static boolean testaAdministrador(String txtUsuario) {

        String usuario_sessao = System.getProperty("user.name");
        
        if (usuario_sessao.equals(txtUsuario)) {

            return true;
        }

        return false;
    }

    public static void trocarUsuario() throws IOException {
        Process comando = Runtime.getRuntime().exec("control userpasswords2");
    }

    public static void opcoesDesempenho() throws IOException {
        Process comando = Runtime.getRuntime().exec("sysdm.cpl");
        while (comando.isAlive()) {
        }
    }

    public static void alerta(String titulo, String texto) {
        JOptionPane.showConfirmDialog(
                null, texto,
                titulo,
                JOptionPane.CLOSED_OPTION);
    }

    public static void instalar() throws IOException {
        int n = JOptionPane.showConfirmDialog(
                null, "Deseja instalar o programa?",
                "Instalação",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            Funcoes.instalarPrograma();
        } else if (n == JOptionPane.NO_OPTION) {

        } else {

        }
    }

    public static void instalarPrograma() throws IOException {
        File pasta = new File("C:\\Status");
        pasta.mkdir();
        Process comando = Runtime.getRuntime().exec("xcopy * c:\\Status /E");
        while (comando.isAlive()) {
        }
        //Process comando2 = Runtime.getRuntime().exec("xcopy \"c:\\Status\\dist\\programa.bat - Atalho.lnk\" \"C:\\Users\\Administrador\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\"");
        Funcoes.alerta("Instalação", "Programa Instalado com Sucesso");
        //Funcoes.adicionarInicializar();

    }

    public static void adicionarInicializar() throws IOException {
        Process comando1 = Runtime.getRuntime().exec("xcopy \"c:\\Status\\dist\\Programa.bat.lnk\" \"C:\\Users\\Administrador\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\"");
        while (comando1.isAlive()) {
        }
        Funcoes.alerta("Instalação", "Atalho gerado com sucesso");
    }

    public static void setAdministrador() throws IOException {
        Process comando = Runtime.getRuntime().exec("net user administrator /active:yes \n");
        while (comando.isAlive()) {
        }
        Process comando2 = Runtime.getRuntime().exec("logoff");
        while (comando2.isAlive()) {
        }
    }

    public static void restartPostgres() throws IOException {
        Process comando = Runtime.getRuntime().exec("net stop postgresql-9.0");
        while (comando.isAlive()) {
        }
        comando = Runtime.getRuntime().exec("net start postgresql-9.0");
        while (comando.isAlive()) {
        }
    }

    public static void testaSystemInfo(JLabel lbVersaoWindows,JLabel lbMemoria, JLabel lbTipoIP, JLabel lbIP) throws IOException {
        
        Process comando = Runtime.getRuntime().exec("systeminfo");
        BufferedReader br = new BufferedReader(new InputStreamReader(comando.getInputStream()));
        String temp1;
        while ((temp1 = br.readLine()) != null) {
            if (temp1.contains("Nome do sistema operacional")) {
                lbVersaoWindows.setText(temp1.substring(temp1.indexOf("Windows")+8));
            }
            if (temp1.contains("sica total")) {
                lbMemoria.setText(temp1.substring(temp1.indexOf("MB")-6));
            }
            if (temp1.contains("DHCP ativado:")) {
                if(temp1.contains("Sim")){
                    lbTipoIP.setText("DHCP");
                    lbTipoIP.setForeground(Color.red);
                }else{
                    lbTipoIP.setText("FIXO");
                    lbTipoIP.setForeground(Color.decode("#27992A"));
                }
            }
            if (temp1.contains("[01]:") && temp1.contains(".")) {
                lbIP.setText(temp1.substring(temp1.indexOf("[01]:")+6));
            }
        }
    }
    
    public static void statusServico(JLabel lbTexto, String servico) throws IOException {
        Process comando = Runtime.getRuntime().exec("sc query " + servico);
        BufferedReader br = new BufferedReader(new InputStreamReader(comando.getInputStream()));
        String temp1;
        while ((temp1 = br.readLine()) != null) {
            if (temp1.contains("RUNNING")) {
                System.out.println("Rodando");
                lbTexto.setText("Iniciado");
                lbTexto.setForeground(Color.decode("#27992A"));
            } else if (temp1.contains("STOPPED")) {
                lbTexto.setText("Parado");
                lbTexto.setForeground(Color.red);
            }
        }
    }

    public static void restartFirewall() throws IOException {
        Process comando1 = Runtime.getRuntime().exec("netsh advfirewall set allprofiles state off");
        while (comando1.isAlive()) {
        }
        comando1 = Runtime.getRuntime().exec("netsh advfirewall set allprofiles state on");
    }

    public static void liberaPorta() throws IOException {
        Process comando1 = Runtime.getRuntime().exec("netsh advfirewall firewall add rule name=\"Postgres Entrada TCP\" dir=in action=allow protocol=TCP localport=5432");
        while (comando1.isAlive()) {
        }
        comando1 = Runtime.getRuntime().exec("netsh advfirewall firewall add rule name=\"Postgres Entrada UDP\" dir=in action=allow protocol=UDP localport=5432");
        while (comando1.isAlive()) {
        }
        comando1 = Runtime.getRuntime().exec("netsh advfirewall firewall add rule name=\"Postgres Saida TCP\" dir=out action=allow protocol=TCP localport=5432");
        while (comando1.isAlive()) {
        }
        comando1 = Runtime.getRuntime().exec("netsh advfirewall firewall add rule name=\"Postgres Saida UDP\" dir=out action=allow protocol=UDP localport=5432");
        while (comando1.isAlive()) {
        }
    }

    public static void progressoPing(JProgressBar jProgressBarPing, boolean boleano) {
        jProgressBarPing.setIndeterminate(boleano);
    }

    public static void setPing(JLabel ping, String linha, String procurada, String procurada2) {
        int pos1 = linha.indexOf(procurada) + procurada.length() + 1;
        int pos2 = linha.indexOf(procurada2);

        if (pos1 != procurada.length()) {
            String temp2 = linha.substring(pos1, pos2);
            ping.setText(temp2);
        }
    }

    public static String consultaEnergia() throws IOException {
        String codigo = null;
        Process comando = Runtime.getRuntime().exec("powercfg /list");
        BufferedReader br = new BufferedReader(new InputStreamReader(comando.getInputStream()));
        String temp1;
        while ((temp1 = br.readLine()) != null) {
            int pos1 = temp1.indexOf("(Alto desempenho)") + "(Alto desempenho)".length() + 1;
            int pos2 = temp1.indexOf("Energia: ") + "Energia: ".length();
            if (pos1 > pos2 + 30) {
                codigo = temp1.substring(pos2, pos1 - "  (Alto desempenho) *".length() + 1);
            }
        }
        return codigo;
    }

    public static void setModelo(JLabel lbModelo) throws IOException {
        Process comando = Runtime.getRuntime().exec("wmic baseboard get product");
        BufferedReader br = new BufferedReader(new InputStreamReader(comando.getInputStream()));
        String temp1;
        while ((temp1 = br.readLine()) != null) {
            if (!temp1.trim().isEmpty() && !temp1.contains("Product")) {
                lbModelo.setText(temp1);
            }
        }
    }

    public static void setVersaoAthos(JLabel lbVersaoAthos) {

    }

    public static void setEnergia(String chave) throws IOException {
        Process comando = Runtime.getRuntime().exec("powercfg /setactive " + chave);
    }

    public static void ping(JLabel jLabelRecebidos, JLabel jLabelEnviados, JLabel jLabelMedia) throws IOException {
        Process comando = Runtime.getRuntime().exec("ping www.google.com.br");
        BufferedReader br = new BufferedReader(new InputStreamReader(comando.getInputStream()));
        String temp1;
        while ((temp1 = br.readLine()) != null) {
            Funcoes.setPing(jLabelRecebidos, temp1, "Recebidos =", ", Per");
            Funcoes.setPing(jLabelEnviados, temp1, "Enviados =", ", Rec");
            if (temp1.indexOf("dia =") != -1) {
                Funcoes.setPing(jLabelMedia, temp1.substring(temp1.indexOf("dia =")), "dia =", "ms");
                jLabelMedia.setText(jLabelMedia.getText()+"ms");
            }
        }
    }

    public static void velocidadeInternet(JSpinner tamanho, JLabel tempo, JLabel velocidade) throws MalformedURLException, IOException {
        URL url = null;
        double tamanhoArquivo=0;
        switch (tamanho.getValue().toString()) {
            case "10":
                url = new URL("http://www.univox.com.br/testedownload/10MB.tst");
                tamanhoArquivo =10287;
                break;
            case "30":
                url = new URL("http://www.univox.com.br/testedownload/30MB.tst");
                tamanhoArquivo =30861;
                break;
            case "50":
                url = new URL("http://www.univox.com.br/testedownload/50MB.tst");
                tamanhoArquivo =51435;
                break;
            case "100":
                url = new URL("http://www.univox.com.br/testedownload/100MB.tst");
                tamanhoArquivo =102870;
                break;
            case "200":
                url = new URL("http://www.univox.com.br/testedownload/200MB.tst");
                tamanhoArquivo =204975;
                break;
        }
        File file = new File("C:\\Stathos\\arquivo-baixado.tst");
        long tempInicial = System.currentTimeMillis();
        FileUtils.copyURLToFile(url, file);
        double tempFinal = System.currentTimeMillis();
        double dif = (tempFinal - tempInicial);
        double teste = (tamanhoArquivo)/(dif/1000);
        int resultadoVelocidade = (int) teste;
        String resultadoTempo = Double.toString((int)dif / 1000);
        tempo.setText( resultadoTempo + "s");
        velocidade.setText(resultadoVelocidade+ "kbps");
        FileUtils.deleteQuietly(file);
    }

    public static boolean verificarPasta(String caminho) {
        boolean encontrou = false;
        File diretorio = new File(caminho);
        if (diretorio.exists()) {
            encontrou = true;
        }
        return encontrou;
    }

    public static void verificaDuplo(JLabel label, String complemento) {
        if (Funcoes.verificarPasta("C:\\Program Files\\" + complemento) || Funcoes.verificarPasta("C:\\Program Files (x86)\\" + complemento)) {
            label.setText("Sim");
            label.setForeground(Color.decode("#27992A"));
        }
    }

    public static void verificaUnico(JLabel label, String complemento) {
        if (Funcoes.verificarPasta(complemento)) {
            label.setText("Sim");
            label.setForeground(Color.decode("#27992A"));
        }
    }

}

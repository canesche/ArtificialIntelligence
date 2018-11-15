/*
 * Configuracao.java
 *
 * Criada em 6 de Abril de 2007, 06:45
 *
 * Esta classe mantem a configura&ccedil;&atilde;o do jogo
 */
package jogo;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author Alcione de Paiva
 * @version 1.0
 */
public final class Configuracao {

    private int DIM = 13;
    private String nome = null;
    private String[] pecas = null;
    private int porta = 1962;
    private int timeout = 6000;
    private int espera = 0;
    private String host = null;
    static private Configuracao instancia = null;

    static synchronized public Configuracao getInstance() {
        if (instancia == null) {
            instancia = new Configuracao();
        }
        return instancia;
    }

    private Configuracao() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("jogo.properties"));
        } catch (Exception e) {
            System.out.println("Arquivo properties do jogo nao encontrado");
            return;
        }

        DIM = Integer.parseInt(props.getProperty("DIM"));
        nome = props.getProperty("nome");
        porta = Integer.parseInt(props.getProperty("porta"));
        int npecas = Integer.parseInt(props.getProperty("npecas"));
        timeout = Integer.parseInt(props.getProperty("timeout"));
        espera = Integer.parseInt(props.getProperty("espera"));
        pecas = new String[npecas];

        for (int i = 0; i < npecas; i++) {
            pecas[i] = props.getProperty("" + i);
        }


    }

    public String getNome() {
        return nome;
    }

    /**
     * Retorna a dimens&atilde;o do tabuleiro
     *
     * @return retorna a dimens&atilde;o.
     */
    public int getDim() {
        return DIM;
    }

    public String[] getPecas() {
        return pecas;
    }

    public int getPorta() {
        return porta;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getHost() {
        return host;
    }

    /**
     * @return the espera
     */
    public int getEspera() {
        return espera;
    }

    /**
     * @param espera the espera to set
     */
    public void setEspera(int espera) {
        this.espera = espera;
    }
}

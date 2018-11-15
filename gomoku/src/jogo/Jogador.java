package jogo;

/*
 * Jogador.java
 *
 * Created on 19 de Mar�o de 2001, 08:28
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import tabuleiro.*;

/**
 * Esta Classe implementa o esqueleto de um jogador para jogar um jogo de tabuleiro distribu&iacute;do.
 * Trata-se de uma classe abstrata e, portanto, &eacute; preciso criar uma subclasse e implementar o m&eacute;todo 
 * <b>calculaJogada</b> para que possa ser instanciada.
 * Ele se conecta no servidor do jogo  no host passado pela linha de argumentos e
 * na porta fornecida pela pelo singleton <b>Configuracao</b>.
 * Passa ent&atilde;o a receber as jogadas do oponente e enviar jogadas por meio do servidor
 * segundo um protocolo pre-definido. <br><br>
 * <b>Execu&ccedil;&atilde;o</b><br>
 * <center><i>java Jogador &lt;nome> &lt;host></i></center> <br><br>
 * <b>Exemplo:</b> <br>
 * <center><i>java Jogador equipe1 localhost</i></center> <br><br>
 * <b>Protocolo</b><br>
 * A cada rodada o jogador recebe uma jogada e envia uma jogada. <br>
 * A jogada recebida possui o seguinte formato:<br>
 * <center><i>&lt;jogador>\n&lt;linha>\n&lt;coluna>\n&lt;linhaInicial>\n&lt;colunaInicial>\n</i></center> <br>
 * Onde:
 * <ul>
 * <li>&lt;jogador>= indica qual &eacute; a cor do jogador (Tabuleiro.AZUL ou Tabuleiro.VERM) ou
 * '#' indicando fim do jogo.
 * <li> &lt;linha>&lt;coluna> = s&atilde;o as coordenadas da posi&ccedil;&atilde;o recem ocupada.
 * <li> &lt;linhaInicial>&lt;colunaInicial> = s&atilde;o as coordenadas da pe&ccedil;a respons&aacute;vel pela jogada .
 * </ul> <br><p>
 * A jogada enviada possui o seguinte formato:<br>
 * <center><i>&lt;linha>\n&lt;coluna>\n&lt;linhaInicial>\n&lt;colunaInicial>\n</i></center> <br>
 * Se o jogador precisar passar a jogada deve atribuir valor -1 as coordenadas.
 * <br>
 * Caso o jogador tenha algum problema ou desista deve enviar o caracter #
 * <br>
 * <br>
 * 
 * 
 * @author Alcione
 * @version 1.0
 */
public abstract class Jogador implements JogadorInt {

    protected String host;
    protected String nome;
    protected Tabuleiro tabuleiro;
    protected int tempoEspera = 1000;
    protected FactoryTabuleiro factoryTabuleiro = null;
    byte jogador = 0;

    /**
     * @param args - args[0] nome do jogador; args[1] enderea&ccedil;o ip ou nome da m&aacute;quina onde est&aacute; o servidor 
     */
    public Jogador(String args[]) {

        host = Configuracao.getInstance().getHost();
        if (args.length == 0) {
            nome = "jogador" + new Double((Math.random() * 100)).shortValue();
        } else if (args.length > 0) {
            nome = args[0];
        }
        if (args.length > 1) {
            host = args[1];
        }
        tabuleiro = getFactoryTabuleiro().createTabuleiro();
        tabuleiro.inicia();
    }

    /**
     * M&eacute;todo que inicia o jogo.
     */
    @Override
    public void joga() {
        BufferedWriter soutput = null;
        BufferedReader cinput = null;
        Socket clisoc = null;
        System.out.println("Iniciando...." + nome);
        try {
            if (clisoc != null) {
                clisoc.close();
            }
            clisoc = new Socket(host, Configuracao.getInstance().getPorta());
            soutput = new BufferedWriter(new OutputStreamWriter(clisoc.getOutputStream()));
            cinput = new BufferedReader(new InputStreamReader(clisoc.getInputStream()));

            System.out.println("enviando...." + nome);
            //cinput.readLine();
            soutput.write(nome + "\n");
            soutput.flush();

            String linhaLida = cinput.readLine();
            System.out.println("Linha lida:" + linhaLida);

            jogador = (byte) Integer.parseInt(linhaLida);
            linhaLida = cinput.readLine();
            int linha = Integer.parseInt(linhaLida);
            linhaLida = cinput.readLine();
            int coluna = Integer.parseInt(linhaLida);

            System.out.println("Linha lida" + jogador + linha + coluna);
            byte oponente = jogador == Tabuleiro.AZUL ? Tabuleiro.VERM : Tabuleiro.AZUL;
            String nJogador = tabuleiro.cor[jogador];
            String nOponente = tabuleiro.cor[(jogador + 1) % 2];
            for (;;) {
                if (linha != -1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Oponente: ").append(nOponente).append(" jogada ").
                            append(linha).append("-").append(coluna);
                    System.out.println(sb.toString());
                    if (!tabuleiro.move(oponente, new Jogada(-1, -1, linha, coluna))) {
                        System.out.println("Jogada Invalida!!");
                    }
                }
                try {
                    // A linha abaixo deve ser retirada quando for implementado o jogador
                    // Thread.sleep(getTempoEspera());
                } catch (Exception e) {
                }
                System.out.println("Vou calcular jogada!");
                Jogada jog = calculaJogada(tabuleiro, jogador);
                if (jog == null) {
                    System.out.println("Jogada Nula!");
                } else {
                    System.out.println("Jogada Boa!");
                }
                if (jog != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Eu: ").append(nJogador).append(" jogada ").
                            append(jog.getLinha()).append("-").append(jog.getColuna());

                    System.out.println(sb.toString());

                    tabuleiro.move(jogador, jog);
                    soutput.write("" + jog.getLinha() + "\n" + jog.getColuna() + "\n");
                    System.out.println("enviando:" + jog.getLinha() + "\n" + jog.getColuna()
                            + "\n");
                } else {
                    soutput.write("-1\n-1\n");
                }
                soutput.flush();
                linhaLida = cinput.readLine();
                if (linhaLida.charAt(0) == '#') {
                    System.out.println("#");
                    return;
                }

                linhaLida = cinput.readLine();
                linha = Integer.parseInt(linhaLida);
                linhaLida = cinput.readLine();
                coluna = Integer.parseInt(linhaLida);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Jogador.class.getName()).log(Level.SEVERE, null, e.getMessage());
            try {
                soutput.write("#\n");
                soutput.flush();
            } catch (IOException ex) {
                Logger.getLogger(Jogador.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            }
        } finally {
            try {
                System.out.println("Fim");
                clisoc.close();
            } catch (IOException ex) {
                Logger.getLogger(Jogador.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            }
        }
    }

    protected int getTempoEspera() {
        return tempoEspera;
    }

    protected void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    protected final FactoryTabuleiro getFactoryTabuleiro() {
        if (factoryTabuleiro == null) {
            setFactoryTabuleiro(new FactoryTabuleiroImp());
        }
        return factoryTabuleiro;
    }

    protected void setFactoryTabuleiro(FactoryTabuleiro factoryTabuleiro) {
        this.factoryTabuleiro = factoryTabuleiro;
    }

    /**
     * Retorna o oponente.
     * @return retorna o oponente.
     */
    @Override
    public byte oponente() {
        return jogador == Tabuleiro.AZUL ? Tabuleiro.VERM : Tabuleiro.AZUL;
    }
}

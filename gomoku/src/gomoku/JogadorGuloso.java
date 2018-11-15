package gomoku;

/*
 * Jogador.java
 *
 * Created on 19 de Mar�o de 2001, 08:28
 */
import jogo.Jogador;
import tabuleiro.Jogada;
import tabuleiro.Tabuleiro;

/**
 * Esta Classe implementa o esqueleto de um jogador guloso.
 * <br>
 * Ele se conecta no servidor do jogo  no host passado pela linha de argumentos e
 * na porta fornecida pela classe Servidor.
 * Passa ent&atilde;o a receber as jogadas do oponente e enviar jogadas por meio do servidor
 * segundo um protocolo pr&eacute;-definido. <br><br>
 * <b>Execu&ccedil;&atilde;o</b><br>
 * <center><i>java Jogador &lt;nome> &lt;host></i></center> <br><br>
 * <b>Exemplo:</b> <br>
 * <center><i>java Jogador equipe1 localhost</i></center> <br><br>
 * <b>Protocolo</b><br>
 * A cada rodada o jogador recebe uma jogada e envia uma jogada. <br>
 * A jogada recebida possui o seguinte formato:<br>
 * <center><i>&lt;jogador>\n&lt;x>\n&lt;y>\n&lt;xp>\n&lt;yp>\n</i></center> <br>
 * Onde:
 * <ul>
 * <li>&lt;jogador>= indica qual &eacute; a cor do jogador (Tabuleiro.AZUL ou Tabuleiro.VERM) ou
 * '#' indicando fim do jogo.
 * <li> &lt;x>&lt;y> = s&atilde;o as coordenadas da posi&ccedil;&atilde;o recem ocupada (0 a 7).
 * <li> &lt;xp>&lt;yp> = s&atilde;o as coordenadas da pe&ccedil;a respons&aacute;vel pela jogada (0 a 7).
 * </ul> <br><p>
 * A jogada enviada possui o seguinte formato:<br>
 * <center><i>&lt;x>\n&lt;y>\n&lt;xp>\n&lt;yp>\n</i></center> <br>
 * Se o jogador precisar passar a jogada deve atribuir valor -1 as coordenadas.
 * <br>
 * Caso o jogador tenha algum problema ou desista deve enviar o caracter #
 * <br>
 *
 * @author Alcione
 * @version 1.0
 */
public class JogadorGuloso extends Jogador {

    /**
     * @param args - args[0] nome do jogador; args[1] endere&ccedil;o ip ou nome da m&aacute;quina onde est&aacute; o servidor
     */
    public JogadorGuloso(String args[]) {
        super(args);
    }

    /**
     * Calcula uma nova jogada para o tabuleiro e jogador corrente.
     * Aqui deve ser colocado o algoritmo com as t&eacute;cnicas de inteligencia
     * artificial. No momento as jogadas s&atilde;o calculadas apenas por crit&eacute;rio de
     * validade. Coloque aqui seu algoritmo minmax.
     *
     *
     * @param tab Tabuleiro corrente
     * @param jogadorCor Jogador corrente
     * @return retorna a jogada calculada.
     */
    public Jogada calculaJogada(Tabuleiro tab, byte jogadorCor) {
       //return tab.obtemJogadaBoa(jogadorCor);
        return tab.obtemJogadaHeuristica(jogadorCor);
    }

    /**
     * @param args argumentos da linha de comando: host do servidor
     */
    public static void main(String args[]) {
        if (args.length < 2) {
            String a[] = new String[2];
            a[0] = "jogador" + new Double((Math.random() * 100)).shortValue();
            a[1] = "localhost";
            (new JogadorGuloso(a)).joga();
        } else {
            (new JogadorGuloso(args)).joga();
        }
        System.out.println("fim!");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * java -cp .:../../dist/lib/jogosTabuleiro.jar jogo.ServidorImp &
 */
package jogo;

import gomoku.TabuleiroGoMoku;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import tabuleiro.FactoryTabuleiro;
import tabuleiro.FactoryTabuleiroImp;
import tabuleiro.Jogada;
import tabuleiro.Tabuleiro;

/**
 *
 * @author alcione
 */
public class ServidorImp implements Servidor {

    List<JogoObservador> lo = new ArrayList<JogoObservador>();
    private final int TIMEOUT = Configuracao.getInstance().getTimeout();
    private Jogador jogadorAzul = null;
    private Jogador jogadorVerm = null;
    private Tabuleiro tabuleiro = null;
    private String nomes[] = new String[2];
    private byte jogador = Tabuleiro.AZUL;
    private Visao visao = null;
    private FactoryTabuleiro ft = null;

    @Override
    public void preInicio() {
        setVisao(new Visao());
        getVisao().setServidor(this);
        getVisao().setVisible(true);
    }

    @Override
    public void inicio() {
        getVisao().setMensagem("");
        ft = new FactoryTabuleiroImp();
        tabuleiro = ft.createTabuleiro();

        tabuleiro.inicia();
        getVisao().desenhaTabuleiro(getTab());
    }

    /**
     * Reinicia o servidor com a configura��o original
     */
    @Override
    public void reinicia() {
        (new Server(ServidorImp.this)).start();
    }

    @Override
    public byte[][] getTab() {
        return tabuleiro.getTab();
    }

    @Override
    public void addObservador(JogoObservador jo) {
        lo.add(jo);
    }

    @Override
    public void removeObservador(JogoObservador jo) {
        lo.remove(jo);
    }

    @Override
    public Jogador getJogadorAzul() {
        return jogadorAzul;
    }

    @Override
    public void setJogadorAzul(Jogador jogadorAzul) {
        this.jogadorAzul = jogadorAzul;
    }

    @Override
    public Jogador getJogadorVerm() {
        return jogadorVerm;
    }

    @Override
    public void setJogadorVerm(Jogador jogadorVerm) {
        this.jogadorVerm = jogadorVerm;
    }

    @Override
    public Visao getVisao() {
        return visao;
    }

    public void setVisao(Visao visao) {
        this.visao = visao;
    }

    /**
     * exibeResultado da partida em caso de desistencia
     * @param j indice do jogador ganhador
     */
    public void exibeResultadoPorDesistencia(byte j) {
        visao.setMensagem("Venceu por timeout o jogador " + nomes[j] + ", Cor: " + Tabuleiro.cor[j]);
    }

    /**
     * exibeResultado da partida
     */
    private void exibeResultado() {
        TabuleiroGoMoku tg = (TabuleiroGoMoku) tabuleiro;
        int linha = tg.win_r1;
        int col = tg.win_c1;
        int il = 0;
        if (tg.win_r1 < tg.win_r2) {
            il = 1;
        }
        if (tg.win_r1 > tg.win_r2) {
            il = -1;
        }
        int ic = 0;
        if (tg.win_c1 < tg.win_c2) {
            ic = 1;
        }
        if (tg.win_c1 > tg.win_c2) {
            ic = -1;
        }

        for (int i = 0; i < 5; i++, linha += il, col += ic) {
            visao.setFundo(linha,col);
        }

        visao.setStatus("Fim");
        visao.setMensagem("Venceu o jogador " + nomes[tabuleiro.vencedorNum()] + ", Cor: " + tabuleiro.vencedorCor());
     }

    public void executaJogada(byte jogador, Jogada j) {
        getVisao().setMensagem("");
        if (!tabuleiro.move(jogador, j)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Servidor: Jogada ").append(j.getLinha()).append("-").append(j.getColuna());

            if (j.getColunaInicial() != -1) {
                sb.append(" relativa a peça ").append(j.getLinhaInicial()).append("-").
                        append(j.getColunaInicial());
            }
            sb.append(" Invalida!!");
            String s = sb.toString();
            getVisao().setMensagem(s);
            System.out.println(s);
        } else {
            getVisao().desenhaTabuleiro(tabuleiro.getTab());
            System.out.println("\n" + tabuleiro.toString());
        }
    }

    public static void main(String a[]) {
        Servidor servidor = new ServidorImp();
        servidor.preInicio();
        servidor.inicio();
    }

    /**
     *classe interna para receber as mensagens.
     */
    final class Server extends Thread {

        Socket arsocket[] = new Socket[2];
        String oHost = "localhost";
        ServerSocket ssocket = null;
        BufferedWriter soutput[] = new BufferedWriter[2];
        BufferedReader cinput[] = new BufferedReader[2];
        private int porta = Configuracao.getInstance().getPorta();
        Servidor pai;

        public Server(Servidor pai) {
            this.pai = pai;
        }

        private void espera() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }

        private void joga() {
            String linhaLida;
            int linha = -1, coluna = -1;

            pai.inicio();

            try {
                ssocket = new ServerSocket(porta);
                oHost = ssocket.getInetAddress().getHostName().trim();

                pai.getVisao().setMensagem("Esperando jogador 1 em " + oHost + "/" + porta);
                arsocket[0] = ssocket.accept();
                soutput[0] = new BufferedWriter(new OutputStreamWriter(arsocket[0].getOutputStream()));
                cinput[0] = new BufferedReader(new InputStreamReader(arsocket[0].getInputStream()));
                nomes[0] = cinput[0].readLine();
                System.out.println("Recebido:"+nomes[0]);
                arsocket[0].setSoTimeout(TIMEOUT);
                pai.getVisao().setMensagem("Esperando jogador 2");
                arsocket[1] = ssocket.accept();
                soutput[1] = new BufferedWriter(new OutputStreamWriter(arsocket[1].getOutputStream()));
                cinput[1] = new BufferedReader(new InputStreamReader(arsocket[1].getInputStream()));
                nomes[1] = cinput[1].readLine();
                System.out.println("Recebido:"+nomes[1]);

                arsocket[1].setSoTimeout(TIMEOUT);

                for (int i = 0;; i++) {
                    byte j = (byte) (i % 2);
                    jogador = j == 0 ? Tabuleiro.AZUL : Tabuleiro.VERM;
                    pai.getVisao().setMensagem("jog:" + i + " Azul:" + tabuleiro.numPecas(Tabuleiro.AZUL) + "  Vermelho:" + tabuleiro.numPecas(Tabuleiro.VERM));
                    linhaLida = "" + jogador + "\n" + linha + "\n" + coluna + "\n";
                    System.out.println("Enviado para o jogador " + Tabuleiro.cor[j] + ":" + linhaLida);
                    soutput[j].write(linhaLida);
                    soutput[j].flush();
                    getVisao().setStatus("Esperando jogador " + nomes[j] + "-" + Tabuleiro.cor[j]);
                    getVisao().resetTimer();
                    try {
                        linhaLida = cinput[j].readLine();
                        if (linhaLida.charAt(0) == '#') {
                            exibeResultadoPorDesistencia((byte) ((i + 1) % 2));
                            return;
                        }
                        linha = Integer.parseInt(linhaLida);
                        linhaLida = cinput[j].readLine();
                        coluna = Integer.parseInt(linhaLida);
                    } catch (SocketTimeoutException ste) {
                        exibeResultadoPorDesistencia((byte) ((i + 1) % 2));
                        return;
                    }
                    Thread.yield();
                    if (linha == -1) {
                        System.out.println("Jogador " + Tabuleiro.cor[j] + " Passando a jogada!");
                    } else {
                        executaJogada(jogador, new Jogada(-1, -1, linha, coluna));
                        espera();
                    }
                    if (tabuleiro.fimJogo()) {
                        exibeResultado();
                        return;
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                getVisao().setMensagem(e.getMessage());
            } finally {
                if (ssocket == null) {
                    return;
                }
                try {
                    soutput[0].write("#\n");
                    soutput[0].flush();
                    soutput[1].write("#\n");
                    soutput[1].flush();
                    arsocket[0].close();
                    arsocket[1].close();
                    ssocket.close();
                } catch (Exception ee) {
                }
            }
        }

        @Override
        public void run() {
            joga();
        }
    }
}

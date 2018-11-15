/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku;
/*
 * Jogador.java
 *
 * Created on 25 de maio de 2010
 */
import static gomoku.JogadorG1.MAXNIVEL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jogo.Jogador;
import tabuleiro.Jogada;
import tabuleiro.Tabuleiro;
/**
 * Esta Classe implementa o esqueleto de um jogador que usa o algoritmo MimMax com corte alfa-beta.
 *<br>
 * @author  Alcione
 * @version 1.0
 */
public class JogadorG1 extends Jogador implements JogadorMinMax{

    public final static int MAXTEMPO = 10000;
    public Jogada jogada = null;
    @Override
    public synchronized void setJogada(Jogada jogada) {
        this.jogada = jogada;
    }
    final static int MAXNIVEL = 4;
    /*
     * @param args host maquina onde esta o servidor
     */
    public JogadorG1(String args[]) {
        super(args);
    }

    /**
     * Calcula uma nova jogada para o tabuleiro e jogador corrente.
     * @param tab Tabuleiro corrente
     * @param jogador Jogador corrente
     * @return retorna a jogada calculada.
     */
    @Override
    public Jogada calculaJogada(Tabuleiro tab, byte jogador) {
        Date tempo1 = new Date();
        Date tempo2;
        long usado =0;
        int prof = MAXNIVEL;
        //for (int prof = 1; prof <= MAXNIVEL; prof++) {
            gomoku.MinMaxTemplateG1 minMax = new gomoku.MinMaxTemplateG1(tab, jogador,  prof);
            minMax.setPai(this);
            minMax.start();
            try {
                minMax.join(MAXTEMPO-usado);
            } catch (InterruptedException ex) {
                Logger.getLogger(JogadorG1.class.getName()).log(Level.SEVERE, null, ex);
            }
            tempo2 = new Date();
            usado = tempo2.getTime() - tempo1.getTime();
            if (usado >= MAXTEMPO){
                return jogada;
            }
        //}
        return jogada;
        
    }

    /**
     * @param args argumentos da linha de comando: host do servidor othelo
     */
    public static void main(String args[]) {
        if (args.length < 2) {
            String a[] = new String[2];
            a[0] = "MinMax" + new Double((Math.random() * 100)).shortValue();
            a[1] = "localhost";
            (new JogadorG1(a)).joga();
            //System.out.println("Usar:java Jogador <nome> <host>!");
        } else {
            (new JogadorG1(args)).joga();
        }
        System.out.println("fim!");
    }
    
    @Override
    public Jogada getJogada() {
        return jogada;
    }
}


class MinMaxTemplateG1 extends Thread {

    private int profundidade = 1;
    TabuleiroG1 tabIni;
    byte jogador, oponente;
    private JogadorMinMax pai = null;
    
    private class Movimento{
        private int valor;
        private Jogada jogada;
        
        public Movimento(){
            jogada = new Jogada(-1,-1,-1,-1);
            valor = 0;            
        }
        public Movimento(int v){
            valor = v;
        }
        public Movimento(int v, Jogada j){
            valor = v;
            jogada = j;
        }
        public Movimento(Movimento m){
            valor = m.getValor();
            this.jogada = m.getJogada();
        }   
        public int getValor() { return valor;}
        public Jogada getJogada() {return jogada;}
        public void setValor(int v){ valor = v;}
        public void setJogada(Jogada j){this.jogada = j;}
        
        @Override
        public String toString(){
            String retorno =  "Movimento[["+valor+", ";
            if (jogada == null) retorno+="null ]]";
            else retorno+=jogada.toString() + " ]]";
            return retorno;
        }
    }//fim da classe movimento
    
    
    public void setPai(JogadorMinMax pai) {
        this.pai = pai;
    }

    public MinMaxTemplateG1(Tabuleiro tab, byte jogador,  int profundidade) {
        tabIni = new TabuleiroG1();
        tabIni.copiaTab(tab.getTab());
        this.jogador = jogador;
        this.oponente = (byte)(this.jogador == 0?1:0);
        this.profundidade = profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }
   
    @Override
    public void run() {
        int melhorPontuacao = Integer.MIN_VALUE;
        Jogada jogadaToReturn = null;
        ArrayList<Jogada> jogadas = (ArrayList) tabIni.obtemJogadasPossiveis(jogador);
        for (Jogada j : jogadas) {
            TabuleiroG1 tb = new TabuleiroG1(tabIni.getTab());
            tb.move(this.jogador, j);
            int alpha = alphabeta(this.jogador, tb, profundidade, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            if (alpha >= melhorPontuacao) {
                melhorPontuacao = alpha;
                 pai.setJogada(j);
            }
        }
        if(jogadas.size() == 1)pai.setJogada(jogadas.get(0));
    }
    //Estrategia utilizada no porgrama
    public int alphabeta(byte jr, final TabuleiroG1 tab, int nivel, int alfa, int beta, boolean maximizando) {
        List<Jogada> jogadas = tab.obtemJogadasPossiveis(jr);
        if (nivel <=  0 || jogadas.isEmpty()) return tab.heuristicaBasica(jr, tab.getTab());
        int v;
        if (maximizando)
        {
            v = Integer.MIN_VALUE;
            for(Jogada j : jogadas)
            {
                TabuleiroG1 tabAux = new TabuleiroG1(tab.getTab());
                tabAux.move(jr, j);
                v = Math.max(v,alphabeta(oponente, tabAux, nivel-1, alfa, beta, false));
                alfa = Math.max(v, alfa);
                if(alfa >= beta) return alfa;
            }//for
            return v;
        }//^if (maximizando)
        else{ // minimizando
            v = Integer.MAX_VALUE;
            for(Jogada j : jogadas)
            {   
                TabuleiroG1 tabAux = new TabuleiroG1(tab.getTab());
                tabAux.move(jr, j);
                v = Math.min(v, alphabeta(this.jogador, tabAux, nivel-1, alfa, beta, true));
                beta = Math.min(v, beta);
                if(alfa >= beta)return beta;
            }//fim do for
            return v;
        }//^else fim de minimizando
    }//fim da funcao*/
}//fim da classe 
    


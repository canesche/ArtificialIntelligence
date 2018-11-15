/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import tabuleiro.Jogada;
import tabuleiro.Tabuleiro;

/**
 *
 * @author alcione
 */
public interface JogadorMinMax {

    /**
     * Calcula uma nova jogada para o tabuleiro e jogador corrente.
     * @param tab Tabuleiro corrente
     * @param jogador Jogador corrente
     * @return retorna a jogada calculada.
     */
    Jogada calculaJogada(Tabuleiro tab, byte jogador);

    Jogada getJogada();

    void setJogada(Jogada jogada);
    
}

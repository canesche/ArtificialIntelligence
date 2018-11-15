/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jogo;

import tabuleiro.Jogada;
import tabuleiro.Tabuleiro;

/**
 *
 * @author alcione
 */
public interface JogadorInt {

    /**
     * Calcula uma nova jogada para o tabuleiro e jogador corrente.
     * Aqui deve ser colocado o algoritmo com as t&eacute;cnicas de inteligencia
     * artificial. No momento as jogadas s&atilde;o calculadas apenas por crit&eacute;rio de
     * validade. Coloque aqui seu algoritmo minmax.
     *
     *
     * @param tab Tabuleiro corrente
     * @param jogador Jogador corrente
     * @return retorna a jogada calculada.
     */
    Jogada calculaJogada(Tabuleiro tab, byte jogador);

    /**
     * M&eacute;todo que inicia o jogo.
     */
    void joga();

    /**
     * Retorna o oponente.
     * @return retorna o oponente.
     */
    byte oponente();

}

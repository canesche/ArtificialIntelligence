/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

/**
 *
 * @author alcione
 */
public interface Servidor {

    public void reinicia();

    public void inicio();

    public void preInicio();

    public Visao getVisao();

    public byte[][] getTab();

    public void addObservador(JogoObservador jo);

    public void removeObservador(JogoObservador jo);

    public Jogador getJogadorAzul();

    public void setJogadorAzul(Jogador jogadorAzul);

    public Jogador getJogadorVerm();

    public void setJogadorVerm(Jogador jogadorVerm);
}

/*
 * Posicao.java
 *
 * Criada em 21 de Abril de 2007, 11:31
 *
 */

package tabuleiro;

/**
 * Armazena uma posicao
 *
 * @author Alcione 
 * @version 1.0
 */
public class Posicao {
    private int linha;
    private int coluna;
    /** Creates a new instance of Posicao */
    public Posicao(int l, int c) {
        linha=l; coluna=c;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
    
}

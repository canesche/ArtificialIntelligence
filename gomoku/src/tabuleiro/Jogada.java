package tabuleiro;

/**
 * Esta Classe implementa as coordenadas de uma jogada.
 * @author Alcione de Paiva
 */
public class Jogada {

    private int linhaInicial, colunaInicial, linha, coluna;

    /**
     * Cria uma objeto jogada com as coordenadas. Se a origem for negativa entao
     * a jogada é considerada como a colocação de uma peça nova.
     * @param linhaInicial linha original da peça
     * @param colunaInicial coluna original da peça 
     * @param linha linha da jogada
     * @param coluna coluna da jogada
     */
    public Jogada(int linhaInicial, int colunaInicial, int linha, int coluna) {
        this.linhaInicial = linhaInicial;
        this.linha = linha;
        this.colunaInicial = colunaInicial;
        this.coluna = coluna;
    }

    /**
     * Cria uma objeto jogada com as coordenadas.
     * @param linha linha da jogada
     * @param coluna coluna da jogada
     */
    public Jogada(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        this.linhaInicial = -1;
        this.colunaInicial = -1;
    }

    /**
     * Define as coordenadas do objeto.
     * @param linhaInicial linha original da peça
     * @param colunaInicial coluna original da peça
     * @param linha linha da jogada
     * @param coluna coluna da jogada
     */
    public void setJogada(int linhaInicial, int colunaInicial, int linha, int coluna) {
        this.linhaInicial = linhaInicial;
        this.linha = linha;
        this.colunaInicial = colunaInicial;
        this.coluna = coluna;
    }

    /**
     * Retorna a coordenada X de destino.
     * @return  coordenada X
     */
    public int getLinha() {
        return linha;
    }

    /**
     * Retorna a coordenada Y de destino.
     * @return  coordenada Y
     */
    public int getColuna() {
        return coluna;
    }

    /**
     * Retorna a coordenada X de origem.
     * @return  coordenada X
     */
    public int getLinhaInicial() {
        return linhaInicial;
    }

    /**
     * Retorna a coordenada Y de origem.
     * @return  coordenada Y
     */
    public int getColunaInicial() {
        return colunaInicial;
    }
}

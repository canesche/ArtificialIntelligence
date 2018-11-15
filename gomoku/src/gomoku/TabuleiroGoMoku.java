package gomoku;

import java.util.ArrayList;
import java.util.List;
import jogo.Configuracao;
import tabuleiro.Jogada;
import tabuleiro.Tabuleiro;

/**
 * Gomoku &eacute; um jogo japon&ecirc;s antigo, conhecido tamb&eacute;m como
 * RanJu . O objetivo do jogo &eacute; conseguir colocar 5 bolinhas na diagonal,
 * na horizontal ou&nbsp; na vertical. Vence quem atingir este objetivo
 * primeiro. <p><img SRC="file:gomoku.gif" height=179 width=178> <br>&nbsp;
 * <br>&nbsp; Esta Classe implementa o servidor GoMokuServer. <br> N&Atilde;O
 * ALTERE ESSA CLASSE.<br> Ela se registra na porta porta 1962. Passa
 * ent&atilde;o a receber as jogadas dos jogadores e envi&aacute;-las para o
 * oponente segundo um protocolo pr&eacute;-definido. <br><br>
 * <b>Execu&ccedil;&atilde;o</b><br> <center><i>java GoMokuServer</i></center>
 * <br><br> <b>Protocolo</b><br> A cada rodada o servidor recebe uma jogada e
 * envia para o oponente. <br> A jogada recebida possui o seguinte formato:<br>
 * <center><i>&lt;x>\n&lt;y>\n</i></center> <br> <br> Caso o servidor receba o
 * caracter # de um jogador significa que ocorreu algum problema e o jogador
 * estï¿½ desistindo <br> A jogada enviada possui o seguinte format:<br>
 * <center><i>&lt;jogador>\n&lt;x>\n&lt;y>\n</i></center> <br> Onde: <ul>
 * <li>&lt;jogador>= indica qual &eacute; a cor do jogador (Tabuleiro.AZUL ou
 * Tabuleiro.VERM) ou '#' indicando fim do jogo. <li> &lt;x>&lt;y> = s&atilde;o
 * as coordenadas da jogada (0 a 7). </ul> <br><p> <br>
 *
 * @author Alcione
 * @version 2.0
 */
public final class TabuleiroGoMoku implements Tabuleiro {

    private final int DIM = Configuracao.getInstance().getDim();
    private byte tab[][];
    public int win_r1, win_c1, win_r2, win_c2;

    /**
     * Inicia o tabuleiro com a configura&ccedil;&atilde;o padr&atilde;o
     */
    public void inicia() {
        tab = new byte[DIM][DIM];

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                tab[i][j] = LIVRE;
            }
        }

    }

    /**
     * Inicia o tabuleiro com a configura&ccedil;&atilde;o passada na forma de
     * array
     *
     * @param aTab tabela com a configura&ccedil;&atilde;o
     */
    public void inicia(byte aTab[][]) {
        tab = new byte[DIM][DIM];
        if (aTab.length == DIM && aTab[0].length == DIM) {
            for (int i = 0; i < DIM; i++) {
                System.arraycopy(aTab[i], 0, tab[i], 0, DIM);
            }
        }
    }

    /**
     * Cria um tabuleiro com os campos inicializados
     */
    public TabuleiroGoMoku() {
        inicia();
    }

    /**
     * Cria um tabuleiro com os campos inicializados segundo um array.
     *
     * @param aabTab Array contendo os valores para a
     * inicializa&ccedil;&atilde;o.
     */
    public TabuleiroGoMoku(byte aabTab[][]) {
        tab = new byte[DIM][DIM];
        if (aabTab.length == DIM && aabTab[0].length == DIM) {
            for (int i = 0; i < DIM; i++) {
                System.arraycopy(aabTab[i], 0, tab[i], 0, DIM);
            }
        }
    }

    /**
     * Copia as posicoes de um array
     *
     * @param aTab Array contendo os valores para a col&oacute;pia.
     */
    public void copiaTab(byte aTab[][]) {
        for (int i = 0; i < DIM; i++) {
            System.arraycopy(aTab[i], 0, tab[i], 0, DIM);
        }
    }

    /**
     * Copia as posicoes para um array
     *
     * @param aTab Array contendo os valores para a col&oacute;pia.
     */
    public void copiaToTab(byte aTab[][]) {
        for (int i = 0; i < DIM; i++) {
            System.arraycopy(tab[i], 0, aTab[i], 0, DIM);
        }
    }

    /**
     * Retorna uma col&oacute;pia do tabuleiro na forma de array.
     *
     * @return array com os valores
     */
    public byte[][] getTab() {
        byte labTab[][] = new byte[DIM][DIM];
        for (int i = 0; i < DIM; i++) {
            System.arraycopy(tab[i], 0, labTab[i], 0, DIM);
        }
        return labTab;
    }

    /**
     * Retorna o n&uacute;mero de pe&ccedil;as de um jogador.
     *
     * @param jogador byte numero do jogador
     * @return n&uacute;mero de pe&ccedil;as
     */
    public int numPecas(byte jogador) {
        int liTot = 0;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (tab[i][j] == jogador) {
                    liTot++;
                }
            }
        }
        return liTot;
    }

    /**
     * Retorna um vetor contendo as jogadas poss&icute;veis de um jogador
     *
     * @param jogador jogador
     * @return jogadas poss&icute;veis
     */
    public List<Jogada> obtemJogadasPossiveis(byte jogador) {
        List<Jogada> lista = null;
        Jogada aux;

        for (int linha = 0; linha < DIM; linha++) {
            for (int coluna = 0; coluna < DIM; coluna++) {
                aux = new Jogada(-1, -1, linha, coluna);
                if (verifica(jogador, aux) > 0) {
                    if (lista == null) {
                        lista = new ArrayList<Jogada>(20);
                    }
                    lista.add(aux);
                }
            }
        }
        return lista;
    }

    /**
     * Retorna a melhor jogada
     *
     * @param jogador numero do jogador
     * @return melhor jogada
     */
    public Jogada obtemJogadaBoa(byte jogador) {
        Jogada maxj = new Jogada(-1, -1, -1, -1);
        for (int k = 5; k > -1; k--) {
            for (int linha = 0; linha < DIM; linha++) {
                for (int coluna = 0; coluna < DIM; coluna++) {
                    maxj = new Jogada(-1, -1, linha, coluna);
                    if (verifica(jogador, maxj) > 0) {
                        if (count(jogador, linha, coluna, 1, 0) >= k) {
                            return maxj;
                        }
                        if (count(jogador, linha, coluna, 0, 1) >= k) {
                            return maxj;
                        }
                        if (count(jogador, linha, coluna, 1, -1) >= k) {
                            return maxj;
                        }
                        if (count(jogador, linha, coluna, 1, 1) >= k) {
                            return maxj;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retorna a melhor jogada
     *
     * @param jogador numero do jogador
     * @return melhor jogada
     */
    public Jogada obtemJogadaHeuristica(byte jogador) {
        Jogada maxj = new Jogada(-1, -1, -1, -1);
        Jogada auxj = new Jogada(-1, -1, -1, -1);
        int valorMax = Integer.MIN_VALUE;
        int valor;
        byte tabAux[][] = new byte[DIM][DIM];
        for (int k = 5; k > -1; k--) {
            for (int linha = 0; linha < DIM; linha++) {
                for (int coluna = 0; coluna < DIM; coluna++) {
                    auxj.setJogada(-1, -1, linha, coluna);
                    if (verifica(jogador, auxj) > 0) {
                        this.copiaToTab(tabAux);
                        tabAux[linha][coluna] = jogador;
                        valor = heuristicaBasica(jogador, tabAux);
                        if (valor > valorMax) {
                            valorMax = valor;
                            maxj.setJogada(-1, -1, linha, coluna);
                        }
                    }
                }
            }
        }
        return maxj;
    }

    /**
     * Retorna um valor heuristico para o tabuleiro dado um jogador
     *
     * @param jogador numero do jogador
     * @param tab tabuleiro
     * @return valor do tabuleiro
     */
    public int heuristicaBasica(byte jogador, byte tab[][]) {
        int valor = 0;
        for (int linha = 0; linha < DIM; linha++) {
            for (int coluna = 0; coluna < DIM; coluna++) {
                if (tab[linha][coluna] == jogador) {
                    valor += contaHeuristica(jogador, linha, coluna, 1, 0,tab);
                    valor += contaHeuristica(jogador, linha, coluna, 0, 1, tab);
                    valor += contaHeuristica(jogador, linha, coluna, 1, -1,tab);
                    valor += contaHeuristica(jogador, linha, coluna, 1, 1,tab);
                } else if (tab[linha][coluna] != Tabuleiro.LIVRE) {
                    valor -= 2 * contaHeuristica(oponente(jogador), linha, coluna, 1, 0,tab);
                    valor -= 2 * contaHeuristica(oponente(jogador), linha, coluna, 0, 1,tab);
                    valor -= 2 * contaHeuristica(oponente(jogador), linha, coluna, 1, -1,tab);
                    valor -= 2 * contaHeuristica(oponente(jogador), linha, coluna, 1, 1,tab);
                }

            }
        }
      //  imprimeTab(tab);
      //  System.out.println("valor do tabuleiro:" + valor + " -- para jogador:" + jogador);
        return valor;
    }

    /**
     * Executa um movimento. Retonar true se o movimento foi bem sucedido
     *
     * @param aiJogador jogador
     * @param jog jogada
     * @return true se o movimento &ecute; v&acute;lido.
     */
    public boolean move(byte aiJogador, Jogada jog) {
        byte lbTot = verifica(aiJogador, jog);
        if (lbTot > 0) {
            tab[jog.getLinha()][jog.getColuna()] = aiJogador;
            return true;
        }
        return false;
    }

    /**
     * Verifica se o jogo terminou
     *
     * @return false = nao terminou; true = terminou
     */
    public boolean fimJogo() {
        for (int linha = 0; linha < DIM; linha++) {
            for (int coluna = 0; coluna < DIM; coluna++) {
                if (tab[linha][coluna] != Tabuleiro.LIVRE) {
                    if (temosVencedor(linha, coluna)) {
                        return true;
                    }
                }
            }
        }
        if (obtemJogadasPossiveis(AZUL) != null
                || obtemJogadasPossiveis(VERM) != null) {
            return false;
        }

        return true;
    }

    public String vencedorCor() {
        return cor[vencedorNum()];
    }

    /**
     * Verifica quem e o vencedor
     *
     * @return 0 = nao terminou; AZUL = venceu o jogador Azul; VERM = venceu o
     * jogador vermelho; -1 empate
     */
    public int vencedorNum() {

        return tab[win_r1][win_c1];

    }

    /**
     * Verifica se um movimento &ecute; v&acute;lido.
     *
     * @param jogador jogador
     * @param j Jogada
     * @return 0 se o movimento é inválido e >0 movimento &ecute; v&acute;lido.
     */
    public byte verifica(byte jogador, Jogada j) {

        if (j.getLinha() < 0 || j.getColuna() < 0 || j.getLinha() > DIM - 1 || j.getColuna() > DIM - 1) {
            return 0;
        }
        if (tab[j.getLinha()][j.getColuna()] != LIVRE) {
            return 0;
        }
        return 1;
    }

    /**
     * Verifica se uma posicao esta fora do tabuleiro.
     *
     * @param linha linha
     * @param coluna coluna
     * @return true se saiu.
     */
    public boolean saiuTabuleiro(int linha, int coluna) {

        if (linha < 0 || coluna < 0 || linha > DIM - 1 || coluna > DIM - 1) {
            return true;
        }
        return false;
    }

    /**
     * Conta o numero de pecas de um jogador a partir da posicao passada e na
     * direcao especificada. O s valores da direcao definida por dirX e dirY
     * devem ser 0, 1, or -1, sendo que um deles deve ser diferente de zero.
     */
    private int count(byte jogador, int linha, int coluna, int dirX, int dirY) {
        int ct = 1;  // Numero de pecas em linha de um jogador.
        int lin, col;    // linha e coluna a serem examinadas

        lin = linha + dirX;  // define a direcao .
        col = coluna + dirY;
        while (lin >= 0 && lin < DIM && col >= 0 && col < DIM && tab[lin][col] == jogador) {
            // Quadrado esta no tabuleiro e contem uma peca do jogador.
            ct++;
            lin += dirX;  // Va para o proximo.
            col += dirY;
        }

        win_r1 = lin - dirX;  // Quadrado anterior.
        win_c1 = col - dirY;  // Quadrado nao esta no tabuleiro ou contem uma peca do jogador.

        lin = linha - dirX;  // Olhe na direcao oposta.
        col = coluna - dirY;
        while (lin >= 0 && lin < DIM && col >= 0 && col < DIM && tab[lin][col] == jogador) {
            // Quadrado esta no tabuleiro e contem uma peca do jogador.
            ct++;
            lin -= dirX;   // Va para o proximo.
            col -= dirY;
        }

        win_r2 = lin + dirX;
        win_c2 = col + dirY;

        // Neste ponto, (win_r1,win_c1) e (win_r2,win_c2) marcam as extremidades 
        // da linha que pertence ao jogador.

        return ct;

    }  // fim count()

    /**
     * Conta o numero de pecas de um jogador a partir da posicao passada e na
     * direcao especificada levando em consideracao a vantagem. Os valores da
     * direcao definida por dirX e dirY devem ser 0, 1, or -1, sendo que um
     * deles deve ser diferente de zero.
     */
    private int contaHeuristica(byte jogador, int linha, int coluna, int dirX, int dirY, byte tab[][]) {
        int ct = 1;  // Numero de pecas em linha de um jogador.
        int lin, col;    // linha e coluna a serem examinadas
        boolean boqueadoPonta1 = false, boqueadoPonta2 = false;
        if ( tab[0][1] != -1 && tab[0][6] != -1 && tab[0][5] != -1) {
            System.out.println("Cheguei");
        }
        lin = linha + dirX;  // define a direcao .
        col = coluna + dirY;
        while (!saiuTabuleiro(lin, col) && tab[lin][col] == jogador) {
            // Quadrado esta no tabuleiro e contem uma peca do jogador.
            ct++;
            lin += dirX;  // Va para o proximo.
            col += dirY;
        }
        // verifica se fechou a ponta
        if (saiuTabuleiro(lin, col) || tab[lin][col] != Tabuleiro.LIVRE) {
            boqueadoPonta1 = true;
        }

        win_r1 = lin - dirX;  // Quadrado anterior.
        win_c1 = col - dirY;  // Quadrado nao esta no tabuleiro ou contem uma peca do jogador.


        lin = linha - dirX;  // Olhe na direcao oposta.
        col = coluna - dirY;
        while (!saiuTabuleiro(lin, col) && tab[lin][col] == jogador) {
            // Quadrado esta no tabuleiro e contem uma peca do jogador.
            ct++;
            lin -= dirX;   // Va para o proximo.
            col -= dirY;
        }
        // verifica se fechou a ponta
        if (saiuTabuleiro(lin, col) || tab[lin][col] != Tabuleiro.LIVRE) {
            boqueadoPonta2 = true;
        }

        win_r2 = lin + dirX;
        win_c2 = col + dirY;

        // Neste ponto, (win_r1,win_c1) e (win_r2,win_c2) marcam as extremidades 
        // da linha que pertence ao jogador.

        // Verifica se esta bloqueado e nao pode fecha essa linha
        if (ct < 5 && boqueadoPonta1 && boqueadoPonta2) {
            ct = 0;
        } else if (ct == 5) {
            ct = 100;
        } else if (ct == 4) {
            ct = 50;
        }
        return ct;

    }  // fim count()

    /**
     * Chamado apos uma jogada para verificar se resultou em um ganhador
     *
     * @param linha linha da jogada
     * @param coluna coluna da jogada
     * @return true se existe um vencedor.
     */
    public boolean temosVencedor(int linha, int coluna) {
        if (count(tab[linha][coluna], linha, coluna, 1, 0) >= 5) {
            return true;
        }
        if (count(tab[linha][coluna], linha, coluna, 0, 1) >= 5) {
            return true;
        }
        if (count(tab[linha][coluna], linha, coluna, 1, -1) >= 5) {
            return true;
        }
        if (count(tab[linha][coluna], linha, coluna, 1, 1) >= 5) {
            return true;
        }

        // ainda nao existe vencedor
        win_r1 = -1;
        return false;

    }  // end winner()

    /**
     * Retorna o tabuleiro na forma de String
     */
    @Override
    public String toString() {

        StringBuilder loBuff = new StringBuilder("   ");
        for (int i = 0; i < DIM; i++) {
            loBuff.append(i).append(' ');
        }
        loBuff.append("\n");
        for (int linha = 0; linha < DIM; linha++) {
            loBuff.append(" ").append(linha);
            for (int coluna = 0; coluna < DIM; coluna++) {
                if (tab[linha][coluna] == VERM) {
                    loBuff.append(" V");
                } else if (tab[linha][coluna] == AZUL) {
                    loBuff.append(" A");
                } else {
                    loBuff.append(" -");
                }
            }
            loBuff.append('\n');
        }
        return loBuff.toString();
    }

    public void imprimeTab(byte tab[][]) {
        for (int linha = 0; linha < DIM; linha++) {

            for (int coluna = 0; coluna < DIM; coluna++) {
                if (tab[linha][coluna] == VERM) {
                    System.out.print(" V");
                } else if (tab[linha][coluna] == AZUL) {
                    System.out.print(" A");
                } else {
                    System.out.print(" -");
                }
            }
            System.out.println("");
        }
    }

    public byte oponente(byte jogador) {

        if (jogador == Tabuleiro.AZUL) {
            return Tabuleiro.VERM;
        } else {
            return Tabuleiro.AZUL;
        }
    }
   
}

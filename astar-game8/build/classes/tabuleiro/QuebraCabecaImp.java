/*
 * QuebraCabecaImp.java
 *
 * criado em 19 de Abril de 2007, 16:04
 *
 * Esta classe implementa as rotinas para manipulação de um quebra-cabeçaa
 *
 */

package tabuleiro;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alcione
 * @version 1.0
 */
public class QuebraCabecaImp implements QuebraCabeca{
  
    
    
    private int [][] tab = null;
    private final int [][] tabGabarito = {{1,2,3},{4,VAZIO,5},{6,7,8}};
    
    /**
     * Construtor que inicia o quebra-cabeca com posicoes aleatorias
     */
    public QuebraCabecaImp() {
        tab = new int [3][3];
        
        java.util.ArrayList<Integer> al = new java.util.ArrayList<Integer>();
        
        while(al.size()<9) {
            int n = (int)Math.round(Math.random()*10)%9;
            if (n==0) n=VAZIO;
            if ( al.indexOf(n)==VAZIO) al.add(n);
        }
        
        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ )
                tab[i][j]=al.get(j+(i*3)).intValue();
    }
    
    /**
     * Método que retorna uma copia do array interno de posições (-1 = vazio)
     * @return int[][] - um array 3x3 de inteiros
     */
    @Override
    public int[][] getTab() {
        int [][] auxTab = new int [3][3];

        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ )
                auxTab[i][j]=tab[i][j];
        return auxTab;
    }
    
    /**
     * Método define o arranjo de peças segundo o array passado por parametro
     * @param  aux - um array 3x3 de inteiros (-1 = vazio)
     * @throws Exception se o array nao e' compativel
     */
    @Override
    public void setTab(int[][] aux) throws Exception {
        if (aux==null) throw new Exception("Array vazio!");
        if (aux.length!=3 && aux[0].length!=3) throw new Exception("Tamanho incompativel!");
        
        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ )
                tab[i][j]=aux[i][j];
    }
    
    /**
     * Move o vazio da posicao linha1, col1 para linha2,  col2
     * @param  linha1 - linha do vazio
     * @param  col1 - coluna do vazio
     * @param  linha2 - linha do destino
     * @param  col2 - coluna do destino
     * @throws Exception se a posicao e' invalida
     */
    @Override
    public void move(int linha1,int col1, int linha2, int col2) throws Exception {
        if (linha1<0 || linha1>2) throw new Exception("Fora de dimensao!");
        if (linha2<0 || linha2>2) throw new Exception("Fora de dimensï¿½o!");
        if (col1<0 || col1>2) throw new Exception("Fora de dimensï¿½o!");
        if (col2<0 || col2>2) throw new Exception("Fora de dimensï¿½o!");
        if (tab[linha1][col1]!=VAZIO) throw new Exception("Nï¿½o ï¿½ a posiï¿½ï¿½o do vazio!");
        if (linha2!=linha1 && col2!=col1) throw new Exception("Movimento na diagonal!");
        if (Math.abs(linha2-linha1)>1 || Math.abs(col2-col1)>1) throw new Exception("Movimento longo!");
        tab[linha1][col1]=tab[linha2][col2];
        tab[linha2][col2]=VAZIO;
    }
    
    /**
     * Verifica se Quebra-Cabeca esta' ordenado
     * @return true se esta' ordenado
     */
    @Override
    public boolean isOrdenado() {
        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ )
                if (tab[i][j]!=tabGabarito[i][j]) return false;
        return true;
    }
    
    /**
     * retorna o Quebra-cabeça na forma de String para a impressao
     * @return quebra-cabeça na forma de String
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ ) {
            if (tab[i][j]==VAZIO) buf.append(' ').append(' ');
            else buf.append(tab[i][j]).append(' ');
            if (j==2)buf.append('\n');
            }
        return buf.toString();
    }
    
    /**
     * retorna a posição do vazio
     * @return objeto Posicao com as coordenadas da posicao vazia
     */
    @Override
    public Posicao getPosVazio() {
        
        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ )
                if (tab[i][j]==VAZIO) {
                    Posicao pos = new Posicao(i,j);
                    return pos;
                }
        
        return null;
    }
    
    /**
     * retorna uma lista de posicoes de movimentos possiveis
     * @return Lista de posicoes
     */
    @Override
    public List<Posicao> getMovePossiveis() {
        List<Posicao> lista = new ArrayList<Posicao>(2);
        Posicao posv=  getPosVazio();
        if (posv.getLinha()>0) lista.add(new Posicao(posv.getLinha()-1,posv.getColuna()));
        if (posv.getLinha()<2) lista.add(new Posicao(posv.getLinha()+1,posv.getColuna()));
        if (posv.getColuna()>0) lista.add(new Posicao(posv.getLinha(),posv.getColuna()-1));
        if (posv.getColuna()<2) lista.add(new Posicao(posv.getLinha(),posv.getColuna()+1));
        return lista;
    }
    
    /**
     * retorna o valor do quebra-cabeca segundo uma heuristica (implementada a distancia quarterao)
     * @return valor do quebra-cabeca
     */
    @Override
    public int getValor() {
        
        int valor=0;
        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ ) {
            
               if (tab[i][j]!=tabGabarito[i][j] && tab[i][j]!=VAZIO) {
                  Posicao pos = getPos(tab[i][j], tabGabarito);
                  valor +=(Math.abs(pos.getLinha()-i)+Math.abs(pos.getColuna()-j));
               }
             
            //if (tab[i][j]!=tabGabarito[i][j]) valor++;
            }
        return valor;
    }
    
    /**
     * retorna a posicao de um valor em relacao a um array
     * @param valor
     * @param tab array a ser investigado
     * @return a posicao
     */
    public static Posicao getPos(int valor, int[][] tab) {
        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ )
                if (tab[i][j]==valor) return new Posicao(i,j);
        return null;
    }
    
    /**
     * Verifica se dois quebra-cabecas sao iguais
     * @param qc - quebra-cabeca
     * @return true se sao iguais
     */
    @Override
    public boolean equals(QuebraCabeca qc) {
        int [][] tabaux = qc.getTab();
        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ )
                if (tab[i][j]!=tabaux[i][j]) return false;
        return true;
    }
    
    /**
     * Retorna um codigo hash para este quebra-cabeca
     * @return codigo hash
     */
    @Override
    public int hashCode(){
        int codigo=0;
        double exp=0.0;
        
        for (int i =0;i<3;i++ )
            for (int j =0;j<3;j++ ) {
                if (tab[i][j]!=VAZIO) {
                  codigo = codigo + tab[i][j]*(int)Math.round(Math.pow(10.0,exp));
                }
                else {
                 codigo = codigo +9*(int)Math.round(Math.pow(10.0,exp));
                }
                exp+=1.0;

            }
        return codigo;      
    }
    
}

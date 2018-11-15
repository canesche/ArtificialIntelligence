/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aestrela;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tabuleiro.Posicao;
import tabuleiro.QuebraCabeca;
import tabuleiro.QuebraCabecaImp;

/**
 *
 * @author canesche
 */

public class AEstrelaImp implements AEstrela {

    // construtor
    public AEstrelaImp() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean igual(int l, ArrayList a){
    
        for(int i = 0; i < a.size(); i++){
            if(a.get(i).equals(l)){
                return true;
            }
        }
            
        return false;
    }
    
    @Override
    public List<Posicao> getSolucao(QuebraCabeca qc) {
        
        List<Posicao> pos = new <Posicao>ArrayList();
        
        int posVazioX, posVazioY, posX, posY, estado = 0, peso = 100;
        
        // declarando uma copia do estado inicial por valor
        QuebraCabeca atual = new QuebraCabecaImp();
        QuebraCabeca proximo = new QuebraCabecaImp();
        ArrayList<Integer> anterior = new <Integer>ArrayList();
        int[][] novoint = qc.getTab();
        
        try {
            proximo.setTab(novoint);
            atual.setTab(novoint);
        } catch (Exception ex) {
            Logger.getLogger(AEstrelaImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(true){
            // se encontrou a solucao, sai do loop
            if(atual.isOrdenado())
                break;            
            
            //System.out.println(pos);
            
            for(int i = 0; i < atual.getMovePossiveis().size(); i++){
                
                posVazioX = atual.getPosVazio().getLinha();
                posVazioY = atual.getPosVazio().getColuna();
                posX = atual.getMovePossiveis().get(i).getLinha();
                posY = atual.getMovePossiveis().get(i).getColuna();
                
                novoint = atual.getTab();
                
                try {
                    proximo.setTab(novoint);
                    proximo.move(posVazioX, posVazioY, posX, posY);
                    
                    System.out.println(posX+" "+posY+" "+peso);
                    if(!igual(proximo.hashCode(), (ArrayList) anterior) && proximo.getValor() < peso){
                        
                        anterior.add(proximo.hashCode());
                        peso = proximo.getValor();
                        estado = i;
                    }
                    
                } catch (Exception ex) {
                    Logger.getLogger(AEstrelaImp.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            posVazioX = atual.getPosVazio().getLinha();
            posVazioY = atual.getPosVazio().getColuna();
            posX = atual.getMovePossiveis().get(estado).getLinha();
            posY = atual.getMovePossiveis().get(estado).getColuna();
            
            // adiciona a linha e coluna da mudança
            pos.add(atual.getMovePossiveis().get(estado));
            try {
                System.out.println(posX+" "+posY+" "+peso);
                atual.move(posVazioX, posVazioY, posX, posY);
                peso = 100;
                
                atual.toString();
            } catch (Exception ex) {
                Logger.getLogger(AEstrelaImp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            break;
        }
        
        return pos;
    }
    
    
}

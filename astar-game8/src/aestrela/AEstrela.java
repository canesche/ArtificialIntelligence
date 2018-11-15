/*
 * AEstrela.java
 *
 * Criada em 21 de Abril de 2007, 09:33
 *
 */

package aestrela;

import java.util.List;
import tabuleiro.Posicao;
import tabuleiro.QuebraCabeca;

/**
 *
 * @author Alcione
 * @version 1.0 
 */
public interface AEstrela {
    /**
     * Recebe um quebra-cabeca e retorna uma lista de posições que representa os movimentos necessarios para chegar a solucao.
     * @param qc - Quebra-cabeca com o estado inicial
     * @return lista com os movimentos a serem realizados 
     */
     public List<Posicao> getSolucao(QuebraCabeca qc);
}

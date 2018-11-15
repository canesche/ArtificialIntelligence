/*
 * QuebraCabecaImpTest.java
 * JUnit based test
 *
 * Created on 19 de Abril de 2007, 16:48
 */

package tabuleiro;

import junit.framework.*;

/**
 *
 * @author alcione
 */
public class QuebraCabecaImpTest extends TestCase {
    
    public QuebraCabecaImpTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

  

    /**
     * Teste do método move, da classe tabuleiro.QuebraCabecaImp.
     */
    public void testMove() throws Exception {
        System.out.println("move");
        
        int linha1 = 0;
        int col1 = 0;
        int linha2 = 0;
        int col2 = 0;
        QuebraCabecaImp instance = new QuebraCabecaImp();
        
        instance.move(linha1, col1, linha2, col2);
        
        // TODO rever o código de teste gerado e remover a chamada de falha padr�o.
        fail("O caso de teste é um prot�tipo.");
    }

    /**
     * Teste do método isOrdenado, da classe tabuleiro.QuebraCabecaImp.
     */
    public void testIsOrdenado() {
        System.out.println("isOrdenado");
        
        QuebraCabecaImp instance = new QuebraCabecaImp();
        
        boolean expResult = true;
        boolean result = instance.isOrdenado();
        assertEquals(expResult, result);
        
        // TODO rever o c�digo de teste gerado e remover a chamada de falha padr�o.
        fail("O caso de teste é um protótipo.");
    }
    
}

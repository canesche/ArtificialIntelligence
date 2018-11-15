/*
 * AEstrelaTest.java
 * JUnit based test
 *
 * Created on 21 de Abril de 2007, 14:17
 */

package aestrela;

import junit.framework.*;
import java.util.List;
import tabuleiro.Posicao;
import tabuleiro.QuebraCabeca;

/**
 *
 * @author kurumin
 */
public class AEstrelaTest extends TestCase {
    
    public AEstrelaTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getSolucao method, of class aestrela.AEstrela.
     */
    public void testGetSolucao() {
        System.out.println("getSolucao");
        
        QuebraCabeca qc = null;
        AEstrela instance = new AEstrelaImp();
        
        List<int[]> expResult = null;
        List<Posicao> result = instance.getSolucao(qc);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

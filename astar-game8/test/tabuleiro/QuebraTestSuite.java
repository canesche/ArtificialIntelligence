/*
 * QuebraTestSuite.java
 * JUnit based test
 *
 * Created on 19 de Abril de 2007, 16:59
 */

package tabuleiro;

import junit.framework.*;

/**
 *
 * @author alcione
 */
public class QuebraTestSuite extends TestCase {
    
    public QuebraTestSuite(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * método da su�te gerado automaticamente pelo m�dulo JUnit
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("QuebraTestSuite");
        return suite;
    }
    
}

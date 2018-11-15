package tabuleiro;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory para cria&ccedil;&atilde;o de tabuleiros
 * @author Alcione de Paiva
 */
public class FactoryTabuleiroImp implements FactoryTabuleiro {

    /**
     * Cria uma nova f�brica
     */
    public FactoryTabuleiroImp() {
    }

    /**
     * Cria um novo tabuleiro 
     */
    public Tabuleiro createTabuleiro() {

        Properties props = new Properties();

        try {
            props.load(new FileInputStream("facTab.properties"));
        } catch (IOException ex) {
            Logger.getLogger(FactoryTabuleiroImp.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            System.out.println("Arquivo properties da factory do tabuleiro não encontrado");
        }

        Class<Tabuleiro> C = null;
        Tabuleiro t = null;
        String nomeClasse = props.getProperty("tabuleiro");
        try {
            C = (Class<Tabuleiro>) Class.forName(nomeClasse);
            t = C.newInstance();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FactoryTabuleiroImp.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FactoryTabuleiroImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FactoryTabuleiroImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return t;
    }
}

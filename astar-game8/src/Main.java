import aestrela.AEstrela;
import aestrela.AEstrelaImp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tabuleiro.Posicao;
import tabuleiro.QuebraCabeca;
import tabuleiro.QuebraCabecaImp;

/**
 *
 * @author Alcione
 * Classe para testar o programa
 */
public class Main {
    /**
     * Ao executar esta classe ela devera' imprimir a seguinte saida: <br><br>
     * <br>Tempo decorrido:104 centesimos de segundos<br>
     *<br>
     * Tabuleiro inicial:<br>
     * 7&#x0020; 2&#x0020; 1 <br>
     * 3&#x0020; 4&#x0020; 8 <br>
     * 6&#x0020;* &#x0020;  5 <br>
     *    <br>
     * Movimentos<br>
     * Linha:2 - Coluna:0<br>
     * Linha:1 - Coluna:0<br>
     * Linha:0 - Coluna:0<br>
     * Linha:0 - Coluna:1<br>
     * Linha:1 - Coluna:1<br>
     * Linha:2 - Coluna:1<br>
     * Linha:2 - Coluna:0<br>
     * Linha:1 - Coluna:0<br>
     * Linha:1 - Coluna:1<br>
     * Linha:2 - Coluna:1<br>
     * Linha:2 - Coluna:2<br>
     * Linha:1 - Coluna:2<br>
     * Linha:1 - Coluna:1<br>
     * Linha:2 - Coluna:1<br>
     * Linha:2 - Coluna:0<br>
     * Linha:1 - Coluna:0<br>
     * Linha:0 - Coluna:0<br>
     * Linha:0 - Coluna:1<br>
     * Linha:0 - Coluna:2<br>
     * Linha:1 - Coluna:2<br>
     * Linha:1 - Coluna:1<br>
     * Linha:1 - Coluna:0<br>
     * Linha:0 - Coluna:0<br>
     * Linha:0 - Coluna:1<br>
     * Linha:1 - Coluna:1<br>
     */
    public static void main(String[] args) {
        QuebraCabeca qc = new QuebraCabecaImp();
        AEstrela instance = new AEstrelaImp();
        
       // int [][] tab = {{1,2,3},{4,5,8},{6,7,QuebraCabeca.VAZIO}};
        int [][] tab = {{7,2,1},{3,4,8},{6,QuebraCabeca.VAZIO,5}};
        try {
            qc.setTab(tab);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        java.util.Date tempo =  new java.util.Date();
        List<Posicao> result = instance.getSolucao(qc);
        System.out.println("Tempo decorrido:"+((new java.util.Date().getTime()-tempo.getTime())/10)+" centesimos de segundos");
        
        System.out.println("\nTabuleiro inicial:");
        System.out.println(qc.toString());
        System.out.println("Movimentos");
        for(Posicao pos: result){
            System.out.println("Linha:"+pos.getLinha()+" - Coluna:"+pos.getColuna());
        }

    }
    
}

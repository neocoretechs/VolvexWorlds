
import java.io.IOException;
import java.util.Random;

import com.neocoretechs.volvex.Chromosome;
import com.neocoretechs.volvex.Genome;
import com.neocoretechs.volvex.fitnessfunctions.FitnessFunction;
import com.neocoretechs.volvex.worlds.RelatrixWorld;

/**
 * Perform simple math.
 * @author jg 2003,2020
 *
 */
public class SimpleMathWorld extends RelatrixWorld {
	private static final long serialVersionUID = 2165916779292359268L;
	private static FitnessFunction fitnessFunction = null;

    
    public SimpleMathWorld(String[] args) throws IOException {
		super(args);
	}

 
     /**
     * Bit more complex: if X < Y result must be X + 1 else 
     * if X > Y result must be X - 1
     * In the unit test we set 2 variables with our test values and
     * If the evolved code comes back and solves it,
     * the fitness is upped
     */
     public float computeRawFitness(Chromosome ind) {
 		return (float) fitnessFunction.execute(ind);
     }
   
 
	/**
	  * Create this world and run the generations converging on
      * best computeFitness. 
      * arg 0=client node<br/>
      * arg 1=remote server node<br/> 
      * arg 2=remote server port for Individuals
      * --deprecated arg 3=remote server port for Chromosomes
      * arg 3=compute:evaluate and run stored individuals from store and ratchet phases<br/>
      * arg 3=ratchet:compute and run like standard, but store best of each generation<br/>
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            Class chromoType = Integer.class;
            Class[] argType = {Integer.class, Integer.class}; // if multiple arguments to chromosome, add additonal
            Class[] nodeType = {Integer.class}; // will pick up integer and boolean
            SimpleMathWorld w = new SimpleMathWorld(args);
            w.genome = new Genome();
            w.genome.setArgType(argType);
            w.genome.setChromoType(chromoType);
            w.genome.setNodeType(nodeType);
            fitnessFunction = new SimpleMath3(w, argType, chromoType);
            if(args.length > 0) {
    			if(args.length == 1 && args[0].equals("compute") || args.length == 4 && args[3].equals("compute") ) {
    				w.compute(20000, 5000, 7, 128, 2048);
    			} else {
    				if( args.length == 1 && args[0].equals("ratchet") || args.length == 4 && args[3].equals("ratchet") ) {
    					w.ratchet(20000, 5000, 7, 128, 2048);
    				}
    			}
            } else {
            	w.compute(20000, 5000, 7, 128, 2048);
            }
        } catch(Exception e) {
                System.out.println(e);
                e.printStackTrace();
        }
	}

}

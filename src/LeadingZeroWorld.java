import java.io.IOException;

import com.neocoretechs.volvex.Chromosome;
import com.neocoretechs.volvex.Genome;
import com.neocoretechs.volvex.fitnessfunctions.FitnessFunction;
import com.neocoretechs.volvex.objects.Strings;
import com.neocoretechs.volvex.worlds.RelatrixWorld;

/**
 * The problem of removing leading zeroes form a string. Typically when a solution is reached here it will strip
 * any leading character specified in argument one from the string specified in argument 2
 * @author jg 2003,2020
 *
 */
public class LeadingZeroWorld extends RelatrixWorld {
	private static final long serialVersionUID = 6742052940362783270L;
	private static FitnessFunction fitnessFunction = null;
	
	public LeadingZeroWorld(String[] args) throws IOException {
		super(args);
	}
	
	@Override
	public float computeRawFitness(Chromosome individual) {
		return (float) fitnessFunction.execute(individual);
	}
  
	/**
	  * Create this world and run the generations converging on
      * best computeFitness. 
      * arg 0=client node<br/>
      * arg 1=remote server node<br/> 
      * arg 2=remote server port for Solver Chromosomes
      * --deprecated arg 3=remote server port for Chromosomes
      * arg 3=compute:evaluate and run stored individuals from store and ratchet phases<br/>
      * arg 3=ratchet:compute and run like standard, but store best of each generation<br/>
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            Class chromoType = Strings.stringClass;
            Class[] argType = {Strings.stringClass}; // if multiple arguments to chromosome, add additonal
            Class[] nodeType = {Strings.stringClass}; // will pick up integer and boolean
            LeadingZeroWorld w = new LeadingZeroWorld(args);
            w.genome = new Genome();
            w.genome.setArgType(argType);
            w.genome.setChromoType(chromoType);
            w.genome.setNodeType(nodeType);
            fitnessFunction = new LeadingZero1(w, argType, chromoType);
            if(args.length > 0) {
    			if( args.length == 1 && args[0].equals("compute") || args.length == 4 && args[3].equals("compute") ) {
    					// Typical operation, run to the maximum number of generations, stopping if a passing Chromosone Solver is found
    					w.compute(20000, 5000, 7, 128, 2048);
    			} else {
    				if( args.length == 1 && args[0].equals("ratchet") || args.length == 4 && args[3].equals("ratchet") ) {
    					// Ratcheting involves storing passing Solver Chromosomes, then continuing to produce more that pass, storing those
    					// until the entire number of generational runs is concluded, rather than stopping
    	  				commitAfter = 5; // set the intermediate Relatrix commit level for ratcheting Chromosomes
    					w.ratchet(20000, 5000, 7, 128, 2048);
    				} else {
    					// Test allows us to load a fitness function, then load the stored Solvers to test against it
       					if(args.length == 4 && args[3].equals("test") ) {
      						w.test();
      					} else {
      						// default to standard mode
      						w.compute(20000, 500, 7, 128, 2048);
      					}
    				}
    			}
            }
        } catch(Exception e) {
                System.out.println(e);
                e.printStackTrace();
        }
	}

}

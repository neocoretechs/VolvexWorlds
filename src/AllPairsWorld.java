import java.io.IOException;

import com.neocoretechs.volvex.Chromosome;
import com.neocoretechs.volvex.Genome;
import com.neocoretechs.volvex.fitnessfunctions.FitnessFunction;
import com.neocoretechs.volvex.objects.Strings;
import com.neocoretechs.volvex.worlds.RelatrixWorld;

/**
 * @author jg
 *
 */
public class AllPairsWorld extends RelatrixWorld {
	private static final long serialVersionUID = -5964339210866058715L;
	private static FitnessFunction fitnessFunction = null;
	
    public AllPairsWorld(String[] args) throws IOException {
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
      * arg 2=remote server port for Individuals
      * --deprecated arg 3=remote server port for Chromosomes
      * arg 3=compute:evaluate and run stored individuals from store and ratchet phases<br/>
      * arg 3=ratchet:compute and run like standard, but store best of each generation<br/>
      */
      public static void main(String[] args) {
              try {
                  Class chromoType = Strings.stringClass;
                  Class[] argType = {Strings.stringClass}; // if multiple arguments to chromosome, add additonal
                  Class[] nodeType = {Strings.stringClass};//, MatrixNxN.matrixClass, Matrix3x3.matrixClass, Matrix2x2.matrixClass}; // will pick up integer and boolean
            	  AllPairsWorld w = new AllPairsWorld(args);
                  w.genome = new Genome();
                  w.genome.setArgType(argType);
                  w.genome.setChromoType(chromoType);
                  w.genome.setNodeType(nodeType);
                  fitnessFunction = new AllPairs1(w, argType, chromoType);
                  w.setStepFactors(1,((AllPairs1)fitnessFunction).seeds[0].length);// 9);
                  if(args.length > 0) {
          			if( args.length == 1 && args[0].equals("compute") || args.length == 4 && args[3].equals("compute")  ) {
          						w.compute(20000, 5000, 7, 128, 2048);
          			} else {
           				if( args.length == 1 && args[0].equals("ratchet") || args.length == 4 && args[3].equals("ratchet") ) {
           					commitAfter = 5;
           					w.ratchet(100000, 5000, 7, 128, 2048);
          				} else {
          					if(args.length == 4 && args[3].equals("test") ) {
          						w.test();
          					} else {
          						//w.standard(20000, 50);
          						w.compute(20000, 5000, 7, 128, 2048);
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

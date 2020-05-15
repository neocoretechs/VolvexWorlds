import java.io.*;

import com.neocoretechs.volvex.Chromosome;
import com.neocoretechs.volvex.Function;
import com.neocoretechs.volvex.Genome;
import com.neocoretechs.volvex.fitnessfunctions.FitnessFunction;
import com.neocoretechs.volvex.functions.*;
import com.neocoretechs.volvex.worlds.RelatrixWorld;

/**
 * The 11-multiplexer problem from Koza[1992], to find a formula to choose
 * one of eight boolean inputs based on the values of three other boolean inputs.
 * Uses a population size of 4000 with (formerly) greedy overselection, and runs for 50 generations, or 20000,
 * depending on how long it takes to converge. 
 * <p>
 * Note: This is where I found the need to optimize fitness calculations. By running this
 * program under the Java profiler (i.e. java -Xprof) I was able to determine where a majority
 * of the run time was being taken up. Originally the accesses to
 * {@link Variable Variable} were the most time-consuming,
 * so I optimized Variable. Now the majority of time is taken by the code of
 * {@link #computeRawFitness computeRawFitness}, which I can't seem to get down any further.
 * Well, now 20 years later I can use a 16 processor box with parallelized raw fitness processor to do it, Bob. - Groff
 * Original code used variables and no arguments, modified to use arguments for 3 inputs.
 * Modified nodeset to inject additional variables 
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: MultiplexerWorld.java,v 1.3 2000/10/12 15:22:55 groovyjava Exp $
 * @author Groff C 4/2020
 */
public class MultiplexerWorld extends RelatrixWorld {
  private static final long serialVersionUID = -2408376816066917144L;
  private static FitnessFunction fitnessFunction = null;
  // This is so that we don't have to use the Variable's Hashtable to get
  // values all the time, which is time-consuming.
  Variable[] vars = new Variable[8];

  /*
  public void create(int individuals) {
    Class[] types = { Function.booleanClass };
    Class[][] argTypes = { {} };*/
    Function[] nodeSets = {   
        vars[0]=Variable.create("D0", Function.booleanClass),
        vars[1]=Variable.create("D1", Function.booleanClass),
        vars[2]=Variable.create("D2", Function.booleanClass),
        vars[3]=Variable.create("D3", Function.booleanClass),
        vars[4]=Variable.create("D4", Function.booleanClass),
        vars[5]=Variable.create("D5", Function.booleanClass),
        vars[6]=Variable.create("D6", Function.booleanClass),
        vars[7]=Variable.create("D7", Function.booleanClass) 
      };/*,
        vars[8]=Variable.create("A0", Function.booleanClass),
        vars[9]=Variable.create("A1", Function.booleanClass),
        vars[10]=Variable.create("A2", Function.booleanClass),
        new And(),
        new Or(),
        new Not(),
        new If(Function.booleanClass)
      }
    };
    create(individuals, types, argTypes, nodeSets);
  }*/

  /*public float computeFitness(Individual ind) {
    return 1.0f/(1.0f+(2048.0f-computeRawFitness(ind)));
  }*/
    
    public MultiplexerWorld(String[] args) throws IOException {
  	  super(args);
      //selectionMethod = new FitnessProportionateSelection();//GreedyOverselection();

    }
    
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
		//try {
			//World world = new MultiplexerWorld();
			//world.create(4000); // population of 4000
			//world.run(50); // for 50 generations
		//} catch (Exception ex) {
			//System.out.println(ex);
			//ex.printStackTrace();
		//}
	        try {
	            Class chromoType = Boolean.class;
	            Class[] argType = {Boolean.class, Boolean.class, Boolean.class}; // if multiple arguments to chromosome, add additonal
	            Class[] nodeType = {Boolean.class, Integer.class}; // will pick up integer and boolean
	            MultiplexerWorld w = new MultiplexerWorld(args);
	            //World.SHOWVARIABLES = true; // show the status of multiplexer variables during truth table
	            w.genome = new Genome();
	            w.genome.setArgType(argType);
	            w.genome.setChromoType(chromoType);
	            w.genome.setNodeType(nodeType);
	            w.genome.setPreload(w.vars); // include the variables in the functionSet
	            fitnessFunction = new Multiplexer1(w, argType, chromoType);
	            w.setStepFactors(8, 256);
	            if(args.length > 0) {
	    			if( args.length == 1 && args[0].equals("compute") || args.length == 4 && args[3].equals("compute") ) {
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

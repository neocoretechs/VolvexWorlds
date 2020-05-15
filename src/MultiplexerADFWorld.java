import com.neocoretechs.volvex.Genome;
import com.neocoretechs.volvex.fitnessfunctions.FitnessFunction;
import com.neocoretechs.volvex.worlds.World;

import java.io.*;

/**
 * The same as MultiplexerWorld, except uses two ADF's with three arguments
 * each. As in MultiplexerWorld, chromosome 0 is the result-producing branch.
 * This module was modified to use the RelatrixWorld paradigm, with original code commented for reference.
 * Hope to chain chromosomes together to demonstrate that functionality.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @author Groff 4/2020
 * @version $Id: MultiplexerADFWorld.java,v 1.3 2000/10/12 15:22:55 groovyjava Exp $
 */
public class MultiplexerADFWorld extends MultiplexerWorld {
  // Use 2 ADF's with 3 args each
  private static final long serialVersionUID = 7411553453392633986L;
  private static FitnessFunction fitnessFunction = null;

  public MultiplexerADFWorld(String[] args) throws IOException {
	  super(args);
  }
  /*
  public void create() {

    Class[] types = { Function.booleanClass, Function.booleanClass, Function.booleanClass };
    Class[][] argTypes = {
      {},
      { Function.booleanClass, Function.booleanClass, Function.booleanClass },
      { Function.booleanClass, Function.booleanClass, Function.booleanClass },
    };
    Function[][] nodeSets = {
      { // result-producing chromosome (0)
        vars[0]=Variable.create("D0", Function.booleanClass),
        vars[1]=Variable.create("D1", Function.booleanClass),
        vars[2]=Variable.create("D2", Function.booleanClass),
        vars[3]=Variable.create("D3", Function.booleanClass),
        vars[4]=Variable.create("D4", Function.booleanClass),
        vars[5]=Variable.create("D5", Function.booleanClass),
        vars[6]=Variable.create("D6", Function.booleanClass),
        vars[7]=Variable.create("D7", Function.booleanClass),
        vars[8]=Variable.create("A0", Function.booleanClass),
        vars[9]=Variable.create("A1", Function.booleanClass),
        vars[10]=Variable.create("A2", Function.booleanClass),
        new ADF(1 ), //, argTypes[1]
        new ADF(2 ), //, argTypes[2]
        new And(),
        new Or(),
        new Not(),
        new If(Function.booleanClass)
      },
      { // chromosome 1
        new And(),
        new Or(),
        new Not(),
        new If(Function.booleanClass)
      },
      { // chromosome 2
        new And(),
        new Or(),
        new Not(),
        new If(Function.booleanClass)
      }
    };

    create(4000, types, argTypes, nodeSets);
  }

	public void output(Individual ind) {
	}

  public static void main(String[] args) {
		try {
			World world = new MultiplexerADFWorld();
			world.create();
			world.run(50); // and run 50 generations
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
  }
  */
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
	            Class chromoType = Boolean.class;
	            Class[] argType = {Boolean.class, Boolean.class, Boolean.class}; // if multiple arguments to chromosome, add additonal
	            Class[] nodeType = {Boolean.class, Integer.class}; // will pick up integer and boolean
	            MultiplexerADFWorld w = new MultiplexerADFWorld(args);
	            World.SHOWVARIABLES = true; // show the status of multiplexer variables during truth table
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
	            	w.compute(20000, 500, 7, 128, 2048);
	            }
	        } catch(Exception e) {
	                System.out.println(e);
	                e.printStackTrace();
	        }
		}

}

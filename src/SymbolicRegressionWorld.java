
import com.neocoretechs.volvex.Chromosome;
import com.neocoretechs.volvex.Function;
import com.neocoretechs.volvex.functions.*;
import com.neocoretechs.volvex.selection.FitnessProportionateSelection;
import com.neocoretechs.volvex.worlds.World;

import java.io.*;

/**
 * The symbolic regression problem of Koza[1992], to find the formula x^4+x^3+x^2+x
 * given 20 random points for x in the range [-1,1]. Uses fitness-proportionate selection.
 * This is an example of a baseline configuration, with none of the bells and whistles of ReltrixWorld.
 * It demonstrates how a minimum configuration can be assembled, and serves as a good manual regression test.
 * <p>
 * Baruch Note: this is where I found that I had to protect the EXP and LN functions against extremely
 * large values, since they would return infinity and then other functions operating on that
 * value (especially SIN and COS) would return Not-a-Number, which would totally throw everything
 * off.
 * <p>
 * Probably a better solution would have been to protect functions against infinity arguments.
 * <P>
 * Copyright (c) 2000 Robert Baruch.
 * Copyright (c) 2020 Jonathan Groff 
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @author Jonathan Groff
 * @version $Id: SymbolicRegressionWorld.java,v 1.4 2000/10/12 15:22:55 groovyjava Exp $
 */
public class SymbolicRegressionWorld extends World {
  private static final long serialVersionUID = 1127104627263133603L;
  Float[] x = new Float[20];
  float[] y = new float[20];
  Variable vx;
  int gen = 0;

  public SymbolicRegressionWorld() throws IOException {
    selectionMethod = new FitnessProportionateSelection();//GreedyOverselection();
    for (int i=0; i<20; i++) {
      float f = 2.0f*(random.nextFloat()-0.5f);
      x[i] = new Float(f);
      y[i] = f*f*f*f+f*f*f+f*f+f;
    }
  }

  public void create(int individuals) {

    Class types = Function.floatClass;
    Class[] argTypes = {Void.class};
    Function[] nodeSets = {   
        vx=Variable.create("X", Function.floatClass),
        new Add(Function.floatClass),
        new Subtract(Function.floatClass),
        new Multiply(Function.floatClass),
        new Divide(Function.floatClass),
        new Sine(Function.floatClass),
        new Cosine(Function.floatClass),
        new Exponential(Function.floatClass),
        new NaturalLogarithm(Function.floatClass)
    };
    create(individuals, types, argTypes, nodeSets);
  }

  public float computeFitness(Chromosome ind) {
    return 1.0f/(1.0f+computeRawFitness(ind));
  }

  public float computeRawFitness(Chromosome ind) {
    float error = 0.0f;

    for (int i=0; i<20; i++) {
      vx.set(x[i]);
      try {
        float result = ind.execute_float(World.noargs);
        error += Math.abs(result-y[i]);
      } catch (ArithmeticException ex) {
        System.out.println("x="+x[i].floatValue());
        System.out.println(ind);
        throw ex;
      }
    }
    return error;
  }

  @Override
  public void run(int numGenerations) {
    Chromosome best;
    long t;

    System.out.println("Best of generation 0:");
    best = getBestIndividual();
    System.out.println(best);
    System.out.println("Raw Fitness = " + computeRawFitness(best));
    System.out.println("Avg Fitness = " +
      (getTotalFitness()/population.getSize()));
    if (computeRawFitness(best)<0.01) {
    	gen = 0;
      return;
    }
    for (int i=1; i<numGenerations; i++) {
      t = System.currentTimeMillis();
      nextGeneration(i);
      long t2 = System.currentTimeMillis()-t;
      System.out.println((((float)t2)/1000)+" sec");
      System.out.println("Best of generation " + i + ":");
      best = getBestIndividual();
      System.out.println(best);
      System.out.println("Raw Fitness = " + computeRawFitness(best));
      System.out.println("Avg Fitness = " +
        (getTotalFitness()/population.getSize()));
      if (computeRawFitness(best)<0.01) {
    	  gen = i;
        return;
      }
    }
    gen = -1;
    return;
  }


  public static void main(String[] args) throws IOException {
	if (args.length == 0) {
		System.out.println("Usage: SymbolicRegressionWorld <population> <#runs>");
		System.exit(0);
	}
    SymbolicRegressionWorld world = new SymbolicRegressionWorld();
    // population of 500
	world.create(Integer.parseInt(args[0]));
	world.run(Integer.parseInt(args[1]));
  }
	
	public void output(Chromosome ind) {
	}

  
}

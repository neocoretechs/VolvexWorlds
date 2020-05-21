import com.neocoretechs.volvex.Genome;
import com.neocoretechs.volvex.fitnessfunctions.FitnessFunction;
import com.neocoretechs.volvex.Chromosome;
import com.neocoretechs.volvex.functions.*;
import com.neocoretechs.volvex.objects.*;
import com.neocoretechs.volvex.worlds.RelatrixWorld;

import java.io.*;

/**
 * ImagerWorld was part of the original jgprog framework. Its a good example of how to construct a minimal 
 * world that nonetheless uses complex objects and functions like matrices and convolution.
 * Title:ImagerWorld
 * Description:
 * Copyright:    Copyright (c) 2001, 2020
 * Company:
 * @author
 * @version 1.0
 */

public class ImagerWorld extends RelatrixWorld {
	private static final long serialVersionUID = -5733588241174688736L;

	public ImagerWorld(String[] args) throws IOException, IllegalAccessException {
		super(args);
	} 
  /*
  public void create(int individuals) {
    Class[] types = { MatrixNxN.matrixClass, MatrixNxN.matrixClass, MatrixNxN.matrixClass, MatrixNxN.matrixClass };
    Class[][] argTypes = {
			{MatrixNxN.matrixClass, Matrix3x3.matrixClass, Matrix3x3.matrixClass},
			{MatrixNxN.matrixClass, Matrix3x3.matrixClass, Matrix3x3.matrixClass},
			{MatrixNxN.matrixClass, Matrix3x3.matrixClass, Matrix3x3.matrixClass},
			{MatrixNxN.matrixClass, MatrixNxN.matrixClass, Matrix3x3.matrixClass}
		};
    Function[][] nodeSets = {
      {
        vars[0]=Variable.create("IMG", MatrixNxN.matrixClass),
        vars[1]=Variable.create("F_", Matrix3x3.matrixClass),
        vars[2]=Variable.create("F-", Matrix3x3.matrixClass),
        vars[3]=Variable.create("F^", Matrix3x3.matrixClass),
        vars[4]=Variable.create("F[", Matrix3x3.matrixClass),
        vars[5]=Variable.create("F|", Matrix3x3.matrixClass),
        vars[6]=Variable.create("F]", Matrix3x3.matrixClass),
        vars[7]=Variable.create("F0", Matrix3x3.matrixClass),
        new Add(Matrix3x3.matrixClass),
        new Subtract(Matrix3x3.matrixClass),
        new Add(MatrixNxN.matrixClass),
        new Subtract(MatrixNxN.matrixClass),
		new Add(Function.integerClass),
		new Subtract(Function.integerClass),
		new One(Function.integerClass),
        new Convolve3x3(),
        new And(),
        new Or(),
        new XOr(),
        new Not(),
        new Nop(),
        new Loop(),
        new True(),
        new False(),
        new Implies(),
        new TwoThings(),
        new If(Function.booleanClass),
        new Equals(Function.booleanClass),
		ADF.ADFs[1],
		ADF.ADFs[2],
		ADF.ADFs[3]
      },
      {
        vars[0],
        vars[1],
        vars[2],
        vars[3],
        vars[4],
        vars[5],
        vars[6],
        vars[7],
        new Add(Matrix3x3.matrixClass),
        new Subtract(Matrix3x3.matrixClass),
        new Add(MatrixNxN.matrixClass),
        new Subtract(MatrixNxN.matrixClass),
		new Add(Function.integerClass),
		new Subtract(Function.integerClass),
		new One(Function.integerClass),
        new Convolve3x3(),
        new And(),
        new Or(),
        new XOr(),
        new Not(),
        new Nop(),
        new Loop(),
        new True(),
        new False(),
        new Implies(),
        new TwoThings(),
        new If(Function.booleanClass),
        new Equals(Function.booleanClass),
		ADF.ADFs[2],
		ADF.ADFs[3]
      },
      {
        vars[0],
        vars[1],
        vars[2],
        vars[3],
        vars[4],
        vars[5],
        vars[6],
        vars[7],
        new Add(Matrix3x3.matrixClass),
        new Subtract(Matrix3x3.matrixClass),
        new Add(MatrixNxN.matrixClass),
        new Subtract(MatrixNxN.matrixClass),
		new Add(Function.integerClass),
		new Subtract(Function.integerClass),
		new One(Function.integerClass),
        new Convolve3x3(),
        new And(),
        new Or(),
        new XOr(),
        new Not(),
        new Nop(),
        new Loop(),
        new True(),
        new False(),
        new Implies(),
        new TwoThings(),
        new If(Function.booleanClass),
        new Equals(Function.booleanClass),
		ADF.ADFs[3]
      },
      {
        vars[0],
        vars[1],
        vars[2],
        vars[3],
        vars[4],
        vars[5],
        vars[6],
        vars[7],
        new Add(Matrix3x3.matrixClass),
        new Subtract(Matrix3x3.matrixClass),
        new Add(MatrixNxN.matrixClass),
        new Subtract(MatrixNxN.matrixClass),
		new Add(Function.integerClass),
		new Subtract(Function.integerClass),
		new One(Function.integerClass),
		new And(),
        new Or(),
        new XOr(),
        new Not(),
        new Nop(),
        new Loop(),
        new True(),
        new False(),
        new Implies(),
        new TwoThings(),
        new If(Function.booleanClass),
        new Equals(Function.booleanClass),
        new Convolve3x3()
      }

    };

		int[][] f1 = {{0,0,0},{0,0,0},{1,1,1}};
		int[][] f2 = {{0,0,0},{1,1,1},{0,0,0}};
		int[][] f3 = {{1,1,1},{0,0,0},{0,0,0}};
		int[][] f4 = {{1,0,0},{1,0,0},{1,0,0}};
		int[][] f5 = {{0,1,0},{0,1,0},{0,1,0}};
		int[][] f6 = {{0,0,1},{0,0,1},{0,0,1}};
		int[][] f0 = {{0,0,0},{0,0,0},{0,0,0}};

		vars[1].set(new Matrix3x3(f1));
		vars[2].set(new Matrix3x3(f2));
		vars[3].set(new Matrix3x3(f3));
		vars[4].set(new Matrix3x3(f4));
		vars[5].set(new Matrix3x3(f5));
		vars[6].set(new Matrix3x3(f6));
		vars[7].set(new Matrix3x3(f0));

    create(individuals, types, argTypes, nodeSets);
  }
  */

	public void output(Chromosome ind) {
		int sum = 0;
		//MatrixNxN result1, result2;
	    Object[][] argVals = new Object[1][3];
   		argVals[0][0] = ((Imager1)getFitnessFunction()).image1;
   		argVals[0][1] = Variable.get("M0");
		argVals[0][2] = new Strings();
		Object result1 = ind.execute_object(argVals[0]);
 		argVals[0][0] = ((Imager1)getFitnessFunction()).image2; 		
 		argVals[0][1] = Variable.get("M1");
		argVals[0][2] = new Strings();
		Object result2 = ind.execute_object(argVals[0]);

		MatrixAccessInterface maf1 = (MatrixAccessInterface)result1;
		MatrixAccessInterface maf2 = (MatrixAccessInterface)result2;
		
		for (int i=0; i<maf1.getData().length; i++) {
			for (int j=0; j<maf1.getData()[0].length; j++)
				System.out.print(maf1.getData()[i][j] + " ");
			System.out.println();
		}
		System.out.println();
		for (int i=0; i<maf2.getData().length; i++) {
			for (int j=0; j<maf2.getData()[0].length; j++)
				System.out.print(maf2.getData()[i][j] + " ");
			System.out.println();
		}
		System.out.println("Chromosome:"+ind);
	}


	public static void main(String[] args) {
	     try {
	            Class type = MatrixNxN.matrixClass;
	            Class[] argTypes = {MatrixNxN.matrixClass, Matrix3x3.matrixClass, Strings.stringClass};     	
	            Class[] nodeType = {MatrixNxN.matrixClass, Matrix2x2.matrixClass, Matrix3x3.matrixClass, Strings.stringClass}; // will pick up integer and boolean
	            ImagerWorld w = new ImagerWorld(args);
	            w.genome = new Genome();
	            w.genome.setArgType(argTypes);
	            w.genome.setChromoType(type);
	            w.genome.setNodeType(nodeType);
	            if(args.length > 0) {
	    			if(args.length == 1 && args[0].equals("compute") || args.length == 4 && args[3].equals("compute") ) {
	    				w.compute(20000, 5000, 7, 128, 2048);
	    			} else {
	    				if(args.length == 1 && args[0].equals("ratchet") || args.length == 4 && args[3].equals("ratchet") ) {
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

import java.lang.*;
import java.util.*;
import java.io.*;

public class strassen2 {

  static int cutoff = 16;

  // The conventional method of multiplying two matrices
  static matrix conventional(matrix a, matrix b, int n) {

    // Answer matrix
    matrix c = new matrix(n);

    // Carries out the math in n^3 time
    for (int i = 0; i < n; i++) {
      for (int k = 0; k < n; k++) {
        for (int j = 0; j < n; j++) {
          int newVal = c.get(i, j) + (a.get(i, k) * b.get(k, j));
          c.edit(i, j, newVal);
        }
      }
    }
    return(c);
  }

  // Adding two matrices together
  static matrix add(matrix a, matrix b) {

    int mDimension = a.getDimension();
    matrix c = new matrix(mDimension);
    for (int row = 0; row < mDimension; row++) {
      for (int col = 0; col < mDimension; col++) {
        c.edit(row, col, a.get(row,col) + b.get(row,col));
      }
    }
    return c;
  }

  // Adding two quadrants together
  static matrix add(matrix a, int quadA, matrix b, int quadB) {

    int mDimension = a.getDimension() / 2;
    matrix c = new matrix(mDimension);
    for (int row = 0; row < mDimension; row++) {
      for (int col = 0; col < mDimension; col++) {
        c.edit(row, col, a.get(quadA, row, col) + b.get(quadB, row, col));
      }
    }
    return c;
  }

  // Adding two matrices together
  static matrix subtract(matrix a, matrix b) {

    int mDimension = a.getDimension();
    matrix c = new matrix(mDimension);
    for (int row = 0; row < mDimension; row++) {
      for (int col = 0; col < mDimension; col++) {
        c.edit(row, col, a.get(row,col) - b.get(row,col));
      }
    }
    return c;
  }

  // Adding two quadrants together
  static matrix subtract(matrix a, int quadA, matrix b, int quadB) {

    int mDimension = a.getDimension() / 2;
    matrix c = new matrix(mDimension);
    for (int row = 0; row < mDimension; row++) {
      for (int col = 0; col < mDimension; col++) {
        c.edit(row, col, a.get(quadA, row, col) - b.get(quadB, row, col));
      }
    }
    return c;
  }

  // Strassen's Algorithm
  static matrix strassMul(matrix a, matrix b, int n) {
    if (n < cutoff) {
      return conventional(a, b, n);
    }

    // Dimension of the submatrices
    int subsize = n / 2;

    matrix P1 = strassMul(a.getQuadrant(1),subtract(b,2, b,4), subsize);
    matrix P2 = strassMul(add(a,1,a,2),b.getQuadrant(4), subsize);
    matrix P3 = strassMul(add(a,3, a,4),b.getQuadrant(1), subsize);
    matrix P4 = strassMul(a.getQuadrant(4), subtract(b,3,b,1), subsize);
    matrix P5 = strassMul(add(a,1, a,4),add(b,1, b,4), subsize);
    matrix P6 = strassMul(subtract(a,2,a,4),add(b,3,b,4), subsize);
    matrix P7 = strassMul(subtract(a,1, a,3),add(b,1, b,2), subsize);

    // Testing the seven products
    // System.out.println("P1: " + P1.get(0,0));
    // System.out.println("P2: " + P2.get(0,0));
    // System.out.println("P3: " + P3.get(0,0));
    // System.out.println("P4: " + P4.get(0,0));
    // System.out.println("P5: " + P5.get(0,0));
    // System.out.println("P6: " + P6.get(0,0));
    // System.out.println("P7: " + P7.get(0,0) + "\n");

    // Answer matrix
    matrix ans = new matrix(a.getDimension());

    // Fills in the answer matrix
    for (int row = 0; row < subsize; row++) {
      for(int col = 0; col < subsize; col++) {
        ans.edit(1, row, col, P5.get(row, col) + P4.get(row, col) - P2.get(row, col) + P6.get(row, col));
        ans.edit(2, row, col, P1.get(row, col) + P2.get(row, col));
        ans.edit(3, row, col, P3.get(row, col) + P4.get(row, col));
        ans.edit(4, row, col, P5.get(row, col) + P1.get(row, col) - P3.get(row, col) - P7.get(row, col));
      }
    }
    return ans;
  }

  public static void main(String[] args) {

    // Checks the flags
    if(args.length != 3) {
      System.out.println("Output should be of the form 'java strassen 0 <dimension> <inputfile>'");
      return;
    }

    // Loads flags into memory
    int n = Integer.parseInt(args[1], 10);
    String filename = args[2];

    // Creates the two matrices
    int[] arr1 = new int[n * n];
    int[] arr2 = new int[n * n];
    BufferedReader in = null;

    // Reading the inputfile
    try {
      int num;

      // Contains the file
      in = new BufferedReader(new FileReader(filename));

      // Populates our first matrix
      for (int i = 0; i < arr1.length; i++) {
        num = Integer.parseInt(in.readLine(), 10);
        arr1[i] = num;
      }

      // Populates our second matrix
      for (int i = 0; i < arr2.length; i++) {
        num = Integer.parseInt(in.readLine(), 10);
        arr2[i] = num;
      }

      in.close();
    }
    catch (Exception e) {
      System.out.println("Exception occurred reading " + filename);
      e.printStackTrace();
      return;
    }

    matrix m1 = new matrix(n, arr1);
    matrix m2 = new matrix(n, arr2);

    // Input Matrices
    // for (int row = 0; row < n; row++) {
    //   for (int col = 0; col < n; col++) {
    //     System.out.print(m1.get(row, col) + " ");
    //   }
    //   System.out.println();
    // }
    // System.out.println();
    // for (int row = 0; row < n; row++) {
    //   for (int col = 0; col < n; col++) {
    //     System.out.print(m2.get(row, col) + " ");
    //   }
    //   System.out.println();
    // }
    // System.out.println();

    // Testing Conventional
    System.out.println("Conventional:");
    long startTime = System.nanoTime();
    matrix c = conventional(m1, m2, n);
    long endTime = System.nanoTime();
    System.out.println("Time elapsed: " + (endTime-startTime) + " millionth milliseconds");

    // for (int row = 0; row < n; row++) {
    //   for (int col = 0; col < n; col++) {
    //     System.out.print(c.get(row, col) + " ");
    //   }
    //   System.out.println();
    // }

    // Testing Strassen's
    System.out.println("\nStrassen's:");
    startTime = System.nanoTime();
    matrix d = strassMul(m1, m2, n);
    endTime = System.nanoTime();
    System.out.println("Time elapsed: " + (endTime-startTime) + " millionth milliseconds");

    // for (int row = 0; row < n; row++) {
    //   for (int col = 0; col < n; col++) {
    //     System.out.print(d.get(row, col) + " ");
    //   }
    //   System.out.println();
    // }

    // Checks if answers match
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (c.get(row, col) != d.get(row, col)) {
          System.out.println("Incorrect Results!");
          return;
        }
      }
    }

  }
}

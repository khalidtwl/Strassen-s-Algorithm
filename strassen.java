import java.lang.*;
import java.util.*;
import java.io.*;

public class strassen {

  static int cutoff = 1;

  // The conventional method of multiplying two matrices
  static int[] conventional(int[] a, int[] b, int n) {

    // Answer matrix
    int[] c = new int[n * n];

    // Carries out the math in n^3 time
    for (int i = 0; i < n; i++) {
      for (int k = 0; k < n; k++) {
        for (int j = 0; j < n; j++) {
          c[(i * n) + j] += a[(i * n) + k] * b[(k * n) + j];
        }
      }
    }
    return(c);
  }

  // Adding two matrices together
  static int[] add(int[] a, int[] b) {
    int[] c = new int[a.length];
    for (int i = 0; i < a.length; i++) {
      c[i] = a[i] + b[i];
    }
    return c;
  }

  // Subtracting two matrices
  static int[] subtract(int[] a, int[] b) {
    int[] c = new int[b.length];
    // System.out.println(a.length + " " + b.length);
    for (int i = 0; i < Math.min(a.length, b.length); i++) {
      c[i] = a[i] - b[i];
    }
    return c;
  }

  // Strassen's Algorithm
  static int[] strassMul(int[] a, int[] b, int n) {
    if (n < cutoff) {
      return conventional(a, b, n);
    }

    if (n == 1) {
      int[] c = new int[1];
      c[0] = a[0] * b[0];
      return c;
    }

    // Dimension of the submatrices
    int subsize = n / 2;
    int subsizeSqd = subsize * subsize;

    // Preparing to subdivide the matrices
    int[] A = new int[subsizeSqd];
    int[] B = new int[subsizeSqd];
    int[] C = new int[subsizeSqd];
    int[] D = new int[subsizeSqd];

    int[] E = new int[subsizeSqd];
    int[] F = new int[subsizeSqd];
    int[] G = new int[subsizeSqd];
    int[] H = new int[subsizeSqd];

    // int[] addAns = new int[subsizeSqd];
    // int[] subAns = new int[subsizeSqd];

    // Fills in the submatrices
    for (int i = 0; i < subsize; i++) {
      for (int j = 0; j < subsize; j++) {
        A[(i * subsize) + j] = a[(i * n) + j];
        E[(i * subsize) + j] = b[(i * n) + j];

        B[(i * subsize) + j] = a[(i * n) + (j + subsize)];
        F[(i * subsize) + j] = b[(i * n) + (j + subsize)];

        C[(i * subsize) + j] = a[(i + subsize) * n + j];
        G[(i * subsize) + j] = b[(i + subsize) * n + j];

        D[(i * subsize) + j] = a[(i + subsize) * n + (j + subsize)];
        H[(i * subsize) + j] = b[(i + subsize) * n + (j + subsize)];
      }
    }

    // Testing the subdivisions
    // System.out.print("Testing A[]...");
    // for (int i = 0; i < A.length; i++) {System.out.print(A[i] + " ");}
    // System.out.print("\nTesting B[]...");
    // for (int i = 0; i < B.length; i++) {System.out.print(B[i] + " ");}
    // System.out.print("\nTesting C[]...");
    // for (int i = 0; i < C.length; i++) {System.out.print(C[i] + " ");}
    // System.out.print("\nTesting D[]...");
    // for (int i = 0; i < D.length; i++) {System.out.print(D[i] + " ");}
    // System.out.print("\nTesting E[]...");
    // for (int i = 0; i < E.length; i++) {System.out.print(E[i] + " ");}
    // System.out.print("\nTesting F[]...");
    // for (int i = 0; i < F.length; i++) {System.out.print(F[i] + " ");}
    // System.out.print("\nTesting G[]...");
    // for (int i = 0; i < G.length; i++) {System.out.print(G[i] + " ");}
    // System.out.print("\nTesting H[]...");
    // for (int i = 0; i < H.length; i++) {System.out.print(H[i] + " ");}
    // System.out.println("\n");

    // The Seven Products
    // subAns = subtract(F, H);
    // int[] P1 = strassMul(A, subAns, subsize);
    //
    // addAns = add(A, B);
    // int[] P2 = strassMul(addAns, H, subsize);
    //
    // addAns = add(C, D);
    // int[] P3 = strassMul(addAns, E, subsize);
    //
    // subAns = subtract(G, E);
    // int[] P4 = strassMul(D, subAns, subsize);
    //
    // addAns = add(A, D);
    // subAns = add(E, H);
    // int[] P5 = strassMul(addAns, subAns, subsize);
    //
    // addAns = add(G, H);
    // subAns = subtract(B, D);
    // int[] P6 = strassMul(subAns, addAns, subsize);
    //
    // addAns = add(E, F);
    // subAns = subtract(A, C);
    // int[] P7 = strassMul(subAns, addAns, subsize);

    int[] P7 = strassMul(subtract(A,C),add(E,F), subsize);
    int[] P1 = strassMul(A,subtract(F,H), subsize);
    int[] P3 = strassMul(add(C,D),E, subsize);
    int[] P5 = strassMul(add(A,D),add(E,H), subsize);
    int[] P2 = strassMul(add(A,B),H, subsize);
    int[] P6 = strassMul(subtract(B,D),add(G,H), subsize);
    int[] P4 = strassMul(D, subtract(G,E), subsize);

    // Testing the seven products
    // System.out.println("P1: " + P1[0]);
    // System.out.println("P2: " + P2[0]);
    // System.out.println("P3: " + P3[0]);
    // System.out.println("P4: " + P4[0]);
    // System.out.println("P5: " + P5[0]);
    // System.out.println("P6: " + P6[0]);
    // System.out.println("P7: " + P7[0] + "\n");

    // Creating 4 new answer submatrices
    A = add(subtract(add(P5, P4), P2), P6);
    B = add(P1, P2);
    C = add(P3, P4);
    D = subtract(subtract(add(P5, P1), P3), P7);

    // Testing answer submatrices
    // for (int i = 0; i < A.length; i++){
    //   if (i % subsize ==  subsize-1)
    //     System.out.println(A[i] + "");
    //   else
    //     System.out.print(A[i] + "  ");
    // }
    // System.out.println("");

    int[] c = new int[n*n];

    // Merging them to get an answer matrix
    for (int i = 0; i < subsize; i++) {
      for (int j = 0; j < subsize; j++) {
        c[(i * n) + j] = A[(i * subsize) + j];
        c[(i * n) + (j + subsize)] = B[(i * subsize) + j];
        c[(i + subsize) * n + j] = C[(i * subsize) + j];
        c[(i + subsize) * n + (j + subsize)] = D[(i * subsize) + j];
      }
    }
    return c;
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

    // // Testing addition
    // System.out.println("Adding...");
    // int[] adding = add(arr1, arr2);
    // for (int i = 0; i < adding.length; i++) {
    //   System.out.print(adding[i] + " ");
    // }
    // System.out.println("");
    //
    // // Testing subtraction
    // System.out.println("Subtracting...");
    // int[] subtracting = subtract(arr2, arr1);
    // for (int i = 0; i < subtracting.length; i++) {
    //   System.out.print(subtracting[i] + " ");
    // }
    // System.out.println("");

    // System.out.println("Initial Matrices");
    // for (int i = 0; i < arr1.length; i++){
    //   if (i % n ==  n-1)
    //     System.out.println(arr1[i] + "");
    //   else
    //     System.out.print(arr1[i] + "  ");
    // }
    // System.out.println("");
    //
    // for (int i = 0; i < arr2.length; i++){
    //   if (i % n ==  n-1)
    //     System.out.println(arr2[i] + "");
    //   else
    //     System.out.print(arr2[i] + "  ");
    // }
    // System.out.println("");

    // Testing Conventional
    System.out.println("Conventional:");
    long startTime = System.nanoTime();
    int[] c = conventional(arr1, arr2, n);
    long endTime = System.nanoTime();
    System.out.println("Time elapsed: " + (endTime-startTime) + " millionth milliseconds");

    // for (int i = 0; i < c.length; i++) {
    //   if (i % n ==  n-1)
    //     System.out.println(c[i] + "");
    //   else
    //     System.out.print(c[i] + "\t");
    // }
    // System.out.println("\n");

    // Testing Strassen's
    System.out.println("\nStrassen's:");
    long startTime2 = System.nanoTime();
    int[] d = strassMul(arr1, arr2, n);
    long endTime2 = System.nanoTime();
    System.out.println("Time elapsed: " + (endTime2-startTime2) + " millionth milliseconds");

    for (int i = 0; i < d.length; i++) {
      if (c[i] != d[i])
        System.out.println("Incorrect Results!");
        return;
    }
    System.out.println("");
  }
}

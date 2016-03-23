import java.lang.*;
import java.util.*;

public class strassen {
  static int[] arr1 = {5,9,2,3,1,4,2,14,8,10,11,7,12,3,5,4};
  static int[] arr2 = {5,9,2,3,1,4,2,14,8,10,11,7,12,3,5,4};
  static int cutoff = 2;

  // The conventional method of multiplying two matrices
  static int[] conventional(int[] a, int[] b, int n) {

    // Answer matrix
    int[] c = new int[n * n];

    // Carries out the math in n^3 time
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          c[(i * n) + j] += a[(i * n) + k] * b[(k * n) + j];
        }
      }
    }
    return(c);
  }

  // Adding two matrices together
  static int[] add(int[] a, int[] b) {
    int[] c = new int[a.length];
    for (int i = 0; i < c.length; i++) {
      c[i] = a[i] + b[i];
    }
    return c;
  }

  // Subtracting two matrices
  static int[] subtract(int[] a, int[] b) {
    int[] c = new int[a.length];
    for (int i = 0; i < c.length; i++) {
      c[i] = a[i] - b[i];
    }
    return c;
  }

  // Strassen's Algorithm
  static int[] strassMul(int[] a, int[] b, int n) {
    if (n < cutoff) {
      return conventional(a, b, n);
    }

    // Dimension of the submatrices
    int subsize = n / 2;

    // Preparing to subdivide the matrices
    int[] A = new int[n * n / 4];
    int[] B = new int[n * n / 4];
    int[] C = new int[n * n / 4];
    int[] D = new int[n * n / 4];
    int[] E = new int[n * n / 4];
    int[] F = new int[n * n / 4];
    int[] G = new int[n * n / 4];
    int[] H = new int[n * n / 4];

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
    // for (int i = 0; i < A.length; i++) {
    //   System.out.print(A[i] + " ");
    // }
    //
    // System.out.print("\nTesting B[]...");
    // for (int i = 0; i < B.length; i++) {
    //   System.out.print(B[i] + " ");
    // }
    //
    // System.out.print("\nTesting C[]...");
    // for (int i = 0; i < C.length; i++) {
    //   System.out.print(C[i] + " ");
    // }
    //
    // System.out.print("\nTesting D[]...");
    // for (int i = 0; i < D.length; i++) {
    //   System.out.print(D[i] + " ");
    // }
    //
    // System.out.print("\nTesting E[]...");
    // for (int i = 0; i < E.length; i++) {
    //   System.out.print(E[i] + " ");
    // }
    //
    // System.out.print("\nTesting F[]...");
    // for (int i = 0; i < F.length; i++) {
    //   System.out.print(F[i] + " ");
    // }
    //
    // System.out.print("\nTesting G[]...");
    // for (int i = 0; i < G.length; i++) {
    //   System.out.print(G[i] + " ");
    // }
    //
    // System.out.print("\nTesting H[]...");
    // for (int i = 0; i < H.length; i++) {
    //   System.out.print(H[i] + " ");
    // }
    // System.out.println("");

    // The Seven Products
    int[] P1 = strassMul(A, subtract(F,H), subsize);
    int[] P2 = strassMul(add(A,B), H, subsize);
    int[] P3 = strassMul(add(C,D), E, subsize);
    int[] P4 = strassMul(D, subtract(G,E), subsize);
    int[] P5 = strassMul(add(A,D), add(E,H), subsize);
    int[] P6 = strassMul(subtract(B,D), add(G,H), subsize);
    int[] P7 = strassMul(subtract(A,C), add(E,F), subsize);

    // Testing the seven products
    // System.out.println("P1: " + P1[0]);
    // System.out.println("P2: " + P2[0]);
    // System.out.println("P3: " + P3[0]);
    // System.out.println("P4: " + P4[0]);
    // System.out.println("P5: " + P5[0]);
    // System.out.println("P6: " + P6[0]);
    // System.out.println("P7: " + P7[0]);

    // Creating 4 new answer submatrices
    int[] AEBG = add(subtract(add(P5, P4), P2), P6);
    int[] AFBH = add(P1, P2);
    int[] CEDG = add(P3, P4);
    int[] CFDH = subtract(subtract(add(P5, P1), P3), P7);

    // Testing answer submatrices
    // System.out.println("AEBG: " + AEBG[0]);
    // System.out.println("AFBH: " + AFBH[0]);
    // System.out.println("CEDG: " + CEDG[0]);
    // System.out.println("CFDH: " + CFDH[0]);

    int[] c = new int[n * n];

    // Merging them to get an answer matrix
    for (int i = 0; i < subsize; i++) {
      for (int j = 0; j < subsize; j++) {
        c[(i * n) + j] = AEBG[(i * subsize) + j];
        c[(i * n) + (j + subsize)] = AFBH[(i * subsize) + j];
        c[(i + subsize) * n + j] = CEDG[(i * subsize) + j];
        c[(i + subsize) * n + (j + subsize)] = CFDH[(i * subsize) + j];
      }
    }

    return c;
  }

  public static void main(String[] args) {

    // Checks the flags
    if(args.length != 2) {
      System.out.println("Output should be of the form 'java strassen 0 <dimension>'");
      return;
    }

    int n = Integer.parseInt(args[1], 10);
    System.out.println("Multiplying a " + n + " by " + n + " matrix.");

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

    // Testing Conventional
    System.out.println("Conventional:");
    int[] c = conventional(arr1, arr2, n);
    for (int i = 0; i < c.length; i++) {
      System.out.print(c[i] + " ");
    }
    System.out.println("\n");

    // Testing Strassen's
    System.out.println("Strassen's:");
    int[] d = strassMul(arr1, arr2, n);
    for (int i = 0; i < d.length; i++) {
      System.out.print(d[i] + " ");
    }
    System.out.println("");
  }
}

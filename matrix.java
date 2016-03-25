class matrix {

  private int[] data;
  private int n;

  public matrix(int dimension) {
    data = new int[dimension * dimension];
    n = dimension;
  }

  public matrix(int dimension, int[] input) {
    data = input;
    n = dimension;
  }

  public void edit(int row, int col, int value) {
    data[(row * n) + col] = value;
    return;
  }

  public void edit(int quadrant, int row, int col, int value){
    if (row >= n || col >= n) {
      System.out.println("Error! You can't index this matrix here!");
    }
    switch (quadrant) {
      case 1:
        data[(row * n) + col] = value;
        break;
      case 2:
        data[(row * n) + (col + (n/2))] = value;
        break;
      case 3:
        data[(row + (n/2)) * n + col] = value;
        break;
      case 4:
        data[(row + (n/2)) * n + (col + (n/2))] = value;
        break;
    }
  }

  public int getDimension(){
    return n;
  }

  public matrix getQuadrant(int quadrant) {
    int subsize = n / 2;
    matrix m = new matrix(subsize);
    for (int row = 0; row < subsize; row++){
      for (int col = 0; col < subsize; col++) {
        m.edit(row, col, this.get(quadrant, row, col));
      }
    }
    return m;
  }

  public int get(int row, int col) {
    return data[(row * n) + col];
  }

  public int get(int quadrant, int row, int col){
    switch (quadrant) {
      case 2:
        return data[(row * n) + (col + (n/2))];
      case 3:
        return data[(row + (n/2)) * n + col];
      case 4:
        return data[(row + (n/2)) * n + (col + (n/2))];
      default:
        return data[(row * n) + col];
    }
  }
}

class test{
  // Finding the next power of 2
  static int nextPowerOf2(int n) {
    double num = Math.ceil(Math.log(n) / Math.log(2));
    return (int)(num * num);
  }

  public static void main(String[] args) {
    matrix m = new matrix(4);

    // Entire matrix
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        m.edit(i,j,i+j);
        System.out.print(m.get(i,j) + " ");
      }
      System.out.println("");
    }
    System.out.println();

    m.edit(1, 1, 1, 20);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        System.out.print(m.get(i,j) + " ");
      }
      System.out.println("");
    }
    System.out.println();

    // 1st Quadrant
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        System.out.print(m.get(1, i, j) + " ");
      }
      System.out.println("");
    }
    System.out.println();

    // 2nd Quadrant
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        System.out.print(m.get(2, i, j) + " ");
      }
      System.out.println("");
    }
    System.out.println();

    // 3rd Quadrant
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        System.out.print(m.get(3, i, j) + " ");
      }
      System.out.println("");
    }
    System.out.println();

    // 4th Quadrant
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        System.out.print(m.get(4, i, j) + " ");
      }
      System.out.println("");
    }
    System.out.println();

    m.edit(1, 1, 1, 20);
    System.out.println(m.get(1, 1, 1));

    System.out.println(nextPowerOf2(6));
  }
}

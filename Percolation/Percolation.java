import java.util.Scanner;

public class Percolation {

    private int[] parents;
    private int[] size;
    private boolean[] isOpen;
    private boolean[] isFull;
    private boolean percolates;
    private int n;
    private int numberOfSites = 0;

    /**
    * Creates n-by-n grid with all sites blocked
    * @param n size of grid
    */
    public Percolation(int n) {
        this.n = n;
        parents = new int[n*n+2];
        isOpen = new boolean[n*n+2];
        isFull = new boolean[n*n+2];
        size = new int[n*n+2];
        for (int i = 0; i < n*n+2; i++) {
            parents[i] = i;
            size[i] = 1;
            isOpen[i] = false;
            isFull[i] = false;
        }
        isOpen[0] = true;
        isOpen[n*n+1] = true;
        isFull[0] = true;
        percolates = false;
    }

    /**
    * opens site at row i, column j if it is not already open
    * @param i,j row and column respectively of site to opened
    * @return Nothing.
    */
    public void open(int i, int j) {
        i--;
        j--;
        int index = 1+i*n+j;
        if (!isOpen[index]) numberOfSites++;
        isOpen[index] = true;
        if (i > 0 && isOpen[index-n]) {
            union(index-n, index);
        } else if (i == 0) {
            union(0, index);
        }

        if (j > 0 && isOpen[index-1]) {
            union(index, index-1);
        }
        if (j < n-1 && isOpen[index+1]) {
            union(index, index+1);
        }

        if (i < n-1 && isOpen[index+n]) {
            union(index, index+n);
        } else if (i == n-1 && !percolates) {
            union(index, n*n+1);
        }
    }

    public boolean isOpen(int i, int j) {
        return isOpen[n*(i-1)+(j-1)+1];    
    }
    
    public boolean isFull(int i, int j) {
        return isFull[root((i-1)*n+(j-1)+1)];
    }

    public boolean percolates() {
        if(percolates) return percolates;
        percolates = isConnected(0, n*n+1);
        return isConnected(0, n*n+1);
    }

    private int root(int i) {
        while (i != parents[i]) {
            parents[i] = parents[parents[i]];
            i = parents[i];
        }
        return i;
    }
    private void union(int i, int j) {
        int rooti = root(i);
        int rootj = root(j);
        if (size[rooti] > size[rootj]) {
            parents[rootj] = rooti;
            if (isFull[rootj]) isFull[rooti] = true;
            size[rooti] += size[rootj];
        } else {
            parents[rooti] = rootj;
            if (isFull[rooti]) isFull[rootj] = true; 
            size[rootj] += size[rooti];
        }
    }
    private boolean isConnected(int i, int j) {
        return (root(i) == root(j));
    }

    public static void main(String[] args){
        Percolation p = new Percolation(Integer.parseInt(args[0]));
        Scanner s = new Scanner(System.in);
        while (true) {
            String[] data = s.nextLine().split(" ");
            switch(data[0]) {
                case "open": p.open(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                    break;
                case "isOpen": System.out.println(p.isOpen(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
                    break;
                case "percolates": System.out.println(p.percolates());
                    break;
                case "isFull": System.out.println(p.isFull(Integer.parseInt(data[1]),Integer.parseInt(data[2])));
                    break;
            }
        }
    }
}

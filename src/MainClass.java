import java.util.Random;

/**
 * Created by ilnaz on 17.04.16.
 */

public class MainClass {
    static Random random = new Random();

    public static void main(String[] args) {

        int n = random.nextInt(6);
        int m = random.nextInt(6);

        int[][] mtrx2 = null, mtrx1 = null;
        mtrx1 = generateMatrix(n, m);
        mtrx2 = generateMatrix(m, n);

        if(n==0||m==0) return;
        int[][] mtr = matrMult(mtrx1, mtrx2);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < mtr[i].length; j++) {
                System.out.print(mtr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

    }

    private static int endThreadsCount = 0;

    /**
     * matrMult - two matrix multiplication method
     * @param m1 - first matrix
     * @param m2 - second matrix
     * @return result of matrix multiplication (also, matrix)
     */
    private static int[][] matrMult(int[][] m1, int[][] m2) {
        int[][] mtrxRes = new int[m1.length][m1.length];
        for (int i = 0; i < m1.length; i++) {

            final int finalI = i;
            Thread t = new Thread(() -> {
                synchronized (mtrxRes) {
                    int mp = 0;
                    for (int j = 0; j < m1.length; j++) {
                        mp = 0;
                        for (int k = 0; k < m2.length; k++) {
                            mp += m1[finalI][k] * m2[k][j];
                        }
                        mtrxRes[finalI][j] = mp;
                    }
                    endThreadsCount++;
                    if (endThreadsCount == m1.length)
                        mtrxRes.notify();
                }
            });
            t.start();
        }

        synchronized (mtrxRes) {
            try {
                mtrxRes.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mtrxRes;
        }
    }

    /**
     * method for generating new matrix
     * @param n - row counts
     * @param m - column counts
     * @return matrix
     */
    private static int[][] generateMatrix(int n, int m) {
        int[][] mtrx = new int[n][];
        for (int i = 0; i < n; i++) {
            mtrx[i] = new int[m];
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mtrx[i][j] = random.nextInt(10);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(mtrx[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        return mtrx;
    }


}

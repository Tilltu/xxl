import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Board {
    enum POINT {
        THREE(3),
        FOUR(4),
        FIVE(5);

        int point;

        POINT(int p) {
            point = p;
        }

        int toInt() {
            return point;
        }
    }

    static final int M = 5;
    static final int N = 5;
    static final int K = 3;
    static int[] B = new int[M * N];   // 降维

    /**
     * Point Gain Pattern
     */
    private static final int H3_1 = 100;
    private static final int H3_2 = 110;
    private static final int H3_3 = 120;
    private static final int H4_1 = 210;
    private static final int H4_2 = 220;
    private static final int H4_3 = 230;
    private static final int H4_4 = 240;
    private static final int H5_1 = 300;
    private static final int H5_2 = 310;
    private static final int H5_3 = 320;
    private static final int H5_4 = 330;
    private static final int H5_5 = 340;

    private static final int V3_1 = 400;
    private static final int V3_2 = 410;
    private static final int V3_3 = 420;
    private static final int V4_1 = 500;
    private static final int V4_2 = 510;
    private static final int V4_3 = 520;
    private static final int V4_4 = 530;
    private static final int V5_1 = 600;
    private static final int V5_2 = 610;
    private static final int V5_3 = 620;
    private static final int V5_4 = 630;
    private static final int V5_5 = 640;

    private static final int NOLINE = 99999;

    static JFrame frame = new JFrame("[BOARD]");
    static VisualBoard vb;

    static {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(370, 390);

        generateBoard();
        printBoard();

        vb = new VisualBoard(B, M, N, K);
        frame.add(vb);

        frame.setVisible(true);
    }

    private static int getEmptyNum() {
        int i, j;
        int counter = 0;

        for (i = 0; i < M * N; i++) {
            if (B[i] == 0) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * @param x
     * @param y
     * @param filler pattern to be filled
     * @return
     */
    private static boolean hasLine(int x, int y, int filler) {
        int i, j;

        boolean flag = false;

        if (getEmptyNum() < 3) {
            return false;
        } else {
            // check three horizontal
            if (y >= 2) {
                if (B[x * N + y - 1] == filler && B[x * N + y - 2] == filler) {
                    flag = true;
                }
            }

            // check three vertical
            if (x >= 2) {
                if (B[(x - 1) * N + y] == filler && B[(x - 2) * N + y] == filler) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    private static void generateBoard() {
        int i, j;
        Random random = new Random();

        for (i = 0; i < M; i++) {
            for (j = 0; j < N; j++) {
                int filler = random.nextInt(K) + 1;
                B[i * N + j] = filler;

                while (hasLine(i, j, filler)) {
                    filler = random.nextInt(K) + 1;
                    B[i * N + j] = filler;
                }
            }
        }
    }

    private static void printBoard() {
        int i, j;

        for (i = 0; i < M; i++) {
            for (j = 0; j < N; j++) {
                if (j == N - 1) {
                    System.out.println(B[i * N + j]);
                } else {
                    System.out.print(B[i * N + j] + " ");
                }
            }
        }
        System.out.println("\n");
    }

    /**
     * @param x
     * @param y
     * @return
     */
    static int checkLine(int x, int y) {
        int color = B[x * N + y];

        int res = NOLINE;

        /*
         check horizontal 5
         */
        if (y + 4 < N) {     // check h 5_1
            if (B[x * N + y + 1] == color &&
                    B[x * N + y + 2] == color &&
                    B[x * N + y + 3] == color &&
                    B[x * N + y + 4] == color) {
                return H5_1;
            }
        }
        if (y + 3 < N && y - 1 >= 0) {     // check h 5_2
            if (B[x * N + y - 1] == color &&
                    B[x * N + y + 1] == color &&
                    B[x * N + y + 2] == color &&
                    B[x * N + y + 3] == color) {
                return H5_2;
            }
        }
        if (y + 2 < N && y - 2 >= 0) {     // check h 5_3
            if (B[x * N + y - 2] == color &&
                    B[x * N + y - 1] == color &&
                    B[x * N + y + 1] == color &&
                    B[x * N + y + 2] == color) {
                return H5_3;
            }
        }
        if (y + 1 < N && y - 3 >= 0) {     // check h 5_4
            if (B[x * N + y - 3] == color &&
                    B[x * N + y - 2] == color &&
                    B[x * N + y - 1] == color &&
                    B[x * N + y + 1] == color) {
                return H5_4;
            }
        }
        if (y - 4 >= 0) {     // check h 5_5
            if (B[x * N + y - 1] == color &&
                    B[x * N + y - 2] == color &&
                    B[x * N + y - 3] == color &&
                    B[x * N + y - 4] == color) {
                return H5_5;
            }
        }

        /*
         check horizontal 4
         */
        if (y + 3 < N) {     // check h 4_1
            if (B[x * N + y + 1] == color &&
                    B[x * N + y + 2] == color &&
                    B[x * N + y + 3] == color) {
                return H4_1;
            }
        }
        if (y + 2 < N && y - 1 >= 0) {     // check h 4_2
            if (B[x * N + y - 1] == color &&
                    B[x * N + y + 1] == color &&
                    B[x * N + y + 2] == color) {
                return H4_2;
            }
        }
        if (y + 1 < N && y - 2 >= 0) {     // check h 4_1
            if (B[x * N + y - 2] == color &&
                    B[x * N + y - 1] == color &&
                    B[x * N + y + 1] == color) {
                return H4_3;
            }
        }
        if (y - 3 >= 0) {     // check h 4_1
            if (B[x * N + y - 3] == color &&
                    B[x * N + y - 2] == color &&
                    B[x * N + y - 1] == color) {
                return H4_4;
            }
        }

        /*
         check horizontal 3
         */
        if (y + 2 < N) {     // check h 3_1
            if (B[x * N + y + 1] == color &&
                    B[x * N + y + 2] == color) {
                return H3_1;
            }
        }
        if (y + 1 < N && y - 1 >= 0) {     // check h 3_2
            if (B[x * N + y - 1] == color &&
                    B[x * N + y + 1] == color) {
                return H3_2;
            }
        }
        if (y - 2 >= 0) {     // check h 3_3
            if (B[x * N + y - 2] == color &&
                    B[x * N + y - 1] == color) {
                return H3_3;
            }
        }

        /*
         check vertical 5
         */
        if (x + 4 < M) {  // check v 5_1
            if (B[(x + 1) * N + y] == color &&
                    B[(x + 2) * N + y] == color &&
                    B[(x + 3) * N + y] == color &&
                    B[(x + 4) * N + y] == color) {
                return V5_1;
            }
        }
        if (x + 3 < M && x - 1 >= 0) {  // check v 5_2
            if (B[(x - 1) * N + y] == color &&
                    B[(x + 1) * N + y] == color &&
                    B[(x + 2) * N + y] == color &&
                    B[(x + 3) * N + y] == color) {
                return V5_2;
            }
        }
        if (x + 2 < M && x - 2 >= 0) {  // check v 5_3
            if (B[(x - 2) * N + y] == color &&
                    B[(x - 1) * N + y] == color &&
                    B[(x + 1) * N + y] == color &&
                    B[(x + 2) * N + y] == color) {
                return V5_3;
            }
        }
        if (x + 1 < M && x - 3 >= 0) {  // check v 5_4
            if (B[(x - 3) * N + y] == color &&
                    B[(x - 2) * N + y] == color &&
                    B[(x - 1) * N + y] == color &&
                    B[(x + 1) * N + y] == color) {
                return V5_4;
            }
        }
        if (x - 4 >= 0) {  // check v 5_5
            if (B[(x - 4) * N + y] == color &&
                    B[(x - 3) * N + y] == color &&
                    B[(x - 2) * N + y] == color &&
                    B[(x - 1) * N + y] == color) {
                return V5_5;
            }
        }

        /*
         check vertical 4
         */
        if (x + 3 < M) {  // check v 4_1
            if (B[(x + 1) * N + y] == color &&
                    B[(x + 2) * N + y] == color &&
                    B[(x + 3) * N + y] == color) {
                return V4_1;
            }
        }
        if (x + 2 < M && x - 1 >= 0) {  // check v 4_2
            if (B[(x - 1) * N + y] == color &&
                    B[(x + 1) * N + y] == color &&
                    B[(x + 2) * N + y] == color) {
                return V4_2;
            }
        }
        if (x + 1 < M && x - 2 >= 0) {  // check v 4_3
            if (B[(x - 2) * N + y] == color &&
                    B[(x - 1) * N + y] == color &&
                    B[(x + 1) * N + y] == color) {
                return V4_3;
            }
        }
        if (x - 3 >= 0) {  // check v 4_4
            if (B[(x - 3) * N + y] == color &&
                    B[(x - 2) * N + y] == color &&
                    B[(x - 1) * N + y] == color) {
                return V4_4;
            }
        }

        /*
         check vertical 3
         */
        if (x + 2 < M) {  // check v 3_1
            if (B[(x + 1) * N + y] == color &&
                    B[(x + 2) * N + y] == color) {
                return V3_1;
            }
        }
        if (x + 1 < M && x - 1 >= 0) {  // check v 3_2
            if (B[(x - 1) * N + y] == color &&
                    B[(x + 1) * N + y] == color) {
                return V3_2;
            }
        }
        if (x - 2 >= 0) {  // check v 3_3
            if (B[(x - 1) * N + y] == color &&
                    B[(x - 2) * N + y] == color) {
                return V3_3;
            }
        }

        return NOLINE;
    }

    /**
     * @param x
     * @param y
     * @param res Lined up result
     * @throws Exception
     */
    static void clear(int x, int y, int res) throws Exception {


        /*if (res != NOLINE) {
            swap(x1, y1, x2, y2);
            Thread.sleep(1500);

            System.out.println();
            printBoard();

            frame.repaint();
        } else {
            swap(x1, y1, x2, y2);
            Thread.sleep(1500);
            System.out.println("[SWAP1]");
            frame.repaint();

            swap(x1, y1, x2, y2);
            Thread.sleep(1500);
            System.out.println("[SWAP2]");
            frame.repaint();
        }*/

        int i, j;  // counter
        switch (res) {
            case H5_1: {
                B[x * N + y] = 0;
                B[x * N + y + 1] = 0;
                B[x * N + y + 2] = 0;
                B[x * N + y + 3] = 0;
                B[x * N + y + 4] = 0;

                for (i = y; i < y + 5; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H5_2: {
                B[x * N + y - 1] = 0;
                B[x * N + y] = 0;
                B[x * N + y + 1] = 0;
                B[x * N + y + 2] = 0;
                B[x * N + y + 3] = 0;

                for (i = y - 1; i < y + 4; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H5_3: {
                B[x * N + y - 2] = 0;
                B[x * N + y - 1] = 0;
                B[x * N + y] = 0;
                B[x * N + y + 1] = 0;
                B[x * N + y + 2] = 0;

                for (i = y - 2; i < y + 3; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H5_4: {
                B[x * N + y - 3] = 0;
                B[x * N + y - 2] = 0;
                B[x * N + y - 1] = 0;
                B[x * N + y] = 0;
                B[x * N + y + 1] = 0;

                for (i = y - 3; i < y + 2; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H5_5: {
                B[x * N + y - 4] = 0;
                B[x * N + y - 3] = 0;
                B[x * N + y - 2] = 0;
                B[x * N + y - 1] = 0;
                B[x * N + y] = 0;

                for (i = y - 4; i < y + 1; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H4_1: {
                B[x * N + y] = 0;
                B[x * N + y + 1] = 0;
                B[x * N + y + 2] = 0;
                B[x * N + y + 3] = 0;

                for (i = y; i < y + 4; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H4_2: {
                B[x * N + y - 1] = 0;
                B[x * N + y] = 0;
                B[x * N + y + 1] = 0;
                B[x * N + y + 2] = 0;

                for (i = y - 1; i < y + 3; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H4_3: {
                B[x * N + y - 2] = 0;
                B[x * N + y - 1] = 0;
                B[x * N + y] = 0;
                B[x * N + y + 1] = 0;

                for (i = y - 2; i < y + 2; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H4_4: {
                B[x * N + y - 3] = 0;
                B[x * N + y - 2] = 0;
                B[x * N + y - 1] = 0;
                B[x * N + y] = 0;

                for (i = y - 3; i < y + 1; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H3_1: {
                B[x * N + y] = 0;
                B[x * N + y + 1] = 0;
                B[x * N + y + 2] = 0;

                for (i = y; i < y + 3; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H3_2: {
                B[x * N + y - 1] = 0;
                B[x * N + y] = 0;
                B[x * N + y + 1] = 0;

                for (i = y - 1; i < y + 2; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case H3_3: {
                B[x * N + y - 2] = 0;
                B[x * N + y - 1] = 0;
                B[x * N + y] = 0;

                for (i = y - 2; i < y + 1; i++) {
                    for (j = 0; (x - j - 1) >= 0; j++) {
                        B[(x - j) * N + i] = B[(x - j - 1) * N + i];
                        B[(x - j - 1) * N + i] = 0;
                    }
                }
                break;
            }
            case V5_1: {
                B[(x) * N + y] = 0;
                B[(x + 1) * N + y] = 0;
                B[(x + 2) * N + y] = 0;
                B[(x + 3) * N + y] = 0;
                B[(x + 4) * N + y] = 0;

                for (i = x + 4; (i - 5) >= 0; i--) {
                    B[i * N + y] = B[(i - 5) * N + y];
                    B[(i - 5) * N + y] = 0;
                }
                break;
            }
            case V5_2: {
                B[(x - 1) * N + y] = 0;
                B[(x) * N + y] = 0;
                B[(x + 1) * N + y] = 0;
                B[(x + 2) * N + y] = 0;
                B[(x + 3) * N + y] = 0;

                for (i = x + 3; (i - 5) >= 0; i--) {
                    B[i * N + y] = B[(i - 5) * N + y];
                    B[(i - 5) * N + y] = 0;
                }
                break;
            }
            case V5_3: {
                B[(x - 2) * N + y] = 0;
                B[(x - 1) * N + y] = 0;
                B[(x) * N + y] = 0;
                B[(x + 1) * N + y] = 0;
                B[(x + 2) * N + y] = 0;

                for (i = x + 2; (i - 5) >= 0; i--) {
                    B[i * N + y] = B[(i - 5) * N + y];
                    B[(i - 5) * N + y] = 0;
                }
                break;
            }
            case V5_4: {
                B[(x - 3) * N + y] = 0;
                B[(x - 2) * N + y] = 0;
                B[(x - 1) * N + y] = 0;
                B[(x) * N + y] = 0;
                B[(x + 1) * N + y] = 0;

                for (i = x + 1; (i - 5) >= 0; i--) {
                    B[i * N + y] = B[(i - 5) * N + y];
                    B[(i - 5) * N + y] = 0;
                }
                break;
            }
            case V5_5: {
                B[(x - 4) * N + y] = 0;
                B[(x - 3) * N + y] = 0;
                B[(x - 2) * N + y] = 0;
                B[(x - 1) * N + y] = 0;
                B[(x) * N + y] = 0;

                for (i = x; (i - 5) >= 0; i--) {
                    B[i * N + y] = B[(i - 5) * N + y];
                    B[(i - 5) * N + y] = 0;
                }
                break;
            }
            case V4_1: {
                B[(x) * N + y] = 0;
                B[(x + 1) * N + y] = 0;
                B[(x + 2) * N + y] = 0;
                B[(x + 3) * N + y] = 0;

                for (i = x + 3; (i - 4) >= 0; i--) {
                    B[i * N + y] = B[(i - 4) * N + y];
                    B[(i - 4) * N + y] = 0;
                }
                break;
            }
            case V4_2: {
                B[(x - 1) * N + y] = 0;
                B[(x) * N + y] = 0;
                B[(x + 1) * N + y] = 0;
                B[(x + 2) * N + y] = 0;

                for (i = x + 2; (i - 4) >= 0; i--) {
                    B[i * N + y] = B[(i - 4) * N + y];
                    B[(i - 4) * N + y] = 0;
                }
                break;
            }
            case V4_3: {
                B[(x - 2) * N + y] = 0;
                B[(x - 1) * N + y] = 0;
                B[(x) * N + y] = 0;
                B[(x + 1) * N + y] = 0;

                for (i = x + 1; (i - 4) >= 0; i--) {
                    B[i * N + y] = B[(i - 4) * N + y];
                    B[(i - 4) * N + y] = 0;
                }
                break;
            }
            case V4_4: {
                B[(x - 3) * N + y] = 0;
                B[(x - 2) * N + y] = 0;
                B[(x - 1) * N + y] = 0;
                B[(x) * N + y] = 0;

                for (i = x; (i - 4) >= 0; i--) {
                    B[i * N + y] = B[(i - 4) * N + y];
                    B[(i - 4) * N + y] = 0;
                }
                break;
            }
            case V3_1: {
                B[(x) * N + y] = 0;
                B[(x + 1) * N + y] = 0;
                B[(x + 2) * N + y] = 0;

                for (i = x + 2; (i - 3) >= 0; i--) {
                    B[i * N + y] = B[(i - 3) * N + y];
                    B[(i - 3) * N + y] = 0;
                }
                break;
            }
            case V3_2: {
                B[(x - 1) * N + y] = 0;
                B[(x) * N + y] = 0;
                B[(x + 1) * N + y] = 0;

                for (i = x + 1; (i - 3) >= 0; i--) {
                    B[i * N + y] = B[(i - 3) * N + y];
                    B[(i - 3) * N + y] = 0;
                }
                break;
            }
            case V3_3: {
                B[(x - 2) * N + y] = 0;
                B[(x - 1) * N + y] = 0;
                B[(x) * N + y] = 0;

                for (i = x; (i - 3) >= 0; i--) {
                    B[i * N + y] = B[(i - 3) * N + y];
                    B[(i - 3) * N + y] = 0;
                }
                break;
            }

            default: {
                break;
            }
        }

    }

    static void swap(int x1, int y1, int x2, int y2) {
        int temp = B[x1 * N + y1];
        B[x1 * N + y1] = B[x2 * N + y2];
        B[x2 * N + y2] = temp;
    }


    /*****************************************************/

    static public void traverse() throws Exception {
        int i, j;

        for (i = 0; i < M; i++) {
            for (j = 0; j < N - 1; j++) {
                if (B[i * N + j] == 0 || B[i * N + j + 1] == 0) {
                    continue;
                }
                swap(i, j, i, j + 1);

                frame.repaint();
                Thread.sleep(1500);
                printBoard();

                int left_res = checkLine(i, j);
                int right_res = checkLine(i, j + 1);

                if (left_res == NOLINE && right_res == NOLINE) {
                    swap(i, j, i, j + 1);

                    frame.repaint();
                    Thread.sleep(1500);
                    printBoard();
                } else {
                    clear(i, j, left_res);
                    clear(i, j + 1, right_res);

                    frame.repaint();
                    Thread.sleep(1500);
                    printBoard();
                }
            }
        }

        JOptionPane.showConfirmDialog(frame, "[Traverse Done!]");

        System.exit(0);
    }

    public static void main(String... args) throws Exception {

        /*for (int i = 0; i < 3; i++) {
            Thread.sleep(2000);

            swap(0, i, 0, i + 1);

            System.out.println();
            printBoard();

            frame.repaint();
        }*/
        traverse();
    }
}


class VisualBoard extends JLabel {
    static int m;
    static int n;
    static int k;
    static int[] b;

    public VisualBoard(@NotNull int[] board, int m, int n, int k) {
        b = board;
        this.m = m;
        this.n = n;
        this.k = k;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int i, j;

        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                int x = (j) * 30 + 30;
                int y = (i) * 30 + 30;
                g.setColor(getColor(b[i * n + j]));
                g.fillOval(x, y, 30, 30);
            }
        }

    }

    static Color getColor(int i) {
        switch (i) {
            case 1: {
                return Color.BLUE;
            }
            case 2: {
                return Color.GREEN;
            }
            case 3: {
                return Color.RED;
            }
            case 4: {
                return Color.YELLOW;
            }
            case 5: {
                return Color.ORANGE;
            }
            default: {
                return Color.WHITE;
            }
        }
    }

}

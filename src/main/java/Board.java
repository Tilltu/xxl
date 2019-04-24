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
    private static final int LH3_1 = 100;
    private static final int LH3_2 = 110;
    private static final int LH3_3 = 120;
    private static final int LH4_1 = 210;
    private static final int LH4_2 = 220;
    private static final int LH4_3 = 230;
    private static final int LH4_4 = 240;
    private static final int LH5_1 = 300;
    private static final int LH5_2 = 310;
    private static final int LH5_3 = 320;
    private static final int LH5_4 = 330;
    private static final int LH5_5 = 340;

    private static final int LV3_1 = 400;
    private static final int LV3_2 = 410;
    private static final int LV3_3 = 420;
    private static final int LV4_1 = 500;
    private static final int LV4_2 = 510;
    private static final int LV4_3 = 520;
    private static final int LV4_4 = 530;
    private static final int LV5_1 = 600;
    private static final int LV5_2 = 610;
    private static final int LV5_3 = 620;
    private static final int LV5_4 = 630;
    private static final int LV5_5 = 640;

    private static final int RH3_1 = 700;
    private static final int RH3_2 = 710;
    private static final int RH3_3 = 720;
    private static final int RH4_1 = 800;
    private static final int RH4_2 = 810;
    private static final int RH4_3 = 820;
    private static final int RH4_4 = 830;
    private static final int RH5_1 = 900;
    private static final int RH5_2 = 910;
    private static final int RH5_3 = 920;
    private static final int RH5_4 = 930;
    private static final int RH5_5 = 940;

    private static final int RV3_1 = 1000;
    private static final int RV3_2 = 1010;
    private static final int RV3_3 = 1020;
    private static final int RV4_1 = 1100;
    private static final int RV4_2 = 1110;
    private static final int RV4_3 = 1120;
    private static final int RV4_4 = 1130;
    private static final int RV5_1 = 1200;
    private static final int RV5_2 = 1210;
    private static final int RV5_3 = 1220;
    private static final int RV5_4 = 1230;
    private static final int RV5_5 = 1240;

    private static final int NOLINE = 9999;

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
                if (B[x * M + y - 1] == filler && B[x * M + y - 2] == filler) {
                    flag = true;
                }
            }

            // check three vertical
            if (x >= 2) {
                if (B[(x - 1) * M + y] == filler && B[(x - 2) * M + y] == filler) {
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
    }

    /**
     * @param x1
     * @param y1 交换前坐标
     * @param x2
     * @param y2 交换后坐标
     * @return
     */
    static int checkLine(int x1, int y1, int x2, int y2) {
        int before_color = B[x1 * N + y1];
        int after_color = B[x2 * N + y2];
        if (before_color == after_color || before_color == 0 || after_color == 0) {
            return NOLINE;
        }

        int counter = 1;

        int res = 0;

        /*
         check horizontal 5
         */
        if (y2 + 4 < N) {     // check 5_1
            if (B[x2 * N + y2 + 1] == before_color &&
                    B[x2 * N + y2 + 2] == before_color &&
                    B[x2 * N + y2 + 3] == before_color &&
                    B[x2 * N + y2 + 4] == before_color) {
                res = RH5_1;
            }
        }
        if (y2 + 3 < N && y2 - 1 >= 0) {     // check 5_2
            if (B[x2 * N + y2 - 1] == before_color &&
                    B[x2 * N + y2 + 1] == before_color &&
                    B[x2 * N + y2 + 2] == before_color &&
                    B[x2 * N + y2 + 3] == before_color) {
                res = RH5_2;
            }
        }
        if (y2 + 2 < N && y2 - 2 >= 0) {     // check 5_3
            if (B[x2 * N + y2 - 2] == before_color &&
                    B[x2 * N + y2 - 1] == before_color &&
                    B[x2 * N + y2 + 1] == before_color &&
                    B[x2 * N + y2 + 2] == before_color) {
                res = RH5_3;
            }
        }
        if (y2 + 1 < N && y2 - 3 >= 0) {     // check 5_4
            if (B[x2 * N + y2 - 3] == before_color &&
                    B[x2 * N + y2 - 2] == before_color &&
                    B[x2 * N + y2 - 1] == before_color &&
                    B[x2 * N + y2 + 1] == before_color) {
                res = RH5_4;
            }
        }
        if (y2 - 4 >= 0) {     // check 5_5
            if (B[x2 * N + y2 - 1] == before_color &&
                    B[x2 * N + y2 - 2] == before_color &&
                    B[x2 * N + y2 - 3] == before_color &&
                    B[x2 * N + y2 - 4] == before_color) {
                res = RH5_5;
            }
        }

        /*
         check horizontal 4
         */
        if (y2 + 3 < N) {     // check 4_1
            if (B[x2 * N + y2 + 1] == before_color &&
                    B[x2 * N + y2 + 2] == before_color &&
                    B[x2 * N + y2 + 3] == before_color) {
                res = RH4_1;
            }
        }
        if (y2 + 2 < N && y2 - 1 >= 0) {     // check 4_2
            if (B[x2 * N + y2 - 1] == before_color &&
                    B[x2 * N + y2 + 1] == before_color &&
                    B[x2 * N + y2 + 2] == before_color) {
                res = RH4_2;
            }
        }
        if (y2 + 1 < N && y2 - 2 >= 0) {     // check 4_1
            if (B[x2 * N + y2 - 2] == before_color &&
                    B[x2 * N + y2 - 1] == before_color &&
                    B[x2 * N + y2 + 1] == before_color) {
                res = RH4_3;
            }
        }
        if (y2 - 3 >= 0) {     // check 4_1
            if (B[x2 * N + y2 - 3] == before_color &&
                    B[x2 * N + y2 - 2] == before_color &&
                    B[x2 * N + y2 - 1] == before_color) {
                res = RH4_4;
            }
        }

        /*
         check horizontal 3
         */
        if (y2 + 2 < N) {     // check 3_1
            if (B[x2 * N + y2 + 1] == before_color &&
                    B[x2 * N + y2 + 2] == before_color) {
                res = RH3_1;
            }
        }
        if (y2 + 1 < N && y2 - 1 >= 0) {     // check 3_2
            if (B[x2 * N + y2 - 1] == before_color &&
                    B[x2 * N + y2 + 1] == before_color) {
                res = RH3_2;
            }
        }
        if (y2 - 2 >= 0) {     // check 3_3
            if (B[x2 * N + y2 - 2] == before_color &&
                    B[x2 * N + y2 - 1] == before_color) {
                res = RH3_3;
            }
        }

        /*
         check vertical 5
         */



        /*
         right 1 horizontal check
         */
        if (y2 + 2 < N) {
            if (B[x2 * N + y2 + 1] == before_color) {
                counter++;
            }
            if (B[x2 * N + y2 + 2] == before_color) {
                counter++;
            }

            if (y2 + 3 < N && counter == 3) {
                if (B[x2 * N + y2 + 3] == before_color) {
                    counter++;
                }
                if (y2 + 4 < N && counter == 4) {
                    if (B[x2 * N + y2 + 4] == before_color) {
                        counter++;
                    }
                }
            }

        }

        if (counter == 3) {
            return RH3_1;
        } else if (counter == 4) {
            return RH4_1;
        } else if (counter == 5) {
            return RH5_1;
        } else {
            counter = 1;
        }

        /*
         right 2 horizontal check
         */
        if (y2 + 1 < N && y2 - 1 >= 0) {
            if (B[x2 * N + y2 - 1] == before_color) {
                counter++;
            }
            if (B[x2 * N + y2 + 1] == before_color) {
                counter++;
            }

            if (y2 + 2 < N && counter == 3) {
                if (B[x2 * N + y2 + 2] == before_color) {
                    counter++;
                }
                if (y2 + 3 < N && counter == 4) {
                    if (B[x2 * N + y2 + 3] == before_color) {
                        counter++;
                    }
                }
            }

        }

        if (counter == 3) {
            return RH3_2;
        } else if (counter == 4) {
            return RH4_2;
        } else if (counter == 5) {
            return RH5_2;
        } else {
            counter = 1;
        }




        /*
         right vertical check
         */
        if (x2 + 2 < M) {
            if (B[(x2 + 1) * N + y2] == before_color) {
                counter++;
            }
            if (B[(x2 + 2) * N + y2] == before_color) {
                counter++;
            }

            if (x2 + 3 < M && counter == 3) {
                if (B[(x2 + 3) * N + y2] == before_color) {
                    counter++;
                }
                if (x2 + 4 < M && counter == 4) {
                    if (B[(x2 + 4) * N + y2] == before_color) {
                        counter++;
                    }
                }
            }

        }

        if (counter == 3) {
            return RV3;
        } else if (counter == 4) {
            return RV4;
        } else if (counter == 5) {
            return RV5;
        } else {
            counter = 1;
        }

        /*
         horizontal check
         */
        if (y1 - 2 >= 0) {
            if (B[x1 * N + y1 - 1] == after_color) {
                counter++;
            }
            if (B[x1 * N + y1 - 2] == after_color) {
                counter++;
            }

            if (y1 - 3 >= 0 && counter == 3) {
                if (B[x1 * N + y1 - 3] == after_color) {
                    counter++;
                }
                if (y1 - 4 >= 0 && counter == 4) {
                    if (B[x1 * N + y1 - 4] == after_color) {
                        counter++;
                    }
                }
            }

        }

        if (counter == 3) {
            return LH3;
        } else if (counter == 4) {
            return LH4;
        } else if (counter == 5) {
            return LH5;
        } else {
            counter = 1;
        }

        /*
         vertical check
         */
        if (x1 - 2 >= 0) {
            if (B[(x1 - 1) * N + y1] == after_color) {
                counter++;
            }
            if (B[(x1 - 2) * N + y1] == after_color) {
                counter++;
            }

            if (x1 - 3 >= 0 && counter == 3) {
                if (B[(x1 - 3) * N + y1] == after_color) {
                    counter++;
                }
                if (x1 - 4 >= 0 && counter == 4) {
                    if (B[(x1 - 4) * N + y1] == after_color) {
                        counter++;
                    }
                }
            }

        }

        if (counter == 3) {
            return LV3;
        } else if (counter == 4) {
            return LV4;
        } else if (counter == 5) {
            return LV5;
        } else {
            return NOLINE;
        }
    }

    /**
     * @param x1
     * @param y1 交换前坐标
     * @param x2
     * @param y2 交换后坐标
     * @return
     */
    static void clear(int x1, int y1, int x2, int y2) throws Exception {
        int res = checkLine(x1, y1, x2, y2);

        if (res != NOLINE) {
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
        }

        int i;  // counter
        switch (res) {
            case RH3: {
                B[x2 * N + y2] = 0;
                B[x2 * N + y2 + 1] = 0;
                B[x2 * N + y2 + 2] = 0;

                for (i = y2 + 2; (i - 3) >= 0; i--) {
                    B[x2 * N + i] = B[x2 * N + i - 3];
                }
                break;
            }
            case RH4: {
                B[x2 * N + y2] = 0;
                B[x2 * N + y2 + 1] = 0;
                B[x2 * N + y2 + 2] = 0;
                B[x2 * N + y2 + 3] = 0;

                for (i = y2 + 3; (i - 4) >= 0; i--) {
                    B[x2 * N + i] = B[x2 * N + i - 4];
                }
                break;
            }
            case RH5: {
                B[x2 * N + y2] = 0;
                B[x2 * N + y2 + 1] = 0;
                B[x2 * N + y2 + 2] = 0;
                B[x2 * N + y2 + 3] = 0;
                B[x2 * N + y2 + 4] = 0;

                for (i = y2 + 4; (i - 5) >= 0; i--) {
                    B[x2 * N + i] = B[x2 * N + i - 5];
                }
                break;
            }
            case RV3: {
                B[(x2) * N + y2] = 0;
                B[(x2 + 1) * N + y2] = 0;
                B[(x2 + 2) * N + y2] = 0;

                for (i = x2 + 2; (i - 3) >= 0; i--) {
                    B[i * N + y2] = B[(i - 3) * N + y2];
                }
                break;
            }
            case RV4: {
                B[(x2) * N + y2] = 0;
                B[(x2 + 1) * N + y2] = 0;
                B[(x2 + 2) * N + y2] = 0;
                B[(x2 + 3) * N + y2] = 0;

                for (i = x2 + 3; (i - 4) >= 0; i--) {
                    B[i * N + y2] = B[(i - 4) * N + y2];
                }
                break;
            }
            case RV5: {
                B[(x2) * N + y2] = 0;
                B[(x2 + 1) * N + y2] = 0;
                B[(x2 + 2) * N + y2] = 0;
                B[(x2 + 3) * N + y2] = 0;
                B[(x2 + 4) * N + y2] = 0;

                for (i = x2 + 4; (i - 5) >= 0; i--) {
                    B[i * N + y2] = B[(i - 5) * N + y2];
                }
                break;
            }
            case LH3: {
                B[x1 * N + y1] = 0;
                B[x1 * N + y1 - 1] = 0;
                B[x1 * N + y1 - 2] = 0;

                for (i = y1 - 2; (i - 3) >= 0; i--) {
                    B[x1 * N + i] = B[x1 * N + i - 3];
                }
                break;
            }
            case LH4: {
                B[x1 * N + y1] = 0;
                B[x1 * N + y1 - 1] = 0;
                B[x1 * N + y1 - 2] = 0;
                B[x1 * N + y1 - 3] = 0;

                for (i = y1 - 3; (i - 4) >= 0; i--) {
                    B[x1 * N + i] = B[x1 * N + i - 4];
                }
                break;
            }
            case LH5: {
                B[x1 * N + y1] = 0;
                B[x1 * N + y1 - 1] = 0;
                B[x1 * N + y1 - 2] = 0;
                B[x1 * N + y1 - 3] = 0;
                B[x1 * N + y1 - 4] = 0;

                for (i = y1 - 4; (i - 5) >= 0; i--) {
                    B[x1 * N + i] = B[x1 * N + i - 5];
                }
                break;
            }
            case LV3: {
                B[(x1) * N + y1] = 0;
                B[(x1 - 1) * N + y1] = 0;
                B[(x1 - 2) * N + y1] = 0;

                for (i = x1 - 2; (i - 3) >= 0; i--) {
                    B[i * N + y1] = B[(i - 3) * N + y1];
                }
                break;
            }
            case LV4: {
                B[(x1) * N + y1] = 0;
                B[(x1 - 1) * N + y1] = 0;
                B[(x1 - 2) * N + y1] = 0;
                B[(x1 - 3) * N + y1] = 0;

                for (i = x1 - 3; (i - 4) >= 0; i--) {
                    B[i * N + y1] = B[(i - 4) * N + y1];
                }
                break;
            }
            case LV5: {
                B[(x1) * N + y1] = 0;
                B[(x1 - 1) * N + y1] = 0;
                B[(x1 - 2) * N + y1] = 0;
                B[(x1 - 3) * N + y1] = 0;
                B[(x1 - 4) * N + y1] = 0;

                for (i = x1 - 4; (i - 5) >= 0; i--) {
                    B[i * N + y1] = B[(i - 5) * N + y1];
                }
                break;
            }
            default: {
                break;
            }
        }
        Thread.sleep(1500);
        frame.repaint();
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
                clear(i, j, i, j + 1);
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

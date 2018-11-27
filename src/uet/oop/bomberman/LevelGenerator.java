package uet.oop.bomberman;

import java.io.*;
import java.util.Random;


public class LevelGenerator {

    private static char[] itemList = new char[]{'b', 'f', 's', 'g', 'd', 'w'};

    public static void generate(int level, int enemy, int item) {
        String filename = "Level" + level + ".txt";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), "utf-8"))) {
            Random random = new Random();
            int height = 30;//13 + (random.nextInt(15));
            int width = 30;//31 + (random.nextInt(15));
            writer.write("" + level + " " + height + " " + width + "\n");
            char[][] board = new char[height][width];
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++)
                    board[i][j] = ' ';
            for (int i = 0; i < height; i++)
                board[i][0] = board[i][width - 1] = '#';
            for (int i = 0; i < width; i++)
                board[0][i] = board[height - 1][i] = '#';
            //Pattern 1
            for (int i = 0; i < height; i += 2) {
                for (int j = 0; j < width; j += 2) {
                    board[i][j] = '#';
                }
            }
            for (int i = 1; i <= enemy; i++) {
                int value = 0;
                /*int randomvalue = random.nextInt(1);
                if (randomvalue < 4)
                    value = 1;
                else if (randomvalue < 7)
                    value = 2;
                else if (randomvalue < 9)
                    value = 3;
                else
                    value = 4;*/
                char k = (char) ('1' + value-1);
                int x = 1 + random.nextInt(height - 2);
                int y = 1 + random.nextInt(width - 2);
                while (board[x][y] != ' ') {
                    x = 1 + random.nextInt(height - 2);
                    y = 1 + random.nextInt(width - 2);
                }
                board[x][y] = k;
            }
            for (int i = 1; i <= item; i++) {
                int value = 0;
                int randomvalue = random.nextInt(9);
                if (randomvalue < 2)
                    value = 0;
                else if (randomvalue < 4)
                    value = 1;
                else if (randomvalue < 6)
                    value = 2;
                else if (randomvalue<7)
                    value = 3;
                else if (randomvalue<8)
                    value = 4;
                else value =5;
                char k = itemList[value];
                int x = 1 + random.nextInt(height - 2);
                int y = 1 + random.nextInt(width - 2);
                while (board[x][y] != ' ') {
                    x = 1 + random.nextInt(height - 2);
                    y = 1 + random.nextInt(width - 2);
                }
                board[x][y] = k;
            }
            int brick = height * width / 3 - item;
            for (int i = 1; i <= brick; i++) {
                char k = '*';
                int x = 1 + random.nextInt(height - 2);
                int y = 1 + random.nextInt(width - 2);
                while (board[x][y] != ' ') {
                    x = 1 + random.nextInt(height - 2);
                    y = 1 + random.nextInt(width - 2);
                }
                board[x][y] = k;
            }
            int x = 1 + random.nextInt(height - 2);
            int y = 1 + random.nextInt(width - 2);
            while (board[x][y] != ' ') {
                x = 1 + random.nextInt(height - 2);
                y = 1 + random.nextInt(width - 2);
            }
            board[x][y] = 'p';
            while (board[x][y] != ' ') {
                x = 1 + random.nextInt(height - 2);
                y = 1 + random.nextInt(width - 2);
            }
            board[x][y] = 'x';
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++)
                    writer.write(board[i][j]);
                writer.write("\n");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generate(1, 1, 7);
    }
}

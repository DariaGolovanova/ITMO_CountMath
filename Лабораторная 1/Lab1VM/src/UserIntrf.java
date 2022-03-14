import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;

public class UserIntrf {

    static String inputType = "";
    static Scanner in = new Scanner(System.in);
    static Matrix matrix = new Matrix();
    static public double[][] content = null;
    static private Random random = new Random(System.currentTimeMillis());

    static {
        System.out.println("Добро пожаловать!");
    }


    static void go() {
        System.out.println("Укажите способ установки данных:  k - клавиатура,     f - файл,     g - сгенерировать данные");
        inputType = in.nextLine();


        switch (inputType) {
            case ("f"): {
                try {
                    System.out.println("Введите путь к файлу...");
                    getDataFromFile(in.nextLine());
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Неверный формат ввода.\nУкажите путь к файлу...");
                }
                break;
            }
            case ("k"): {
                getDataFromKeyboard(-1);
                break;
            }
            case ("g"): {
                getDataFromKeyboard(1);
                break;
            }
            default: {
                System.out.println("Такого ключа не существует");
                go();
            }
        }

        System.out.println("Вы ввели: " + matrix.show());

        System.out.println(matrix.start());


        System.out.println("Желаете продолжить?" +
                "\n y - да" +
                "\n n - нет");
        if (in.nextLine().equals("y")) go();
        else exit(0);

    }

    public static void getDataFromKeyboard(int spKey) {

        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Введите размер матрицы... (Размер матрицы - целое число [1;20])");
                String x = in.next();
                if (Integer.parseInt(x) > 20 || Integer.parseInt(x) < 1) {
                    continue;
                }

                matrix.setSize(Integer.parseInt(x));
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Некорректный формат данных." +
                        "\nРазмер матрицы - целое число [1;20] " + "\n");
            }
        }
        while (true) {
            try {

                System.out.println("Введите точность...");
                String x = in.next();
                if (Double.parseDouble(x) <= 0) {
                    continue;
                }
                ;

                matrix.setAccuracy(Double.parseDouble(x));
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Некорректный формат данных. " +
                        "\nТочность  - число с плавающей точкой ");
            }

        }
        if (spKey == -1) {
            System.out.println("Введитее матрицу построчно, разделяя элементы строки пробелами: ");
            content = new double[matrix.getSize()][matrix.getSize() + 1];
            for (int i = 0; i < matrix.getSize(); i++) {
                for (int j = 0; j < matrix.getSize() + 1; j++)
                    content[i][j] = in.nextDouble();
            }
            matrix.setContent(content);
        } else if (spKey == 1) {
            generateMatrix();
        }
    }

    private static void generateMatrix() {
        double leftBorder = 1;
        double rightBorder = 31;
        content = new double[matrix.getSize()][matrix.getSize() + 1];
        for (int i = 0; i < matrix.getSize(); i++) {
            double lineSum = 0;
            for (int j = 0; j < matrix.getSize() + 1; j++) {
                content[i][j] = random.nextInt(30) - 15 + random.nextDouble();
                lineSum += Math.abs(content[i][j]);
            }
            if (lineSum - Math.abs(content[i][i]) > 0) {
                if (content[i][i] < 0) {
                    leftBorder = lineSum - Math.abs(content[i][i]) - 1;
                    rightBorder = leftBorder - 10;
                }
                content[i][i] = lineSum + (leftBorder + (int) (Math.random() * rightBorder));
            }
            matrix.setContent(content);
        }
    }

    public static void getDataFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            matrix.setAccuracy(Double.parseDouble(br.readLine()));
            matrix.setSize(Integer.parseInt(br.readLine()));
            content = new double[matrix.getSize()][matrix.getSize() + 1];

            String[] matrixLineElements = null;
            System.out.println();
            for (int i = 0; i < matrix.getSize(); i++) {
                matrixLineElements = (br.readLine().trim().split(" "));
                for (int j = 0; j < matrix.getSize() + 1; j++) {
                    content[i][j] = Double.parseDouble(matrixLineElements[j]);
                }
            }
            matrix.setContent(content);
        } catch (IOException ex) {
            System.out.println("Не удается найти указанный файл");
        }
    }
}

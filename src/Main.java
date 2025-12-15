import controller.ShapeController;
import model.*;
import service.CipherService;
import service.HtmlTagCounter;
import service.ShapesFileService;
import service.TextFileAnalyzer;
import view.ShapeView;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Shape[] defaultShapes() {
        return new Shape[]{
                new Rectangle("Red", 4, 5),
                new Circle("Blue", 3),
                new Triangle("Green", 6, 4),
                new Rectangle("Yellow", 2, 8),
                new Circle("Red", 2.5),
                new Triangle("Blue", 10, 3),
                new Rectangle("Green", 7, 3),
                new Circle("Yellow", 4),
                new Triangle("Red", 5, 5),
                new Rectangle("Blue", 1.5, 9)
        };
    }

    private static void shapesMenu(Scanner sc) {
        ShapeView view = new ShapeView();
        ShapesFileService fileService = new ShapesFileService();

        Shape[] shapes = defaultShapes();
        ShapeController controller = new ShapeController(shapes);

        while (true) {
            System.out.println("\n--- TASK 2: SHAPES (MVC + SAVE/LOAD) ---");
            System.out.println("1) Show shapes");
            System.out.println("2) Draw all");
            System.out.println("3) Total area");
            System.out.println("4) Total area of type");
            System.out.println("5) Sort by area");
            System.out.println("6) Sort by color");
            System.out.println("7) Save shapes to file");
            System.out.println("8) Load shapes from file");
            System.out.println("0) Back");

            String choice = sc.nextLine().trim();
            if (choice.equals("0")) return;

            try {
                switch (choice) {
                    case "1" -> {
                        view.printTitle("ALL SHAPES");
                        view.printShapes(controller.getShapes());
                    }
                    case "2" -> {
                        view.printTitle("DRAW ALL");
                        for (Shape s : controller.getShapes()) s.draw();
                    }
                    case "3" -> {
                        view.printTitle("TOTAL AREA");
                        view.printMessage(String.format("%.2f", controller.totalArea()));
                    }
                    case "4" -> {
                        System.out.println("Enter type: circle / rectangle / triangle");
                        String t = sc.nextLine().trim().toLowerCase();
                        Class<? extends Shape> cls = switch (t) {
                            case "circle" -> Circle.class;
                            case "rectangle" -> Rectangle.class;
                            case "triangle" -> Triangle.class;
                            default -> throw new IllegalArgumentException("Unknown type");
                        };
                        view.printTitle("TOTAL AREA OF " + t.toUpperCase());
                        view.printMessage(String.format("%.2f", controller.totalAreaByClass(cls)));
                    }
                    case "5" -> {
                        view.printTitle("SORTED BY AREA");
                        view.printShapes(controller.sortedCopy(new ShapeAreaComparator()));
                    }
                    case "6" -> {
                        view.printTitle("SORTED BY COLOR");
                        view.printShapes(controller.sortedCopy(new ShapeColorComparator()));
                    }
                    case "7" -> {
                        System.out.println("Enter full file path to save (example: C:\\temp\\shapes.dat):");
                        String path = sc.nextLine().trim();
                        fileService.save(controller.getShapes(), path);
                        System.out.println("Saved.");
                    }
                    case "8" -> {
                        System.out.println("Enter full file path to load (example: C:\\temp\\shapes.dat):");
                        String path = sc.nextLine().trim();
                        Shape[] loaded = fileService.load(path);
                        controller = new ShapeController(loaded);
                        System.out.println("Loaded. Current set size: " + loaded.length);
                    }
                    default -> System.out.println("Unknown option");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== LAB MENU ===");
            System.out.println("1) Task 1: line with maximum words from file");
            System.out.println("2) Task 2: Shapes MVC + save/load (serialization)");
            System.out.println("3) Task 3: encrypt/decrypt stream (FilterInputStream/FilterReader)");
            System.out.println("4) Task 4: count HTML tags frequency by URL");
            System.out.println("0) Exit");

            String choice = sc.nextLine().trim();
            if (choice.equals("0")) break;

            try {
                switch (choice) {
                    case "1" -> {
                        System.out.println("Enter input text file path:");
                        String path = sc.nextLine().trim();
                        String result = new TextFileAnalyzer().lineWithMaxWords(path);
                        System.out.println("\nLine with max words:");
                        System.out.println(result == null ? "(empty file)" : result);
                    }
                    case "2" -> shapesMenu(sc);
                    case "3" -> {
                        CipherService cipher = new CipherService();
                        System.out.println("1) Encrypt file");
                        System.out.println("2) Decrypt file");
                        String op = sc.nextLine().trim();

                        System.out.println("Enter input file path:");
                        String in = sc.nextLine().trim();
                        System.out.println("Enter output file path:");
                        String out = sc.nextLine().trim();
                        System.out.println("Enter key character (single char):");
                        String keyStr = sc.nextLine();
                        if (keyStr.isEmpty()) throw new IllegalArgumentException("Key is empty");
                        char key = keyStr.charAt(0);

                        if (op.equals("1")) cipher.encrypt(in, out, key);
                        else if (op.equals("2")) cipher.decrypt(in, out, key);
                        else System.out.println("Unknown option");

                        System.out.println("Done.");
                    }
                    case "4" -> {
                        HtmlTagCounter counter = new HtmlTagCounter();
                        System.out.println("Enter URL:");
                        String url = sc.nextLine().trim();

                        Map<String, Integer> tags = counter.countTags(url);

                        System.out.println("\nA) Tags sorted lexicographically:");
                        counter.printLexicographic(tags);

                        System.out.println("\nB) Tags sorted by frequency ascending:");
                        counter.printByFrequency(tags);
                    }
                    default -> System.out.println("Unknown option");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}

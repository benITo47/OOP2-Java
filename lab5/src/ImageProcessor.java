import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.floor;

public class ImageProcessor {
    private BufferedImage testImage = null; //Obrazek do sprawdzenia
    private BufferedImage referenceImage = null; //Szukane elementy
    private int[][] binaryTestArray; //Binarna reprezentacja obrazu testowego - tylko czarne i białe
    private int[][] binaryReferenceArray; //Binarna reprezentacja obrazu szukanego - tylko czarne i białe
    private ArrayList<int[]> magpieCoordinates = new ArrayList<>();


    public final void loadImage(String fileName) {
        try {
            testImage = ImageIO.read(new File(fileName));
            System.out.println("Obraz testowy załadowany pomyślnie.");
        } catch (IOException e) {
            System.out.println("Błąd: Nie można otworzyć pliku " + fileName);
        }
    }


    public final void loadReferenceImage(String fileName) {
        try {
            referenceImage = ImageIO.read(new File(fileName));
            System.out.println("Obraz referencyjny załadowany pomyślnie.");
        } catch (IOException e) {
            System.out.println("Błąd: Nie można otworzyć pliku " + fileName);
        }
    }


    private int[][] imageToBinaryArray(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] binaryArray = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                if (r < 128 && g < 128 && b < 128) {
                    binaryArray[y][x] = 0; //Ustawiamy tylko czarny i bialy, zeby latwiej bylo prowonywac - mamy jeden wymiar tablicy mniej
                } else {
                    binaryArray[y][x] = 1;
                }
            }
        }
        return binaryArray;
    }


    public void getMagpies() {
        if (testImage == null || referenceImage == null) {
            System.out.println("Obraz testowy lub referencyjny nie został załadowany.");
            return;
        }

        binaryTestArray = imageToBinaryArray(testImage);
        binaryReferenceArray = imageToBinaryArray(referenceImage);

        int testHeight = binaryTestArray.length;
        int testWidth = binaryTestArray[0].length;
        int refHeight = binaryReferenceArray.length;
        int refWidth = binaryReferenceArray[0].length;

        for (int y = 0; y <= testHeight - refHeight; y++) {
            for (int x = 0; x <= testWidth - refWidth; x++) {
                if (matchesAt(x, y)) {
                    magpieCoordinates.add(new int[]{x, y});
                }
            }
        }

        System.out.println("Znaleziono " + magpieCoordinates.size() + " srok.");
    }


    private boolean matchesAt(int startX, int startY) {
        /**
         To jest wersja sprawdzenia, która gubiła sroke i program zwracał 10 zamiast 11 matchy . Spowodowane jest to tym, \
         że sprawdza dokładnie wymiary tego obrazka referencyjnego, a kilka srok ma nalozone inne obrazki na siebie ( prawdopodobnie przy wklejaniu mozaiki)
         Dla wiekszosci srok jest to nieszkodliwe bo nachodzi czarne na czarne
         Ale jedna ma uciety ogon

         for (int y = 0; y < binaryReferenceArray.length; y++) {
            for (int x = 0; x < binaryReferenceArray[0].length; x++) {
                if (binaryTestArray[startY + y][startX + x] != binaryReferenceArray[y][x]) {
                    return false;
                }
            }
         }
         return true;
         **/
        /**
         * Dlatego napisałem wersej drugą, z poolingiem;
         */

        int pooling_X = (int) (0.1 * binaryReferenceArray[0].length); // 8 pixeli dla referncynego obrazka
        int pooling_Y = (int) (0.1 * binaryReferenceArray.length); // 6 pixeli dla referencynnego obrazka


        //Czyli sprawdzamy obsza mniejszy o 16 pixeli na X, i 12 pixeli na Y, obszar do sprawdzenia mniejszy!
        for (int y = 0+pooling_Y; y < binaryReferenceArray.length - pooling_Y; y++) {
            for(int x = 0+pooling_X; x < binaryReferenceArray.length - pooling_X; x++) {
                if(binaryTestArray[startY+y][startX+x] != binaryReferenceArray[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }


    public void clearImage() {
        if (binaryTestArray == null) {
            System.out.println("Czysty obraz nie stworzony");
            return;
        }


        int height = binaryTestArray.length;
        int width = binaryTestArray[0].length;
        int[][] clearedArray = new int[height][width];


        for (int[] coords : magpieCoordinates) {
            int x = coords[0];
            int y = coords[1];
            for (int refY = 0; refY < binaryReferenceArray.length; refY++) {
                for (int refX = 0; refX < binaryReferenceArray[0].length; refX++) {
                    if (binaryReferenceArray[refY][refX] == 1) {
                        if (y + refY < height && x + refX < width) {
                            clearedArray[y + refY][x + refX] = 1;
                        }
                    }
                }
            }
        }

        binaryTestArray = clearedArray;
        System.out.println("Obraz zostal wyczyszczony, zostaly tylko sroki.");
    }

    public void displayImage(String outputFileName) {
        if (binaryTestArray == null) {
            System.out.println("Tablica binarna obrazu testowego nie została utworzona.");
            return;
        }

        int height = binaryTestArray.length;
        int width = binaryTestArray[0].length;
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Color white = new Color(255, 255, 255);
        int whiteRGB = white.getRGB();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (binaryTestArray[y][x] == 1) {
                    outputImage.setRGB(x, y, whiteRGB);
                }
            }
        }

        try {
            ImageIO.write(outputImage, "png", new File(outputFileName));
            System.out.println("Obraz został zapisany jako " + outputFileName);
        } catch (IOException e) {
            System.out.println("Błąd przy zapisie obrazu: " + outputFileName);
            e.printStackTrace();
        }
    }


}

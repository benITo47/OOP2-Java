
public class Main {
    public static void main(String[] args) {
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.loadImage("testImage.tif");
        imageProcessor.loadReferenceImage("refImage.tif");


        imageProcessor.getMagpies();
        imageProcessor.clearImage();
        imageProcessor.displayImage("out.png"); // Ścieżka do zapisu wyniku

    }
}
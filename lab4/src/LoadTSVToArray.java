import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadTSVToArray {

    public String[][] Load(
    ) {
        String filePath = "maze_txt.txt";

        // Use an ArrayList to dynamically store rows (each row is an array of strings)
        ArrayList<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by the tab character
                String[] values = line.split("\t");
                // Add the array of values (a row) to the ArrayList
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert ArrayList to a 2D array
        String[][] dataArray = new String[data.size()][];
        data.toArray(dataArray);

        return dataArray;
    }
}

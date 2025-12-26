import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter; // For formatted output
import java.io.FileNotFoundException; // Specific exception for file not found
import java.io.IOException; // General IO exception
import java.util.Scanner; // Can be used to read files line by line
import java.io.File;
import java.util.Map;
import java.util.HashMap;



public class ParseFile {

  public static String parse_city(String row) {
    int index = 0;
    while (index < row.length() && row.charAt(index) != ';') {
      index++;
    }
    return row.substring(0, index).trim();
  }

  public static Float parse_temp(String row) {
    int start_index = row.indexOf(';');
    if (start_index == -1 || start_index + 1 >= row.length()) {
        throw new NumberFormatException("row missing ';': " + row);
    }
    String temp_str = row.substring(start_index + 1).replaceAll("\\s+", "");
    if (temp_str.isEmpty()) {
        throw new NumberFormatException("empty temperature in row: " + row);
    }
    return Float.parseFloat(temp_str);
  }

  public static void update_city(String city_name, float temp,
                                 Map<String, City> cities) {
    City city = cities.get(city_name);
    if (city != null) {
      city.avg_temp = ((city.avg_temp * city.num_entries) + temp) / (city.num_entries + 1);
      if (temp < city.min_temp) {
        city.min_temp = temp;
      }
      if (temp > city.max_temp) {
        city.max_temp = temp;
      }
      city.num_entries++;
    } else {
      cities.put(city_name, new City(temp));
    }
  }

  public static void read_and_parse_file(File file, Map<String, City> cities) {
    // try-with-resources: Scanner will be closed automatically
    try (Scanner myReader = new Scanner(file)) {
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        if (data.isEmpty()) {
          continue;
        }
        String city_name = parse_city(data);
        float temp = parse_temp(data);
        update_city(city_name, temp, cities);
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static void print_summary(Map<String, City> cities) {
    for (String city_name : cities.keySet()) {
      System.out.println(city_name);
      City city = cities.get(city_name);
      System.out.println(city_name + ", Min Temp =" + city.min_temp);
      System.out.println(city_name + ", Avg Temp =" + city.avg_temp);
      System.out.println(city_name + ", Max Temp =" + city.max_temp);
    }
  }

  public static void main(String[] args) {
    File billion_row_file = new File(args[0]);
    Map<String, City> cities = new HashMap<>();
    read_and_parse_file(billion_row_file, cities);
    print_summary(cities);
  }
}
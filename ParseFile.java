import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException; // Specific exception for file not found
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; // General IO exception
import java.io.PrintWriter; // For formatted output
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner; // Can be used to read files line by line

public class ParseFile {

  public static String parse_city(String row) {
    int index = row.indexOf(';');
    return row.substring(0, index);
  }

  public static Float parse_temp(String row) {
    int start_index = row.indexOf(';');
    if (start_index == -1 || start_index + 1 >= row.length()) {
      throw new NumberFormatException("row missing ';': " + row);
    }
    // test
    return Float.parseFloat(row.substring(start_index + 1));
  }

  public static void update_city(String city_name, float temp,
                                 Map<String, City> cities) {
    City city = cities.get(city_name);
    if (city != null) {
      city.temp_sum += temp;
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
    System.out.print('{');
    Boolean first_loop = true;
    for (String city_name : cities.keySet()) {
      if (!first_loop) {
        System.out.print(", ");
      }
      System.out.print(city_name + "=");
      City city = cities.get(city_name);
      System.out.print(city.min_temp + "/");
      System.out.print((city.temp_sum / city.num_entries) + "/");
      System.out.print(city.max_temp);
      first_loop = false;
    }
    System.out.print("}\n");
  }

  public static void main(String[] args) {
    File billion_row_file = new File(args[0]);
    Map<String, City> cities = new HashMap<>();
    read_and_parse_file(billion_row_file, cities);
    print_summary(cities);
  }
}
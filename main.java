
public class City {
  float min_temp;
  float max_temp;
  float avg_temp;
  int num_entries;

  public City(float temp) {
    this.minTemp = temp;
    this.maxTemp = temp;
    this.avgTemp = temp;
    this.numEntries = 1;
  }
}

public class parse_file {

  public static String parse_city(String row) {
    int index = 0;
    while ((index < row.length()) && (row.charAt(index) != '-') &&
           !(Character.isDigit(row.charAt(index)))) {
      index++;
    }
    return row.substring(0, index).trim();
  }

  public static Float parse_temp(String row) {
    int index = 0;
    while (index < row.length() && row.charAt(index) != '-' &&
           !Character.isDigit(row.charAt(index))) {
      index++;
    }
    int start_index = index;
    while (index < row.length() &&
           (Character.isDigit(row.charAt(index)) || row.charAt(index) == '.')) {
      index++;
    }
    return Float.parseFloat(row.substring(start_index, index));
  }

  public static void update_city(String city_name, float temp,
                                 Map<String, City> cities) {
    City city = cities.get(cityName);
    if (city != null) {
      city.avg = ((city.average * num_entries) + temp) / (num_entries + 1);
      if (temp < city.min) {
        city.min = temp;
      }
      if (temp > city.max) {
        city.max = temp;
      }
      city.num_entries++;
    } else {
      cities.put(city_name, new City(temp));
    }
  }

  public static void read_and_parse_file(File file, Map<String, City> cities) {
    // try-with-resources: Scanner will be closed automatically
    try (Scanner myReader = new Scanner(myObj)) {
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        string city_name = parse_city(row);
        string temp = parse_temp(row);
        update_city(city_name, temp, cities);
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    // what to do with hash map??? do we return anything?
  }

  public static void main(String[] args) {
    File billion_row_file = new File(args[0]);
    Map<String, City> cities = new HashMap<>();
    read_and_parse_file(billion_row_file, cities);
  }
}
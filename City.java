public class City {
  float min_temp;
  float max_temp;
  float temp_sum;
  int num_entries;

  public City(float temp) {
    this.min_temp = temp;
    this.max_temp = temp;
    this.temp_sum = temp;
    this.num_entries = 1;
  }
}

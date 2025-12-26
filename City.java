public class City {
  float min_temp;
  float max_temp;
  float avg_temp;
  int num_entries;

  public City(float temp) {
    this.min_temp = temp;
    this.max_temp = temp;
    this.avg_temp = temp;
    this.num_entries = 1;
    }
}

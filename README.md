# Billion row challenge

I am using [this repo](https://github.com/ClaytonKnittel/1brc) for reference

To compile code, run the following command:

```
javac ParseFile.java City.java
```

To run code, run the following command:

```
java ParseFile test_files/measurements.txt
```

To run timed code, run the following command:

```
time java ParseFile measurements.txt
```

To make a billion line file, run the following command in the 1brc repo:

```
cargo r --release
```

Then, copy it over using:

```
mv measurements.txt ../billion_row_challenge/test_files/
```

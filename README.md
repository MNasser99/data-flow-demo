# CSV Data Flow Web Application.

This project is a data flow demo developed by me for an assessment from Mamun.
The web app takes two csv files, processes them and returns a resulting third csv file. You can either download the result or view it online.

## Installation

Clone the repository from Github.

```bash
git clone https://github.com/MNasser99/data-flow-demo.git
```

## Usage

Step 1: Run Docker Compose inside the project directory to build and run the project image.
```bash
docker-compose up
```

Step 2: Once the project is built, go to localhost port [8080](http://localhost:8080/) to view and use the web app.

## Installation through Docker Hub
This project is also published on Docker Hub as a Docker Image with the name ``` mohhafez/data-flow-demo```. You can install and run it using the following command:

```
docker run -p 8080:8080 mohhafez/data-flow-demo:latest
```

## Functionality
User will be met with a form asking for the following information:

- File 1: The first csv file, used to produce the resulting csv.
- File 2: The second csv file, used to produce the resulting csv.
- Process Lines(n): Determines the number of lines to process in File 1 and File 2. Minimum is 5 and maximum is 100.
- Collation: Has two options:
  - Full: Will produce a csv file with exactly n rows. If one of the input files don't have enough rows, its last rows will be left blank.
  - Normal: Will produce a csv with n rows as long as both files have rows equal to or more than n. Otherwise, it will produce as many rows as the smaller of the two files.
- Download Checkbox: Determines whether the result will be printed on the website or downloaded as a csv.

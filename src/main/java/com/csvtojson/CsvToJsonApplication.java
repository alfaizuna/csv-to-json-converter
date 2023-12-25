package com.csvtojson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CsvToJsonApplication {

	public static void main(String[] args) {
		String csvFilePath = "C:\\alfaizuna/employees.csv";
		String jsonFilePath = "C:\\alfaizuna/output.json";

		try {
			List<String[]> csvData = readCsvFile(csvFilePath);
			String jsonOutput = convertToJson(csvData);

			writeJsonFile(jsonFilePath, jsonOutput);

			System.out.println("Conversion successful!");
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	private static List<String[]> readCsvFile(String filePath) throws IOException, CsvValidationException {
		List<String[]> records = new ArrayList<>();

		try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
			String[] values;
			while ((values = csvReader.readNext()) != null) {
				records.add(values);
			}
		}

		return records;
	}

	private static String convertToJson(List<String[]> csvData) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(csvData);
	}

	private static void writeJsonFile(String filePath, String jsonOutput) throws IOException {
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(jsonOutput);
		}
	}

}

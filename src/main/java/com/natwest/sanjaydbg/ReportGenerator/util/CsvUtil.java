package com.natwest.sanjaydbg.ReportGenerator.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvUtil {

    public static <T> List<T> readCsv(Path path, Class<T> type) {
        try (Reader reader = Files.newBufferedReader(path)) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(type)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV file: " + path, e);
        }
    }

    public static <T> void writeCsv(Path path, List<T> data) {
        try (Writer writer = Files.newBufferedWriter(path)) {
            new StatefulBeanToCsvBuilder<T>(writer)
                    .build()
                    .write(data);
        } catch (Exception e) {
            throw new RuntimeException("Error writing CSV file: " + path, e);
        }
    }
}

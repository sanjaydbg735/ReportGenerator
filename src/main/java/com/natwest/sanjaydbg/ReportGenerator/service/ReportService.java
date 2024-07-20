package com.natwest.sanjaydbg.ReportGenerator.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportService {

    @Value("${input.file.path}")
    private String inputFilePath;

    @Value("${reference.file.path}")
    private String referenceFilePath;

    @Value("${output.file.path}")
    private String outputFilePath;

    public void generateReport() throws Exception {
        try (
                CSVReader inputReader = new CSVReader(new FileReader(inputFilePath));
                CSVReader referenceReader = new CSVReader(new FileReader(referenceFilePath));
                CSVWriter outputWriter = new CSVWriter(new FileWriter(outputFilePath))
        ) {
            List<String[]> inputRecords = inputReader.readAll();
            List<String[]> referenceRecords = referenceReader.readAll();

            // Assuming the first row is the header and skipping it
            String[] outputHeader = {"outfield1", "outfield2", "outfield3", "outfield4", "outfield5"};
            outputWriter.writeNext(outputHeader);

            for (int i = 1; i < inputRecords.size(); i++) {
                String[] inputRecord = inputRecords.get(i);

                for (int j = 1; j < referenceRecords.size(); j++) {
                    String[] referenceRecord = referenceRecords.get(j);

                    if (inputRecord[5].equals(referenceRecord[0]) && inputRecord[6].equals(referenceRecord[2])) {
                        String outfield1 = inputRecord[0] + inputRecord[1];
                        String outfield2 = referenceRecord[1];
                        String outfield3 = referenceRecord[3] + referenceRecord[4];

                        BigDecimal field3 = new BigDecimal(inputRecord[2]);
                        BigDecimal field5 = new BigDecimal(inputRecord[4]);
                        BigDecimal refdata4 = new BigDecimal(referenceRecord[5]);

                        BigDecimal outfield4 = field3.multiply(field5.max(refdata4));
                        BigDecimal outfield5 = field5.max(refdata4);

                        String[] outputRecord = {
                                outfield1,
                                outfield2,
                                outfield3,
                                outfield4.toString(),
                                outfield5.toString()
                        };
                        outputWriter.writeNext(outputRecord);
                        break;
                    }
                }
            }
        }
    }
}

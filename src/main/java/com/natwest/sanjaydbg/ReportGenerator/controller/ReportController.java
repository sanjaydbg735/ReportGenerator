package com.natwest.sanjaydbg.ReportGenerator.controller;

import com.natwest.sanjaydbg.ReportGenerator.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/generate-report")
    public String generateReport() {
        try {
            reportService.generateReport();
            return "Report generated successfully.";
        } catch (Exception e) {
            // Log the exception (you can use a logging framework here)
            e.printStackTrace();
            return "Failed to generate report: " + e.getMessage();
        }
    }
}

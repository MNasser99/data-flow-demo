package com.mohhafez.dataflowdemo.controller;

import com.mohhafez.dataflowdemo.model.CSVModel;
import com.mohhafez.dataflowdemo.model.DataModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import org.json.JSONArray;

@Controller
public class UserController {
    @GetMapping("/")
    public String getHomePage(Model model){
        model.addAttribute("dataRequest", new DataModel());
        return "home_page";
    }

    @PostMapping("/")
    public String dataFlow(@ModelAttribute DataModel dataModel, @RequestParam("files") MultipartFile[] files, ModelMap modelMap, HttpServletResponse response) throws IOException {
        System.out.println("Data Values: " + dataModel);

        List<String> file1Content = new Vector<String>();
        List<String> file2Content = new Vector<String>();

        String file1Substring = "";
        String file2Substring = "";

        Boolean toRecordFile1 = true;
        Boolean toRecordFile2 = true;

        byte[] file1Bytes = files[0].getBytes();
        byte[] file2Bytes = files[1].getBytes();

        int n = dataModel.getProcessLines();


        for(int i = 0; i < file1Bytes.length; i++) {
            byte file1Byte = file1Bytes[i];

            if (file1Byte == 10) { // New Row
                toRecordFile1 = true;
            } else if (file1Byte == 44) {// ,
                toRecordFile1 = false;
                file1Content.add(file1Substring);
                file1Substring = "";

            } else {
                if(toRecordFile1){
                    file1Substring += (char) (file1Byte & 0xFF);
                }
            }
        }

        for(int i = 0; i < file2Bytes.length; i++) {
            byte file2Byte = file2Bytes[i];

            if (file2Byte == 10) { // New Row
                toRecordFile2 = true;
            } else if (file2Byte == 44) {// ,
                toRecordFile2 = false;
                file2Content.add(file2Substring);
                file2Substring = "";

            } else {
                if(toRecordFile2){
                    file2Substring += (char) (file2Byte & 0xFF);
                }
            }
        }

        Collections.reverse(file2Content);

        if(file1Content.size() > n){
            int file1diff = file1Content.size() - n;
            for(int i = 0; i < file1diff; i++){
                file1Content.remove(file1Content.size()-1);
            }
        }
        if(file2Content.size() > n){
            int file2diff = file2Content.size() - n;

            for(int i = 0; i < file2diff; i++){
                file2Content.remove(file2Content.size()-1);
            }
        }

        if(dataModel.getCollation().equals("full")){
            if(file1Content.size() < n){
                int sizeDiff = n - file1Content.size();
                for(int i = 0; i < sizeDiff; i++){
                    file1Content.add(" ");
                }
            }
            if(file2Content.size() < n){
                int sizeDiff = n - file2Content.size();
                for(int i = 0; i < sizeDiff; i++){
                    file2Content.add(" ");
                }
            }
        } else { // normal
            int sizediff = file2Content.size() - file1Content.size();
            if(sizediff < 0){ // file1Content size is bigger
                sizediff = Math.abs(sizediff);

                for(int i = 0; i < sizediff; i++){
                    file1Content.remove(file1Content.size()-1);
                }

            } else if (sizediff > 0){ // file2Content size is bigger
                for(int i = 0; i < sizediff; i++){
                    file2Content.remove(file2Content.size()-1);
                }
            }
        }

        List<CSVModel> csvmodels = new Vector<>();
        int k = 1;
        for(int i = 0; i < file1Content.size(); i++){
            CSVModel csvModel = new CSVModel(k, file2Content.get(i), file1Content.get(i));
            k++;
            csvmodels.add(csvModel);
        }

        if(dataModel.getToDownload() != null){


            response.setContentType("text/csv");

            String headerKey = "Content-Disposition";
            String headerValue = "result.csv";
            response.setHeader(headerKey, headerValue);

            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"Column 1", "Column 2"};
            String[] nameMapping = {"column1", "column2"};
            csvWriter.writeHeader(csvHeader);

            for(CSVModel csvm : csvmodels){
                csvWriter.write(csvm, nameMapping);
            }

            csvWriter.close();
        } else {
            System.out.println("File 1: " + file1Content);
            System.out.println("File 2: " + file2Content);

            System.out.println(csvmodels);

            modelMap.addAttribute("files", csvmodels);
        }
        return "home_page";
    }

}

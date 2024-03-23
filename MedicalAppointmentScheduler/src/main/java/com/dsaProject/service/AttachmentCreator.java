package com.dsaProject.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.dsaProject.entities.Doctor;
import com.dsaProject.entities.Patient;
import com.dsaProject.entities.Schedule;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class AttachmentCreator {
	
	public void createAttachment(Doctor doctor, Patient patient, Schedule schedule) throws DocumentException, IOException {
		
		ClassPathResource resource = new ClassPathResource("static/AcknowledgementFiles/AcknowledgementTemplate.txt");
		InputStream inputStream = resource.getInputStream();
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		Document pdfDocument = new Document();
		String line = "";
		
		String slot = schedule.getSlot()+"";
		String date = slot.substring(0,10);
		String time = slot.substring(11,16);
		String values[] = {patient.getPatientName(), doctor.getName(), doctor.getHospitalName(), date, time, doctor.getName(), doctor.getHospitalName()};
		int i = 0;
		
		
		String fileCopyName = schedule.getScheduleId()+".pdf";
		PdfWriter.getInstance(pdfDocument, new FileOutputStream("src/main/resources/static/AcknowledgementFiles/"+fileCopyName));
		pdfDocument.open();
		
		while((line = bufferedReader.readLine())!=null) {
			String writtenLine = "";
			for(String s : line.split(" ")) {
				if(s.equals("$")) {
					writtenLine += values[i++] +" ";
				}
				else {
					writtenLine += s + " ";
				}				
			}
			Paragraph paragraph = new Paragraph(writtenLine);
			pdfDocument.addTitle("Acknowledgement of Booking Confirmation");
			pdfDocument.add(paragraph);
		}
		pdfDocument.close();
		bufferedReader.close();
	}
}

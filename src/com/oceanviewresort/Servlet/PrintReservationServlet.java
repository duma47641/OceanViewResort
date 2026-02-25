package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.ReservationDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Reservation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.Image;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.BaseColor;

@WebServlet("/printReservation")
public class PrintReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        ReservationDAO dao = DAOFactory.getReservationDAO();
        Reservation res = dao.getReservationById(reservationId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=Reservation_" + reservationId + ".pdf");

        try {

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // ---------- FONTS ----------
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

// ---------- TITLE ----------
            Paragraph title = new Paragraph("OCEAN VIEW RESORT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subtitle = new Paragraph("Reservation Invoice", headerFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

// ---------- RESERVATION INFO TABLE ----------
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingAfter(15);

            infoTable.addCell(getCell("Reservation ID:", boldFont));
            infoTable.addCell(getCell(String.valueOf(res.getId()), normalFont));

            infoTable.addCell(getCell("Guest Full Name:", boldFont));
            infoTable.addCell(getCell(res.getGuestFullName(), normalFont));

            infoTable.addCell(getCell("Guest Contact Number:", boldFont));
            infoTable.addCell(getCell(res.getGuestContactNumber(), normalFont));

            infoTable.addCell(getCell("Checked-In Date:", boldFont));
            infoTable.addCell(getCell(String.valueOf(res.getCheckInDate()), normalFont));

            infoTable.addCell(getCell("Checked-Out Date:", boldFont));
            infoTable.addCell(getCell(String.valueOf(res.getCheckOutDate()), normalFont));

            document.add(infoTable);

// ---------- ROOM IMAGE ----------
            if(res.getRoom().getImage() != null){
                Image roomImg = Image.getInstance(res.getRoom().getImage());
                roomImg.scaleToFit(200,150);
                roomImg.setAlignment(Element.ALIGN_CENTER);
                roomImg.setSpacingAfter(15);
                document.add(roomImg);
            }

// ---------- ROOM DETAILS TABLE ----------
            PdfPTable roomTable = new PdfPTable(4);
            roomTable.setWidthPercentage(100);
            roomTable.setSpacingAfter(15);

            addHeaderCell(roomTable,"Room Name");
            addHeaderCell(roomTable,"Room Type");
            addHeaderCell(roomTable,"Room Price/Night");
            addHeaderCell(roomTable,"Room Status");

            roomTable.addCell(getCell(res.getRoom().getName(), normalFont));
            roomTable.addCell(getCell(res.getRoom().getRoomType().getName(), normalFont));
            roomTable.addCell(getCell(String.valueOf(res.getRoom().getPrice()), normalFont));
            roomTable.addCell(getCell(res.getRoom().getStatus(), normalFont));

            document.add(roomTable);

// ---------- TOTAL BOX ----------
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(40);
            totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell label = new PdfPCell(new Paragraph("TOTAL", boldFont));
            label.setBackgroundColor(BaseColor.LIGHT_GRAY);
            totalTable.addCell(label);

            PdfPCell amount = new PdfPCell(new Paragraph(String.valueOf(res.getTotalAmount()), boldFont));
            amount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalTable.addCell(amount);

            document.add(totalTable);

// ---------- FOOTER ----------
            Paragraph footer = new Paragraph("\nThank you for choosing Ocean View Resort!", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(25);
            document.add(footer);

            document.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private PdfPCell getCell(String text, Font font){
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setPadding(8);
        return cell;
    }

    private void addHeaderCell(PdfPTable table, String text){
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell header = new PdfPCell(new Paragraph(text, headFont));
        header.setBackgroundColor(BaseColor.GRAY);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setPadding(8);
        table.addCell(header);
    }
}
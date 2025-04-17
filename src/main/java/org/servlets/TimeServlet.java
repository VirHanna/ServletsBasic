package org.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    public void init() {
        ParseUTC parseUTC = new ParseUTC();
        System.out.println("Servlet init!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html; charset=utf-8");

        String formattedUTC = (String) request.getAttribute("formattedUTC");
        ZonedDateTime timeWthOffset = ZonedDateTime.now(ZoneOffset.of(formattedUTC));
        String currentTime = timeWthOffset.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss "));

        try {
            response.getWriter().write(currentTime + " UTC " + formattedUTC);
            response.getWriter().close();
        } catch (IOException e) {
            System.out.println("Invalid time: " + e.getMessage());
        }


    }

}
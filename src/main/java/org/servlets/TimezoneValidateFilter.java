package org.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.servlets.ParseUTC.parseUTC;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // Ініціалізація не потрібна, parseUTC — статичний метод
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        String notFormatedUTC = req.getParameter("timezone");
        String formattedUTC;

        try {
            if (notFormatedUTC != null) {
                formattedUTC = parseUTC(notFormatedUTC);
            } else {
                formattedUTC = "+00:00";
            }
            req.setAttribute("formattedUTC", formattedUTC);
            chain.doFilter(req, resp);

        } catch (IllegalArgumentException e) {
            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid UTC format: " + e.getMessage());
        }
    }
}

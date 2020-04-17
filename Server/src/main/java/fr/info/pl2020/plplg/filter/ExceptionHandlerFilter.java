package fr.info.pl2020.plplg.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.info.pl2020.plplg.dto.ErrorResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            System.err.println("ERROR : " + e);

            ErrorResponse error = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur est survenue.");
            ObjectMapper mapper = new ObjectMapper();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(mapper.writeValueAsString(error));
        }
    }
}

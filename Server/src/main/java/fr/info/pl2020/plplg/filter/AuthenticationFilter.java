package fr.info.pl2020.plplg.filter;

import fr.info.pl2020.plplg.security.StudentDetailsService;
import fr.info.pl2020.plplg.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static fr.info.pl2020.plplg.util.FunctionsUtils.isNullOrBlank;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private StudentDetailsService studentDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int studentId = 0;
        try {
            String token = getToken(request);

            if (!isNullOrBlank(token) && jwtTokenProvider.validateToken(token)) {
                studentId = jwtTokenProvider.getUserIdFromToken(token);

                UserDetails userDetails = studentDetailsService.loadUserById(studentId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
        System.out.println("La requête '" + request.getMethod().toUpperCase() + " " + request.getServletPath() + "' a été demandé par " +
                (studentId == 0 ? "un utilisateur non connecté" : "l'utilisateur '" + studentId + "'") +
                "\t\t(Status " + response.getStatus() + ")");
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!isNullOrBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }

        return null;
    }
}

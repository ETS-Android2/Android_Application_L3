package fr.info.pl2020.plplg.filter;

import fr.info.pl2020.plplg.security.JwtTokenProvider;
import fr.info.pl2020.plplg.security.StudentDetailsService;
import org.jboss.logging.Logger;
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

    private static Logger logger = Logger.getLogger(AuthenticationFilter.class);

    @Autowired
    private StudentDetailsService studentDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int studentId = 0;
        try {
            String token = getToken(request);

            if (!isNullOrBlank(token) && this.jwtTokenProvider.validateToken(token)) {
                studentId = this.jwtTokenProvider.getUserIdFromToken(token);

                UserDetails userDetails = this.studentDetailsService.loadUserById(studentId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Impossible de définir l'utilisateur dans le contexte de sécurité.", e);
        }

        filterChain.doFilter(request, response);
        logger.info(String.format("%-80s", "La requête '" + request.getMethod().toUpperCase() + " " + request.getServletPath() + "' a été demandé par " +
                (studentId == 0 ? "un utilisateur non connecté" : "l'utilisateur '" + studentId + "'")) +
                "(Status " + response.getStatus() + ")");
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!isNullOrBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }

        return null;
    }
}

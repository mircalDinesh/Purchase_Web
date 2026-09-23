package cloud9.webapplication.entryrequest;
import cloud9.webapplication.security.JavaWebToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class EntryRequest extends OncePerRequestFilter {

    private final JavaWebToken javaWebToken;

    public EntryRequest(@NonNull JavaWebToken javaWebToken) {
        this.javaWebToken = javaWebToken;
    }

    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, (10 * 1024));
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        String path = request.getRequestURI();

        // TOKEN GENERATION and Only Allow for the Login API
        if(path.equals("/api/auth/authentication/login")) {
            filterChain.doFilter(request, response);
            String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
            if (!body.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(body);
                String username = jsonNode.get("email").asText();
                String password = jsonNode.get("password").asText();
                if (username == null  || password == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    wrappedResponse.copyBodyToResponse();
                }
            }
            wrappedResponse.copyBodyToResponse();
            return;
        }

        // In-valid Token or Exception Api Path 'api/auth/authentication/login'
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String jwt = authHeader.substring(7);
         try {
            if(javaWebToken.validateToken(jwt)) {
                filterChain.doFilter(request, response);
                wrappedResponse.copyBodyToResponse();
            }
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            wrappedResponse.copyBodyToResponse();
         }

    }
}

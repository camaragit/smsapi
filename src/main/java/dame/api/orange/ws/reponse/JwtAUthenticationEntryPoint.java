package dame.api.orange.ws.reponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

@Component
public class JwtAUthenticationEntryPoint implements AuthenticationEntryPoint,Serializable {
    @Override
    public void commence(HttpServletRequest requete, HttpServletResponse reponse, AuthenticationException e) throws IOException, ServletException {

       // reponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Pas autorisé");
        reponse.resetBuffer();
        reponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        reponse.setHeader("Content-Type", "application/json");
        reponse.setCharacterEncoding("UTF-8");
        PrintWriter out = reponse.getWriter();
        out.write( "{\"status\":\"401\",\"code\":\"401\",\"message\":\"Désolé vous n'êtes pas autorisé à acceder à ce service\"}" );
        out.close();

    }
}

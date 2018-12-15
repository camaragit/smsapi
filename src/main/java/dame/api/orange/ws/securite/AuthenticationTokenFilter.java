package dame.api.orange.ws.securite;
import dame.api.orange.ws.utils.JwtTokenUtil;
import dame.api.orange.ws.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService detailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.header}")
    private String tokenHeader;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("je suis bon dans le filter");

        String autToken = request.getHeader(tokenHeader);

        if(autToken!=null && autToken.length() > 7 ){

            autToken = autToken.substring(7);
            System.out.println("Le token est "+autToken);
        }
        String username = jwtTokenUtil.getUsernameFromToken(autToken);
        System.out.println("username dans le filtre est "+username);
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            System.out.println("je suis toujours dans le filter");
            UserDetails userDetails = this.detailsService.loadUserByUsername(username);
            System.out.println("userDetails "+userDetails.getUsername());
            boolean isValid = jwtTokenUtil.validateToken(autToken,userDetails);
            if(isValid){
                Utils.EMAIL = username;
                System.out.println("Validite Ok");
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        filterChain.doFilter(request,response);
    }
}

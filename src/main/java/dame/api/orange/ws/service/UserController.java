package dame.api.orange.ws.service;


import dame.api.orange.ws.entities.MyUserDetails;
import dame.api.orange.ws.entities.User;
import dame.api.orange.ws.reponse.ApiSucessReponse;
import dame.api.orange.ws.reponse.CustomException;
import dame.api.orange.ws.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${jwt.header}")
    private String tokenHeader;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @GetMapping(value = "/users")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiSucessReponse> getAllUsers() throws CustomException {
        List<User> users = userService.findAll();
        if(users==null || users.size()<=0)
            throw new CustomException("Aucun utilisateur trouvé!");
        Map<String,Object> map =new HashMap<>();
        map.put("utilisateurs",users);
        ApiSucessReponse reponse = new ApiSucessReponse("Ok",map);

        return new ResponseEntity<ApiSucessReponse>(reponse, HttpStatus.OK);
    }

   //Donne les infos de l'utilisateur connecté
    @GetMapping(value = "/getuser")

  //  @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiSucessReponse> getuser(Principal principal) throws CustomException {
        User user = userService.getUserByEmail(principal.getName());
        if(user==null)
            throw new CustomException("Utilisateur "+principal.getName()+" n'existe pas");
        Map<String,Object> map =new HashMap<>();
        map.put("utilisateurs",user);
        ApiSucessReponse reponse = new ApiSucessReponse("Ok",map);
        return new ResponseEntity<ApiSucessReponse>(reponse, HttpStatus.OK);
    }

    @PostMapping(value = "/inscription")
    public ResponseEntity<ApiSucessReponse> registration(@RequestBody User user) throws CustomException {
        user.setPassword(user.getPassword()==null?"pass":user.getPassword());
        User dbUser = userService.save(user);
        if(dbUser!=null){
            Map<String,Object> userMap = new HashMap<>();
            userMap.put("user",dbUser);

            return new ResponseEntity<ApiSucessReponse>(new ApiSucessReponse("Utilisateur "+dbUser.getPrenom()+" "+dbUser.getNom()+" est crée avec succès",userMap),HttpStatus.OK);
        }
        throw  new CustomException("Inscription échouée");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ApiSucessReponse> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws CustomException {

        if(user.getPassword()==null || user.getLogin()==null)
            throw new CustomException("Login ou de mot de passe indefini!");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(),user.getPassword()));
        final MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        System.out.println(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Date date = jwtTokenUtil.generateExpirationDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm:ss.SSSZ");
        String datexep = dateFormat.format(date);
        response.setHeader("Token",token);
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("token",token);
        objectMap.put("user",userDetails.getUser());
        objectMap.put("expiration",datexep);
        return new ResponseEntity<ApiSucessReponse>(new ApiSucessReponse("Connexion reussie avec succes",objectMap), HttpStatus.OK);


    }

}

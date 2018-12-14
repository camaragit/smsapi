package dame.api.orange.ws.reponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = { javax.validation.ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse constraintViolationException(javax.validation.ConstraintViolationException ex) {
        return new ApiErrorResponse(500, 5001, ex.getMessage());
    }
    @ExceptionHandler(value = { CustomException.class })
   @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse monexeception(CustomException ex) {
        return new ApiErrorResponse(500, 5001, ex.getMessage());
    }
    @ExceptionHandler(value = { Exception.class })
     @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse monexeception(Exception ex) {

        return new ApiErrorResponse(500, 5001,"Exception non gerée");
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse noHandlerFoundException(NoHandlerFoundException ex) {
        System.out.println("Dame===>"+ex.getMessage());
        return new ApiErrorResponse(404, 4041, "End point non reconnu");
    }
    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse autreexception(HttpMessageNotReadableException ex) {
        System.out.println("Dame===>"+ex.getMessage());
        return new ApiErrorResponse(404, 4041, "revoir les parametres fournis");
    }
    @ExceptionHandler(value = { BadCredentialsException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badCredentialsException(BadCredentialsException ex) {
        return new ApiErrorResponse(400, 4041, "Login ou mot de passe incorrect!");
    }
    @ExceptionHandler(value = { DisabledException.class })
    @ResponseStatus(HttpStatus.LOCKED)
    public ApiErrorResponse comptedesactive(DisabledException ex) {
        return new ApiErrorResponse(400, 4041, "Le compte de l'utilisateur est désactivé!");
    }
    @ExceptionHandler(value = { AccessDeniedException.class })
    @ResponseStatus(HttpStatus.LOCKED)
    public ApiErrorResponse accessRefuse(AccessDeniedException ex) {
        return new ApiErrorResponse(400, 4041, "Vous êtes pas autorisé à acceder à ce service!");
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class })
    @ResponseStatus(HttpStatus.LOCKED)
    public ApiErrorResponse notfounduser(UsernameNotFoundException ex) {
        return new ApiErrorResponse(400, 4041, ex.getMessage());
    }

    @ExceptionHandler(value = {org.hibernate.exception.ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse problmcontrainte(org.hibernate.exception.ConstraintViolationException ex) {
        return new ApiErrorResponse(400, 4041, "La requête ne peut être traitée à cause d'une contrainte d'integrite");
    }
    @ExceptionHandler(value = {org.springframework.dao.DataIntegrityViolationException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse problmcontrainte(org.springframework.dao.DataIntegrityViolationException ex) {
        return new ApiErrorResponse(400, 4041, "La requête ne peut être traitée à cause d'une contrainte d'integrite");
    }
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse problformat(HttpMediaTypeNotSupportedException ex) {
        return new ApiErrorResponse(400, 4041, "Le format de la requête n'est pas supportée !");
    }
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse methodeNotSupporte(HttpRequestMethodNotSupportedException ex) {
        return new ApiErrorResponse(400, 4041, "La méthode n'existe pas pour ce endPoind !");
    }
    @ExceptionHandler(value = {NullPointerException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse nullpointeur(NullPointerException ex) {
        return new ApiErrorResponse(400, 4041, "Une des infos necessaires est manquante !");
    }

/*
    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse unknownException(Exception ex) {
        return new ApiErrorResponse(500, 5002, ex.getMessage());
    }
*/


}

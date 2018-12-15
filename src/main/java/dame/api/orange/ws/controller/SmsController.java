package dame.api.orange.ws.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dame.api.orange.ws.entities.HistoriqueSms;
import dame.api.orange.ws.entities.User;
import dame.api.orange.ws.repo.HistoRepo;
import dame.api.orange.ws.reponse.ApiSucessReponse;
import dame.api.orange.ws.reponse.CustomException;
import dame.api.orange.ws.service.UserService;
import dame.api.orange.ws.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

@Controller
public class SmsController {

    @Autowired
    private UserService userService;

    @Autowired
    private HistoRepo histoRepo;
    @PostMapping(value ="sendsms")
    public ResponseEntity<ApiSucessReponse> sendsms(@RequestBody String requete) throws CustomException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode actualObj = mapper.readTree(requete);
            String telephone = actualObj.get("telephone").asText().replaceAll("\\s+", "");
            String message = actualObj.get("message").asText();
            if (!Arrays.asList(Utils.SUFFIX).contains(telephone.substring(0, 2)))
                throw new CustomException("Le numéro de téléphone doit commencer par 77,76;70 ou 78");
            if (telephone.length() != 9)
                throw new CustomException("Le numéro de téléphone est invalide !");
            if(Utils.sendSms(telephone, message)){
                HistoriqueSms historiqueSms = new HistoriqueSms();
                historiqueSms.setNumero(telephone);
                historiqueSms.setSms(message);
                historiqueSms.setDateEnvoi(new Date());
                User user = userService.getUserByEmail("dame");
                historiqueSms.setUser(user);
                histoRepo.save(historiqueSms);
                ApiSucessReponse reponse = new ApiSucessReponse("sms envoyé avec succès", new HashMap<>());
                return new ResponseEntity<ApiSucessReponse>(reponse, HttpStatus.OK);
            }
            else
                throw new CustomException("Impossible d'envoyer l'SMS");




        } catch (IOException e) {

            throw new CustomException("Revoir les données du parametre");
        }


    }
}

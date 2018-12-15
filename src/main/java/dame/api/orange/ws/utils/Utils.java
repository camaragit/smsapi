package dame.api.orange.ws.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
@Component
public class Utils {
    public static final String[] SUFFIX = {"77","76","70","78"};
    public static final String BASEURL ="https://api.orange.com/";
    public static  String EMAIL ="";
    public static final String CLIENTID = "Sir2w4bXgGTIQbnyR2qkXltnPyduie1V";
    public static final String CLIENTSECRET = "GhgmWjZttCJjuikf";
    public static final String TOENCODE = CLIENTID+":"+CLIENTSECRET;
    public static byte[] VALENCODE = Base64.getEncoder().encode(TOENCODE.getBytes());
    public static String HEADERS ="Basic "+new String(VALENCODE);
    public static RestTemplate restTemplate;
    public static String getToken(){
        HttpHeaders headerToken = new HttpHeaders();
        headerToken.add("Authorization",HEADERS);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        String urlToken= Utils.BASEURL+"oauth/v2/token";
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headerToken);
        restTemplate = new RestTemplate();
        try {
        ResponseEntity<String> response = restTemplate.postForEntity( urlToken, request , String.class );
        String result = response.getBody();
        System.out.println(result);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(result);
            String token = actualObj.get("access_token").asText();
            return token;
        }catch (Exception e){
            System.out.println("Message d'erreur "+e.getMessage()+" "+new Object(){}.getClass().getEnclosingMethod().getName());

            return "";
        }

    }
    public static Boolean sendSms(String numtel,String message){
        if(nbreSMSRestant()>0){
            message = StringUtils.stripAccents(message);
            System.out.println("message vaut "+message);
            System.out.println("telephone vaut "+numtel);
            String Token = getToken();
            HttpHeaders headersms = new HttpHeaders();
            headersms.setContentType(MediaType.APPLICATION_JSON);
            headersms.add("Authorization","Bearer "+Token);
            String requestJson ="{\"outboundSMSMessageRequest\":{\"address\": \"tel:+221"+numtel+"\",\"senderAddress\":\"tel:+22100000000\",\"outboundSMSTextMessage\":{\"message\": \""+message+"\"}}}";
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headersms);
            System.out.println(requestJson);
            String urlSendSMS = BASEURL+"smsmessaging/v1/outbound/tel:+22100000000/requests/";
            try {
                ResponseEntity<String> rep = restTemplate.postForEntity(urlSendSMS, entity , String.class );
                System.out.println(rep.getBody());
                System.out.println("STATUT REPONSE "+rep.getStatusCodeValue());

                return rep.getStatusCodeValue()==201;
            }
            catch (Exception e){
                System.out.println("Message d'erreur "+e.getMessage()+" "+new Object(){}.getClass().getEnclosingMethod().getName());
                return false;
            }
        }
        else return false;

    }
    public static int nbreSMSRestant(){
        String Token = getToken();
        String urlRestant = Utils.BASEURL+"sms/admin/v1/contracts";
        System.out.println(urlRestant);
        HttpHeaders headenbsms = new HttpHeaders();
        headenbsms.add("Authorization","Bearer "+Token);
        HttpEntity<String> entitysms = new HttpEntity(headenbsms);
        try {
        ResponseEntity<String> repnb = restTemplate.exchange(urlRestant, HttpMethod.GET, entitysms , String.class );
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(repnb.getBody());

            int nbsms = actualObj.get("partnerContracts").get("contracts").get(0).get("serviceContracts").get(0).get("availableUnits").asInt();
            System.out.println(nbsms);
            return nbsms;
        }catch (Exception e){
            System.out.println("Message d'erreur "+e.getMessage()+" "+new Object(){}.getClass().getEnclosingMethod().getName());
            return 0;
        }
    }


}

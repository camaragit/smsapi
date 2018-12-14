package dame.api.orange.ws.service;

import dame.api.orange.ws.utils.Utils;
import org.springframework.stereotype.Service;

@Service
public class SMSService implements SMSAPI {

    @Override
    public void sendsms(String num, String msg) {
        Utils.sendSms(num,msg);
    }
}

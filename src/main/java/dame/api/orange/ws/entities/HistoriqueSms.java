package dame.api.orange.ws.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriqueSms implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idsms;
    private String sms;
    private String numero;
    private Date dateEnvoi;
    @ManyToOne
    private User user;
}

package dame.api.orange.ws.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity(name = "utilisateur")
@Data
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    @JsonIgnore
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonIgnore
    private boolean active;
    @ManyToMany(fetch=FetchType.EAGER)// fetch=FetchType.EAGER a resole le probleme suivant org.springframework.security.authentication.InternalAuthenticationServiceException: failed to lazily initialize a collection of role:
    @JoinTable(name="USERS_PROFIL")
    private Collection<Profil> roles;
    private String telephone;
    @JsonIgnore
    private Date createdDate;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<HistoriqueSms> historiqueSms;
    public User(){}
}

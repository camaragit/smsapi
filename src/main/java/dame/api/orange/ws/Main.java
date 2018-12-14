package dame.api.orange.ws;
import dame.api.orange.ws.entities.Profil;
import dame.api.orange.ws.entities.User;
import dame.api.orange.ws.repo.ProfilRepository;
import dame.api.orange.ws.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Collection;


@SpringBootApplication
@EnableWebMvc
public class Main implements CommandLineRunner {
	@Autowired
	ProfilRepository profilRepository;
	@Autowired
	UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

	}

	@Bean
	public CorsFilter corsFilter(){
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedHeader("*");
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("POST");
		configuration.addAllowedMethod("PUT");
		configuration.addAllowedMethod("DELETE");
		configuration.addAllowedMethod("GET");
		configuration.addAllowedMethod("OPTIONS");
		source.registerCorsConfiguration("/**",configuration);

		return new CorsFilter(source);
	}

	@Override
	public void run(String... args) throws Exception {
		try {

			System.out.println(StringUtils.stripAccents("éda(èfkjfkjfkjjfjô"));
			Profil role1 = new Profil("ADMIN","role admin");
			Profil role2 = new Profil("USER","role user");

			profilRepository.save(role1);
			profilRepository.save(role2);
			Collection<Profil> rolesUser1 = new ArrayList<>();
			Collection<Profil> rolesUser2 = new ArrayList<>();
			rolesUser1.add(role1);
			rolesUser2.add(role2);

			User user1 = new User();

			user1.setActive(true);
			user1.setLogin("ajit");
			user1.setPassword("ajit@2018");
			user1.setPrenom("AJIT");
			user1.setNom("AJIT");
			user1.setRoles(rolesUser2);

			User user2 = new User();

			user2.setActive(true);
			user2.setLogin("dame");
			user2.setPassword("dame");
			user2.setPrenom("Dame");
			user2.setNom("camara");
			user2.setRoles(rolesUser1);

			userService.save(user1);
			userService.save(user2);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}


	}
}

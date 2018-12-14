package dame.api.orange.ws.service;


import dame.api.orange.ws.entities.User;

import java.util.List;

public interface UserService {
    public User save(User user) ;

    List<User> findAll();

    User getUserByEmail(String name);
}

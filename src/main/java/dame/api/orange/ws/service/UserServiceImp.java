package dame.api.orange.ws.service;


import dame.api.orange.ws.entities.User;
import dame.api.orange.ws.repo.UserRepository;
import dame.api.orange.ws.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
         String password = PasswordUtil.getPassWordHash(user.getPassword());
         user.setPassword(password);
         user.setCreatedDate(new Date());
         return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String name) {
        return userRepository.findByLoginIgnoreCase(name);
    }
}

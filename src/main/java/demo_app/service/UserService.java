package demo_app.service;

import demo_app.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> findAllPaginated(Pageable pageable);

    User findById(Integer id);

    User findByNickname(String nickname);

    User add(User user);

    User update(Integer id, User user);

    void deleteById(Integer id);
}

package demo_app.service;

import demo_app.domain.User;
import demo_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findAllPaginated(Pageable pageable) {
        List<User> usersAll = (List<User>) userRepository.findAll();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<User> usersCurrentList;

        if (usersAll.size() < startItem) {
            usersCurrentList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, usersAll.size());
            usersCurrentList = usersAll.subList(startItem, toIndex);
        }

        return new PageImpl<>(usersCurrentList,
                              PageRequest.of(currentPage, pageSize),
                              usersAll.size());
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, User user) {
        User existingUser = findById(id);

        user.setId(existingUser.getId());

        if (user.getActive() == null) {
            user.setActive(existingUser.getActive());
        }

        if (user.getNickname() == null) {
            user.setNickname(existingUser.getNickname());
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}

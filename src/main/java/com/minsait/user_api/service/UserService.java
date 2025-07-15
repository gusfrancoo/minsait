package com.minsait.user_api.service;


import com.minsait.user_api.model.User;
import com.minsait.user_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User update(Long id, User userDetails) {
        User user = findById(id);
        user.setNome(userDetails.getNome());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

}

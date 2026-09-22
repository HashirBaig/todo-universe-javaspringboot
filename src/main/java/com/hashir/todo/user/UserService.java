package com.hashir.todo.user;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;

    private static final String[] ADJECTIVE = {"swift","calm","brave","clever","happy","lucky","quiet","bright"};
    private static final String[] ANIMALS = {"otter","falcon","panda","fox","koala","lynx","heron","gecko"};
    private static final Random RANDOM = new Random();

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponse createUser() {
        User user = new User();
        user.setUsername(generateUniqueUsername());
        return UserResponse.fromEntity(userRepository.save(user));
    }

    private String pick(String[] list) {
        return list[RANDOM.nextInt(list.length)];
    }

    private String generateUsername() {
        int number = 1000 + RANDOM.nextInt(9000);
        return String.format("%s-%s-%d", pick(ADJECTIVE), pick(ANIMALS), number);
    }

    private String generateUniqueUsername() {
        String username;

        do {
            username = generateUsername();
        } while (userRepository.existsByUsername(username));

        return username;
    }
}
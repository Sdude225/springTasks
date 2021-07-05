package com.example.springTask.seeder;

import com.example.springTask.models.User;
import com.example.springTask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        addUsers();
    }

    private void addUsers() {
        if (userRepository.count() == 0) {
            User user = new User("studentUser", bCryptPasswordEncoder.encode("studentPassword"), true, "STUDENT");
            User user2 = new User("teacherUser", bCryptPasswordEncoder.encode("teacherPassword"), true, "TEACHER");
            User user3 = new User("powerUser", bCryptPasswordEncoder.encode("powerPassword"), true, "POWER_USER");
            userRepository.save(user);
            userRepository.save(user2);
            userRepository.save(user3);
        }
    }
}

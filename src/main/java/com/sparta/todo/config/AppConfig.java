package com.sparta.todo.config;

import com.sparta.todo.entities.Spartan;
import com.sparta.todo.entities.Todo;
import com.sparta.todo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner loadData(SpartanRepository spartanRepo, PasswordEncoder encoder){
        return args -> {
            if (spartanRepo.count() == 0) {
                Spartan trainer = new Spartan("nish@spartaglobal.com", encoder.encode("trainerpass"), "TRAINER", new ArrayList<>());

                Spartan rick = new Spartan("Rick@spartaglobal.com", encoder.encode("rickpass"), "SPARTAN", new ArrayList<>());
                Todo t1 = new Todo("Buy groceries", "Milk, eggs, bread", false, LocalDate.now(), rick);
                Todo t2 = new Todo("Complete project", "Finish the Spring Boot MVC module", true, LocalDate.now().minusDays(2), rick);
                rick.addTodoItem(t1);
                rick.addTodoItem(t2);

                Spartan morty = new Spartan("Morty@spartaglobal.com", encoder.encode("mortypass"), "SPARTAN", new ArrayList<>());
                Todo t3 = new Todo("Call mum", "Check in and say hello", false, LocalDate.now().minusDays(1), morty);
                Todo t4 = new Todo("Workout", "Go for a 30-minute run", true, LocalDate.now().minusWeeks(1), morty);
                morty.addTodoItem(t3);
                morty.addTodoItem(t4);

                spartanRepo.saveAll(List.of(trainer, rick, morty));
            }
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(form -> form.defaultSuccessUrl("/todos/").permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
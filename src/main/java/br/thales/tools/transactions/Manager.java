package br.thales.tools.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"br.thales.tools.transactions.manager.model"} )
@EnableJpaRepositories(basePackages = {"br.thales.tools.transactions.manager.database"})
public class Manager {

    public static void main (String[] args) {
        SpringApplication.run(Manager.class, args);
    }
}

package br.thales.tools.transactions.manager.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String firstName;
    //Nullable
    private String middleName;
    @NonNull
    private String lastName;
    @NonNull
    private String type; // TODO: Change to enum or class
    @NonNull
    private String status; // TODO: Change to enum or class
    //NonNull
    private int version;
    @NonNull
    private Date date;
}

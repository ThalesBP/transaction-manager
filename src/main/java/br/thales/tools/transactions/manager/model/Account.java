package br.thales.tools.transactions.manager.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long userId;
    @NonNull
    private String type; // TODO: Change to enum or class
    //NonNull
    private int version;
    @NonNull
    private String status;
    @NonNull
    private Date date;
}
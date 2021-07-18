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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long fromAccountID;
    @NonNull
    private Long toAccountID;
    @NonNull
    private String type; // TODO: Change to enum or class
    //NonNull
    private float value;
    @NonNull
    private Date date;
}

package br.thales.tools.transactions.manager.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    public enum Type {
        DRAW,
        DEPOSIT,
        TRANSFER,
        INCOME,
        FEES
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long fromAccountId;
    @NonNull
    private Long toAccountId;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Type type;
    //NonNull
    private float value;
    @NonNull
    private Date date;
}

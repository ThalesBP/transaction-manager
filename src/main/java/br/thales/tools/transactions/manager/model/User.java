package br.thales.tools.transactions.manager.model;

import br.thales.tools.transactions.manager.utils.Constants.Status;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public enum Type {
        CUSTOMER,
        BUSINESS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @NonNull
    protected String name;
    @NonNull
    @Enumerated(EnumType.STRING)
    protected Type type;
    @NonNull
    @Enumerated(EnumType.STRING)
    protected Status status;
    //NonNull
    protected int version;
    @NonNull
    protected Date date;
}

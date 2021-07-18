package br.thales.tools.transactions.manager.model;

import br.thales.tools.transactions.manager.utils.Constants.*;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    public enum Type {
        CHECKING,
        SAVING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long userId;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Type type;
    //NonNull
    private int version;
    @NonNull
    private Status status;
    @NonNull
    private Date date;
}

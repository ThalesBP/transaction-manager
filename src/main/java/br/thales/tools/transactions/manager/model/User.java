package br.thales.tools.transactions.manager.model;

import br.thales.tools.transactions.manager.error.ModelException;
import br.thales.tools.transactions.manager.utils.Constants.Status;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static br.thales.tools.transactions.manager.utils.Constants.Strings.*;

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
    private Long id;
    @NonNull
    private String firstName;
    //Nullable
    private String middleName;
    @NonNull
    private String lastName;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Type type;
    @NonNull
    private Status status;
    //NonNull
    private int version;
    @NonNull
    private Date date;

    public static String[] normalizeName(String [] fullName) throws ModelException {
        String[] auxName = new String[3];
        if (fullName.length == 0) {
            throw new ModelException("Name cannot be empty");
        }
        auxName[0] = fullName[0];
        StringBuilder middleName = new StringBuilder(EMPTY);
        for (int i = 1; i < fullName.length - 1; i++) {
            middleName.append(fullName[i]);
            if (i < fullName.length - 2) {
                middleName.append(SPACE);
            }
        }
        auxName[1] = middleName.toString();
        auxName[2] = fullName.length > 1 ? fullName[fullName.length -1] : EMPTY;
        return auxName;
    }

    public void setFullName(String fullName) throws ModelException {
        String[] auxName = normalizeName(fullName.split(SPACE));
        firstName = auxName[0];
        middleName = auxName[1];
        lastName = auxName[2];
    }

    public String getFullName () {
        return firstName + SPACE + middleName + SPACE + lastName;
    }
}

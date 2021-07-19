package br.thales.tools.transactions.manager.model;

import br.thales.tools.transactions.manager.error.ModelException;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import static br.thales.tools.transactions.manager.utils.Constants.Strings.EMPTY;
import static br.thales.tools.transactions.manager.utils.Constants.Strings.SPACE;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    @NonNull
    private String firstName;
    //Nullable
    private String middleName;
    @NonNull
    private String lastName;


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

    @Override
    public void setName(String name) {
        String[] auxName;
        try {
            auxName = normalizeName(name.split(SPACE));
        } catch (ModelException e) {
            System.out.println("Forbbiden format: " + name);
            e.printStackTrace();
            return;
        }
        firstName = auxName[0];
        middleName = auxName[1];
        lastName = auxName[2];
        this.name = name;
    }

    @Override
    public String getName() {
        if (name == null) {
            name = firstName + SPACE + middleName + SPACE + lastName;
        }
        return name;
    }

    public static boolean validateName(@RequestParam String name) {
        String[] fullName = name.split(SPACE);
        return fullName.length > 1;
    }
}

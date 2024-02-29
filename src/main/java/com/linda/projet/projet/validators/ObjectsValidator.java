package com.linda.projet.projet.validators;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.stereotype.Component;
import com.linda.projet.projet.exceptions.ObjectValidationException;

import java.util.Set;
import java.util.stream.Collectors;

// ceci est une classe générique qui permet de valider les objets , je passe en param ou comme type mon objet et le validateur va pouvoir le valider de facon auto
// les classes générqiues permet de creer des classes des interfaces te des méthdeos qui prennent en param différentstypes de données ce qui permet dutiliser
// la memme classe pour plsueirs type d'objets !


// si je veux que le cycle de vie de cette calsse soit gérer par spring je lenote comme spring bean = component = service = repository
@Component
public class ObjectsValidator<T>{

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator(); // lle validator c lui qui va permettre de valider les objets
    public void validate(T objectToValidate) {

        Set<ConstraintViolation<T>> violations =  validator.validate(objectToValidate);

        if (!violations.isEmpty()){
            Set<String> errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());

            // todo raise an exception
            throw new ObjectValidationException(errorMessage, objectToValidate.getClass().getName());

        }
    }
}

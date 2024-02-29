package com.linda.projet.projet.Handlers;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;



@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) // SI JAI PAS private Set<String> validationErrors; la réposne ca sera     private String errorMsg; private String errorSource;

public class ExceptionRepresentation {

    private String errorMsg;
    private String errorSource;
    private Set<String> validationErrors;

}

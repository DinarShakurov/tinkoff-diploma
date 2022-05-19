package ru.tfs.spring.hw.medical_service.csv;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.exceptions.CsvConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;
import ru.tfs.spring.hw.medical_service.dto.VaccinationDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class VaccinationBeanVerifier implements BeanVerifier<VaccinationDto> {

    private final SmartValidator smartValidator;

    @Override
    public boolean verifyBean(VaccinationDto bean) throws CsvConstraintViolationException {
        BindingResult bindingResult = new BeanPropertyBindingResult(bean, "bean");
        smartValidator.validate(bean, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("csv: Vaccination {} can't be processed. Errors - {}", bean,
                    buildErrorMessageFromBindingResult(bindingResult));
            return false;
        }
        return true;
    }

    private String buildErrorMessageFromBindingResult(BindingResult br) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : br.getAllErrors()) {
            String param;
            if (error instanceof FieldError) {
                param = ((FieldError) error).getField();
            } else {
                param = error.getObjectName();
            }
            sb.append("'")
                    .append(param)
                    .append(" (").append(error.getCode()).append(") ")
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append("'; ");
        }
        return sb.toString();
    }
}

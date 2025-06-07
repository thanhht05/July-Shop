package Spring_MVC.JulyShop.service.validator;

import org.springframework.stereotype.Service;

import Spring_MVC.JulyShop.doamin.dto.RegisterDTO;
import Spring_MVC.JulyShop.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {
    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO dto, ConstraintValidatorContext context) {

        if (this.userService.checkExistsByEmail(dto.getEmail())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email already exist")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Confirm password incorrect")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}

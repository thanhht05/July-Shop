package Spring_MVC.JulyShop.doamin.dto;

import Spring_MVC.JulyShop.service.validator.RegisterChecked;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@RegisterChecked
public class RegisterDTO {
    @NotEmpty(message = "Email cannot be empty.")
    private String email;
    @NotEmpty(message = "First name cannot be empty.")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty.")
    private String lastName;
    @NotEmpty(message = "Password cannot be empty.")
    private String password;
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}

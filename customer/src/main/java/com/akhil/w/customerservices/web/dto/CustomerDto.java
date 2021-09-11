package com.akhil.w.customerservices.web.dto;

import com.akhil.w.customerservices.domain.Customer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * akhil - 9/3/2021 - 6:08 PM
 */
@Data
@Slf4j
public class CustomerDto implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

    //TODO: validate firstName, lastName and email values for valid/allowed format
    public Customer toDao() {
        if(null==this.firstName || this.firstName.trim().length()==0) {
            log.error("First Name can not be empty.");
            throw new RuntimeException("First Name can not be empty.");
        }
        if(null==this.lastName || this.lastName.trim().length()==0) {
            log.error("Last Name can not be empty.");
            throw new RuntimeException("Last Name can not be empty.");
        }
        if(null==this.email || this.email.trim().length()==0 || !isValidFormat(email)) {
            log.error("`{}` is not a valid email format.", email);
            throw new RuntimeException("`" +email+ "` is not a valid email format.");
        }
        //TODO: use Builder pattern to build customer object, otherwise we may need multiple constructors
        return new Customer(this.firstName, this.lastName, this.email);
    }

    public static CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.id = customer.getId();
        dto.firstName = customer.getFirstName();
        dto.lastName = customer.getLastName();
        dto.email = customer.getEmail();

        return dto;
    }

    public static boolean isValidFormat(String input) {
        if(input==null || input.trim().isEmpty()) {
            return true;
        }
        String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
                +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input.trim());
        return m.find();
    }

}

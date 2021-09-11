package com.akhil.w.orderservices.web.dto;

import com.akhil.w.orderservices.domain.Order;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * akhil - 9/4/2021 - 4:18 PM
 */
@Data
@Slf4j
public class OrderDto implements Serializable {

    private Long id;
    private Long customerId;
    private String description;
    private String email;
    private String phoneNumber;

    public Order toDao() {
        if(null==this.customerId || this.customerId < 1) {
            log.error("Please provide valid customer id.");
            throw new RuntimeException("Please provide valid customer id.");
        }
        if(null==this.description || this.description.trim().length()==0) {
            log.error("Please provide order description.");
            throw new RuntimeException("Please provide order description.");
        }
        if(null==this.email || this.email.trim().length()==0 || !isValidFormat(email)) {
            log.error("`{}` is not a valid email format.", email);
            throw new RuntimeException("`" +email+ "` is not a valid email format.");
        }
        //TODO: use Builder pattern to build customer object, otherwise we may need multiple constructors
        return new Order(this.customerId, this.description, this.email, this.phoneNumber);
    }

    public static OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.id = order.getId();
        dto.customerId = order.getCustomerId();
        dto.description = order.getDescription();
        dto.email = order.getEmail();
        dto.phoneNumber = order.getPhoneNumber();

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

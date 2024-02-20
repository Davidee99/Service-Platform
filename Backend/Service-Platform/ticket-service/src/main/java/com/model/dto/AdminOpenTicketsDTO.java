package com.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOpenTicketsDTO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7056403936758394443L;

    private String type;
    private String message;
    private String status;
    private String status_error;
    private Integer priority;
}

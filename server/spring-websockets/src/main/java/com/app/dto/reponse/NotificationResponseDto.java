package com.app.dto.reponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto implements Serializable {

    String sender;
    String receiver;
    Double amount;
    OperationType operation;
}


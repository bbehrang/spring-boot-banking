package com.app.dto.request;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ValidListDto<E> implements List<E> {
    @Valid
    @Delegate
    private List<E> list = new ArrayList<>();
}

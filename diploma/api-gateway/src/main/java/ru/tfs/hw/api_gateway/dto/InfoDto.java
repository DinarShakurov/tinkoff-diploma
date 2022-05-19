package ru.tfs.hw.api_gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tfs.hw.api_gateway.common.JsonItem;

@Data
@AllArgsConstructor(staticName = "create")
public class InfoDto {

    public JsonItem person;
    public JsonItem vaccination;
    public JsonItem qr;

}

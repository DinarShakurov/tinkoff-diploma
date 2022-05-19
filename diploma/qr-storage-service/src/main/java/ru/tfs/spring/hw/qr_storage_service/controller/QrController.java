package ru.tfs.spring.hw.qr_storage_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tfs.spring.hw.qr_storage_service.common.JsonItem;
import ru.tfs.spring.hw.qr_storage_service.common.JsonItemFactory;
import ru.tfs.spring.hw.qr_storage_service.dto.QrDto;
import ru.tfs.spring.hw.qr_storage_service.service.QrService;

@RestController
@RequestMapping("/api/public/qr")
@RequiredArgsConstructor
@Slf4j
public class QrController {

    private final JsonItemFactory jsonItemFactory;
    private final QrService qrService;

    @GetMapping("/{document}")
    public JsonItem<QrDto> getQr(@PathVariable String document) {
        return jsonItemFactory.successResult(qrService.getQr(document));
    }

    @GetMapping("/check")
    public JsonItem<Boolean> verifyQr(@RequestParam String code) {
        return jsonItemFactory.successResult(qrService.verifyQr(code));
    }
}

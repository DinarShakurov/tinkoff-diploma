package ru.tfs.hw.api_gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import ru.tfs.hw.api_gateway.common.JsonItem;
import ru.tfs.hw.api_gateway.dto.InfoDto;

import static ru.tfs.hw.api_gateway.common.ResponseFactory.failResult;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InfoController {

    @Qualifier("loadBalancedWebClientBuilder")
    private final WebClient.Builder webClientBuilder;

    // https://www.vinsguru.com/spring-webflux-aggregation/
    @GetMapping("/api/v1/info")
    public Mono<InfoDto> getAggregatedInfo(@RequestParam String document) {
        Mono<JsonItem> person = webClientBuilder.build()
                .get()
                .uri(UriComponentsBuilder
                                .fromUriString("http://person-service/person-service/api/public/person")
                                .queryParam("passport", document)
                                .build().toUriString()
                )
                .retrieve()
                .bodyToMono(JsonItem.class)
                .onErrorResume(t -> {
                    log.error("Error [Person-Service]", t);
                    return Mono.just(failResult(t));
                });

        Mono<JsonItem> medical = webClientBuilder.build()
                .get()
                .uri(UriComponentsBuilder
                        .fromUriString("http://medical-service/medical-service/api/public/vaccination")
                        .queryParam("document", document)
                        .build().toUriString()
                )
                //.uri("http://medical-service/medical-service/api/public/vaccination?document="+document)
                .retrieve()
                .bodyToMono(JsonItem.class)
                .onErrorResume(t -> {
                    log.error("Error [Medical-Service]", t);
                    return Mono.just(failResult(t));
                });

        Mono<JsonItem> qr = webClientBuilder.build()
                .get()
                .uri("http://qr-storage-service/qr-storage-service/api/public/qr/{document}", document)
                .retrieve()
                .bodyToMono(JsonItem.class)
                .onErrorResume(t -> {
                    log.error("Error [QR-Storage-Service]", t);
                    return Mono.just(failResult(t));
                });

       return Mono.zip(person, medical, qr)
                .map(o -> InfoDto.create(o.getT1(), o.getT2(), o.getT3()));
    }
}

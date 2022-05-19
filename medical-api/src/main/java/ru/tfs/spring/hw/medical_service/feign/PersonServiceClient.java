package ru.tfs.spring.hw.medical_service.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tfs.spring.hw.medical_service.common.JsonItem;

import static ru.tfs.spring.hw.medical_service.common.ResponseFactory.failResult;

@FeignClient(name = "person-service", path = "person-service")
@Retry(name = "person-service")
@CircuitBreaker(name = "person-service", fallbackMethod = "defaultFallback")
public interface PersonServiceClient {

    @GetMapping("/api/public/person/verify")
    JsonItem<Boolean> verifyPerson(@RequestParam String name,
                                   @RequestParam String passport);

    static JsonItem<Boolean> defaultFallback(Throwable e) {
        return failResult(e);
    }
}
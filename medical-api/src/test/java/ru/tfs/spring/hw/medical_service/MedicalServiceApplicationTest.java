package ru.tfs.spring.hw.medical_service;

import com.google.common.base.Charsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import ru.tfs.spring.hw.medical_service.common.JsonItem;
import ru.tfs.spring.hw.medical_service.entity.Vaccination;
import ru.tfs.spring.hw.medical_service.entity.VaccinationPoint;
import ru.tfs.spring.hw.medical_service.entity.Vaccine;
import ru.tfs.spring.hw.medical_service.feign.PersonServiceClient;
import ru.tfs.spring.hw.medical_service.repository.VaccinationProcessingRepository;
import ru.tfs.spring.hw.medical_service.repository.VaccinationRepository;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MedicalServiceApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceClient personServiceClient;

    @Autowired
    private VaccinationRepository vaccinationRepository;
    @Autowired
    private VaccinationProcessingRepository vpRepository;

    @AfterEach
    public void afterEach() {
        vpRepository.deleteAllInBatch();
        vaccinationRepository.deleteAllInBatch();
    }

    @Test
    public void processFileTest() throws Exception {
        when(personServiceClient.verifyPerson(any(), any())).thenReturn(new JsonItem<>() {{
            setSuccess(true);
            setData(true);
        }});

        String content = StreamUtils.copyToString(new ClassPathResource("csv/file.csv").getInputStream(), Charsets.UTF_8);

        mockMvc.perform(post("/api/private/vaccination/process-file")
                        .contentType("text/csv")
                        .content(content)
                )
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    public void getVaccinationTest() throws Exception {
        Vaccination vaccination = getVaccination();

        mockMvc.perform(get("/api/public/vaccination")
                .param("document", "1234"))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.vaccinationId").value(vaccination.getVaccinationId()));
    }

    private Vaccination getVaccination() {
        Vaccination vaccination = new Vaccination();
        vaccination.setVaccine(new Vaccine() {{
            setVaccineId(1L);
        }});
        vaccination.setVaccinationPoint(new VaccinationPoint() {{
            setVaccinationPointId(1L);
        }});
        vaccination.setFirstName("first_name");
        vaccination.setLastName("last_name");
        vaccination.setPatronymic("patronymic");
        vaccination.setDocumentNumber("1234");
        vaccination.setVaccineNumber("322");
        vaccination.setVaccinationDate(LocalDate.of(2022, 1, 1));
        return vaccinationRepository.save(vaccination);
    }

}
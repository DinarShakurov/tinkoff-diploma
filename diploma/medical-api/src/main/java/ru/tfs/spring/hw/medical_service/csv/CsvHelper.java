package ru.tfs.spring.hw.medical_service.csv;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.util.List;

@Slf4j
public class CsvHelper {

    public static <T> List<T> readCsvToList(String csvData, Class<T> clazz, BeanVerifier<T> verifier) {
        log.info("Converting CSV to List<{}>", clazz);
        try (StringReader reader = new StringReader(csvData)) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(
                    new CSVReaderBuilder(reader)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                    .build()
            )
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(',')
                    .withVerifier(verifier)
                    .build();
            List<T> list = csvToBean.parse();
            log.info("Successful converted CSV to List. List size - {}", list.size());
            return list;
        }
    }
}

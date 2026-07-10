package br.com.jca.services;

import br.com.jca.entity.EmployeesEntity;
import br.com.jca.repository.EmployeesRepository;
import br.com.jca.request.EmployeesRequest;
import br.com.jca.response.EmployeeResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeesRepository repository;

    @Autowired
    private MessageProducer kafkaProducer;

    public EmployeeResponse incluir(EmployeesRequest request) {
        EmployeeResponse response = new EmployeeResponse();
        ModelMapper modelMapper = new ModelMapper();
        EmployeesEntity entity = modelMapper.map(request, EmployeesEntity.class);

//        entity  = repository.save(entity);

        response = modelMapper.map(entity, EmployeeResponse.class);

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            String jsonkafka = mapper.writeValueAsString(response);
            kafkaProducer.sendMessage(jsonkafka);
        }catch (Exception e) {
            log.error("Falha ao converter o response para json ", e);
        }



        return response;
    }
}

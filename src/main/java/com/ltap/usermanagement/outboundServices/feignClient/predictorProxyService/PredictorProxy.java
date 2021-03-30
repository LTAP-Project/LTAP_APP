package com.ltap.usermanagement.outboundServices.feignClient.predictorProxyService;

import com.ltap.usermanagement.outboundServices.feignClient.dto.HobbiesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.ltap.usermanagement.controller.urlconstants.UriContents.PREDICTOR_URL;

@FeignClient(name = "predictor-service", url = "${predictor-service}")
public interface PredictorProxy {
    @GetMapping(value = PREDICTOR_URL)
    List<HobbiesDTO> getPredictorHobbies(@RequestParam String time,
                                         @RequestParam String status,
                                         @RequestParam String emotion,
                                         @RequestParam String duration);
}



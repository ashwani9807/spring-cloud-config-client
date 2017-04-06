package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
    	
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}

@RefreshScope
@RestController
class MessageRestController {

    @Value("${message:Hello default}")
    private String message;
    
   @Autowired
   MyConfiguration config;
     
    
    @RequestMapping("/message")
    String getMessage() throws JsonProcessingException {
    	ObjectMapper objectmapper = new ObjectMapper();
        return objectmapper.writeValueAsString(config.getJson_map());
    }
}
@Component
@ConfigurationProperties
class MyConfiguration {
	private List<VCAP> json_map = new ArrayList<VCAP>();
	private List<VCAP> json_map_common = new ArrayList<VCAP>();
	
	public List<VCAP> getJson_map() {
		json_map.addAll(getJson_map_common());
		return json_map;
	}

	public void setJson_map(List<VCAP> json_map) {
		this.json_map = json_map;
	}

	public List<VCAP> getJson_map_common() {
		return json_map_common;
	}

	public void setJson_map_common(List<VCAP> json_map_common) {
		this.json_map_common = json_map_common;
	}
	
}
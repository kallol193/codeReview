package dev.dt.pick_a_spot;

import dev.dt.pick_a_spot.response.PickRequest;
import dev.dt.pick_a_spot.response.PickResponse;
import dev.dt.pick_a_spot.service.PickerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class PickASpotApplication {

    public PickASpotApplication(PickerService picker) {
        this.picker = picker;
    }

    public static void main(String[] args) {
		SpringApplication.run(PickASpotApplication.class, args);
	}

	//constructor injection for thread safety & immutability while generating tests
	private final PickerService picker;

	@PostMapping("/pickSpot")
	public ResponseEntity<Object> pick(@RequestBody PickRequest req) {
		return picker.chooseBestSlot(req.container(), req.yardMap())
				.map(slot -> ResponseEntity.ok((Object) new PickResponse(req.container().id(), slot.x(), slot.y())))
				.orElseGet(() -> new ResponseEntity<>(Map.of("error", "no suitable slot"), HttpStatus.BAD_REQUEST));
	}

}

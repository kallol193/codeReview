package dev.dt.pick_a_spot;

import dev.dt.pick_a_spot.model.Container;
import dev.dt.pick_a_spot.model.Slot;
import dev.dt.pick_a_spot.service.PickerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PickerServiceTest {

    @Autowired
    private PickerService pickerService;

    @Test
    void yardFullReturnsEmpty() {
        Container container = new Container("C1", "small", false, 0, 0);
        List<Slot> fullYard = List.of(new Slot(0, 1, "small", false, true));
        assertTrue(pickerService.chooseBestSlot(container, fullYard).isEmpty());
    }

    @Test
    void suitableSlotIsPickedCorrectly() {
        Container container = new Container("C2", "small", false, 0, 0);
        List<Slot> yard = List.of(
                new Slot(0, 1, "small", false, false),
                new Slot(1, 1, "small", false, true) // occupied
        );

        Optional<Slot> slot = pickerService.chooseBestSlot(container, yard);
        assertTrue(slot.isPresent());
        assertEquals(0, slot.get().x());
        assertEquals(1, slot.get().y());
    }


}

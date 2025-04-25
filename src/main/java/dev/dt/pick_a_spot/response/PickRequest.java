package dev.dt.pick_a_spot.response;

import dev.dt.pick_a_spot.model.Container;
import dev.dt.pick_a_spot.model.Slot;

import java.util.List;

public record PickRequest(Container container, List<Slot> yardMap) {
}

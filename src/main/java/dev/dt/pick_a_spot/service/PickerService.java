package dev.dt.pick_a_spot.service;

import dev.dt.pick_a_spot.model.Container;
import dev.dt.pick_a_spot.model.Slot;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PickerService {

    private static final int INVALID = 10_000;

    public Optional<Slot> chooseBestSlot(Container container, List<Slot> slots) {
        Slot bestSlot = null;
        int bestScore = INVALID + 1;

        for (Slot slot : slots) {
            int score = score(container, slot);
            if (score < bestScore) {
                bestScore = score;
                bestSlot = slot;
            }
        }

        return bestScore >= INVALID ? Optional.empty() : Optional.of(bestSlot);
    }

    private int score(Container container, Slot slot) {
        int distance = Math.abs(container.x() - slot.x()) + Math.abs(container.y() - slot.y());
        int sizePenalty = (container.size().equals("big") && slot.sizeCap().equals("small")) ? INVALID : 0;
        int coldPenalty = (container.needsCold() && !slot.hasColdUnit()) ? INVALID : 0;
        int occupiedPenalty = slot.isOccupied() ? INVALID : 0;
        return distance + sizePenalty + coldPenalty + occupiedPenalty;
    }
}

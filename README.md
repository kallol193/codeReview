# @Pick-a-Spot API

This is a simple Spring Boot application that helps decide the best possible slot to place a container in a yard, based on size, cooling needs, and current availability.

---

## ? What It Does

The app takes in details of a container and the current state of the yard (i.e., all the slots), and picks the most suitable slot for that container. It checks for things like:

- Is the slot already occupied?
- Does it have a cold unit (if the container needs one)?
- Does the slot support the container's size?
- How far is the slot from the container's current position?

If all checks pass, the one with the shortest distance is picked.

---

## ? How It Works

### - Scoring Logic

At the heart of this is a service class `PickerService` that loops through all available slots and calculates a score for each one. The lower the score, the better the slot.

Here’s how scoring is done:

- **Distance**: Uses simple Manhattan distance.
- **Invalidation**: Slot gets disqualified (big penalty score) if:
    - It’s too small for the container.
    - It doesn’t have a cold unit when needed.
    - It’s already taken.

This makes sure only suitable and available slots are considered.

---

## + API Usage

### Endpoint: `POST /pickSpot`

You send a JSON with a container and the yard map.

### Sample Request:
```json
{
  "container": {
    "id": "C1",
    "size": "small",
    "needsCold": false,
    "x": 0,
    "y": 0
  },
  "yardMap": [
    {
      "x": 1,
      "y": 0,
      "sizeCap": "small",
      "hasColdUnit": false,
      "isOccupied": false
    }
  ]
}
---

Success Response
{
  "containerId": "C1",
  "targetX": 0,
  "targetY": 1
}

if no slot fits
{
  "error": "no suitable slot"
}




package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.AnimalGender;
import community.whatever.petcoming.feed.domain.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LostPetFeedSubmitRequest {

    private final String specialNote;
    private final String details;

    private final AnimalType animalType;
    private final AnimalGender animalGender;
    private final String breed;
    private final String lostArea;
    private final String contact;
}

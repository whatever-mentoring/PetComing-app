package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.AnimalGender;
import community.whatever.petcoming.feed.domain.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LostPetFeedSubmitRequest {

    private String specialNote;
    private String details;

    private AnimalType animalType;
    private AnimalGender animalGender;
    private String breed;
    private String lostArea;
    private String contact;
}

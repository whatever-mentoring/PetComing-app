package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.AnimalGender;
import community.whatever.petcoming.feed.domain.AnimalType;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class LostPetFeedGetRequest {

    private Long lastFeedId;
    @NotNull
    private Integer size;
    private FeedsSortOption sort;
    private String keyword;
    private AnimalGender animalGender;
    private AnimalType animalType;
}

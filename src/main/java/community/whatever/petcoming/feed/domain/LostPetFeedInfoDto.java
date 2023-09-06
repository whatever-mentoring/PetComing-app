package community.whatever.petcoming.feed.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LostPetFeedInfoDto {

    private final Long feedId;
    private final String specialNote;
    private final String authorName;

    private final AnimalType animalType;
    private final AnimalGender animalGender;
    private final String breed;
    private final String lostArea;

    private final Long viewCount;
    private final Long likeCount;

    private final String imageUrl;
}

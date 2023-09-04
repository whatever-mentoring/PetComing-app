package community.whatever.petcoming.feed.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LostPetFeedFullDto {

    private final Long feedId;
    private final String specialNote;
    private final String authorName;

    private final AnimalType animalType;
    private final AnimalGender animalGender;
    private final String breed;

    private final Long viewCount;
    private final Long likeCount;

    private final String imageUrl;
    private final String details;

    private final LocalDateTime createDate;
}

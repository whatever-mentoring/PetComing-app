package community.whatever.petcoming.feed.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class LostPetFeedFullDto {

    private final Long feedId;
    private final String specialNote;
    private final String authorName;

    private final AnimalType animalType;
    private final AnimalGender animalGender;
    private final String breed;
    private final String lostArea;
    private final String contact;

    private final Long viewCount;
    private final Long likeCount;

    private final List<String> imageUrls;
    private final String details;

    private final LocalDateTime createDate;
}

package community.whatever.petcoming.feed.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LostPetFeedInfoDto {

    private final Long feedId;
    private final String specialNote;
    private final String authorName;

    private final AnimalType animalType;
    private final AnimalGender animalGender;
    private final String breed;
    private final String imageUrl;

    private final Long viewCount;
    private final Long likeCount;

    @Builder
    public LostPetFeedInfoDto(Long feedId, String specialNote, String authorName, AnimalType animalType, AnimalGender animalGender, String breed, String imageUrl, Long viewCount, Long likeCount) {
        this.feedId = feedId;
        this.specialNote = specialNote;
        this.authorName = authorName;
        this.animalType = animalType;
        this.animalGender = animalGender;
        this.breed = breed;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }
}

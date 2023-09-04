package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.AnimalGender;
import community.whatever.petcoming.feed.domain.AnimalType;
import community.whatever.petcoming.feed.domain.LostPetFeedInfoDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LostPetFeedInfoResponse {

    private final Long feedId;
    private final String specialNote;
    private final String authorName;

    private final AnimalType animalType;
    private final AnimalGender animalGender;
    private final String breed;
    private final String imageUrl;

    private final Long viewCount;
    private final Long likeCount;

    public static LostPetFeedInfoResponse of(LostPetFeedInfoDto dto) {
        return LostPetFeedInfoResponse.builder()
                .feedId(dto.getFeedId())
                .specialNote(dto.getSpecialNote())
                .authorName(dto.getAuthorName())
                .animalType(dto.getAnimalType())
                .animalGender(dto.getAnimalGender())
                .breed(dto.getBreed())
                .imageUrl(dto.getImageUrl())
                .viewCount(dto.getViewCount())
                .likeCount(dto.getLikeCount())
                .build();
    }
}

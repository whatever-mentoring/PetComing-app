package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.AnimalGender;
import community.whatever.petcoming.feed.domain.AnimalType;
import community.whatever.petcoming.feed.domain.LostPetFeedFullDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LostPetFeedFullResponse {

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

    public static LostPetFeedFullResponse of(LostPetFeedFullDto fullDto) {
        return LostPetFeedFullResponse.builder()
                .feedId(fullDto.getFeedId())
                .specialNote(fullDto.getSpecialNote())
                .authorName(fullDto.getAuthorName())
                .animalType(fullDto.getAnimalType())
                .animalGender(fullDto.getAnimalGender())
                .breed(fullDto.getBreed())
                .viewCount(fullDto.getViewCount())
                .likeCount(fullDto.getLikeCount())
                .imageUrl(fullDto.getImageUrl())
                .details(fullDto.getDetails())
                .createDate(fullDto.getCreateDate())
                .build();
    }
}
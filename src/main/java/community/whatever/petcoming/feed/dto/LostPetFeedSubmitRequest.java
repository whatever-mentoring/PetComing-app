package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.AnimalGender;
import community.whatever.petcoming.feed.domain.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LostPetFeedSubmitRequest {

    private final String specialNote;
    private final String details;

    private final AnimalType animalType;
    private final AnimalGender animalGender;
    private final String breed;
    private final String lostArea;
    private final String contact;

    private final List<MultipartFile> imageFiles;
}

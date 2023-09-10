package community.whatever.petcoming.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommunityFeedFullRequest {

    private final String title;
    private final String content;

    private final List<MultipartFile> imageFiles;
}

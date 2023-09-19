package community.whatever.petcoming.feed.service;

import community.whatever.petcoming.common.infra.S3Uploader;
import community.whatever.petcoming.feed.domain.CommunityFeed;
import community.whatever.petcoming.feed.domain.CommunityFeedEditor;
import community.whatever.petcoming.feed.domain.CommunityFeedFinder;
import community.whatever.petcoming.feed.domain.CommunityFeedFullDto;
import community.whatever.petcoming.feed.domain.CommunityFeedInfoDto;
import community.whatever.petcoming.feed.domain.CommunityFeedRepository;
import community.whatever.petcoming.feed.domain.FeedContent;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.CommunityFeedSubmitRequest;
import community.whatever.petcoming.feed.dto.CommunityFeedFullResponse;
import community.whatever.petcoming.feed.dto.CommunityFeedInfoResponse;
import community.whatever.petcoming.uploadimage.domain.UploadImage;
import community.whatever.petcoming.uploadimage.domain.UploadImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityFeedService {

    private final int MAX_FEED_IMAGE_COUNT = 3;

    private final CommunityFeedRepository communityFeedRepository;
    private final UploadImageRepository uploadImageRepository;

    private final S3Uploader s3Uploader;

    private final CommunityFeedFinder communityFeedFinder;
    private final CommunityFeedEditor communityFeedEditor;

    @Transactional(readOnly = true)
    public List<CommunityFeedInfoResponse> getCommunityFeedInfoList(Long memberId, Integer size, FeedsSortOption sort) {
        // 내부 코드 호출
        return getCommunityFeedInfoList(memberId, Long.MAX_VALUE, size, sort);
    }

    @Transactional(readOnly = true)
    public List<CommunityFeedInfoResponse> getCommunityFeedInfoList(Long memberId, Long lastFeedId, Integer size, FeedsSortOption sort) {
        List<CommunityFeedInfoDto> dtoList = communityFeedFinder.getCommunityFeedInfoList(memberId, lastFeedId, size, sort);

        return dtoList.stream()
                .map(CommunityFeedInfoResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommunityFeedFullResponse getCommunityFeedFull(Long loginMemberId, Long feedId) {
        CommunityFeedFullDto fullDto = communityFeedFinder.getCommunityFeedFull(loginMemberId, feedId);
        return CommunityFeedFullResponse.of(fullDto);
    }

    @Transactional
    public void increaseViewCount(Long feedId) {
        communityFeedEditor.increaseViewCount(feedId);
    }

    @Transactional
    public void submitFeed(Long memberId, CommunityFeedSubmitRequest dto, List<MultipartFile> imageFiles) throws IOException {
        FeedContent feedContent = new FeedContent(null, dto.getContent()); // 알아서 저장됨

        CommunityFeed feed = CommunityFeed.builder()
                .title(dto.getTitle())
                .content(feedContent)
                .authorId(memberId)
                .build();

        feed = communityFeedRepository.save(feed);

        int count = 0;
        for (MultipartFile file : imageFiles) {
            count++;
            if (count > MAX_FEED_IMAGE_COUNT) break;

            String path = s3Uploader.upload(file, "image");

            UploadImage uploadImage = UploadImage.builder()
                    .feedCategory("community")
                    .feedId(feed.getId())
                    .uploadUrl(path)
                    .build();

            uploadImageRepository.save(uploadImage);
        }
    }

    @Transactional
    public void deleteFeed(Long memberId, Long feedId) {
        CommunityFeed myFeed = communityFeedFinder.isAuthorOrThrow(memberId, feedId);
        myFeed.softDelete();
    }

    @Transactional
    public Long likeFeed(Long memberId, Long feedId) {
        return communityFeedEditor.likeFeed(memberId, feedId);
    }

    @Transactional
    public Long unlikeFeed(Long memberId, Long feedId) {
        return communityFeedEditor.unlikeFeed(memberId, feedId);
    }
}

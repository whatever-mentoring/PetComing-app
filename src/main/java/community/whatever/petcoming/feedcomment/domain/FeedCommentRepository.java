package community.whatever.petcoming.feedcomment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {

    List<FeedComment> findByFeedCategoryAndFeedId(String feedCategory, Long feedId);
}

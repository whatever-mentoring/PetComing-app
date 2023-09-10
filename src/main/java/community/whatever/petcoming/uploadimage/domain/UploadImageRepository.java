package community.whatever.petcoming.uploadimage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UploadImageRepository extends JpaRepository<UploadImage, Long> {

    List<UploadImage> findByFeedCategoryAndFeedIdOrderByFeedIdAsc(String feedCategory, Long feedId);

    Optional<UploadImage> findFirstByFeedCategoryAndFeedIdOrderByIdAsc(String feedCategory, Long feedId);
}

package community.whatever.petcoming.feed.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LostPetFeedRepository extends JpaRepository<LostPetFeed, Long> {

    List<LostPetFeed> findByIdLessThan(Long lastFeedId, Pageable pageable);
}

package community.whatever.petcoming.feed.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LostPetFeedRepository extends JpaRepository<LostPetFeed, Long> {

    List<LostPetFeed> findByIdLessThan(Long lastFeedId, Pageable pageable);

    @Modifying
    @Query("UPDATE LostPetFeed f SET f.viewCount = f.viewCount + 1 WHERE f.id = :id")
    void increaseViewCountById(Long id);
}

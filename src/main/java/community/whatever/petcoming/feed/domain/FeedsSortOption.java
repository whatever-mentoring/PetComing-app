package community.whatever.petcoming.feed.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public enum FeedsSortOption {
    LATEST("latest", "id"),
    POPULAR("popular", "view_count")
    ;

    private final int FIRST_PAGE = 0;
    private final Sort.Direction SORT_DIRECTION = Sort.Direction.DESC;

    private final String option;
    private final String sortCriteria;

    FeedsSortOption(String option, String sortCriteria) {
        this.option = option;
        this.sortCriteria = sortCriteria;
    }

    @JsonValue
    public String getValue() {
        return option;
    }

    public Pageable getPageable(Integer size) {
        if (this == FeedsSortOption.POPULAR) {
            return PageRequest.of(FIRST_PAGE, size, SORT_DIRECTION, sortCriteria, FeedsSortOption.LATEST.getSortCriteria());
        } else {
            return PageRequest.of(FIRST_PAGE, size, SORT_DIRECTION, FeedsSortOption.LATEST.getSortCriteria());
        }
    }
}

package community.whatever.petcoming.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MemberResponse {

    private Long id;
    private String nickname;
}

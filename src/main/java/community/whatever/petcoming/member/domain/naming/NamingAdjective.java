package community.whatever.petcoming.member.domain.naming;

import lombok.Getter;

@Getter
public enum NamingAdjective {
    HAPPY("기쁜"), SMART("똑똑한"), LOVELY("사랑스러운"),
    ACTIVE("활발한"), KIND("착한"), COOL("멋진"),
    WITTY("재치있는"), JOYFUL("유쾌한"), SHINING("빛나는"),
    BRAVE("용감한"), CREATIVE("창의적인"), CHEERFUL("쾌활한"),
    DILIGENT("성실한"), KINDLY("친절한"), ELEGANT("우아한"),
    TRUSTWORTHY("믿을만한"), LIVELY("생기있는"), HONEST("정직한"),
    FREE("자유로운"), AFFECTIONATE("애정있는"), VIBRANT("활기찬"),
    PASSIONATE("열정적인"), BRILLIANT("똑똑한"), DAZZLING("눈부신"),
    HEALTHY("건강한"), TOUGH("강인한"), CONFIDENT("자신있는"),
    TALENTED("재능있는"), BOLD("대담한"), ADMIRABLE("감탄스러운")
    ;

    private final String korean;

    NamingAdjective(String korean) {
        this.korean = korean;
    }
}

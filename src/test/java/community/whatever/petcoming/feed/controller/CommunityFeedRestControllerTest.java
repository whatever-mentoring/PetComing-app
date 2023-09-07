package community.whatever.petcoming.feed.controller;

import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.CommunityFeedFullResponse;
import community.whatever.petcoming.feed.dto.CommunityFeedInfoResponse;
import community.whatever.petcoming.feed.service.CommunityFeedService;
import community.whatever.petcoming.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WithMockUser
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest(CommunityFeedRestController.class)
class CommunityFeedRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityFeedService communityFeedService;

    @MockBean
    private MemberService memberService;

    private final String COMMUNITY_FEED_URL = "/api/v1/feed/community/";
    private final String DOCUMENT_IDENTIFIER_PREFIX = "CommunityFeed/";

    @Test
    @DisplayName("댕글냥글 피드 목록 조회. 첫 조회")
    void 첫조회_CommunityFeedInfoList() throws Exception {

        //given
        List<CommunityFeedInfoResponse> list = new ArrayList<>();
        CommunityFeedInfoResponse response = CommunityFeedInfoResponse.builder()
                .feedId(10L)
                .title("고양이가 귀여워요")
                .authorName("집사1")
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .viewCount(100L)
                .likeCount(100L)
                .build();
        list.add(response);

        BDDMockito.given(communityFeedService.getCommunityFeedInfoList(
                        ArgumentMatchers.any(Integer.class),
                        ArgumentMatchers.any(FeedsSortOption.class)))
                .willReturn(list);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get(COMMUNITY_FEED_URL)
                        .param("size", "10")
                        .param("sort", "LATEST")
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "목록_첫조회",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("댕글냥글 피드 목록 조회. 재조회")
    void 재조회_CommunityFeedInfoList() throws Exception {

        //given
        List<CommunityFeedInfoResponse> list = new ArrayList<>();
        CommunityFeedInfoResponse response = CommunityFeedInfoResponse.builder()
                .feedId(10L)
                .title("고양이가 귀여워요")
                .authorName("집사1")
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .viewCount(100L)
                .likeCount(100L)
                .build();
        list.add(response);

        BDDMockito.given(communityFeedService.getCommunityFeedInfoList(
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.any(Integer.class),
                        ArgumentMatchers.any(FeedsSortOption.class)))
                .willReturn(list);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get(COMMUNITY_FEED_URL)
                        .param("last-feed", "1")
                        .param("size", "10")
                        .param("sort", "POPULAR")
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "목록_재조회",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("댕글냥글 피드 상세정보 조회")
    void 댕글냥글_피드_상세정보_조회() throws Exception {

        //given
        Long targetFeedId = 10L;

        CommunityFeedFullResponse fullResponse = CommunityFeedFullResponse.builder()
                .feedId(targetFeedId)
                .title("고양이가 귀여워요")
                .authorName("집사1")
                .viewCount(100L)
                .likeCount(100L)
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .content("이 고양이는 매우 귀여운 고양이입니다. 보고 가세요.")
                .createDate(LocalDateTime.now())
                .build();

        BDDMockito.given(communityFeedService.getCommunityFeedFull(ArgumentMatchers.any()))
                .willReturn(fullResponse);

        //when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get(COMMUNITY_FEED_URL + "{feedId}", targetFeedId))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "전체피드조회",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("feedId").description("피드 Identifier")
                                )
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
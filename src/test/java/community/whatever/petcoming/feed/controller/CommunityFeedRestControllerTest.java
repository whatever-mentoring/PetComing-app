package community.whatever.petcoming.feed.controller;

import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.CommunityFeedResponse;
import community.whatever.petcoming.feed.service.CommunityFeedService;
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
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest(CommunityFeedRestController.class)
class CommunityFeedRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityFeedService communityFeedService;

    private final String getCommunityInfoListUrl = "/api/v1/feed/community/";
    private final String DOCUMENT_IDENTIFIER_PREFIX = "/CommunityFeed/";

    @Test
    @DisplayName("댕글냥글 피드 목록 조회. 첫 조회")
    void 첫조회_CommunityFeedInfoList() throws Exception {

        //given
        List<CommunityFeedResponse> list = new ArrayList<>();
        CommunityFeedResponse response = CommunityFeedResponse.builder()
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
        mockMvc.perform(MockMvcRequestBuilders.get(getCommunityInfoListUrl)
                        .param("size", "10")
                        .param("sort", "LATEST")
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX+"목록_첫조회",
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
        List<CommunityFeedResponse> list = new ArrayList<>();
        CommunityFeedResponse response = CommunityFeedResponse.builder()
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
        mockMvc.perform(MockMvcRequestBuilders.get(getCommunityInfoListUrl)
                        .param("last-feed", "1")
                        .param("size", "10")
                        .param("sort", "LATEST")
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX+"목록_재조회",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
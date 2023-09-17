package community.whatever.petcoming.feedcomment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.whatever.petcoming.feedcomment.dto.FeedCommentResponse;
import community.whatever.petcoming.feedcomment.dto.FeedCommentSubmitRequest;
import community.whatever.petcoming.feedcomment.service.FeedCommentService;
import community.whatever.petcoming.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WithMockUser
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest(FeedCommentRestController.class)
class FeedCommentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedCommentService feedCommentService;

    @MockBean
    private MemberService memberService;

    private final String FEED_COMMENT_URL = "/api/v1/feed/comment";
    private final String DOCUMENT_IDENTIFIER_PREFIX = "FeedComment/";

    @BeforeEach
    public void setUp() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "1234567890");
        OidcIdToken idToken = new OidcIdToken("asdf", Instant.now(), Instant.now().plusSeconds(60), claims);
        OAuth2UserAuthority authority = new OAuth2UserAuthority("ROLE_USER", claims);
        OidcUser oidcUser = new DefaultOidcUser(Collections.singletonList(authority), idToken);

        SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(oidcUser, Collections.emptyList(), "oidc"));
    }

    @Test
    @DisplayName("피드 댓글 작성")
    void 피드_댓글_작성() throws Exception {
        BDDMockito.doNothing().when(feedCommentService).submitFeedComment(ArgumentMatchers.anyLong(), ArgumentMatchers.any());
        BDDMockito.when(memberService.findIdByProviderIdAndSubject(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(1L);

        FeedCommentSubmitRequest dto = new FeedCommentSubmitRequest("community", 1L, "댓글 내용입니다.");

        ObjectMapper objectMapper = new ObjectMapper();
        String dtoJson = objectMapper.writeValueAsString(dto);

        // When, Then
        mockMvc.perform(
                        MockMvcRequestBuilders.post(FEED_COMMENT_URL + "/submit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dtoJson)
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "submit-feed-comment",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("피드 댓글 리스트 조회")
    void getFeedCommentListTest() throws Exception {
        // Given
        List<FeedCommentResponse> mockResponses = Arrays.asList(
                FeedCommentResponse.builder()
                        .category("community")
                        .feedId(1L)
                        .authorId(101L)
                        .authorNickname("댓글작성자닉네임1")
                        .content("댓글 내용입니다.")
                        .createDate(LocalDateTime.now())
                        .build(),
                FeedCommentResponse.builder()
                        .category("community")
                        .feedId(1L)
                        .authorId(102L)
                        .authorNickname("댓글작성자닉네임2")
                        .content("댓글 내용입니다.")
                        .createDate(LocalDateTime.now())
                        .build()
        );

        BDDMockito.when(feedCommentService.getCommunityFeedCommentList(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(mockResponses);

        // When, Then
        mockMvc.perform(
                        MockMvcRequestBuilders.get(FEED_COMMENT_URL + "/")
                                .param("category", "community")
                                .param("feedId", "1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "read-feed-comments",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
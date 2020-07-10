package utils.module;

import static org.junit.Assert.assertNull;

import java.util.concurrent.CompletionStage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

@RunWith(PowerMockRunner.class)
@PrepareForTest({})
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*", "javax.security.*"})
public class OnRequestHandlerTest {

  @Test
  public void checkForServiceHealth() {
    Http.RequestBuilder requestBuilder =
        Helpers.fakeRequest(
                Helpers.GET,
                "http://localhost:9000/v1/user/read/56c2d9a3-fae9-4341-9862-4eeeead2e9a1")
            .header(
                "x-authenticated-user-token",
                "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIyZUNvWGlZRHFDbHRaX1F1ZXNRMEhtNkNYVF91emJiN2d3bXlMZXhsN1JnIn0.eyJqdGkiOiIwMTVmNmRlOC1jODRiLTRkNmUtOGRkYy1mNzZmNTk3NTViNjgiLCJleHAiOjE1OTQxMDg0MjUsIm5iZiI6MCwiaWF0IjoxNTk0MDIyMDI1LCJpc3MiOiJodHRwczovL3N0YWdpbmcubnRwLm5ldC5pbi9hdXRoL3JlYWxtcy9zdW5iaXJkIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZjo5MzFhOWRjOS00NTk0LTQ4MzktYWExNi1jZjBjYWMwOTYzODE6M2Y0YmYzMTEtOTNkMy00ODY3LTgxMGMtZGViMDYzYjQzNzg5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiZGFmYzU0YmUtNmZkOS00MDRlLTljYzctN2FkYzYxYzVjYzI1IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6W10sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7fSwibmFtZSI6Ik5hdjIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJuYXYyMzMzNyIsImdpdmVuX25hbWUiOiJOYXYyIiwiZW1haWwiOiJuYXYyQHlvcG1haWwuY29tIn0.HAG5Uv7F7J82HCmNsM9NjzMKEW_65nsJX-P_SC5XNfSoz9w5FkQQ4Xlx9elw5vbvtG9UU5Jn5TDMRGAnjdCZ-FMgMv0BLGy3uRKq6Xu6drf6oN9kYMIgTGYuf946EX3pelXQtL6kXwi5_OQ6OQT7Ie94l525BEn09SkeiKJsUrrxShLMlCaX3ERt83MwNdxLkuJ0tI8Jx22leksaf8cxGteC3iF31eLVxIe3ioIexUpbbTI-zBZHHURX_5tAIZvq91kV7Laibngqg4RDluaBltmbBWufFBPAYHqwFRvhix2E78t3d6cb7mx4xRNDrbTJCxHQCL2kE-VXkPGBDEHa3g")
            .header(
                "x-authenticated-for",
                "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIyZUNvWGlZRHFDbHRaX1F1ZXNRMEhtNkNYVF91emJiN2d3bXlMZXhsN1JnIn0.eyJqdGkiOiIwMTVmNmRlOC1jODRiLTRkNmUtOGRkYy1mNzZmNTk3NTViNjgiLCJleHAiOjE1OTQxMDg0MjUsIm5iZiI6MCwiaWF0IjoxNTk0MDIyMDI1LCJpc3MiOiJodHRwczovL3N0YWdpbmcubnRwLm5ldC5pbi9hdXRoL3JlYWxtcy9zdW5iaXJkIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZjo5MzFhOWRjOS00NTk0LTQ4MzktYWExNi1jZjBjYWMwOTYzODE6M2Y0YmYzMTEtOTNkMy00ODY3LTgxMGMtZGViMDYzYjQzNzg5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiZGFmYzU0YmUtNmZkOS00MDRlLTljYzctN2FkYzYxYzVjYzI1IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6W10sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7fSwibmFtZSI6Ik5hdjIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJuYXYyMzMzNyIsImdpdmVuX25hbWUiOiJOYXYyIiwiZW1haWwiOiJuYXYyQHlvcG1haWwuY29tIn0.HAG5Uv7F7J82HCmNsM9NjzMKEW_65nsJX-P_SC5XNfSoz9w5FkQQ4Xlx9elw5vbvtG9UU5Jn5TDMRGAnjdCZ-FMgMv0BLGy3uRKq6Xu6drf6oN9kYMIgTGYuf946EX3pelXQtL6kXwi5_OQ6OQT7Ie94l525BEn09SkeiKJsUrrxShLMlCaX3ERt83MwNdxLkuJ0tI8Jx22leksaf8cxGteC3iF31eLVxIe3ioIexUpbbTI-zBZHHURX_5tAIZvq91kV7Laibngqg4RDluaBltmbBWufFBPAYHqwFRvhix2E78t3d6cb7mx4xRNDrbTJCxHQCL2kE-VXkPGBDEHa3g");
    Http.Request req = requestBuilder.build();
    CompletionStage<Result> result = OnRequestHandler.checkForServiceHealth(req);
    assertNull(result);
  }
}

package com.oakleyp.youtubeautostream.clients.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.LiveChatMessageListResponse;
import com.oakleyp.youtubeautostream.clients.google.GoogleOauth2Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YouTubeClient {

    @Value("${google.app.name}")
    private final String APPLICATION_NAME = "Youtube Autostream";

    @Value("${google.youtube.service.account.username}")
    private final String SERVICE_ACCT_USERNAME = null;

    private static final String CLIENT_SECRETS = "client_secret.json";
    private static final Collection<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/youtube.readonly");
    
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private GoogleOauth2Service oauth2Service = new GoogleOauth2Service(SCOPES);

    private Optional<YouTube> connectedYoutubeClient = Optional.empty();

    public YouTubeClient() {

    }

    private InputStream getSecretsAsStream() {
        // Load client secrets.
        return this.getClass().getResourceAsStream(CLIENT_SECRETS);
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public YouTube getYouTubeService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = oauth2Service.authorize(httpTransport, this.getSecretsAsStream(), SERVICE_ACCT_USERNAME);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }


    public void connect() throws GeneralSecurityException, IOException {
        connectedYoutubeClient = Optional.of(getYouTubeService());
    }

    private YouTube getConnectedYouTubeService() throws IllegalStateException {
        if (connectedYoutubeClient.isEmpty()) {
            throw new IllegalStateException("Attempted to perform youtube operation before calling connect().");
        }

        return connectedYoutubeClient.get();
    }

    public LiveChatMessageListResponse getLiveChatMessages(String liveChatId) throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        YouTube youtubeService = getConnectedYouTubeService();
        // Define and execute the API request
        YouTube.LiveChatMessages.List request = youtubeService.liveChatMessages()
            .list(liveChatId, Arrays.asList("snippet", "authorDetails"));
        LiveChatMessageListResponse response = request.execute();

        return response;
    }
}

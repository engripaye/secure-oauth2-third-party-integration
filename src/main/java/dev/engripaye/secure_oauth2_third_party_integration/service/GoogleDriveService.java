package dev.engripaye.secure_oauth2_third_party_integration.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import org.springframework.stereotype.Service;

@Service
public class GoogleDriveService {

    public FileList listFiles(String accessToken) throws Exception {
        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new com.google.api.client.http.HttpRequestInitializer() {
                    @Override
                    public void initialize(com.google.api.client.http.HttpRequest request){
                        request.getHeaders().setAuthorization("Bearer " + accessToken);
                    }
                }
        ).setApplicationName("Third Party Integration").build();

        return drive.files().list().setFields("files(id, name)").execute();
    }

}

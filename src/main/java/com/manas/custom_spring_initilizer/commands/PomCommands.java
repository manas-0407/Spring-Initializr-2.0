package com.manas.custom_spring_initilizer.commands;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.io.*;

@Component
public class PomCommands {

    @Autowired
    RestTemplate restTemplate;

    private static final String MAVEN_CENTRAL_SEARCH_URL = "https://search.maven.org/solrsearch/select?q=g:\"%s\"+AND+a:\"%s\"&rows=1&wt=json";

    public String dependencyVersionCheck(boolean update) throws IOException, XmlPullParserException {
        if(System.getProperty("user.dir") == null)
            return "No directory specified";

        File pomDirectory = new File(System.getProperty("user.dir"), "pom.xml");

        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(pomDirectory));

        StringBuilder result = new StringBuilder();
        boolean changed = false;

        List<Dependency> dependencies = model.getDependencies();
        for(Dependency dependency : dependencies){
            String groupId = dependency.getGroupId();
            String artifactId = dependency.getArtifactId();
            String version = dependency.getVersion();

            if(version == null) continue;

            String latestVersion = getLatestVersion(groupId, artifactId);

            if(latestVersion != null && !version.equals(latestVersion)){
                result.append("Dependency ").append(artifactId).append(" version changed ").append(version).append(" --> ").append(latestVersion).append("\n");
                dependency.setVersion(latestVersion);
                changed = true;
            }
        }
        if (changed) {

            if(update){
                MavenXpp3Writer writer = new MavenXpp3Writer();
                writer.write(new FileWriter(pomDirectory), model);

                result.append("pom.xml has been updated with latest versions.");
            }
        } else {
            result.append("All dependencies are up-to-date.");
        }

        return result.toString();
    }

    private String getLatestVersion(String groupId , String artifactId){

        String queryUrl = String.format(MAVEN_CENTRAL_SEARCH_URL, groupId, artifactId);
        String jsonResponse = restTemplate.getForObject(queryUrl, String.class);
        return parseVersion(jsonResponse);
    }

    private String parseVersion(String json){
        if(json == null) return null;
        int versionIndex = json.indexOf("\"latestVersion\":\"");
        if (versionIndex != -1) {
            int start = versionIndex + "\"latestVersion\":\"".length();
            int end = json.indexOf("\"", start);
            if (end != -1) {
                return json.substring(start, end);
            }
        }
        return null;
    }

}

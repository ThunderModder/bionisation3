package com.thunder.bionisation.versions;

import com.thunder.bionisation.Information;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VersionChecker implements Runnable {

    private static boolean isLatestVersion = false;
    private static String latestVersion = "";
    private String newVersionURL = "";
    private List<String> changes = new ArrayList<>();

    @Override
    public void run() {
        try(InputStream in = new URL("https://raw.githubusercontent.com/ThunderModder/bionisation3/master/update1.12.2.txt").openStream()) {
            String [] version = IOUtils.readLines(in).get(0).split("_");
            latestVersion = version[0];
            newVersionURL = version[1];
            for (int i = 2; i < version.length; i++) {
                changes.add(version[i]);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isLatestVersion = Information.VERSION.equals(latestVersion);
    }

    public boolean isLatestVersion() {
        return isLatestVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public String getNewVersionURL() {
        return newVersionURL;
    }

    public List<String> getChanges() {
        return changes;
    }
}

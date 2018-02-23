package com.thunder.bionisation.versions;

import com.thunder.bionisation.Information;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class VersionChecker implements Runnable {

    private static boolean isLatestVersion = false;
    private static String latestVersion = "";
    private String newVersionURL = "";
    private String changes = "";

    @Override
    public void run() {
        InputStream in = null;
        try {
            in = new URL("https://raw.githubusercontent.com/ThunderModder/bionisation3/master/update1.11.2.txt").openStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String [] version = IOUtils.readLines(in).get(0).split("_");
            latestVersion = version[0];
            newVersionURL = version[1];
            changes = version[2];
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
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

    public String getChanges() {
        return changes;
    }
}

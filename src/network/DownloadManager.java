package network;

import io.OutputWriter;
import static_data.ExceptionMessages;
import static_data.SessionData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;

public class DownloadManager {

    public static void download(String fileUrl) {
        URL url = null;
        ReadableByteChannel rbc = null;
        FileOutputStream fos = null;

        try {
            OutputWriter.writeMessageOnNewLine("Started downloading...");
            url = new URL(fileUrl);
            rbc = Channels.newChannel(url.openStream());

            String fileName = exctractNameOfFile(url.toString());
            File file = new File(SessionData.currentPath + "/" + fileName);

            fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            OutputWriter.writeMessageOnNewLine("Download complete!");
        }
        catch (MalformedURLException e) {
            OutputWriter.displayException(ExceptionMessages.FILE_NOT_FOUND);
        }
        catch (IOException e) {
            OutputWriter.displayException(e.getMessage());
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (rbc != null) {
                    rbc.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void downloadOnNewThread(String fileUrl) {
        Thread thread = new Thread(() -> download(fileUrl));
        thread.setDaemon(false);
        thread.start();
    }

    public static String exctractNameOfFile(String fileUrl) throws MalformedURLException {
        int indexOfLastSlash = fileUrl.lastIndexOf("/");

        if (indexOfLastSlash == -1) {
            throw new MalformedURLException(ExceptionMessages.INVALID_PATH);
        }

        return fileUrl.substring(indexOfLastSlash + 1);
    }
}

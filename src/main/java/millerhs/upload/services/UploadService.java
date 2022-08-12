package millerhs.upload.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UploadService {
	
	private static final String SAVE_DIR = "C:\\Work\\_testFiles\\upload";
	
    private static final Logger LOG = LoggerFactory.getLogger(UploadService.class);
	
    public void upload(FileItemIterator iter) throws IOException, FileUploadException {
    	LOG.info("===== Beginning file upload =====");
        while (iter.hasNext()) {
            FileItemStream item = iter.next();
        	LOG.info("\tProcessing next file item");
            if(item.isFormField()) {
                continue;
            }
            upload(item);
        }
    	LOG.info("===== Finished file upload =====");
    }

    private void upload(FileItemStream item) throws IOException {
        var fileName = item.getName();
        var type = item.getContentType();
        var ins = item.openStream();
        var destination = new File(SAVE_DIR, String.format("%s_%s", fileName, System.currentTimeMillis()));
        var outs = new FileOutputStream(destination);

        IOUtils.copy(ins, outs);
        IOUtils.closeQuietly(ins);
        IOUtils.closeQuietly(outs);

        LOG.info("\tSaved {} with type {} to {}", fileName, type, destination);
    }

	
}

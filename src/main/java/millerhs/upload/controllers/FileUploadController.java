package millerhs.upload.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import millerhs.upload.exceptions.BadRequestException;
import millerhs.upload.services.UploadService;

/**
 * This was primarily inspired by https://github.com/nielsutrecht/spring-fileservice-example
 */

@Controller(value = "/api")
public class FileUploadController {
	
	@Autowired
	UploadService uploadService;
    
    /*
     * To invoke via CURL:
     * 	curl -F file=@C:/Work/_testfiles/2GB.bin http://localhost:8080/api/upload
     * 
     * To create a new test file:
     * 	fsutil file createnew 2GB.bin 2147483648
     */
    
    @ApiOperation( value = "Upload file")
    @ApiImplicitParams (value = {
            @ApiImplicitParam(dataType = "__file", name = "fileData", required = true,paramType = "form")})
    @PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Boolean> upload(HttpServletRequest request) throws FileUploadException, IOException {
        if(!ServletFileUpload.isMultipartContent(request)) {
            throw new BadRequestException("Multipart request expected");
        }
        
        uploadService.upload(new ServletFileUpload().getItemIterator(request));
    	
    	return ResponseEntity.ok(true);
    }
    
    /**
     * Retrieves the total, maximum, and available memory for the application.
     * 
     * @return MemoryStats
     */
    @GetMapping("memory-status")
    public ResponseEntity<MemoryStats> getMemoryStatistics() {
        MemoryStats stats = new MemoryStats();
        
        stats.setHeapSize(Runtime.getRuntime().totalMemory());
        stats.setHeapMaxSize(Runtime.getRuntime().maxMemory());
        stats.setHeapFreeSize(Runtime.getRuntime().freeMemory());
        
        return ResponseEntity.ok(stats);
    }
    
    public class MemoryStats {
    	private Long heapSize;
    	private Long heapMaxSize;
    	private Long heapFreeSize;
    	
		public Long getHeapSize() {
			return heapSize;
		}
		public void setHeapSize(Long heapSize) {
			this.heapSize = heapSize;
		}
		public Long getHeapMaxSize() {
			return heapMaxSize;
		}
		public void setHeapMaxSize(Long heapMaxSize) {
			this.heapMaxSize = heapMaxSize;
		}
		public Long getHeapFreeSize() {
			return heapFreeSize;
		}
		public void setHeapFreeSize(Long heapFreeSize) {
			this.heapFreeSize = heapFreeSize;
		}
    }

	
}

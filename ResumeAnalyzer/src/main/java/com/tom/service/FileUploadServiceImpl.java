package com.tom.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tom.constants.ResumeAnalyzerConstants;
import com.tom.exception.ResumeAnalyzerServiceException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Override
	public void saveMultibleFiles(MultipartFile[] fileList)  throws ResumeAnalyzerServiceException{
		
		//loop through the file and save it in directory: 
		
		if(null!=fileList&&fileList.length>0){
			System.out.println("Total No of files to Upload: "+fileList.length);
			
			//change into to something else so that it dosen't break while uploading files
			for(int i=0;i<fileList.length;i++){

				MultipartFile file=fileList[i];
				
				if (!file.isEmpty()) {
					try {
						if(saveFile(file,ResumeAnalyzerConstants.CVANALYZER_FILE_UPLOAD_LOCATION)){
							System.out.println(file.getOriginalFilename()+ "  Uploading Complete");	
						}
					} catch (Exception e) {
						System.out.println("Exception while uploading single file:  " + e.getMessage());
						throw new ResumeAnalyzerServiceException(e.getMessage());
					}
				} else {
					System.out.println("Unable to upload. File is empty."+file.getOriginalFilename());
				}
			}

		}
		
		
	}
	
	private boolean saveFile(MultipartFile file,String serverFileLocation) throws ResumeAnalyzerServiceException{
		
		//saving file to location
		try{
			String fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
           // System.out.println("File size: "+bytes.length);
           // System.out.println("File contents: "+bytes.toString());
            BufferedOutputStream buffStream = 
                    new BufferedOutputStream(new FileOutputStream(new File(serverFileLocation+"/" + fileName)));
            buffStream.write(bytes);
            buffStream.close();
		}catch(Exception e){
			throw new ResumeAnalyzerServiceException(e.getMessage());
		}
		return true;
	}

}

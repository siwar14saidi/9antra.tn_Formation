package tn.antraFormationSpringBoot.services;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
public interface IFileService {
	public String saveFile(MultipartFile file, String subPath);
	public String saveFileCours(MultipartFile file, String subPath);
	public void deleteFile(String subPath);
    public Resource downloadFile(String fileName);
    
    //video
    public String saveVideoCours(MultipartFile video, String subPath);
    public void deleteVideo(String fileName);
}

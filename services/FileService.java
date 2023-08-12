package tn.antraFormationSpringBoot.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;

@Service
public class FileService implements IFileService {
	private String rootPath = System.getProperty("user.dir") + "/src/main/resources/static/upload/images";
	/********** Generate random file name *************/
    private String generateRandomName() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnoprstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();

        return saltStr;

    }
	@Override
	public String saveFile(MultipartFile file, String subPath) {
		// TODO Auto-generated method stub
		 String fileName = generateRandomName() + "." + (file.getContentType().split("/"))[1] ;
	        String completePath = rootPath +"/"+ subPath;
	        Path path = Paths.get(completePath);
	        try
	        {
	            Files.copy(file.getInputStream(), path.resolve(fileName));
	        }
	        catch (Exception exception)
	        {
	            System.out.println("error while uploading image catch: " + exception.getMessage());
	        }
	        //System.out.println("fileName ==" + fileName);
	        //System.out.println("completePath ==" + completePath);
	        return fileName;
	}

	@Override
	public String saveFileCours(MultipartFile file, String subPath) {
		// TODO Auto-generated method stub

        String fileName = generateRandomName() + "." + (file.getContentType().split("/"))[1] ;
        String completePath = rootPath +"/"+ subPath;
        Path path = Paths.get(completePath);
        try
        {
            Files.copy(file.getInputStream(), path.resolve(fileName));
        }
        catch (Exception exception)
        {
            System.out.println("error while uploading image catch: " + exception.getMessage());
        }
        System.out.println("fileName ==" + fileName);
        System.out.println("completePath ==" + completePath);
        return fileName;
	}
	/*@Override
	public void deleteFile(String subPath) {
		// TODO Auto-generated method stub
		 String completePath = rootPath +"/"+ subPath;
	        Path path = Paths.get(completePath);
	        try
	        {
	            Files.delete(path);
	        } catch (Exception exception) {
	            System.out.println("error while deleting image catch: " + exception.getMessage());
	        }
	}*/
	 
	
	
	@Override
	public void deleteFile(String subPath) {
	    String completePath = rootPath + "/" + subPath;
	    Path path = Paths.get(completePath);
	    try {
	        Files.delete(path);
	        System.out.println("L'image a été supprimée avec succès !");
	    } catch (Exception exception) {
	        System.out.println("Erreur lors de la suppression de l'image : " + exception.getMessage());
	    }
	}

	
	
	
	@Override
	public Resource downloadFile(String fileName) {
		// TODO Auto-generated method stub
		  String completePath = rootPath +"/cours/creator";
	        try {
	            Path file = Paths.get(completePath)
	                    .resolve(fileName);
	            Resource resource = new UrlResource(file.toUri());
	            if (resource.exists() || resource.isReadable()) {
	                return resource;
	            } else {
	                throw new RuntimeException("Could not read the file!");

	            }
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("Error: " + e.getMessage());
	        }
	}
	@Override
	public String saveVideoCours(MultipartFile video, String subPath) {
		// TODO Auto-generated method stub
		String videoName = generateRandomName() + "." + (video.getContentType().split("/"))[1];
	    String completePath = rootPath + "/" + subPath;
	    Path path = Paths.get(completePath);

	    try {
	        Files.copy(video.getInputStream(), path.resolve(videoName));
	    } catch (Exception exception) {
	        System.out.println("error while uploading video catch: " + exception.getMessage());
	    }
	    System.out.println("videoName ==" + videoName);
	    System.out.println("completePath ==" + completePath);
	    return videoName;
	}
	@Override
	public void deleteVideo(String fileName) {
		// TODO Auto-generated method stub
		 String completePath = rootPath + "/videos";
		    try {
		        Path file = Paths.get(completePath)
		                .resolve(fileName);
		        Files.deleteIfExists(file);
		    } catch (IOException e) {
		        throw new RuntimeException("Error deleting video: " + e.getMessage());
		    }
	}

	
}

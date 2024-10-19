package Utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyDir {
	
	 public static void copyDirectory(Path source, Path target) throws IOException {
	        // Cammina lungo la directory di origine e copia i file e le sottodirectory
	        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
	            
	            @Override
	            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
	                // Creare la directory nella destinazione se non esiste
	                Path targetDir = target.resolve(source.relativize(dir));
	                if (Files.notExists(targetDir)) {
	                    Files.createDirectories(targetDir);
	                }
	                return FileVisitResult.CONTINUE;
	            }
	            
	            @Override
	            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	                // Copiare ogni file
	                Files.copy(file, target.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
	                return FileVisitResult.CONTINUE;
	            }
	        });
	    }
	    
	    public static void main(String[] args) {
	        // Imposta il percorso della directory sorgente e destinazione
	        Path sourceDir = Paths.get("path/to/source");
	        Path targetDir = Paths.get("path/to/destination");
	        
	        try {
	            copyDirectory(sourceDir, targetDir);
	            System.out.println("Copia completata con successo!");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}

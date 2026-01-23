import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 588. Design In-Memory File System
 */
public class _588 {
    class FileSystem {
        class File {
            boolean isFile;
            StringBuilder content;
            Map<String, File> children;
            File() {
                isFile = false;
                content = new StringBuilder();
                children = new TreeMap<>();
            }
        }

        private File root;

        public FileSystem() {
            root = new File();
        }

        private File cd(String path) {
            String[] dirs = path.split("/");
            File curr = root;
            for (int i = 1; i < dirs.length; i++) {
                String dir = dirs[i];
                // if (dir.isEmpty()) continue; // Skip empty segments
                if (!curr.children.containsKey(dir)) {
                    return null;
                }
                curr = curr.children.get(dir);
            }
            return curr;
        }
        
        public List<String> ls(String path) {
            File file = cd(path);
            if (file == null) return List.of();

            if (file.isFile) {
                int start = path.lastIndexOf("/") + 1;
                return List.of(path.substring(start, path.length()));
            }
            return new ArrayList<>(file.children.keySet());
        }
        
        public void mkdir(String path) {
            String[] dirs = path.split("/");
            File curr = root;
            for (int i = 1; i < dirs.length; i++) {
                String dir = dirs[i];
                // if (curr.isFile) return;
                curr.children.putIfAbsent(dir, new File());
                curr = curr.children.get(dir);
            }
        }
        
        public void addContentToFile(String filePath, String content) {
            mkdir(filePath);
            File file = cd(filePath);
            file.isFile = true;
            file.content.append(content);
        }
        
        public String readContentFromFile(String filePath) {
            File file = cd(filePath);
            if (file == null) {
                return "";
            }
            return file.content.toString();
        }
    }
}

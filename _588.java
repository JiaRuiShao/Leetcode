import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 588. Design In-Memory File System
 */
public class _588 {
    class FileSystem {
        class Directory {
            StringBuilder content;
            boolean isFile;
            Map<String, Directory> directories;
            Directory() {
                content = new StringBuilder();
                isFile = false;
                directories = new TreeMap<>();
            }
        }

        private Directory root;

        public FileSystem() {
            root = new Directory();
        }
        
        public List<String> ls(String path) {
            List<String> names = new ArrayList<>();
            Directory curr = navigate(path);
            if (curr.isFile) {
                int start = path.lastIndexOf("/") + 1;
                if (start < path.length()) {
                    names.add(path.substring(start, path.length()));
                }
            } else {
                names = new ArrayList<>(curr.directories.keySet());
            }
            return names;
        }

        private Directory navigate(String path) {
            Directory curr = root;
            String[] dirs = path.split("/");
            for (int i = 1; i < dirs.length; i++) {
                String dir = dirs[i];
                curr.directories.putIfAbsent(dir, new Directory());
                curr = curr.directories.get(dir);
            }
            return curr;
        }
        
        public void mkdir(String path) {
            navigate(path);
        }
        
        public void addContentToFile(String filePath, String content) {
            Directory curr = navigate(filePath);
            curr.isFile = true;
            curr.content.append(content);
        }
        
        public String readContentFromFile(String filePath) {
            Directory curr = navigate(filePath);
            return curr.content.toString();
        }
    }
}

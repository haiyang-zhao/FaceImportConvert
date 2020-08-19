import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Convert {
    public static void main(String[] args) {
        try {
            List<User> users = new ArrayList<>();
            final InputStream stream = Convert.class.getResourceAsStream("metadata.json");
            Gson gson = new Gson();
            final JsonData jsonData = gson.fromJson(new InputStreamReader(stream), JsonData.class);
            final Map<String, List<Info>> map = jsonData.data.stream()
                    .collect(Collectors.groupingBy(info -> info.person.uid));
            map.forEach((key, value) -> {
                User user = new User();
                user.setUid(key);
                user.setExtraId(key);
                user.setName(value.get(0).person.name);
                value.forEach(info -> user.getPhotos().add(info.image.file_name));
                users.add(user);
            });

            String fileName = Convert.class.getResource("/").getPath() + "user" + System.currentTimeMillis() + ".xlsx";
//            EasyExcel.write(fileName, User.class).sheet("Sheet1").doWrite(users);
            copyFile(users);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(List<User> users) {
        File file = new File("/Users/zhaohaiyang/project/FaceImportConvert/src/main/resources/wg_face_export");
        if (file.isDirectory()) {
            Arrays.stream(Objects.requireNonNull(file.listFiles(f -> f.length() > 10 * 1024))).forEach(
                    f -> {
                        final Optional<User> mayUser = users.stream().filter(user -> user.getPhotos()
                                .stream().anyMatch(p -> p.equals(f.getName()))).findFirst();
                        if (mayUser.isPresent()) {
                            final User user = mayUser.get();
                            final List<String> photos = user.getPhotos();
                            for (int i = 0; i < photos.size(); i++) {
                                if (photos.get(i).equals(f.getName())) {
                                    try {
                                        final String dirName = "/Users/zhaohaiyang/project/FaceImportConvert/src/main/resources/photos/" + i;
                                        File dir = new File(dirName);
                                        if (!dir.exists()) {
                                            dir.mkdir();
                                        }
                                        Files.copy(Paths.get(f.getAbsolutePath()),
                                                Paths.get(dirName,
                                                        user.getUid().concat(".jpg")));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
            );
        }
    }

}

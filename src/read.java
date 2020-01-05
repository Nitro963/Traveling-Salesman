import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class read {
    public void ReadJson() {
        try (Reader reader = new FileReader("src/Subjects.code-workspace.json")) {
            JSONParser parser = new JSONParser();
            JSONArray list = (JSONArray) parser.parse(reader);
            ArrayList<Subject> subjects = new ArrayList<>();
            for (Object obj : list) {
                JSONObject jsonObject = (JSONObject) obj;
                Subject subject = new Subject((String) jsonObject.get("name"), (long) jsonObject.get("studentCnt"), (long) jsonObject.get("day"), (long) jsonObject.get("time"));
                subjects.add(subject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        try (Reader reader = new FileReader("src/Tables.code-workspace.json")) {
            JSONParser parser = new JSONParser();
            JSONArray list = (JSONArray) parser.parse(reader);
            ArrayList<ClassRoom> rooms = new ArrayList<>();
            for (Object obj : list) {
                JSONObject jsonObject = (JSONObject) obj;
                ClassRoom room = new ClassRoom((String) jsonObject.get("name"), (long) jsonObject.get("floor"), (long) jsonObject.get("cap"));
                rooms.add(room);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        try (Reader reader = new FileReader("src/Tables.code-workspace.json")) {
            JSONParser parser = new JSONParser();
            JSONArray list = (JSONArray) parser.parse(reader);
            ArrayList<Watcher> watchers = new ArrayList<>();
            for (Object obj : list) {
                JSONObject jsonObject = (JSONObject) obj;
                String type = (String) jsonObject.get("type");
                switch (type) {
                    case "master": {
                        MasterStudent masterStudent = new MasterStudent();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

    }
}

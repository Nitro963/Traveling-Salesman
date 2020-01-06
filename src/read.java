import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class read {
    public HashMap<String, Object> ReadJson() {
        ArrayList<Subject> subjects = new ArrayList<>();
        ArrayList<ClassRoom> rooms = new ArrayList<>();
        ArrayList<MasterStudent> masterStudents = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<Teacher> teachers = new ArrayList<>();
        ArrayList<TeacherAssistant> teacherAssistants = new ArrayList<>();
        try (Reader reader = new FileReader("src/Subjects.code-workspace.json")) {
            JSONParser parser = new JSONParser();
            JSONArray list = (JSONArray) parser.parse(reader);
            for (Object obj : list) {
                JSONObject jsonObject = (JSONObject) obj;
                Subject subject = new Subject((String) jsonObject.get("name"), (long) jsonObject.get("studentCnt"), (long) jsonObject.get("day"), (long) jsonObject.get("time"));
                subjects.add(subject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try (Reader reader = new FileReader("src/Tables.code-workspace.json")) {
            JSONParser parser = new JSONParser();
            JSONArray list = (JSONArray) parser.parse(reader);
            for (Object obj : list) {
                JSONObject jsonObject = (JSONObject) obj;
                ClassRoom room = new ClassRoom((String) jsonObject.get("name"), (long) jsonObject.get("floor"), (long) jsonObject.get("cap"));
                rooms.add(room);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try (Reader reader = new FileReader("src/Watchers.code-workspace.json")) {
            JSONParser parser = new JSONParser();
            JSONArray list = (JSONArray) parser.parse(reader);
            int id = -1;
            for (Object obj : list) {
                JSONObject jsonObject = (JSONObject) obj;
                String type = (String) jsonObject.get("type");
                id++;
                switch (type) {
                    case "master": {
                        Object object = jsonObject.get("constrain");
                        MasterStudent masterStudent = new MasterStudent((String) jsonObject.get("name"), id,
                                new Constrain(
                                        (String) ((JSONObject) object).get("days"), (String) ((JSONObject) object).get("secDays"), (long) ((JSONObject) object).get("conTime"), (long) ((JSONObject) object).get("cntTime")
                                ));
                        masterStudents.add(masterStudent);
                        break;
                    }
                    case "Teacher": {
                        Object object = jsonObject.get("constrain");
                        Teacher teacher = new Teacher((String) jsonObject.get("name"), id,
                                new Constrain(
                                        (String) ((JSONObject) object).get("days"), (String) ((JSONObject) object).get("secDays"), (long) ((JSONObject) object).get("conTime"), (long) ((JSONObject) object).get("cntTime")
                                )
                        );
                        teachers.add(teacher);
                        break;
                    }
                    case "TeacherAssist": {
                        Object object = jsonObject.get("constrain");
                        TeacherAssistant teacherAssistant = new TeacherAssistant((String) jsonObject.get("name"), id,
                                new Constrain(
                                        (String) ((JSONObject) object).get("days"), (String) ((JSONObject) object).get("secDays"), (long) ((JSONObject) object).get("conTime"), (long) ((JSONObject) object).get("cntTime")
                                )
                        );
                        teacherAssistants.add(teacherAssistant);
                        break;
                    }
                    case "Employee": {
                        Employee employee = new Employee((String) jsonObject.get("name"), id);
                        employees.add(employee);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("master", masterStudents);
        retMap.put("teachers", teachers);
        retMap.put("employees", employees);
        retMap.put("teacherAssistants", teacherAssistants);
        retMap.put("rooms", rooms);
        retMap.put("subjects", subjects);
        return retMap;
    }
}

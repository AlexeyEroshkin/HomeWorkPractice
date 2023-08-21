package org.db.students;

import java.util.Map;

public class StudentCommandHAndler {
    private StudentStorage studentStorage = new StudentStorage();

    public void processCommand(Command command) {
        Action action = command.getAction();
        switch (action) {
            case CREATE -> {
                processCreateCommand(command);
                break;
            }
            case UPDATE -> {
                processUpdateCommand(command);
                break;
            }
            case DELETE -> {
                processDeleteCommand(command);
                break;
            }
            case STATS_BY_COURSE -> {
                processStatsByCourseCommand(command);
                break;
            }
            case STATS_BY_CITY -> {
                processStatsByCityCommand(command);
                break;
            }
            case SEARCH -> {
                processSearchCommand(command);
                break;
            }
            default -> {
                System.out.println("Действие не поддерживается");
            }
        }

        System.out.println("Обработка команды. Действие: " + command.getAction().name() +
                ",данные: " + command.getData());
    }

    private void processSearchCommand(Command command) {
        try {
            String surname = command.getData();
            studentStorage.search(surname);
        } catch (Exception e) {
            System.out.println("Проблема поиска студента," +
                    "пожалуйста ВВедите : оставьте пустую строку для просмотра всего списка студентов;" +
                    "введите фамилию студента;" +
                    "введите фамилию двух студентов через запятую без пробела " + e.getMessage());
        }
    }

    private void processStatsByCourseCommand(Command command) {
        Map<String, Long> course = studentStorage.getCountByCourse();
        studentStorage.printMap(course);
    }

    private void processStatsByCityCommand(Command command) {
        Map<String, Long> city = studentStorage.getCountByCity();
        studentStorage.printMap(city);
    }

    private void processCreateCommand(Command command) {
        try {


            String data = command.getData();
            String[] dataArray = data.split(",");

            Student student = new Student();
            student.setSurname(dataArray[0]);
            student.setName(dataArray[1]);
            student.setCourse(dataArray[2]);
            student.setCity(dataArray[3]);
            student.setAge(Integer.valueOf(dataArray[4]));


            studentStorage.createStudent(student);
            studentStorage.printAll();

        } catch (Exception e) {
            System.out.println("Проблема создания студента," +
                    "пожалуйста ВВедите фамилию,имя,курс,город," +
                    "возраст через запятую и без пробелов! " + e.getMessage());
        }
    }

    public void processUpdateCommand(Command command) {
        try {
            String data = command.getData();
            String[] dataArray = data.split(",");
            Long id = Long.valueOf(dataArray[0]);

            Student student = new Student();

            student.setSurname(dataArray[1]);
            student.setName(dataArray[2]);
            student.setCourse(dataArray[3]);
            student.setCity(dataArray[4]);
            student.setAge(Integer.valueOf(dataArray[5]));

            studentStorage.updateStudent(id, student);
            studentStorage.printAll();


        } catch (Exception e) {
            System.out.println("Проблема обновления студента," +
                    "пожалуйста ВВедите уникальный идентификатор,фамилию,имя,курс," +
                    "город,возраст через запятую и без пробелов! " + e.getMessage());
        }
    }

    public void processDeleteCommand(Command command) {
        try {


            String data = command.getData();
            Long id = Long.valueOf(data);
            studentStorage.deleteStudent(id);
            studentStorage.printAll();
        } catch (Exception e) {
            System.out.println("Проблема удаления студента," +
                    "пожалуйста ВВедите уникальный идентификатор студента" + e.getMessage());
        }
    }
}
